package com.LazyFlesh.variablehorizons.util.blockUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    private static final Map<String, Set<Integer>> BLACKLIST = new HashMap<>();

    static {
        // Meta -1 blacklists all metadata variants for a block
        blacklistBlock("tectech:Eye of Harmony Renderer", 0);
        blacklistBlock("tectech:ForgeOfGodsRenderBlock", 0);
        blacklistBlock("gregtech:gt.nanoforgerenderer", 0);
        blacklistBlock("gregtech:gt.blackholerenderer", 0);
        blacklistBlock("gregtech:gt.wormholerenderer", 0);
        blacklistBlock("GoodGenerator:antimatterRenderBlock", 0);
        blacklistBlock("HardcoreEnderExpansion:corrupted_energy_high", 0);
        blacklistBlock("HardcoreEnderExpansion:corrupted_energy_low", 0);
        blacklistBlock("OpenBlocks:tank", 0);
        blacklistBlock("ExtraUtilities:drum", 0, 1);
    }

    private static void blacklistBlock(String blockID, int... metas) {
        Set<Integer> metaSet = BLACKLIST.computeIfAbsent(blockID, k -> new HashSet<>());
        for (int m : metas) {
            metaSet.add(m);
        }
    }

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
                if (checkIfBlacklisted(uniqueId, 0)) {
                    continue;
                }
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
                if (checkIfBlacklisted(uniqueId, 0)) {
                    continue;
                }
                // Only one block variant (meta 0)
                blocks.add(new BlockData((byte) 0, uniqueId, block, null));
                continue;
            }

            for (ItemStack stack : subItems) {
                if (stack == null) continue;
                int meta = stack.getItemDamage();
                if (meta < 0 || meta > 15) continue;
                if (checkIfBlacklisted(uniqueId, meta)) {
                    continue;
                }
                blocks.add(new BlockData((byte) meta, uniqueId, block, null));
            }
        }

        return blocks;
    }

    private static boolean checkIfBlacklisted(String blockID, int meta) {
        if (BLACKLIST.containsKey(blockID)) {
            Set<Integer> blacklistedMetas = BLACKLIST.get(blockID);
            return (blacklistedMetas.contains(meta) || blacklistedMetas.contains(-1));
        }
        return false;
    }
}
