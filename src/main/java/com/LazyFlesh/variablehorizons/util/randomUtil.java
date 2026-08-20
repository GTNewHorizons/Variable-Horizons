package com.LazyFlesh.variablehorizons.util;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;

public class randomUtil {

    private static final java.util.Map<EntityPlayerMP, Long> WARN_TIMES = new java.util.WeakHashMap<>();

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

    public static boolean shouldCancelDimensionChange(EntityPlayerMP player, int targetDim) {
        boolean cancel;

        switch (GeneralConfig.dimensionRestrictionMode) {

            case 1:
                cancel = targetDim != player.worldObj.provider.dimensionId;
                break;

            case 2:
                cancel = isGalaxyDimension(targetDim);
                break;

            default:
                cancel = false;
                break;
        }

        if (cancel && randomUtil.warningCooldownFinished(player, player.worldObj)) {
            player.addChatMessage(
                new net.minecraft.util.ChatComponentText(randomUtil.getRandomPortalMessage(player, player.worldObj)));
        }
        return cancel;
    }

    private static boolean isGalaxyDimension(int dim) {
        return GALAXY_DIM_IDS.contains(dim);
    }

    private static final Set<Integer> GALAXY_DIM_IDS = new HashSet<>();
    static {
        GALAXY_DIM_IDS.add(25);
        for (int i = 28; i <= 33; i++) GALAXY_DIM_IDS.add(i);
        for (int i = 35; i <= 49; i++) GALAXY_DIM_IDS.add(i);
        GALAXY_DIM_IDS.add(51);
        GALAXY_DIM_IDS.add(63);
        GALAXY_DIM_IDS.add(64);
        GALAXY_DIM_IDS.add(71);
        GALAXY_DIM_IDS.add(74);
        GALAXY_DIM_IDS.add(77);
        for (int i = 81; i <= 95; i++) GALAXY_DIM_IDS.add(i);
    }
}
