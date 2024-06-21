package com.slampvp.factory.plot;

import com.slampvp.factory.FactoryServer;
import com.slampvp.factory.common.Constants;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Player;

import java.util.*;

public class PlotManager {
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
     * @param player the player who executed the claim
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

        this.plots.remove(optionalPlot.get());

        PlotGenerator.unClaimPlot(player.getPosition(), player.getInstance());

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

        PlotId targetId = new PlotId(
                Math.floorDiv(targetCenter.blockX(), Constants.Plot.FULL_WIDTH),
                Math.floorDiv(targetCenter.blockZ(), Constants.Plot.FULL_WIDTH)
        );

        Optional<Plot> optionalTargetPlot = plots.stream()
                .filter(targetPlot -> targetPlot.getId().equals(targetId))
                .findFirst();

        if (optionalTargetPlot.isEmpty()
                || !optionalTargetPlot.get().getOwner().equals(player.getUuid())) {
            return MergeResult.NO_MERGE_CANDIDATE;
        }

        Plot targetPlot = optionalTargetPlot.get();
        plot.mergeWith(targetPlot);

        PlotGenerator.setPlotBorder(plot, player.getInstance());
        plots.remove(targetPlot);

        return MergeResult.SUCCESS;
    }
}
