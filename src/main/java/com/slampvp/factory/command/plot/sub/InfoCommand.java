package com.slampvp.factory.command.plot.sub;

import com.slampvp.factory.command.Command;
import com.slampvp.factory.command.FactoryCommand;
import com.slampvp.factory.common.Locale;
import com.slampvp.factory.player.Rank;
import com.slampvp.factory.plot.models.Plot;
import com.slampvp.factory.plot.PlotManager;
import net.minestom.server.entity.Player;

import java.util.Optional;

@Command(
        description = "Get info of a plot.",
        usage = "/plot info",
        minimumRank = Rank.ADMIN,
        playerOnly = true
)
public class InfoCommand extends FactoryCommand {
    public InfoCommand() {
        super("info");
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

            player.sendMessage("ID: " + plot.getId());
            player.sendMessage("Owner: " + plot.getOwner());
            player.sendMessage("Start: " + plot.getStart());
            player.sendMessage("End: " + plot.getEnd());
            player.sendMessage("");
        });
    }
}
