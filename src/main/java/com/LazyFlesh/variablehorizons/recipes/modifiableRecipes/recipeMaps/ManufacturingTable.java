package com.LazyFlesh.variablehorizons.recipes.modifiableRecipes.recipeMaps;

import static com.LazyFlesh.variablehorizons.recipes.VHRecipeMaps.manufacturingTableRecipes;
import static gregtech.api.enums.Mods.IndustrialCraft2;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.util.recipeBuilder.RecipeDraft;
import com.LazyFlesh.variablehorizons.util.recipeBuilder.RecipeModifiers;
import com.LazyFlesh.variablehorizons.util.recipeBuilder.RecipePhase;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import bartworks.system.material.CircuitGeneration.CircuitWraps;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;

public class ManufacturingTable implements Runnable {

    private static final Materials[] RUBBERS = new Materials[] { Materials.Rubber, Materials.RubberSilicone,
        Materials.StyreneButadieneRubber };
    private static final String[] RUBBER_NAMES = new String[] { "rubber", "silicone", "styrene-butadiene" };
    private static final Materials[] TIER_MATS = new Materials[] { Materials.ULV, Materials.LV, Materials.MV,
        Materials.HV, Materials.EV, Materials.IV, Materials.LuV, Materials.ZPM, Materials.UV, Materials.UHV,
        Materials.UEV, Materials.UIV, Materials.UMV, Materials.UXV, Materials.MAX };

    @Override
    public void run() {

        RecipeModifiers.defineBase(
            "manufacturing/vacuum_tube",
            () -> RecipeDraft.of(manufacturingTableRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 3L),
                    GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Copper, 2L),
                    ItemList.Circuit_Parts_Glass_Tube.get(1L),
                    GTOreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 2L),
                    GTOreDictUnificator.get(OrePrefixes.bolt, Materials.RedAlloy, 1L))
                .itemOutputs(ItemList.Circuit_Parts_Vacuum_Tube.get(1L)));

        RecipeModifiers.modify("manufacturing/vacuum_tube", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
            d.itemIn()
                .condenseAll();
        });

        // LV Parts
        {
            RecipeModifiers.defineBase(
                "manufacturing/lv_motor",
                () -> RecipeDraft.of(manufacturingTableRecipes)
                    .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.IronMagnetic, 1L),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Iron, 2L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 4L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 2L))
                    .itemOutputs(ItemList.Electric_Motor_LV.get(1L)));

            RecipeModifiers.modify("manufacturing/lv_motor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                d.itemIn()
                    .condenseAll();
            });

            for (int i = 0; i < RUBBERS.length; i++) {
                int finalI = i;
                RecipeModifiers.defineBase(
                    "manufacturing/lv_pump_" + RUBBER_NAMES[i],
                    () -> RecipeDraft.of(manufacturingTableRecipes)
                        .itemInputs(
                            ItemList.Electric_Motor_LV.get(1),
                            GTOreDictUnificator.get(OrePrefixes.ring, RUBBERS[finalI], 2L),
                            GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Tin, 1L),
                            GTOreDictUnificator.get(OrePrefixes.screw, Materials.Tin, 1L),
                            GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1L),
                            GTOreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Bronze, 1L))
                        .itemOutputs(ItemList.Electric_Pump_LV.get(1L)));

                RecipeModifiers.modify(
                    "manufacturing/lv_pump_" + RUBBER_NAMES[i],
                    VariantNames.ALTERED_PRICING,
                    RecipePhase.SCALE,
                    d -> {
                        d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Electric_Motor_LV.get(1));
                        d.itemIn()
                            .condenseAll();
                    });

                RecipeModifiers.defineBase(
                    "manufacturing/lv_conveyor_" + RUBBER_NAMES[i],
                    () -> RecipeDraft.of(manufacturingTableRecipes)
                        .itemInputs(
                            ItemList.Electric_Motor_LV.get(2),
                            GTOreDictUnificator.get(OrePrefixes.plate, RUBBERS[finalI], 6L),
                            GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1L))
                        .itemOutputs(ItemList.Conveyor_Module_LV.get(1L)));

                RecipeModifiers.modify(
                    "manufacturing/lv_conveyor_" + RUBBER_NAMES[i],
                    VariantNames.ALTERED_PRICING,
                    RecipePhase.SCALE,
                    d -> {
                        d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Electric_Motor_LV.get(1));
                        d.itemIn()
                            .condenseAll();
                    });
            }

            RecipeModifiers.defineBase(
                "manufacturing/lv_piston",
                () -> RecipeDraft.of(manufacturingTableRecipes)
                    .itemInputs(
                        ItemList.Electric_Motor_LV.get(1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 3L),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 2L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 2L),
                        GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Steel, 1L))
                    .itemOutputs(ItemList.Electric_Piston_LV.get(1L)));

            RecipeModifiers.modify("manufacturing/lv_piston", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Electric_Motor_LV.get(1));
                d.itemIn()
                    .condenseAll();
            });

            RecipeModifiers.defineBase(
                "manufacturing/lv_robot_arm",
                () -> RecipeDraft.of(manufacturingTableRecipes)
                    .itemInputs(
                        ItemList.Electric_Motor_LV.get(2),
                        ItemList.Electric_Piston_LV.get(1),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 2L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 1L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 3L))
                    .itemOutputs(ItemList.Robot_Arm_LV.get(1L)));

            RecipeModifiers.modify("manufacturing/lv_robot_arm", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(
                    GeneralConfig.partCostMultiplier,
                    ItemList.Electric_Motor_LV.get(1),
                    ItemList.Electric_Piston_LV.get(1));
                d.itemIn()
                    .condenseAll();
            });

            RecipeModifiers.defineBase(
                "manufacturing/lv_emitter",
                () -> RecipeDraft.of(manufacturingTableRecipes)
                    .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.gem, Materials.CertusQuartz, 1L),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Brass, 4L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 2L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 2L))
                    .itemOutputs(ItemList.Emitter_LV.get(1L)));

            RecipeModifiers.modify("manufacturing/lv_emitter", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                d.itemIn()
                    .condenseAll();
            });

            RecipeModifiers.defineBase(
                "manufacturing/lv_sensor",
                () -> RecipeDraft.of(manufacturingTableRecipes)
                    .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.gem, Materials.CertusQuartz, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 4L),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Brass, 1L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 1L))
                    .itemOutputs(ItemList.Sensor_LV.get(1L)));

            RecipeModifiers.modify("manufacturing/lv_sensor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                d.itemIn()
                    .condenseAll();
            });

            RecipeModifiers.defineBase(
                "manufacturing/lv_field_gen",
                () -> RecipeDraft.of(manufacturingTableRecipes)
                    .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.EnderPearl, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.RedSteel, 4L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 4L))
                    .itemOutputs(ItemList.Field_Generator_LV.get(1L)));

            RecipeModifiers.modify("manufacturing/lv_field_gen", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                d.itemIn()
                    .condenseAll();
            });

            RecipeModifiers.defineBase(
                "manufacturing/lv_casing",
                () -> RecipeDraft.of(manufacturingTableRecipes)
                    .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 8L),
                        GTUtility.getIntegratedCircuit(8))
                    .itemOutputs(ItemList.Casing_LV.get(1L)));

            RecipeModifiers.modify("manufacturing/lv_casing", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                d.itemIn()
                    .condenseAll();
            });

            RecipeModifiers.defineBase(
                "manufacturing/lv_hull",
                () -> RecipeDraft.of(manufacturingTableRecipes)
                    .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 2L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.CastIron, 2L),
                        ItemList.Casing_LV.get(1))
                    .itemOutputs(ItemList.Hull_LV.get(1L)));

            RecipeModifiers.modify("manufacturing/lv_hull", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Casing_LV.get(1));
                d.itemIn()
                    .condenseAll();
            });
        }

        RecipeModifiers.defineBase(
            "manufacturing/electronic_circuit",
            () -> RecipeDraft.of(manufacturingTableRecipes)
                .itemInputs(
                    ItemList.Circuit_Parts_Resistor.get(2),
                    GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.Steel, 1L),
                    ItemList.Circuit_Parts_Vacuum_Tube.get(2),
                    ItemList.Circuit_Board_Coated_Basic.get(1),
                    GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.RedAlloy, 3L))
                .itemOutputs(GTModHandler.getModItem(IndustrialCraft2.ID, "itemPartCircuit", 1, 0)));

        RecipeModifiers
            .modify("manufacturing/electronic_circuit", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Circuit_Parts_Vacuum_Tube.get(2));
                d.itemIn()
                    .condenseAll();
            });

        RecipeModifiers.defineBase(
            "manufacturing/good_electronic_circuit",
            () -> RecipeDraft.of(manufacturingTableRecipes)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 2L),
                    GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.Steel, 1L),
                    GTModHandler.getModItem(IndustrialCraft2.ID, "itemPartCircuit", 3, 0),
                    ItemList.Circuit_Board_Phenolic_Good.get(1),
                    ItemList.Circuit_Parts_Diode.get(2))
                .itemOutputs(ItemList.Circuit_Good.get(1)));

        RecipeModifiers
            .modify("manufacturing/good_electronic_circuit", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(
                    GeneralConfig.partCostMultiplier,
                    GTModHandler.getModItem(IndustrialCraft2.ID, "itemPartCircuit", 1, 0));
                d.itemIn()
                    .condenseAll();
            });

        // Wraps
        {
            for (CircuitWraps partWrap : CircuitWraps.VALUES) {
                RecipeModifiers.defineBase(
                    "manufacturing/wrap_part_" + partWrap.id,
                    () -> RecipeDraft.of(manufacturingTableRecipes)
                        .itemInputs(
                            partWrap.itemSingle.get(16),
                            GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.SolderingAlloy, 4L),
                            GTUtility.getIntegratedCircuit(16))
                        .itemOutputs(partWrap.itemWrap.get(1)));
            }

            for (int i = 0; i < 15; i++) {
                int finalI = i;
                RecipeModifiers.defineBase(
                    "manufacturing/wrap_circuit_" + i,
                    () -> RecipeDraft.of(manufacturingTableRecipes)
                        .itemInputs(
                            GTOreDictUnificator.get(OrePrefixes.circuit, TIER_MATS[finalI], 16L),
                            GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.SolderingAlloy, 4L),
                            GTUtility.getIntegratedCircuit(16))
                        .itemOutputs(GTOreDictUnificator.get(OrePrefixes.wrapCircuit, TIER_MATS[finalI], 1L)));
            }
        }

        {
            RecipeModifiers.defineBase(
                "manufacturing/mv_motor",
                () -> RecipeDraft.of(manufacturingTableRecipes)
                    .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.SteelMagnetic, 1L),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Aluminium, 2L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt02, Materials.Cupronickel, 4L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 2L))
                    .itemOutputs(ItemList.Electric_Motor_MV.get(1L)));

            RecipeModifiers.modify("manufacturing/mv_motor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                d.itemIn()
                    .condenseAll();
            });

            for (int i = 0; i < RUBBERS.length; i++) {
                int finalI = i;
                RecipeModifiers.defineBase(
                    "manufacturing/mv_pump_" + RUBBER_NAMES[i],
                    () -> RecipeDraft.of(manufacturingTableRecipes)
                        .itemInputs(
                            ItemList.Electric_Motor_MV.get(1),
                            GTOreDictUnificator.get(OrePrefixes.ring, RUBBERS[finalI], 2L),
                            GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Bronze, 1L),
                            GTOreDictUnificator.get(OrePrefixes.screw, Materials.Bronze, 1L),
                            GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 1L),
                            GTOreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Steel, 1L))
                        .itemOutputs(ItemList.Electric_Pump_MV.get(1L)));

                RecipeModifiers.modify(
                    "manufacturing/mv_pump_" + RUBBER_NAMES[i],
                    VariantNames.ALTERED_PRICING,
                    RecipePhase.SCALE,
                    d -> {
                        d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Electric_Motor_MV.get(1));
                        d.itemIn()
                            .condenseAll();
                    });

                RecipeModifiers.defineBase(
                    "manufacturing/mv_conveyor_" + RUBBER_NAMES[i],
                    () -> RecipeDraft.of(manufacturingTableRecipes)
                        .itemInputs(
                            ItemList.Electric_Motor_MV.get(2),
                            GTOreDictUnificator.get(OrePrefixes.plate, RUBBERS[finalI], 6L),
                            GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 1L))
                        .itemOutputs(ItemList.Conveyor_Module_MV.get(1L)));

                RecipeModifiers.modify(
                    "manufacturing/mv_conveyor_" + RUBBER_NAMES[i],
                    VariantNames.ALTERED_PRICING,
                    RecipePhase.SCALE,
                    d -> {
                        d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Electric_Motor_MV.get(1));
                        d.itemIn()
                            .condenseAll();
                    });
            }

            RecipeModifiers.defineBase(
                "manufacturing/mv_piston",
                () -> RecipeDraft.of(manufacturingTableRecipes)
                    .itemInputs(
                        ItemList.Electric_Motor_MV.get(1),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 3L),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Aluminium, 2L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 2L),
                        GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Aluminium, 1L))
                    .itemOutputs(ItemList.Electric_Piston_MV.get(1L)));

            RecipeModifiers.modify("manufacturing/mv_piston", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Electric_Motor_MV.get(1));
                d.itemIn()
                    .condenseAll();
            });

            RecipeModifiers.defineBase(
                "manufacturing/mv_robot_arm",
                () -> RecipeDraft.of(manufacturingTableRecipes)
                    .itemInputs(
                        ItemList.Electric_Motor_MV.get(2),
                        ItemList.Electric_Piston_MV.get(1),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Aluminium, 2L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 1L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 3L))
                    .itemOutputs(ItemList.Robot_Arm_MV.get(1L)));

            RecipeModifiers.modify("manufacturing/mv_robot_arm", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(
                    GeneralConfig.partCostMultiplier,
                    ItemList.Electric_Motor_MV.get(1),
                    ItemList.Electric_Piston_MV.get(1));
                d.itemIn()
                    .condenseAll();
            });

            RecipeModifiers.defineBase(
                "manufacturing/mv_emitter",
                () -> RecipeDraft.of(manufacturingTableRecipes)
                    .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.gem, Materials.EnderPearl, 1L),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Electrum, 4L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 2L),
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 2L))
                    .itemOutputs(ItemList.Emitter_MV.get(1L)));

            RecipeModifiers.modify("manufacturing/mv_emitter", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                d.itemIn()
                    .condenseAll();
            });

            RecipeModifiers.defineBase(
                "manufacturing/mv_sensor",
                () -> RecipeDraft.of(manufacturingTableRecipes)
                    .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.gemFlawless, Materials.Emerald, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 4L),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Electrum, 1L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 1L))
                    .itemOutputs(ItemList.Sensor_MV.get(1L)));

            RecipeModifiers.modify("manufacturing/mv_sensor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                d.itemIn()
                    .condenseAll();
            });

            RecipeModifiers.defineBase(
                "manufacturing/mv_field_gen",
                () -> RecipeDraft.of(manufacturingTableRecipes)
                    .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.EnderEye, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 1L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 4L))
                    .itemOutputs(ItemList.Field_Generator_MV.get(1L)));

            RecipeModifiers.modify("manufacturing/mv_field_gen", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                d.itemIn()
                    .condenseAll();
            });

            RecipeModifiers.defineBase(
                "manufacturing/mv_casing",
                () -> RecipeDraft.of(manufacturingTableRecipes)
                    .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 8L),
                        GTUtility.getIntegratedCircuit(8))
                    .itemOutputs(ItemList.Casing_MV.get(1L)));

            RecipeModifiers.modify("manufacturing/mv_casing", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                d.itemIn()
                    .condenseAll();
            });

            RecipeModifiers.defineBase(
                "manufacturing/mv_hull",
                () -> RecipeDraft.of(manufacturingTableRecipes)
                    .itemInputs(
                        GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 2L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.CastIron, 2L),
                        ItemList.Casing_MV.get(1))
                    .itemOutputs(ItemList.Hull_MV.get(1L)));

            RecipeModifiers.modify("manufacturing/mv_hull", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Casing_MV.get(1));
                d.itemIn()
                    .condenseAll();
            });
        }
    }

}
