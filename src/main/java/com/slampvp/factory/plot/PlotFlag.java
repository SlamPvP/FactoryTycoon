package com.slampvp.factory.plot;

import java.util.EnumMap;
import java.util.Map;

public class PlotFlag {

    /**
     * Default permissions for each target.
     * TRUSTED: can BUILD, BREAK, and FLY.
     * MEMBER: can BUILD and BREAK.
     * PUBLIC: no permissions.
     */
    public static final Map<Target, Integer> DEFAULT_PERMISSIONS = new EnumMap<>(Target.class);

    static {
        DEFAULT_PERMISSIONS.put(Target.TRUSTED, (1 << Flag.BUILD.getBit()) | (1 << Flag.BREAK.getBit()) | (1 << Flag.FLY.getBit()));
        DEFAULT_PERMISSIONS.put(Target.MEMBER, (1 << Flag.BUILD.getBit()) | (1 << Flag.BREAK.getBit()));
        DEFAULT_PERMISSIONS.put(Target.PUBLIC, 0);
    }

    public enum Target {
        /**
         * Trusted target: has the highest level of permissions.
         * Default permissions: can BUILD, BREAK, and FLY.
         */
        TRUSTED,

        /**
         * Member target: has standard permissions.
         * Default permissions: can BUILD and BREAK.
         */
        MEMBER,

        /**
         * Public target: has the most restricted permissions.
         * Default permissions: no permissions.
         */
        PUBLIC;
    }

    /**
     * Enumeration representing different permission flags for a plot target.
     * Each flag corresponds to a specific action and is associated with a unique bit position.
     */
    public enum Flag {
        /**
         * Permission to build structures or modify the environment.
         * This action is represented by the bit position 0.
         */
        BUILD(0),

        /**
         * Permission to break structures or objects.
         * This action is represented by the bit position 1.
         */
        BREAK(1),

        /**
         * Permission to fly.
         * This action is represented by the bit position 2.
         */
        FLY(2);

        private final int bit;

        /**
         * Constructs a new Flag with the specified bit position.
         *
         * @param bit the bit position associated with the flag
         */
        Flag(int bit) {
            this.bit = bit;
        }

        /**
         * Returns the bit position associated with this flag.
         *
         * @return the bit position
         */
        public int getBit() {
            return bit;
        }
    }
}
