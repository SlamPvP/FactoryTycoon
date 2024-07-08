package com.slampvp.factory.randomticksystem;

import com.slampvp.factory.blocks.behaviours.randomtick.RandomTickable;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.instance.InstanceTickEvent;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;

import java.util.SplittableRandom;

public class RandomTickManager {

    private static final Short2ObjectMap<RandomTickable> randomTickables = new Short2ObjectOpenHashMap<>();
    private final SplittableRandom random = new SplittableRandom();
    private static RandomTickManager instance;

    private RandomTickManager() {}

    public static synchronized RandomTickManager getInstance() {
        if (instance == null) {
            instance = new RandomTickManager();
        }
        return instance;
    }

    public void init() {
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(InstanceTickEvent.class, event -> {
            int randomTickCount = 20;
            instance.handleInstanceTick(event, randomTickCount);
        });
    }

    public static void registerRandomTickable(short stateId, RandomTickable randomTickable) {
        synchronized (randomTickables) {
            randomTickables.put(stateId, randomTickable);
        }
    }

    private void handleInstanceTick(InstanceTickEvent event, int randomTickCount) {
        Instance instance = event.getInstance();
        synchronized (randomTickables) {
            for (Chunk chunk : instance.getChunks()) {
                int minSection = chunk.getMinSection();
                int maxSection = chunk.getMaxSection();
                for (int section = minSection; section < maxSection; section++) {
                    for (int i = 0; i < randomTickCount; i++) {
                        randomTickSection(chunk, section);
                    }
                }
            }
        }
    }

    private void randomTickSection(Chunk chunk, int minSection) {
        int minX = chunk.getChunkX() * Chunk.CHUNK_SIZE_X;
        int minZ = chunk.getChunkZ() * Chunk.CHUNK_SIZE_Z;
        int minY = minSection * Chunk.CHUNK_SECTION_SIZE;

        int x = minX + random.nextInt(Chunk.CHUNK_SIZE_X);
        int z = minZ + random.nextInt(Chunk.CHUNK_SIZE_Z);
        int y = minY + random.nextInt(Chunk.CHUNK_SECTION_SIZE);
        Point pos = new Vec(x, y, z);

        Block block = chunk.getBlock(pos);
        RandomTickable randomTickable = randomTickables.get((short) block.stateId());
        if (randomTickable != null) {
            randomTickable.randomTick(new RandomTick(pos, block.stateId()));
        }
    }
}