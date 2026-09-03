package com.LazyFlesh.variablehorizons.Config;

import com.LazyFlesh.variablehorizons.VariableHorizons;
import com.gtnewhorizon.gtnhlib.config.Config;

@Config.RequiresMcRestart
@Config(
    modid = VariableHorizons.MODID,
    configSubDirectory = "VariableHorizons",
    filename = "GeneralConfig",
    category = "General")
public class GeneralConfig {

    @Config.Comment("Disables all changes made by Variable Horizons.")
    @Config.DefaultBoolean(false)
    public static boolean disableVariants;

    @Config.Comment("List of all active Variants' names. You can use in-game commands to change it, as well as see all options.")
    @Config.DefaultStringList({ "NORMAL" })
    public static String[] activeVariants;

    @Config.Comment("ID of chosen starting dimension")
    @Config.DefaultInt(0)
    public static int startingDimID;

    @Config.Comment("Global recipe time multiplier")
    @Config.DefaultFloat(1f)
    public static float recipeTimeMultiplier;

    @Config.Comment("Global machine efficiency multiplier")
    @Config.DefaultFloat(1f)
    public static float efficiencyMultiplier;

    @Config.Comment("Should Superflat allow all structures and foliage to generate (villages are always generated)")
    @Config.DefaultBoolean(false)
    public static boolean allowSuperflatPopulation;

    @Config.Comment("Should Superflat overwold generate biomes other than plains")
    @Config.DefaultBoolean(false)
    public static boolean allowSuperflatBiomes;

    @Config.Comment("Should Void Islands spawn with a tree")
    @Config.DefaultBoolean(true)
    public static boolean allowVoidIslandTree;

    @Config.Comment("Should Void Islands spawn with a starting chest")
    @Config.DefaultBoolean(true)
    public static boolean allowVoidIslandChest;

    @Config.Comment("Block type the entire world should be made of")
    @Config.DefaultString("minecraft:stone:0")
    public static String replacementBlock;

}
