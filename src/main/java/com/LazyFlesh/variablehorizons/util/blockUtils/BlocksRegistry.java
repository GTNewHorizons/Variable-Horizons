package com.LazyFlesh.variablehorizons.util.blockUtils;

import java.util.ArrayList;
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

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.VariableHorizons;
import com.LazyFlesh.variablehorizons.util.randomUtil;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import akka.japi.Pair;
import cpw.mods.fml.common.registry.FMLControlledNamespacedRegistry;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlocksRegistry {

    private static List<BlockData> blocks;
    private static final Map<String, Set<Integer>> BLACKLIST = new HashMap<>();
    private static final List<Pair<Block, Integer>> TE_WHITELIST = randomUtil.getBlocksFromConfig(VariantNames.SKYGRID);

    static {
        // Meta -1 affects all metadata variants for a block
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
        blacklistBlock("ExtraUtilities:chandelier", 0);
        blacklistBlock("ExtraUtilities:magnumTorch", 0);
        blacklistBlock("DraconicEvolution:placedItem", -1);
        blacklistBlock("EMT:electricCloud", -1);
        blacklistBlock("BiblioCraft:BiblioSeats", -1);
        blacklistBlock("BiblioWoodsBoP:BiblioWoodSeat", -1);
        blacklistBlock("BiblioWoodsForestry:BiblioWoodSeat", -1);
        blacklistBlock("BiblioWoodsForestry:BiblioWoodSeat2", -1);
        blacklistBlock("BiblioWoodsNatura:BiblioWoodSeat", -1);
        blacklistBlock("kubatech:kubablocks", 0, 1);
        blacklistBlock("OpenComputers:print", 0);
        blacklistBlock("OpenComputers:printer", 0);
        blacklistBlock("ae2fc:walrus", 0);
        blacklistBlock("EnderIO:blockHyperCube", 0);
        blacklistBlock("ForgeMicroblock:microblock", -1);
        blacklistBlock("IC2:blockDynamite", -1);
        blacklistBlock("IC2:blockDynamiteRemote", -1);

        blacklistConfig();
    }

    private static void blacklistBlock(String blockID, int... metas) {
        Set<Integer> metaSet = BLACKLIST.computeIfAbsent(blockID, k -> new HashSet<>());
        for (int m : metas) {
            metaSet.add(m);
        }
    }

    private static void blacklistConfig() {
        String[] blacklisted = GeneralConfig.skygridBlacklist;
        for (String blockString : blacklisted) {
            String[] split = blockString.split(":");
            if (split.length < 2) {
                VariableHorizons.LOG.info(
                    "Invalid Skygrid blacklist config value '{}' (expected format 'modid:blockname[:meta]'), ignoring this entry",
                    blockString);
                continue;
            }
            if (split.length == 2) {
                VariableHorizons.LOG
                    .info("Skygrid blacklist entry '{}:{}' has no meta value, falling back to 0", split[0], split[1]);
                blacklistBlock(split[0] + ":" + split[1], 0);
                continue;
            }
            int meta;
            try {
                meta = Integer.parseInt(split[2]);
            } catch (NumberFormatException e) {
                VariableHorizons.LOG
                    .info("Skygrid blacklist entry meta value '{}' is not a valid number, falling back to 0", split[2]);
                blacklistBlock(split[0] + ":" + split[1], 0);
                continue;
            }
            blacklistBlock(split[0] + ":" + split[1], meta);
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
                blocks.add(new BlockData((byte) 0, 0, uniqueId, block));
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
                blocks.add(new BlockData((byte) 0, 0, uniqueId, block));
                continue;
            }

            for (ItemStack stack : subItems) {
                if (stack == null) continue;
                int meta = stack.getItemDamage();
                if (meta < 0 || meta > 15) continue;
                processBlock(block, uniqueId, meta);
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

    private static boolean checkIfWhitelisted(Block block, int meta) {
        if (TE_WHITELIST.contains(new Pair<>(block, meta))) {
            return true;
        }
        return TE_WHITELIST.contains(new Pair<>(block, -1));
    }

    private static void addWhitelistedTEs() {
        for (Pair<Block, Integer> entry : TE_WHITELIST) {
            Block block = entry.first();
            if (block == null) {
                continue;
            }
            String blockID = String.valueOf(GameRegistry.findUniqueIdentifierFor(block));
            int entryMeta = entry.second();
            if (entryMeta == -1) {
                // Wildcard meta of -1, add all variations
                Item blockItem = Item.getItemFromBlock(block);
                if (blockItem != null) {
                    List<ItemStack> subItems = new ArrayList<>();
                    try {
                        blockItem.getSubItems(blockItem, block.getCreativeTabToDisplayOn(), subItems);
                    } catch (Exception ignored) {}

                    if (subItems.isEmpty()) {
                        blocks.add(new BlockData((byte) 0, 0, blockID, block));
                    } else {
                        for (ItemStack stack : subItems) {
                            if (stack != null) {
                                int meta = stack.getItemDamage();
                                if (meta >= 0) {
                                    byte chunkMeta = (byte) (meta > 15 ? 0 : meta);
                                    blocks.add(new BlockData(chunkMeta, meta, blockID, block));
                                }
                            }
                        }
                    }
                } else {
                    blocks.add(new BlockData((byte) 0, 0, blockID, block));
                }
            } else {
                // Explicitly chosen meta
                if (entryMeta >= 0) {
                    byte chunkMeta = (byte) (entryMeta > 15 ? 0 : entryMeta);
                    blocks.add(new BlockData(chunkMeta, entryMeta, blockID, block));
                }
            }
        }
    }

    private static void processBlock(Block block, String uniqueId, int meta) {
        if (checkIfBlacklisted(uniqueId, meta)) return;

        boolean hasTE = block instanceof ITileEntityProvider || block.hasTileEntity(meta > 15 ? 0 : meta);

        if (hasTE && !checkIfWhitelisted(block, meta)) return;
        byte chunkMeta = (byte) (meta > 15 ? 0 : meta);
        blocks.add(new BlockData(chunkMeta, meta, uniqueId, block));
    }
}
