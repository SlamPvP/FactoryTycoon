package com.slampvp.factory.command.generic.gamemode;

import com.slampvp.factory.command.Command;
import com.slampvp.factory.command.FactoryCommand;
import com.slampvp.factory.player.Rank;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;

@Command(description = "Set your game mode to spectator.", usage = "/gmsp", minimumRank = Rank.ADMIN, playerOnly = true)
public class GMSPCommand extends FactoryCommand {
    public GMSPCommand() {
        super("gmsp");
    }

    @Override
    public void init() {
        addSyntax((sender, context) -> {
            Player player = (Player) sender;
            player.setGameMode(GameMode.SPECTATOR);
        });
    }
}
