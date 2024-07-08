package com.slampvp.factory.blocks.behaviours.randomtick.bamboo;

import com.slampvp.factory.blocks.VanillaBlocks;
import com.slampvp.factory.blocks.behaviours.randomtick.RandomTickable;
import com.slampvp.factory.randomticksystem.RandomTick;
import net.minestom.server.coordinate.Point;
import net.minestom.server.instance.block.Block;
import org.jetbrains.annotations.NotNull;

public class RandomTickableBambooCrop extends RandomTickable {

    private final int growthChance;

    public RandomTickableBambooCrop(VanillaBlocks.@NotNull BlockContext context, int growthChance) {
        super(context);

        this.growthChance = growthChance;
    }

    @Override
    public void randomTick(RandomTick randomTick) {
        Point pos = randomTick.position();

        if (context.instance().getBlock(pos.add(0, 1, 0)) == Block.AIR) {
            context.instance().setBlock(pos, Block.BAMBOO);
            context.instance().setBlock(pos.add(0, 1, 0), Block.BAMBOO.withProperty("leaves", "small"));
        }
    }
}
