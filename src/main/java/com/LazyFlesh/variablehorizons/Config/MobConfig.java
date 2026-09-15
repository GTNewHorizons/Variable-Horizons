package com.LazyFlesh.variablehorizons.Config;

import com.LazyFlesh.variablehorizons.VariableHorizons;
import com.gtnewhorizon.gtnhlib.config.Config;

@Config(
    modid = VariableHorizons.MODID,
    configSubDirectory = "VariableHorizons",
    filename = "MobConfig",
    category = "Mobs")
public class MobConfig {

    @Config.Comment("Global mob health multiplier")
    @Config.DefaultDouble(1d)
    public static double mobHealthMultiplier;

    @Config.Comment("Global mob melee damage multiplier")
    @Config.DefaultDouble(1d)
    public static double mobMeleeMultiplier;

    @Config.Comment("Blood Moon mobcap multiplier")
    @Config.DefaultInt(3)
    @Config.RangeInt(min = 1)
    public static int bloodMoonMobcapMultiplier;

    @Config.Comment("Should all lighting have a red tint during a Blood Moon")
    @Config.DefaultBoolean(true)
    public static boolean bloodMoonTint;

    @Config.Comment("Global infernal mob difficulty level")
    @Config.DefaultInt(1)
    @Config.RangeInt(min = 0, max = 3)
    public static int infernalMobDifficulty;
}
