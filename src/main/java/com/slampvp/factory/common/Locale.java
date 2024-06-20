package com.slampvp.factory.common;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

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

    /**
     * Plot-related messages.
     */
    public static final class Plot {
        public static final TextComponent PREFIX = Component.text()
                .append(Component.text("[").color(NamedTextColor.GREEN).decorate(TextDecoration.BOLD))
                .append(Component.text("Plot").color(NamedTextColor.YELLOW).decorate(TextDecoration.BOLD))
                .append(Component.text("] ").color(NamedTextColor.GREEN).decorate(TextDecoration.BOLD))
                .build();

        public static final TextComponent NOT_IN_PLOT = PREFIX.append(
                Component.text("You need to be in a plot to execute this command!").color(NamedTextColor.RED)
        );
        public static final TextComponent ALREADY_CLAIMED = PREFIX.append(
                Component.text("This plot is already claimed!").color(NamedTextColor.RED)
        );
        public static final TextComponent NOT_CLAIMED = PREFIX.append(
                Component.text("This plot is not claimed!").color(NamedTextColor.RED)
        );

        public static final TextComponent CLAIMED = PREFIX.append(
                Component.text("You've successfully claimed this plot!").color(NamedTextColor.GREEN)
        );
        public static final TextComponent UNCLAIMED = PREFIX.append(
                Component.text("You've successfully unclaimed this plot!").color(NamedTextColor.GREEN)
        );
    }
}
