package com.slampvp.factory.blocks.behaviours.randomtick;

import com.slampvp.factory.blocks.VanillaBlocks;
import net.minestom.server.coordinate.Point;
import org.jetbrains.annotations.NotNull;

public class RandomTickableCrop extends RandomTickable {


    protected final int maxAge;
    protected final int growthChance;

    public RandomTickableCrop(@NotNull VanillaBlocks.BlockContext context, int maxAge, int growthChance) {
        super(context);

        this.maxAge = maxAge;
        this.growthChance = growthChance;
    }

    @Override
    public void randomTick(Point pos) {
        // TODO: Implement random tick for crops





    }
}
