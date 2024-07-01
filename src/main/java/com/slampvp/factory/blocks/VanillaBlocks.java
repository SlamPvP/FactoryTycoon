package com.slampvp.factory.blocks;

import com.slampvp.factory.blocks.behaviours.randomtick.RandomTickable;
import com.slampvp.factory.blocks.behaviours.randomtick.RandomTickableCactus;
import com.slampvp.factory.blocks.behaviours.randomtick.RandomTickableCrop;
import com.slampvp.factory.randomticksystem.RandomTickManager;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public enum VanillaBlocks {

    WHEAT(Block.WHEAT, (context) -> new RandomTickableCrop(context, 7, 2)),
    CARROT(Block.CARROTS, (context) -> new RandomTickableCrop(context, 7, 2)),
    POTATO(Block.POTATOES, (context) -> new RandomTickableCrop(context, 7, 2)),
    BEETROOT(Block.BEETROOTS, (context) -> new RandomTickableCrop(context, 3, 2)),
    CACTUS(Block.CACTUS, (context) -> new RandomTickableCactus(context, 3, 1)),

    ;

    private final @NotNull short stateId;
    private final @NotNull Context2Handler context2handler;
    private final Random random = new Random();

    VanillaBlocks(@NotNull Block block, @NotNull Context2Handler context2handler) {
        this.stateId = (short) block.stateId();
        this.context2handler = context -> {
            if (context.stateId() != block.stateId()) {
                throw new IllegalStateException("Block registry mismatch. Registered block: " + block.stateId() +
                        " !=  Given block:" + context.stateId());
            }
            return context2handler.apply(context);
        };
    }

    public interface Context2Handler {
        @NotNull BlockBehaviour apply(@NotNull BlockContext context);
    }

    /**
     * Used to provide context for creating block handlers
     */
    public interface BlockContext {
        @NotNull short stateId();
        @NotNull Instance instance();
        @NotNull Random random();
    }

    /**
     * Register all tickable blocks.
     */
    public static void registerAll(Instance instance) {

        for (VanillaBlocks block : values()) {
            BlockContext context = new BlockContext() {
                @Override
                public short stateId() {
                    return block.stateId;
                }

                @Override
                public @NotNull Instance instance() {
                    return instance;
                }

                @Override
                public @NotNull Random random() {
                    return block.random;
                }
            };

            BlockBehaviour behaviour = block.context2handler.apply(context);

            if (behaviour instanceof RandomTickableCrop crop) {
                for (int i = 0; i < crop.maxAge; i++) {
                    RandomTickManager.registerRandomTickable((short) (block.stateId + i), crop);
                }
            }

            else if (behaviour instanceof RandomTickable randomTickable) {
                RandomTickManager.registerRandomTickable(block.stateId, randomTickable);
            }
        }
    }
}
