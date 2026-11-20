package com.LazyFlesh.variablehorizons.variants.runtime;

import net.minecraftforge.common.MinecraftForge;

import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;
import com.gtnewhorizon.gtnhlib.config.ConfigurationManager;

import gregtech.GTMod;
import gregtech.common.pollution.Pollution;
import gregtech.common.pollution.PollutionConfig;

public class NoPollution extends VariantLoader implements IRuntimeVariant {
    // Close enough to runtime. Requires world restart if done in-world, but hopefully works without instance restart.
    // (it does! hurray!)

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        VariantNames.NO_POLLUTION.hasLoaded = true;

        // runtime only, config not saved so the next boot still creates GT's pollution handlers
        // turn off overall
        PollutionConfig.pollution = false;
        // turn off for tooltips registration
        PollutionConfig.furnacesPollute = false;
        PollutionConfig.rocketsPollute = false;
        PollutionConfig.railcraftPollutes = false;
        // turn off for pollution event handler
        GTMod.proxy.mPollution = false;

        // deregister so blocks aren't cached to map for lookup for w/e pollution stuff it needs 'em for
        unregister(Pollution.standardBlocks);
        unregister(Pollution.liquidBlocks);
        unregister(Pollution.doublePlants);
        unregister(Pollution.crossedSquares);
        unregister(Pollution.blockVine);
    }

    @Override
    public void variantRecipes(VariantNames... activeVariants) {
        // none
    }

    @Override
    public void undoVariant(VariantNames... activeVariants) {
        VariantNames.NO_POLLUTION.hasLoaded = false;

        PollutionConfig.pollution = true;
        PollutionConfig.furnacesPollute = true;
        PollutionConfig.rocketsPollute = true;
        PollutionConfig.railcraftPollutes = true;
        GTMod.proxy.mPollution = true;

        // save config as true, the effects are overridden when variant is on
        ConfigurationManager.save(PollutionConfig.class);

        // re-register; requires world restart
        register(Pollution.standardBlocks);
        register(Pollution.liquidBlocks);
        register(Pollution.doublePlants);
        register(Pollution.crossedSquares);
        register(Pollution.blockVine);
    }

    private static void register(Object handler) {
        if (handler != null) {
            MinecraftForge.EVENT_BUS.register(handler);
        }
    }

    private static void unregister(Object handler) {
        if (handler != null) {
            MinecraftForge.EVENT_BUS.unregister(handler);
        }
    }
}
