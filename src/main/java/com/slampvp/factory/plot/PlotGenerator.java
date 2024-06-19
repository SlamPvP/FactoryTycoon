package com.slampvp.factory.plot;

import com.slampvp.factory.util.Constants;
import com.slampvp.factory.util.Manager;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.generator.Generator;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public final class PlotGenerator {
    public static @NotNull Generator getGenerator() {
        return unit -> {
            Point start = unit.absoluteStart();
            Point size = unit.size();

            unit.modifier().fillHeight(start.blockY(), Constants.Plot.HEIGHT, Block.STONE);

            for (int x = start.blockX(); x < start.blockX() + size.blockX(); x++) {
                for (int z = start.blockZ(); z < start.blockZ() + size.blockZ(); z++) {
                    Block block = getBlockForPoint(x, z);
                    unit.modifier().setBlock(new Vec(x, Constants.Plot.HEIGHT, z), block);
                }
            }
        };
    }

    private static Block getBlockForPoint(int x, int z) {
        int plotWithBorderSize = Constants.Plot.PLOT_SIZE + Constants.Plot.ROAD_WIDTH;

        // Calculate plot center coordinates relative to (0, 0)
        int plotCenterX = Math.floorDiv(x + plotWithBorderSize / 2, plotWithBorderSize) * plotWithBorderSize;
        int plotCenterZ = Math.floorDiv(z + plotWithBorderSize / 2, plotWithBorderSize) * plotWithBorderSize;

        // Calculate the position within the plot
        int posXWithinPlot = x - plotCenterX + plotWithBorderSize / 2;
        int posZWithinPlot = z - plotCenterZ + plotWithBorderSize / 2;

        // Check if we are at the center
        int plotCenter = Constants.Plot.PLOT_SIZE / 2;
        if (posXWithinPlot == plotCenter && posZWithinPlot == plotCenter) {
            return Block.GREEN_CONCRETE;
        }

        // Check if we are at the border
        if (posXWithinPlot == 0 || posXWithinPlot == Constants.Plot.PLOT_SIZE - 1
                || posZWithinPlot == 0 || posZWithinPlot == Constants.Plot.PLOT_SIZE - 1) {
            return Block.RED_CONCRETE;
        }

        // Check if we are at the road
        if (posXWithinPlot >= Constants.Plot.PLOT_SIZE || posZWithinPlot >= Constants.Plot.PLOT_SIZE) {
            return Block.YELLOW_CONCRETE;
        }

        return Block.GRASS_BLOCK;
    }
}
