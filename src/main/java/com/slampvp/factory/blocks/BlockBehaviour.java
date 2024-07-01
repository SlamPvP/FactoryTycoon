package com.slampvp.factory.blocks;

import org.jetbrains.annotations.NotNull;

public abstract class BlockBehaviour {

    protected final @NotNull VanillaBlocks.BlockContext context;
    protected final int baseBlock;

    protected BlockBehaviour(@NotNull VanillaBlocks.BlockContext context) {
        this.context = context;
        this.baseBlock = context.stateId();
    }
}
