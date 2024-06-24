package com.slampvp.factory.minion.models;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

interface Minions {
    Minion FARMER = new MinionImpl(
            "farmer",
            Component.text("Farmer").color(NamedTextColor.GREEN).decorate(TextDecoration.BOLD),
            Component.text("lore"),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTUxZjI0M2NkMTE0YmQwZjIyYzljYjVmYjA3NmFkOWY1ZjYyMDRhM2U1YTFhNDcxODNiMzNmZTkyOGUxOWExYyJ9fX0="
    );
}
