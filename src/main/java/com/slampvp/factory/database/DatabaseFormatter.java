package com.slampvp.factory.database;

import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public final class DatabaseFormatter {
    public static String pointToString(Point point) {
        if (point instanceof Vec vec) {
            return "(" + vec.x() + "," + vec.y() + "," + vec.z() + ")";
        } else if (point instanceof Pos pos) {
            return "(" + pos.x() + "," + pos.y() + "," + pos.z() + "," + pos.yaw() + "," + pos.pitch() + ")";
        }
        return null;
    }

    public static Vec stringToVec(String string) {
        Float[] array = parseString(string);
        return new Vec(array[0], array[1], array[2]);
    }

    public static Pos stringToPos(String string) {
        Float[] array = parseString(string);
        return new Pos(array[0], array[1], array[2], array[3], array[4]);
    }

    private static Float @NotNull [] parseString(@NotNull String string) {
        return Arrays.stream(string
                        .substring(1, string.length() - 1)
                        .split(","))
                .map(Float::parseFloat)
                .toArray(Float[]::new);
    }
}
