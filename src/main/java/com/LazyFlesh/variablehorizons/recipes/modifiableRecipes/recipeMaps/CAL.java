package com.LazyFlesh.variablehorizons.recipes.modifiableRecipes.recipeMaps;

import static bartworks.API.recipe.BartWorksRecipeMaps.circuitAssemblyLineRecipes;
import static com.dreammaster.scripts.IngredientFactory.getModItem;
import static gregtech.api.enums.Mods.IndustrialCraft2;
import static gregtech.api.util.GTRecipeBuilder.HALF_INGOTS;
import static gregtech.api.util.GTRecipeBuilder.INGOTS;
import static gregtech.api.util.GTRecipeBuilder.MINUTES;
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
import gregtech.api.util.GTOreDictUnificator;
import gtPlusPlus.core.material.MaterialsAlloy;

public class CAL implements Runnable {

    private static final String[] SMD_STRING = new String[] { "_smd", "_smd_advanced" };
    private static final ItemList[] RESISTORS = new ItemList[] { ItemList.Wrap_SMDResistors,
        ItemList.Wrap_AdvancedSMDResistors };
    private static final ItemList[] DIODES = new ItemList[] { ItemList.Wrap_SMDDiodes,
        ItemList.Wrap_AdvancedSMDDiodes };
    private static final ItemList[] TRANSISTORS = new ItemList[] { ItemList.Wrap_SMDTransistors,
        ItemList.Wrap_AdvancedSMDTransistors };
    private static final ItemList[] INDUCTORS = new ItemList[] { ItemList.Wrap_SMDInductors,
        ItemList.Wrap_AdvancedSMDInductors };
    private static final ItemList[] CAPACITORS = new ItemList[] { ItemList.Wrap_SMDCapacitors,
        ItemList.Wrap_AdvancedSMDCapacitors };

    @Override
    public void run() {
        RecipeModifiers.defineBase(
            "CAL/good_electronic_circuit",
            () -> RecipeDraft.of(circuitAssemblyLineRecipes)
                .itemInputs(
                    ItemList.Wrap_GoodCircuitBoards.get(1),
                    getModItem(IndustrialCraft2.ID, "itemPartCircuit", 32, 0),
                    ItemList.Wrap_SMDDiodes.get(2),
                    GTOreDictUnificator.get(OrePrefixes.wireGt16, Materials.Copper, 2))
                .fluidInputs(Materials.SolderingAlloy.getMolten(1 * HALF_INGOTS))
                .itemOutputs(ItemList.Circuit_Good.get(16))
                .duration(3 * MINUTES)
                .eut(TierEU.RECIPE_LV)
                .special(ItemList.CircuitImprint_GoodElectronicCircuit.get(0)));

        RecipeModifiers.modify("CAL/good_electronic_circuit", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                getModItem(IndustrialCraft2.ID, "itemPartCircuit", 32, 0));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "CAL/integrated_logic_circuit",
            () -> RecipeDraft.of(circuitAssemblyLineRecipes)
                .itemInputs(
                    ItemList.Wrap_CircuitBoards.get(1),
                    ItemList.Wrap_IntegratedLogicCircuits.get(1),
                    RESISTORS[0].get(2),
                    DIODES[0].get(2),
                    GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.Copper, 2),
                    GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Tin, 32))
                .fluidInputs(Materials.SolderingAlloy.getMolten(1 * HALF_INGOTS))
                .itemOutputs(ItemList.Circuit_Basic.get(16))
                .duration(2 * MINUTES)
                .eut(TierEU.RECIPE_LV / 2)
                .special(ItemList.CircuitImprint_IntegratedLogicCircuit.get(0)));

        RecipeModifiers.modify("CAL/integrated_logic_circuit", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
            d.itemIn()
                .condenseAll();
        });

        for (int i = 0; i < 2; i++) {
            int finalI = i;
            boolean advancedSMD = i == 1;
            RecipeModifiers.defineBase(
                "CAL/good_integrated_logic_circuit" + SMD_STRING[i],
                () -> RecipeDraft.of(circuitAssemblyLineRecipes)
                    .itemInputs(
                        ItemList.Wrap_GoodCircuitBoards.get(1),
                        ItemList.Circuit_Basic.get(32),
                        RESISTORS[finalI].get(advancedSMD ? 1 : 4),
                        DIODES[finalI].get(advancedSMD ? 1 : 4),
                        GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.Gold, 4),
                        GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Silver, 64))
                    .fluidInputs(Materials.SolderingAlloy.getMolten(1 * HALF_INGOTS))
                    .itemOutputs(ItemList.Circuit_Integrated_Good.get(16))
                    .duration((advancedSMD ? 2 : 4) * MINUTES)
                    .eut(TierEU.RECIPE_LV * 4 / 5)
                    .special(ItemList.CircuitImprint_GoodIntegratedCircuit.get(0)));

            RecipeModifiers.modify(
                "CAL/good_integrated_logic_circuit" + SMD_STRING[i],
                VariantNames.ALTERED_PRICING,
                RecipePhase.SCALE,
                d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Circuit_Basic.get(32));
                    d.itemIn()
                        .condenseAll();
                });

            RecipeModifiers.defineBase(
                "CAL/integrated_processor" + SMD_STRING[i],
                () -> RecipeDraft.of(circuitAssemblyLineRecipes)
                    .itemInputs(
                        ItemList.Wrap_PlasticCircuitBoards2.get(1),
                        ItemList.Wrap_CentralProcessingUnits.get(1),
                        RESISTORS[finalI].get(advancedSMD ? 1 : 4),
                        CAPACITORS[finalI].get(advancedSMD ? 1 : 4),
                        TRANSISTORS[finalI].get(advancedSMD ? 1 : 4),
                        GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.RedAlloy, 4))
                    .fluidInputs(Materials.SolderingAlloy.getMolten(1 * HALF_INGOTS))
                    .itemOutputs(ItemList.Circuit_Processor.get(16))
                    .duration((advancedSMD ? 1 : 2) * MINUTES)
                    .eut(TierEU.RECIPE_MV / 2)
                    .special(ItemList.CircuitImprint_IntegratedProcessor.get(0)));

            RecipeModifiers.modify(
                "CAL/integrated_processor" + SMD_STRING[i],
                VariantNames.ALTERED_PRICING,
                RecipePhase.SCALE,
                d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                    d.itemIn()
                        .condenseAll();
                });

            RecipeModifiers.defineBase(
                "CAL/processor_assembly" + SMD_STRING[i],
                () -> RecipeDraft.of(circuitAssemblyLineRecipes)
                    .itemInputs(
                        ItemList.Wrap_PlasticCircuitBoards2.get(1L),
                        ItemList.Circuit_Processor.get(32L),
                        INDUCTORS[finalI].get(advancedSMD ? 1 : 4),
                        CAPACITORS[finalI].get(advancedSMD ? 2 : 8),
                        ItemList.Wrap_RandomAccessMemoryChips.get(4L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.RedAlloy, 8))
                    .fluidInputs(Materials.SolderingAlloy.getMolten(1 * INGOTS))
                    .itemOutputs(ItemList.Circuit_Advanced.get(16L))
                    .duration((advancedSMD ? 2 : 4) * MINUTES)
                    .eut(advancedSMD ? TierEU.RECIPE_MV * 4 / 5 : TierEU.RECIPE_MV)
                    .special(ItemList.CircuitImprint_ProcessorAssembly.get(0)));

            RecipeModifiers.modify(
                "CAL/processor_assembly" + SMD_STRING[i],
                VariantNames.ALTERED_PRICING,
                RecipePhase.SCALE,
                d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Circuit_Processor.get(2L));
                    d.itemIn()
                        .condenseAll();
                });

            RecipeModifiers.defineBase(
                "CAL/workstation" + SMD_STRING[i],
                () -> RecipeDraft.of(circuitAssemblyLineRecipes)
                    .itemInputs(
                        ItemList.Wrap_PlasticCircuitBoards2.get(1),
                        ItemList.Circuit_Advanced.get(32),
                        DIODES[finalI].get(advancedSMD ? 1 : 4),
                        ItemList.Wrap_RandomAccessMemoryChips.get(8),
                        GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.Electrum, 16),
                        GTOreDictUnificator.get(OrePrefixes.bolt, Materials.BlueAlloy, 64))
                    .fluidInputs(Materials.SolderingAlloy.getMolten(1 * INGOTS))
                    .itemOutputs(ItemList.Circuit_Data.get(16L))
                    .duration((advancedSMD ? 2 : 4) * MINUTES)
                    .eut(TierEU.RECIPE_MV)
                    .special(ItemList.CircuitImprint_Workstation.get(0)));

            RecipeModifiers
                .modify("CAL/workstation" + SMD_STRING[i], VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Circuit_Advanced.get(2L));
                    d.itemIn()
                        .condenseAll();
                });

            RecipeModifiers.defineBase(
                "CAL/mainframe" + SMD_STRING[i],
                () -> RecipeDraft.of(circuitAssemblyLineRecipes)
                    .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Aluminium, 32),
                        ItemList.Circuit_Data.get(32),
                        INDUCTORS[finalI].get(advancedSMD ? 3 : 12),
                        CAPACITORS[finalI].get(advancedSMD ? 4 : 24),
                        ItemList.Wrap_RandomAccessMemoryChips.get(16),
                        GTOreDictUnificator.get(OrePrefixes.wireGt16, Materials.AnnealedCopper, advancedSMD ? 16 : 24))
                    .fluidInputs(Materials.SolderingAlloy.getMolten(2 * INGOTS))
                    .itemOutputs(ItemList.Circuit_Elite.get(16L))
                    .duration((advancedSMD ? 8 : 16) * MINUTES)
                    .eut(TierEU.RECIPE_HV)
                    .special(ItemList.CircuitImprint_Mainframe.get(0)));

            RecipeModifiers
                .modify("CAL/mainframe" + SMD_STRING[i], VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Circuit_Data.get(2L));
                    d.itemIn()
                        .condenseAll();
                });

            RecipeModifiers.defineBase(
                "CAL/nano_processor" + SMD_STRING[i],
                () -> RecipeDraft.of(circuitAssemblyLineRecipes)
                    .itemInputs(
                        ItemList.Wrap_AdvancedCircuitBoards.get(1),
                        ItemList.Wrap_NanocomponentCentralProcessingUnits.get(1),
                        RESISTORS[finalI].get(advancedSMD ? 2 : 8),
                        CAPACITORS[finalI].get(advancedSMD ? 2 : 8),
                        TRANSISTORS[finalI].get(advancedSMD ? 2 : 8),
                        GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.Electrum, 8))
                    .fluidInputs(Materials.SolderingAlloy.getMolten(1 * HALF_INGOTS))
                    .itemOutputs(ItemList.Circuit_Nanoprocessor.get(16L))
                    .duration((advancedSMD ? 1 : 2) * MINUTES)
                    .eut(TierEU.RECIPE_EV * 5 / 16)
                    .special(ItemList.CircuitImprint_NanoProcessor.get(0)));

            RecipeModifiers
                .modify("CAL/nano_processor" + SMD_STRING[i], VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                    d.itemIn()
                        .condenseAll();
                });

            RecipeModifiers.defineBase(
                "CAL/nano_assembly" + SMD_STRING[i],
                () -> RecipeDraft.of(circuitAssemblyLineRecipes)
                    .itemInputs(
                        ItemList.Wrap_AdvancedCircuitBoards.get(1),
                        ItemList.Circuit_Nanoprocessor.get(32),
                        INDUCTORS[finalI].get(advancedSMD ? 2 : 8),
                        CAPACITORS[finalI].get(advancedSMD ? 2 : 8),
                        ItemList.Wrap_RandomAccessMemoryChips.get(8),
                        GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.Electrum, 16))
                    .fluidInputs(Materials.SolderingAlloy.getMolten(1 * INGOTS))
                    .itemOutputs(ItemList.Circuit_Nanocomputer.get(16L))
                    .duration((advancedSMD ? 2 : 4) * MINUTES)
                    .eut(TierEU.RECIPE_EV * 5 / 16)
                    .special(ItemList.CircuitImprint_NanoAssembly.get(0)));

            RecipeModifiers
                .modify("CAL/nano_assembly" + SMD_STRING[i], VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Circuit_Nanoprocessor.get(32));
                    d.itemIn()
                        .condenseAll();
                });

            RecipeModifiers.defineBase(
                "CAL/nano_computer" + SMD_STRING[i],
                () -> RecipeDraft.of(circuitAssemblyLineRecipes)
                    .itemInputs(
                        ItemList.Wrap_AdvancedCircuitBoards.get(1),
                        ItemList.Circuit_Nanocomputer.get(32),
                        DIODES[finalI].get(advancedSMD ? 2 : 8),
                        ItemList.Wrap_NORMemoryChips.get(4),
                        ItemList.Wrap_RandomAccessMemoryChips.get(16),
                        GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.Electrum, 16))
                    .fluidInputs(Materials.SolderingAlloy.getMolten(1 * INGOTS))
                    .itemOutputs(ItemList.Circuit_Elitenanocomputer.get(16L))
                    .duration((advancedSMD ? 2 : 4) * MINUTES)
                    .eut(TierEU.RECIPE_EV * 5 / 16)
                    .special(ItemList.CircuitImprint_NanoSupercomputer.get(0)));

            RecipeModifiers
                .modify("CAL/nano_computer" + SMD_STRING[i], VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Circuit_Nanocomputer.get(32));
                    d.itemIn()
                        .condenseAll();
                });

            RecipeModifiers.defineBase(
                "CAL/nano_mainframe" + SMD_STRING[i],
                () -> RecipeDraft.of(circuitAssemblyLineRecipes)
                    .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Aluminium, 32),
                        ItemList.Circuit_Elitenanocomputer.get(32),
                        INDUCTORS[finalI].get(advancedSMD ? 4 : 16),
                        CAPACITORS[finalI].get(advancedSMD ? 8 : 32),
                        ItemList.Wrap_RandomAccessMemoryChips.get(16),
                        GTOreDictUnificator.get(OrePrefixes.wireGt16, Materials.AnnealedCopper, 32))
                    .fluidInputs(Materials.SolderingAlloy.getMolten(2 * INGOTS))
                    .itemOutputs(ItemList.Circuit_Master.get(16L))
                    .duration((advancedSMD ? 8 : 16) * MINUTES)
                    .eut(TierEU.RECIPE_EV)
                    .special(ItemList.CircuitImprint_NanoMainframe.get(0)));

            RecipeModifiers
                .modify("CAL/nano_mainframe" + SMD_STRING[i], VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                    d.scaleAllInputsBalanced(
                        GeneralConfig.partCostMultiplier,
                        ItemList.Circuit_Elitenanocomputer.get(32));
                    d.itemIn()
                        .condenseAll();
                });

            RecipeModifiers.defineBase(
                "CAL/quantum_processor" + SMD_STRING[i],
                () -> RecipeDraft.of(circuitAssemblyLineRecipes)
                    .itemInputs(
                        ItemList.Wrap_MoreAdvancedCircuitBoards.get(1),
                        ItemList.Wrap_QBitProcessingUnits.get(1),
                        ItemList.Wrap_NanocomponentCentralProcessingUnits.get(1),
                        CAPACITORS[finalI].get(advancedSMD ? 3 : 12),
                        TRANSISTORS[finalI].get(advancedSMD ? 3 : 12),
                        GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.Platinum, 16))
                    .fluidInputs(Materials.SolderingAlloy.getMolten(1 * HALF_INGOTS))
                    .itemOutputs(ItemList.Circuit_Quantumprocessor.get(16L))
                    .duration((advancedSMD ? 1 : 2) * MINUTES)
                    .eut(TierEU.RECIPE_IV * 5 / 16)
                    .special(ItemList.CircuitImprint_QuantumProcessor.get(0)));

            RecipeModifiers
                .modify("CAL/quantum_processor" + SMD_STRING[i], VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                    d.itemIn()
                        .condenseAll();
                });

            RecipeModifiers.defineBase(
                "CAL/quantum_assembly" + SMD_STRING[i],
                () -> RecipeDraft.of(circuitAssemblyLineRecipes)
                    .itemInputs(
                        ItemList.Wrap_MoreAdvancedCircuitBoards.get(1),
                        ItemList.Circuit_Quantumprocessor.get(32),
                        INDUCTORS[finalI].get(advancedSMD ? 3 : 12),
                        CAPACITORS[finalI].get(advancedSMD ? 4 : 16),
                        ItemList.Wrap_RandomAccessMemoryChips.get(4),
                        GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.Platinum, 24))
                    .fluidInputs(Materials.SolderingAlloy.getMolten(1 * INGOTS))
                    .itemOutputs(ItemList.Circuit_Quantumcomputer.get(16L))
                    .duration((advancedSMD ? 2 : 4) * MINUTES)
                    .eut(TierEU.RECIPE_IV * 5 / 16)
                    .special(ItemList.CircuitImprint_QuantumAssembly.get(0)));

            RecipeModifiers
                .modify("CAL/quantum_assembly" + SMD_STRING[i], VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                    d.scaleAllInputsBalanced(
                        GeneralConfig.partCostMultiplier,
                        ItemList.Circuit_Quantumprocessor.get(32));
                    d.itemIn()
                        .condenseAll();
                });

            RecipeModifiers.defineBase(
                "CAL/quantum_computer" + SMD_STRING[i],
                () -> RecipeDraft.of(circuitAssemblyLineRecipes)
                    .itemInputs(
                        ItemList.Wrap_MoreAdvancedCircuitBoards.get(1),
                        ItemList.Circuit_Quantumcomputer.get(32),
                        DIODES[finalI].get(advancedSMD ? 2 : 8),
                        ItemList.Wrap_NORMemoryChips.get(4),
                        ItemList.Wrap_RandomAccessMemoryChips.get(16),
                        GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.Platinum, 48))
                    .fluidInputs(Materials.SolderingAlloy.getMolten(1 * INGOTS))
                    .itemOutputs(ItemList.Circuit_Masterquantumcomputer.get(16L))
                    .duration((advancedSMD ? 2 : 4) * MINUTES)
                    .eut(TierEU.RECIPE_IV * 5 / 16)
                    .special(ItemList.CircuitImprint_QuantumSupercomputer.get(0)));

            RecipeModifiers
                .modify("CAL/quantum_computer" + SMD_STRING[i], VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                    d.scaleAllInputsBalanced(
                        GeneralConfig.partCostMultiplier,
                        ItemList.Circuit_Quantumcomputer.get(32));
                    d.itemIn()
                        .condenseAll();
                });

            RecipeModifiers.defineBase(
                "CAL/quantum_mainframe" + SMD_STRING[i],
                () -> RecipeDraft.of(circuitAssemblyLineRecipes)
                    .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Aluminium, 32),
                        ItemList.Circuit_Masterquantumcomputer.get(32),
                        INDUCTORS[finalI].get(advancedSMD ? 6 : 24),
                        CAPACITORS[finalI].get(advancedSMD ? 12 : 48),
                        ItemList.Wrap_RandomAccessMemoryChips.get(24),
                        GTOreDictUnificator.get(OrePrefixes.wireGt16, Materials.AnnealedCopper, 48))
                    .fluidInputs(Materials.SolderingAlloy.getMolten(2 * INGOTS))
                    .itemOutputs(ItemList.Circuit_Quantummainframe.get(16L))
                    .duration((advancedSMD ? 8 : 16) * MINUTES)
                    .eut(TierEU.RECIPE_IV)
                    .special(ItemList.CircuitImprint_QuantumMainframe.get(0)));

            RecipeModifiers
                .modify("CAL/quantum_mainframe" + SMD_STRING[i], VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                    d.scaleAllInputsBalanced(
                        GeneralConfig.partCostMultiplier,
                        ItemList.Circuit_Masterquantumcomputer.get(32));
                    d.itemIn()
                        .condenseAll();
                });
        }

        RecipeModifiers.defineBase(
            "CAL/integrated_processor_soc",
            () -> RecipeDraft.of(circuitAssemblyLineRecipes)
                .itemInputs(
                    ItemList.Wrap_PlasticCircuitBoards2.get(1),
                    ItemList.Wrap_SoCs.get(1),
                    GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.RedAlloy, 4),
                    GTOreDictUnificator.get(OrePrefixes.bolt, Materials.AnnealedCopper, 64))
                .fluidInputs(Materials.SolderingAlloy.getMolten(1 * HALF_INGOTS))
                .itemOutputs(ItemList.Circuit_Processor.get(16))
                .duration(30 * SECONDS)
                .eut(TierEU.RECIPE_IV * 5 / 16)
                .special(ItemList.CircuitImprint_IntegratedProcessor.get(0)));

        RecipeModifiers.modify("CAL/integrated_processor_soc", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "CAL/nano_processor_soc",
            () -> RecipeDraft.of(circuitAssemblyLineRecipes)
                .itemInputs(
                    ItemList.Wrap_AdvancedCircuitBoards.get(1),
                    ItemList.Wrap_ASoCs.get(1),
                    GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.Electrum, 8),
                    GTOreDictUnificator.get(OrePrefixes.bolt, Materials.Platinum, 64))
                .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(1 * HALF_INGOTS))
                .itemOutputs(ItemList.Circuit_Nanoprocessor.get(16L))
                .duration(30 * SECONDS)
                .eut(TierEU.RECIPE_LuV * 5 / 16)
                .special(ItemList.CircuitImprint_NanoProcessor.get(0)));

        RecipeModifiers.modify("CAL/nano_processor_soc", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "CAL/quantum_processor_soc",
            () -> RecipeDraft.of(circuitAssemblyLineRecipes)
                .itemInputs(
                    ItemList.Wrap_MoreAdvancedCircuitBoards.get(1),
                    ItemList.Wrap_ASoCs.get(1),
                    GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.Platinum, 16),
                    GTOreDictUnificator.get(OrePrefixes.bolt, Materials.NiobiumTitanium, 64))
                .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(1 * HALF_INGOTS))
                .itemOutputs(ItemList.Circuit_Quantumprocessor.get(16L))
                .duration(30 * SECONDS)
                .eut(TierEU.RECIPE_ZPM * 5 / 16)
                .special(ItemList.CircuitImprint_QuantumProcessor.get(0)));

        RecipeModifiers.modify("CAL/quantum_processor_soc", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "CAL/crystal_processor",
            () -> RecipeDraft.of(circuitAssemblyLineRecipes)
                .itemInputs(
                    ItemList.Wrap_EliteCircuitBoards.get(1),
                    ItemList.Wrap_CrystalProcessingUnits.get(1),
                    ItemList.Wrap_NanocomponentCentralProcessingUnits.get(2),
                    ItemList.Wrap_AdvancedSMDCapacitors.get(6),
                    ItemList.Wrap_AdvancedSMDTransistors.get(6),
                    GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.NiobiumTitanium, 8))
                .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(1 * HALF_INGOTS))
                .itemOutputs(ItemList.Circuit_Crystalprocessor.get(16L))
                .duration(60 * SECONDS)
                .eut(TierEU.RECIPE_LuV * 5 / 16)
                .special(ItemList.CircuitImprint_CrystalProcessor.get(0)));

        RecipeModifiers.modify("CAL/crystal_processor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "CAL/crystal_processor_soc",
            () -> RecipeDraft.of(circuitAssemblyLineRecipes)
                .itemInputs(
                    ItemList.Wrap_EliteCircuitBoards.get(1),
                    ItemList.Wrap_CrystalSoCs.get(1),
                    GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.NiobiumTitanium, 8),
                    GTOreDictUnificator.get(OrePrefixes.bolt, Materials.YttriumBariumCuprate, 64))
                .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(1 * HALF_INGOTS))
                .itemOutputs(ItemList.Circuit_Crystalprocessor.get(16L))
                .duration(30 * SECONDS)
                .eut(TierEU.RECIPE_UV * 5 / 16)
                .special(ItemList.CircuitImprint_CrystalProcessor.get(0)));

        RecipeModifiers.modify("CAL/crystal_processor_soc", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "CAL/crystal_assembly",
            () -> RecipeDraft.of(circuitAssemblyLineRecipes)
                .itemInputs(
                    ItemList.Wrap_EliteCircuitBoards.get(1),
                    ItemList.Circuit_Crystalprocessor.get(32),
                    ItemList.Wrap_AdvancedSMDInductors.get(6),
                    ItemList.Wrap_AdvancedSMDCapacitors.get(8),
                    ItemList.Wrap_RandomAccessMemoryChips.get(24),
                    GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.NiobiumTitanium, 16))
                .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(1 * INGOTS))
                .itemOutputs(ItemList.Circuit_Crystalcomputer.get(16L))
                .duration(2 * MINUTES)
                .eut(TierEU.RECIPE_LuV * 5 / 16)
                .special(ItemList.CircuitImprint_CrystalAssembly.get(0)));

        RecipeModifiers.modify("CAL/crystal_assembly", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Circuit_Crystalprocessor.get(32));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "CAL/crystal_computer",
            () -> RecipeDraft.of(circuitAssemblyLineRecipes)
                .itemInputs(
                    ItemList.Wrap_EliteCircuitBoards.get(1),
                    ItemList.Circuit_Crystalcomputer.get(32),
                    ItemList.Wrap_RandomAccessMemoryChips.get(4),
                    ItemList.Wrap_NORMemoryChips.get(32),
                    ItemList.Wrap_NANDMemoryChips.get(64),
                    GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.NiobiumTitanium, 32))
                .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(1 * INGOTS))
                .itemOutputs(ItemList.Circuit_Ultimatecrystalcomputer.get(16L))
                .duration(4 * MINUTES)
                .eut(TierEU.RECIPE_LuV * 5 / 16)
                .special(ItemList.CircuitImprint_CrystalSupercomputer.get(0)));

        RecipeModifiers.modify("CAL/crystal_computer", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Circuit_Crystalcomputer.get(32));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "CAL/crystal_mainframe",
            () -> RecipeDraft.of(circuitAssemblyLineRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Aluminium, 32),
                    ItemList.Circuit_Ultimatecrystalcomputer.get(32),
                    ItemList.Wrap_AdvancedSMDInductors.get(8),
                    ItemList.Wrap_AdvancedSMDCapacitors.get(16),
                    ItemList.Wrap_RandomAccessMemoryChips.get(32),
                    GTOreDictUnificator.get(OrePrefixes.wireGt16, Materials.SuperconductorLuV, 16))
                .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(2 * INGOTS))
                .itemOutputs(ItemList.Circuit_Crystalmainframe.get(16L))
                .duration(8 * MINUTES)
                .eut(TierEU.RECIPE_LuV)
                .special(ItemList.CircuitImprint_CrystalMainframe.get(0)));

        RecipeModifiers.modify("CAL/crystal_mainframe", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                ItemList.Circuit_Ultimatecrystalcomputer.get(32));
            d.itemIn()
                .condenseAll();
        });
    }
}
