package com.slampvp.factory.blocks.behaviours.randomtick;

import com.slampvp.factory.blocks.VanillaBlocks;
import com.slampvp.factory.randomticksystem.RandomTick;
import net.minestom.server.instance.block.Block;
import org.jetbrains.annotations.NotNull;

public class RandomTickableCrop extends RandomTickable {

    public final int maxAge;
    protected final int growthChance;

    public RandomTickableCrop(@NotNull VanillaBlocks.BlockContext context, int maxAge, int growthChance) {
        super(context);

        this.maxAge = maxAge;
        this.growthChance = growthChance;
    }

    @Override
    public void randomTick(RandomTick randomTick) {
        //if (context.random().nextInt(growthChance) != 0) { return; }
        context.instance().setBlock(randomTick.position(), Block.fromStateId(randomTick.stage() + 1));
    }
}
