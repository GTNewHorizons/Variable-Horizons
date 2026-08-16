package com.LazyFlesh.variablehorizons.util;

import java.util.Random;

import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;

public class randomUtil {

    public static String getRandomPortalMessage() {
        int randomNumber = MathHelper.getRandomIntegerInRange(new Random(), 1, 20);
        return StatCollector.translateToLocal("variants.dimlock.message." + randomNumber);
    }

}
