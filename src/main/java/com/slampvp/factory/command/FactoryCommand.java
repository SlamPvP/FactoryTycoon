package com.slampvp.factory.command;


import com.slampvp.factory.common.Locale;
import net.kyori.adventure.text.TextReplacementConfig;
import net.minestom.server.command.ConsoleSender;
import org.jetbrains.annotations.NotNull;

public abstract class FactoryCommand extends net.minestom.server.command.builder.Command {
    public FactoryCommand(@NotNull String name) {
        super(name);

        Command annotation = getClass().getAnnotation(Command.class);

        setDefaultExecutor((sender, context) -> {
            sender.sendMessage(Locale.Command.INVALID_COMMAND
                    .replaceText(TextReplacementConfig.builder().match("<usage>").replacement(annotation.usage()).build())
            );
        });

        setCondition((commandSender, string) -> {
            if (commandSender instanceof ConsoleSender) {
                return !annotation.playerOnly();
            }

            // TODO: implement permission checking when we implement ranks
            return true;
        });

        init();
    }

    public abstract void init();
}
