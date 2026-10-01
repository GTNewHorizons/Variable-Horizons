package com.LazyFlesh.variablehorizons.variants.runtime;

import static gregtech.api.recipe.RecipeMaps.assemblerRecipes;
import static gregtech.api.util.GTRecipeBuilder.QUARTER_INGOTS;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import bartworks.system.material.WerkstoffLoader;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.objects.SubstituteFluidStack;
import gregtech.api.util.GTOreDictUnificator;
import tectech.thing.CustomItemList;

public class Wireless extends VariantLoader implements IRuntimeVariant {

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        VariantNames.WIRELESS.hasLoaded = true;
    }

    @Override
    public void variantRecipes(VariantNames... activeVariants) {
        // recipes for each wireless hatch, 1 to 1 conversion from normal e hatch
        // skip adding dynamo to wireless dynamo, since pointless

        // skip ulv and max
        for (int i = 1; i < ItemList.WIRELESS_ENERGY_HATCHES.length - 1; i++) {
            CraftingManager.getInstance()
                .addShapelessRecipe(ItemList.WIRELESS_ENERGY_HATCHES[i].get(1), ItemList.HATCHES_ENERGY[i].get(1));
            CraftingManager.getInstance()
                .addShapelessRecipe(ItemList.HATCHES_ENERGY[i].get(1), ItemList.WIRELESS_ENERGY_HATCHES[i].get(1));
        }

        Materials[] plateMat = new Materials[] { Materials.Iron, Materials.Aluminium, Materials.StainlessSteel,
            Materials.Titanium, Materials.TungstenSteel, WerkstoffLoader.RhodiumPlatedPalladium.getGTMaterial(),
            Materials.Iridium, Materials.Osmium, Materials.Neutronium, Materials.Infinity, Materials.TranscendentMetal,
            Materials.SpaceTime, Materials.MHDCSM };

        // skip MAX
        for (int i = 0; i < ItemList.WIRELESS_ENERGY_COVERS.length - 1; i++) {
            GTValues.RA.stdBuilder()
                .itemInputs(
                    ItemList.WIRELESS_ENERGY_HATCHES[i + 1].get(1),
                    GTOreDictUnificator.get(OrePrefixes.plate, plateMat[i], 4L))
                .itemOutputs(ItemList.WIRELESS_ENERGY_COVERS[i].get(1))
                .fluidInputs(SubstituteFluidStack.soldering(QUARTER_INGOTS))
                .duration(5 * SECONDS)
                .eut(GTValues.VP[i])
                .addTo(assemblerRecipes);
        }

        ItemStack[] energyHatches_4A = new ItemStack[] { CustomItemList.eM_energyMulti4_EV.get(1L),
            CustomItemList.eM_energyMulti4_IV.get(1L), CustomItemList.eM_energyMulti4_LuV.get(1L),
            CustomItemList.eM_energyMulti4_ZPM.get(1L), CustomItemList.eM_energyMulti4_UV.get(1L),
            CustomItemList.eM_energyMulti4_UHV.get(1L), CustomItemList.eM_energyMulti4_UEV.get(1L),
            CustomItemList.eM_energyMulti4_UIV.get(1L), CustomItemList.eM_energyMulti4_UMV.get(1L),
            CustomItemList.eM_energyMulti4_UXV.get(1L) };

        ItemStack[] wirelessHatches_4A = new ItemStack[] { CustomItemList.eM_energyWirelessMulti4_EV.get(1L),
            CustomItemList.eM_energyWirelessMulti4_IV.get(1L), CustomItemList.eM_energyWirelessMulti4_LuV.get(1L),
            CustomItemList.eM_energyWirelessMulti4_ZPM.get(1L), CustomItemList.eM_energyWirelessMulti4_UV.get(1L),
            CustomItemList.eM_energyWirelessMulti4_UHV.get(1L), CustomItemList.eM_energyWirelessMulti4_UEV.get(1L),
            CustomItemList.eM_energyWirelessMulti4_UIV.get(1L), CustomItemList.eM_energyWirelessMulti4_UMV.get(1L),
            CustomItemList.eM_energyWirelessMulti4_UXV.get(1L) };

        ItemStack[] energyHatches_16A = { CustomItemList.eM_energyMulti16_EV.get(1),
            CustomItemList.eM_energyMulti16_IV.get(1), CustomItemList.eM_energyMulti16_LuV.get(1),
            CustomItemList.eM_energyMulti16_ZPM.get(1), CustomItemList.eM_energyMulti16_UV.get(1),
            CustomItemList.eM_energyMulti16_UHV.get(1), CustomItemList.eM_energyMulti16_UEV.get(1),
            CustomItemList.eM_energyMulti16_UIV.get(1), CustomItemList.eM_energyMulti16_UMV.get(1),
            CustomItemList.eM_energyMulti16_UXV.get(1) };

        ItemStack[] wirelessHatches_16A = { CustomItemList.eM_energyWirelessMulti16_EV.get(1),
            CustomItemList.eM_energyWirelessMulti16_IV.get(1), CustomItemList.eM_energyWirelessMulti16_LuV.get(1),
            CustomItemList.eM_energyWirelessMulti16_ZPM.get(1), CustomItemList.eM_energyWirelessMulti16_UV.get(1),
            CustomItemList.eM_energyWirelessMulti16_UHV.get(1), CustomItemList.eM_energyWirelessMulti16_UEV.get(1),
            CustomItemList.eM_energyWirelessMulti16_UIV.get(1), CustomItemList.eM_energyWirelessMulti16_UMV.get(1),
            CustomItemList.eM_energyWirelessMulti16_UXV.get(1) };

        ItemStack[] energyHatches_64A = { CustomItemList.eM_energyMulti64_EV.get(1),
            CustomItemList.eM_energyMulti64_IV.get(1), CustomItemList.eM_energyMulti64_LuV.get(1),
            CustomItemList.eM_energyMulti64_ZPM.get(1), CustomItemList.eM_energyMulti64_UV.get(1),
            CustomItemList.eM_energyMulti64_UHV.get(1), CustomItemList.eM_energyMulti64_UEV.get(1),
            CustomItemList.eM_energyMulti64_UIV.get(1), CustomItemList.eM_energyMulti64_UMV.get(1),
            CustomItemList.eM_energyMulti64_UXV.get(1) };

        ItemStack[] wirelessHatches_64A = { CustomItemList.eM_energyWirelessMulti64_EV.get(1),
            CustomItemList.eM_energyWirelessMulti64_IV.get(1), CustomItemList.eM_energyWirelessMulti64_LuV.get(1),
            CustomItemList.eM_energyWirelessMulti64_ZPM.get(1), CustomItemList.eM_energyWirelessMulti64_UV.get(1),
            CustomItemList.eM_energyWirelessMulti64_UHV.get(1), CustomItemList.eM_energyWirelessMulti64_UEV.get(1),
            CustomItemList.eM_energyWirelessMulti64_UIV.get(1), CustomItemList.eM_energyWirelessMulti64_UMV.get(1),
            CustomItemList.eM_energyWirelessMulti64_UXV.get(1) };

        for (int i = 0; i < wirelessHatches_4A.length; i++) {
            CraftingManager.getInstance()
                .addShapelessRecipe(wirelessHatches_4A[i], energyHatches_4A[i]);
            CraftingManager.getInstance()
                .addShapelessRecipe(energyHatches_4A[i], wirelessHatches_4A[i]);
            CraftingManager.getInstance()
                .addShapelessRecipe(wirelessHatches_16A[i], energyHatches_16A[i]);
            CraftingManager.getInstance()
                .addShapelessRecipe(energyHatches_16A[i], wirelessHatches_16A[i]);
            CraftingManager.getInstance()
                .addShapelessRecipe(wirelessHatches_64A[i], energyHatches_64A[i]);
            CraftingManager.getInstance()
                .addShapelessRecipe(energyHatches_64A[i], wirelessHatches_64A[i]);
        }

    }

    @Override
    public void undoVariant(VariantNames... activeVariants) {
        // can't really turn off easily, so just... won't
        // hence also not toggling the .hasLoaded
    }
}
