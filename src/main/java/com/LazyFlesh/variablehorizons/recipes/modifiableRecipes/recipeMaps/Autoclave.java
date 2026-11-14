package com.LazyFlesh.variablehorizons.recipes.modifiableRecipes.recipeMaps;

import static gregtech.api.recipe.RecipeMaps.autoclaveRecipes;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.util.randomUtil;
import com.LazyFlesh.variablehorizons.util.recipeBuilder.RecipeDraft;
import com.LazyFlesh.variablehorizons.util.recipeBuilder.RecipeModifiers;
import com.LazyFlesh.variablehorizons.util.recipeBuilder.RecipePhase;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.TierEU;

public class Autoclave implements Runnable {

    private static final VariantNames[] deps = new VariantNames[] { VariantNames.ALTERED_PRICING };

    @Override
    public void run() {
        if (!randomUtil.checkDeps(deps)) {
            return;
        }

        RecipeModifiers.defineBase(
            "autoclave/realized_quantum_mainframe_rack",
            () -> RecipeDraft.of(autoclaveRecipes)
                .itemInputs(ItemList.LostQuantumCircuitRack.get(1))
                .fluidInputs(Materials.ExcitedDTSC.getFluid(1000))
                .itemOutputs(ItemList.RealizedQuantumCircuitRack.get(1))
                .duration(1 * SECONDS)
                .eut(TierEU.RECIPE_UMV));

        RecipeModifiers.modify(
            "autoclave/realized_quantum_mainframe_rack",
            VariantNames.ALTERED_PRICING,
            RecipePhase.SCALE,
            d -> d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.LostQuantumCircuitRack.get(1)));
    }
}
