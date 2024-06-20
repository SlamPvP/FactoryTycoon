package com.slampvp.factory.player;

/**
 * Represents the different ranks a player can have.
 * Each rank has an associated weight that can be used
 * to compare the rank's hierarchy.
 * <p>
 * The weight is also used as bits for the rank bitmap.
 */
public enum Rank {
    /**
     * Default rank with the lowest weight.
     */
    DEFAULT(0),
    ADMIN(10);

    private final int weight;

    /**
     * Constructor to set the weight of the rank.
     *
     * @param weight The weight associated with the rank.
     */
    Rank(int weight) {
        this.weight = weight;
    }

    /**
     * Gets the weight of the rank.
     *
     * @return The weight of the rank.
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Checks if the current rank is equal to or higher than the specified rank.
     * In this context, a lower weight means a higher rank.
     *
     * @param rank The rank to compare with.
     * @return True if the current rank is equal to or higher than the specified rank,
     *         otherwise false.
     */
    public boolean isEqualOrHigherThan(Rank rank) {
        return this.getWeight() <= rank.getWeight();
    }
}
