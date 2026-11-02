package com.LazyFlesh.variablehorizons.recipes.modifiableRecipes.recipeMaps;

import static gregtech.api.recipe.RecipeMaps.assemblerRecipes;
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
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;

public class Assembler implements Runnable {

    private static final Materials[] RUBBERS = new Materials[] { Materials.Rubber, Materials.RubberSilicone,
        Materials.StyreneButadieneRubber };
    private static final String[] RUBBER_NAMES = new String[] { "rubber", "silicone", "styrene-butadiene" };

    @Override
    public void run() {

        // LV Parts
        {
            RecipeModifiers.defineBase(
                "assembler/lv_motor",
                () -> RecipeDraft.of(assemblerRecipes)
                    .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.IronMagnetic, 1L),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Iron, 2L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 4L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 2L))
                    .itemOutputs(ItemList.Electric_Motor_LV.get(1L))
                    .duration(20 * TICKS)
                    .eut(TierEU.RECIPE_LV));

            RecipeModifiers.modify("assembler/lv_motor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                d.itemIn()
                    .condenseAll();
            });

            for (int i = 0; i < RUBBERS.length; i++) {
                int finalI = i;
                RecipeModifiers.defineBase(
                    "assembler/lv_pump_" + RUBBER_NAMES[i],
                    () -> RecipeDraft.of(assemblerRecipes)
                        .itemInputs(
                            ItemList.Electric_Motor_LV.get(1),
                            GTOreDictUnificator.get(OrePrefixes.ring, RUBBERS[finalI], 2L),
                            GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Tin, 1L),
                            GTOreDictUnificator.get(OrePrefixes.screw, Materials.Tin, 1L),
                            GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1L),
                            GTOreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Bronze, 1L))
                        .itemOutputs(ItemList.Electric_Pump_LV.get(1L))
                        .duration(20 * TICKS)
                        .eut(TierEU.RECIPE_LV));

                RecipeModifiers.modify(
                    "assembler/lv_pump_" + RUBBER_NAMES[i],
                    VariantNames.ALTERED_PRICING,
                    RecipePhase.SCALE,
                    d -> {
                        d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Electric_Motor_LV.get(1));
                        d.itemIn()
                            .condenseAll();
                    });

                RecipeModifiers.defineBase(
                    "assembler/lv_conveyor_" + RUBBER_NAMES[i],
                    () -> RecipeDraft.of(assemblerRecipes)
                        .itemInputs(
                            ItemList.Electric_Motor_LV.get(2),
                            GTOreDictUnificator.get(OrePrefixes.plate, RUBBERS[finalI], 6L),
                            GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1L))
                        .itemOutputs(ItemList.Conveyor_Module_LV.get(1L))
                        .duration(20 * TICKS)
                        .eut(TierEU.RECIPE_LV));

                RecipeModifiers.modify(
                    "assembler/lv_conveyor_" + RUBBER_NAMES[i],
                    VariantNames.ALTERED_PRICING,
                    RecipePhase.SCALE,
                    d -> {
                        d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Electric_Motor_LV.get(1));
                        d.itemIn()
                            .condenseAll();
                    });
            }

            RecipeModifiers.defineBase(
                "assembler/lv_piston",
                () -> RecipeDraft.of(assemblerRecipes)
                    .itemInputs(
                        ItemList.Electric_Motor_LV.get(1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 3L),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 2L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 2L),
                        GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Steel, 1L))
                    .itemOutputs(ItemList.Electric_Piston_LV.get(1L))
                    .duration(20 * TICKS)
                    .eut(TierEU.RECIPE_LV));

            RecipeModifiers.modify("assembler/lv_piston", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Electric_Motor_LV.get(1));
                d.itemIn()
                    .condenseAll();
            });

            RecipeModifiers.defineBase(
                "assembler/lv_robot_arm",
                () -> RecipeDraft.of(assemblerRecipes)
                    .itemInputs(
                        ItemList.Electric_Motor_LV.get(2),
                        ItemList.Electric_Piston_LV.get(1),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 2L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 1L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 3L))
                    .itemOutputs(ItemList.Robot_Arm_LV.get(1L))
                    .duration(20 * TICKS)
                    .eut(TierEU.RECIPE_LV));

            RecipeModifiers.modify("assembler/lv_robot_arm", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(
                    GeneralConfig.partCostMultiplier,
                    ItemList.Electric_Motor_LV.get(1),
                    ItemList.Electric_Piston_LV.get(1));
                d.itemIn()
                    .condenseAll();
            });

            RecipeModifiers.defineBase(
                "assembler/lv_emitter",
                () -> RecipeDraft.of(assemblerRecipes)
                    .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.gem, Materials.CertusQuartz, 1L),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Brass, 4L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 2L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 2L))
                    .itemOutputs(ItemList.Emitter_LV.get(1L))
                    .duration(20 * TICKS)
                    .eut(TierEU.RECIPE_LV));

            RecipeModifiers.modify("assembler/lv_emitter", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                d.itemIn()
                    .condenseAll();
            });

            RecipeModifiers.defineBase(
                "assembler/lv_sensor",
                () -> RecipeDraft.of(assemblerRecipes)
                    .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.gem, Materials.CertusQuartz, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 4L),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Brass, 1L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 1L))
                    .itemOutputs(ItemList.Sensor_LV.get(1L))
                    .duration(20 * TICKS)
                    .eut(TierEU.RECIPE_LV));

            RecipeModifiers.modify("assembler/lv_sensor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                d.itemIn()
                    .condenseAll();
            });

            RecipeModifiers.defineBase(
                "assembler/lv_field_gen",
                () -> RecipeDraft.of(assemblerRecipes)
                    .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.EnderPearl, 1L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 4L),
                        GTUtility.getIntegratedCircuit(1))
                    .fluidInputs(Materials.RedSteel.getMolten(288L))
                    .itemOutputs(ItemList.Field_Generator_LV.get(1L))
                    .duration(30 * SECONDS)
                    .eut(TierEU.RECIPE_LV));

            RecipeModifiers.modify("assembler/lv_field_gen", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                d.itemIn()
                    .condenseAll();
            });

            RecipeModifiers.defineBase(
                "assembler/lv_fluid_regulator",
                () -> RecipeDraft.of(assemblerRecipes)
                    .itemInputs(
                        ItemList.Electric_Pump_LV.get(1),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 2L),
                        GTUtility.getIntegratedCircuit(3))
                    .itemOutputs(ItemList.FluidRegulator_LV.get(1L))
                    .duration(20 * SECONDS)
                    .eut(TierEU.RECIPE_LV));

            RecipeModifiers
                .modify("assembler/lv_fluid_regulator", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Electric_Pump_LV.get(1));
                    d.itemIn()
                        .condenseAll();
                });

            RecipeModifiers.defineBase(
                "assembler/lv_casing",
                () -> RecipeDraft.of(assemblerRecipes)
                    .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 8L),
                        GTUtility.getIntegratedCircuit(8))
                    .itemOutputs(ItemList.Casing_LV.get(1L))
                    .duration(50 * TICKS)
                    .eut(TierEU.RECIPE_LV / 2));

            RecipeModifiers.modify("assembler/lv_casing", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                d.itemIn()
                    .condenseAll();
            });

            RecipeModifiers.defineBase(
                "assembler/lv_hull",
                () -> RecipeDraft.of(assemblerRecipes)
                    .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 2L),
                        ItemList.Casing_LV.get(1))
                    .fluidInputs(Materials.Polyethylene.getMolten(288L))
                    .itemOutputs(ItemList.Hull_LV.get(1L))
                    .duration(50 * TICKS)
                    .eut(TierEU.RECIPE_LV / 2));

            RecipeModifiers.modify("assembler/lv_hull", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Casing_LV.get(1));
                d.itemIn()
                    .condenseAll();
            });
        }
    }

}
