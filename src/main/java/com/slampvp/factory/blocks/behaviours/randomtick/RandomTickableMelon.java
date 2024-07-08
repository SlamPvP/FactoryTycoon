package com.slampvp.factory.blocks.behaviours.randomtick;

import com.slampvp.factory.blocks.VanillaBlocks;
import com.slampvp.factory.randomticksystem.RandomTick;
import net.minestom.server.coordinate.Point;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.BlockFace;
import net.minestom.server.utils.Direction;
import org.jetbrains.annotations.NotNull;

public class RandomTickableMelon extends RandomTickable {

    private final int growthChance;
    private static final BlockFace[] faces = new BlockFace[]{BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};

    public RandomTickableMelon(VanillaBlocks.@NotNull BlockContext context, int growthChance) {
        super(context);
        this.growthChance = growthChance;
    }

    @Override
    public void randomTick(RandomTick randomTick) {
        Point pos = randomTick.position();
        Instance instance = context.instance();

        Direction growDirection = faces[context.random().nextInt(4)].toDirection();
        Point growPoint = pos.add(growDirection.normalX(), 0, growDirection.normalZ());

        if (instance.getBlock(growPoint) == Block.AIR) {
            // Check for melon or pumpkin stem
            if (context.block() == Block.MELON_STEM) {
                instance.setBlock(growPoint, Block.MELON);
                instance.setBlock(pos, Block.ATTACHED_MELON_STEM.withProperty("facing", growDirection.name().toLowerCase()));
            } else {
                instance.setBlock(growPoint, Block.PUMPKIN);
                instance.setBlock(pos, Block.ATTACHED_PUMPKIN_STEM.withProperty("facing", growDirection.name().toLowerCase()));
            }
        }
    }
}
