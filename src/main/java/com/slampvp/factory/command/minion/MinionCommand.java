package com.slampvp.factory.command.minion;

import com.slampvp.factory.command.Command;
import com.slampvp.factory.command.FactoryCommand;
import com.slampvp.factory.minion.models.Minion;
import com.slampvp.factory.player.Rank;

@Command(description = "Minion command.", usage = "/minion", minimumRank = Rank.ADMIN, playerOnly = false)
public class MinionCommand extends FactoryCommand {
    public MinionCommand() {
        super("minion");
    }

    @Override
    public void init() {
        addSyntax(((sender, context) -> {
            Minion.all().forEach(minion -> {
                sender.sendMessage(minion.toString());
            });
        }));
    }
}
