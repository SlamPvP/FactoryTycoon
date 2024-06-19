package com.slampvp.factory.command;

import com.slampvp.factory.player.Rank;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    String description();

    String usage();

    Rank minimumRank();

    boolean playerOnly();
}