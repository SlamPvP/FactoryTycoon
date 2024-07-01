package com.slampvp.factory.minion.models;

import com.slampvp.factory.common.ItemStackUtil;
import net.kyori.adventure.text.TextComponent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.tag.Tag;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

record MinionImpl(String id, TextComponent name, TextComponent lore, String texture) implements Minion {
    MinionImpl(@NotNull String id, @NotNull TextComponent name, @NotNull TextComponent lore, @NotNull String texture) {
        this.id = id;
        this.name = name;
        this.lore = lore;
        this.texture = texture;
    }

    @Override
    public @NotNull ItemStack getItem() {
        return ItemStackUtil
                .texturedHead(this.texture)
                .customName(this.name)
                .lore(this.lore)
                .build()
                .withTag(Tag.String("minion"), id());
    }
}
