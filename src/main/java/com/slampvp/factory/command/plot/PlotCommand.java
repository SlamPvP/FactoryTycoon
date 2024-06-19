package com.slampvp.factory.command.plot;

import com.slampvp.factory.command.Command;
import com.slampvp.factory.command.FactoryCommand;
import com.slampvp.factory.command.plot.sub.ClaimCommand;
import com.slampvp.factory.player.Rank;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.number.ArgumentInteger;

@Command(description = "Plot command.", usage = "/plot", minimumRank = Rank.DEFAULT, playerOnly = true)
public class PlotCommand extends FactoryCommand {
    public PlotCommand() {
        super("plot");

        addSubcommand(new ClaimCommand());
    }

    @Override
    public void init() {
        ArgumentInteger numberArgument = ArgumentType.Integer("number");

        addSyntax((sender, context) -> {
            final int number = context.get(numberArgument);
            sender.sendMessage("You typed the number " + number);
        }, numberArgument);
    }
}
