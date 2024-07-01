package com.slampvp.factory.minion.models;

import net.minestom.server.coordinate.BlockVec;
import net.minestom.server.coordinate.Vec;

import java.util.UUID;

public final class ActiveMinion {
    private final Minion minion;
    private final UUID owner;
    private final MinionStats stats;
    private final MinionUpgrades upgrades;
    private Vec position;
    private BlockVec chestPosition;
    private long dbId;

    public ActiveMinion(long dbId, BlockVec chestPosition, Vec position, MinionUpgrades upgrades, MinionStats stats, Minion minion, UUID owner) {
        this.dbId = dbId;
        this.chestPosition = chestPosition;
        this.position = position;
        this.upgrades = upgrades;
        this.stats = stats;
        this.minion = minion;
        this.owner = owner;
    }

    public ActiveMinion(Minion minion, UUID owner, Vec position) {
        this.minion = minion;
        this.owner = owner;
        this.position = position;
        this.stats = new MinionStats();
        this.upgrades = new MinionUpgrades();
    }

    public Minion minion() {
        return this.minion;
    }

    public Vec position() {
        return this.position;
    }

    public BlockVec chestPosition() {
        return this.chestPosition;
    }

    public MinionStats stats() {
        return this.stats;
    }

    public MinionUpgrades upgrades() {
        return upgrades;
    }

    public void setChestPosition(BlockVec chestPosition) {
        this.chestPosition = chestPosition;
    }

    public void setPosition(Vec position) {
        this.position = position;
    }

    public void setDbId(long dbId) {
        this.dbId = dbId;
    }

    public long dbId() {
        return this.dbId;
    }
}
