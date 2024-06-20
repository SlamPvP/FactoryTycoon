package com.slampvp.factory.command.generic;

import com.slampvp.factory.command.Command;
import com.slampvp.factory.command.FactoryCommand;
import com.slampvp.factory.player.Rank;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;

@Command(description = "Change the gamemode of a player.",
        usage = "/gamemode <gamemode> [player]",
        minimumRank = Rank.DEFAULT,
        playerOnly = false)
public class GamemodeCommand extends FactoryCommand {
    public GamemodeCommand() {
        super("gamemode", "gm");
    }

    @Override
    public void init() {
        var gamemodeArgument = ArgumentType.Word("gamemode").from("creative", "survival", "adventure", "spectator");
        var playerArgument = ArgumentType.Entity("player").onlyPlayers(true);

        addSyntax((sender, context) -> {
            Player player = (Player) sender;
            player.sendMessage("§7[§bFT§7] §8» §7Gamemode changed to §b" + context.get(gamemodeArgument));
            player.setGameMode(GameMode.valueOf(context.get(gamemodeArgument).toUpperCase()));
        }, gamemodeArgument);

        addSyntax((sender, context) -> {
            Player player = (Player) sender;
            Player target = context.get(playerArgument).findFirstPlayer(sender);
            assert target != null;

            player.sendMessage("§7[§bFT§7] §8» §7Gamemode of §b" + target.getUsername() + "§7 changed to §b" + context.get(gamemodeArgument));
            target.sendMessage("§7[§bFT§7] §8» §7Gamemode changed to §b" + context.get(gamemodeArgument));
            target.setGameMode(GameMode.valueOf(context.get(gamemodeArgument).toUpperCase()));
        }, gamemodeArgument, playerArgument);
    }
}
