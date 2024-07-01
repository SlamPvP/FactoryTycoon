package com.slampvp.factory.command.generic.gamemode;

import com.slampvp.factory.command.Command;
import com.slampvp.factory.command.FactoryCommand;
import com.slampvp.factory.common.menu.TestMenu;
import com.slampvp.factory.player.Rank;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;

@Command(description = "Open menu.", usage = "/menu", minimumRank = Rank.ADMIN, playerOnly = true)
public class Menu extends FactoryCommand {
    public Menu() {
        super("menu");
    }

    @Override
    public void init() {
        addSyntax((sender, context) -> {
            Player player = (Player) sender;

            new TestMenu().open(player);
        });
    }
}
