package com.LazyFlesh.variablehorizons.modifiableRecipes.recipeMaps;

import static gregtech.api.recipe.RecipeMaps.assemblerRecipes;
import static gregtech.api.recipe.RecipeMaps.blastFurnaceRecipes;
import static gregtech.api.util.GTRecipeBuilder.TICKS;

import com.LazyFlesh.variablehorizons.util.recipeBuilder.RecipeDraft;
import com.LazyFlesh.variablehorizons.util.recipeBuilder.RecipeModifiers;
import com.LazyFlesh.variablehorizons.util.recipeBuilder.RecipePhase;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.objects.OreDictItemStack;
import gregtech.api.util.GTOreDictUnificator;

public class Assembler implements Runnable {

    @Override
    public void run() {
        RecipeModifiers.defineBase(
            "assembler/electric_motor_lv",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.stick, Materials.IronMagnetic, 1L),
                    new OreDictItemStack("stickAnyIron", 2),
                    new OreDictItemStack("wireGt01AnyCopper", 4),
                    GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 2L))
                .itemOutputs(ItemList.Electric_Motor_LV.get(1L))
                .duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV));

        RecipeModifiers.modify(
            "assembler/electric_motor_lv",
            VariantNames.ALTERED_PRICING,
            RecipePhase.SCALE,
            d -> d.itemIn()
                .scale(new OreDictItemStack("stickAnyIron", 1), 2));

        RecipeModifiers.modify(
            "assembler/electric_motor_lv",
            VariantNames.SUPERFLAT,
            RecipePhase.SUBSTITUTE,
            d -> d.itemIn()
                .replace(new OreDictItemStack("stickAnyIron", 1), new OreDictItemStack("stickSteel", 2)));

        RecipeModifiers.defineBase(
            "ebf/air",
            () -> RecipeDraft.of(blastFurnaceRecipes)
                .itemInputs(ItemList.AcceleratorHV.get(1))
                .fluidInputs(Materials.Air.getGas(50000))
                .fluidOutputs(Materials.NetherAir.getFluid(10000))
                .itemOutputs(ItemList.Electric_Motor_LV.get(1L))
                .duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV));

        RecipeModifiers.modify(
            "ebf/air",
            VariantNames.ALTERED_PRICING,
            RecipePhase.SCALE,
            d -> d.fluidIn()
                .replace(Materials.Air.getGas(50000), Materials.Eternity.getMolten(20))
                .scale(Materials.Air.getGas(50000), 2));

        RecipeModifiers.modify(
            "ebf/air",
            VariantNames.SUPERFLAT,
            RecipePhase.SCALE,
            d -> d.itemOut()
                .add(ItemList.MacerationStack.get(1)));

    }

}
