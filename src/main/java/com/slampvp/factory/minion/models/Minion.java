package com.slampvp.factory.minion.models;

import net.kyori.adventure.text.TextComponent;
import net.minestom.server.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;

public sealed interface Minion permits MinionImpl {
    static @NotNull Optional<Minion> byId(String id) {
        return Minions.getById(id);
    }

    static @NotNull Set<Minion> all() {
        return Minions.all();
    }

    @NotNull
    String id();

    @NotNull
    TextComponent name();

    @NotNull
    TextComponent lore();

    @NotNull
    String texture();

    @NotNull
    ItemStack getItem();
}
