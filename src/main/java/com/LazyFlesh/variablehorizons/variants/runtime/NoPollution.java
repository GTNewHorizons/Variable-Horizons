package com.LazyFlesh.variablehorizons.variants.runtime;

import net.minecraftforge.common.MinecraftForge;

import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import gregtech.GTMod;
import gregtech.common.pollution.Pollution;
import gregtech.common.pollution.PollutionConfig;

public class NoPollution extends VariantLoader implements IRuntimeVariant {
    // Close enough to runtime. Requires world restart if done in-world, but hopefully works without instance restart.

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        VariantNames.NO_POLLUTION.hasLoaded = true;

        // turn off overall
        PollutionConfig.pollution = false;
        // turn off for tooltips registration
        PollutionConfig.furnacesPollute = false;
        PollutionConfig.rocketsPollute = false;
        PollutionConfig.railcraftPollutes = false;
        // turn off for pollution event handler
        GTMod.proxy.mPollution = false;

        // deregister so blocks aren't cached to map for lookup for w/e pollution stuff it needs 'em for
        MinecraftForge.EVENT_BUS.unregister(Pollution.standardBlocks);
        MinecraftForge.EVENT_BUS.unregister(Pollution.liquidBlocks);
        MinecraftForge.EVENT_BUS.unregister(Pollution.doublePlants);
        MinecraftForge.EVENT_BUS.unregister(Pollution.crossedSquares);
        MinecraftForge.EVENT_BUS.unregister(Pollution.blockVine);
    }

    @Override
    public void variantRecipes(VariantNames... activeVariants) {
        // none
    }
}
