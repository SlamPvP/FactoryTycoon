package com.slampvp.factory.command.plot.sub;

import com.slampvp.factory.command.Command;
import com.slampvp.factory.command.FactoryCommand;
import com.slampvp.factory.common.Locale;
import com.slampvp.factory.player.Rank;
import com.slampvp.factory.plot.models.Plot;
import com.slampvp.factory.plot.PlotManager;
import net.minestom.server.entity.Player;

import java.util.Optional;

@Command(description = "Set the spawn of your plot to your current location.", usage = "/plot setspawn", minimumRank = Rank.DEFAULT, playerOnly = true)
public class SetSpawnCommand extends FactoryCommand {
    public SetSpawnCommand() {
        super("setspawn");
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

            plot.setSpawn(player.getPosition());
            sender.sendMessage(Locale.Plot.SET_SPAWN);
        });
    }
}
