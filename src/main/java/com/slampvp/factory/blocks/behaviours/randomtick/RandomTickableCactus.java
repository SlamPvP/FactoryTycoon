package com.slampvp.factory.blocks.behaviours.randomtick;

import com.slampvp.factory.blocks.VanillaBlocks;
import com.slampvp.factory.randomticksystem.RandomTick;
import net.minestom.server.coordinate.Point;
import net.minestom.server.instance.block.Block;
import org.jetbrains.annotations.NotNull;

public class RandomTickableCactus extends RandomTickable {

    protected final int maxHeight;
    protected final int growthChance;

    public RandomTickableCactus(VanillaBlocks.@NotNull BlockContext context, int maxHeight, int growthChance) {
        super(context);

        this.maxHeight = maxHeight;
        this.growthChance = growthChance;
    }

    @Override
    public void randomTick(RandomTick randomTick) {
        Point pos = randomTick.position();
        Block block = context.block();

        if (getCurrentHeight(pos, block) >= maxHeight) { return; }
        if (context.random().nextInt(growthChance) != 0) { return; }

        // Check for air above the cactus
        if (context.instance().getBlock(pos.add(0, 1, 0)) == Block.AIR) {
            context.instance().setBlock(pos.add(0, 1, 0), block);
        }
    }

    /**
     * Determine the current height of a cactus
     *
     * @param pos the position of the cactus
     * @param block the block of the cactus
     * @return the height of the cactus
     */
    private int getCurrentHeight(Point pos, Block block) {
        int height = 1;
        while (context.instance().getBlock(pos.add(0, height, 0)) == block) {
            height++;
        }

        int depth = 1;
        while (context.instance().getBlock(pos.add(0, -depth, 0)) == block) {
            depth++;
        }

        return height + depth - 1;
    }
}
