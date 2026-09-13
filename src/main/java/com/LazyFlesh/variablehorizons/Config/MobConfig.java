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

}
