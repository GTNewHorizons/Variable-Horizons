package com.LazyFlesh.variablehorizons;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import com.LazyFlesh.variablehorizons.util.islands.IslandControlSaveData;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

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

        if (VariantNames.activeContains(VariantNames.VOID_ISLAND.id)) {
            if (event.phase == TickEvent.Phase.START) {
                final World world = FMLCommonHandler.instance()
                    .getMinecraftServerInstance()
                    .worldServerForDimension(0);
                IslandControlSaveData data = (IslandControlSaveData) world.mapStorage
                    .loadData(IslandControlSaveData.class, IslandControlSaveData.saveDataID);
                if (data == null) {
                    data = new IslandControlSaveData(IslandControlSaveData.saveDataID);
                    world.mapStorage.setData(IslandControlSaveData.saveDataID, data);
                    data.markDirty();
                }
            }
        }
    }
}
