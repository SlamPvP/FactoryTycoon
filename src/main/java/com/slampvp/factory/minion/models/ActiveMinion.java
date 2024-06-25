package com.slampvp.factory.minion.models;

import net.minestom.server.coordinate.BlockVec;

public record ActiveMinion(Minion minion, BlockVec position, BlockVec chestPosition) {

}
