package com.slampvp.factory.blocks.behaviours.randomtick;

import com.slampvp.factory.blocks.BlockBehaviour;
import com.slampvp.factory.blocks.VanillaBlocks;
import com.slampvp.factory.randomticksystem.RandomTick;
import org.jetbrains.annotations.NotNull;

public abstract class RandomTickable extends BlockBehaviour {

    public RandomTickable(@NotNull VanillaBlocks.BlockContext context) {
        super(context);
    }

    public void randomTick(RandomTick randomTick) {}
}
