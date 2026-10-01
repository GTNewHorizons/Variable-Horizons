package com.LazyFlesh.variablehorizons.variants.runtime;

import static gregtech.api.recipe.RecipeMaps.assemblerRecipes;
import static gregtech.api.util.GTRecipeBuilder.QUARTER_INGOTS;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;

import net.minecraft.item.crafting.CraftingManager;

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
                .eut((int) (TierEU.RECIPE_ULV * Math.pow(4, i)))
                .addTo(assemblerRecipes);
        }
    }

    @Override
    public void undoVariant(VariantNames... activeVariants) {
        // can't really turn off easily, so just... won't
        // hence also not toggling the .hasLoaded
    }
}
