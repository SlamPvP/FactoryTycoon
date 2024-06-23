package com.slampvp.factory.command.plot.sub;

import com.slampvp.factory.FactoryServer;
import com.slampvp.factory.command.Command;
import com.slampvp.factory.command.FactoryCommand;
import com.slampvp.factory.common.Locale;
import com.slampvp.factory.player.Rank;
import com.slampvp.factory.plot.models.MergeResult;
import com.slampvp.factory.plot.PlotManager;
import net.minestom.server.entity.Player;

@Command(description = "Merge two plots together into one large plot.", usage = "/plot merge", minimumRank = Rank.DEFAULT, playerOnly = true)
public class MergeCommand extends FactoryCommand {
    public MergeCommand() {
        super("merge");
    }

    @Override
    public void init() {
        addSyntax((sender, context) -> {
            Player player = (Player) sender;
            MergeResult mergeResult = PlotManager.getInstance().mergePlot(player);

            switch (mergeResult) {
                case NOT_IN_PLOT -> sender.sendMessage(Locale.Plot.NOT_IN_PLOT);
                case NO_MERGE_CANDIDATE -> sender.sendMessage(Locale.Plot.NO_MERGE_CANDIDATE);
                case SUCCESS -> {
                    PlotManager.getInstance().getPlot(player.getPosition()).ifPresent(plot -> player.teleport(plot.getSpawn()));
                    sender.sendMessage(Locale.Plot.MERGED);
                }
                case FAILURE -> {
                    FactoryServer.LOGGER.error("Plot merging failed.");
                }
            }
        });
    }
}
