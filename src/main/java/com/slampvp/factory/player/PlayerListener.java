package com.slampvp.factory.player;

import com.slampvp.factory.common.Constants;
import com.slampvp.factory.minion.MinionManager;
import com.slampvp.factory.plot.PlotManager;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.instance.InstanceContainer;

public class PlayerListener {

    public PlayerListener(InstanceContainer instanceContainer) {
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();

        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            event.setSpawningInstance(instanceContainer);

            Player player = event.getPlayer();

            player.setRespawnPoint(new Pos(0.5, Constants.HEIGHT + 1, 0.5));
            player.setGameMode(GameMode.CREATIVE);
        });

        globalEventHandler.addListener(PlayerSpawnEvent.class, event -> {
            Player player = event.getPlayer();

            PlotManager.getInstance().loadPlots(player);
            MinionManager.getInstance().loadMinions(player);
        });
    }
}
