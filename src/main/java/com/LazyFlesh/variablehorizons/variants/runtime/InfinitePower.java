package com.LazyFlesh.variablehorizons.variants.runtime;

import static gregtech.api.recipe.RecipeMaps.assemblerRecipes;
import static gregtech.api.util.GTRecipeBuilder.QUARTER_INGOTS;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;

import java.math.BigInteger;

import net.minecraft.item.crafting.CraftingManager;

import com.LazyFlesh.variablehorizons.util.RecipeRemover;
import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import bartworks.system.material.WerkstoffLoader;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.objects.SubstituteFluidStack;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.common.misc.GlobalVariableStorage;

public class InfinitePower extends VariantLoader implements IRuntimeVariant {

    public final static BigInteger infinitePowaaaaaaahhhhh = BigInteger.TEN.pow(50);

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        VariantNames.INFINITE_POWER.hasLoaded = true;

        // Remove wireless hatch recipes. Not like they'll use em.
        for (int i = 0; i < ItemList.WIRELESS_ENERGY_HATCHES.length; i++) {
            RecipeRemover.addItemsForRecipeRemoval(ItemList.WIRELESS_ENERGY_HATCHES[i].get(1));
        }
    }

    public static void givePower() {
        // runs every restart, so... I doubt it will *ever* deplete
        for (java.util.UUID uuid : GlobalVariableStorage.GlobalEnergy.keySet()) {
            GlobalVariableStorage.GlobalEnergy.replace(uuid, infinitePowaaaaaaahhhhh);
        }
    }

    @Override
    public void variantRecipes(VariantNames... activeVariants) {
        // recipes for each wireless hatch, 1 to 1 conversion from normal e hatch
        // skip adding dynamo to wireless dynamo, since pointless

        // skip ulv
        for (int i = 1; i < ItemList.WIRELESS_ENERGY_HATCHES.length; i++) {
            CraftingManager.getInstance()
                .addShapelessRecipe(ItemList.WIRELESS_ENERGY_HATCHES[1].get(1), ItemList.HATCHES_ENERGY[1].get(1));
            CraftingManager.getInstance()
                .addShapelessRecipe(ItemList.HATCHES_ENERGY[1].get(1), ItemList.WIRELESS_ENERGY_HATCHES[1].get(1));
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
                .eut((int) (TierEU.RECIPE_ULV * Math.pow(4, i)))
                .addTo(assemblerRecipes);
        }
    }
}
