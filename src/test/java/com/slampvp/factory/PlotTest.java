package com.slampvp.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.slampvp.factory.plot.Plot;
import com.slampvp.factory.plot.PlotFlag;
import net.minestom.server.coordinate.BlockVec;
import org.junit.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class PlotTest {
    @Test
    public void testSerialize() throws JsonProcessingException {
        UUID ID = UUID.randomUUID();
        UUID owner = UUID.randomUUID();

        Plot plot = new Plot(ID, owner, new BlockVec(0, 0, 0), new BlockVec(1, 1, 1), Set.of(), Map.of(PlotFlag.Target.MEMBER, 3, PlotFlag.Target.PUBLIC, 4));

        ObjectMapper objectMapper = new ObjectMapper();
        String serialized = objectMapper.writeValueAsString(plot);

        // TODO: fix flaky test
        assertEquals(serialized, "{" +
                "\"ID\":\"" + ID + "\"," +
                "\"owner\":\"" + owner + "\"," +
                "\"start\":{\"x\":0,\"y\":0,\"z\":0}," +
                "\"end\":{\"x\":1,\"y\":1,\"z\":1}," +
                "\"members\":[]," +
                "\"flags\":{\"MEMBER\":3,\"PUBLIC\":4}" +
                "}"
        );
    }

    @Test
    public void testDeserialize() throws JsonProcessingException {
        UUID ID = UUID.randomUUID();
        UUID owner = UUID.randomUUID();

        String json = "{" +
                "\"ID\":\"" + ID + "\"," +
                "\"owner\":\"" + owner + "\"," +
                "\"start\":{\"x\":0,\"y\":0,\"z\":0}," +
                "\"end\":{\"x\":1,\"y\":1,\"z\":1}," +
                "\"members\":[]," +
                "\"flags\":{\"MEMBER\":3,\"PUBLIC\":4}" +
                "}";

        ObjectMapper objectMapper = new ObjectMapper();
        Plot plot = objectMapper.readValue(json, Plot.class);

        assertEquals(plot.ID(), ID);
        assertEquals(plot.owner(), owner);
        assertEquals(plot.start(), new BlockVec(0, 0, 0));
        assertEquals(plot.end(), new BlockVec(1, 1, 1));
        assertEquals(plot.members(), Set.of());
        assertEquals(plot.flags(), Map.of(PlotFlag.Target.MEMBER, 3, PlotFlag.Target.PUBLIC, 4));
    }

    @Test
    public void testFlags() {
        Plot plot = new Plot(UUID.randomUUID(), UUID.randomUUID(), new BlockVec(0, 0, 0), new BlockVec(1, 1, 1));

        assertEquals(plot.flags(PlotFlag.Target.MEMBER), Set.of(PlotFlag.Flag.BUILD, PlotFlag.Flag.BREAK));
        assertEquals(plot.flags(PlotFlag.Target.PUBLIC), new HashSet<>());

        plot.setFlag(PlotFlag.Target.MEMBER, PlotFlag.Flag.BREAK, false);
        plot.setFlag(PlotFlag.Target.PUBLIC, PlotFlag.Flag.FLY, true);

        assertEquals(plot.flags(PlotFlag.Target.MEMBER), Set.of(PlotFlag.Flag.BUILD));
        assertEquals(plot.flags(PlotFlag.Target.PUBLIC), Set.of(PlotFlag.Flag.FLY));
    }
}
