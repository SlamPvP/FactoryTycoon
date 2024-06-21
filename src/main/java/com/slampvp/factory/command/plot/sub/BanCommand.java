package com.slampvp.factory.command.plot.sub;

import com.slampvp.factory.command.Command;
import com.slampvp.factory.command.FactoryCommand;
import com.slampvp.factory.common.Constants;
import com.slampvp.factory.common.Locale;
import com.slampvp.factory.player.Rank;
import com.slampvp.factory.plot.Plot;
import com.slampvp.factory.plot.PlotManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.minecraft.ArgumentEntity;
import net.minestom.server.entity.Player;

import java.util.Optional;

@Command(description = "Ban a user from your plot.", usage = "/plot ban <player>", minimumRank = Rank.DEFAULT, playerOnly = true)
public class BanCommand extends FactoryCommand {
    public BanCommand() {
        super("ban");
    }

    @Override
    public void init() {
        ArgumentEntity playerArgument = ArgumentType.Entity("player").singleEntity(true).onlyPlayers(true);

        addSyntax((sender, context) -> {
            Player player = (Player) sender;
            Optional<Plot> optionalPlot = PlotManager.getInstance().getPlot(player.getPosition());

            if (optionalPlot.isEmpty()) {
                sender.sendMessage(Locale.Plot.NOT_IN_PLOT);
                return;
            }

            Plot plot = optionalPlot.get();

            if (!plot.getOwner().equals(player.getUuid())) {
                sender.sendMessage(Locale.Plot.NO_PLOT_OWNER);
                return;
            }

            Player target = context.get(playerArgument).findFirstPlayer(sender);

            if (target == null) {
                sender.sendMessage(Locale.Command.INVALID_PLAYER);
                return;
            }

            if (target.getUuid().equals(player.getUuid())) {
                sender.sendMessage(Locale.Plot.BAN_SELF);
                return;
            }

            if (plot.isBanned(target)) {
                sender.sendMessage(Locale.Plot.ALREADY_BANNED);
                return;
            }

            plot.banPlayer(target);

            if (plot.contains(target.getPosition())) {
                target.teleport(Constants.SPAWN);
            }

            sender.sendMessage(Locale.Plot.BANNED
                    .replaceText(TextReplacementConfig.builder().match("<player>").replacement(target.getName()).build())
            );
        }, playerArgument);
    }
}
