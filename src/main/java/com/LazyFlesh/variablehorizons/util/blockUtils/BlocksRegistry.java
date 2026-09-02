package com.LazyFlesh.variablehorizons.util.blockUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.registry.FMLControlledNamespacedRegistry;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlocksRegistry {

    private static List<BlockData> blocks;

    public static List<BlockData> getBlocks() {
        if (blocks != null) return blocks;
        blocks = new ArrayList<>();

        FMLControlledNamespacedRegistry<Block> blocksDiscovered = GameData.getBlockRegistry();
        Iterator<Block> blockIterator = blocksDiscovered.iterator();

        while (blockIterator.hasNext()) {
            Block block = blockIterator.next();

            Item blockItem = Item.getItemFromBlock(block);
            byte meta = 0;
            if (blockItem != null) {
                try {
                    meta = (byte) blockItem.getDamage(new ItemStack(blockItem));
                } catch (Exception e) {
                    meta = 0;
                }
            }

            blocks.add(new BlockData(meta, String.valueOf(GameRegistry.findUniqueIdentifierFor(block)), block, null));
        }

        return blocks;
    }
}
