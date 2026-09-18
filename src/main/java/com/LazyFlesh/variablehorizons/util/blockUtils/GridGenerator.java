package com.LazyFlesh.variablehorizons.util.blockUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.init.Blocks;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;

public class GridGenerator {

    private static final int BLOCKS_PER_CHUNK = 65536;

    // Large odd multipliers for chunk seeding
    private static final long CHUNK_X_MULT = 341873128712L;
    private static final long CHUNK_Z_MULT = 132897987541L;

    public static class TEWrapper {

        public final int index;
        public final int damage;
        public final Block block;

        public TEWrapper(int index, int damage, Block block) {
            this.index = index;
            this.damage = damage;
            this.block = block;
        }
    }

    public static class GridResult {

        public final Block[] blocks;
        public final byte[] metadata;
        public final List<TEWrapper> teData;

        public GridResult(Block[] blocks, byte[] metadata, List<TEWrapper> teData) {
            this.blocks = blocks;
            this.metadata = metadata;
            this.teData = teData;
        }
    }

    public static GridResult generateGrid(long worldSeed, int chunkX, int chunkZ) {
        Block[] blocks = new Block[BLOCKS_PER_CHUNK];
        byte[] metadata = new byte[BLOCKS_PER_CHUNK];
        List<TEWrapper> teData = new ArrayList<>();
        Arrays.fill(blocks, Blocks.air);
        long chunkSeed = worldSeed ^ (chunkX * CHUNK_X_MULT) ^ (chunkZ * CHUNK_Z_MULT);
        List<BlockData> blocksList = BlocksRegistry.getBlocks();
        int blocksListSize = blocksList.size();
        int blockDistance = Math.max(GeneralConfig.skygridDistance + 1, 1);

        int xCoord = getOffset(chunkX, blockDistance);
        int zCoord = getOffset(chunkZ, blockDistance);
        for (int x = xCoord; x < 16; x += blockDistance) {
            for (int z = zCoord; z < 16; z += blockDistance) {
                for (int y = 3; y < 256; y += blockDistance) {
                    int index = (x << 12) | (z << 8) | y;
                    long posSeed = mixSeed(chunkSeed, x, y, z);
                    BlockData data = blocksList.get(boundedIndex(posSeed, blocksListSize));

                    blocks[index] = data.block;
                    metadata[index] = data.chunkMeta;

                    if (data.block instanceof ITileEntityProvider || data.block.hasTileEntity(data.chunkMeta)) {
                        teData.add(new TEWrapper(index, data.itemDamage, data.block));
                    }
                }
            }
        }
        return new GridResult(blocks, metadata, teData);
    }

    private static int getOffset(int chunkCoord, int blockDistance) {
        int coord = chunkCoord * 16;
        int steps = coord / blockDistance;
        if (coord < 0 && coord % blockDistance != 0) {
            steps--;
        }
        int start = steps * blockDistance;
        if (start < coord) {
            start += blockDistance;
        }
        return start - coord;
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
