package com.LazyFlesh.variablehorizons.recipes.modifiableRecipes.recipeMaps;

import static gregtech.api.enums.Mods.IndustrialCraft2;
import static gregtech.api.recipe.RecipeMaps.circuitAssemblerRecipes;
import static gregtech.api.util.GTRecipeBuilder.HALF_INGOTS;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.util.recipeBuilder.RecipeDraft;
import com.LazyFlesh.variablehorizons.util.recipeBuilder.RecipeModifiers;
import com.LazyFlesh.variablehorizons.util.recipeBuilder.RecipePhase;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.objects.SubstituteFluidStack;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTOreDictUnificator;

public class CircuitAssembler implements Runnable {

    @Override
    public void run() {
        RecipeModifiers.defineBase(
            "circuitassembler/electronic_circuit",
            () -> RecipeDraft.of(circuitAssemblerRecipes)
                .itemInputs(
                    ItemList.Circuit_Board_Coated_Basic.get(1L),
                    ItemList.Circuit_Parts_Resistor.get(2L),
                    GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.RedAlloy, 2),
                    ItemList.Circuit_Parts_Vacuum_Tube.get(2L))
                .fluidInputs(SubstituteFluidStack.soldering(1 * HALF_INGOTS))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemPartCircuit", 1, 0))
                .duration(10 * SECONDS)
                .eut(TierEU.RECIPE_LV / 2));

        RecipeModifiers
            .modify("circuitassembler/electronic_circuit", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                d.itemIn()
                    .condenseAll();
            });

        RecipeModifiers.defineBase(
            "circuitassembler/electronic_circuit_smd",
            () -> RecipeDraft.of(circuitAssemblerRecipes)
                .itemInputs(
                    ItemList.Circuit_Board_Coated_Basic.get(1L),
                    ItemList.Circuit_Parts_ResistorSMD.get(2L),
                    GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.RedAlloy, 2),
                    ItemList.Circuit_Parts_Vacuum_Tube.get(2L))
                .fluidInputs(SubstituteFluidStack.soldering(1 * HALF_INGOTS))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemPartCircuit", 1, 0))
                .duration(10 * SECONDS)
                .eut(TierEU.RECIPE_LV / 2));

        RecipeModifiers
            .modify("circuitassembler/electronic_circuit_smd", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                d.itemIn()
                    .condenseAll();
            });

        RecipeModifiers.defineBase(
            "circuitassembler/good_electronic_circuit",
            () -> RecipeDraft.of(circuitAssemblerRecipes)
                .itemInputs(
                    ItemList.Circuit_Board_Phenolic_Good.get(1),
                    GTModHandler.getModItem(IndustrialCraft2.ID, "itemPartCircuit", 2, 0),
                    ItemList.Circuit_Parts_Diode.get(2),
                    GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 2L))
                .fluidInputs(SubstituteFluidStack.soldering(1 * HALF_INGOTS))
                .itemOutputs(ItemList.Circuit_Good.get(1))
                .duration(15 * SECONDS)
                .eut(TierEU.RECIPE_LV));

        RecipeModifiers
            .modify("circuitassembler/good_electronic_circuit", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(
                    GeneralConfig.partCostMultiplier,
                    GTModHandler.getModItem(IndustrialCraft2.ID, "itemPartCircuit", 1, 0));
                d.itemIn()
                    .condenseAll();
            });

        RecipeModifiers.defineBase(
            "circuitassembler/good_electronic_circuit_smd",
            () -> RecipeDraft.of(circuitAssemblerRecipes)
                .itemInputs(
                    ItemList.Circuit_Board_Phenolic_Good.get(1),
                    GTModHandler.getModItem(IndustrialCraft2.ID, "itemPartCircuit", 2, 0),
                    ItemList.Circuit_Parts_DiodeSMD.get(2),
                    GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 2L))
                .fluidInputs(SubstituteFluidStack.soldering(1 * HALF_INGOTS))
                .itemOutputs(ItemList.Circuit_Good.get(1))
                .duration(15 * SECONDS)
                .eut(TierEU.RECIPE_LV));

        RecipeModifiers.modify(
            "circuitassembler/good_electronic_circuit_smd",
            VariantNames.ALTERED_PRICING,
            RecipePhase.SCALE,
            d -> {
                d.scaleAllInputsBalanced(
                    GeneralConfig.partCostMultiplier,
                    GTModHandler.getModItem(IndustrialCraft2.ID, "itemPartCircuit", 1, 0));
                d.itemIn()
                    .condenseAll();
            });
    }
}
