package com.slampvp.factory.command.minion.sub;

import com.slampvp.factory.command.Command;
import com.slampvp.factory.command.FactoryCommand;
import com.slampvp.factory.command.minion.arguments.ArgumentMinion;
import com.slampvp.factory.common.Locale;
import com.slampvp.factory.minion.Minion;
import com.slampvp.factory.player.Rank;
import com.slampvp.factory.plot.PlotManager;
import com.slampvp.factory.plot.models.Plot;
import com.slampvp.factory.plot.models.PlotFlag;
import net.kyori.adventure.text.TextReplacementConfig;
import net.minestom.server.command.builder.arguments.ArgumentBoolean;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.minecraft.ArgumentBlockState;
import net.minestom.server.command.builder.arguments.minecraft.ArgumentEntity;
import net.minestom.server.entity.Player;

import java.util.Optional;

@Command(
        description = "Give a minion to a player.",
        usage = "/minion give <player> <minion>",
        minimumRank = Rank.ADMIN,
        playerOnly = false
)
public class GiveCommand extends FactoryCommand {
    public GiveCommand() {
        super("give");
    }

    @Override
    public void init() {
        ArgumentEntity argumentPlayer = ArgumentType.Entity("player").singleEntity(true).onlyPlayers(true);
        ArgumentMinion argumentMinion = new ArgumentMinion("minion");

        addSyntax((sender, context) -> {
            Player player = (Player) sender;
            Player target = context.get(argumentPlayer).findFirstPlayer(sender);
            Minion minion = context.get(argumentMinion);

        }, argumentPlayer, argumentMinion);
    }
}
