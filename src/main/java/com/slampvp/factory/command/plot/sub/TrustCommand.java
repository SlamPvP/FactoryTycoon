package com.slampvp.factory.command.plot.sub;

import com.slampvp.factory.command.Command;
import com.slampvp.factory.command.FactoryCommand;
import com.slampvp.factory.common.Locale;
import com.slampvp.factory.player.Rank;
import com.slampvp.factory.plot.Plot;
import com.slampvp.factory.plot.PlotFlag;
import com.slampvp.factory.plot.PlotManager;
import net.kyori.adventure.text.TextReplacementConfig;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.minecraft.ArgumentEntity;
import net.minestom.server.entity.Player;

import java.util.Optional;

@Command(
        description = "Add a trusted player to your plot.",
        usage = "/plot trust <player>",
        minimumRank = Rank.DEFAULT,
        playerOnly = true
)
public class TrustCommand extends FactoryCommand {
    public TrustCommand() {
        super("trust");
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

            if (target.getUuid().equals(player.getUuid())) {
                sender.sendMessage(Locale.Plot.ADD_SELF);
                return;
            }

            if (plot.isTrusted(player)) {
                sender.sendMessage(Locale.Plot.ALREADY_TRUSTED);
                return;
            }

            plot.addMember(target, PlotFlag.Target.TRUSTED);

            sender.sendMessage(Locale.Plot.TRUSTED
                    .replaceText(TextReplacementConfig.builder().match("<player>").replacement(target.getName()).build())
            );
            target.sendMessage(Locale.Plot.TRUSTED_TARGET
                    .replaceText(TextReplacementConfig.builder().match("<player>").replacement(player.getName()).build())
            );
        }, playerArgument);
    }
}
