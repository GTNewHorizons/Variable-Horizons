package com.LazyFlesh.variablehorizons.util.blockUtils;

import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import akka.japi.Pair;

public class GridGenerator {

    private static final int BLOCKS_PER_CHUNK = 65536;

    // Large odd multipliers for chunk seeding
    private static final long CHUNK_X_MULT = 341873128712L;
    private static final long CHUNK_Z_MULT = 132897987541L;

    public static Pair<Block[], byte[]> generateGrid(long worldSeed, int chunkX, int chunkZ) {
        Block[] blocks = new Block[BLOCKS_PER_CHUNK];
        byte[] metadata = new byte[BLOCKS_PER_CHUNK];
        Arrays.fill(blocks, Blocks.air);
        long chunkSeed = worldSeed ^ (chunkX * CHUNK_X_MULT) ^ (chunkZ * CHUNK_Z_MULT);
        List<BlockData> blocksList = BlocksRegistry.getBlocks();
        int blocksListSize = blocksList.size();

        for (int x = 1; x < 16; x += 4) {
            for (int z = 1; z < 16; z += 4) {
                for (int y = 3; y < 256; y += 4) {
                    int index = (x << 12) | (z << 8) | y;
                    long posSeed = mixSeed(chunkSeed, x, y, z);
                    BlockData data = blocksList.get(boundedIndex(posSeed, blocksListSize));

                    blocks[index] = data.block;
                    metadata[index] = data.meta;
                }
            }
        }
        return new Pair<>(blocks, metadata);
    }

    /**
     * This is called splitmix64 apparently, used to combine a base seed with numbers
     * into a well-mixed 64-bit value. Here its meant to avoid correlation between nearby blocks.
     */
    private static long mixSeed(long baseSeed, int x, int y, int z) {
        long h = baseSeed;
        h ^= (x * 0x9E3779B97F4A7C15L);
        h ^= (y * 0xBF58476D1CE4E5B9L);
        h ^= (z * 0x94D049BB133111EBL);

        // splitmix64 finalizer
        h = (h ^ (h >>> 30)) * 0xBF58476D1CE4E5B9L;
        h = (h ^ (h >>> 27)) * 0x94D049BB133111EBL;
        h = h ^ (h >>> 31);
        return h;
    }

    private static int boundedIndex(long mixed, int bound) {
        return (int) ((mixed & Long.MAX_VALUE) % bound);
    }
}
