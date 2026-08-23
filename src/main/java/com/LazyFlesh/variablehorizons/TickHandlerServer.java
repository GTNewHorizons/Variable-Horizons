package com.LazyFlesh.variablehorizons;

import net.minecraft.server.MinecraftServer;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class TickHandlerServer {

    public static TickHandlerServer instance = new TickHandlerServer();

    public TickHandlerServer() {
        instance = this;
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        final MinecraftServer server = FMLCommonHandler.instance()
            .getMinecraftServerInstance();
        // Prevent issues when clients switch to LAN servers
        if (server == null) {
            return;
        }
    }
}
