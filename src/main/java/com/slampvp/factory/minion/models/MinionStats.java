package com.slampvp.factory.minion.models;

public final class MinionStats {
    private long timeActive;
    private long amountGenerated;

    public MinionStats(long timeActive, long amountGenerated) {
        this.timeActive = timeActive;
        this.amountGenerated = amountGenerated;
    }

    public MinionStats() {
        this(0,0);
    }

    public long timeActive() {
        return this.timeActive;
    }

    public long amountGenerated() {
        return this.amountGenerated;
    }

    public void addTime(long time) {
        this.timeActive += time;
    }

    public void addAmount(long amount) {
        this.amountGenerated += amount;
    }
}
