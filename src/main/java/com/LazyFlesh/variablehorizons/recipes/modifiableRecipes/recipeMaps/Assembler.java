package com.LazyFlesh.variablehorizons.recipes.modifiableRecipes.recipeMaps;

import static gregtech.api.recipe.RecipeMaps.assemblerRecipes;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeBuilder.TICKS;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.util.recipeBuilder.RecipeDraft;
import com.LazyFlesh.variablehorizons.util.recipeBuilder.RecipeModifiers;
import com.LazyFlesh.variablehorizons.util.recipeBuilder.RecipePhase;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import bartworks.system.material.WerkstoffLoader;
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
    private static final Materials[] SC_BASE = new Materials[] { Materials.SuperconductorMVBase,
        Materials.SuperconductorHVBase, Materials.SuperconductorEVBase, Materials.SuperconductorIVBase,
        Materials.SuperconductorLuVBase, Materials.SuperconductorZPMBase, Materials.SuperconductorUVBase,
        Materials.SuperconductorUHVBase, Materials.SuperconductorUEVBase, Materials.SuperconductorUIVBase,
        Materials.SuperconductorUMVBase };
    private static final Materials[] SC = new Materials[] { Materials.SuperconductorMV, Materials.SuperconductorHV,
        Materials.SuperconductorEV, Materials.SuperconductorIV, Materials.SuperconductorLuV,
        Materials.SuperconductorZPM, Materials.SuperconductorUV, Materials.SuperconductorUHV,
        Materials.SuperconductorUEV, Materials.SuperconductorUIV, Materials.SuperconductorUMV };
    private static final ItemStack[] PUMPS = new ItemStack[] { ItemList.Electric_Pump_MV.get(1),
        ItemList.Electric_Pump_HV.get(1), ItemList.Electric_Pump_EV.get(1), ItemList.Electric_Pump_IV.get(1),
        ItemList.Electric_Pump_LuV.get(1), ItemList.Electric_Pump_ZPM.get(1), ItemList.Electric_Pump_UV.get(1),
        ItemList.Electric_Pump_UHV.get(1), ItemList.Electric_Pump_UEV.get(1), ItemList.Electric_Pump_UIV.get(1),
        ItemList.Electric_Pump_UMV.get(1) };
    private static final Materials[] SC_PIPES = new Materials[] { Materials.StainlessSteel, Materials.Titanium,
        Materials.TungstenSteel, Materials.NiobiumTitanium, Materials.Enderium, Materials.Naquadah,
        Materials.Neutronium, Materials.Bedrockium, Materials.Infinity, Materials.TranscendentMetal,
        Materials.SpaceTime };
    private static final int[] SC_SPACETIME_AMOUNTS = new int[] { 4, 8, 12, 16, 24, 32, 40, 48, 56, 68, 72 };

    @Override
    public void run() {
        circuitStuff();
        lvParts();
        mvParts();
        hvParts();
        evParts();
        ivParts();
        hulls();
        superconductors();
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
    }

    private static void hulls() {
        RecipeModifiers.defineBase(
            "assembler/bronze_hull",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.plate, Materials.Bronze, 8L),
                    GTUtility.getIntegratedCircuit(8))
                .itemOutputs(ItemList.Hull_Bronze.get(1L))
                .duration(50 * TICKS)
                .eut(TierEU.RECIPE_LV / 2));

        RecipeModifiers.modify("assembler/bronze_hull", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/bricked_bronze_hull",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.plate, Materials.Bronze, 5L),
                    new ItemStack(Blocks.brick_block, 3),
                    GTUtility.getIntegratedCircuit(6))
                .itemOutputs(ItemList.Hull_Bronze_Bricks.get(1L))
                .duration(50 * TICKS)
                .eut(TierEU.RECIPE_LV / 2));

        RecipeModifiers.modify("assembler/bricked_bronze_hull", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/steel_hull",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 8L),
                    GTUtility.getIntegratedCircuit(7))
                .itemOutputs(ItemList.Hull_HP.get(1L))
                .duration(50 * TICKS)
                .eut(TierEU.RECIPE_LV / 2));

        RecipeModifiers.modify("assembler/steel_hull", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/bricked_cast_iron_hull",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.plate, Materials.CastIron, 5L),
                    new ItemStack(Blocks.brick_block, 3),
                    GTUtility.getIntegratedCircuit(6))
                .itemOutputs(ItemList.Hull_HP_Bricks.get(1L))
                .duration(50 * TICKS)
                .eut(TierEU.RECIPE_LV / 2));

        RecipeModifiers
            .modify("assembler/bricked_cast_iron_hull", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                d.itemIn()
                    .condenseAll();
            });

        RecipeModifiers.defineBase(
            "assembler/ulv_casing",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.plate, Materials.CastIron, 8L),
                    GTUtility.getIntegratedCircuit(8))
                .itemOutputs(ItemList.Casing_ULV.get(1L))
                .duration(25 * TICKS)
                .eut(TierEU.RECIPE_LV / 2));

        RecipeModifiers.modify("assembler/ulv_casing", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/ulv_hull",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Lead, 2L),
                    ItemList.Casing_ULV.get(1))
                .fluidInputs(Materials.Polyethylene.getMolten(288L))
                .itemOutputs(ItemList.Hull_ULV.get(1L))
                .duration(25 * TICKS)
                .eut(TierEU.RECIPE_LV / 2));

        RecipeModifiers.modify("assembler/ulv_hull", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Casing_ULV.get(1));
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

        RecipeModifiers.defineBase(
            "assembler/luv_casing",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator
                        .get(OrePrefixes.plate, WerkstoffLoader.RhodiumPlatedPalladium.getGTMaterial(), 8),
                    GTUtility.getIntegratedCircuit(8))
                .itemOutputs(ItemList.Casing_LuV.get(1L))
                .duration(50 * TICKS)
                .eut(TierEU.RECIPE_LV / 2));

        RecipeModifiers.modify("assembler/luv_casing", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/luv_hull",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.VanadiumGallium, 2L),
                    ItemList.Casing_LuV.get(1))
                .fluidInputs(Materials.Polytetrafluoroethylene.getMolten(288L))
                .itemOutputs(ItemList.Hull_LuV.get(1L))
                .duration(50 * TICKS)
                .eut(TierEU.RECIPE_LV / 2));

        RecipeModifiers.modify("assembler/luv_hull", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Casing_LuV.get(1));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/zpm_casing",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.plate, Materials.Iridium, 8),
                    GTUtility.getIntegratedCircuit(8))
                .itemOutputs(ItemList.Casing_ZPM.get(1L))
                .duration(50 * TICKS)
                .eut(TierEU.RECIPE_LV / 2));

        RecipeModifiers.modify("assembler/zpm_casing", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/zpm_hull",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Naquadah, 2L),
                    ItemList.Casing_ZPM.get(1))
                .fluidInputs(Materials.Polybenzimidazole.getMolten(288L))
                .itemOutputs(ItemList.Hull_ZPM.get(1L))
                .duration(50 * TICKS)
                .eut(TierEU.RECIPE_LV / 2));

        RecipeModifiers.modify("assembler/zpm_hull", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Casing_ZPM.get(1));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/uv_casing",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.plate, Materials.Osmium, 8),
                    GTUtility.getIntegratedCircuit(8))
                .itemOutputs(ItemList.Casing_UV.get(1L))
                .duration(50 * TICKS)
                .eut(TierEU.RECIPE_LV / 2));

        RecipeModifiers.modify("assembler/uv_casing", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/uv_hull",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.cableGt04, Materials.NaquadahAlloy, 2L),
                    ItemList.Casing_UV.get(1))
                .fluidInputs(Materials.Polybenzimidazole.getMolten(288L))
                .itemOutputs(ItemList.Hull_UV.get(1L))
                .duration(50 * TICKS)
                .eut(TierEU.RECIPE_LV / 2));

        RecipeModifiers.modify("assembler/uv_hull", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Casing_UV.get(1));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/uhv_casing",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 8),
                    GTUtility.getIntegratedCircuit(8))
                .itemOutputs(ItemList.Casing_MAX.get(1L))
                .duration(50 * TICKS)
                .eut(TierEU.RECIPE_LV / 2));

        RecipeModifiers.modify("assembler/uhv_casing", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/uhv_hull",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.SuperconductorUV, 2L),
                    ItemList.Casing_MAX.get(1))
                .fluidInputs(Materials.Polybenzimidazole.getMolten(288L))
                .itemOutputs(ItemList.Hull_MAX.get(1L))
                .duration(50 * TICKS)
                .eut(TierEU.RECIPE_LV / 2));

        RecipeModifiers.modify("assembler/uhv_hull", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Casing_MAX.get(1));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/uev_casing",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.plate, Materials.Infinity, 8),
                    GTUtility.getIntegratedCircuit(8))
                .itemOutputs(ItemList.Casing_UEV.get(1L))
                .duration(50 * TICKS)
                .eut(TierEU.RECIPE_LV / 2));

        RecipeModifiers.modify("assembler/uev_casing", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/uev_hull",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.cableGt08, Materials.Draconium, 2L),
                    ItemList.Casing_UEV.get(1))
                .fluidInputs(Materials.Polybenzimidazole.getMolten(576L))
                .itemOutputs(ItemList.Hull_UEV.get(1L))
                .duration(50 * TICKS)
                .eut(TierEU.RECIPE_LV / 2));

        RecipeModifiers.modify("assembler/uev_hull", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Casing_UEV.get(1));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/uiv_casing",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.plate, Materials.TranscendentMetal, 8),
                    GTUtility.getIntegratedCircuit(8))
                .itemOutputs(ItemList.Casing_UIV.get(1L))
                .duration(50 * TICKS)
                .eut(TierEU.RECIPE_LV / 2));

        RecipeModifiers.modify("assembler/uiv_casing", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/uiv_hull",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.cableGt08, Materials.NetherStar, 2L),
                    ItemList.Casing_UIV.get(1))
                .fluidInputs(Materials.Polybenzimidazole.getMolten(576L))
                .itemOutputs(ItemList.Hull_UIV.get(1L))
                .duration(50 * TICKS)
                .eut(TierEU.RECIPE_LV / 2));

        RecipeModifiers.modify("assembler/uiv_hull", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Casing_UIV.get(1));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/umv_casing",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.plate, Materials.SpaceTime, 8),
                    GTUtility.getIntegratedCircuit(8))
                .itemOutputs(ItemList.Casing_UMV.get(1L))
                .duration(50 * TICKS)
                .eut(TierEU.RECIPE_LV / 2));

        RecipeModifiers.modify("assembler/umv_casing", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assembler/umv_hull",
            () -> RecipeDraft.of(assemblerRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.cableGt12, Materials.Quantium, 2L),
                    ItemList.Casing_UMV.get(1))
                .fluidInputs(Materials.Polybenzimidazole.getMolten(576L))
                .itemOutputs(ItemList.Hull_UMV.get(1L))
                .duration(50 * TICKS)
                .eut(TierEU.RECIPE_LV / 2));

        RecipeModifiers.modify("assembler/umv_hull", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Casing_UMV.get(1));
            d.itemIn()
                .condenseAll();
        });
    }

    private static void superconductors() {
        for (int i = 0; i < 11; i++) {
            int finalI = i;
            int spacetimeRecipeTime = 10 * SECONDS;
            if (i > 2) {
                spacetimeRecipeTime *= 2;
            }
            if (i > 4) {
                spacetimeRecipeTime *= 2;
            }
            if (i > 6) {
                spacetimeRecipeTime *= 2;
            }
            if (i == 10) {
                spacetimeRecipeTime *= 2;
            }
            int finalSpacetimeRecipeTime = spacetimeRecipeTime;

            if (i < 8) {
                RecipeModifiers.defineBase(
                    "assembler/superconductor_helium_" + i,
                    () -> RecipeDraft.of(assemblerRecipes)
                        .itemInputs(
                            GTOreDictUnificator.get(OrePrefixes.wireGt01, SC_BASE[finalI], 3 * (finalI + 1)),
                            GTOreDictUnificator.get(OrePrefixes.pipeTiny, SC_PIPES[finalI], 2 * (finalI + 1)),
                            PUMPS[finalI],
                            GTUtility.getIntegratedCircuit(9))
                        .fluidInputs(Materials.Helium.getGas(SC_SPACETIME_AMOUNTS[finalI] * 500))
                        .itemOutputs(GTOreDictUnificator.get(OrePrefixes.wireGt01, SC[finalI], 3 * (finalI + 1)))
                        .duration(finalSpacetimeRecipeTime * 2)
                        .eut(TierEU.RECIPE_MV * (long) Math.pow(4, finalI)));

                RecipeModifiers.modify(
                    "assembler/superconductor_helium_" + i,
                    VariantNames.ALTERED_PRICING,
                    RecipePhase.SCALE,
                    d -> {
                        d.scaleAllInputsExcept(Math.max(1, GeneralConfig.partCostMultiplier), PUMPS[finalI]);
                        d.itemIn()
                            .condenseAll();
                        d.scaleAllOutputs(Math.max(1, GeneralConfig.partCostMultiplier));
                    });
            }

            if (i < 10) {
                RecipeModifiers.defineBase(
                    "assembler/superconductor_liquid_helium_" + i,
                    () -> RecipeDraft.of(assemblerRecipes)
                        .itemInputs(
                            GTOreDictUnificator.get(OrePrefixes.wireGt01, SC_BASE[finalI], 3 * (finalI + 1)),
                            GTOreDictUnificator.get(OrePrefixes.pipeTiny, SC_PIPES[finalI], 2 * (finalI + 1)),
                            PUMPS[finalI],
                            GTUtility.getIntegratedCircuit(9))
                        .fluidInputs(WerkstoffLoader.LiquidHelium.getFluidOrGas(SC_SPACETIME_AMOUNTS[finalI] * 500))
                        .itemOutputs(GTOreDictUnificator.get(OrePrefixes.wireGt01, SC[finalI], 3 * (finalI + 1)))
                        .duration((int) (finalSpacetimeRecipeTime * (finalI > 7 ? 2 : 8 / 5f)))
                        .eut(TierEU.RECIPE_MV * (long) Math.pow(4, finalI)));

                RecipeModifiers.modify(
                    "assembler/superconductor_liquid_helium_" + i,
                    VariantNames.ALTERED_PRICING,
                    RecipePhase.SCALE,
                    d -> {
                        d.scaleAllInputsExcept(Math.max(1, GeneralConfig.partCostMultiplier), PUMPS[finalI]);
                        d.itemIn()
                            .condenseAll();
                        d.scaleAllOutputs(Math.max(1, GeneralConfig.partCostMultiplier));
                    });
            }

            RecipeModifiers.defineBase(
                "assembler/superconductor_spacetime_" + i,
                () -> RecipeDraft.of(assemblerRecipes)
                    .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, SC_BASE[finalI], 3 * (finalI + 1)),
                        GTOreDictUnificator.get(OrePrefixes.pipeTiny, SC_PIPES[finalI], 2 * (finalI + 1)),
                        PUMPS[finalI],
                        GTUtility.getIntegratedCircuit(9))
                    .fluidInputs(Materials.SpaceTime.getMolten(SC_SPACETIME_AMOUNTS[finalI]))
                    .itemOutputs(GTOreDictUnificator.get(OrePrefixes.wireGt01, SC[finalI], 3 * (finalI + 1)))
                    .duration(finalSpacetimeRecipeTime)
                    .eut(TierEU.RECIPE_MV * (long) Math.pow(4, finalI)));

            RecipeModifiers.modify(
                "assembler/superconductor_spacetime_" + i,
                VariantNames.ALTERED_PRICING,
                RecipePhase.SCALE,
                d -> {
                    d.scaleAllInputsExcept(Math.max(1, GeneralConfig.partCostMultiplier), PUMPS[finalI]);
                    d.itemIn()
                        .condenseAll();
                    d.scaleAllOutputs(Math.max(1, GeneralConfig.partCostMultiplier));
                });
        }
    }
}
