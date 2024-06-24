package com.slampvp.factory.minion.models;

import com.slampvp.factory.common.SkinnableHead;
import net.kyori.adventure.text.TextComponent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.tag.Tag;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

final class MinionImpl implements Minion {
    private static final Set<Minion> MINIONS = new HashSet<>();

    private final String id;
    private final TextComponent name;
    private final TextComponent lore;
    private final String texture;

    public MinionImpl(@NotNull String id, @NotNull TextComponent name, @NotNull TextComponent lore, @NotNull String texture) {
        this.id = id;
        this.name = name;
        this.lore = lore;
        this.texture = texture;

        MINIONS.add(this);
    }

    static @NotNull Optional<Minion> getById(String id) {
        System.out.println(MINIONS);
        return MINIONS.stream().filter(minion -> minion.getId().equals(id)).findFirst();
    }

    static @NotNull Set<Minion> getAll() {
        return Collections.unmodifiableSet(MINIONS);
    }

    @Override
    public @NotNull String getId() {
        return this.id;
    }

    @Override
    public @NotNull TextComponent getName() {
        return this.name;
    }

    @Override
    public @NotNull TextComponent getLore() {
        return this.lore;
    }

    @Override
    public @NotNull String getTexture() {
        return this.texture;
    }

    @Override
    public @NotNull ItemStack getItem() {
        return new SkinnableHead(this.texture)
                .builder()
                .customName(this.name)
                .lore(this.lore)
                .build()
                .withTag(Tag.String("minion"), getId());
    }

    @Override
    public String toString() {
        return "MinionImpl{" +
                "id='" + id + '\'' +
                ", name=" + name.content() +
                ", lore=" + lore.content() +
                '}';
    }
}
