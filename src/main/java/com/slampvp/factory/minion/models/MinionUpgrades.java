package com.slampvp.factory.minion.models;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public final class MinionUpgrades {
    private final Map<Type, Integer> upgrades;

    public MinionUpgrades() {
        this(new EnumMap<>(Type.class) {{
            put(Type.Range, 1);
            put(Type.Speed, 1);
        }});
    }

    public MinionUpgrades(EnumMap<Type, Integer> upgrades) {
        this.upgrades = upgrades;
    }

    public Map<Type, Integer> getUpgrades() {
        return Collections.unmodifiableMap(upgrades);
    }

    public enum Type {
        Range(1, 3, 1000, 2),
        Speed(1, 3, 1000, 2);

        private final int minLevel;
        private final int maxLevel;
        private final double startPrice;
        private final double priceIncrement;

        Type(int minLevel, int max, double startPrice, double priceIncrement) {
            this.minLevel = minLevel;
            this.maxLevel = max;
            this.startPrice = startPrice;
            this.priceIncrement = priceIncrement;
        }

        public int minLevel() {
            return this.minLevel;
        }

        public int maxLevel() {
            return this.maxLevel;
        }

        public double price(int level) {
            if (level > this.maxLevel) {
                throw new IllegalArgumentException("Level cannot be higher than " + level + ".");
            }
            return this.startPrice * this.priceIncrement * level;
        }
    }
}
