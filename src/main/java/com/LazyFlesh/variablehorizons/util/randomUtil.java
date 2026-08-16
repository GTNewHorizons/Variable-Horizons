package com.LazyFlesh.variablehorizons.util;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class randomUtil {

    private static final java.util.Map<EntityPlayerMP, Long> WARN_TIMES = new java.util.WeakHashMap<>();

    public static String getRandomPortalMessage() {
        int randomNumber = MathHelper.getRandomIntegerInRange(new Random(), 1, 20);
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
}
