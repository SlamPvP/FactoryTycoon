package com.slampvp.factory.minion;

import net.kyori.adventure.text.Component;
import net.minestom.server.instance.block.Block;
import net.minestom.server.item.Material;

interface Minions {
    Minion FARMER_MINION =
            new MinionImpl("farmer", Component.text("Farmer"), Component.text("lore"), Material.GRASS_BLOCK);
}
