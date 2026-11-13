package com.LazyFlesh.variablehorizons.recipes.modifiableRecipes.recipeMaps;

import static bartworks.API.recipe.BartWorksRecipeMaps.electricImplosionCompressorRecipes;
import static gregtech.api.enums.GTValues.M;
import static gregtech.api.enums.Mods.OpenComputers;
import static gregtech.api.enums.Mods.SuperSolarPanels;
import static gregtech.api.util.GTModHandler.getModItem;
import static gregtech.api.util.GTRecipeBuilder.INGOTS;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.util.recipeBuilder.RecipeDraft;
import com.LazyFlesh.variablehorizons.util.recipeBuilder.RecipeModifiers;
import com.LazyFlesh.variablehorizons.util.recipeBuilder.RecipePhase;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GTOreDictUnificator;

public class EIC implements Runnable {

    @Override
    public void run() {
        partsMHDCSM();
    }

    private void partsMHDCSM() {
        if (!SuperSolarPanels.isModLoaded()) {
            return;
        }
        this.addMHDCSMPartRecipe(OrePrefixes.frameGt, 1, 1);
        this.addMHDCSMPartRecipe(OrePrefixes.nugget, 9, 1);
        this.addMHDCSMPartRecipe(OrePrefixes.ingot, 1, 1);
        this.addMHDCSMPartRecipe(OrePrefixes.plate, 1, 1);
        this.addMHDCSMPartRecipe(OrePrefixes.plateDense, 1, 3);
        this.addMHDCSMPartRecipe(OrePrefixes.stick, 2, 1);
        this.addMHDCSMPartRecipe(OrePrefixes.round, 8, 1);
        this.addMHDCSMPartRecipe(OrePrefixes.bolt, 8, 1);
        this.addMHDCSMPartRecipe(OrePrefixes.screw, 8, 1);
        this.addMHDCSMPartRecipe(OrePrefixes.ring, 4, 1);
        this.addMHDCSMPartRecipe(OrePrefixes.foil, 8, 1);
        this.addMHDCSMPartRecipe(OrePrefixes.itemCasing, 2, 1);
        this.addMHDCSMPartRecipe(OrePrefixes.gearGtSmall, 1, 1);
        this.addMHDCSMPartRecipe(OrePrefixes.rotor, 1, 2);
        this.addMHDCSMPartRecipe(OrePrefixes.stickLong, 1, 1);
        this.addMHDCSMPartRecipe(OrePrefixes.springSmall, 2, 1);
        this.addMHDCSMPartRecipe(OrePrefixes.spring, 1, 1);
        this.addMHDCSMPartRecipe(OrePrefixes.gearGt, 1, 2);
        this.addMHDCSMPartRecipe(OrePrefixes.wireFine, 8, 1);
        this.addMHDCSMPartRecipe(OrePrefixes.block, 1, 1);
    }

    private void addMHDCSMPartRecipe(final OrePrefixes part, final int multiplier, final int circuitMultiplier) {

        final int partFraction = (int) (part.getMaterialAmount() * INGOTS / M);

        RecipeModifiers.defineBase(
            "EIC/mhdcsm_" + part.getName(),
            () -> RecipeDraft.of(electricImplosionCompressorRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.circuit, Materials.UHV, circuitMultiplier),
                    GTOreDictUnificator.get(OrePrefixes.plate, Materials.Terbium, circuitMultiplier),
                    getModItem(SuperSolarPanels.ID, "solarsplitter", 1, 0),
                    getModItem(OpenComputers.ID, "hologram2", circuitMultiplier, 0),
                    GTOreDictUnificator.get(part, Materials.Eternity, multiplier))
                .fluidInputs(Materials.MHDCSM.getMolten((long) partFraction * multiplier))
                .itemOutputs(GTOreDictUnificator.get(part, Materials.MHDCSM, multiplier))
                .duration((int) (multiplier * (20 * partFraction / (float) INGOTS)))
                .eut(TierEU.RECIPE_UXV));

        RecipeModifiers.modify("EIC/mhdcsm_" + part.getName(), VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsExcept(
                Math.max(1, GeneralConfig.partCostMultiplier),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.UHV, 1));
            d.itemIn()
                .condenseAll();
            d.scaleAllOutputs(Math.max(1, GeneralConfig.partCostMultiplier));
        });
    }
}
