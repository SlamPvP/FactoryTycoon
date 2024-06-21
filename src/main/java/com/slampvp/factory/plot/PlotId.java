package com.slampvp.factory.plot;

public record PlotId(int x, int z) {
    public int getSum() {
        return Math.abs(x) + Math.abs(z);
    }

    @Override
    public String toString() {
        return "PlotId{" +
                "x=" + x +
                ", z=" + z +
                '}';
    }

    public PlotId increment() {
        boolean xLarger = Math.abs(x) >= Math.abs(z);

        // TODO: fix with -

        return new PlotId(xLarger ? x : x + 1, xLarger ? z + 1 : z);
    }
}
