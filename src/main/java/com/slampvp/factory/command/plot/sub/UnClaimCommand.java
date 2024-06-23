package com.slampvp.factory.command.plot.sub;

import com.slampvp.factory.command.Command;
import com.slampvp.factory.command.FactoryCommand;
import com.slampvp.factory.common.Locale;
import com.slampvp.factory.player.Rank;
import com.slampvp.factory.plot.PlotManager;
import com.slampvp.factory.plot.models.UnClaimResult;
import net.minestom.server.entity.Player;

@Command(description = "Un-claim a plot.", usage = "/plot unclaim", minimumRank = Rank.DEFAULT, playerOnly = true)
public class UnClaimCommand extends FactoryCommand {
    public UnClaimCommand() {
        super("unclaim");
    }

    @Override
    public void init() {
        addSyntax((sender, context) -> {
            Player player = (Player) sender;
            UnClaimResult claimResult = PlotManager.getInstance().unClaimPlot(player);

            switch (claimResult) {
                case NOT_IN_PLOT -> sender.sendMessage(Locale.Plot.NOT_IN_PLOT);
                case NOT_CLAIMED -> sender.sendMessage(Locale.Plot.NOT_CLAIMED);
                case SUCCESS -> sender.sendMessage(Locale.Plot.UNCLAIMED);
            }
        });
    }
}
