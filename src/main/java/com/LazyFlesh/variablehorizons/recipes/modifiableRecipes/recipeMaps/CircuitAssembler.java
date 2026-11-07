package com.LazyFlesh.variablehorizons.recipes.modifiableRecipes.recipeMaps;

import static gregtech.api.enums.Mods.IndustrialCraft2;
import static gregtech.api.recipe.RecipeMaps.circuitAssemblerRecipes;
import static gregtech.api.util.GTRecipeBuilder.HALF_INGOTS;
import static gregtech.api.util.GTRecipeBuilder.INGOTS;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeBuilder.TICKS;

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
import gregtech.api.util.GTRecipeConstants;

public class CircuitAssembler implements Runnable {

    private static final String[] SMD_STRING = new String[] { "", "_smd", "_smd_advanced", "_smd_optical" };
    private static final ItemList[] RESISTORS = new ItemList[] { ItemList.Circuit_Parts_Resistor,
        ItemList.Circuit_Parts_ResistorSMD, ItemList.Circuit_Parts_ResistorASMD, ItemList.Circuit_Parts_ResistorXSMD };
    private static final ItemList[] DIODES = new ItemList[] { ItemList.Circuit_Parts_Diode,
        ItemList.Circuit_Parts_DiodeSMD, ItemList.Circuit_Parts_DiodeASMD, ItemList.Circuit_Parts_DiodeXSMD };
    private static final ItemList[] TRANSISTORS = new ItemList[] { ItemList.Circuit_Parts_Transistor,
        ItemList.Circuit_Parts_TransistorSMD, ItemList.Circuit_Parts_TransistorASMD,
        ItemList.Circuit_Parts_TransistorXSMD };
    private static final ItemList[] INDUCTORS = new ItemList[] { ItemList.Circuit_Parts_Coil,
        ItemList.Circuit_Parts_InductorSMD, ItemList.Circuit_Parts_InductorASMD, ItemList.Circuit_Parts_InductorXSMD };
    private static final ItemList[] CAPACITORS = new ItemList[] { ItemList.Circuit_Parts_Capacitor,
        ItemList.Circuit_Parts_CapacitorSMD, ItemList.Circuit_Parts_CapacitorASMD,
        ItemList.Circuit_Parts_CapacitorXSMD };

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            boolean basic = i == 0;
            boolean SMD = i == 1;
            boolean advancedSMD = i == 2;
            if (i < 2) {
                RecipeModifiers.defineBase(
                    "circuitassembler/electronic_circuit" + SMD_STRING[i],
                    () -> RecipeDraft.of(circuitAssemblerRecipes)
                        .itemInputs(
                            ItemList.Circuit_Board_Coated_Basic.get(1L),
                            RESISTORS[finalI].get(2L),
                            GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.RedAlloy, 2),
                            ItemList.Circuit_Parts_Vacuum_Tube.get(2L))
                        .fluidInputs(SubstituteFluidStack.soldering(1 * HALF_INGOTS))
                        .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemPartCircuit", 1, 0))
                        .duration(10 * SECONDS)
                        .eut(TierEU.RECIPE_LV / 2));

                RecipeModifiers.modify(
                    "circuitassembler/electronic_circuit" + SMD_STRING[i],
                    VariantNames.ALTERED_PRICING,
                    RecipePhase.SCALE,
                    d -> {
                        d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                        d.itemIn()
                            .condenseAll();
                    });

                RecipeModifiers.defineBase(
                    "circuitassembler/good_electronic_circuit" + SMD_STRING[i],
                    () -> RecipeDraft.of(circuitAssemblerRecipes)
                        .itemInputs(
                            ItemList.Circuit_Board_Phenolic_Good.get(1),
                            GTModHandler.getModItem(IndustrialCraft2.ID, "itemPartCircuit", 2, 0),
                            DIODES[finalI].get(2),
                            GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 2L))
                        .fluidInputs(SubstituteFluidStack.soldering(1 * HALF_INGOTS))
                        .itemOutputs(ItemList.Circuit_Good.get(1))
                        .duration(15 * SECONDS)
                        .eut(TierEU.RECIPE_LV));

                RecipeModifiers.modify(
                    "circuitassembler/good_electronic_circuit" + SMD_STRING[i],
                    VariantNames.ALTERED_PRICING,
                    RecipePhase.SCALE,
                    d -> {
                        d.scaleAllInputsBalanced(
                            GeneralConfig.partCostMultiplier,
                            GTModHandler.getModItem(IndustrialCraft2.ID, "itemPartCircuit", 1, 0));
                        d.itemIn()
                            .condenseAll();
                    });

                RecipeModifiers.defineBase(
                    "circuitassembler/integrated_logic_circuit" + SMD_STRING[i],
                    () -> RecipeDraft.of(circuitAssemblerRecipes)
                        .itemInputs(
                            ItemList.Circuit_Board_Coated_Basic.get(1),
                            ItemList.Circuit_Chip_ILC.get(1),
                            RESISTORS[finalI].get(2),
                            DIODES[finalI].get(2),
                            GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 2L),
                            GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Tin, 2L))
                        .fluidInputs(SubstituteFluidStack.soldering(1 * HALF_INGOTS))
                        .itemOutputs(ItemList.Circuit_Basic.get(1))
                        .duration(10 * SECONDS)
                        .eut(TierEU.RECIPE_LV / 2));

                RecipeModifiers.modify(
                    "circuitassembler/integrated_logic_circuit" + SMD_STRING[i],
                    VariantNames.ALTERED_PRICING,
                    RecipePhase.SCALE,
                    d -> {
                        d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                        d.itemIn()
                            .condenseAll();
                    });
            }

            RecipeModifiers.defineBase(
                "circuitassembler/good_integrated_logic_circuit" + SMD_STRING[i],
                () -> RecipeDraft.of(circuitAssemblerRecipes)
                    .itemInputs(
                        ItemList.Circuit_Board_Phenolic_Good.get(1),
                        ItemList.Circuit_Basic.get(2),
                        RESISTORS[finalI].get(advancedSMD ? 1 : 4),
                        DIODES[finalI].get(advancedSMD ? 1 : 4),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Gold, 4L),
                        GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Silver, 4L))
                    .fluidInputs(SubstituteFluidStack.soldering(1 * HALF_INGOTS))
                    .itemOutputs(ItemList.Circuit_Integrated_Good.get(1))
                    .duration((advancedSMD ? 10 : 20) * SECONDS)
                    .eut(TierEU.RECIPE_LV / 5 * 4));

            RecipeModifiers.modify(
                "circuitassembler/good_integrated_logic_circuit" + SMD_STRING[i],
                VariantNames.ALTERED_PRICING,
                RecipePhase.SCALE,
                d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Circuit_Integrated.get(2));
                    d.itemIn()
                        .condenseAll();
                });

            RecipeModifiers.defineBase(
                "circuitassembler/advanced_circuit" + SMD_STRING[i],
                () -> RecipeDraft.of(circuitAssemblerRecipes)
                    .itemInputs(
                        ItemList.Circuit_Integrated_Good.get(1L),
                        ItemList.Circuit_Chip_ILC.get(2L),
                        ItemList.Circuit_Chip_Ram.get(2L),
                        TRANSISTORS[finalI].get(advancedSMD ? 1 : 4),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Electrum, 8),
                        GTOreDictUnificator.get(OrePrefixes.bolt, Materials.AnnealedCopper, 8))
                    .fluidInputs(SubstituteFluidStack.soldering(1 * HALF_INGOTS))
                    .itemOutputs(GTModHandler.getIC2Item("advancedCircuit", 1L))
                    .duration((advancedSMD ? 20 : 40) * SECONDS)
                    .eut(TierEU.RECIPE_LV));

            RecipeModifiers.modify(
                "circuitassembler/advanced_circuit" + SMD_STRING[i],
                VariantNames.ALTERED_PRICING,
                RecipePhase.SCALE,
                d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Circuit_Integrated_Good.get(1));
                    d.itemIn()
                        .condenseAll();
                });

            RecipeModifiers.defineBase(
                "circuitassembler/integrated_processor" + SMD_STRING[i],
                () -> RecipeDraft.of(circuitAssemblerRecipes)
                    .itemInputs(
                        ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        ItemList.Circuit_Chip_CPU.get(1L),
                        RESISTORS[finalI].get(advancedSMD ? 1 : 4),
                        DIODES[finalI].get(advancedSMD ? 1 : 4),
                        TRANSISTORS[finalI].get(advancedSMD ? 1 : 4),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.RedAlloy, 4))
                    .fluidInputs(SubstituteFluidStack.soldering(1 * HALF_INGOTS))
                    .itemOutputs(ItemList.Circuit_Processor.get(1L))
                    .metadata(GTRecipeConstants.CLEANROOM, true)
                    .duration((advancedSMD ? 5 : 10) * SECONDS)
                    .eut(TierEU.RECIPE_MV / 2));

            RecipeModifiers.modify(
                "circuitassembler/integrated_processor" + SMD_STRING[i],
                VariantNames.ALTERED_PRICING,
                RecipePhase.SCALE,
                d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                    d.itemIn()
                        .condenseAll();
                });

            RecipeModifiers.defineBase(
                "circuitassembler/processor_assembly" + SMD_STRING[i],
                () -> RecipeDraft.of(circuitAssemblerRecipes)
                    .itemInputs(
                        ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                        ItemList.Circuit_Processor.get(2L),
                        INDUCTORS[finalI].get(advancedSMD ? 1 : 4),
                        CAPACITORS[finalI].get(advancedSMD ? 2 : 8),
                        ItemList.Circuit_Chip_Ram.get(4L),
                        GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.RedAlloy, 8))
                    .fluidInputs(SubstituteFluidStack.soldering(1 * INGOTS))
                    .itemOutputs(ItemList.Circuit_Advanced.get(1L))
                    .metadata(GTRecipeConstants.CLEANROOM, true)
                    .duration((advancedSMD ? 10 : 20) * SECONDS)
                    .eut(advancedSMD ? TierEU.RECIPE_MV * 4 / 5 : TierEU.RECIPE_MV));

            RecipeModifiers.modify(
                "circuitassembler/processor_assembly" + SMD_STRING[i],
                VariantNames.ALTERED_PRICING,
                RecipePhase.SCALE,
                d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Circuit_Processor.get(2L));
                    d.itemIn()
                        .condenseAll();
                });
        }

        RecipeModifiers.defineBase(
            "circuitassembler/integrated_processor_soc",
            () -> RecipeDraft.of(circuitAssemblerRecipes)
                .itemInputs(
                    ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                    ItemList.Circuit_Chip_SoC.get(1L),
                    GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.RedAlloy, 4),
                    GTOreDictUnificator.get(OrePrefixes.bolt, Materials.AnnealedCopper, 4))
                .fluidInputs(SubstituteFluidStack.soldering(1 * HALF_INGOTS))
                .itemOutputs(ItemList.Circuit_Processor.get(1L))
                .metadata(GTRecipeConstants.CLEANROOM, true)
                .duration(50 * TICKS)
                .eut(TierEU.RECIPE_IV * 5 / 16));

        RecipeModifiers
            .modify("circuitassembler/integrated_processor_soc", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                d.itemIn()
                    .condenseAll();
            });
    }
}
