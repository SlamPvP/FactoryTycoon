package com.slampvp.factory.minion.models;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public final class Minions {
    private static final Set<Minion> MINIONS = new HashSet<>();

    public static final Minion FARMER = new MinionImpl(
            "farmer",
            Component.text("Farmer").color(NamedTextColor.GREEN).decorate(TextDecoration.BOLD),
            Component.text("lore"),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTUxZjI0M2NkMTE0YmQwZjIyYzljYjVmYjA3NmFkOWY1ZjYyMDRhM2U1YTFhNDcxODNiMzNmZTkyOGUxOWExYyJ9fX0="
    );

    static {
        MINIONS.add(FARMER);
    }

    static @NotNull Optional<Minion> getById(String id) {
        return MINIONS.stream().filter(minion -> minion.id().equals(id)).findFirst();
    }

    static @NotNull Set<Minion> all() {
        return Collections.unmodifiableSet(MINIONS);
    }
}
