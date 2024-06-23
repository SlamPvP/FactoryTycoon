package com.slampvp.factory.command.plot.sub;

import com.slampvp.factory.command.Command;
import com.slampvp.factory.command.FactoryCommand;
import com.slampvp.factory.common.Locale;
import com.slampvp.factory.player.Rank;
import com.slampvp.factory.plot.*;
import com.slampvp.factory.plot.models.Plot;
import net.minestom.server.entity.Player;

import java.util.Optional;

@Command(description = "Clear a plot.", usage = "/plot clear", minimumRank = Rank.DEFAULT, playerOnly = true)
public class ClearCommand extends FactoryCommand {
    public ClearCommand() {
        super("clear");
    }

    @Override
    public void init() {
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

            PlotGenerator.clearPlot(player.getPosition(), player.getInstance());

            sender.sendMessage(Locale.Plot.CLEARED);
        });
    }
}
