package com.slampvp.factory.command.plot.sub;

import com.slampvp.factory.command.Command;
import com.slampvp.factory.command.FactoryCommand;
import com.slampvp.factory.common.Locale;
import com.slampvp.factory.player.Rank;
import com.slampvp.factory.plot.PlotManager;
import com.slampvp.factory.plot.models.Plot;
import net.kyori.adventure.text.TextReplacementConfig;
import net.minestom.server.command.builder.arguments.ArgumentString;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;

import java.util.Optional;

@Command(description = "Remove a warp from your plot.", usage = "/plot removewarp <name>", minimumRank = Rank.DEFAULT, playerOnly = true)
public class RemoveWarpCommand extends FactoryCommand {
    public RemoveWarpCommand() {
        super("removewarp");
    }

    @Override
    public void init() {
        ArgumentString nameArgument = ArgumentType.String("name");

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

            String name = context.get(nameArgument);
            Optional<Pos> warp = plot.getWarp(name);

            if (warp.isEmpty()) {
                sender.sendMessage(Locale.Plot.INVALID_WARP);
                return;
            }

            plot.removeWarp(name);
            sender.sendMessage(Locale.Plot.REMOVED_WARP
                    .replaceText(TextReplacementConfig.builder().match("<warp>").replacement(name).build())
            );
        }, nameArgument);
    }
}
