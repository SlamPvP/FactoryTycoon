package com.slampvp.factory.plot.models;

import java.util.Arrays;

public record PlotId(int x, int z) {
    public static PlotId fromSQL(String string) {
        Integer[] array = Arrays.stream(string.substring(1, string.length() - 1).split(","))
                .map(Integer::parseInt)
                .toArray(Integer[]::new);

        return new PlotId(array[0], array[1]);
    }

    public String toSQL() {
        return "(" + x + "," + z + ")";
    }

    @Override
    public String toString() {
        return "PlotId{" +
                "x=" + x +
                ", z=" + z +
                '}';
    }
}
