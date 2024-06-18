package com.slampvp.factory.plot;

import net.minestom.server.coordinate.Vec;

import java.util.UUID;

public record Plot(UUID owner, Vec start, Vec end) {
}
