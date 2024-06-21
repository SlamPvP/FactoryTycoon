package com.slampvp.factory.common;

import net.minestom.server.coordinate.Pos;

public final class Constants {

    public static final int HEIGHT = 40;
    public static final Pos SPAWN = new Pos(0.5, HEIGHT, 0.5);

    public static final class Plot {
        public static final int PLOT_HEIGHT = HEIGHT + 50;
        public static final int PLOT_SIZE = 14;
        public static final int ROAD_WIDTH = 3;
        public static final int FULL_WIDTH = PLOT_SIZE + ROAD_WIDTH;
    }
}
