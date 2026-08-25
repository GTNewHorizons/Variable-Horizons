package com.LazyFlesh.variablehorizons;

import com.LazyFlesh.variablehorizons.util.RecipeRemover;
import com.LazyFlesh.variablehorizons.util.VillagerRecipe;
import com.LazyFlesh.variablehorizons.util.islands.IslandCommands;
import com.LazyFlesh.variablehorizons.util.islands.IslandControl;
import com.LazyFlesh.variablehorizons.util.islands.IslandControlSaveData;
import com.LazyFlesh.variablehorizons.variants.VariantCommands;
import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;
import com.LazyFlesh.variablehorizons.variants.invasive.DimLocked;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.VillagerRegistry;

public class CommonProxy {

    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        VariableHorizons.LOG.info("Variable Horizons, version " + Tags.VERSION);

        // damn it needs to be registered in preinit
        if (VariantNames.activeContains(VariantNames.NO_QUEST_REWARDS.id)) {
            VillagerRegistry.instance()
                .registerVillageTradeHandler(80, new VillagerRecipe());
        }
    }

    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {}

    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {}

    // damn nhcoremod... loads recipes too late. At least its not on server starting.
    public void completeLoad(FMLLoadCompleteEvent event) {
        VariantLoader.loadActiveVariants();

        // Once all variants loaded and added their items to the to-be-removed hashset, run remover
        RecipeRemover.removeRecipesByOutput();
        // Then after the recipes are removed, add the custom recipes.
        // Some chance a custom recipe could be removed. if run before remover.
        VariableHorizons.LOG.info("Added recipes.");
        VariantLoader.loadVariantRecipes();

    }

    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {
        VariableHorizons.LOG.info("Loading commands:");
        VariableHorizons.LOG.info("Loaded VariantCommands.");
        event.registerServerCommand(new VariantCommands());
        if (VariantNames.activeContains(VariantNames.DIMLOCKED.id)) {
            VariableHorizons.LOG.info("Loaded Demon Invasion Blacklist Command.");
            event.registerServerCommand(new DimLocked.DemonInvasionBlacklistCommand());
        }
        if (VariantNames.activeContains(VariantNames.VOID_ISLAND.id)) {
            VariableHorizons.LOG.info("Loaded Island Commands.");
            event.registerServerCommand(new IslandCommands());
            FMLCommonHandler.instance()
                .bus()
                .register(IslandControl.instance);
        }
    }

    public void serverStarted(FMLServerStartedEvent event) {
        IslandControlSaveData.init();

        if (VariantNames.activeContains(VariantNames.INFINITE_POWER.id)) {
            FMLCommonHandler.instance()
                .bus()
                .register(VariantNames.INFINITE_POWER.loaderClass);
        }
    }
}
