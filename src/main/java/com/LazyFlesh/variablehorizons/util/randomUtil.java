package com.LazyFlesh.variablehorizons.util;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

public class randomUtil {

    private static final java.util.Map<EntityPlayerMP, Long> WARN_TIMES = new java.util.WeakHashMap<>();
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
        if (VOID_ISLAND_ACTIVE) {
            if (CUSTOM_STARTING_DIM_ACTIVE) {
                return GeneralConfig.startingDimID == dimID;
            }
            return dimID == 0;
        }
        return false;
    }
}
