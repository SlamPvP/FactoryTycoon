package com.slampvp.factory;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.slampvp.factory.plot.Plot;
import com.slampvp.factory.plot.PlotFlag;
import net.minestom.server.coordinate.BlockVec;
import net.minestom.server.coordinate.Pos;
import org.junit.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class PlotTest {
    @Test
    public void testSerialize() {
        UUID id = UUID.randomUUID();
        UUID owner = UUID.randomUUID();

        Plot plot = new Plot(id, owner, new BlockVec(0, 0, 0), new BlockVec(1, 1, 1),
                new Pos(1, 1, 1),
                Map.of(), Map.of(), Map.of(PlotFlag.Target.MEMBER, 3, PlotFlag.Target.PUBLIC, 4));

        String json = new Gson().toJson(plot);

        String expected = "{" +
                "\"id\":\"" + id + "\"," +
                "\"owner\":\"" + owner + "\"," +
                "\"start\":{\"x\":0.0,\"y\":0.0,\"z\":0.0}," +
                "\"end\":{\"x\":1.0,\"y\":1.0,\"z\":1.0}," +
                "\"spawn\":{\"x\":1.0,\"y\":1.0,\"z\":1.0,\"yaw\":0.0,\"pitch\":0.0}," +
                "\"warps\":{}," +
                "\"members\":{}," +
                "\"flags\":{\"MEMBER\":3,\"PUBLIC\":4}" +
                "}";

        JsonObject expectedJson = JsonParser.parseString(expected).getAsJsonObject();
        JsonObject actualJson = JsonParser.parseString(json).getAsJsonObject();

        assertEquals(expectedJson, actualJson);
    }

    @Test
    public void testDeserialize() {
        UUID id = UUID.randomUUID();
        UUID owner = UUID.randomUUID();

        String json = "{" +
                "\"id\":\"" + id + "\"," +
                "\"owner\":\"" + owner + "\"," +
                "\"start\":{\"x\":0.0,\"y\":0.0,\"z\":0.0}," +
                "\"end\":{\"x\":1.0,\"y\":1.0,\"z\":1.0}," +
                "\"spawn\":{\"x\":1.0,\"y\":1.0,\"z\":1.0,\"yaw\":0.0,\"pitch\":0.0}," +
                "\"members\":{}," +
                "\"flags\":{\"MEMBER\":3,\"PUBLIC\":4}" +
                "}";

        Plot plot = new Gson().fromJson(json, Plot.class);

        assertEquals(id, plot.getId());
        assertEquals(owner, plot.getOwner());
        assertEquals(new BlockVec(0, 0, 0), plot.getStart());
        assertEquals(new BlockVec(1, 1, 1), plot.getEnd());
        assertEquals(new Pos(1, 1, 1, 0, 0), plot.getSpawn());
        assertEquals(Map.of(), plot.getMembers());
        assertEquals(Map.of(PlotFlag.Target.MEMBER, 3, PlotFlag.Target.PUBLIC, 4), plot.getFlags());
    }

    @Test
    public void testFlags() {
        Plot plot = new Plot(UUID.randomUUID(), UUID.randomUUID(), new BlockVec(0, 0, 0), new BlockVec(1, 1, 1));

        assertEquals(Set.of(PlotFlag.Flag.BUILD, PlotFlag.Flag.BREAK), plot.getFlags(PlotFlag.Target.MEMBER));
        assertEquals(new HashSet<>(), plot.getFlags(PlotFlag.Target.PUBLIC));

        plot.setFlag(PlotFlag.Target.MEMBER, PlotFlag.Flag.BREAK, false);
        plot.setFlag(PlotFlag.Target.PUBLIC, PlotFlag.Flag.FLY, true);

        assertEquals(Set.of(PlotFlag.Flag.BUILD), plot.getFlags(PlotFlag.Target.MEMBER));
        assertEquals(Set.of(PlotFlag.Flag.FLY), plot.getFlags(PlotFlag.Target.PUBLIC));
    }
}
