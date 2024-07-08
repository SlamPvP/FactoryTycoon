package com.slampvp.factory.blocks.behaviours.randomtick.bamboo;

import com.slampvp.factory.blocks.VanillaBlocks;
import com.slampvp.factory.blocks.behaviours.randomtick.RandomTickable;
import com.slampvp.factory.randomticksystem.RandomTick;
import org.jetbrains.annotations.NotNull;

public class RandomTickableBamboo extends RandomTickable {

    private final int minHeight;
    private final int maxHeight;
    private final int growthChance;

    public RandomTickableBamboo(VanillaBlocks.@NotNull BlockContext context, int minHeight, int maxHeight, int growthChance) {
        super(context);

        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.growthChance = growthChance;
    }

    @Override
    public void randomTick(RandomTick randomTick) {





    }


}
