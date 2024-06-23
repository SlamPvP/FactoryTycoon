package com.slampvp.factory.minion;

import net.kyori.adventure.text.Component;
import net.minestom.server.instance.block.Block;

interface Minions {
    Minion FARMER_MINION =
            new MinionImpl("farmer", Component.text("Farmer"), Component.text("lore"), Block.COMMAND_BLOCK);
}
