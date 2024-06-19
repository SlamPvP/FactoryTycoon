package com.slampvp.factory.plot;

import net.minestom.server.coordinate.BlockVec;
import net.minestom.server.coordinate.Pos;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Plot {
    private final UUID id;
    private final UUID owner;
    private final BlockVec start;
    private final BlockVec end;
    private final Pos spawn;
    private final Map<String, Pos> warps;
    private final Set<UUID> members;
    private final Map<PlotFlag.Target, Integer> flags;

    public Plot(UUID id, UUID owner, BlockVec start, BlockVec end, Pos spawn, Map<String, Pos> warps, Set<UUID> members, Map<PlotFlag.Target, Integer> flags) {
        this.id = id;
        this.owner = owner;
        this.start = start;
        this.end = end;
        this.spawn = spawn;
        this.warps = warps;
        this.members = members;
        this.flags = flags;
    }

    public Plot(UUID id, UUID owner, BlockVec start, BlockVec end) {
        this(id, owner, start, end, null, new HashMap<>(), new HashSet<>(), new EnumMap<>(PlotFlag.Target.class));
        this.flags.putAll(PlotFlag.DEFAULT_PERMISSIONS);
    }

    public void setFlag(PlotFlag.Target target, PlotFlag.Flag flag, boolean enabled) {
        int currentFlags = this.flags.getOrDefault(target, 0);
        int newFlags = currentFlags ^ ((-(enabled ? 1 : 0) ^ currentFlags) & (1 << flag.getBit()));

        this.flags.put(target, newFlags);
    }

    public int getLevel() {
        return 1;
    }

    public UUID getId() {
        return id;
    }

    public UUID getOwner() {
        return owner;
    }

    public BlockVec getStart() {
        return start;
    }

    public BlockVec getEnd() {
        return end;
    }

    public Pos getSpawn() {
        return spawn;
    }

    public Map<String, Pos> getWarps() {
        return warps;
    }

    public Set<UUID> getMembers() {
        return members;
    }

    public Map<PlotFlag.Target, Integer> getFlags() {
        return flags;
    }

    public Set<PlotFlag.Flag> getFlags(PlotFlag.Target target) {
        int bitmap = this.flags.getOrDefault(target, 0);

        return Stream.of(PlotFlag.Flag.values())
                .filter(flag -> (bitmap & (1 << flag.getBit())) != 0)
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(PlotFlag.Flag.class)));
    }
}
