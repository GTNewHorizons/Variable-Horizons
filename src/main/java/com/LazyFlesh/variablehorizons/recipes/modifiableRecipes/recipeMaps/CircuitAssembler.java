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
import gtPlusPlus.core.material.MaterialMisc;
import gtPlusPlus.core.material.MaterialsAlloy;
import gtPlusPlus.core.material.MaterialsElements;

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
        for (int i = 0; i < 4; i++) {
            int finalI = i;
            boolean basic = i == 0;
            boolean SMD = i == 1;
            boolean advancedSMD = i == 2;
            boolean opticalSMD = i == 3;

            // Non-SMD & SMD
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

            // Up to advanced SMD
            if (i < 3) {
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
                        d.scaleAllInputsBalanced(
                            GeneralConfig.partCostMultiplier,
                            ItemList.Circuit_Integrated_Good.get(1));
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

                RecipeModifiers.defineBase(
                    "circuitassembler/workstation" + SMD_STRING[i],
                    () -> RecipeDraft.of(circuitAssemblerRecipes)
                        .itemInputs(
                            ItemList.Circuit_Board_Plastic_Advanced.get(1L),
                            ItemList.Circuit_Advanced.get(2L),
                            DIODES[finalI].get(advancedSMD ? 1 : 4),
                            ItemList.Circuit_Chip_Ram.get(8L),
                            GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Electrum, 16),
                            GTOreDictUnificator.get(OrePrefixes.bolt, Materials.BlueAlloy, 16))
                        .fluidInputs(SubstituteFluidStack.soldering(1 * INGOTS))
                        .itemOutputs(ItemList.Circuit_Data.get(1L))
                        .metadata(GTRecipeConstants.CLEANROOM, true)
                        .duration((advancedSMD ? 10 : 20) * SECONDS)
                        .eut(TierEU.RECIPE_MV));

                RecipeModifiers.modify(
                    "circuitassembler/workstation" + SMD_STRING[i],
                    VariantNames.ALTERED_PRICING,
                    RecipePhase.SCALE,
                    d -> {
                        d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Circuit_Advanced.get(2L));
                        d.itemIn()
                            .condenseAll();
                    });

                RecipeModifiers.defineBase(
                    "circuitassembler/mainframe" + SMD_STRING[i],
                    () -> RecipeDraft.of(circuitAssemblerRecipes)
                        .itemInputs(
                            GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Aluminium, 2),
                            ItemList.Circuit_Data.get(2),
                            INDUCTORS[finalI].get(advancedSMD ? 3 : 12),
                            CAPACITORS[finalI].get(advancedSMD ? 4 : 24),
                            ItemList.Circuit_Chip_Ram.get(16L),
                            GTOreDictUnificator
                                .get(OrePrefixes.wireGt01, Materials.AnnealedCopper, advancedSMD ? 16 : 24))
                        .fluidInputs(SubstituteFluidStack.soldering(2 * INGOTS))
                        .itemOutputs(ItemList.Circuit_Elite.get(1L))
                        .metadata(GTRecipeConstants.CLEANROOM, true)
                        .duration((advancedSMD ? 40 : 80) * SECONDS)
                        .eut(TierEU.RECIPE_HV));

                RecipeModifiers.modify(
                    "circuitassembler/mainframe" + SMD_STRING[i],
                    VariantNames.ALTERED_PRICING,
                    RecipePhase.SCALE,
                    d -> {
                        d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Circuit_Data.get(2L));
                        d.itemIn()
                            .condenseAll();
                    });
                // no more non-SMDs
                if (i > 0) {
                    RecipeModifiers.defineBase(
                        "circuitassembler/nano_processor" + SMD_STRING[i],
                        () -> RecipeDraft.of(circuitAssemblerRecipes)
                            .itemInputs(
                                ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                                ItemList.Circuit_Chip_NanoCPU.get(1L),
                                RESISTORS[finalI].get(advancedSMD ? 2 : 8),
                                CAPACITORS[finalI].get(advancedSMD ? 2 : 8),
                                TRANSISTORS[finalI].get(advancedSMD ? 2 : 8),
                                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Electrum, 8))
                            .fluidInputs(SubstituteFluidStack.soldering(1 * HALF_INGOTS))
                            .itemOutputs(ItemList.Circuit_Nanoprocessor.get(1L))
                            .metadata(GTRecipeConstants.CLEANROOM, true)
                            .duration((advancedSMD ? 5 : 10) * SECONDS)
                            .eut(TierEU.RECIPE_EV * 5 / 16));

                    RecipeModifiers.modify(
                        "circuitassembler/nano_processor" + SMD_STRING[i],
                        VariantNames.ALTERED_PRICING,
                        RecipePhase.SCALE,
                        d -> {
                            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                            d.itemIn()
                                .condenseAll();
                        });

                    RecipeModifiers.defineBase(
                        "circuitassembler/nano_assembly" + SMD_STRING[i],
                        () -> RecipeDraft.of(circuitAssemblerRecipes)
                            .itemInputs(
                                ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                                ItemList.Circuit_Nanoprocessor.get(2L),
                                INDUCTORS[finalI].get(advancedSMD ? 2 : 8),
                                CAPACITORS[finalI].get(advancedSMD ? 2 : 8),
                                ItemList.Circuit_Chip_Ram.get(8L),
                                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Electrum, 16))
                            .fluidInputs(SubstituteFluidStack.soldering(1 * INGOTS))
                            .itemOutputs(ItemList.Circuit_Nanocomputer.get(1L))
                            .metadata(GTRecipeConstants.CLEANROOM, true)
                            .duration((advancedSMD ? 10 : 20) * SECONDS)
                            .eut(TierEU.RECIPE_EV * 5 / 16));

                    RecipeModifiers.modify(
                        "circuitassembler/nano_assembly" + SMD_STRING[i],
                        VariantNames.ALTERED_PRICING,
                        RecipePhase.SCALE,
                        d -> {
                            d.scaleAllInputsBalanced(
                                GeneralConfig.partCostMultiplier,
                                ItemList.Circuit_Nanoprocessor.get(2L));
                            d.itemIn()
                                .condenseAll();
                        });

                    RecipeModifiers.defineBase(
                        "circuitassembler/nano_computer" + SMD_STRING[i],
                        () -> RecipeDraft.of(circuitAssemblerRecipes)
                            .itemInputs(
                                ItemList.Circuit_Board_Epoxy_Advanced.get(1L),
                                ItemList.Circuit_Nanocomputer.get(2L),
                                DIODES[finalI].get(advancedSMD ? 2 : 8),
                                ItemList.Circuit_Chip_NOR.get(4L),
                                ItemList.Circuit_Chip_Ram.get(16L),
                                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Electrum, 16))
                            .fluidInputs(SubstituteFluidStack.soldering(1 * INGOTS))
                            .itemOutputs(ItemList.Circuit_Elitenanocomputer.get(1L))
                            .metadata(GTRecipeConstants.CLEANROOM, true)
                            .duration((advancedSMD ? 10 : 20) * SECONDS)
                            .eut(TierEU.RECIPE_EV * 5 / 16));

                    RecipeModifiers.modify(
                        "circuitassembler/nano_computer" + SMD_STRING[i],
                        VariantNames.ALTERED_PRICING,
                        RecipePhase.SCALE,
                        d -> {
                            d.scaleAllInputsBalanced(
                                GeneralConfig.partCostMultiplier,
                                ItemList.Circuit_Nanocomputer.get(2L));
                            d.itemIn()
                                .condenseAll();
                        });

                    RecipeModifiers.defineBase(
                        "circuitassembler/nano_mainframe" + SMD_STRING[i],
                        () -> RecipeDraft.of(circuitAssemblerRecipes)
                            .itemInputs(
                                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Aluminium, 2),
                                ItemList.Circuit_Elitenanocomputer.get(2L),
                                INDUCTORS[finalI].get(advancedSMD ? 4 : 16),
                                CAPACITORS[finalI].get(advancedSMD ? 8 : 32),
                                ItemList.Circuit_Chip_Ram.get(16L),
                                GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.AnnealedCopper, 32))
                            .fluidInputs(SubstituteFluidStack.soldering(2 * INGOTS))
                            .itemOutputs(ItemList.Circuit_Master.get(1L))
                            .metadata(GTRecipeConstants.CLEANROOM, true)
                            .duration((advancedSMD ? 80 : 40) * SECONDS)
                            .eut(TierEU.RECIPE_EV));

                    RecipeModifiers.modify(
                        "circuitassembler/nano_mainframe" + SMD_STRING[i],
                        VariantNames.ALTERED_PRICING,
                        RecipePhase.SCALE,
                        d -> {
                            d.scaleAllInputsBalanced(
                                GeneralConfig.partCostMultiplier,
                                ItemList.Circuit_Elitenanocomputer.get(2L));
                            d.itemIn()
                                .condenseAll();
                        });

                    RecipeModifiers.defineBase(
                        "circuitassembler/quantum_processor" + SMD_STRING[i],
                        () -> RecipeDraft.of(circuitAssemblerRecipes)
                            .itemInputs(
                                ItemList.Circuit_Board_Fiberglass_Advanced.get(1L),
                                ItemList.Circuit_Chip_QuantumCPU.get(1L),
                                ItemList.Circuit_Chip_NanoCPU.get(1L),
                                CAPACITORS[finalI].get(advancedSMD ? 3 : 12),
                                TRANSISTORS[finalI].get(advancedSMD ? 3 : 12),
                                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Platinum, 16))
                            .fluidInputs(SubstituteFluidStack.soldering(1 * HALF_INGOTS))
                            .itemOutputs(ItemList.Circuit_Quantumprocessor.get(1L))
                            .metadata(GTRecipeConstants.CLEANROOM, true)
                            .duration((advancedSMD ? 5 : 10) * SECONDS)
                            .eut(TierEU.RECIPE_IV * 5 / 16));

                    RecipeModifiers.modify(
                        "circuitassembler/quantum_processor" + SMD_STRING[i],
                        VariantNames.ALTERED_PRICING,
                        RecipePhase.SCALE,
                        d -> {
                            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                            d.itemIn()
                                .condenseAll();
                        });

                    RecipeModifiers.defineBase(
                        "circuitassembler/quantum_assembly" + SMD_STRING[i],
                        () -> RecipeDraft.of(circuitAssemblerRecipes)
                            .itemInputs(
                                ItemList.Circuit_Board_Fiberglass_Advanced.get(1L),
                                ItemList.Circuit_Quantumprocessor.get(2L),
                                INDUCTORS[finalI].get(advancedSMD ? 3 : 12),
                                CAPACITORS[finalI].get(advancedSMD ? 4 : 16),
                                ItemList.Circuit_Chip_Ram.get(4L),
                                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Platinum, 24))
                            .fluidInputs(SubstituteFluidStack.soldering(1 * INGOTS))
                            .itemOutputs(ItemList.Circuit_Quantumcomputer.get(1L))
                            .metadata(GTRecipeConstants.CLEANROOM, true)
                            .duration((advancedSMD ? 10 : 20) * SECONDS)
                            .eut(TierEU.RECIPE_IV * 5 / 16));

                    RecipeModifiers.modify(
                        "circuitassembler/quantum_assembly" + SMD_STRING[i],
                        VariantNames.ALTERED_PRICING,
                        RecipePhase.SCALE,
                        d -> {
                            d.scaleAllInputsBalanced(
                                GeneralConfig.partCostMultiplier,
                                ItemList.Circuit_Quantumprocessor.get(2L));
                            d.itemIn()
                                .condenseAll();
                        });

                    RecipeModifiers.defineBase(
                        "circuitassembler/quantum_computer" + SMD_STRING[i],
                        () -> RecipeDraft.of(circuitAssemblerRecipes)
                            .itemInputs(
                                ItemList.Circuit_Board_Fiberglass_Advanced.get(1L),
                                ItemList.Circuit_Quantumcomputer.get(2L),
                                DIODES[finalI].get(advancedSMD ? 2 : 8),
                                ItemList.Circuit_Chip_NOR.get(4L),
                                ItemList.Circuit_Chip_Ram.get(16L),
                                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Platinum, 48))
                            .fluidInputs(SubstituteFluidStack.soldering(1 * INGOTS))
                            .itemOutputs(ItemList.Circuit_Masterquantumcomputer.get(1L))
                            .metadata(GTRecipeConstants.CLEANROOM, true)
                            .duration((advancedSMD ? 10 : 20) * SECONDS)
                            .eut(TierEU.RECIPE_IV * 5 / 16));

                    RecipeModifiers.modify(
                        "circuitassembler/quantum_computer" + SMD_STRING[i],
                        VariantNames.ALTERED_PRICING,
                        RecipePhase.SCALE,
                        d -> {
                            d.scaleAllInputsBalanced(
                                GeneralConfig.partCostMultiplier,
                                ItemList.Circuit_Quantumcomputer.get(2L));
                            d.itemIn()
                                .condenseAll();
                        });

                    RecipeModifiers.defineBase(
                        "circuitassembler/quantum_mainframe" + SMD_STRING[i],
                        () -> RecipeDraft.of(circuitAssemblerRecipes)
                            .itemInputs(
                                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Aluminium, 2),
                                ItemList.Circuit_Masterquantumcomputer.get(2L),
                                INDUCTORS[finalI].get(advancedSMD ? 6 : 24),
                                CAPACITORS[finalI].get(advancedSMD ? 12 : 48),
                                ItemList.Circuit_Chip_Ram.get(24),
                                GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.AnnealedCopper, 48))
                            .fluidInputs(SubstituteFluidStack.soldering(2 * INGOTS))
                            .itemOutputs(ItemList.Circuit_Quantummainframe.get(1L))
                            .metadata(GTRecipeConstants.CLEANROOM, true)
                            .duration((advancedSMD ? 40 : 80) * SECONDS)
                            .eut(TierEU.RECIPE_IV));

                    RecipeModifiers.modify(
                        "circuitassembler/quantum_mainframe" + SMD_STRING[i],
                        VariantNames.ALTERED_PRICING,
                        RecipePhase.SCALE,
                        d -> {
                            d.scaleAllInputsBalanced(
                                GeneralConfig.partCostMultiplier,
                                ItemList.Circuit_Masterquantumcomputer.get(2L));
                            d.itemIn()
                                .condenseAll();
                        });
                }
            }

            // Advanced & optical SMD
            if (i > 1) {
                RecipeModifiers.defineBase(
                    "circuitassembler/wetware_processor" + SMD_STRING[i],
                    () -> RecipeDraft.of(circuitAssemblerRecipes)
                        .itemInputs(
                            ItemList.Circuit_Chip_NeuroCPU.get(6),
                            ItemList.Circuit_Chip_CrystalCPU.get(6),
                            ItemList.Circuit_Chip_NanoCPU.get(6),
                            CAPACITORS[finalI].get(opticalSMD ? 12 : 48),
                            TRANSISTORS[finalI].get(opticalSMD ? 12 : 48),
                            GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.YttriumBariumCuprate, 48))
                        .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(2 * INGOTS))
                        .itemOutputs(ItemList.Circuit_Neuroprocessor.get(1L))
                        .metadata(GTRecipeConstants.CLEANROOM, true)
                        .duration((opticalSMD ? 5 : 40) * SECONDS)
                        .eut((opticalSMD ? TierEU.RECIPE_UV : TierEU.RECIPE_ZPM) * 5 / 16));

                RecipeModifiers.modify(
                    "circuitassembler/wetware_processor" + SMD_STRING[i],
                    VariantNames.ALTERED_PRICING,
                    RecipePhase.SCALE,
                    d -> {
                        d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                        d.itemIn()
                            .condenseAll();
                    });

                RecipeModifiers.defineBase(
                    "circuitassembler/wetware_assembly" + SMD_STRING[i],
                    () -> RecipeDraft.of(circuitAssemblerRecipes)
                        .itemInputs(
                            ItemList.Circuit_Board_Wetware_Extreme.get(6),
                            ItemList.Circuit_Neuroprocessor.get(2),
                            INDUCTORS[finalI].get(opticalSMD ? 12 : 48),
                            CAPACITORS[finalI].get(opticalSMD ? 18 : 64),
                            ItemList.Circuit_Chip_Ram.get(64),
                            GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.YttriumBariumCuprate, 64))
                        .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(4 * INGOTS))
                        .itemOutputs(ItemList.Circuit_Wetwarecomputer.get(1L))
                        .metadata(GTRecipeConstants.CLEANROOM, true)
                        .duration(opticalSMD ? 7 * SECONDS + 8 * TICKS : 60 * SECONDS)
                        .eut((opticalSMD ? TierEU.RECIPE_UV : TierEU.RECIPE_ZPM) * 5 / 16));

                RecipeModifiers.modify(
                    "circuitassembler/wetware_assembly" + SMD_STRING[i],
                    VariantNames.ALTERED_PRICING,
                    RecipePhase.SCALE,
                    d -> {
                        d.scaleAllInputsBalanced(
                            GeneralConfig.partCostMultiplier,
                            ItemList.Circuit_Neuroprocessor.get(2));
                        d.itemIn()
                            .condenseAll();
                    });

                RecipeModifiers.defineBase(
                    "circuitassembler/wetware_computer" + SMD_STRING[i],
                    () -> RecipeDraft.of(circuitAssemblerRecipes)
                        .itemInputs(
                            ItemList.Circuit_Board_Wetware_Extreme.get(12),
                            ItemList.Circuit_Wetwarecomputer.get(2),
                            DIODES[finalI].get(opticalSMD ? 12 : 48),
                            ItemList.Circuit_Chip_NOR.get(64),
                            ItemList.Circuit_Chip_Ram.get(64),
                            GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.YttriumBariumCuprate, 64))
                        .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(4 * INGOTS))
                        .itemOutputs(ItemList.Circuit_Wetwaresupercomputer.get(1L))
                        .metadata(GTRecipeConstants.CLEANROOM, true)
                        .duration((opticalSMD ? 15 : 120) * SECONDS)
                        .eut((opticalSMD ? TierEU.RECIPE_UV : TierEU.RECIPE_ZPM) * 5 / 16));

                RecipeModifiers.modify(
                    "circuitassembler/wetware_computer" + SMD_STRING[i],
                    VariantNames.ALTERED_PRICING,
                    RecipePhase.SCALE,
                    d -> {
                        d.scaleAllInputsBalanced(
                            GeneralConfig.partCostMultiplier,
                            ItemList.Circuit_Wetwarecomputer.get(2));
                        d.itemIn()
                            .condenseAll();
                    });

                RecipeModifiers.defineBase(
                    "circuitassembler/bio_processor" + SMD_STRING[i],
                    () -> RecipeDraft.of(circuitAssemblerRecipes)
                        .itemInputs(
                            ItemList.Circuit_Chip_BioCPU.get(6),
                            ItemList.Circuit_Chip_CrystalSoC2.get(6),
                            ItemList.Circuit_Chip_NanoCPU.get(12),
                            CAPACITORS[finalI].get(opticalSMD ? 18 : 64),
                            TRANSISTORS[finalI].get(opticalSMD ? 18 : 64),
                            GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.NiobiumTitanium, 64))
                        .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(2 * INGOTS))
                        .itemOutputs(ItemList.Circuit_Bioprocessor.get(1L))
                        .metadata(GTRecipeConstants.CLEANROOM, true)
                        .duration(opticalSMD ? 7 * SECONDS + 8 * TICKS : 60 * SECONDS)
                        .eut((opticalSMD ? TierEU.RECIPE_UHV : TierEU.RECIPE_UV) * 5 / 16));

                RecipeModifiers.modify(
                    "circuitassembler/bio_processor" + SMD_STRING[i],
                    VariantNames.ALTERED_PRICING,
                    RecipePhase.SCALE,
                    d -> {
                        d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                        d.itemIn()
                            .condenseAll();
                    });

                RecipeModifiers.defineBase(
                    "circuitassembler/bio_assembly" + SMD_STRING[i],
                    () -> RecipeDraft.of(circuitAssemblerRecipes)
                        .itemInputs(
                            ItemList.Circuit_Board_Bio_Ultra.get(6),
                            ItemList.Circuit_Bioprocessor.get(2),
                            INDUCTORS[finalI].get(opticalSMD ? 18 : 64),
                            CAPACITORS[finalI].get(opticalSMD ? 24 : 64),
                            ItemList.Circuit_Chip_Ram.get(64),
                            GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.NiobiumTitanium, 64))
                        .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(4 * INGOTS))
                        .itemOutputs(ItemList.Circuit_Biowarecomputer.get(1L))
                        .metadata(GTRecipeConstants.CLEANROOM, true)
                        .duration((opticalSMD ? 10 : 80) * SECONDS)
                        .eut((opticalSMD ? TierEU.RECIPE_UHV : TierEU.RECIPE_UV) * 5 / 16));

                RecipeModifiers.modify(
                    "circuitassembler/bio_assembly" + SMD_STRING[i],
                    VariantNames.ALTERED_PRICING,
                    RecipePhase.SCALE,
                    d -> {
                        d.scaleAllInputsBalanced(
                            GeneralConfig.partCostMultiplier,
                            ItemList.Circuit_Bioprocessor.get(2));
                        d.itemIn()
                            .condenseAll();
                    });
            }
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

        RecipeModifiers.defineBase(
            "circuitassembler/nano_processor_soc",
            () -> RecipeDraft.of(circuitAssemblerRecipes)
                .itemInputs(
                    ItemList.Circuit_Board_Epoxy_Advanced.get(6),
                    ItemList.Circuit_Chip_SoC2.get(6),
                    GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Electrum, 48),
                    GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Platinum, 48))
                .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(2 * INGOTS))
                .itemOutputs(ItemList.Circuit_Nanoprocessor.get(1L))
                .metadata(GTRecipeConstants.CLEANROOM, true)
                .duration(10 * SECONDS)
                .eut(TierEU.RECIPE_LuV * 5 / 16));

        RecipeModifiers
            .modify("circuitassembler/nano_processor_soc", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                d.itemIn()
                    .condenseAll();
            });

        RecipeModifiers.defineBase(
            "circuitassembler/quantum_processor_soc",
            () -> RecipeDraft.of(circuitAssemblerRecipes)
                .itemInputs(
                    ItemList.Circuit_Board_Fiberglass_Advanced.get(6),
                    ItemList.Circuit_Chip_SoC2.get(6),
                    GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Platinum, 64),
                    GTOreDictUnificator.get(OrePrefixes.bolt, Materials.NiobiumTitanium, 48))
                .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(2 * INGOTS))
                .itemOutputs(ItemList.Circuit_Quantumprocessor.get(1L))
                .metadata(GTRecipeConstants.CLEANROOM, true)
                .duration(10 * SECONDS)
                .eut(TierEU.RECIPE_ZPM * 5 / 16));

        RecipeModifiers
            .modify("circuitassembler/quantum_processor_soc", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                d.itemIn()
                    .condenseAll();
            });

        RecipeModifiers.defineBase(
            "circuitassembler/crystal_processor",
            () -> RecipeDraft.of(circuitAssemblerRecipes)
                .itemInputs(
                    ItemList.Circuit_Board_Multifiberglass_Elite.get(6),
                    ItemList.Circuit_Chip_CrystalCPU.get(6),
                    ItemList.Circuit_Chip_NanoCPU.get(12),
                    ItemList.Circuit_Parts_CapacitorASMD.get(36),
                    ItemList.Circuit_Parts_TransistorASMD.get(36),
                    GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.NiobiumTitanium, 48))
                .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(2 * INGOTS))
                .itemOutputs(ItemList.Circuit_Crystalprocessor.get(1L))
                .metadata(GTRecipeConstants.CLEANROOM, true)
                .duration(20 * SECONDS)
                .eut(TierEU.RECIPE_LuV * 5 / 16));

        RecipeModifiers
            .modify("circuitassembler/crystal_processor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                d.itemIn()
                    .condenseAll();
            });

        RecipeModifiers.defineBase(
            "circuitassembler/crystal_processor_soc",
            () -> RecipeDraft.of(circuitAssemblerRecipes)
                .itemInputs(
                    ItemList.Circuit_Board_Multifiberglass_Elite.get(6),
                    ItemList.Circuit_Chip_CrystalSoC.get(6),
                    GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.NiobiumTitanium, 48),
                    GTOreDictUnificator.get(OrePrefixes.bolt, Materials.YttriumBariumCuprate, 48))
                .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(2 * INGOTS))
                .itemOutputs(ItemList.Circuit_Crystalprocessor.get(1L))
                .metadata(GTRecipeConstants.CLEANROOM, true)
                .duration(10 * SECONDS)
                .eut(TierEU.RECIPE_UV * 5 / 16));

        RecipeModifiers
            .modify("circuitassembler/crystal_processor_soc", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                d.itemIn()
                    .condenseAll();
            });

        RecipeModifiers.defineBase(
            "circuitassembler/crystal_assembly",
            () -> RecipeDraft.of(circuitAssemblerRecipes)
                .itemInputs(
                    ItemList.Circuit_Board_Multifiberglass_Elite.get(6),
                    ItemList.Circuit_Crystalprocessor.get(2),
                    ItemList.Circuit_Parts_InductorASMD.get(36),
                    ItemList.Circuit_Parts_CapacitorASMD.get(48),
                    ItemList.Circuit_Chip_Ram.get(64),
                    GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.NiobiumTitanium, 64))
                .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(4 * INGOTS))
                .itemOutputs(ItemList.Circuit_Crystalcomputer.get(1L))
                .metadata(GTRecipeConstants.CLEANROOM, true)
                .duration(40 * SECONDS)
                .eut(TierEU.RECIPE_LuV * 5 / 16));

        RecipeModifiers
            .modify("circuitassembler/crystal_assembly", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Circuit_Crystalprocessor.get(2));
                d.itemIn()
                    .condenseAll();
            });

        RecipeModifiers.defineBase(
            "circuitassembler/crystal_computer",
            () -> RecipeDraft.of(circuitAssemblerRecipes)
                .itemInputs(
                    ItemList.Circuit_Board_Multifiberglass_Elite.get(6),
                    ItemList.Circuit_Crystalcomputer.get(2),
                    ItemList.Circuit_Chip_Ram.get(24),
                    ItemList.Circuit_Chip_NOR.get(64),
                    ItemList.Circuit_Chip_NAND.get(64),
                    GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.NiobiumTitanium, 64))
                .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(4 * INGOTS))
                .itemOutputs(ItemList.Circuit_Ultimatecrystalcomputer.get(1L))
                .metadata(GTRecipeConstants.CLEANROOM, true)
                .duration(80 * SECONDS)
                .eut(TierEU.RECIPE_LuV * 5 / 16));

        RecipeModifiers
            .modify("circuitassembler/crystal_computer", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Circuit_Crystalcomputer.get(2));
                d.itemIn()
                    .condenseAll();
            });

        RecipeModifiers.defineBase(
            "circuitassembler/crystal_mainframe",
            () -> RecipeDraft.of(circuitAssemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Aluminium, 12),
                    ItemList.Circuit_Ultimatecrystalcomputer.get(2),
                    ItemList.Circuit_Parts_InductorASMD.get(48),
                    ItemList.Circuit_Parts_CapacitorASMD.get(64),
                    ItemList.Circuit_Chip_Ram.get(64),
                    GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorLuV, 64))
                .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(8 * INGOTS))
                .itemOutputs(ItemList.Circuit_Crystalmainframe.get(1L))
                .metadata(GTRecipeConstants.CLEANROOM, true)
                .duration(160 * SECONDS)
                .eut(TierEU.RECIPE_LuV));

        RecipeModifiers
            .modify("circuitassembler/crystal_mainframe", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(
                    GeneralConfig.partCostMultiplier,
                    ItemList.Circuit_Ultimatecrystalcomputer.get(2));
                d.itemIn()
                    .condenseAll();
            });

        RecipeModifiers.defineBase(
            "circuitassembler/wetware_processor_soc",
            () -> RecipeDraft.of(circuitAssemblerRecipes)
                .itemInputs(
                    ItemList.Circuit_Board_Wetware_Extreme.get(6),
                    ItemList.Circuit_Parts_Crystal_Chip_Wetware.get(6),
                    GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.YttriumBariumCuprate, 48),
                    GTOreDictUnificator.get(OrePrefixes.bolt, Materials.CosmicNeutronium, 48))
                .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(4 * INGOTS))
                .itemOutputs(ItemList.Circuit_Neuroprocessor.get(1L))
                .metadata(GTRecipeConstants.CLEANROOM, true)
                .duration(10 * SECONDS)
                .eut(TierEU.RECIPE_UHV * 5 / 16));

        RecipeModifiers
            .modify("circuitassembler/wetware_processor_soc", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                d.itemIn()
                    .condenseAll();
            });

        RecipeModifiers.defineBase(
            "circuitassembler/bio_processor_soc",
            () -> RecipeDraft.of(circuitAssemblerRecipes)
                .itemInputs(
                    ItemList.Circuit_Board_Bio_Ultra.get(6),
                    ItemList.Circuit_Parts_Chip_Bioware.get(6),
                    GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.NiobiumTitanium, 64),
                    MaterialsElements.STANDALONE.CHRONOMATIC_GLASS.getBolt(48))
                .fluidInputs(MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(4 * INGOTS))
                .itemOutputs(ItemList.Circuit_Bioprocessor.get(1L))
                .metadata(GTRecipeConstants.CLEANROOM, true)
                .duration(15 * SECONDS)
                .eut(TierEU.RECIPE_UEV * 5 / 16));

        RecipeModifiers
            .modify("circuitassembler/bio_processor_soc", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                d.itemIn()
                    .condenseAll();
            });

        RecipeModifiers.defineBase(
            "circuitassembler/optical_processor",
            () -> RecipeDraft.of(circuitAssemblerRecipes)
                .itemInputs(
                    ItemList.Optically_Perfected_CPU.get(6),
                    ItemList.Optically_Compatible_Memory.get(12),
                    ItemList.Circuit_Parts_CapacitorXSMD.get(64),
                    ItemList.Circuit_Parts_DiodeXSMD.get(64),
                    tectech.thing.CustomItemList.DATApipe.get(24),
                    GTOreDictUnificator.get(OrePrefixes.bolt, Materials.EnrichedHolmium, 64))
                .fluidInputs(MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(8 * INGOTS))
                .itemOutputs(ItemList.Circuit_OpticalProcessor.get(1L))
                .metadata(GTRecipeConstants.CLEANROOM, true)
                .duration(80 * SECONDS)
                .eut(TierEU.RECIPE_UHV * 5 / 16));

        RecipeModifiers
            .modify("circuitassembler/optical_processor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                d.itemIn()
                    .condenseAll();
            });
    }
}
