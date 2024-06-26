package com.slampvp.factory.plot.models;

import com.slampvp.factory.common.Constants;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Plot {
    private final PlotId id;
    private final UUID owner;
    private final Vec start;
    private final Set<UUID> bannedPlayers;
    private final Map<String, Pos> warps;
    private final Map<UUID, PlotFlag.Target> members;
    private final Map<PlotFlag.Target, Integer> flags;
    private long dbId;
    private Vec end;
    private Pos spawn;

    public Plot(long dbId, PlotId id, UUID owner, Vec start, Vec end, Set<UUID> bannedPlayers, Pos spawn, Map<String, Pos> warps, Map<UUID, PlotFlag.Target> members, Map<PlotFlag.Target, Integer> flags) {
        this.dbId = dbId;
        this.id = id;
        this.owner = owner;
        this.start = start;
        this.end = end;
        this.bannedPlayers = bannedPlayers;
        this.spawn = spawn;
        this.warps = warps;
        this.members = members;
        this.flags = flags;
    }

    public Plot(PlotId id, UUID owner, Vec start, Vec end) {
        this(-1, id, owner, start, end, new HashSet<>(), null, new HashMap<>(), new HashMap<>(), new EnumMap<>(PlotFlag.Target.class));
        this.spawn = end.add(start).div(2).add(0.5).withY(Constants.HEIGHT + 1).asPosition();
        this.flags.putAll(PlotFlag.DEFAULT_PERMISSIONS);
    }

    public Plot(long dbId, PlotId id, UUID owner, Vec start, Vec end, Pos spawn) {
        this(dbId, id, owner, start, end, new HashSet<>(), spawn, new HashMap<>(), new HashMap<>(), new EnumMap<>(PlotFlag.Target.class));
        this.flags.putAll(PlotFlag.DEFAULT_PERMISSIONS);
    }

    public void setFlag(PlotFlag.Target target, PlotFlag.Flag flag, boolean enabled) {
        int currentFlags = this.flags.getOrDefault(target, 0);
        int newFlags = currentFlags ^ ((-(enabled ? 1 : 0) ^ currentFlags) & (1 << flag.getBit()));

        this.flags.put(target, newFlags);
    }

    public boolean isAdded(Player player) {
        UUID uuid = player.getUuid();

        if (this.owner.equals(uuid)) return true;

        return this.members.containsKey(uuid);
    }

    public boolean isMember(Player player) {
        return isAdded(player) &&
                this.getMembers().getOrDefault(player.getUuid(), PlotFlag.Target.PUBLIC) == PlotFlag.Target.MEMBER;
    }

    public boolean isTrusted(Player player) {
        return isAdded(player) &&
                this.getMembers().getOrDefault(player.getUuid(), PlotFlag.Target.PUBLIC) == PlotFlag.Target.TRUSTED;
    }

    public void addWarp(String name, Pos position) {
        this.warps.put(name, position);
    }

    public void removeWarp(String name) {
        this.warps.remove(name);
    }

    public Optional<Pos> getWarp(String name) {
        return Optional.ofNullable(this.warps.get(name));
    }

    public boolean contains(Point point) {
        return point.blockX() >= this.start.blockX() && point.blockX() <= this.end.blockX() &&
                point.blockY() >= this.start.blockY() && point.blockY() <= this.end.blockY() &&
                point.blockZ() >= this.start.blockZ() && point.blockZ() <= this.end.blockZ();
    }

    public void mergeWith(@NotNull Plot targetPlot) {
        if (!this.owner.equals(targetPlot.getOwner())) {
            throw new IllegalArgumentException("Cannot merge plots with different owners");
        }

        this.end = targetPlot.getEnd();
        this.spawn = this.start.add(this.end).div(2).add(0.5).withY(Constants.HEIGHT + 1).asPosition();

        this.bannedPlayers.addAll(targetPlot.getBannedPlayers());
        this.warps.putAll(targetPlot.getWarps());
        this.members.putAll(targetPlot.getMembers());

        for (Map.Entry<PlotFlag.Target, Integer> entry : targetPlot.getFlags().entrySet()) {
            this.flags.merge(entry.getKey(), entry.getValue(), (v1, v2) -> v1 | v2);
        }
    }

    public boolean isBanned(Player target) {
        return this.bannedPlayers.contains(target.getUuid());
    }

    public void banPlayer(Player player) {
        this.bannedPlayers.add(player.getUuid());
    }

    public void unbanPlayer(Player player) {
        this.bannedPlayers.remove(player.getUuid());
    }

    public void addMember(Player target, PlotFlag.Target type) {
        this.members.put(target.getUuid(), type);
    }

    public void removeMember(Player target) {
        this.members.remove(target.getUuid());
    }

    public long getDbId() {
        return dbId;
    }

    public void setDbId(long dbId) {
        this.dbId = dbId;
    }

    public PlotId getId() {
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
        return new HashMap<>(warps);
    }

    public Map<UUID, PlotFlag.Target> getMembers() {
        return new HashMap<>(members);
    }

    public Map<PlotFlag.Target, Integer> getFlags() {
        return new EnumMap<>(flags);
    }

    public Set<PlotFlag.Flag> getFlags(PlotFlag.Target target) {
        int bitmap = this.flags.getOrDefault(target, 0);

        return Stream.of(PlotFlag.Flag.values())
                .filter(flag -> (bitmap & (1 << flag.getBit())) != 0)
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(PlotFlag.Flag.class)));
    }

    public Set<UUID> getBannedPlayers() {
        return new HashSet<>(bannedPlayers);
    }

    public Vec getCenter() {
        return this.start.add(this.end).div(2);
    }

    @Override
    public String toString() {
        return "Plot{" +
                "dbId=" + dbId +
                ", id=" + id +
                ", owner=" + owner +
                ", start=" + start +
                ", end=" + end +
                ", bannedPlayers=" + bannedPlayers +
                ", warps=" + warps +
                ", members=" + members +
                ", flags=" + flags +
                ", spawn=" + spawn +
                '}';
    }
}
