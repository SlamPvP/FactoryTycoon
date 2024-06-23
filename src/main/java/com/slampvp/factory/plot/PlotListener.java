package com.slampvp.factory.plot;

import com.slampvp.factory.common.Constants;
import com.slampvp.factory.plot.models.Plot;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.BlockVec;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;

import java.util.Optional;

public class PlotListener {
    public PlotListener() {
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        PlotManager plotManager = PlotManager.getInstance();

        globalEventHandler.addListener(PlayerBlockBreakEvent.class, event -> {
            BlockVec position = event.getBlockPosition();
            Optional<Plot> optionalPlot = plotManager.getPlot(position);

            if (optionalPlot.isEmpty()) {
                System.out.println("no plot");
                event.setCancelled(true);
                return;
            }

            Player player = event.getPlayer();
            Plot plot = optionalPlot.get();

            if (!plot.isAdded(player)) {
                event.setCancelled(true);
            }

        });

        globalEventHandler.addListener(PlayerBlockPlaceEvent.class, event -> {
            BlockVec position = event.getBlockPosition();
            Optional<Plot> optionalPlot = plotManager.getPlot(position);

            if (optionalPlot.isEmpty()) {
                event.setCancelled(true);
                return;
            }

            Player player = event.getPlayer();
            Plot plot = optionalPlot.get();

            if (!plot.isAdded(player)) {
                event.setCancelled(true);
                return;
            }

            if (position.blockY() > Constants.Plot.PLOT_HEIGHT) {
                event.setCancelled(true);
            }
        });
    }
}
