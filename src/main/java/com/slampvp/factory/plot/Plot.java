package com.slampvp.factory.plot;

import com.slampvp.factory.common.Constants;
import net.minestom.server.coordinate.BlockVec;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Plot {
    private final UUID id;
    private final UUID owner;
    private final Vec start;
    private final Vec end;
    private Pos spawn;
    private final Map<String, Pos> warps;
    private final Map<UUID, PlotFlag.Target> members;
    private final Map<PlotFlag.Target, Integer> flags;

    public Plot(UUID id, UUID owner, Vec start, Vec end, Pos spawn, Map<String, Pos> warps, Map<UUID, PlotFlag.Target> members, Map<PlotFlag.Target, Integer> flags) {
        this.id = id;
        this.owner = owner;
        this.start = start;
        this.end = end;
        this.spawn = spawn;
        this.warps = warps;
        this.members = members;
        this.flags = flags;
    }

    public Plot(UUID id, UUID owner, Vec start, Vec end) {
        this(id, owner, start, end, null, new HashMap<>(), new HashMap<>(), new EnumMap<>(PlotFlag.Target.class));
        this.spawn = end.add(start).div(2).add(0.5).withY(Constants.Plot.HEIGHT + 1).asPosition();
        this.flags.putAll(PlotFlag.DEFAULT_PERMISSIONS);
    }

    public void setFlag(PlotFlag.Target target, PlotFlag.Flag flag, boolean enabled) {
        int currentFlags = this.flags.getOrDefault(target, 0);
        int newFlags = currentFlags ^ ((-(enabled ? 1 : 0) ^ currentFlags) & (1 << flag.getBit()));

        this.flags.put(target, newFlags);
    }

    public boolean isAdded(Player player) {
        UUID uuid = player.getUuid();

        if (owner.equals(uuid)) return true;

        return members.containsKey(uuid);
    }

    public boolean contains(Point point) {
        return point.blockX() >= start.blockX() && point.blockX() <= end.blockX() &&
                point.blockY() >= start.blockY() && point.blockY() <= end.blockY() &&
                point.blockZ() >= start.blockZ() && point.blockZ() <= end.blockZ();
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

    public Vec getStart() {
        return start;
    }

    public Vec getEnd() {
        return end;
    }

    public Pos getSpawn() {
        return spawn;
    }

    public void setSpawn(@NotNull Pos spawn) {
        this.spawn = spawn;
    }

    public Map<String, Pos> getWarps() {
        return warps;
    }

    public Map<UUID, PlotFlag.Target> getMembers() {
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
