package com.slampvp.factory.command.minion.sub;

import com.slampvp.factory.command.Command;
import com.slampvp.factory.command.FactoryCommand;
import com.slampvp.factory.common.Locale;
import com.slampvp.factory.common.StringUtil;
import com.slampvp.factory.minion.models.Minion;
import com.slampvp.factory.minion.models.Minions;
import com.slampvp.factory.player.Rank;
import net.minestom.server.command.builder.arguments.ArgumentString;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.minecraft.ArgumentEntity;
import net.minestom.server.command.builder.arguments.number.ArgumentInteger;
import net.minestom.server.command.builder.arguments.number.ArgumentNumber;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;
import net.minestom.server.entity.Player;

import java.util.ArrayList;
import java.util.Optional;

@Command(
        description = "Give a minion to a player.",
        usage = "/minion give <player> <minion> [amount]",
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
        ArgumentString argumentMinion = ArgumentType.String("minion");
        ArgumentNumber<Integer> argumentAmount = ArgumentType.Integer("amount").min(1);

        argumentMinion.setSuggestionCallback((sender, context, suggestion) -> {
            String arg = suggestion.getInput().substring(suggestion.getStart() - 1, suggestion.getStart() + suggestion.getLength() - 1);
            StringUtil.copyPartialMatches(arg, Minion.all().stream().map(Minion::id).toList(), new ArrayList<>()).forEach(string -> {
                suggestion.addEntry(new SuggestionEntry(string));
            });
        });

        addSyntax((sender, context) -> {
            Player target = context.get(argumentPlayer).findFirstPlayer(sender);

            if (target == null) {
                sender.sendMessage(Locale.Command.INVALID_PLAYER);
                return;
            }

            Optional<Minion> optionalMinion = Minion.byId(context.get(argumentMinion));

            if (optionalMinion.isEmpty()) {
                sender.sendMessage(Locale.Minion.INVALID_ID);
                return;
            }

            Minion minion = optionalMinion.get();

            target.getInventory().addItemStack(minion.getItem());
        }, argumentPlayer, argumentMinion);

        addSyntax((sender, context) -> {
            Player target = context.get(argumentPlayer).findFirstPlayer(sender);

            if (target == null) {
                sender.sendMessage(Locale.Command.INVALID_PLAYER);
                return;
            }

            Optional<Minion> optionalMinion = Minion.byId(context.get(argumentMinion));

            if (optionalMinion.isEmpty()) {
                sender.sendMessage(Locale.Minion.INVALID_ID);
                return;
            }

            int amount = context.get(argumentAmount);

            Minion minion = optionalMinion.get();

            for (int i = 0; i < amount; i++) {
                target.getInventory().addItemStack(minion.getItem());
            }
        }, argumentPlayer, argumentMinion, argumentAmount);
    }
}
