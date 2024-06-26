package com.slampvp.factory.command.plot.sub;

import com.slampvp.factory.command.Command;
import com.slampvp.factory.command.FactoryCommand;
import com.slampvp.factory.common.Locale;
import com.slampvp.factory.player.Rank;
import com.slampvp.factory.plot.PlotManager;
import com.slampvp.factory.plot.models.ClaimResult;
import net.minestom.server.entity.Player;

@Command(description = "Claim a new plot at your location.", usage = "/plot claim", minimumRank = Rank.DEFAULT, playerOnly = true)
public class ClaimCommand extends FactoryCommand {
    public ClaimCommand() {
        super("claim");
    }

    @Override
    public void init() {
        addSyntax((sender, context) -> {
            Player player = (Player) sender;
            ClaimResult claimResult = PlotManager.getInstance().claimPlot(player);

            switch (claimResult) {
                case NOT_IN_PLOT -> sender.sendMessage(Locale.Plot.NOT_IN_PLOT);
                case ALREADY_CLAIMED -> sender.sendMessage(Locale.Plot.ALREADY_CLAIMED);
                case SUCCESS -> {
                    PlotManager.getInstance().getPlot(player.getPosition()).ifPresent(plot -> player.teleport(plot.getSpawn()));
                    sender.sendMessage(Locale.Plot.CLAIMED);
                }
            }
        });
    }
}
