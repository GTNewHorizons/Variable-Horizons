package com.LazyFlesh.variablehorizons;

import com.LazyFlesh.variablehorizons.util.islands.IslandControl;
import com.LazyFlesh.variablehorizons.variants.DemonInvasionBlacklistCommand;
import com.LazyFlesh.variablehorizons.variants.VariantCommands;
import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;
import com.LazyFlesh.variablehorizons.variants.invasive.VoidIsland;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

public class CommonProxy {

    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        VariableHorizons.LOG.info("Variable Horizons, version " + Tags.VERSION);
    }

    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {}

    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {}

    // damn nhcoremod...
    public void completeLoad(FMLLoadCompleteEvent event) {
        VariantLoader.loadActiveVariants();
    }

    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new VariantCommands());
        event.registerServerCommand(new DemonInvasionBlacklistCommand());
        if (VariantNames.activeContains(VariantNames.VOID_ISLAND.id))
            event.registerServerCommand(new VoidIsland.IslandCommands());

        FMLCommonHandler.instance()
            .bus()
            .register(TickHandlerServer.instance);
        FMLCommonHandler.instance()
            .bus()
            .register(IslandControl.instance);
    }
}
