package com.slampvp.factory.command.minion.plot;

import com.slampvp.factory.command.Command;
import com.slampvp.factory.command.FactoryCommand;
import com.slampvp.factory.player.Rank;

@Command(description = "Plot command.", usage = "/plot", minimumRank = Rank.DEFAULT, playerOnly = true)
public class PlotCommand extends FactoryCommand {
    public PlotCommand() {
        super("plot", "p");
    }

    @Override
    public void init() {
    }
}
