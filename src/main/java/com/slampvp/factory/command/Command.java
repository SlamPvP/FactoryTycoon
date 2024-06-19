package com.slampvp.factory.command;

import com.slampvp.factory.player.Rank;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Command annotation is used to provide metadata for command methods.
 * It includes a description of the command, its usage, the minimum rank required
 * to execute the command, and whether the command can only be executed by players.
 *
 * @see com.slampvp.factory.player.Rank
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Command {

    /**
     * A brief description of what the command does.
     *
     * @return the description of the command
     */
    String description();

    /**
     * A string explaining how to use the command.
     *
     * @return the usage syntax of the command
     */
    String usage();

    /**
     * The minimum rank required to execute the command.
     *
     * @return the minimum rank required to execute the command
     */
    Rank minimumRank();

    /**
     * A flag indicating whether the command can only be executed by players
     * (as opposed to console or other entities).
     *
     * @return true if the command can only be executed by players, false otherwise
     */
    boolean playerOnly();
}
