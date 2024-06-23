package com.slampvp.factory.command.plot.sub;

import com.slampvp.factory.command.Command;
import com.slampvp.factory.command.FactoryCommand;
import com.slampvp.factory.common.Locale;
import com.slampvp.factory.player.Rank;
import com.slampvp.factory.plot.PlotManager;
import com.slampvp.factory.plot.models.Plot;
import net.kyori.adventure.text.TextReplacementConfig;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.minecraft.ArgumentEntity;
import net.minestom.server.entity.Player;

import java.util.Optional;

@Command(
        description = "Remove a member or a trusted user from your plot.",
        usage = "/plot remove <player>",
        minimumRank = Rank.DEFAULT,
        playerOnly = true
)
public class RemoveCommand extends FactoryCommand {
    public RemoveCommand() {
        super("remove");
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
                sender.sendMessage(Locale.Plot.NOT_PLOT_OWNER);
                return;
            }

            Player target = context.get(playerArgument).findFirstPlayer(sender);

            if (target == null) {
                sender.sendMessage(Locale.Command.INVALID_PLAYER);
                return;
            }

            if (!plot.isAdded(player)) {
                sender.sendMessage(Locale.Plot.REMOVE_INVALID);
                return;
            }

            plot.removeMember(target);

            sender.sendMessage(Locale.Plot.REMOVED
                    .replaceText(TextReplacementConfig.builder().match("<player>").replacement(target.getName()).build())
            );
            target.sendMessage(Locale.Plot.REMOVED_TARGET
                    .replaceText(TextReplacementConfig.builder().match("<player>").replacement(player.getName()).build())
            );
        }, playerArgument);
    }
}
