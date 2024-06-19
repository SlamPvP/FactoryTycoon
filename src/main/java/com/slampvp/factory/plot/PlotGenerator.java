package com.slampvp.factory.plot;

import com.slampvp.factory.common.Constants;
import net.minestom.server.coordinate.BlockVec;
import net.minestom.server.coordinate.Point;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.generator.Generator;
import org.jetbrains.annotations.NotNull;

public final class PlotGenerator {
    public static @NotNull Generator getGenerator() {
        return unit -> {
            Point start = unit.absoluteStart();
            Point size = unit.size();

            unit.modifier().fillHeight(start.blockY(), Constants.Plot.HEIGHT, Block.STONE);

            for (int x = start.blockX(); x < start.blockX() + size.blockX(); x++) {
                for (int z = start.blockZ(); z < start.blockZ() + size.blockZ(); z++) {
                    Block block = getBlockForPoint(x, z);
                    unit.modifier().setBlock(new BlockVec(x, Constants.Plot.HEIGHT, z), block);
                }
            }
        };
    }

    public static boolean isInPlot(Point point) {
        int x = point.blockX();
        int z = point.blockZ();

        int[] positionInfo = calculatePositionInfo(x, z);

        return positionInfo[0] >= 0 && positionInfo[0] < Constants.Plot.PLOT_SIZE && positionInfo[1] >= 0 && positionInfo[1] < Constants.Plot.PLOT_SIZE;
    }

    public static void claimPlot(Instance instance, Point playerPosition) {
        int plotCenterX = calculatePlotCenter(playerPosition.blockX());
        int plotCenterZ = calculatePlotCenter(playerPosition.blockZ());

        int plotStartX = plotCenterX - Constants.Plot.PLOT_SIZE / 2 - Constants.Plot.ROAD_WIDTH;
        int plotEndX = plotCenterX + Constants.Plot.PLOT_SIZE / 2 + Constants.Plot.ROAD_WIDTH;
        int plotStartZ = plotCenterZ - Constants.Plot.PLOT_SIZE / 2 - Constants.Plot.ROAD_WIDTH;
        int plotEndZ = plotCenterZ + Constants.Plot.PLOT_SIZE / 2 + Constants.Plot.ROAD_WIDTH;

        for (int x = plotStartX; x <= plotEndX; x++) {
            instance.setBlock(new BlockVec(x, Constants.Plot.HEIGHT, plotStartZ), Block.GREEN_CONCRETE);
            instance.setBlock(new BlockVec(x, Constants.Plot.HEIGHT, plotEndZ), Block.GREEN_CONCRETE);
        }
        for (int z = plotStartZ; z <= plotEndZ; z++) {
            instance.setBlock(new BlockVec(plotStartX, Constants.Plot.HEIGHT, z), Block.GREEN_CONCRETE);
            instance.setBlock(new BlockVec(plotEndX, Constants.Plot.HEIGHT, z), Block.GREEN_CONCRETE);
        }
    }

    private static int calculatePlotCenter(int coordinate) {
        int plotWithBorderSize = Constants.Plot.PLOT_SIZE + Constants.Plot.ROAD_WIDTH;
        return Math.floorDiv(coordinate + plotWithBorderSize / 2, plotWithBorderSize) * plotWithBorderSize;
    }

    private static Block getBlockForPoint(int x, int z) {
        int[] positionInfo = calculatePositionInfo(x, z);

        // Check if we are at the center
        int plotCenter = Constants.Plot.PLOT_SIZE / 2;
        if (positionInfo[0] == plotCenter && positionInfo[1] == plotCenter) {
            return Block.GREEN_CONCRETE;
        }

        // Check if we are at the border
        if (positionInfo[0] == 0 || positionInfo[0] == Constants.Plot.PLOT_SIZE - 1 || positionInfo[1] == 0 || positionInfo[1] == Constants.Plot.PLOT_SIZE - 1) {
            return Block.RED_CONCRETE;
        }

        // Check if we are at the road
        if (positionInfo[0] >= Constants.Plot.PLOT_SIZE || positionInfo[1] >= Constants.Plot.PLOT_SIZE) {
            return Block.YELLOW_CONCRETE;
        }

        return Block.GRASS_BLOCK;
    }

    public static int[] calculatePositionInfo(int x, int z) {
        int plotWithBorderSize = Constants.Plot.PLOT_SIZE + Constants.Plot.ROAD_WIDTH;

        // Calculate plot center coordinates relative to (0, 0)
        int plotCenterX = Math.floorDiv(x + plotWithBorderSize / 2, plotWithBorderSize) * plotWithBorderSize;
        int plotCenterZ = Math.floorDiv(z + plotWithBorderSize / 2, plotWithBorderSize) * plotWithBorderSize;

        // Calculate the position within the plot
        int posXWithinPlot = x - plotCenterX + plotWithBorderSize / 2;
        int posZWithinPlot = z - plotCenterZ + plotWithBorderSize / 2;

        return new int[]{posXWithinPlot, posZWithinPlot};
    }
}
