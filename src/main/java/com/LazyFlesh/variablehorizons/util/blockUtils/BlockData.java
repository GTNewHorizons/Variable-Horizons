package com.LazyFlesh.variablehorizons.util.blockUtils;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class BlockData {

    Block block;
    String uID;
    Byte meta;
    List<ItemStack> subBlocks;

    public BlockData(Byte meta, String uID, Block block, List<ItemStack> subBlocks) {
        this.meta = meta;
        this.uID = uID;
        this.block = block;
        this.subBlocks = subBlocks;
    }
}
