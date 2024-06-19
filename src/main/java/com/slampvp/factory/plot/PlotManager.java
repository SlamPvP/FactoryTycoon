package com.slampvp.factory.plot;

import com.slampvp.factory.FactoryServer;
import com.slampvp.factory.common.Constants;
import com.slampvp.factory.common.Locale;
import net.minestom.server.coordinate.Point;
import net.minestom.server.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class PlotManager {
    private static PlotManager instance;

    private final HashSet<Plot> plots;

    private PlotManager() {
        this.plots = new HashSet<>();
    }

    public static void init() {
        FactoryServer.LOGGER.info("Initializing Plot Manager...");
        getInstance();
    }

    public static synchronized PlotManager getInstance() {
        if (instance == null) {
            instance = new PlotManager();
        }
        return instance;
    }

    public Set<Plot> getPlots(Player player) {
        return null;
    }

    public boolean getPlot(Point point) {

        return PlotGenerator.isInPlot(point);
    }
}
