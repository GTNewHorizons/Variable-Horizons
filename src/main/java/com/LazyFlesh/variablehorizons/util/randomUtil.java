package com.LazyFlesh.variablehorizons.util;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.WeakHashMap;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import org.apache.commons.lang3.StringUtils;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.VariableHorizons;
import com.LazyFlesh.variablehorizons.variants.VariantNames;
import com.LazyFlesh.variablehorizons.variants.invasive.VoidIsland;

public class randomUtil {

    private static final Map<EntityPlayerMP, Long> WARN_TIMES = new WeakHashMap<>();
    private static final boolean VOID_WORLD_ACTIVE = VariantNames.activeContains(VariantNames.VOID_WORLD.id);
    private static final boolean VOID_ISLAND_ACTIVE = VariantNames.activeContains(VariantNames.VOID_ISLAND.id);
    private static final boolean CUSTOM_STARTING_DIM_ACTIVE = VariantNames
        .activeContains(VariantNames.CUSTOM_DIM_START.id);

    public static String getRandomPortalMessage(EntityPlayerMP player, World world) {
        int randomNumber = MathHelper.getRandomIntegerInRange(new Random(), 1, 32);
        if (randomNumber == 32) {
            world.playSoundAtEntity(player, "variablehorizons:event.incorrectBuzzer", 0.5f, 1f);
        }
        return StatCollector.translateToLocal("variants.dimlock.message." + randomNumber);
    }

    public static boolean warningCooldownFinished(EntityPlayerMP player, World world) {
        long currentTime = world.getTotalWorldTime();
        Long lastWarned = WARN_TIMES.get(player);
        if (lastWarned == null || currentTime - lastWarned > 50) {
            WARN_TIMES.put(player, currentTime);
            return true;
        }
        return false;
    }

    public static boolean generateVoidInThisDim(int dimID) {
        if (VOID_WORLD_ACTIVE) {
            return true;
        }
        return voidIslandVoidCheck(dimID);
    }

    public static boolean voidIslandVoidCheck(int dimID) {
        if (VOID_ISLAND_ACTIVE) {
            if (CUSTOM_STARTING_DIM_ACTIVE) {
                return GeneralConfig.startingDimID == dimID;
            }
            return dimID == 0;
        }
        return false;
    }

    public static void generateVoidIsland(ChunkCoordinates spawn, WorldServer world, int dimID) {
        VariableHorizons.LOG.info("Generating Sky Island for dimension: {}", dimID);

        Object[][] island = VoidIsland.getIsland(dimID); // [0][] is offset from player/spawn, [1][] is mapping key,
                                                         // after that is structure
        int structX = spawn.posX + (Integer) island[0][0];
        int structY = spawn.posY + (Integer) island[0][1];
        int structZ = spawn.posZ + (Integer) island[0][2];

        String empty = "";

        HashMap<Character, SimpleEntry<Block, Integer>> blockMap = new HashMap<>();
        char chestKey = '+';
        for (int j = 0; j + 2 < island[1].length; j += 3) { // populate map
            char key = (Character) island[1][j];
            Block block = (Block) island[1][j + 1];
            int meta = (Integer) island[1][j + 2];
            blockMap.put(key, new SimpleEntry<>(block, meta));
            if (block == Blocks.chest) chestKey = key;
        }

        for (int i = 0; i < island[2].length; i++) { // find an example empty string to use for emptyness check
            String s = (String) island[2][i]; // should only need layer 1, all current examples have an empty in it.
            if (StringUtils.isBlank(s)) { // could use this, but it's a for loop, so it's not like its better.
                empty = s;
                break;
            }
        }

        // build structure
        for (int x = 0; x + 2 < island.length; x++) {
            Object[] sliceX = island[x + 2];
            for (int y = 0; y < sliceX.length; y++) {
                String row = (String) sliceX[y];
                if (row.equals(empty)) { // skip empty strings (Thank you, trees, for the amount of air in the bb)
                    continue;
                }

                for (int z = 0; z < row.length(); z++) {
                    char c = row.charAt(z);
                    if (blockMap.containsKey(c)) {
                        SimpleEntry<Block, Integer> entry = blockMap.get(c);
                        // if the char maps to a block, place it
                        world.setBlock(structX + x, structY - y, structZ + z, entry.getKey(), entry.getValue(), 3);

                        if (c == chestKey) { // chest loot
                            if (world.getTileEntity(structX + x, structY - y, structZ + z) == null) {
                                world.setTileEntity(structX + x, structY - y, structZ + z, new TileEntityChest());
                            }
                            TileEntityChest chest = (TileEntityChest) world
                                .getTileEntity(structX + x, structY - y, structZ + z);
                            ItemStack[] loot = VoidIsland.getChestLoot(dimID);
                            for (int j = 0; j < loot.length; j++) {
                                chest.setInventorySlotContents(j, loot[j]);
                            }
                        }
                    }
                }
            }
        }

    }
}
