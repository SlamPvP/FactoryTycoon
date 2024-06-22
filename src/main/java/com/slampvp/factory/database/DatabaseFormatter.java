package com.slampvp.factory.database;

import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;

public class DatabaseFormatter {
    public static String pointToString(Point point) {
        if (point instanceof Vec vec) {
            return "(" + vec.x() + "," + vec.y() + "," + vec.z() + ")";
        } else if (point instanceof Pos pos) {
            return "(" + pos.x() + "," + pos.y() + "," + pos.z() + "," + pos.yaw() + "," + pos.pitch() + ")";
        }
        return null;
    }
}
