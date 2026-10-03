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
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTOreDictUnificator;
import ic2.core.Ic2Items;
import tectech.thing.CustomItemList;

public class Wireless extends VariantLoader implements IRuntimeVariant {

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        VariantNames.WIRELESS.hasLoaded = true;
    }

    @Override
    public void variantRecipes(VariantNames... activeVariants) {
        // recipes for each wireless hatch, 1 to 1 conversion from normal e hatch
        // skip dynamos, those are handled by mixins

        // skip ulv and max
        for (int i = 1; i < ItemList.WIRELESS_ENERGY_HATCHES.length - 1; i++) {
            CraftingManager.getInstance()
                .addShapelessRecipe(ItemList.WIRELESS_ENERGY_HATCHES[i].get(1), ItemList.HATCHES_ENERGY[i].get(1));
            CraftingManager.getInstance()
                .addShapelessRecipe(ItemList.HATCHES_ENERGY[i].get(1), ItemList.WIRELESS_ENERGY_HATCHES[i].get(1));
        }

        // Cover Recipes
        Materials[] plateMat = new Materials[] { Materials.Aluminium, Materials.StainlessSteel, Materials.Titanium,
            Materials.TungstenSteel, WerkstoffLoader.RhodiumPlatedPalladium.getGTMaterial(), Materials.Iridium,
            Materials.Osmium, Materials.Neutronium, Materials.Infinity, Materials.TranscendentMetal,
            Materials.SpaceTime, Materials.MHDCSM };

        ItemList[] coil = new ItemList[] { ItemList.LV_Coil, ItemList.MV_Coil, ItemList.HV_Coil, ItemList.EV_Coil,
            ItemList.IV_Coil, ItemList.LuV_Coil, ItemList.ZPM_Coil, ItemList.UV_Coil, ItemList.UHV_Coil,
            ItemList.UEV_Coil, ItemList.UIV_Coil, ItemList.UMV_Coil };

        // LV Recipe
        GTModHandler.addCraftingRecipe(
            ItemList.Cover_Wireless_Energy_LV.get(1),
            GTModHandler.RecipeBits.BUFFERED | GTModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS,
            new Object[] { "SPS", "dBC", "SPS", 'S', OrePrefixes.screw.get(Materials.Steel), 'P',
                OrePrefixes.plate.get(Materials.Iron), 'B', OrePrefixes.circuit.get(Materials.LV), 'C',
                Ic2Items.coil });

        // skip LV & MAX
        for (int i = 0; i < ItemList.WIRELESS_ENERGY_COVERS.length - 2; i++) {
            GTValues.RA.stdBuilder()
                .itemInputs(GTOreDictUnificator.get(OrePrefixes.plate, plateMat[i], 1L), coil[i].get(1))
                .itemOutputs(ItemList.WIRELESS_ENERGY_COVERS[i + 1].get(1))
                .fluidInputs(SubstituteFluidStack.soldering(QUARTER_INGOTS))
                .duration(5 * SECONDS)
                .eut(GTValues.VP[i + 1])
                .addTo(assemblerRecipes);
        }

        // Multiamp energy hatch conversion recipes
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
