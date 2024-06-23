package com.slampvp.factory.command.plot.sub;

import com.slampvp.factory.FactoryServer;
import com.slampvp.factory.command.Command;
import com.slampvp.factory.command.FactoryCommand;
import com.slampvp.factory.common.Locale;
import com.slampvp.factory.player.Rank;
import com.slampvp.factory.plot.models.ClaimResult;
import com.slampvp.factory.plot.PlotManager;
import net.minestom.server.entity.Player;

import java.util.Objects;

@Command(description = "Claim a new plot.", usage = "/plot auto", minimumRank = Rank.DEFAULT, playerOnly = true)
public class AutoCommand extends FactoryCommand {
    public AutoCommand() {
        super("auto");
    }

    @Override
    public void init() {
        addSyntax((sender, context) -> {
            PlotManager plotManager = PlotManager.getInstance();
            Player player = (Player) sender;
            ClaimResult claimResult = plotManager.claimFreePlot(player);

            if (Objects.requireNonNull(claimResult) == ClaimResult.SUCCESS) {
                player.teleport(plotManager.getPlots(player).getLast().getSpawn());
                sender.sendMessage(Locale.Plot.CLAIMED);
            } else {
                FactoryServer.LOGGER.error("This should not happen.");
            }
        });
    }
}
