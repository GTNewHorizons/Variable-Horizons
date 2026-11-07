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
    }
}
