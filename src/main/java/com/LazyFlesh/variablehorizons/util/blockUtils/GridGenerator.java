package com.LazyFlesh.variablehorizons.util.blockUtils;

import java.util.Arrays;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import akka.japi.Pair;

public class GridGenerator {

    private static final int BLOCKS_PER_CHUNK = 65536;

    public static Pair<Block[], byte[]> generateGrid(long randSeed) {
        Block[] blocks = new Block[BLOCKS_PER_CHUNK];
        byte[] metadata = new byte[BLOCKS_PER_CHUNK];
        Random rand = new Random(randSeed);

        Arrays.fill(blocks, Blocks.air);

        for (int x = 1; x < 16; x += 2) {
            for (int z = 1; z < 16; z += 2) {
                for (int y = 3; y < 256; y += 6) {
                    int index = (x << 12) | (z << 8) | y;
                    Pair<Block, Byte> p = getRandomBlock(rand);
                    blocks[index] = p.first();
                    metadata[index] = p.second();
                }
            }
        }
        return new Pair(blocks, metadata);
    }

    public static Pair<Block, Byte> getRandomBlock(Random rand) {
        BlockData[] blocks = BlocksRegistry.getBlocks()
            .toArray(new BlockData[] {});
        BlockData data = blocks[rand.nextInt(blocks.length)];
        return new Pair(data.block, data.meta);
    }
}
