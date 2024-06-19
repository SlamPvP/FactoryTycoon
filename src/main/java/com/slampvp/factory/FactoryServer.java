package com.slampvp.factory;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.instance.block.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class FactoryServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(FactoryServer.class);

    private static final int HEIGHT = 20;
    private static final int PLOT_SIZE = 20;   // This needs to be an even number.
    private static final int ROAD_WIDTH = 3;

    public static void main(String[] args) {
        MinecraftServer minecraftServer = MinecraftServer.init();

        MojangAuth.init();

        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        InstanceContainer instanceContainer = instanceManager.createInstanceContainer();

        instanceContainer.setChunkSupplier(LightingChunk::new);

        instanceContainer.setGenerator(unit -> {
            final Point start = unit.absoluteStart();
            final Point size = unit.size();

            unit.modifier().fillHeight(start.blockY(), HEIGHT, Block.STONE);

            for (int x = start.blockX(); x < start.blockX() + size.blockX(); x++) {
                for (int z = start.blockZ(); z < start.blockZ() + size.blockZ(); z++) {
                    Block block = getBlockForPoint(x, z);
                    unit.modifier().setBlock(new Vec(x, HEIGHT, z), block);
                }
            }
        });

        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();

        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            final Player player = event.getPlayer();

            event.setSpawningInstance(instanceContainer);

            player.setRespawnPoint(new Pos(0, 40, 0));
            player.setGameMode(GameMode.CREATIVE);
        });

        minecraftServer.start("0.0.0.0", 25565);
    }

    private static Block getBlockForPoint(int x, int z) {
        int plotWithBorderSize = PLOT_SIZE + ROAD_WIDTH;

        // Calculate plot center coordinates relative to (0, 0)
        int plotCenterX = Math.floorDiv(x + plotWithBorderSize / 2, plotWithBorderSize) * plotWithBorderSize;
        int plotCenterZ = Math.floorDiv(z + plotWithBorderSize / 2, plotWithBorderSize) * plotWithBorderSize;

        // Calculate the position within the plot
        int posXWithinPlot = x - plotCenterX + plotWithBorderSize / 2;
        int posZWithinPlot = z - plotCenterZ + plotWithBorderSize / 2;

        // Check if we are at the center
        int plotCenter = PLOT_SIZE / 2;
        if (posXWithinPlot == plotCenter && posZWithinPlot == plotCenter) {
            return Block.GREEN_CONCRETE;
        }

        // Check if we are at the border
        if (posXWithinPlot == 0 || posXWithinPlot == PLOT_SIZE - 1 || posZWithinPlot == 0 || posZWithinPlot == PLOT_SIZE - 1) {
            return Block.RED_CONCRETE;
        }

        // Check if we are at the road
        if (posXWithinPlot >= PLOT_SIZE || posZWithinPlot >= PLOT_SIZE) {
            return Block.YELLOW_CONCRETE;
        }

        return Block.GRASS_BLOCK;
    }
}
