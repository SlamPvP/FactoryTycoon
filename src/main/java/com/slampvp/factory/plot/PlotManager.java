package com.slampvp.factory.plot;

import com.slampvp.factory.FactoryServer;
import net.minestom.server.coordinate.BlockVec;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

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

    public Plot getFreePlot() {
        return null;
    }

    public Set<Plot> getPlots(Player player) {
        return null;
    }

    public Optional<Plot> getPlot(Point point) {
        return plots
                .stream()
                .filter(plot -> plot.contains(point))
                .findFirst();
    }

    /**
     * Tries to claim the plot at the current location of the player.
     * If there is no valid plot at the location or the plot has already been claimed, returns false.
     *
     * @param player the player who executed the claim
     * @return whether the claim was successful
     */
    public ClaimResult claimPlot(Player player) {
        Pos position = player.getPosition();

        if (!PlotGenerator.isInPlot(position)) {
            return ClaimResult.NOT_IN_PLOT;
        }

        Optional<Plot> optionalPlot = getPlot(player.getPosition());

        if (optionalPlot.isPresent()) {
            return ClaimResult.ALREADY_CLAIMED;
        }

        BlockVec[] dimensions = PlotGenerator.getDimensions(position);

        Plot plot = new Plot(
                UUID.randomUUID(),
                player.getUuid(),
                new BlockVec(dimensions[0]),
                new BlockVec(dimensions[1])
        );

        this.plots.add(plot);

        PlotGenerator.claimPlot(player);

        return ClaimResult.SUCCESS;
    }
}
