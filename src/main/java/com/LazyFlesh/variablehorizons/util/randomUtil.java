package com.LazyFlesh.variablehorizons.util;

import java.util.HashMap;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.variants.VariantNames;
import com.LazyFlesh.variablehorizons.variants.invasive.VoidIsland;

public class randomUtil {

    private static final java.util.Map<EntityPlayerMP, Long> WARN_TIMES = new java.util.WeakHashMap<>();
    private static final boolean VOID_WORLD_ACTIVE = VariantNames.activeContains(VariantNames.VOID_WORLD.id);
    private static final boolean VOID_ISLAND_ACTIVE = VariantNames.activeContains(VariantNames.VOID_ISLAND.id);
    private static final boolean CUSTOM_STARTING_DIM_ACTIVE = VariantNames
        .activeContains(VariantNames.CUSTOM_DIM_START.id);

    public static Object[] chestLoad = null;

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
        Object[][] island = VoidIsland.getIsland(dimID); // [0][] is offset from player/spawn, [1][] is mapping key,
                                                         // after that is structure
        int structX = spawn.posX + (Integer) island[0][0];
        int structY = spawn.posY + (Integer) island[0][1];
        int structZ = spawn.posZ + (Integer) island[0][2];

        HashMap<Character, Block> blockMap = new HashMap<>();
        char chestKey = '+';
        for (int j = 0; j + 1 < island[1].length; j += 2) { // populate map
            blockMap.put((Character) island[1][j], (Block) island[1][j + 1]);
            if (island[1][j + 1] == Blocks.chest) chestKey = (Character) island[1][j];
        }

        // build structure
        for (int x = 0; x + 2 < island.length; x++) {
            Object[] sliceX = island[x + 2];
            for (int y = 0; y < sliceX.length; y++) {
                String row = (String) sliceX[y];
                for (int z = 0; z < row.length(); z++) {
                    if (blockMap.containsKey(row.charAt(z))) {
                        // if the char maps to a block, place it
                        world.setBlock(structX + x, structY - y, structZ + z, blockMap.get(row.charAt(z)));

                        if (row.charAt(z) == chestKey) {
                            // cache chest location for later due to mixin conflict causing crash
                            randomUtil.chestLoad = new Object[] { world, structX + x, structY - y, structZ + z, dimID };
                        }
                    }
                }
            }
        }

    }
}
