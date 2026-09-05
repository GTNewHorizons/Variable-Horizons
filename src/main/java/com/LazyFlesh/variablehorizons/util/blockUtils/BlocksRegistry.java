package com.LazyFlesh.variablehorizons.util.blockUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
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
    private static final Map<String, Set<Integer>> TE_WHITELIST = new HashMap<>();

    static {
        // Meta -1 affects all metadata variants for a block
        addBlockToList(BLACKLIST, "tectech:Eye of Harmony Renderer", 0);
        addBlockToList(BLACKLIST, "tectech:ForgeOfGodsRenderBlock", 0);
        addBlockToList(BLACKLIST, "gregtech:gt.nanoforgerenderer", 0);
        addBlockToList(BLACKLIST, "gregtech:gt.blackholerenderer", 0);
        addBlockToList(BLACKLIST, "gregtech:gt.wormholerenderer", 0);
        addBlockToList(BLACKLIST, "GoodGenerator:antimatterRenderBlock", 0);
        addBlockToList(BLACKLIST, "HardcoreEnderExpansion:corrupted_energy_high", 0);
        addBlockToList(BLACKLIST, "HardcoreEnderExpansion:corrupted_energy_low", 0);
        addBlockToList(BLACKLIST, "OpenBlocks:tank", 0);
        addBlockToList(BLACKLIST, "ExtraUtilities:drum", 0, 1);
        addBlockToList(BLACKLIST, "ExtraUtilities:chandelier", 0);
        addBlockToList(BLACKLIST, "ExtraUtilities:magnumTorch", 0);
        addBlockToList(BLACKLIST, "DraconicEvolution:placedItem", -1);
        addBlockToList(BLACKLIST, "EMT:electricCloud", -1);
        addBlockToList(BLACKLIST, "BiblioCraft:BiblioSeats", -1);
        addBlockToList(BLACKLIST, "BiblioWoodsBoP:BiblioWoodSeat", -1);
        addBlockToList(BLACKLIST, "BiblioWoodsForestry:BiblioWoodSeat", -1);
        addBlockToList(BLACKLIST, "BiblioWoodsForestry:BiblioWoodSeat2", -1);
        addBlockToList(BLACKLIST, "BiblioWoodsNatura:BiblioWoodSeat", -1);
        addBlockToList(BLACKLIST, "kubatech:kubablocks", 0, 1);
        addBlockToList(BLACKLIST, "OpenComputers:print", 0);
        addBlockToList(BLACKLIST, "OpenComputers:printer", 0);
        addBlockToList(BLACKLIST, "ae2fc:walrus", 0);
        addBlockToList(BLACKLIST, "EnderIO:blockHyperCube", 0);
        addBlockToList(TE_WHITELIST, "gregtech:gt.blockmachines", getGTSingleblockMetas());
        addBlockToList(BLACKLIST, "ForgeMicroblock:microblock", -1);
    }

    private static void addBlockToList(Map<String, Set<Integer>> filter, String blockID, int... metas) {
        Set<Integer> metaSet = filter.computeIfAbsent(blockID, k -> new HashSet<>());
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
            String uniqueId = String.valueOf(GameRegistry.findUniqueIdentifierFor(block));

            // No TEs
            if (block instanceof ITileEntityProvider) {
                continue;
            }
            // No fluids
            if (block instanceof BlockLiquid || block instanceof IFluidBlock) {
                continue;
            }

            Item blockItem = Item.getItemFromBlock(block);
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

        addWhitelistedTEs();
        return blocks;
    }

    private static boolean checkIfBlacklisted(String blockID, int meta) {
        if (BLACKLIST.containsKey(blockID)) {
            Set<Integer> blacklistedMetas = BLACKLIST.get(blockID);
            return (blacklistedMetas.contains(meta) || blacklistedMetas.contains(-1));
        }
        return false;
    }

    private static void addWhitelistedTEs() {
        for (Map.Entry<String, Set<Integer>> entry : TE_WHITELIST.entrySet()) {
            String blockID = entry.getKey();
            Block block = Block.getBlockFromName(blockID);

            if (block == null) {
                continue;
            }

            Set<Integer> metas = entry.getValue();
            if (metas.contains(-1)) {
                // Wildcard meta of -1, add all variations
                Item blockItem = Item.getItemFromBlock(block);
                if (blockItem != null) {
                    List<ItemStack> subItems = new ArrayList<>();
                    try {
                        blockItem.getSubItems(blockItem, block.getCreativeTabToDisplayOn(), subItems);
                    } catch (Exception ignored) {}

                    if (subItems.isEmpty()) {
                        blocks.add(new BlockData((byte) 0, blockID, block, null));
                    } else {
                        for (ItemStack stack : subItems) {
                            if (stack != null) {
                                int meta = stack.getItemDamage();
                                if (meta >= 0) {
                                    blocks.add(new BlockData((byte) meta, blockID, block, null));
                                }
                            }
                        }
                    }
                } else {
                    blocks.add(new BlockData((byte) 0, blockID, block, null));
                }
            } else {
                // Explicitly chosen metas
                for (int meta : metas) {
                    if (meta >= 0) {
                        blocks.add(new BlockData((byte) meta, blockID, block, null));
                    }
                }
            }
        }
    }

    private static int[] getGTSingleblockMetas() {
        ArrayList<Integer> metas = new ArrayList<>();
        int IDOffsetBasicSingleblocks = 200;
        Set<Integer> skippedIDs = new HashSet<>(Arrays.asList(22, 41, 44));
        for (int i = 0; i < 46; i++) {
            if (skippedIDs.contains(i)) {
                continue;
            }
            for (int j = 1; j < 6; j++) {
                metas.add(IDOffsetBasicSingleblocks + j);
            }
        }
        return metas.stream()
            .mapToInt(i -> i)
            .toArray();
    }
}
