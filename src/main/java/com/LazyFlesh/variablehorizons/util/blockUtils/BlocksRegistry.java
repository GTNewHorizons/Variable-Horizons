package com.LazyFlesh.variablehorizons.util.blockUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.IFluidBlock;

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

            // No fluids or TEs
            if (block instanceof ITileEntityProvider || block instanceof IFluidBlock) {
                continue;
            }

            Item blockItem = Item.getItemFromBlock(block);
            String uniqueId = String.valueOf(GameRegistry.findUniqueIdentifierFor(block));

            if (blockItem == null) {
                // Item not found, use meta 0
                blocks.add(new BlockData((byte) 0, uniqueId, block, null));
                continue;
            }

            List<ItemStack> subItems = new ArrayList<>();
            try {
                // Null for CreativeTabs param to attempt getting all variants always
                blockItem.getSubItems(blockItem, null, subItems);
            } catch (Exception e) {
                subItems.clear();
            }

            if (subItems.isEmpty()) {
                // Only one block variant (meta 0)
                blocks.add(new BlockData((byte) 0, uniqueId, block, null));
                continue;
            }

            for (ItemStack stack : subItems) {
                if (stack == null) continue;
                int meta = stack.getItemDamage();
                if (meta < 0 || meta > 15) continue;
                blocks.add(new BlockData((byte) meta, uniqueId, block, null));
            }
        }

        return blocks;
    }
}
