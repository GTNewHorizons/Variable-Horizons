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
        circuitStuff();
        lvParts();
        mvParts();
        hvParts();
        evParts();
        ivParts();
    }

    private static void circuitStuff() {
        RecipeModifiers.defineBase(
            "assembler/vacuum_tube_1",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    ItemList.Circuit_Parts_Glass_Tube.get(2L),
                    GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 4L),
                    GTOreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 4L),
                    GTUtility.getIntegratedCircuit(5))
                .fluidInputs(Materials.Redstone.getMolten(144))
                .itemOutputs(ItemList.Circuit_Parts_Vacuum_Tube.get(2L))
                .duration(8 * SECONDS)
                .eut(TierEU.RECIPE_LV / 4));

        RecipeModifiers.defineBase(
            "assembler/vacuum_tube_2",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    ItemList.Circuit_Parts_Glass_Tube.get(4L),
                    GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 4L),
                    GTOreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 4L),
                    GTUtility.getIntegratedCircuit(5))
                .fluidInputs(Materials.RedAlloy.getMolten(72))
                .itemOutputs(ItemList.Circuit_Parts_Vacuum_Tube.get(4L))
                .duration(8 * SECONDS)
                .eut(TierEU.RECIPE_LV / 4));

        RecipeModifiers.defineBase(
            "assembler/vacuum_tube_3",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    ItemList.Circuit_Parts_Glass_Tube.get(4L),
                    GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 4L),
                    GTOreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 4L),
                    GTUtility.getIntegratedCircuit(5))
                .fluidInputs(Materials.RedstoneAlloy.getMolten(72))
                .itemOutputs(ItemList.Circuit_Parts_Vacuum_Tube.get(8L))
                .duration(8 * SECONDS)
                .eut(TierEU.RECIPE_LV / 4));

        RecipeModifiers.defineBase(
            "assembler/vacuum_tube_4",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    ItemList.Circuit_Parts_Glass_Tube.get(4L),
                    GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.AnnealedCopper, 4L),
                    GTOreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 4L),
                    GTUtility.getIntegratedCircuit(5))
                .fluidInputs(Materials.RedAlloy.getMolten(72))
                .itemOutputs(ItemList.Circuit_Parts_Vacuum_Tube.get(8L))
                .duration(8 * SECONDS)
                .eut(TierEU.RECIPE_LV / 4));

        RecipeModifiers.defineBase(
            "assembler/vacuum_tube_5",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    ItemList.Circuit_Parts_Glass_Tube.get(4L),
                    GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.AnnealedCopper, 4L),
                    GTOreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 4L),
                    GTUtility.getIntegratedCircuit(5))
                .fluidInputs(Materials.RedstoneAlloy.getMolten(72))
                .itemOutputs(ItemList.Circuit_Parts_Vacuum_Tube.get(16L))
                .duration(8 * SECONDS)
                .eut(TierEU.RECIPE_LV / 4));

        for (int i = 1; i < 6; i++) {
            RecipeModifiers.modify("assembler/vacuum_tube_" + i, VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                d.itemIn()
                    .condenseAll();
            });
        }
    }

    private static void lvParts() {
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

            RecipeModifiers
                .modify("assembler/lv_pump_" + RUBBER_NAMES[i], VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
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
                ItemList.Electric_Piston_LV.get(1),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 1L));
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
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 1L));
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
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 1L));
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
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 1L));
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

    private static void mvParts() {
        RecipeModifiers.defineBase(
            "assembler/mv_motor",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.stick, Materials.SteelMagnetic, 1L),
                    GTOreDictUnificator.get(OrePrefixes.stick, Materials.Aluminium, 2L),
                    GTOreDictUnificator.get(OrePrefixes.wireGt02, Materials.Cupronickel, 4L),
                    GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 2L))
                .itemOutputs(ItemList.Electric_Motor_MV.get(1L))
                .duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV));

        RecipeModifiers.modify("assembler/mv_motor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
            d.itemIn()
                .condenseAll();
        });

        for (int i = 0; i < RUBBERS.length; i++) {
            int finalI = i;
            RecipeModifiers.defineBase(
                "assembler/mv_pump_" + RUBBER_NAMES[i],
                () -> RecipeDraft.of(assemblerRecipes)
                    .itemInputs(
                        ItemList.Electric_Motor_MV.get(1),
                        GTOreDictUnificator.get(OrePrefixes.ring, RUBBERS[finalI], 2L),
                        GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Bronze, 1L),
                        GTOreDictUnificator.get(OrePrefixes.screw, Materials.Bronze, 1L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 1L),
                        GTOreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Steel, 1L))
                    .itemOutputs(ItemList.Electric_Pump_MV.get(1L))
                    .duration(20 * TICKS)
                    .eut(TierEU.RECIPE_LV));

            RecipeModifiers
                .modify("assembler/mv_pump_" + RUBBER_NAMES[i], VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Electric_Motor_MV.get(1));
                    d.itemIn()
                        .condenseAll();
                });

            RecipeModifiers.defineBase(
                "assembler/mv_conveyor_" + RUBBER_NAMES[i],
                () -> RecipeDraft.of(assemblerRecipes)
                    .itemInputs(
                        ItemList.Electric_Motor_MV.get(2),
                        GTOreDictUnificator.get(OrePrefixes.plate, RUBBERS[finalI], 6L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 1L))
                    .itemOutputs(ItemList.Conveyor_Module_MV.get(1L))
                    .duration(20 * TICKS)
                    .eut(TierEU.RECIPE_LV));

            RecipeModifiers.modify(
                "assembler/mv_conveyor_" + RUBBER_NAMES[i],
                VariantNames.ALTERED_PRICING,
                RecipePhase.SCALE,
                d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Electric_Motor_MV.get(1));
                    d.itemIn()
                        .condenseAll();
                });
        }

        RecipeModifiers.defineBase(
            "assembler/mv_piston",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    ItemList.Electric_Motor_MV.get(1),
                    GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 3L),
                    GTOreDictUnificator.get(OrePrefixes.stick, Materials.Aluminium, 2L),
                    GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 2L),
                    GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Aluminium, 1L))
                .itemOutputs(ItemList.Electric_Piston_MV.get(1L))
                .duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV));

        RecipeModifiers.modify("assembler/mv_piston", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Electric_Motor_MV.get(1));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/mv_robot_arm",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    ItemList.Electric_Motor_MV.get(2),
                    ItemList.Electric_Piston_MV.get(1),
                    GTOreDictUnificator.get(OrePrefixes.stick, Materials.Aluminium, 2L),
                    GTOreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 1L),
                    GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 3L))
                .itemOutputs(ItemList.Robot_Arm_MV.get(1L))
                .duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV));

        RecipeModifiers.modify("assembler/mv_robot_arm", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                ItemList.Electric_Motor_MV.get(1),
                ItemList.Electric_Piston_MV.get(1),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 1L));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/mv_emitter",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.gem, Materials.EnderPearl, 1L),
                    GTOreDictUnificator.get(OrePrefixes.stick, Materials.Electrum, 4L),
                    GTOreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 2L),
                    GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 2L))
                .itemOutputs(ItemList.Emitter_MV.get(1L))
                .duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV));

        RecipeModifiers.modify("assembler/mv_emitter", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 1L));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/mv_sensor",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.gemFlawless, Materials.Emerald, 1L),
                    GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 4L),
                    GTOreDictUnificator.get(OrePrefixes.stick, Materials.Electrum, 1L),
                    GTOreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 1L))
                .itemOutputs(ItemList.Sensor_MV.get(1L))
                .duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV));

        RecipeModifiers.modify("assembler/mv_sensor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 1L));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/mv_field_gen",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.plate, Materials.EnderEye, 1L),
                    GTOreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 4L),
                    GTUtility.getIntegratedCircuit(1))
                .fluidInputs(Materials.Titanium.getMolten(288L))
                .itemOutputs(ItemList.Field_Generator_MV.get(1L))
                .duration(30 * SECONDS)
                .eut(TierEU.RECIPE_MV));

        RecipeModifiers.modify("assembler/mv_field_gen", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 1L));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/mv_casing",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 8L),
                    GTUtility.getIntegratedCircuit(8))
                .itemOutputs(ItemList.Casing_MV.get(1L))
                .duration(50 * TICKS)
                .eut(TierEU.RECIPE_LV / 2));

        RecipeModifiers.modify("assembler/mv_casing", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/mv_hull",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 2L),
                    ItemList.Casing_MV.get(1))
                .fluidInputs(Materials.Polyethylene.getMolten(288L))
                .itemOutputs(ItemList.Hull_MV.get(1L))
                .duration(50 * TICKS)
                .eut(TierEU.RECIPE_LV / 2));

        RecipeModifiers.modify("assembler/mv_hull", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Casing_MV.get(1));
            d.itemIn()
                .condenseAll();
        });
    }

    private static void hvParts() {
        RecipeModifiers.defineBase(
            "assembler/hv_motor",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.stick, Materials.SteelMagnetic, 1L),
                    GTOreDictUnificator.get(OrePrefixes.stick, Materials.StainlessSteel, 2L),
                    GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.Electrum, 4L),
                    GTOreDictUnificator.get(OrePrefixes.cableGt02, Materials.Silver, 2L))
                .itemOutputs(ItemList.Electric_Motor_HV.get(1L))
                .duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV));

        RecipeModifiers.modify("assembler/hv_motor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
            d.itemIn()
                .condenseAll();
        });

        for (int i = 0; i < RUBBERS.length; i++) {
            int finalI = i;
            RecipeModifiers.defineBase(
                "assembler/hv_pump_" + RUBBER_NAMES[i],
                () -> RecipeDraft.of(assemblerRecipes)
                    .itemInputs(
                        ItemList.Electric_Motor_HV.get(1),
                        GTOreDictUnificator.get(OrePrefixes.ring, RUBBERS[finalI], 2L),
                        GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Steel, 1L),
                        GTOreDictUnificator.get(OrePrefixes.screw, Materials.Steel, 1L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1L),
                        GTOreDictUnificator.get(OrePrefixes.pipeMedium, Materials.StainlessSteel, 1L))
                    .itemOutputs(ItemList.Electric_Pump_HV.get(1L))
                    .duration(20 * TICKS)
                    .eut(TierEU.RECIPE_LV));

            RecipeModifiers
                .modify("assembler/hv_pump_" + RUBBER_NAMES[i], VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Electric_Motor_HV.get(1));
                    d.itemIn()
                        .condenseAll();
                });

            RecipeModifiers.defineBase(
                "assembler/hv_conveyor_" + RUBBER_NAMES[i],
                () -> RecipeDraft.of(assemblerRecipes)
                    .itemInputs(
                        ItemList.Electric_Motor_HV.get(2),
                        GTOreDictUnificator.get(OrePrefixes.plate, RUBBERS[finalI], 6L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1L))
                    .itemOutputs(ItemList.Conveyor_Module_HV.get(1L))
                    .duration(20 * TICKS)
                    .eut(TierEU.RECIPE_LV));

            RecipeModifiers.modify(
                "assembler/hv_conveyor_" + RUBBER_NAMES[i],
                VariantNames.ALTERED_PRICING,
                RecipePhase.SCALE,
                d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Electric_Motor_HV.get(1));
                    d.itemIn()
                        .condenseAll();
                });
        }

        RecipeModifiers.defineBase(
            "assembler/hv_piston",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    ItemList.Electric_Motor_HV.get(1),
                    GTOreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 3L),
                    GTOreDictUnificator.get(OrePrefixes.stick, Materials.StainlessSteel, 2L),
                    GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 2L),
                    GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.StainlessSteel, 1L))
                .itemOutputs(ItemList.Electric_Piston_HV.get(1L))
                .duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV));

        RecipeModifiers.modify("assembler/hv_piston", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Electric_Motor_HV.get(1));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/hv_robot_arm",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    ItemList.Electric_Motor_HV.get(2),
                    ItemList.Electric_Piston_HV.get(1),
                    GTOreDictUnificator.get(OrePrefixes.stick, Materials.StainlessSteel, 2L),
                    GTOreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 1L),
                    GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 3L))
                .itemOutputs(ItemList.Robot_Arm_HV.get(1L))
                .duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV));

        RecipeModifiers.modify("assembler/hv_robot_arm", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                ItemList.Electric_Motor_HV.get(1),
                ItemList.Electric_Piston_HV.get(1),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 1L));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/hv_emitter",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.gem, Materials.EnderEye, 1L),
                    GTOreDictUnificator.get(OrePrefixes.stick, Materials.Chrome, 4L),
                    GTOreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 2L),
                    GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 2L))
                .itemOutputs(ItemList.Emitter_HV.get(1L))
                .duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV));

        RecipeModifiers.modify("assembler/hv_emitter", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 1L));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/hv_sensor",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.gem, Materials.EnderEye, 1L),
                    GTOreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 4L),
                    GTOreDictUnificator.get(OrePrefixes.stick, Materials.Chrome, 1L),
                    GTOreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 1L))
                .itemOutputs(ItemList.Sensor_HV.get(1L))
                .duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV));

        RecipeModifiers.modify("assembler/hv_sensor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 1L));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/hv_field_gen",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    ItemList.QuantumEye.get(1L),
                    GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 4L),
                    GTUtility.getIntegratedCircuit(1))
                .fluidInputs(Materials.NiobiumTitanium.getMolten(576L))
                .itemOutputs(ItemList.Field_Generator_HV.get(1L))
                .duration(30 * SECONDS)
                .eut(TierEU.RECIPE_HV));

        RecipeModifiers.modify("assembler/hv_field_gen", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 1L));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/hv_casing",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 8L),
                    GTUtility.getIntegratedCircuit(8))
                .itemOutputs(ItemList.Casing_HV.get(1L))
                .duration(50 * TICKS)
                .eut(TierEU.RECIPE_LV / 2));

        RecipeModifiers.modify("assembler/hv_casing", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/hv_hull",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 2L),
                    ItemList.Casing_HV.get(1))
                .fluidInputs(Materials.Polyethylene.getMolten(288L))
                .itemOutputs(ItemList.Hull_HV.get(1L))
                .duration(50 * TICKS)
                .eut(TierEU.RECIPE_LV / 2));

        RecipeModifiers.modify("assembler/hv_hull", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Casing_HV.get(1));
            d.itemIn()
                .condenseAll();
        });
    }

    private static void evParts() {
        RecipeModifiers.defineBase(
            "assembler/ev_motor",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.stick, Materials.NeodymiumMagnetic, 1L),
                    GTOreDictUnificator.get(OrePrefixes.stick, Materials.Titanium, 2L),
                    GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.BlackSteel, 4L),
                    GTOreDictUnificator.get(OrePrefixes.cableGt02, Materials.Aluminium, 2L))
                .itemOutputs(ItemList.Electric_Motor_EV.get(1L))
                .duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV));

        RecipeModifiers.modify("assembler/ev_motor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
            d.itemIn()
                .condenseAll();
        });

        for (int i = 0; i < RUBBERS.length; i++) {
            int finalI = i;
            RecipeModifiers.defineBase(
                "assembler/ev_pump_" + RUBBER_NAMES[i],
                () -> RecipeDraft.of(assemblerRecipes)
                    .itemInputs(
                        ItemList.Electric_Motor_EV.get(1),
                        GTOreDictUnificator.get(OrePrefixes.ring, RUBBERS[finalI], 2L),
                        GTOreDictUnificator.get(OrePrefixes.rotor, Materials.StainlessSteel, 1L),
                        GTOreDictUnificator.get(OrePrefixes.screw, Materials.StainlessSteel, 1L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 1L),
                        GTOreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Titanium, 1L))
                    .itemOutputs(ItemList.Electric_Pump_EV.get(1L))
                    .duration(20 * TICKS)
                    .eut(TierEU.RECIPE_LV));

            RecipeModifiers
                .modify("assembler/ev_pump_" + RUBBER_NAMES[i], VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Electric_Motor_EV.get(1));
                    d.itemIn()
                        .condenseAll();
                });

            RecipeModifiers.defineBase(
                "assembler/ev_conveyor_" + RUBBER_NAMES[i],
                () -> RecipeDraft.of(assemblerRecipes)
                    .itemInputs(
                        ItemList.Electric_Motor_EV.get(2),
                        GTOreDictUnificator.get(OrePrefixes.plate, RUBBERS[finalI], 6L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 1L))
                    .itemOutputs(ItemList.Conveyor_Module_EV.get(1L))
                    .duration(20 * TICKS)
                    .eut(TierEU.RECIPE_LV));

            RecipeModifiers.modify(
                "assembler/ev_conveyor_" + RUBBER_NAMES[i],
                VariantNames.ALTERED_PRICING,
                RecipePhase.SCALE,
                d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Electric_Motor_EV.get(1));
                    d.itemIn()
                        .condenseAll();
                });
        }

        RecipeModifiers.defineBase(
            "assembler/ev_piston",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    ItemList.Electric_Motor_EV.get(1),
                    GTOreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 3L),
                    GTOreDictUnificator.get(OrePrefixes.stick, Materials.Titanium, 2L),
                    GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 2L),
                    GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Titanium, 1L))
                .itemOutputs(ItemList.Electric_Piston_EV.get(1L))
                .duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV));

        RecipeModifiers.modify("assembler/ev_piston", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Electric_Motor_EV.get(1));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/ev_robot_arm",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    ItemList.Electric_Motor_EV.get(2),
                    ItemList.Electric_Piston_EV.get(1),
                    GTOreDictUnificator.get(OrePrefixes.stick, Materials.Titanium, 2L),
                    GTOreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 1L),
                    GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 3L))
                .itemOutputs(ItemList.Robot_Arm_EV.get(1L))
                .duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV));

        RecipeModifiers.modify("assembler/ev_robot_arm", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                ItemList.Electric_Motor_EV.get(1),
                ItemList.Electric_Piston_EV.get(1),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 1L));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/ev_emitter",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    ItemList.QuantumEye.get(1L),
                    GTOreDictUnificator.get(OrePrefixes.stick, Materials.Platinum, 4L),
                    GTOreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 2L),
                    GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 2L))
                .itemOutputs(ItemList.Emitter_EV.get(1L))
                .duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV));

        RecipeModifiers.modify("assembler/ev_emitter", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 1L));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/ev_sensor",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    ItemList.QuantumEye.get(1L),
                    GTOreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 4L),
                    GTOreDictUnificator.get(OrePrefixes.stick, Materials.Platinum, 1L),
                    GTOreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 1L))
                .itemOutputs(ItemList.Sensor_EV.get(1L))
                .duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV));

        RecipeModifiers.modify("assembler/ev_sensor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 1L));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/ev_field_gen",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.gem, Materials.NetherStar, 1L),
                    GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LuV, 4L),
                    GTUtility.getIntegratedCircuit(1))
                .fluidInputs(Materials.HSSG.getMolten(576L))
                .itemOutputs(ItemList.Field_Generator_EV.get(1L))
                .duration(30 * SECONDS)
                .eut(TierEU.RECIPE_EV));

        RecipeModifiers.modify("assembler/ev_field_gen", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LuV, 1L));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/ev_casing",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 8L),
                    GTUtility.getIntegratedCircuit(8))
                .itemOutputs(ItemList.Casing_EV.get(1L))
                .duration(50 * TICKS)
                .eut(TierEU.RECIPE_LV / 2));

        RecipeModifiers.modify("assembler/ev_casing", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/ev_hull",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 2L),
                    ItemList.Casing_EV.get(1))
                .fluidInputs(Materials.Polyethylene.getMolten(288L))
                .itemOutputs(ItemList.Hull_EV.get(1L))
                .duration(50 * TICKS)
                .eut(TierEU.RECIPE_LV / 2));

        RecipeModifiers.modify("assembler/ev_hull", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Casing_EV.get(1));
            d.itemIn()
                .condenseAll();
        });
    }

    private static void ivParts() {
        RecipeModifiers.defineBase(
            "assembler/iv_motor",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.stick, Materials.NeodymiumMagnetic, 1L),
                    GTOreDictUnificator.get(OrePrefixes.stick, Materials.TungstenSteel, 2L),
                    GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.Graphene, 4L),
                    GTOreDictUnificator.get(OrePrefixes.cableGt02, Materials.Tungsten, 2L))
                .itemOutputs(ItemList.Electric_Motor_IV.get(1L))
                .duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV));

        RecipeModifiers.modify("assembler/iv_motor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
            d.itemIn()
                .condenseAll();
        });

        for (int i = 1; i < RUBBERS.length; i++) {
            int finalI = i;
            RecipeModifiers.defineBase(
                "assembler/iv_pump_" + RUBBER_NAMES[i],
                () -> RecipeDraft.of(assemblerRecipes)
                    .itemInputs(
                        ItemList.Electric_Motor_IV.get(1),
                        GTOreDictUnificator.get(OrePrefixes.ring, RUBBERS[finalI], 2L),
                        GTOreDictUnificator.get(OrePrefixes.rotor, Materials.TungstenSteel, 1L),
                        GTOreDictUnificator.get(OrePrefixes.screw, Materials.TungstenSteel, 1L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 1L),
                        GTOreDictUnificator.get(OrePrefixes.pipeMedium, Materials.TungstenSteel, 1L))
                    .itemOutputs(ItemList.Electric_Pump_IV.get(1L))
                    .duration(20 * TICKS)
                    .eut(TierEU.RECIPE_LV));

            RecipeModifiers
                .modify("assembler/iv_pump_" + RUBBER_NAMES[i], VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Electric_Motor_IV.get(1));
                    d.itemIn()
                        .condenseAll();
                });

            RecipeModifiers.defineBase(
                "assembler/iv_conveyor_" + RUBBER_NAMES[i],
                () -> RecipeDraft.of(assemblerRecipes)
                    .itemInputs(
                        ItemList.Electric_Motor_IV.get(2),
                        GTOreDictUnificator.get(OrePrefixes.plate, RUBBERS[finalI], 6L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 1L))
                    .itemOutputs(ItemList.Conveyor_Module_IV.get(1L))
                    .duration(20 * TICKS)
                    .eut(TierEU.RECIPE_LV));

            RecipeModifiers.modify(
                "assembler/iv_conveyor_" + RUBBER_NAMES[i],
                VariantNames.ALTERED_PRICING,
                RecipePhase.SCALE,
                d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Electric_Motor_IV.get(1));
                    d.itemIn()
                        .condenseAll();
                });
        }

        RecipeModifiers.defineBase(
            "assembler/iv_piston",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    ItemList.Electric_Motor_IV.get(1),
                    GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 3L),
                    GTOreDictUnificator.get(OrePrefixes.stick, Materials.TungstenSteel, 2L),
                    GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 2L),
                    GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.TungstenSteel, 1L))
                .itemOutputs(ItemList.Electric_Piston_IV.get(1L))
                .duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV));

        RecipeModifiers.modify("assembler/iv_piston", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Electric_Motor_IV.get(1));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/iv_robot_arm",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    ItemList.Electric_Motor_IV.get(2),
                    ItemList.Electric_Piston_IV.get(1),
                    GTOreDictUnificator.get(OrePrefixes.stick, Materials.TungstenSteel, 2L),
                    GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 1L),
                    GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 3L))
                .itemOutputs(ItemList.Robot_Arm_IV.get(1L))
                .duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV));

        RecipeModifiers.modify("assembler/iv_robot_arm", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                ItemList.Electric_Motor_IV.get(1),
                ItemList.Electric_Piston_IV.get(1),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 1L));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/iv_emitter",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    ItemList.QuantumStar.get(1L),
                    GTOreDictUnificator.get(OrePrefixes.stick, Materials.Iridium, 4L),
                    GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 2L),
                    GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 2L))
                .itemOutputs(ItemList.Emitter_IV.get(1L))
                .duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV));

        RecipeModifiers.modify("assembler/iv_emitter", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 1L));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/iv_sensor",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    ItemList.QuantumStar.get(1L),
                    GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 4L),
                    GTOreDictUnificator.get(OrePrefixes.stick, Materials.Iridium, 1L),
                    GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 1L))
                .itemOutputs(ItemList.Sensor_IV.get(1L))
                .duration(20 * TICKS)
                .eut(TierEU.RECIPE_LV));

        RecipeModifiers.modify("assembler/iv_sensor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 1L));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/iv_field_gen",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    ItemList.QuantumStar.get(1L),
                    GTOreDictUnificator.get(OrePrefixes.circuit, Materials.ZPM, 4L),
                    GTUtility.getIntegratedCircuit(1))
                .fluidInputs(Materials.HSSS.getMolten(576L))
                .itemOutputs(ItemList.Field_Generator_IV.get(1L))
                .duration(30 * SECONDS)
                .eut(TierEU.RECIPE_IV));

        RecipeModifiers.modify("assembler/iv_field_gen", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.ZPM, 1L));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/iv_casing",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 8L),
                    GTUtility.getIntegratedCircuit(8))
                .itemOutputs(ItemList.Casing_IV.get(1L))
                .duration(50 * TICKS)
                .eut(TierEU.RECIPE_LV / 2));

        RecipeModifiers.modify("assembler/iv_casing", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/iv_hull",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 2L),
                    ItemList.Casing_IV.get(1))
                .fluidInputs(Materials.Polytetrafluoroethylene.getMolten(288L))
                .itemOutputs(ItemList.Hull_IV.get(1L))
                .duration(50 * TICKS)
                .eut(TierEU.RECIPE_LV / 2));

        RecipeModifiers.modify("assembler/iv_hull", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Casing_IV.get(1));
            d.itemIn()
                .condenseAll();
        });
    }
}
