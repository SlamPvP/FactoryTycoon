package com.slampvp.factory.plot;

import com.slampvp.factory.FactoryServer;
import com.slampvp.factory.common.Constants;
import com.slampvp.factory.database.DatabaseFormatter;
import com.slampvp.factory.database.DatabaseManager;
import com.slampvp.factory.database.queries.PlotQueries;
import com.slampvp.factory.plot.models.*;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Player;

import java.sql.SQLException;
import java.sql.Types;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public final class PlotManager {
    private static PlotManager instance;

    // TODO: Store this in a map.
    private final HashSet<Plot> plots;

    private PlotManager() {
        this.plots = new HashSet<>();
    }

    public static synchronized PlotManager getInstance() {
        if (instance == null) {
            instance = new PlotManager();
        }
        return instance;
    }

    public void init() {
        FactoryServer.LOGGER.info("Initializing Plot Manager...");

        FactoryServer.LOGGER.info("Initializing Plot Listener...");
        new PlotListener();
        FactoryServer.LOGGER.info("Initializing Plot Listener ✔");
    }

    public List<Plot> getPlots(Player player) {
        return plots
                .stream()
                .filter(plot -> plot.getOwner().equals(player.getUuid()))
                .toList();
    }

    public Optional<Plot> getPlot(Point point) {
        return plots
                .stream()
                .filter(plot -> plot.contains(point))
                .findFirst();
    }

    public void loadPlots(Player player) {
        DatabaseManager.getInstance().executeQuery(
                PlotQueries.Select.JOINED_BY_OWNER,
                preparedStatement -> {
                    preparedStatement.setString(1, player.getUuid().toString());
                },
                resultSet -> {
                    try {
                        while (resultSet.next()) {
                            Plot plot = new Plot(
                                    resultSet.getLong("id"),
                                    PlotId.fromSQL(resultSet.getString("plot_id")),
                                    UUID.fromString(resultSet.getString("owner")),
                                    DatabaseFormatter.stringToVec(resultSet.getString("start")),
                                    DatabaseFormatter.stringToVec(resultSet.getString("end")),
                                    DatabaseFormatter.stringToPos(resultSet.getString("spawn"))
                            );
                            this.plots.add(plot);
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException("Error processing ResultSet", e);
                    } finally {
                        try {
                            resultSet.close();
                        } catch (SQLException e) {
                            FactoryServer.LOGGER.error(e.toString());
                        }
                    }
                });
    }

    /**
     * Tries to claim the plot at the current location of the player.
     * If there is no valid plot at the position or the plot has already been claimed, returns false.
     *
     * @param player the player who executed the claim
     * @return whether the claim was successful
     */
    public ClaimResult claimPlot(Player player) {
        Pos position = player.getPosition();
        return claimPlot(player, position);
    }

    /**
     * Tries to claim the plot at the provided position.
     * If there is no valid plot at the position or the plot has already been claimed, returns false.
     *
     * @param player   the player who executed the claim
     * @param position the position of the requested plot
     * @return whether the claim was successful
     */
    public ClaimResult claimPlot(Player player, Pos position) {
        if (!PlotGenerator.isInPlot(position)) {
            return ClaimResult.NOT_IN_PLOT;
        }

        Optional<Plot> optionalPlot = getPlot(position);

        if (optionalPlot.isPresent()) {
            return ClaimResult.ALREADY_CLAIMED;
        }

        Vec[] dimensions = PlotGenerator.getDimensions(position);
        Vec center = dimensions[0].add(dimensions[1]).div(2);
        PlotId id = new PlotId(Math.floorDiv(center.blockX(), Constants.Plot.FULL_WIDTH), Math.floorDiv(center.blockZ(), Constants.Plot.FULL_WIDTH));

        Plot plot = new Plot(
                id,
                player.getUuid(),
                Vec.fromPoint(dimensions[0]),
                Vec.fromPoint(dimensions[1])
        );

        this.plots.add(plot);

        PlotGenerator.claimPlot(position, player.getInstance());

        DatabaseManager.getInstance().executeUpdate(PlotQueries.Insert.PLOT, preparedStatement -> {
            preparedStatement.setObject(1, plot.getId().toSQL(), Types.OTHER);
            preparedStatement.setString(2, plot.getOwner().toString());
            preparedStatement.setObject(3, DatabaseFormatter.pointToString(plot.getStart()), Types.OTHER);
            preparedStatement.setObject(4, DatabaseFormatter.pointToString(plot.getEnd()), Types.OTHER);
            preparedStatement.setObject(5, DatabaseFormatter.pointToString(plot.getSpawn()), Types.OTHER);
        }).thenAccept(plot::setDbId);

        return ClaimResult.SUCCESS;
    }

    /**
     * Tries to un-claim the plot at the current location of the player.
     * If there is no valid plot at the location or the plot is not claimed, returns false.
     *
     * @param player the player who executed the un-claim
     * @return whether the un-claim was successful
     */
    public UnClaimResult unClaimPlot(Player player) {
        Pos position = player.getPosition();

        if (!PlotGenerator.isInPlot(position)) {
            return UnClaimResult.NOT_IN_PLOT;
        }

        Optional<Plot> optionalPlot = getPlot(player.getPosition());

        if (optionalPlot.isEmpty()) {
            return UnClaimResult.NOT_CLAIMED;
        }

        Plot plot = optionalPlot.get();

        this.plots.remove(plot);

        PlotGenerator.unClaimPlot(player.getPosition(), player.getInstance());
        DatabaseManager.getInstance().executeUpdate(PlotQueries.Delete.PLOT_BY_ID, preparedStatement -> {
            preparedStatement.setObject(1, plot.getDbId(), Types.OTHER);
        });

        return UnClaimResult.SUCCESS;
    }

    /**
     * Tries to claim the first available plot.
     *
     * @param player the player who executed the claim
     * @return whether the claim was successful
     */
    public ClaimResult claimFreePlot(Player player) {
        int x = 0;
        int z = 0;
        int dx = Constants.Plot.FULL_WIDTH;
        int dz = 0;

        int segmentLength = 1;
        int segmentPassed = 0;

        for (int i = 0; i < plots.size() + 1; i++) {
            ClaimResult result = claimPlot(player, new Pos(x, Constants.HEIGHT, z));
            if (result == ClaimResult.SUCCESS) {
                return result;
            }

            x += dx;
            z += dz;
            segmentPassed++;

            if (segmentPassed == segmentLength) {
                segmentPassed = 0;

                int temp = dx;
                dx = -dz;
                dz = temp;

                if (dz == 0) {
                    segmentLength++;
                }
            }
        }

        return ClaimResult.FAILURE;
    }

    public MergeResult mergePlot(Player player) {
        Pos position = player.getPosition();

        Optional<Plot> optionalPlot = getPlot(position);

        if (optionalPlot.isEmpty() || !optionalPlot.get().getOwner().equals(player.getUuid())) {
            return MergeResult.NOT_IN_PLOT;
        }

        Plot plot = optionalPlot.get();

        Vec direction = position.direction();
        Vec offset = direction.mul(Constants.Plot.FULL_WIDTH);
        Vec center = plot.getCenter();
        Vec targetCenter = center.add(offset);

        Optional<Plot> optionalTargetPlot = getPlot(targetCenter);
        if (optionalTargetPlot.isEmpty()
                || !optionalTargetPlot.get().getOwner().equals(player.getUuid())) {
            return MergeResult.NO_MERGE_CANDIDATE;
        }

        Plot targetPlot = optionalTargetPlot.get();

        player.sendMessage(plot.getStart() + " " + plot.getEnd());
        player.sendMessage(targetPlot.getStart() + " " + targetPlot.getEnd());

        plot.mergeWith(targetPlot);

        player.sendMessage(plot.getStart() + " " + plot.getEnd());

        PlotGenerator.setPlotBorder(plot, player.getInstance());
        plots.remove(targetPlot);

        return MergeResult.SUCCESS;
    }
}
