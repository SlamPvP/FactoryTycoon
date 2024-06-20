package com.slampvp.factory.plot;

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

            if (!PlotGenerator.isInPlot(position)) {
                event.setCancelled(true);
                return;
            }

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

        });

        globalEventHandler.addListener(PlayerBlockPlaceEvent.class, event -> {
            BlockVec position = event.getBlockPosition();

            if (!PlotGenerator.isInPlot(position)) {
                event.setCancelled(true);
                return;
            }

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

        });
    }
}
