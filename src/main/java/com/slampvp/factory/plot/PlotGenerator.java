package com.slampvp.factory.plot;

import com.slampvp.factory.common.Constants;
import com.slampvp.factory.plot.models.Plot;
import net.minestom.server.coordinate.BlockVec;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.batch.AbsoluteBlockBatch;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.generator.Generator;
import org.jetbrains.annotations.NotNull;

public final class PlotGenerator {
    public static @NotNull Generator getGenerator() {
        return unit -> {
            Point start = unit.absoluteStart();
            Point size = unit.size();

            unit.modifier().fillHeight(start.blockY(), Constants.HEIGHT, Block.STONE);

            for (int x = start.blockX(); x < start.blockX() + size.blockX(); x++) {
                for (int z = start.blockZ(); z < start.blockZ() + size.blockZ(); z++) {
                    Block block = getBlockForPoint(x, z);
                    unit.modifier().setBlock(new BlockVec(x, Constants.HEIGHT, z), block);
                }
            }
        };
    }

    public static boolean isInPlot(Point point) {
        int x = point.blockX();
        int z = point.blockZ();

        int[] positionInfo = calculateRelativePosition(x, z);

        return positionInfo[0] >= 0 && positionInfo[0] < Constants.Plot.PLOT_SIZE && positionInfo[1] >= 0 && positionInfo[1] < Constants.Plot.PLOT_SIZE;
    }

    public static void claimPlot(Pos position, Instance instance) {
        setPlotBorderAt(position, instance, Block.GREEN_CONCRETE);
    }

    public static void unClaimPlot(Pos position, Instance instance) {
        clearPlot(position, instance);
        setPlotBorderAt(position, instance, Block.RED_CONCRETE);
    }

    private static void setPlotBorderAt(Pos position, Instance instance, Block block) {
        int centerX = calculatePlotCenter(position.blockX());
        int centerZ = calculatePlotCenter(position.blockZ());

        int plotStartX = centerX - Constants.Plot.PLOT_SIZE / 2;
        int plotStartZ = centerZ - Constants.Plot.PLOT_SIZE / 2;

        AbsoluteBlockBatch absoluteBlockBatch = new AbsoluteBlockBatch();

        for (int x = 0; x <= Constants.Plot.PLOT_SIZE; x++) {
            for (int z = 0; z <= Constants.Plot.PLOT_SIZE; z++) {
                if (!isBorder(x, z)) {
                    continue;
                }

                absoluteBlockBatch.setBlock(new BlockVec(x + plotStartX, Constants.HEIGHT, z + plotStartZ), block);
            }
        }

        absoluteBlockBatch.apply(instance, () -> {
        });
    }

    public static void setPlotBorder(Plot plot, Instance instance) {
        Vec start = plot.getStart();
        Vec end = plot.getEnd();
        int height = Constants.HEIGHT;
        Block block = Block.GREEN_CONCRETE;

        AbsoluteBlockBatch absoluteBlockBatch = new AbsoluteBlockBatch();

        for (int x = start.blockX(); x <= end.blockX(); x++) {
            for (int z = start.blockZ(); z <= end.blockZ(); z++) {
                if (x == start.blockX() || x == end.blockX() || z == start.blockZ() || z == end.blockZ()) {
                    absoluteBlockBatch.setBlock(new BlockVec(x, height, z), block);
                }
            }
        }

        absoluteBlockBatch.apply(instance, () -> {
        });
    }

    public static void clearPlot(Pos position, Instance instance) {
        int centerX = calculatePlotCenter(position.blockX());
        int centerZ = calculatePlotCenter(position.blockZ());

        int plotStartX = centerX - Constants.Plot.PLOT_SIZE / 2;
        int plotStartZ = centerZ - Constants.Plot.PLOT_SIZE / 2;

        AbsoluteBlockBatch absoluteBlockBatch = new AbsoluteBlockBatch();

        for (int x = plotStartX + 1; x < plotStartX + Constants.Plot.PLOT_SIZE; x++) {
            for (int z = plotStartZ + 1; z < plotStartZ + Constants.Plot.PLOT_SIZE; z++) {
                Block block = getBlockForPoint(x, z);
                absoluteBlockBatch.setBlock(new BlockVec(x, Constants.HEIGHT, z), block);

                for (int y = Constants.HEIGHT + 1; y <= Constants.Plot.PLOT_HEIGHT; y++) {
                    absoluteBlockBatch.setBlock(new BlockVec(x, y, z), Block.AIR);
                }
            }
        }

        absoluteBlockBatch.apply(instance, () -> {
        });
    }

    public static Vec[] getDimensions(Pos position) {
        int plotCenterX = calculatePlotCenter(position.blockX());
        int plotCenterZ = calculatePlotCenter(position.blockZ());

        return new Vec[]{
                new Vec(plotCenterX - (Constants.Plot.PLOT_SIZE / 2.0), 0, plotCenterZ - (Constants.Plot.PLOT_SIZE / 2.0)),
                new Vec(plotCenterX + (Constants.Plot.PLOT_SIZE / 2.0), Constants.Plot.PLOT_HEIGHT, plotCenterZ + (Constants.Plot.PLOT_SIZE / 2.0))
        };
    }

    private static int calculatePlotCenter(int coordinate) {
        int plotWithBorderSize = Constants.Plot.PLOT_SIZE + Constants.Plot.ROAD_WIDTH;

        return Math.floorDiv(coordinate + plotWithBorderSize / 2, plotWithBorderSize) * plotWithBorderSize;
    }

    private static Block getBlockForPoint(int x, int z) {
        int[] positionInfo = calculateRelativePosition(x, z);

        // Check if we are at the center
        int plotCenter = Math.floorDiv(Constants.Plot.PLOT_SIZE, 2);
        if (positionInfo[0] == plotCenter && positionInfo[1] == plotCenter) {
            return Block.GREEN_CONCRETE;
        }

        // Check if we are at the border
        if (isBorder(positionInfo[0], positionInfo[1])) {
            return Block.RED_CONCRETE;
        }

        // Check if we are at the road
        if ((positionInfo[0] < 0 || positionInfo[1] < 0)
                || (positionInfo[0] >= Constants.Plot.PLOT_SIZE || positionInfo[1] >= Constants.Plot.PLOT_SIZE)) {
            return Block.YELLOW_CONCRETE;
        }

        return Block.GRASS_BLOCK;
    }

    private static boolean isBorder(int x, int z) {
        if (x < 0 || z < 0) {
            return false;
        }

        return ((x % (Constants.Plot.PLOT_SIZE)) == 0 && (z % (Constants.Plot.PLOT_SIZE)) == 0)
                || ((x % (Constants.Plot.PLOT_SIZE)) == 0 && (z < Constants.Plot.PLOT_SIZE))
                || ((z % (Constants.Plot.PLOT_SIZE)) == 0 && (x < Constants.Plot.PLOT_SIZE));
    }

    private static int[] calculateRelativePosition(int x, int z) {
        // Calculate plot center coordinates relative to (0, 0)
        int plotCenterX = calculatePlotCenter(x);
        int plotCenterZ = calculatePlotCenter(z);

        // Calculate the position within the plot
        int posXWithinPlot = x - plotCenterX + (Constants.Plot.PLOT_SIZE / 2);
        int posZWithinPlot = z - plotCenterZ + (Constants.Plot.PLOT_SIZE / 2);

        return new int[]{posXWithinPlot, posZWithinPlot};
    }
}
