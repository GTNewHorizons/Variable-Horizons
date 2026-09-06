package com.LazyFlesh.variablehorizons.util.blockUtils;

import net.minecraft.block.Block;

public class BlockData {

    byte chunkMeta;
    int itemDamage; // Holds GT IDs up to 32000
    String uniqueId;
    Block block;

    public BlockData(byte chunkMeta, int itemDamage, String uniqueId, Block block) {
        this.chunkMeta = chunkMeta;
        this.itemDamage = itemDamage;
        this.uniqueId = uniqueId;
        this.block = block;
    }
}
