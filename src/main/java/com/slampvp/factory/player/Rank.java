package com.slampvp.factory.player;

public enum Rank {
    DEFAULT(1);

    private final int weight;

    Rank(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public boolean isEqualOrHigherThan(Rank rank) {
        return this.getWeight() <= rank.getWeight();
    }
}
