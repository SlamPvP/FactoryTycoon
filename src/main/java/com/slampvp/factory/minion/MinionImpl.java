package com.slampvp.factory.minion;

import net.kyori.adventure.text.TextComponent;
import net.minestom.server.instance.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public final class MinionImpl implements Minion {
    private static final Set<Minion> MINIONS = new HashSet<>();

    static {
        MINIONS.add(FARMER_MINION);
    }

    private final String id;
    private final TextComponent name;
    private final TextComponent lore;
    private final Block block;

    public MinionImpl(@NotNull String id, @NotNull TextComponent name, @NotNull TextComponent lore, @NotNull Block block) {
        this.id = id;
        this.name = name;
        this.lore = lore;
        this.block = block;
    }

    static @NotNull Optional<Minion> getById(String id) {
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
    public @NotNull Block getBlock() {
        return this.block;
    }

    @Override
    public String toString() {
        return "MinionImpl{" +
                "id='" + id + '\'' +
                ", name=" + name +
                ", lore=" + lore +
                ", block=" + block +
                '}';
    }
}
