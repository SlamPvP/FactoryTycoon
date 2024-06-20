package com.slampvp.factory.command;

import com.slampvp.factory.common.Locale;
import net.kyori.adventure.text.TextReplacementConfig;
import net.minestom.server.command.ConsoleSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public abstract class FactoryCommand extends net.minestom.server.command.builder.Command {
    public FactoryCommand(@NotNull String name) {
        super(name);
        configure();
    }

    public FactoryCommand(@NotNull String... names) {
        super(names[0], Arrays.copyOfRange(names, 1, names.length));
        configure();
    }

    private void configure() {
        Command command = getClass().getAnnotation(Command.class);

        setDefaultExecutor((sender, context) -> {
            sender.sendMessage(Locale.Command.INVALID_COMMAND
                    .replaceText(TextReplacementConfig.builder().match("<usage>").replacement(command.usage()).build())
            );
        });

        setCondition((commandSender, string) -> {
            if (commandSender instanceof ConsoleSender) {
                return !command.playerOnly();
            }

            // TODO: implement permission checking when we implement ranks
            return true;
        });

        init();
    }

    public abstract void init();
}
