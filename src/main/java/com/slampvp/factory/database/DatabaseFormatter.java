package com.slampvp.factory.database;

import net.minestom.server.coordinate.BlockVec;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public final class DatabaseFormatter {
    public static String pointToString(Point point) {
        if (point instanceof Vec vec) {
            return "(" + vec.x() + "," + vec.y() + "," + vec.z() + ")";
        } else if (point instanceof Pos pos) {
            return "(" + pos.x() + "," + pos.y() + "," + pos.z() + "," + pos.yaw() + "," + pos.pitch() + ")";
        }
        return null;
    }

    public static BlockVec stringToBlockVec(String string) {
        Float[] array = parseString(string);
        if (array.length == 0) {
            return null;
        }
        return new BlockVec(array[0], array[1], array[2]);
    }

    public static Vec stringToVec(String string) {
        Float[] array = parseString(string);
        if (array.length == 0) {
            return null;
        }
        return new Vec(array[0], array[1], array[2]);
    }

    public static Pos stringToPos(String string) {
        Float[] array = parseString(string);
        if (array.length == 0) {
            return null;
        }
        return new Pos(array[0], array[1], array[2], array[3], array[4]);
    }

    private static Float @NotNull [] parseString(String string) {
        if (string == null) {
            return new Float[0];
        }
        return Arrays.stream(string
                        .substring(1, string.length() - 1)
                        .split(","))
                .map(Float::parseFloat)
                .toArray(Float[]::new);
    }
}
