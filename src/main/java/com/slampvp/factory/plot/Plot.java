package com.slampvp.factory.plot;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.slampvp.factory.util.BlockVecSerializer;
import net.minestom.server.coordinate.BlockVec;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record Plot(
        UUID ID,
        UUID owner,
        @JsonSerialize(using = BlockVecSerializer.class) BlockVec start,
        @JsonSerialize(using = BlockVecSerializer.class) BlockVec end,
        Set<UUID> members,
        Map<PlotFlag.Target, Integer> flags
) {
    public Plot(UUID ID, UUID owner, BlockVec start, BlockVec end) {
        this(ID, owner, start, end, new HashSet<>(), new EnumMap<>(PlotFlag.Target.class));
        this.flags.putAll(PlotFlag.DEFAULT_PERMISSIONS);
    }

    public void setFlag(PlotFlag.Target target, PlotFlag.Flag flag, boolean enabled) {
        final int currentFlags = this.flags.getOrDefault(target, 0);
        final int newFlags = currentFlags ^ ((-(enabled ? 1 : 0) ^ currentFlags) & (1 << flag.getBit()));

        this.flags.put(target, newFlags);
    }

    public Set<PlotFlag.Flag> flags(PlotFlag.Target target) {
        int bitmap = this.flags.getOrDefault(target, 0);

        return Stream.of(PlotFlag.Flag.values())
                .filter(flag -> (bitmap & (1 << flag.getBit())) != 0)
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(PlotFlag.Flag.class)));
    }
}
