package com.slampvp.factory.minion;

import net.kyori.adventure.text.TextComponent;
import net.minestom.server.instance.block.Block;

public sealed interface Minion extends Minions permits MinionImpl {
    TextComponent getName();

    TextComponent getLore();

    Block getBlock();
}
