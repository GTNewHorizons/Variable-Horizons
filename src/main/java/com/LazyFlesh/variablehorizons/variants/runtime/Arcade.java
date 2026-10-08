package com.LazyFlesh.variablehorizons.variants.runtime;

import static detrav.enums.IDDetraveMetaGeneratedTool01.ElectricProspectorScannerUHV;
import static gregtech.api.enums.Mods.AE2FluidCraft;
import static gregtech.api.enums.Mods.AppliedEnergistics2;
import static gregtech.api.enums.Mods.StorageDrawers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import detrav.items.DetravMetaGeneratedTool01;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.util.GTModHandler;
import gregtech.common.items.IDMetaTool01;
import gregtech.common.items.MetaGeneratedTool01;

public class Arcade extends VariantLoader implements IRuntimeVariant {

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        VariantNames.ARCADE.hasLoaded = true;
    }

    @Override
    public void variantRecipes(VariantNames... activeVariants) {}

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!VariantNames.ARCADE.hasLoaded) {
            return;
        }
        DefaultItems.giveItems(event.player);
    }

    @Override
    public void undoVariant(VariantNames... activeVariants) {
        VariantNames.ARCADE.hasLoaded = false;
    }

    private static final class DefaultItems {

        private static final String FIRST_JOIN_NBT_KEY = "variablehorizons.arcade.firstjoin";

        static void giveItems(EntityPlayer player) {
            NBTTagCompound data = player.getEntityData();
            NBTTagCompound persistent;
            if (!data.hasKey(EntityPlayer.PERSISTED_NBT_TAG)) {
                data.setTag(EntityPlayer.PERSISTED_NBT_TAG, (persistent = new NBTTagCompound()));
            } else {
                persistent = data.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
            }

            if (!persistent.hasKey(FIRST_JOIN_NBT_KEY)) {
                persistent.setBoolean(FIRST_JOIN_NBT_KEY, true);

                player.inventory.addItemStackToInventory(
                    MetaGeneratedTool01.INSTANCE.getToolWithStats(
                        IDMetaTool01.DRILL_HV.ID,
                        1,
                        Materials.SpaceTime,
                        Materials.StainlessSteel,
                        null));
                player.inventory.addItemStackToInventory(
                    DetravMetaGeneratedTool01.INSTANCE.getToolWithStats(
                        ElectricProspectorScannerUHV.ID,
                        1,
                        Materials.SpaceTime,
                        Materials.Infinity,
                        null));
                player.inventory
                    .addItemStackToInventory(GTModHandler.getModItem(StorageDrawers.ID, "upgradeCreative", 64, 1));
                player.inventory
                    .addItemStackToInventory(GTModHandler.getModItem(StorageDrawers.ID, "fullDrawers1", 64, 0));
                player.inventory.addItemStackToInventory(ItemList.Debug_Fluid_Tank.get(1));
                player.inventory.addItemStackToInventory(
                    GTModHandler.getModItem(AppliedEnergistics2.ID, "item.ItemCreativeStorageCell", 1, 0));
                player.inventory
                    .addItemStackToInventory(GTModHandler.getModItem(AE2FluidCraft.ID, "creative_fluid_storage", 1, 0));
            }
        }
    }
}
