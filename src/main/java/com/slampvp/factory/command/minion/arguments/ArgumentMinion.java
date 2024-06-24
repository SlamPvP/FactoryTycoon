package com.slampvp.factory.command.minion.arguments;

import com.slampvp.factory.minion.Minion;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.arguments.Argument;
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
import org.jetbrains.annotations.NotNull;

public class ArgumentMinion extends Argument<Minion> {
    public ArgumentMinion(@NotNull String id) {
        super(id);
    }

    @Override
    public @NotNull Minion parse(@NotNull CommandSender sender, @NotNull String input) throws ArgumentSyntaxException {
//        Optional<Minion> optionalMinion = Minion.byId(input);

//        if (optionalMinion.isEmpty()) {
        throw new ArgumentSyntaxException("Invalid Minion", input, 1);
//        } else {
//            return optionalMinion.get();
//        }
    }

    @Override
    public String parser() {
        return null;
    }

    public String toString() {
        return String.format("Minion<%s>", this.getId());
    }
}
