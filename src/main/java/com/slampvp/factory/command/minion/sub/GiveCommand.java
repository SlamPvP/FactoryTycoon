package com.slampvp.factory.command.minion.sub;

import com.slampvp.factory.command.Command;
import com.slampvp.factory.command.FactoryCommand;
import com.slampvp.factory.common.Locale;
import com.slampvp.factory.minion.models.Minion;
import com.slampvp.factory.player.Rank;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.minecraft.ArgumentEntity;
import net.minestom.server.entity.Player;

@Command(
        description = "Give a minion to a player.",
        usage = "/minion give <player> <minion>",
        minimumRank = Rank.ADMIN,
        playerOnly = false
)
public class GiveCommand extends FactoryCommand {
    public GiveCommand() {
        super("give");
    }

    @Override
    public void init() {
        ArgumentEntity argumentPlayer = ArgumentType.Entity("player").singleEntity(true).onlyPlayers(true);
//        ArgumentMinion argumentMinion = new ArgumentMinion("minion");

        addSyntax((sender, context) -> {
            Player target = context.get(argumentPlayer).findFirstPlayer(sender);

            if (target == null) {
                sender.sendMessage(Locale.Command.INVALID_PLAYER);
                return;
            }

//            Minion minion = context.get(argumentMinion);

            Minion minion = Minion.FARMER;

            target.getInventory().addItemStack(minion.getItem());

        }, argumentPlayer);
    }
}
