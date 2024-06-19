package com.slampvp.factory.common;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

public final class Locale {
    public static final class Command {
        public static final TextComponent INVALID_COMMAND = Component.text("Usage: <usage>").color(NamedTextColor.RED);
        public static final TextComponent INVALID_PERMISSIONS = Component.text("You don't have the permissions to execute this command!").color(NamedTextColor.RED);
    }
}
