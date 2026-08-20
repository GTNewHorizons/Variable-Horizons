package com.LazyFlesh.variablehorizons;

import java.util.Arrays;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import com.LazyFlesh.variablehorizons.variants.VariantLoader;

import WayofTime.alchemicalWizardry.AlchemicalWizardry;
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
        event.registerServerCommand(new CommandBase() {

            @Override
            public String getCommandName() {
                return "dimlocktest";
            }

            @Override
            public String getCommandUsage(ICommandSender sender) {
                return "/dimlocktest";
            }

            @Override
            public void processCommand(ICommandSender sender, String[] args) {
                int[] blacklist = AlchemicalWizardry.demonRitualDimensionBlacklist;
                sender.addChatMessage(new ChatComponentText("Blacklist: " + Arrays.toString(blacklist)));
            }
        });
    }
}
