package com.LazyFlesh.variablehorizons.recipes.modifiableRecipes.recipeMaps;

import static bartworks.system.material.WerkstoffLoader.Ruridit;
import static goodgenerator.api.recipe.GoodGeneratorRecipeMaps.componentAssemblyLineRecipes;
import static gregtech.api.enums.ItemList.Conveyor_Module_EV;
import static gregtech.api.enums.ItemList.Conveyor_Module_HV;
import static gregtech.api.enums.ItemList.Conveyor_Module_IV;
import static gregtech.api.enums.ItemList.Conveyor_Module_LV;
import static gregtech.api.enums.ItemList.Conveyor_Module_LuV;
import static gregtech.api.enums.ItemList.Conveyor_Module_MV;
import static gregtech.api.enums.ItemList.Conveyor_Module_UV;
import static gregtech.api.enums.ItemList.Conveyor_Module_ZPM;
import static gregtech.api.enums.ItemList.Electric_Motor_EV;
import static gregtech.api.enums.ItemList.Electric_Motor_HV;
import static gregtech.api.enums.ItemList.Electric_Motor_IV;
import static gregtech.api.enums.ItemList.Electric_Motor_LV;
import static gregtech.api.enums.ItemList.Electric_Motor_LuV;
import static gregtech.api.enums.ItemList.Electric_Motor_MV;
import static gregtech.api.enums.ItemList.Electric_Motor_UV;
import static gregtech.api.enums.ItemList.Electric_Motor_ZPM;
import static gregtech.api.enums.ItemList.Electric_Piston_EV;
import static gregtech.api.enums.ItemList.Electric_Piston_HV;
import static gregtech.api.enums.ItemList.Electric_Piston_IV;
import static gregtech.api.enums.ItemList.Electric_Piston_LV;
import static gregtech.api.enums.ItemList.Electric_Piston_LuV;
import static gregtech.api.enums.ItemList.Electric_Piston_MV;
import static gregtech.api.enums.ItemList.Electric_Piston_UV;
import static gregtech.api.enums.ItemList.Electric_Piston_ZPM;
import static gregtech.api.enums.ItemList.Electric_Pump_EV;
import static gregtech.api.enums.ItemList.Electric_Pump_HV;
import static gregtech.api.enums.ItemList.Electric_Pump_IV;
import static gregtech.api.enums.ItemList.Electric_Pump_LV;
import static gregtech.api.enums.ItemList.Electric_Pump_LuV;
import static gregtech.api.enums.ItemList.Electric_Pump_MV;
import static gregtech.api.enums.ItemList.Electric_Pump_UV;
import static gregtech.api.enums.ItemList.Electric_Pump_ZPM;
import static gregtech.api.enums.ItemList.Emitter_EV;
import static gregtech.api.enums.ItemList.Emitter_HV;
import static gregtech.api.enums.ItemList.Emitter_IV;
import static gregtech.api.enums.ItemList.Emitter_LV;
import static gregtech.api.enums.ItemList.Emitter_LuV;
import static gregtech.api.enums.ItemList.Emitter_MV;
import static gregtech.api.enums.ItemList.Emitter_UV;
import static gregtech.api.enums.ItemList.Emitter_ZPM;
import static gregtech.api.enums.ItemList.Field_Generator_EV;
import static gregtech.api.enums.ItemList.Field_Generator_HV;
import static gregtech.api.enums.ItemList.Field_Generator_IV;
import static gregtech.api.enums.ItemList.Field_Generator_LV;
import static gregtech.api.enums.ItemList.Field_Generator_LuV;
import static gregtech.api.enums.ItemList.Field_Generator_MV;
import static gregtech.api.enums.ItemList.Field_Generator_UV;
import static gregtech.api.enums.ItemList.Field_Generator_ZPM;
import static gregtech.api.enums.ItemList.Gravistar;
import static gregtech.api.enums.ItemList.QuantumEye;
import static gregtech.api.enums.ItemList.QuantumStar;
import static gregtech.api.enums.ItemList.Robot_Arm_EV;
import static gregtech.api.enums.ItemList.Robot_Arm_HV;
import static gregtech.api.enums.ItemList.Robot_Arm_IV;
import static gregtech.api.enums.ItemList.Robot_Arm_LV;
import static gregtech.api.enums.ItemList.Robot_Arm_LuV;
import static gregtech.api.enums.ItemList.Robot_Arm_MV;
import static gregtech.api.enums.ItemList.Robot_Arm_UV;
import static gregtech.api.enums.ItemList.Robot_Arm_ZPM;
import static gregtech.api.enums.ItemList.Sensor_EV;
import static gregtech.api.enums.ItemList.Sensor_HV;
import static gregtech.api.enums.ItemList.Sensor_IV;
import static gregtech.api.enums.ItemList.Sensor_LV;
import static gregtech.api.enums.ItemList.Sensor_LuV;
import static gregtech.api.enums.ItemList.Sensor_MV;
import static gregtech.api.enums.ItemList.Sensor_UV;
import static gregtech.api.enums.ItemList.Sensor_ZPM;
import static gregtech.api.enums.Materials.Aluminium;
import static gregtech.api.enums.Materials.Americium;
import static gregtech.api.enums.Materials.AnnealedCopper;
import static gregtech.api.enums.Materials.BlackSteel;
import static gregtech.api.enums.Materials.Brass;
import static gregtech.api.enums.Materials.Bronze;
import static gregtech.api.enums.Materials.CertusQuartz;
import static gregtech.api.enums.Materials.Chrome;
import static gregtech.api.enums.Materials.Copper;
import static gregtech.api.enums.Materials.Cupronickel;
import static gregtech.api.enums.Materials.EV;
import static gregtech.api.enums.Materials.Electrum;
import static gregtech.api.enums.Materials.Emerald;
import static gregtech.api.enums.Materials.EnderEye;
import static gregtech.api.enums.Materials.EnderPearl;
import static gregtech.api.enums.Materials.Enderium;
import static gregtech.api.enums.Materials.Europium;
import static gregtech.api.enums.Materials.Gallium;
import static gregtech.api.enums.Materials.Gold;
import static gregtech.api.enums.Materials.Graphene;
import static gregtech.api.enums.Materials.HSSG;
import static gregtech.api.enums.Materials.HSSS;
import static gregtech.api.enums.Materials.HV;
import static gregtech.api.enums.Materials.IV;
import static gregtech.api.enums.Materials.Iridium;
import static gregtech.api.enums.Materials.Iron;
import static gregtech.api.enums.Materials.IronMagnetic;
import static gregtech.api.enums.Materials.LV;
import static gregtech.api.enums.Materials.LuV;
import static gregtech.api.enums.Materials.Lubricant;
import static gregtech.api.enums.Materials.MV;
import static gregtech.api.enums.Materials.Naquadah;
import static gregtech.api.enums.Materials.NaquadahAlloy;
import static gregtech.api.enums.Materials.Naquadria;
import static gregtech.api.enums.Materials.NeodymiumMagnetic;
import static gregtech.api.enums.Materials.NetherStar;
import static gregtech.api.enums.Materials.Neutronium;
import static gregtech.api.enums.Materials.NiobiumTitanium;
import static gregtech.api.enums.Materials.Osmiridium;
import static gregtech.api.enums.Materials.Platinum;
import static gregtech.api.enums.Materials.RedSteel;
import static gregtech.api.enums.Materials.Samarium;
import static gregtech.api.enums.Materials.SamariumMagnetic;
import static gregtech.api.enums.Materials.Silver;
import static gregtech.api.enums.Materials.StainlessSteel;
import static gregtech.api.enums.Materials.Steel;
import static gregtech.api.enums.Materials.SteelMagnetic;
import static gregtech.api.enums.Materials.Tin;
import static gregtech.api.enums.Materials.Titanium;
import static gregtech.api.enums.Materials.Trinium;
import static gregtech.api.enums.Materials.Tungsten;
import static gregtech.api.enums.Materials.TungstenSteel;
import static gregtech.api.enums.Materials.UHV;
import static gregtech.api.enums.Materials.UV;
import static gregtech.api.enums.Materials.VanadiumGallium;
import static gregtech.api.enums.Materials.YttriumBariumCuprate;
import static gregtech.api.enums.Materials.ZPM;
import static gregtech.api.enums.OrePrefixes.cableGt16;
import static gregtech.api.enums.OrePrefixes.frameGt;
import static gregtech.api.enums.OrePrefixes.gearGt;
import static gregtech.api.enums.OrePrefixes.gem;
import static gregtech.api.enums.OrePrefixes.gemFlawless;
import static gregtech.api.enums.OrePrefixes.pipeMedium;
import static gregtech.api.enums.OrePrefixes.plate;
import static gregtech.api.enums.OrePrefixes.plateDense;
import static gregtech.api.enums.OrePrefixes.rotor;
import static gregtech.api.enums.OrePrefixes.screw;
import static gregtech.api.enums.OrePrefixes.stickLong;
import static gregtech.api.enums.OrePrefixes.wireGt16;
import static gregtech.api.enums.OrePrefixes.wrapCircuit;
import static gregtech.api.enums.TierEU.RECIPE_EV;
import static gregtech.api.enums.TierEU.RECIPE_HV;
import static gregtech.api.enums.TierEU.RECIPE_IV;
import static gregtech.api.enums.TierEU.RECIPE_LV;
import static gregtech.api.enums.TierEU.RECIPE_LuV;
import static gregtech.api.enums.TierEU.RECIPE_MV;
import static gregtech.api.enums.TierEU.RECIPE_ULV;
import static gregtech.api.enums.TierEU.RECIPE_ZPM;
import static gregtech.api.util.GTRecipeBuilder.INGOTS;
import static gregtech.api.util.GTRecipeBuilder.MINUTES;
import static gregtech.api.util.GTRecipeBuilder.NUGGETS;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeBuilder.STACKS;
import static gregtech.api.util.GTRecipeConstants.COAL_CASING_TIER;
import static gtPlusPlus.core.material.MaterialsAlloy.INDALLOY_140;

import net.minecraft.item.ItemStack;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.util.recipeBuilder.RecipeDraft;
import com.LazyFlesh.variablehorizons.util.recipeBuilder.RecipeModifiers;
import com.LazyFlesh.variablehorizons.util.recipeBuilder.RecipePhase;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import bartworks.system.material.Werkstoff;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;

public class CoAL implements Runnable {

    private static final int COAL_LV = 1, COAL_MV = 2, COAL_HV = 3, COAL_EV = 4, COAL_IV = 5, COAL_LuV = 6,
        COAL_ZPM = 7, COAL_UV = 8, COAL_UHV = 9, COAL_UEV = 10, COAL_UIV = 11, COAL_UMV = 12, COAL_UXV = 13;

    private static final int MOTOR_CIRCUIT = 1, PISTON_CIRCUIT = 2, PUMP_CIRCUIT = 3, ROBOT_ARM_CIRCUIT = 4,
        CONVEYOR_CIRCUIT = 5, EMITTER_CIRCUIT = 6, SENSOR_CIRCUIT = 7, FIELD_GENERATOR_CIRCUIT = 8;

    private static final Materials[] RUBBERS = new Materials[] { Materials.Rubber, Materials.RubberSilicone,
        Materials.StyreneButadieneRubber };
    private static final String[] RUBBER_NAMES = new String[] { "rubber", "silicone", "styrene-butadiene" };
    private static final Materials[] COPPERS = new Materials[] { Copper, AnnealedCopper };
    private static final String[] COPPER_NAMES = new String[] { "copper", "annealed_copper" };

    @Override
    public void run() {
        lvParts();
        mvParts();
        hvParts();
        evParts();
        ivParts();
        luvParts();
        zpmParts();
        uvParts();
    }

    private static void lvParts() {
        for (int i = 0; i < COPPERS.length; i++) {
            int finalI = i;
            RecipeModifiers.defineBase(
                "coal/lv_motor_iron_" + COPPER_NAMES[i],
                () -> RecipeDraft.of(componentAssemblyLineRecipes)
                    .itemInputs(
                        get(stickLong, IronMagnetic, 24),
                        get(stickLong, Iron, 48),
                        get(wireGt16, COPPERS[finalI], 12),
                        get(cableGt16, Tin, 6))
                    .itemOutputs(Electric_Motor_LV.get(64))
                    .metadata(COAL_CASING_TIER, COAL_LV)
                    .duration(48 * SECONDS)
                    .eut(RECIPE_ULV));

            RecipeModifiers
                .modify("coal/lv_motor_iron_" + COPPER_NAMES[i], VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                    d.itemIn()
                        .condenseAll();
                });

            RecipeModifiers.defineBase(
                "coal/lv_motor_steel_" + COPPER_NAMES[i],
                () -> RecipeDraft.of(componentAssemblyLineRecipes)
                    .itemInputs(
                        get(stickLong, SteelMagnetic, 24),
                        get(stickLong, Steel, 48),
                        get(wireGt16, COPPERS[finalI], 12),
                        get(cableGt16, Tin, 6))
                    .itemOutputs(Electric_Motor_LV.get(64))
                    .metadata(COAL_CASING_TIER, COAL_LV)
                    .duration(48 * SECONDS)
                    .eut(RECIPE_ULV));

            RecipeModifiers.modify(
                "coal/lv_motor_steel_" + COPPER_NAMES[i],
                VariantNames.ALTERED_PRICING,
                RecipePhase.SCALE,
                d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                    d.itemIn()
                        .condenseAll();
                });
        }

        RecipeModifiers.defineBase(
            "coal/lv_piston",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(
                    get(Electric_Motor_LV, 48),
                    get(plateDense, Steel, 16),
                    get(stickLong, Steel, 48),
                    get(cableGt16, Tin, 6),
                    get(gearGt, Steel, 12))
                .itemOutputs(Electric_Piston_LV.get(64))
                .metadata(COAL_CASING_TIER, COAL_LV)
                .duration(48 * SECONDS)
                .eut(RECIPE_ULV));

        RecipeModifiers.modify("coal/lv_piston", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(Electric_Motor_LV, 48));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "coal/lv_robot_arm",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(
                    get(Electric_Motor_LV, 96),
                    get(Electric_Piston_LV, 48),
                    get(stickLong, Steel, 48),
                    get(wrapCircuit, LV, 3),
                    get(cableGt16, Tin, 9))
                .itemOutputs(Robot_Arm_LV.get(64))
                .metadata(COAL_CASING_TIER, COAL_LV)
                .duration(48 * SECONDS)
                .eut(RECIPE_ULV));

        RecipeModifiers.modify("coal/lv_robot_arm", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                get(Electric_Motor_LV, 96),
                get(Electric_Piston_LV, 48),
                get(wrapCircuit, LV, 3));
            d.itemIn()
                .condenseAll();
        });

        for (int i = 0; i < RUBBERS.length; i++) {
            int finalI = i;
            RecipeModifiers.defineBase(
                "coal/lv_pump_" + RUBBER_NAMES[i],
                () -> RecipeDraft.of(componentAssemblyLineRecipes)
                    .itemInputs(
                        get(Electric_Motor_LV, 48),
                        get(rotor, Tin, 48),
                        get(screw, Tin, 48),
                        get(cableGt16, Tin, 3),
                        get(pipeMedium, Bronze, 48))
                    .fluidInputs(RUBBERS[finalI].getMolten(24 * INGOTS))
                    .itemOutputs(Electric_Pump_LV.get(64))
                    .metadata(COAL_CASING_TIER, COAL_LV)
                    .duration(48 * SECONDS)
                    .eut(RECIPE_ULV));

            RecipeModifiers
                .modify("coal/lv_pump_" + RUBBER_NAMES[i], VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(Electric_Motor_LV, 96));
                    d.itemIn()
                        .condenseAll();
                });

            RecipeModifiers.defineBase(
                "coal/lv_conveyor_" + RUBBER_NAMES[i],
                () -> RecipeDraft.of(componentAssemblyLineRecipes)
                    .itemInputs(
                        get(Electric_Motor_LV, 96),
                        get(plateDense, RUBBERS[finalI], 32),
                        get(cableGt16, Tin, 3))
                    .itemOutputs(Conveyor_Module_LV.get(64))
                    .metadata(COAL_CASING_TIER, COAL_LV)
                    .duration(48 * SECONDS)
                    .eut(RECIPE_ULV));

            RecipeModifiers
                .modify("coal/lv_conveyor_" + RUBBER_NAMES[i], VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(Electric_Motor_LV, 96));
                    d.itemIn()
                        .condenseAll();
                });
        }

        RecipeModifiers.defineBase(
            "coal/lv_sensor",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(
                    get(gem, CertusQuartz, 48),
                    get(plateDense, Steel, 21),
                    get(stickLong, Brass, 24),
                    get(wrapCircuit, LV, 3))
                .itemOutputs(Sensor_LV.get(64))
                .metadata(COAL_CASING_TIER, COAL_LV)
                .duration(48 * SECONDS)
                .eut(RECIPE_ULV));

        RecipeModifiers.modify("coal/lv_sensor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(wrapCircuit, LV, 3));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "coal/lv_emitter",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(get(gem, CertusQuartz, 48), get(wrapCircuit, LV, 6), get(cableGt16, Tin, 6))
                .fluidInputs(Brass.getMolten(1 * STACKS + 32 * INGOTS))
                .itemOutputs(Emitter_LV.get(64))
                .metadata(COAL_CASING_TIER, COAL_LV)
                .duration(48 * SECONDS)
                .eut(RECIPE_ULV));

        RecipeModifiers.modify("coal/lv_emitter", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(wrapCircuit, LV, 6));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "coal/lv_field_gen",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(get(plate, EnderPearl, 48), get(wrapCircuit, HV, 12))
                .fluidInputs(RedSteel.getMolten(1 * STACKS + 32 * INGOTS))
                .itemOutputs(Field_Generator_LV.get(64))
                .metadata(COAL_CASING_TIER, COAL_LV)
                .duration(24 * MINUTES)
                .eut(RECIPE_ULV));

        RecipeModifiers.modify("coal/lv_field_gen", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(wrapCircuit, HV, 12));
            d.itemIn()
                .condenseAll();
        });
    }

    private static void mvParts() {
        for (int i = 0; i < COPPERS.length; i++) {
            int finalI = i;
            RecipeModifiers.defineBase(
                "coal/mv_motor_" + COPPER_NAMES[i],
                () -> RecipeDraft.of(componentAssemblyLineRecipes)
                    .itemInputs(
                        get(stickLong, SteelMagnetic, 24),
                        get(stickLong, Aluminium, 48),
                        get(wireGt16, Cupronickel, 24),
                        get(cableGt16, COPPERS[finalI], 6))
                    .itemOutputs(Electric_Motor_MV.get(64))
                    .metadata(COAL_CASING_TIER, COAL_MV)
                    .duration(48 * SECONDS)
                    .eut(RECIPE_LV));

            RecipeModifiers
                .modify("coal/mv_motor_" + COPPER_NAMES[i], VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
                    d.itemIn()
                        .condenseAll();
                });
        }

        RecipeModifiers.defineBase(
            "coal/mv_piston",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(
                    get(Electric_Motor_MV, 48),
                    get(plateDense, Aluminium, 16),
                    get(stickLong, Aluminium, 48),
                    get(cableGt16, Copper, 6),
                    get(gearGt, Aluminium, 12))
                .itemOutputs(Electric_Piston_MV.get(64))
                .metadata(COAL_CASING_TIER, COAL_MV)
                .duration(48 * SECONDS)
                .eut(RECIPE_LV));

        RecipeModifiers.modify("coal/mv_piston", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(Electric_Motor_MV, 48));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "coal/mv_robot_arm",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(
                    get(Electric_Motor_MV, 96),
                    get(Electric_Piston_MV, 48),
                    get(stickLong, Aluminium, 48),
                    get(wrapCircuit, MV, 3),
                    get(cableGt16, Copper, 9))
                .itemOutputs(Robot_Arm_MV.get(64))
                .metadata(COAL_CASING_TIER, COAL_MV)
                .duration(48 * SECONDS)
                .eut(RECIPE_LV));

        RecipeModifiers.modify("coal/mv_robot_arm", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                get(Electric_Motor_MV, 96),
                get(Electric_Piston_MV, 48),
                get(wrapCircuit, MV, 3));
            d.itemIn()
                .condenseAll();
        });

        for (int i = 0; i < RUBBERS.length; i++) {
            int finalI = i;
            RecipeModifiers.defineBase(
                "coal/mv_pump_" + RUBBER_NAMES[i],
                () -> RecipeDraft.of(componentAssemblyLineRecipes)
                    .itemInputs(
                        get(Electric_Motor_MV, 48),
                        get(rotor, Bronze, 48),
                        get(screw, Bronze, 48),
                        get(cableGt16, Copper, 3),
                        get(pipeMedium, Steel, 48))
                    .fluidInputs(RUBBERS[finalI].getMolten(24 * INGOTS))
                    .itemOutputs(Electric_Pump_MV.get(64))
                    .metadata(COAL_CASING_TIER, COAL_MV)
                    .duration(48 * SECONDS)
                    .eut(RECIPE_LV));

            RecipeModifiers
                .modify("coal/mv_pump_" + RUBBER_NAMES[i], VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(Electric_Motor_MV, 96));
                    d.itemIn()
                        .condenseAll();
                });

            RecipeModifiers.defineBase(
                "coal/mv_conveyor_" + RUBBER_NAMES[i],
                () -> RecipeDraft.of(componentAssemblyLineRecipes)
                    .itemInputs(
                        get(Electric_Motor_MV, 96),
                        get(plateDense, RUBBERS[finalI], 32),
                        get(cableGt16, Copper, 3))
                    .itemOutputs(Conveyor_Module_MV.get(64))
                    .metadata(COAL_CASING_TIER, COAL_MV)
                    .duration(48 * SECONDS)
                    .eut(RECIPE_LV));

            RecipeModifiers
                .modify("coal/mv_conveyor_" + RUBBER_NAMES[i], VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(Electric_Motor_MV, 96));
                    d.itemIn()
                        .condenseAll();
                });
        }

        RecipeModifiers.defineBase(
            "coal/mv_sensor",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(
                    get(gemFlawless, Emerald, 48),
                    get(plateDense, Aluminium, 21),
                    get(stickLong, Electrum, 24),
                    get(wrapCircuit, MV, 3))
                .itemOutputs(Sensor_MV.get(64))
                .metadata(COAL_CASING_TIER, COAL_MV)
                .duration(48 * SECONDS)
                .eut(RECIPE_LV));

        RecipeModifiers.modify("coal/mv_sensor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(wrapCircuit, MV, 3));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "coal/mv_emitter",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(get(gem, EnderPearl, 48), get(wrapCircuit, MV, 6), get(cableGt16, Copper, 6))
                .fluidInputs(Electrum.getMolten(1 * STACKS + 32 * INGOTS))
                .itemOutputs(Emitter_MV.get(64))
                .metadata(COAL_CASING_TIER, COAL_MV)
                .duration(48 * SECONDS)
                .eut(RECIPE_LV));

        RecipeModifiers.modify("coal/mv_emitter", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(wrapCircuit, MV, 6));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "coal/mv_field_gen",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(get(plate, EnderEye, 48), get(wrapCircuit, EV, 12))
                .fluidInputs(Titanium.getMolten(1 * STACKS + 32 * INGOTS))
                .itemOutputs(Field_Generator_MV.get(64))
                .metadata(COAL_CASING_TIER, COAL_MV)
                .duration(24 * MINUTES)
                .eut(RECIPE_LV));

        RecipeModifiers.modify("coal/mv_field_gen", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(wrapCircuit, EV, 12));
            d.itemIn()
                .condenseAll();
        });
    }

    private static void hvParts() {
        RecipeModifiers.defineBase(
            "coal/hv_motor",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(
                    get(stickLong, SteelMagnetic, 24),
                    get(stickLong, StainlessSteel, 48),
                    get(wireGt16, Electrum, 48),
                    get(cableGt16, Silver, 12))
                .itemOutputs(Electric_Motor_HV.get(64))
                .metadata(COAL_CASING_TIER, COAL_HV)
                .duration(48 * SECONDS)
                .eut(RECIPE_MV));

        RecipeModifiers.modify("coal/hv_motor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "coal/hv_piston",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(
                    get(Electric_Motor_HV, 48),
                    get(plateDense, StainlessSteel, 16),
                    get(stickLong, StainlessSteel, 48),
                    get(cableGt16, Gold, 6),
                    get(gearGt, StainlessSteel, 12))
                .itemOutputs(Electric_Piston_HV.get(64))
                .metadata(COAL_CASING_TIER, COAL_HV)
                .duration(48 * SECONDS)
                .eut(RECIPE_MV));

        RecipeModifiers.modify("coal/hv_piston", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(Electric_Motor_HV, 48));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "coal/hv_robot_arm",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(
                    get(Electric_Motor_HV, 96),
                    get(Electric_Piston_HV, 48),
                    get(stickLong, StainlessSteel, 48),
                    get(wrapCircuit, HV, 3),
                    get(cableGt16, Gold, 9))
                .itemOutputs(Robot_Arm_HV.get(64))
                .metadata(COAL_CASING_TIER, COAL_HV)
                .duration(48 * SECONDS)
                .eut(RECIPE_MV));

        RecipeModifiers.modify("coal/hv_robot_arm", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                get(Electric_Motor_HV, 96),
                get(Electric_Piston_HV, 48),
                get(wrapCircuit, HV, 3));
            d.itemIn()
                .condenseAll();
        });

        for (int i = 0; i < RUBBERS.length; i++) {
            int finalI = i;
            RecipeModifiers.defineBase(
                "coal/hv_pump_" + RUBBER_NAMES[i],
                () -> RecipeDraft.of(componentAssemblyLineRecipes)
                    .itemInputs(
                        get(Electric_Motor_HV, 48),
                        get(rotor, Steel, 48),
                        get(screw, Steel, 48),
                        get(cableGt16, Gold, 3),
                        get(pipeMedium, StainlessSteel, 48))
                    .fluidInputs(RUBBERS[finalI].getMolten(24 * INGOTS))
                    .itemOutputs(Electric_Pump_HV.get(64))
                    .metadata(COAL_CASING_TIER, COAL_HV)
                    .duration(48 * SECONDS)
                    .eut(RECIPE_MV));

            RecipeModifiers
                .modify("coal/hv_pump_" + RUBBER_NAMES[i], VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(Electric_Motor_HV, 96));
                    d.itemIn()
                        .condenseAll();
                });

            RecipeModifiers.defineBase(
                "coal/hv_conveyor_" + RUBBER_NAMES[i],
                () -> RecipeDraft.of(componentAssemblyLineRecipes)
                    .itemInputs(
                        get(Electric_Motor_HV, 96),
                        get(plateDense, RUBBERS[finalI], 32),
                        get(cableGt16, Gold, 3))
                    .itemOutputs(Conveyor_Module_HV.get(64))
                    .metadata(COAL_CASING_TIER, COAL_HV)
                    .duration(48 * SECONDS)
                    .eut(RECIPE_MV));

            RecipeModifiers
                .modify("coal/hv_conveyor_" + RUBBER_NAMES[i], VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(Electric_Motor_HV, 96));
                    d.itemIn()
                        .condenseAll();
                });
        }

        RecipeModifiers.defineBase(
            "coal/hv_sensor",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(
                    get(gem, EnderEye, 48),
                    get(plateDense, StainlessSteel, 21),
                    get(stickLong, Chrome, 24),
                    get(wrapCircuit, HV, 3))
                .itemOutputs(Sensor_HV.get(64))
                .metadata(COAL_CASING_TIER, COAL_HV)
                .duration(48 * SECONDS)
                .eut(RECIPE_MV));

        RecipeModifiers.modify("coal/hv_sensor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(wrapCircuit, HV, 3));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "coal/hv_emitter",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(get(gem, EnderEye, 48), get(wrapCircuit, HV, 6), get(cableGt16, Gold, 6))
                .fluidInputs(Chrome.getMolten(1 * STACKS + 32 * INGOTS))
                .itemOutputs(Emitter_HV.get(64))
                .metadata(COAL_CASING_TIER, COAL_HV)
                .duration(48 * SECONDS)
                .eut(RECIPE_MV));

        RecipeModifiers.modify("coal/hv_emitter", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(wrapCircuit, HV, 6));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "coal/hv_field_gen",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(get(QuantumEye, 48), get(wrapCircuit, IV, 12))
                .fluidInputs(NiobiumTitanium.getMolten(3 * STACKS))
                .itemOutputs(Field_Generator_HV.get(64))
                .metadata(COAL_CASING_TIER, COAL_HV)
                .duration(24 * MINUTES)
                .eut(RECIPE_MV));

        RecipeModifiers.modify("coal/hv_field_gen", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(wrapCircuit, IV, 12));
            d.itemIn()
                .condenseAll();
        });
    }

    private static void evParts() {
        RecipeModifiers.defineBase(
            "coal/ev_motor",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(
                    get(stickLong, NeodymiumMagnetic, 24),
                    get(stickLong, Titanium, 48),
                    get(wireGt16, BlackSteel, 48),
                    get(cableGt16, Aluminium, 12))
                .itemOutputs(Electric_Motor_EV.get(64))
                .metadata(COAL_CASING_TIER, COAL_EV)
                .duration(48 * SECONDS)
                .eut(RECIPE_HV));

        RecipeModifiers.modify("coal/ev_motor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "coal/ev_piston",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(
                    get(Electric_Motor_EV, 48),
                    get(plateDense, Titanium, 16),
                    get(stickLong, Titanium, 48),
                    get(cableGt16, Aluminium, 6),
                    get(gearGt, Titanium, 12))
                .itemOutputs(Electric_Piston_EV.get(64))
                .metadata(COAL_CASING_TIER, COAL_EV)
                .duration(48 * SECONDS)
                .eut(RECIPE_HV));

        RecipeModifiers.modify("coal/ev_piston", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(Electric_Motor_EV, 48));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "coal/ev_robot_arm",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(
                    get(Electric_Motor_EV, 96),
                    get(Electric_Piston_EV, 48),
                    get(stickLong, Titanium, 48),
                    get(wrapCircuit, EV, 3),
                    get(cableGt16, Aluminium, 9))
                .itemOutputs(Robot_Arm_EV.get(64))
                .metadata(COAL_CASING_TIER, COAL_EV)
                .duration(48 * SECONDS)
                .eut(RECIPE_HV));

        RecipeModifiers.modify("coal/ev_robot_arm", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                get(Electric_Motor_EV, 96),
                get(Electric_Piston_EV, 48),
                get(wrapCircuit, EV, 3));
            d.itemIn()
                .condenseAll();
        });

        for (int i = 0; i < RUBBERS.length; i++) {
            int finalI = i;
            RecipeModifiers.defineBase(
                "coal/ev_pump_" + RUBBER_NAMES[i],
                () -> RecipeDraft.of(componentAssemblyLineRecipes)
                    .itemInputs(
                        get(Electric_Motor_EV, 48),
                        get(rotor, StainlessSteel, 48),
                        get(screw, StainlessSteel, 48),
                        get(cableGt16, Aluminium, 3),
                        get(pipeMedium, Titanium, 48))
                    .fluidInputs(RUBBERS[finalI].getMolten(24 * INGOTS))
                    .itemOutputs(Electric_Pump_EV.get(64))
                    .metadata(COAL_CASING_TIER, COAL_EV)
                    .duration(48 * SECONDS)
                    .eut(RECIPE_HV));

            RecipeModifiers
                .modify("coal/ev_pump_" + RUBBER_NAMES[i], VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(Electric_Motor_EV, 96));
                    d.itemIn()
                        .condenseAll();
                });

            RecipeModifiers.defineBase(
                "coal/ev_conveyor_" + RUBBER_NAMES[i],
                () -> RecipeDraft.of(componentAssemblyLineRecipes)
                    .itemInputs(
                        get(Electric_Motor_EV, 96),
                        get(plateDense, RUBBERS[finalI], 32),
                        get(cableGt16, Aluminium, 3))
                    .itemOutputs(Conveyor_Module_EV.get(64))
                    .metadata(COAL_CASING_TIER, COAL_EV)
                    .duration(48 * SECONDS)
                    .eut(RECIPE_HV));

            RecipeModifiers
                .modify("coal/ev_conveyor_" + RUBBER_NAMES[i], VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(Electric_Motor_EV, 96));
                    d.itemIn()
                        .condenseAll();
                });
        }

        RecipeModifiers.defineBase(
            "coal/ev_sensor",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(
                    get(QuantumEye, 48),
                    get(plateDense, Titanium, 21),
                    get(stickLong, Platinum, 24),
                    get(wrapCircuit, EV, 3))
                .itemOutputs(Sensor_EV.get(64))
                .metadata(COAL_CASING_TIER, COAL_EV)
                .duration(48 * SECONDS)
                .eut(RECIPE_HV));

        RecipeModifiers.modify("coal/ev_sensor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(wrapCircuit, EV, 3));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "coal/ev_emitter",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(get(QuantumEye, 48), get(wrapCircuit, EV, 6), get(cableGt16, Aluminium, 6))
                .fluidInputs(Platinum.getMolten(1 * STACKS + 32 * INGOTS))
                .itemOutputs(Emitter_EV.get(64))
                .metadata(COAL_CASING_TIER, COAL_EV)
                .duration(48 * SECONDS)
                .eut(RECIPE_HV));

        RecipeModifiers.modify("coal/ev_emitter", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(wrapCircuit, EV, 6));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "coal/ev_field_gen",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(get(gem, NetherStar, 48), get(wrapCircuit, LuV, 12))
                .fluidInputs(HSSG.getMolten(3 * STACKS))
                .itemOutputs(Field_Generator_EV.get(64))
                .metadata(COAL_CASING_TIER, COAL_EV)
                .duration(24 * MINUTES)
                .eut(RECIPE_HV));

        RecipeModifiers.modify("coal/ev_field_gen", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(wrapCircuit, LuV, 12));
            d.itemIn()
                .condenseAll();
        });
    }

    private static void ivParts() {
        RecipeModifiers.defineBase(
            "coal/iv_motor",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(
                    get(stickLong, NeodymiumMagnetic, 24),
                    get(stickLong, TungstenSteel, 48),
                    get(wireGt16, Graphene, 48),
                    get(cableGt16, Tungsten, 12),
                    GTUtility.getIntegratedCircuit(MOTOR_CIRCUIT))
                .itemOutputs(Electric_Motor_IV.get(64))
                .metadata(COAL_CASING_TIER, COAL_IV)
                .duration(48 * SECONDS)
                .eut(RECIPE_EV));

        RecipeModifiers.modify("coal/iv_motor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "coal/iv_piston",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(
                    get(Electric_Motor_IV, 48),
                    get(plateDense, TungstenSteel, 16),
                    get(stickLong, TungstenSteel, 48),
                    get(cableGt16, Tungsten, 6),
                    get(gearGt, TungstenSteel, 12),
                    GTUtility.getIntegratedCircuit(PISTON_CIRCUIT))
                .itemOutputs(Electric_Piston_IV.get(64))
                .metadata(COAL_CASING_TIER, COAL_IV)
                .duration(48 * SECONDS)
                .eut(RECIPE_EV));

        RecipeModifiers.modify("coal/iv_piston", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(Electric_Motor_IV, 48));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "coal/iv_robot_arm",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(
                    get(Electric_Motor_IV, 96),
                    get(Electric_Piston_IV, 48),
                    get(stickLong, TungstenSteel, 48),
                    get(wrapCircuit, IV, 3),
                    get(cableGt16, Tungsten, 9),
                    GTUtility.getIntegratedCircuit(ROBOT_ARM_CIRCUIT))
                .itemOutputs(Robot_Arm_IV.get(64))
                .metadata(COAL_CASING_TIER, COAL_IV)
                .duration(48 * SECONDS)
                .eut(RECIPE_EV));

        RecipeModifiers.modify("coal/iv_robot_arm", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                get(Electric_Motor_IV, 96),
                get(Electric_Piston_IV, 48),
                get(wrapCircuit, IV, 3));
            d.itemIn()
                .condenseAll();
        });

        for (int i = 1; i < RUBBERS.length; i++) {
            int finalI = i;
            RecipeModifiers.defineBase(
                "coal/iv_pump_" + RUBBER_NAMES[i],
                () -> RecipeDraft.of(componentAssemblyLineRecipes)
                    .itemInputs(
                        get(Electric_Motor_IV, 48),
                        get(rotor, TungstenSteel, 48),
                        get(screw, TungstenSteel, 48),
                        get(cableGt16, Tungsten, 3),
                        get(pipeMedium, TungstenSteel, 48),
                        GTUtility.getIntegratedCircuit(PUMP_CIRCUIT))
                    .fluidInputs(RUBBERS[finalI].getMolten(24 * INGOTS))
                    .itemOutputs(Electric_Pump_IV.get(64))
                    .metadata(COAL_CASING_TIER, COAL_IV)
                    .duration(48 * SECONDS)
                    .eut(RECIPE_EV));

            RecipeModifiers
                .modify("coal/iv_pump_" + RUBBER_NAMES[i], VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(Electric_Motor_IV, 96));
                    d.itemIn()
                        .condenseAll();
                });

            RecipeModifiers.defineBase(
                "coal/iv_conveyor_" + RUBBER_NAMES[i],
                () -> RecipeDraft.of(componentAssemblyLineRecipes)
                    .itemInputs(
                        get(Electric_Motor_IV, 96),
                        get(plateDense, RUBBERS[finalI], 32),
                        get(cableGt16, Tungsten, 3),
                        GTUtility.getIntegratedCircuit(CONVEYOR_CIRCUIT))
                    .itemOutputs(Conveyor_Module_IV.get(64))
                    .metadata(COAL_CASING_TIER, COAL_IV)
                    .duration(48 * SECONDS)
                    .eut(RECIPE_EV));

            RecipeModifiers
                .modify("coal/iv_conveyor_" + RUBBER_NAMES[i], VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(Electric_Motor_IV, 96));
                    d.itemIn()
                        .condenseAll();
                });
        }

        RecipeModifiers.defineBase(
            "coal/iv_sensor",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(
                    get(QuantumStar, 48),
                    get(plateDense, TungstenSteel, 21),
                    get(stickLong, Iridium, 24),
                    get(wrapCircuit, IV, 3),
                    GTUtility.getIntegratedCircuit(SENSOR_CIRCUIT))
                .itemOutputs(Sensor_IV.get(64))
                .metadata(COAL_CASING_TIER, COAL_IV)
                .duration(48 * SECONDS)
                .eut(RECIPE_EV));

        RecipeModifiers.modify("coal/iv_sensor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(wrapCircuit, IV, 3));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "coal/iv_emitter",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(
                    get(QuantumStar, 48),
                    get(wrapCircuit, IV, 6),
                    get(cableGt16, Tungsten, 6),
                    GTUtility.getIntegratedCircuit(EMITTER_CIRCUIT))
                .fluidInputs(Iridium.getMolten(1 * STACKS + 32 * INGOTS))
                .itemOutputs(Emitter_IV.get(64))
                .metadata(COAL_CASING_TIER, COAL_IV)
                .duration(48 * SECONDS)
                .eut(RECIPE_EV));

        RecipeModifiers.modify("coal/iv_emitter", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(wrapCircuit, IV, 6));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "coal/iv_field_gen",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(
                    get(QuantumStar, 48),
                    get(wrapCircuit, ZPM, 12),
                    GTUtility.getIntegratedCircuit(FIELD_GENERATOR_CIRCUIT))
                .fluidInputs(HSSS.getMolten(3 * STACKS))
                .itemOutputs(Field_Generator_IV.get(64))
                .metadata(COAL_CASING_TIER, COAL_IV)
                .duration(24 * MINUTES)
                .eut(RECIPE_EV));

        RecipeModifiers.modify("coal/iv_field_gen", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(wrapCircuit, ZPM, 12));
            d.itemIn()
                .condenseAll();
        });
    }

    private static void luvParts() {
        RecipeModifiers.defineBase(
            "coal/luv_motor",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(
                    get(stickLong, SamariumMagnetic, 24),
                    get(cableGt16, YttriumBariumCuprate, 6),
                    GTUtility.getIntegratedCircuit(MOTOR_CIRCUIT))
                .fluidInputs(
                    INDALLOY_140.getFluidStack(48 * INGOTS),
                    Lubricant.getFluid(12_000),
                    Ruridit.getMolten(12 * STACKS),
                    HSSS.getMolten(1 * STACKS + 32 * INGOTS))
                .itemOutputs(Electric_Motor_LuV.get(64))
                .metadata(COAL_CASING_TIER, COAL_LuV)
                .duration(24 * MINUTES)
                .eut(RECIPE_IV));

        RecipeModifiers.modify("coal/luv_motor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "coal/luv_piston",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(
                    get(Electric_Motor_LuV, 48),
                    get(plateDense, HSSS, 32),
                    get(cableGt16, YttriumBariumCuprate, 12),
                    GTUtility.getIntegratedCircuit(PISTON_CIRCUIT))
                .fluidInputs(
                    INDALLOY_140.getFluidStack(48 * INGOTS),
                    Lubricant.getFluid(12_000),
                    HSSS.getMolten(9 * STACKS + 26 * INGOTS + 6 * NUGGETS))
                .itemOutputs(Electric_Piston_LuV.get(64))
                .metadata(COAL_CASING_TIER, COAL_LuV)
                .duration(24 * MINUTES)
                .eut(RECIPE_IV));

        RecipeModifiers.modify("coal/luv_piston", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(Electric_Motor_LuV, 48));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "coal/luv_robot_arm",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(
                    get(Electric_Motor_LuV, 96),
                    get(Electric_Piston_LuV, 48),
                    get(wrapCircuit, LuV, 6),
                    get(wrapCircuit, IV, 12),
                    get(wrapCircuit, EV, 24),
                    get(cableGt16, YttriumBariumCuprate, 18),
                    GTUtility.getIntegratedCircuit(ROBOT_ARM_CIRCUIT))
                .fluidInputs(
                    INDALLOY_140.getFluidStack(3 * STACKS),
                    Lubricant.getFluid(12_000),
                    HSSS.getMolten(8 * STACKS + 16 * INGOTS))
                .itemOutputs(Robot_Arm_LuV.get(64))
                .metadata(COAL_CASING_TIER, COAL_LuV)
                .duration(24 * MINUTES)
                .eut(RECIPE_IV));

        RecipeModifiers.modify("coal/luv_robot_arm", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                get(Electric_Motor_LuV, 96),
                get(Electric_Piston_LuV, 48),
                get(wrapCircuit, LuV, 6),
                get(wrapCircuit, IV, 12),
                get(wrapCircuit, EV, 24));
            d.itemIn()
                .condenseAll();
        });

        for (int i = 1; i < RUBBERS.length; i++) {
            int finalI = i;
            RecipeModifiers.defineBase(
                "coal/luv_pump_" + RUBBER_NAMES[i],
                () -> RecipeDraft.of(componentAssemblyLineRecipes)
                    .itemInputs(
                        get(Electric_Motor_LuV, 48),
                        get(plateDense, HSSS, 10),
                        get(cableGt16, YttriumBariumCuprate, 6),
                        GTUtility.getIntegratedCircuit(PUMP_CIRCUIT))
                    .fluidInputs(
                        INDALLOY_140.getFluidStack(48 * INGOTS),
                        Lubricant.getFluid(12_000),
                        HSSS.getMolten(7 * STACKS + 2 * INGOTS + 6 * NUGGETS),
                        NiobiumTitanium.getMolten(1 * STACKS + 32 * INGOTS),
                        RUBBERS[finalI].getMolten(48 * INGOTS))
                    .itemOutputs(Electric_Pump_LuV.get(64))
                    .metadata(COAL_CASING_TIER, COAL_LuV)
                    .duration(24 * MINUTES)
                    .eut(RECIPE_IV));

            RecipeModifiers
                .modify("coal/luv_pump_" + RUBBER_NAMES[i], VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(Electric_Motor_LuV, 96));
                    d.itemIn()
                        .condenseAll();
                });

            RecipeModifiers.defineBase(
                "coal/luv_conveyor_" + RUBBER_NAMES[i],
                () -> RecipeDraft.of(componentAssemblyLineRecipes)
                    .itemInputs(
                        get(Electric_Motor_LuV, 96),
                        get(plateDense, HSSS, 10),
                        get(cableGt16, YttriumBariumCuprate, 6),
                        get(plateDense, RUBBERS[finalI], 53),
                        GTUtility.getIntegratedCircuit(CONVEYOR_CIRCUIT))
                    .fluidInputs(
                        INDALLOY_140.getFluidStack(48 * INGOTS),
                        Lubricant.getFluid(12_000),
                        HSSS.getMolten(3 * STACKS + 26 * INGOTS + 6 * NUGGETS))
                    .itemOutputs(Conveyor_Module_LuV.get(64))
                    .metadata(COAL_CASING_TIER, COAL_LuV)
                    .duration(24 * MINUTES)
                    .eut(RECIPE_IV));

            RecipeModifiers
                .modify("coal/luv_conveyor_" + RUBBER_NAMES[i], VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(Electric_Motor_LuV, 96));
                    d.itemIn()
                        .condenseAll();
                });
        }

        RecipeModifiers.defineBase(
            "coal/luv_sensor",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(
                    get(frameGt, HSSS, 48),
                    get(Electric_Motor_LuV, 48),
                    get(plateDense, Ruridit, 42),
                    get(QuantumStar, 48),
                    get(wrapCircuit, LuV, 12),
                    get(cableGt16, YttriumBariumCuprate, 21),
                    GTUtility.getIntegratedCircuit(SENSOR_CIRCUIT))
                .fluidInputs(INDALLOY_140.getFluidStack(3 * STACKS), Gallium.getMolten(36 * STACKS))
                .itemOutputs(Sensor_LuV.get(64))
                .metadata(COAL_CASING_TIER, COAL_LuV)
                .duration(24 * MINUTES)
                .eut(RECIPE_IV));

        RecipeModifiers.modify("coal/luv_sensor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                get(wrapCircuit, LuV, 3),
                get(Electric_Motor_LuV, 48));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "coal/luv_emitter",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(
                    get(frameGt, HSSS, 48),
                    get(Electric_Motor_LuV, 48),
                    get(QuantumStar, 48),
                    get(wrapCircuit, LuV, 12),
                    get(cableGt16, YttriumBariumCuprate, 21),
                    GTUtility.getIntegratedCircuit(EMITTER_CIRCUIT))
                .fluidInputs(
                    INDALLOY_140.getFluidStack(3 * STACKS),
                    Gallium.getMolten(36 * STACKS),
                    Ruridit.getMolten(3 * STACKS))
                .itemOutputs(Emitter_LuV.get(64))
                .metadata(COAL_CASING_TIER, COAL_LuV)
                .duration(24 * MINUTES)
                .eut(RECIPE_IV));

        RecipeModifiers.modify("coal/luv_emitter", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                get(wrapCircuit, LuV, 6),
                get(Electric_Motor_LuV, 48));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "coal/luv_field_gen",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(
                    get(frameGt, HSSS, 48),
                    get(plateDense, HSSS, 32),
                    get(QuantumStar, 96),
                    get(Emitter_LuV, 192),
                    get(wrapCircuit, Materials.ZPM, 12),
                    get(cableGt16, YttriumBariumCuprate, 24),
                    GTUtility.getIntegratedCircuit(FIELD_GENERATOR_CIRCUIT))
                .fluidInputs(INDALLOY_140.getFluidStack(3 * STACKS), Ruridit.getMolten(24 * STACKS))
                .itemOutputs(Field_Generator_LuV.get(64))
                .metadata(COAL_CASING_TIER, COAL_LuV)
                .duration(24 * MINUTES)
                .eut(RECIPE_IV));

        RecipeModifiers.modify("coal/luv_field_gen", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                get(wrapCircuit, ZPM, 12),
                get(Emitter_LuV, 192));
            d.itemIn()
                .condenseAll();
        });
    }

    private static void zpmParts() {
        RecipeModifiers.defineBase(
            "coal/zpm_motor",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(
                    get(stickLong, SamariumMagnetic, 48),
                    get(cableGt16, VanadiumGallium, 24),
                    GTUtility.getIntegratedCircuit(MOTOR_CIRCUIT))
                .fluidInputs(
                    INDALLOY_140.getFluidStack(1 * STACKS + 32 * INGOTS),
                    Lubricant.getFluid(36_000),
                    Europium.getMolten(18 * STACKS),
                    NaquadahAlloy.getMolten(5 * STACKS + 5 * INGOTS + 3 * NUGGETS))
                .itemOutputs(Electric_Motor_ZPM.get(64))
                .metadata(COAL_CASING_TIER, COAL_ZPM)
                .duration(24 * MINUTES)
                .eut(RECIPE_LuV));

        RecipeModifiers.modify("coal/zpm_motor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "coal/zpm_piston",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(
                    get(Electric_Motor_ZPM, 48),
                    get(plateDense, NaquadahAlloy, 32),
                    get(cableGt16, VanadiumGallium, 48),
                    GTUtility.getIntegratedCircuit(PISTON_CIRCUIT))
                .fluidInputs(
                    INDALLOY_140.getFluidStack(1 * STACKS + 32 * INGOTS),
                    Lubricant.getFluid(36_000),
                    NaquadahAlloy.getMolten(9 * STACKS + 26 * INGOTS + 6 * NUGGETS))
                .itemOutputs(Electric_Piston_ZPM.get(64))
                .metadata(COAL_CASING_TIER, COAL_ZPM)
                .duration(24 * MINUTES)
                .eut(RECIPE_LuV));

        RecipeModifiers.modify("coal/zpm_piston", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(Electric_Motor_ZPM, 48));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "coal/zpm_robot_arm",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(
                    get(Electric_Motor_ZPM, 96),
                    get(Electric_Piston_ZPM, 48),
                    get(wrapCircuit, ZPM, 6),
                    get(wrapCircuit, LuV, 12),
                    get(wrapCircuit, IV, 24),
                    GTUtility.getIntegratedCircuit(ROBOT_ARM_CIRCUIT))
                .fluidInputs(
                    INDALLOY_140.getFluidStack(6 * STACKS),
                    Lubricant.getFluid(36_000),
                    VanadiumGallium.getMolten(9 * STACKS),
                    NaquadahAlloy.getMolten(8 * STACKS + 16 * INGOTS))
                .itemOutputs(Robot_Arm_ZPM.get(64))
                .metadata(COAL_CASING_TIER, COAL_ZPM)
                .duration(24 * MINUTES)
                .eut(RECIPE_LuV));

        RecipeModifiers.modify("coal/zpm_robot_arm", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                get(Electric_Motor_ZPM, 96),
                get(Electric_Piston_ZPM, 48),
                get(wrapCircuit, ZPM, 6),
                get(wrapCircuit, LuV, 12),
                get(wrapCircuit, IV, 24));
            d.itemIn()
                .condenseAll();
        });

        for (int i = 1; i < RUBBERS.length; i++) {
            int finalI = i;
            RecipeModifiers.defineBase(
                "coal/zpm_pump_" + RUBBER_NAMES[i],
                () -> RecipeDraft.of(componentAssemblyLineRecipes)
                    .itemInputs(
                        get(Electric_Motor_ZPM, 48),
                        get(plateDense, NaquadahAlloy, 10),
                        get(cableGt16, VanadiumGallium, 24),
                        GTUtility.getIntegratedCircuit(PUMP_CIRCUIT))
                    .fluidInputs(
                        INDALLOY_140.getFluidStack(1 * STACKS + 32 * INGOTS),
                        Lubricant.getFluid(36_000),
                        NaquadahAlloy.getMolten(7 * STACKS + 2 * INGOTS + 6 * NUGGETS),
                        Enderium.getMolten(4 * STACKS + 32 * INGOTS),
                        RUBBERS[finalI].getMolten(1 * STACKS + 32 * INGOTS))
                    .itemOutputs(Electric_Pump_ZPM.get(64))
                    .metadata(COAL_CASING_TIER, COAL_ZPM)
                    .duration(24 * MINUTES)
                    .eut(RECIPE_LuV));

            RecipeModifiers
                .modify("coal/zpm_pump_" + RUBBER_NAMES[i], VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(Electric_Motor_ZPM, 48));
                    d.itemIn()
                        .condenseAll();
                });

            RecipeModifiers.defineBase(
                "coal/zpm_conveyor_" + RUBBER_NAMES[i],
                () -> RecipeDraft.of(componentAssemblyLineRecipes)
                    .itemInputs(
                        get(Electric_Motor_ZPM, 96),
                        get(plateDense, NaquadahAlloy, 10),
                        get(cableGt16, VanadiumGallium, 24),
                        GTUtility.getIntegratedCircuit(CONVEYOR_CIRCUIT))
                    .fluidInputs(
                        INDALLOY_140.getFluidStack(1 * STACKS + 32 * INGOTS),
                        Lubricant.getFluid(36_000),
                        RUBBERS[finalI].getMolten(14 * STACKS + 58 * INGOTS),
                        NaquadahAlloy.getMolten(3 * STACKS + 26 * INGOTS + 6 * NUGGETS))
                    .itemOutputs(Conveyor_Module_ZPM.get(64))
                    .metadata(COAL_CASING_TIER, COAL_ZPM)
                    .duration(24 * MINUTES)
                    .eut(RECIPE_LuV));

            RecipeModifiers
                .modify("coal/zpm_conveyor_" + RUBBER_NAMES[i], VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(Electric_Motor_ZPM, 96));
                    d.itemIn()
                        .condenseAll();
                });
        }

        RecipeModifiers.defineBase(
            "coal/zpm_sensor",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(
                    get(frameGt, NaquadahAlloy, 48),
                    get(Electric_Motor_ZPM, 48),
                    get(plateDense, Osmiridium, 42),
                    get(QuantumStar, 96),
                    get(wrapCircuit, ZPM, 12),
                    GTUtility.getIntegratedCircuit(SENSOR_CIRCUIT))
                .fluidInputs(
                    INDALLOY_140.getFluidStack(6 * STACKS),
                    Trinium.getMolten(36 * STACKS),
                    VanadiumGallium.getMolten(10 * STACKS + 32 * INGOTS))
                .itemOutputs(Sensor_ZPM.get(64))
                .metadata(COAL_CASING_TIER, COAL_ZPM)
                .duration(24 * MINUTES)
                .eut(RECIPE_LuV));

        RecipeModifiers.modify("coal/zpm_sensor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                get(wrapCircuit, ZPM, 3),
                get(Electric_Motor_ZPM, 48));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "coal/zpm_emitter",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(
                    get(frameGt, NaquadahAlloy, 48),
                    get(Electric_Motor_ZPM, 48),
                    get(QuantumStar, 96),
                    get(wrapCircuit, ZPM, 12),
                    GTUtility.getIntegratedCircuit(EMITTER_CIRCUIT))
                .fluidInputs(
                    INDALLOY_140.getFluidStack(6 * STACKS),
                    Trinium.getMolten(36 * STACKS),
                    VanadiumGallium.getMolten(10 * STACKS + 32 * INGOTS),
                    Osmiridium.getMolten(3 * STACKS))
                .itemOutputs(Emitter_ZPM.get(64))
                .metadata(COAL_CASING_TIER, COAL_ZPM)
                .duration(24 * MINUTES)
                .eut(RECIPE_LuV));

        RecipeModifiers.modify("coal/zpm_emitter", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                get(wrapCircuit, ZPM, 6),
                get(Electric_Motor_ZPM, 48));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "coal/zpm_field_gen",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(
                    get(frameGt, NaquadahAlloy, 48),
                    get(plateDense, NaquadahAlloy, 32),
                    get(QuantumStar, 96),
                    get(Emitter_ZPM, 192),
                    get(wrapCircuit, UV, 12),
                    GTUtility.getIntegratedCircuit(FIELD_GENERATOR_CIRCUIT))
                .fluidInputs(
                    INDALLOY_140.getFluidStack(6 * STACKS),
                    Europium.getMolten(24 * STACKS),
                    VanadiumGallium.getMolten(12 * STACKS))
                .itemOutputs(Field_Generator_ZPM.get(64))
                .metadata(COAL_CASING_TIER, COAL_ZPM)
                .duration(24 * MINUTES)
                .eut(RECIPE_LuV));

        RecipeModifiers.modify("coal/zpm_field_gen", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(wrapCircuit, UV, 12), get(Emitter_ZPM, 192));
            d.itemIn()
                .condenseAll();
        });
    }

    private static void uvParts() {
        RecipeModifiers.defineBase(
            "coal/uv_motor",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(get(cableGt16, NaquadahAlloy, 24), GTUtility.getIntegratedCircuit(MOTOR_CIRCUIT))
                .fluidInputs(
                    INDALLOY_140.getFluidStack(6 * STACKS + 48 * INGOTS),
                    Lubricant.getFluid(96_000),
                    Americium.getMolten(36 * STACKS),
                    Naquadria.getMolten(6 * STACKS + 48 * INGOTS),
                    Neutronium.getMolten(5 * STACKS + 5 * INGOTS + 3 * NUGGETS),
                    Samarium.getMolten(1 * STACKS + 32 * INGOTS))
                .itemOutputs(Electric_Motor_UV.get(64))
                .metadata(COAL_CASING_TIER, COAL_UV)
                .duration(24 * MINUTES)
                .eut(RECIPE_ZPM));

        RecipeModifiers.modify("coal/uv_motor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "coal/uv_piston",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(
                    get(Electric_Motor_UV, 48),
                    get(plateDense, Neutronium, 32),
                    get(cableGt16, NaquadahAlloy, 48),
                    GTUtility.getIntegratedCircuit(PISTON_CIRCUIT))
                .fluidInputs(
                    INDALLOY_140.getFluidStack(6 * STACKS + 48 * INGOTS),
                    Lubricant.getFluid(96_000),
                    Neutronium.getMolten(9 * STACKS + 26 * INGOTS + 6 * NUGGETS),
                    Naquadria.getMolten(6 * STACKS + 48 * INGOTS))
                .itemOutputs(Electric_Piston_UV.get(64))
                .metadata(COAL_CASING_TIER, COAL_UV)
                .duration(24 * MINUTES)
                .eut(RECIPE_ZPM));

        RecipeModifiers.modify("coal/uv_piston", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(Electric_Motor_UV, 48));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "coal/uv_robot_arm",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(
                    get(Electric_Motor_UV, 96),
                    get(Electric_Piston_UV, 48),
                    get(wrapCircuit, UV, 6),
                    get(wrapCircuit, ZPM, 12),
                    get(wrapCircuit, LuV, 24),
                    GTUtility.getIntegratedCircuit(ROBOT_ARM_CIRCUIT))
                .fluidInputs(
                    INDALLOY_140.getFluidStack(12 * STACKS),
                    Lubricant.getFluid(96_000),
                    NaquadahAlloy.getMolten(9 * STACKS),
                    Neutronium.getMolten(8 * STACKS + 16 * INGOTS),
                    Naquadria.getMolten(6 * STACKS + 48 * INGOTS))
                .itemOutputs(Robot_Arm_UV.get(64))
                .metadata(COAL_CASING_TIER, COAL_UV)
                .duration(24 * MINUTES)
                .eut(RECIPE_ZPM));

        RecipeModifiers.modify("coal/uv_robot_arm", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                get(Electric_Motor_UV, 96),
                get(Electric_Piston_UV, 48),
                get(wrapCircuit, UV, 6),
                get(wrapCircuit, ZPM, 12),
                get(wrapCircuit, LuV, 24));
            d.itemIn()
                .condenseAll();
        });

        for (int i = 1; i < RUBBERS.length; i++) {
            int finalI = i;
            RecipeModifiers.defineBase(
                "coal/uv_pump_" + RUBBER_NAMES[i],
                () -> RecipeDraft.of(componentAssemblyLineRecipes)
                    .itemInputs(
                        get(Electric_Motor_UV, 48),
                        get(plateDense, Neutronium, 10),
                        get(cableGt16, NaquadahAlloy, 24),
                        GTUtility.getIntegratedCircuit(PUMP_CIRCUIT))
                    .fluidInputs(
                        INDALLOY_140.getFluidStack(6 * STACKS + 48 * INGOTS),
                        Lubricant.getFluid(96_000),
                        Naquadah.getMolten(9 * STACKS),
                        Neutronium.getMolten(7 * STACKS + 2 * INGOTS + 6 * NUGGETS),
                        Naquadria.getMolten(6 * STACKS + 48 * INGOTS),
                        RUBBERS[finalI].getMolten(3 * STACKS))
                    .itemOutputs(Electric_Pump_UV.get(64))
                    .metadata(COAL_CASING_TIER, COAL_UV)
                    .duration(24 * MINUTES)
                    .eut(RECIPE_ZPM));

            RecipeModifiers
                .modify("coal/uv_pump_" + RUBBER_NAMES[i], VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(Electric_Motor_UV, 48));
                    d.itemIn()
                        .condenseAll();
                });

            RecipeModifiers.defineBase(
                "coal/uv_conveyor_" + RUBBER_NAMES[i],
                () -> RecipeDraft.of(componentAssemblyLineRecipes)
                    .itemInputs(
                        get(Electric_Motor_UV, 96),
                        get(plateDense, Neutronium, 10),
                        get(cableGt16, NaquadahAlloy, 24),
                        GTUtility.getIntegratedCircuit(CONVEYOR_CIRCUIT))
                    .fluidInputs(
                        INDALLOY_140.getFluidStack(6 * STACKS + 48 * INGOTS),
                        Lubricant.getFluid(96_000),
                        RUBBERS[finalI].getMolten(29 * STACKS + 61 * INGOTS),
                        Naquadria.getMolten(6 * STACKS + 48 * INGOTS),
                        Neutronium.getMolten(3 * STACKS + 26 * INGOTS + 6 * NUGGETS))
                    .itemOutputs(Conveyor_Module_UV.get(64))
                    .metadata(COAL_CASING_TIER, COAL_UV)
                    .duration(24 * MINUTES)
                    .eut(RECIPE_ZPM));

            RecipeModifiers
                .modify("coal/uv_conveyor_" + RUBBER_NAMES[i], VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                    d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(Electric_Motor_UV, 96));
                    d.itemIn()
                        .condenseAll();
                });
        }

        RecipeModifiers.defineBase(
            "coal/uv_sensor",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(
                    get(frameGt, Neutronium, 48),
                    get(Electric_Motor_UV, 48),
                    get(plateDense, Neutronium, 42),
                    get(Gravistar, 192),
                    get(wrapCircuit, UV, 12),
                    GTUtility.getIntegratedCircuit(SENSOR_CIRCUIT))
                .fluidInputs(
                    INDALLOY_140.getFluidStack(12 * STACKS),
                    Naquadria.getMolten(42 * STACKS + 48 * INGOTS),
                    NaquadahAlloy.getMolten(10 * STACKS + 32 * INGOTS))
                .itemOutputs(Sensor_UV.get(64))
                .metadata(COAL_CASING_TIER, COAL_UV)
                .duration(24 * MINUTES)
                .eut(RECIPE_ZPM));

        RecipeModifiers.modify("coal/uv_sensor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                get(wrapCircuit, UV, 3),
                get(Electric_Motor_UV, 48));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "coal/uv_emitter",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(
                    get(frameGt, Neutronium, 48),
                    get(Electric_Motor_UV, 48),
                    get(Gravistar, 192),
                    get(wrapCircuit, UV, 12),
                    GTUtility.getIntegratedCircuit(EMITTER_CIRCUIT))
                .fluidInputs(
                    INDALLOY_140.getFluidStack(12 * STACKS),
                    Naquadria.getMolten(42 * STACKS + 48 * INGOTS),
                    NaquadahAlloy.getMolten(10 * STACKS + 32 * INGOTS),
                    Neutronium.getMolten(3 * STACKS))
                .itemOutputs(Emitter_UV.get(64))
                .metadata(COAL_CASING_TIER, COAL_UV)
                .duration(24 * MINUTES)
                .eut(RECIPE_ZPM));

        RecipeModifiers.modify("coal/uv_emitter", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                get(wrapCircuit, UV, 6),
                get(Electric_Motor_UV, 48));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "coal/uv_field_gen",
            () -> RecipeDraft.of(componentAssemblyLineRecipes)
                .itemInputs(
                    get(frameGt, Neutronium, 48),
                    get(plateDense, Neutronium, 32),
                    get(Gravistar, 96),
                    get(Emitter_UV, 192),
                    get(wrapCircuit, UHV, 12),
                    GTUtility.getIntegratedCircuit(FIELD_GENERATOR_CIRCUIT))
                .fluidInputs(
                    INDALLOY_140.getFluidStack(12 * STACKS),
                    Americium.getMolten(36 * STACKS),
                    NaquadahAlloy.getMolten(12 * STACKS),
                    Naquadria.getMolten(6 * STACKS + 48 * INGOTS))
                .itemOutputs(Field_Generator_UV.get(64))
                .metadata(COAL_CASING_TIER, COAL_UV)
                .duration(24 * MINUTES)
                .eut(RECIPE_ZPM));

        RecipeModifiers.modify("coal/uv_field_gen", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, get(wrapCircuit, UHV, 12), get(Emitter_UV, 192));
            d.itemIn()
                .condenseAll();
        });
    }

    private static ItemStack get(OrePrefixes prefix, Object material, int amount) {
        if (material instanceof Werkstoff w) {
            return GTUtility.copyAmountUnsafe(amount, w.get(prefix, 1));
        }
        return GTUtility.copyAmountUnsafe(amount, GTOreDictUnificator.get(prefix, material, 1));
    }

    private static ItemStack get(ItemList item, int amount) {
        return GTUtility.copyAmountUnsafe(amount, item.get(1));
    }
}
