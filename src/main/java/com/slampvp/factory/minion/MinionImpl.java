package com.slampvp.factory.minion;

import net.kyori.adventure.text.TextComponent;
import net.minestom.server.instance.block.Block;

public final class MinionImpl implements Minion {
    private final TextComponent name;
    private final TextComponent lore;
    private final Block block;

    public MinionImpl(TextComponent name, TextComponent lore, Block block) {
        this.name = name;
        this.lore = lore;
        this.block = block;
    }

    @Override
    public TextComponent getName() {
        return this.name;
    }

    @Override
    public TextComponent getLore() {
        return this.lore;
    }

    @Override
    public Block getBlock() {
        return this.block;
    }
}
