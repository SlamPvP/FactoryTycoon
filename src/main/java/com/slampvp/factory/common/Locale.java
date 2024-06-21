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
        public static final TextComponent INVALID_PLAYER = Component.text("Cannot find specified player!").color(NamedTextColor.RED);
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
        public static final TextComponent NO_PLOT = PREFIX.append(
                Component.text("You don't have a plot!").color(NamedTextColor.RED)
        );
        public static final TextComponent NO_PLOT_OWNER = PREFIX.append(
                Component.text("You need to be the owner of this plot!").color(NamedTextColor.RED)
        );
        public static final TextComponent INVALID_WARP = PREFIX.append(
                Component.text("This warp does not exist!").color(NamedTextColor.RED)
        );
        public static final TextComponent ALREADY_BANNED = PREFIX.append(
                Component.text("This player is already banned from your plot!").color(NamedTextColor.RED)
        );
        public static final TextComponent BAN_SELF = PREFIX.append(
                Component.text("You cannot ban yourself!").color(NamedTextColor.RED)
        );
        public static final TextComponent KICK_SELF = PREFIX.append(
                Component.text("You cannot kick yourself!").color(NamedTextColor.RED)
        );
        public static final TextComponent KICK_NOT_IN_PLOT = PREFIX.append(
                Component.text("The player you want to kick needs to be in your plot!").color(NamedTextColor.RED)
        );

        public static final TextComponent CLAIMED = PREFIX.append(
                Component.text("You've successfully claimed this plot!").color(NamedTextColor.GREEN)
        );
        public static final TextComponent UNCLAIMED = PREFIX.append(
                Component.text("You've successfully unclaimed this plot!").color(NamedTextColor.GREEN)
        );
        public static final TextComponent TELEPORTED = PREFIX.append(
                Component.text("You've successfully teleported to your plot!").color(NamedTextColor.GREEN)
        );
        public static final TextComponent WARPED = PREFIX.append(
                Component.text("You've successfully warped to warp <warp>!").color(NamedTextColor.GREEN)
        );
        public static final TextComponent SET_SPAWN = PREFIX.append(
                Component.text("You've successfully set the spawn of this plot to your location!").color(NamedTextColor.GREEN)
        );
        public static final TextComponent ADDED_WARP = PREFIX.append(
                Component.text("You've successfully added warp <warp> to your plot!").color(NamedTextColor.GREEN)
        );
        public static final TextComponent REMOVED_WARP = PREFIX.append(
                Component.text("You've successfully removed warp <warp> from your plot!").color(NamedTextColor.GREEN)
        );
        public static final TextComponent CLEARED = PREFIX.append(
                Component.text("You've successfully cleared your plot!").color(NamedTextColor.GREEN)
        );
        public static final TextComponent BANNED = PREFIX.append(
                Component.text("You've successfully banned <player> from your plot!").color(NamedTextColor.GREEN)
        );
        public static final TextComponent KICKED = PREFIX.append(
                Component.text("You've successfully kicked <player> from your plot!").color(NamedTextColor.GREEN)
        );
    }
}
