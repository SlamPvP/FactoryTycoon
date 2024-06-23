package com.slampvp.factory;

import com.slampvp.factory.plot.models.Plot;
import com.slampvp.factory.plot.models.PlotFlag;
import com.slampvp.factory.plot.models.PlotId;
import net.minestom.server.coordinate.Vec;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class PlotTest {
    @Test
    public void testFlags() {
        Plot plot = new Plot(new PlotId(1, 1), UUID.randomUUID(), new Vec(0, 0, 0), new Vec(1, 1, 1));

        assertEquals(Set.of(PlotFlag.Flag.BUILD, PlotFlag.Flag.BREAK), plot.getFlags(PlotFlag.Target.MEMBER));
        assertEquals(new HashSet<>(), plot.getFlags(PlotFlag.Target.PUBLIC));

        plot.setFlag(PlotFlag.Target.MEMBER, PlotFlag.Flag.BREAK, false);
        plot.setFlag(PlotFlag.Target.PUBLIC, PlotFlag.Flag.FLY, true);

        assertEquals(Set.of(PlotFlag.Flag.BUILD), plot.getFlags(PlotFlag.Target.MEMBER));
        assertEquals(Set.of(PlotFlag.Flag.FLY), plot.getFlags(PlotFlag.Target.PUBLIC));
    }
}
