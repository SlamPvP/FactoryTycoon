package com.slampvp.factory.command.plot.sub;

import com.slampvp.factory.command.Command;
import com.slampvp.factory.command.FactoryCommand;
import com.slampvp.factory.common.Locale;
import com.slampvp.factory.player.Rank;
import com.slampvp.factory.plot.Plot;
import com.slampvp.factory.plot.PlotGenerator;
import com.slampvp.factory.plot.PlotManager;
import net.minestom.server.entity.Player;

import java.util.Set;

@Command(description = "Claim a new plot.", usage = "/plot claim", minimumRank = Rank.DEFAULT, playerOnly = true)
public class ClaimCommand extends FactoryCommand {
    public ClaimCommand() {
        super("claim");
    }

    @Override
    public void init() {
        addSyntax((sender, context) -> {
            Player player = (Player) sender;

            boolean inPlot = PlotManager.getInstance().getPlot(player.getPosition());

            if (inPlot) {
                sender.sendMessage(Locale.Plot.CLAIM);
                PlotGenerator.claimPlot(player.getInstance(), player.getPosition());
            } else {
                sender.sendMessage(Locale.Plot.NOT_IN_PLOT);
            }

        });
    }
}
