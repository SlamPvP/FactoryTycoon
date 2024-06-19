package com.slampvp.factory.command.generic;

import com.slampvp.factory.command.Command;
import com.slampvp.factory.command.FactoryCommand;
import com.slampvp.factory.player.Rank;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.number.ArgumentInteger;

@Command(description = "Test", usage = "/test <number>", minimumRank = Rank.DEFAULT, playerOnly = false)
public class TestCommand extends FactoryCommand {
    public TestCommand() {
        super("test");
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
