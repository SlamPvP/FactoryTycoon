package com.slampvp.factory.command.plot.sub;

import com.slampvp.factory.command.Command;
import com.slampvp.factory.command.FactoryCommand;
import com.slampvp.factory.common.Locale;
import com.slampvp.factory.player.Rank;
import com.slampvp.factory.plot.models.Plot;
import com.slampvp.factory.plot.PlotManager;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;

import java.util.List;
import java.util.Optional;

@Command(description = "Teleport to your plot.", usage = "/plot tp", minimumRank = Rank.DEFAULT, playerOnly = true)
public class TpCommand extends FactoryCommand {
    public TpCommand() {
        super("tp", "spawn");
    }

    @Override
    public void init() {
        addSyntax((sender, context) -> {
            Player player = (Player) sender;

            Optional<Plot> optionalPlot = PlotManager.getInstance().getPlot(player.getPosition());

            Plot plot;

            if (optionalPlot.isPresent()) {
                plot = optionalPlot.get();
            } else {
                List<Plot> plots = PlotManager.getInstance().getPlots(player);

                if (plots.isEmpty()) {
                    sender.sendMessage(Locale.Plot.NO_PLOT);
                    return;
                }

                plot = plots.getFirst();
            }

            Pos spawn = plot.getSpawn();
            player.teleport(spawn);

            sender.sendMessage(Locale.Plot.TELEPORTED);
        });
    }
}
