package com.slampvp.factory.common;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

/**
 * This class contains constants related to localization and messages.
 */
public interface Locale {
    /**
     * Command-related messages.
     */
    interface Command {
        TextComponent INVALID_COMMAND = Component.text("Usage: <usage>").color(NamedTextColor.RED);
        TextComponent INVALID_PERMISSIONS = Component.text("You don't have the permissions to execute this command!").color(NamedTextColor.RED);
        TextComponent INVALID_PLAYER = Component.text("Cannot find specified player!").color(NamedTextColor.RED);
    }

    /**
     * Plot-related messages.
     */
    interface Plot {
        TextComponent PREFIX = Component.text()
                .append(Component.text("[").color(NamedTextColor.GREEN).decorate(TextDecoration.BOLD))
                .append(Component.text("Plot").color(NamedTextColor.YELLOW).decorate(TextDecoration.BOLD))
                .append(Component.text("] ").color(NamedTextColor.GREEN).decorate(TextDecoration.BOLD))
                .build();

        TextComponent NOT_IN_PLOT = PREFIX.append(
                Component.text("You need to be in a plot to execute this command!").color(NamedTextColor.RED)
        );
        TextComponent ALREADY_CLAIMED = PREFIX.append(
                Component.text("This plot is already claimed!").color(NamedTextColor.RED)
        );
        TextComponent NOT_CLAIMED = PREFIX.append(
                Component.text("This plot is not claimed!").color(NamedTextColor.RED)
        );
        TextComponent NO_PLOT = PREFIX.append(
                Component.text("You don't have a plot!").color(NamedTextColor.RED)
        );
        TextComponent NOT_PLOT_OWNER = PREFIX.append(
                Component.text("You need to be the owner of this plot!").color(NamedTextColor.RED)
        );
        TextComponent INVALID_WARP = PREFIX.append(
                Component.text("This warp does not exist!").color(NamedTextColor.RED)
        );
        TextComponent ALREADY_BANNED = PREFIX.append(
                Component.text("This player is already banned from your plot!").color(NamedTextColor.RED)
        );
        TextComponent BAN_SELF = PREFIX.append(
                Component.text("You cannot ban yourself!").color(NamedTextColor.RED)
        );
        TextComponent KICK_SELF = PREFIX.append(
                Component.text("You cannot kick yourself!").color(NamedTextColor.RED)
        );
        TextComponent KICK_NOT_IN_PLOT = PREFIX.append(
                Component.text("The player you want to kick needs to be in your plot!").color(NamedTextColor.RED)
        );
        TextComponent ADD_SELF = PREFIX.append(
                Component.text("You cannot add yourself as a member to your plot!").color(NamedTextColor.RED)
        );
        TextComponent REMOVE_INVALID = PREFIX.append(
                Component.text("This player is not a member of your plot!").color(NamedTextColor.RED)
        );
        TextComponent ALREADY_ADDED = PREFIX.append(
                Component.text("This player is already a member of your plot!").color(NamedTextColor.RED)
        );
        TextComponent ALREADY_TRUSTED = PREFIX.append(
                Component.text("This player is already a trusted player of your plot!").color(NamedTextColor.RED)
        );
        TextComponent NO_MERGE_CANDIDATE = PREFIX.append(
                Component.text("Cannot find a merge candidate!").color(NamedTextColor.RED)
        );

        TextComponent CLAIMED = PREFIX.append(
                Component.text("You've successfully claimed this plot!").color(NamedTextColor.GREEN)
        );
        TextComponent UNCLAIMED = PREFIX.append(
                Component.text("You've successfully unclaimed this plot!").color(NamedTextColor.GREEN)
        );
        TextComponent TELEPORTED = PREFIX.append(
                Component.text("You've successfully teleported to your plot!").color(NamedTextColor.GREEN)
        );
        TextComponent WARPED = PREFIX.append(
                Component.text("You've successfully warped to warp <warp>!").color(NamedTextColor.GREEN)
        );
        TextComponent SET_SPAWN = PREFIX.append(
                Component.text("You've successfully set the spawn of this plot to your location!").color(NamedTextColor.GREEN)
        );
        TextComponent ADDED_WARP = PREFIX.append(
                Component.text("You've successfully added warp <warp> to your plot!").color(NamedTextColor.GREEN)
        );
        TextComponent REMOVED_WARP = PREFIX.append(
                Component.text("You've successfully removed warp <warp> from your plot!").color(NamedTextColor.GREEN)
        );
        TextComponent CLEARED = PREFIX.append(
                Component.text("You've successfully cleared your plot!").color(NamedTextColor.GREEN)
        );
        TextComponent BANNED = PREFIX.append(
                Component.text("You've successfully banned <player> from your plot!").color(NamedTextColor.GREEN)
        );
        TextComponent KICKED = PREFIX.append(
                Component.text("You've successfully kicked <player> from your plot!").color(NamedTextColor.GREEN)
        );
        TextComponent ADDED = PREFIX.append(
                Component.text("You've successfully added <player> as a member of your plot!").color(NamedTextColor.GREEN)
        );
        TextComponent ADDED_TARGET = PREFIX.append(
                Component.text("You've been added as a member of <player>'s plot!").color(NamedTextColor.GREEN)
        );
        TextComponent TRUSTED = PREFIX.append(
                Component.text("You've successfully trusted <player>!").color(NamedTextColor.GREEN)
        );
        TextComponent TRUSTED_TARGET = PREFIX.append(
                Component.text("You've been added as a trusted player of <player>'s plot!").color(NamedTextColor.GREEN)
        );
        TextComponent REMOVED = PREFIX.append(
                Component.text("You've successfully removed <player> as a member of your plot!").color(NamedTextColor.GREEN)
        );
        TextComponent REMOVED_TARGET = PREFIX.append(
                Component.text("You've been removed from <player>'s plot!").color(NamedTextColor.GREEN)
        );
        TextComponent MERGED = PREFIX.append(
                Component.text("You've successfully merged your plots!").color(NamedTextColor.GREEN)
        );
    }

    /**
     * Minion-related messages.
     */
    interface Minion {
        TextComponent PREFIX = Component.text()
                .append(Component.text("[").color(NamedTextColor.RED).decorate(TextDecoration.BOLD))
                .append(Component.text("Minion").color(NamedTextColor.BLUE).decorate(TextDecoration.BOLD))
                .append(Component.text("] ").color(NamedTextColor.RED).decorate(TextDecoration.BOLD))
                .build();

        TextComponent INVALID_ID = PREFIX.append(
                Component.text("Cannot find a minion with specified id!").color(NamedTextColor.RED)
        );
    }
}
