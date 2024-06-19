package com.slampvp.factory.common;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

/**
 * This class contains constants related to localization and messages.
 */
public final class Locale {
    /**
     * Command-related messages.
     */
    public static final class Command {
        public static final TextComponent INVALID_COMMAND = Component.text("Usage: <usage>").color(NamedTextColor.RED);
        public static final TextComponent INVALID_PERMISSIONS = Component.text("You don't have the permissions to execute this command!").color(NamedTextColor.RED);
    }
}
