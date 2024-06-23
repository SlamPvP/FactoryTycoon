package com.slampvp.factory.command.plot.sub;

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

@com.slampvp.factory.command.Command(description = "Teleport to a warp in your plot.", usage = "/plot warp <name>", minimumRank = Rank.DEFAULT, playerOnly = true)
public class WarpCommand extends FactoryCommand {
    public WarpCommand() {
        super("warp");
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
            String name = context.get(nameArgument);
            Optional<Pos> warp = plot.getWarp(name);

            if (warp.isEmpty()) {
                sender.sendMessage(Locale.Plot.INVALID_WARP);
                return;
            }

            player.teleport(warp.get());
            sender.sendMessage(Locale.Plot.WARPED
                    .replaceText(TextReplacementConfig.builder().match("<warp>").replacement(name).build())
            );
        }, nameArgument);
    }
}
