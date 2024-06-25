package com.slampvp.factory.minion.models;

import com.slampvp.factory.common.ItemStackUtils;
import net.kyori.adventure.text.TextComponent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.tag.Tag;
import org.jetbrains.annotations.NotNull;

import java.util.*;

record MinionImpl(String id, TextComponent name, TextComponent lore, String texture) implements Minion {
    private static final Set<Minion> MINIONS = new HashSet<>();

    MinionImpl(@NotNull String id, @NotNull TextComponent name, @NotNull TextComponent lore, @NotNull String texture) {
        this.id = id;
        this.name = name;
        this.lore = lore;
        this.texture = texture;

        MINIONS.add(this);
    }

    static @NotNull Optional<Minion> getById(String id) {
        System.out.println(MINIONS);
        return MINIONS.stream().filter(minion -> minion.id().equals(id)).findFirst();
    }

    static @NotNull Set<Minion> getAll() {
        return Collections.unmodifiableSet(MINIONS);
    }

    @Override
    public @NotNull ItemStack getItem() {
        return ItemStackUtils
                .getTexturedHead(this.texture)
                .customName(this.name)
                .lore(this.lore)
                .build()
                .withTag(Tag.String("minion"), id());
    }
}
