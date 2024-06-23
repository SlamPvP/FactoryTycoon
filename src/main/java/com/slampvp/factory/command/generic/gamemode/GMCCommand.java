package com.slampvp.factory.command.generic.gamemode;

import com.slampvp.factory.command.Command;
import com.slampvp.factory.command.FactoryCommand;
import com.slampvp.factory.player.Rank;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;

@Command(description = "Set your game mode to creative.", usage = "/gmc", minimumRank = Rank.ADMIN, playerOnly = true)
public class GMCCommand extends FactoryCommand {
    public GMCCommand() {
        super("gmc");
    }

    @Override
    public void init() {
        addSyntax((sender, context) -> {
            Player player = (Player) sender;
            player.setGameMode(GameMode.CREATIVE);
        });
    }
}
