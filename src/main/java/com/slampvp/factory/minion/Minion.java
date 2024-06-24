package com.slampvp.factory.minion;

import net.kyori.adventure.text.TextComponent;
import net.minestom.server.instance.block.Block;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;

public sealed interface Minion extends Minions permits MinionImpl {
    static @NotNull Optional<Minion> byId(String id) {
        return MinionImpl.getById(id);
    }

    static @NotNull Set<Minion> getAll() {
        return MinionImpl.getAll();
    }

    @NotNull
    String getId();

    @NotNull
    TextComponent getName();

    @NotNull
    TextComponent getLore();

    @NotNull
    Material getMaterial();

    @NotNull
    ItemStack getItem();
}
