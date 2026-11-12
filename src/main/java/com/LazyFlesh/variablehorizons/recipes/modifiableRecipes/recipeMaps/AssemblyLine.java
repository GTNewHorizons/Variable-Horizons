package com.LazyFlesh.variablehorizons.recipes.modifiableRecipes.recipeMaps;

import static gregtech.api.util.GTRecipeBuilder.INGOTS;
import static gregtech.api.util.GTRecipeBuilder.MINUTES;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeConstants.AssemblyLine;
import static gregtech.api.util.GTRecipeConstants.RESEARCH_ITEM;
import static gregtech.api.util.GTRecipeConstants.SCANNING;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.util.recipeBuilder.AsslineDraft;
import com.LazyFlesh.variablehorizons.util.recipeBuilder.RecipeDraft;
import com.LazyFlesh.variablehorizons.util.recipeBuilder.RecipeModifiers;
import com.LazyFlesh.variablehorizons.util.recipeBuilder.RecipePhase;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import bartworks.system.material.WerkstoffLoader;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.recipe.Scanning;
import gtPlusPlus.core.material.MaterialMisc;
import gtPlusPlus.core.material.MaterialsAlloy;

public class AssemblyLine implements Runnable {

    @Override
    public void run() {
        circuitStuff();
        luvParts();
        zpmParts();
        uvParts();
    }

    private static void circuitStuff() {
        RecipeModifiers.defineBase(
            "assemblyLine/wetware_mainframe",
            () -> AsslineDraft.of()
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Tritanium, 2),
                    ItemList.Circuit_Wetwaresupercomputer.get(2L),
                    new ItemStack[] { ItemList.Circuit_Parts_InductorASMD.get(16L),
                        ItemList.Circuit_Parts_InductorXSMD.get(4L) },
                    new ItemStack[] { ItemList.Circuit_Parts_CapacitorASMD.get(16L),
                        ItemList.Circuit_Parts_CapacitorXSMD.get(4L) },
                    new ItemStack[] { ItemList.Circuit_Parts_ResistorASMD.get(16L),
                        ItemList.Circuit_Parts_ResistorXSMD.get(4L) },
                    new ItemStack[] { ItemList.Circuit_Parts_TransistorASMD.get(16L),
                        ItemList.Circuit_Parts_TransistorXSMD.get(4L) },
                    new ItemStack[] { ItemList.Circuit_Parts_DiodeASMD.get(16L),
                        ItemList.Circuit_Parts_DiodeXSMD.get(4L) },
                    ItemList.Circuit_Chip_Ram.get(48L),
                    new ItemStack[] { GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorZPM, 64L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorUV, 32L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorUHV, 16L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorUEV, 8L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorUIV, 4L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorUMV, 2L) },
                    new Object[] { OrePrefixes.foil.get(Materials.AnySyntheticRubber), 64L })
                .fluidInputs(
                    MaterialsAlloy.INDALLOY_140.getFluidStack(20 * INGOTS),
                    GTModHandler.getIC2Coolant(10_000),
                    Materials.Radon.getGas(2_500))
                .itemOutput(ItemList.Circuit_Wetwaremainframe.get(1L))
                .research(ItemList.Circuit_Wetwaresupercomputer.get(1L), 24000, 64, 50000, 4)
                .duration(100 * SECONDS)
                .eut(300_000));

        RecipeModifiers.modifyAssemblyLine(
            "assemblyLine/wetware_mainframe",
            VariantNames.ALTERED_PRICING,
            RecipePhase.SCALE,
            d -> {
                d.scaleAllInputsBalanced(
                    GeneralConfig.partCostMultiplier,
                    ItemList.Circuit_Wetwaresupercomputer.get(2L));
                d.itemIn()
                    .condenseAll();
            });

        RecipeModifiers.defineBase(
            "assemblyLine/bio_computer",
            () -> AsslineDraft.of()
                .itemInputs(
                    ItemList.Circuit_Board_Bio_Ultra.get(2L),
                    ItemList.Circuit_Biowarecomputer.get(2L),
                    new ItemStack[] { ItemList.Circuit_Parts_TransistorASMD.get(16L),
                        ItemList.Circuit_Parts_TransistorXSMD.get(4L) },
                    new ItemStack[] { ItemList.Circuit_Parts_ResistorASMD.get(16L),
                        ItemList.Circuit_Parts_ResistorXSMD.get(4L) },
                    new ItemStack[] { ItemList.Circuit_Parts_CapacitorASMD.get(16L),
                        ItemList.Circuit_Parts_CapacitorXSMD.get(4L) },
                    new ItemStack[] { ItemList.Circuit_Parts_DiodeASMD.get(16L),
                        ItemList.Circuit_Parts_DiodeXSMD.get(4L) },
                    ItemList.Circuit_Chip_NOR.get(32L),
                    ItemList.Circuit_Chip_Ram.get(64L),
                    GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.NiobiumTitanium, 32L),
                    new Object[] { OrePrefixes.foil.get(Materials.AnySyntheticRubber), 64L })
                .fluidInputs(
                    MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(10 * INGOTS),
                    Materials.BioMediumSterilized.getFluid(10 * INGOTS),
                    Materials.SuperCoolant.getFluid(10_000))
                .itemOutput(ItemList.Circuit_Biowaresupercomputer.get(1L))
                .research(ItemList.Circuit_Biowarecomputer.get(1L), 48000, 128, (int) TierEU.RECIPE_UV, 8)
                .duration(200 * SECONDS)
                .eut(TierEU.RECIPE_UV));

        RecipeModifiers
            .modifyAssemblyLine("assemblyLine/bio_computer", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Circuit_Biowarecomputer.get(2L));
                d.itemIn()
                    .condenseAll();
            });

        RecipeModifiers.defineBase(
            "assemblyLine/bio_mainframe",
            () -> AsslineDraft.of()
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Tritanium, 4L),
                    ItemList.Circuit_Biowaresupercomputer.get(2L),
                    new ItemStack[] { ItemList.Circuit_Parts_InductorASMD.get(24L),
                        ItemList.Circuit_Parts_InductorXSMD.get(6L) },
                    new ItemStack[] { ItemList.Circuit_Parts_TransistorASMD.get(24L),
                        ItemList.Circuit_Parts_TransistorXSMD.get(6L) },
                    new ItemStack[] { ItemList.Circuit_Parts_ResistorASMD.get(24L),
                        ItemList.Circuit_Parts_ResistorXSMD.get(6L) },
                    new ItemStack[] { ItemList.Circuit_Parts_CapacitorASMD.get(24L),
                        ItemList.Circuit_Parts_CapacitorXSMD.get(6L) },
                    new ItemStack[] { ItemList.Circuit_Parts_DiodeASMD.get(24L),
                        ItemList.Circuit_Parts_DiodeXSMD.get(6L) },
                    ItemList.Circuit_Chip_Ram.get(64L),
                    new ItemStack[] { GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorUHV, 64L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorUEV, 32L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorUIV, 16L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorUMV, 8L) },
                    new Object[] { OrePrefixes.foil.get(Materials.AnySyntheticRubber), 64L },
                    GTOreDictUnificator.get(OrePrefixes.foil, Materials.Polybenzimidazole, 64))
                .fluidInputs(
                    MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(20 * INGOTS),
                    Materials.BioMediumSterilized.getFluid(20 * INGOTS),
                    Materials.SuperCoolant.getFluid(20_000L))
                .itemOutput(ItemList.Circuit_Biomainframe.get(1L))
                .research(ItemList.Circuit_Biowaresupercomputer.get(1L), 96000, 256, 1_000_000, 16)
                .duration(5 * MINUTES)
                .eut(TierEU.RECIPE_UHV));

        RecipeModifiers
            .modifyAssemblyLine("assemblyLine/bio_mainframe", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(
                    GeneralConfig.partCostMultiplier,
                    ItemList.Circuit_Biowaresupercomputer.get(2L));
                d.itemIn()
                    .condenseAll();
            });

        RecipeModifiers.defineBase(
            "assemblyLine/optical_assembly",
            () -> AsslineDraft.of()
                .itemInputs(
                    ItemList.Circuit_Board_Optical.get(1L),
                    ItemList.Circuit_OpticalProcessor.get(4L),
                    ItemList.Circuit_Parts_InductorXSMD.get(16L),
                    ItemList.Circuit_Parts_CapacitorXSMD.get(20L),
                    ItemList.Circuit_Parts_ResistorXSMD.get(20L),
                    ItemList.Circuit_Chip_NOR.get(32L),
                    ItemList.Circuit_Chip_Ram.get(64L),
                    new ItemStack(WerkstoffLoader.items.get(OrePrefixes.wireFine), 24, 10101), // Fine
                    // Lumiium
                    // Wire
                    new Object[] { OrePrefixes.foil.get(Materials.AnySyntheticRubber), 64L })
                .fluidInputs(
                    MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(10 * INGOTS),
                    Materials.Radon.getPlasma(10 * INGOTS),
                    Materials.SuperCoolant.getFluid(10_000L),
                    new FluidStack(FluidRegistry.getFluid("oganesson"), 500))
                .itemOutput(ItemList.Circuit_OpticalAssembly.get(1L))
                .research(ItemList.Circuit_OpticalProcessor.get(1L), 192_000, 256, (int) TierEU.RECIPE_UHV, 16)
                .duration(20 * SECONDS)
                .eut(TierEU.RECIPE_UHV));

        RecipeModifiers
            .modifyAssemblyLine("assemblyLine/optical_assembly", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Circuit_OpticalProcessor.get(2L));
                d.itemIn()
                    .condenseAll();
            });

        RecipeModifiers.defineBase(
            "assemblyLine/optical_computer",
            () -> AsslineDraft.of()
                .itemInputs(
                    ItemList.Circuit_Board_Optical.get(2L),
                    ItemList.Circuit_OpticalAssembly.get(4L),
                    ItemList.Circuit_Parts_TransistorXSMD.get(24L),
                    ItemList.Circuit_Parts_ResistorXSMD.get(24L),
                    ItemList.Circuit_Parts_CapacitorXSMD.get(24L),
                    ItemList.Circuit_Parts_DiodeXSMD.get(24L),
                    ItemList.Circuit_Chip_NOR.get(64L),
                    ItemList.Circuit_Chip_SoC2.get(32L),
                    new ItemStack(WerkstoffLoader.items.get(OrePrefixes.wireFine), 32, 10101), // Fine
                    // Lumiium
                    // Wire
                    new Object[] { OrePrefixes.foil.get(Materials.AnySyntheticRubber), 64L },
                    GTOreDictUnificator.get(OrePrefixes.foil, Materials.Polybenzimidazole, 64))
                .fluidInputs(
                    MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(20 * INGOTS),
                    Materials.Radon.getPlasma(20 * INGOTS),
                    Materials.SuperCoolant.getFluid(20_000),
                    new FluidStack(FluidRegistry.getFluid("oganesson"), 1_000))
                .itemOutput(ItemList.Circuit_OpticalComputer.get(1L))
                .research(ItemList.Circuit_OpticalAssembly.get(1L), 384_000, 512, 4_000_000, 32)
                .duration(200 * SECONDS)
                .eut(TierEU.RECIPE_UHV));

        RecipeModifiers
            .modifyAssemblyLine("assemblyLine/optical_computer", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Circuit_OpticalAssembly.get(2L));
                d.itemIn()
                    .condenseAll();
            });

        RecipeModifiers.defineBase(
            "assemblyLine/optical_mainframe",
            () -> AsslineDraft.of()
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Tritanium, 8),
                    ItemList.Circuit_OpticalComputer.get(4L),
                    ItemList.Circuit_Parts_InductorXSMD.get(32L),
                    ItemList.Circuit_Parts_TransistorXSMD.get(32L),
                    ItemList.Circuit_Parts_ResistorXSMD.get(32L),
                    ItemList.Circuit_Parts_CapacitorXSMD.get(32L),
                    ItemList.Circuit_Parts_DiodeXSMD.get(32L),
                    ItemList.Circuit_Chip_SoC2.get(64L),
                    new ItemStack[] { GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorUEV, 64L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorUIV, 32L),
                        GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorUMV, 16L) },
                    new Object[] { OrePrefixes.foil.get(Materials.AnySyntheticRubber), 64L },
                    new Object[] { OrePrefixes.foil.get(Materials.AnySyntheticRubber), 64L },
                    GTOreDictUnificator.get(OrePrefixes.foil, Materials.Polybenzimidazole, 64),
                    GTOreDictUnificator.get(OrePrefixes.foil, Materials.Polybenzimidazole, 64))
                .fluidInputs(
                    MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(40 * INGOTS),
                    Materials.Radon.getPlasma(40 * INGOTS),
                    Materials.SuperCoolant.getFluid(40_000),
                    new FluidStack(FluidRegistry.getFluid("oganesson"), 2_000))
                .itemOutput(ItemList.Circuit_OpticalMainframe.get(1L))
                .research(ItemList.Circuit_OpticalComputer.get(1L), 768_000, 1024, (int) TierEU.RECIPE_UEV, 64)
                .duration(5 * MINUTES)
                .eut(TierEU.RECIPE_UEV));

        RecipeModifiers.modifyAssemblyLine(
            "assemblyLine/optical_mainframe",
            VariantNames.ALTERED_PRICING,
            RecipePhase.SCALE,
            d -> {
                d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Circuit_OpticalComputer.get(2L));
                d.itemIn()
                    .condenseAll();
            });
    }

    private static void luvParts() {
        RecipeModifiers.defineBase(
            "assemblyLine/luv_motor",
            () -> RecipeDraft.of(AssemblyLine)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.stick, Materials.SamariumMagnetic, 1),
                    GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSS, 2),
                    GTOreDictUnificator.get(OrePrefixes.wireFine, WerkstoffLoader.Ruridit.getGTMaterial(), 64),
                    GTOreDictUnificator.get(OrePrefixes.wireFine, WerkstoffLoader.Ruridit.getGTMaterial(), 64),
                    GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.YttriumBariumCuprate, 2))
                .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(1 * INGOTS), Materials.Lubricant.getFluid(250))
                .itemOutputs(ItemList.Electric_Motor_LuV.get(1))
                .metadata(RESEARCH_ITEM, ItemList.Electric_Motor_IV.get(1))
                .metadata(SCANNING, new Scanning(1 * MINUTES, TierEU.RECIPE_EV))
                .eut(TierEU.RECIPE_IV)
                .duration(30 * SECONDS));

        RecipeModifiers.modify("assemblyLine/luv_motor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assemblyLine/luv_pump",
            () -> RecipeDraft.of(AssemblyLine)
                .itemInputs(
                    ItemList.Electric_Motor_LuV.get(1),
                    GTOreDictUnificator.get(OrePrefixes.pipeSmall, Materials.NiobiumTitanium, 2),
                    GTOreDictUnificator.get(OrePrefixes.plate, Materials.HSSS, 2),
                    GTOreDictUnificator.get(OrePrefixes.screw, Materials.HSSS, 8),
                    new Object[] { OrePrefixes.ring.get(Materials.AnySyntheticRubber), 4 },
                    GTOreDictUnificator.get(OrePrefixes.rotor, Materials.HSSS, 2),
                    GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.YttriumBariumCuprate, 2))
                .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(1 * INGOTS), Materials.Lubricant.getFluid(250))
                .itemOutputs(ItemList.Electric_Pump_LuV.get(1))
                .metadata(RESEARCH_ITEM, ItemList.Electric_Pump_IV.get(1))
                .metadata(SCANNING, new Scanning(1 * MINUTES, TierEU.RECIPE_EV))
                .eut(TierEU.RECIPE_IV)
                .duration(30 * SECONDS));

        RecipeModifiers.modify("assemblyLine/luv_pump", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Electric_Motor_LuV.get(1));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assemblyLine/luv_conveyor",
            () -> RecipeDraft.of(AssemblyLine)
                .itemInputs(
                    ItemList.Electric_Motor_LuV.get(2),
                    GTOreDictUnificator.get(OrePrefixes.plate, Materials.HSSS, 2),
                    GTOreDictUnificator.get(OrePrefixes.ring, Materials.HSSS, 4),
                    GTOreDictUnificator.get(OrePrefixes.round, Materials.HSSS, 32),
                    GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.YttriumBariumCuprate, 2),
                    new Object[] { OrePrefixes.plate.get(Materials.AnySyntheticRubber), 10 })
                .itemOutputs(ItemList.Conveyor_Module_LuV.get(1))
                .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(1 * INGOTS), Materials.Lubricant.getFluid(250))
                .metadata(RESEARCH_ITEM, ItemList.Conveyor_Module_IV.get(1))
                .metadata(SCANNING, new Scanning(1 * MINUTES, TierEU.RECIPE_EV))
                .duration(30 * SECONDS)
                .eut(TierEU.RECIPE_IV));

        RecipeModifiers.modify("assemblyLine/luv_conveyor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Electric_Motor_LuV.get(1));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assemblyLine/luv_piston",
            () -> RecipeDraft.of(AssemblyLine)
                .itemInputs(
                    ItemList.Electric_Motor_LuV.get(1),
                    GTOreDictUnificator.get(OrePrefixes.plate, Materials.HSSS, 6),
                    GTOreDictUnificator.get(OrePrefixes.ring, Materials.HSSS, 4),
                    GTOreDictUnificator.get(OrePrefixes.round, Materials.HSSS, 32),
                    GTOreDictUnificator.get(OrePrefixes.stick, Materials.HSSS, 4),
                    GTOreDictUnificator.get(OrePrefixes.gear, Materials.HSSS, 1),
                    GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSS, 2),
                    GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.YttriumBariumCuprate, 4))
                .itemOutputs(ItemList.Electric_Piston_LuV.get(1))
                .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(1 * INGOTS), Materials.Lubricant.getFluid(250))
                .metadata(RESEARCH_ITEM, ItemList.Electric_Piston_IV.get(1))
                .metadata(SCANNING, new Scanning(1 * MINUTES, TierEU.RECIPE_EV))
                .duration(30 * SECONDS)
                .eut(TierEU.RECIPE_IV));

        RecipeModifiers.modify("assemblyLine/luv_piston", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Electric_Motor_LuV.get(1));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assemblyLine/luv_robot_arm",
            () -> RecipeDraft.of(AssemblyLine)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.HSSS, 4),
                    GTOreDictUnificator.get(OrePrefixes.gear, Materials.HSSS, 1),
                    GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.HSSS, 3),
                    ItemList.Electric_Motor_LuV.get(2),
                    ItemList.Electric_Piston_LuV.get(1),
                    new Object[] { OrePrefixes.circuit.get(Materials.LuV), 2 },
                    new Object[] { OrePrefixes.circuit.get(Materials.IV), 4 },
                    new Object[] { OrePrefixes.circuit.get(Materials.EV), 8 },
                    GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.YttriumBariumCuprate, 6))
                .itemOutputs(ItemList.Robot_Arm_LuV.get(1))
                .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(4 * INGOTS), Materials.Lubricant.getFluid(250))
                .metadata(RESEARCH_ITEM, ItemList.Robot_Arm_IV.get(1))
                .metadata(SCANNING, new Scanning(1 * MINUTES, TierEU.RECIPE_EV))
                .duration(30 * SECONDS)
                .eut(TierEU.RECIPE_IV));

        RecipeModifiers.modify("assemblyLine/luv_robot_arm", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                ItemList.Electric_Motor_LuV.get(1),
                ItemList.Electric_Piston_LuV.get(1),
                new Object[] { OrePrefixes.circuit.get(Materials.LuV), 2 },
                new Object[] { OrePrefixes.circuit.get(Materials.IV), 4 },
                new Object[] { OrePrefixes.circuit.get(Materials.EV), 8 });
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assemblyLine/luv_emitter",
            () -> RecipeDraft.of(AssemblyLine)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.HSSS, 1),
                    ItemList.Electric_Motor_LuV.get(1),
                    GTOreDictUnificator.get(OrePrefixes.stick, WerkstoffLoader.Ruridit.getGTMaterial(), 8),
                    ItemList.QuantumStar.get(1),
                    new Object[] { OrePrefixes.circuit.get(Materials.LuV), 4 },
                    GTOreDictUnificator.get(OrePrefixes.foil, Materials.Gallium, 64),
                    GTOreDictUnificator.get(OrePrefixes.foil, Materials.Gallium, 64),
                    GTOreDictUnificator.get(OrePrefixes.foil, Materials.Gallium, 64),
                    GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.YttriumBariumCuprate, 7))
                .itemOutputs(ItemList.Emitter_LuV.get(1))
                .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(4 * INGOTS))
                .metadata(RESEARCH_ITEM, ItemList.Emitter_IV.get(1))
                .metadata(SCANNING, new Scanning(1 * MINUTES, TierEU.RECIPE_EV))
                .duration(30 * SECONDS)
                .eut(TierEU.RECIPE_IV));

        RecipeModifiers.modify("assemblyLine/luv_emitter", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                ItemList.Electric_Motor_LuV.get(1),
                new Object[] { OrePrefixes.circuit.get(Materials.LuV), 4 });
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assemblyLine/luv_sensor",
            () -> RecipeDraft.of(AssemblyLine)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.HSSS, 1),
                    ItemList.Electric_Motor_LuV.get(1),
                    GTOreDictUnificator.get(OrePrefixes.plate, WerkstoffLoader.Ruridit.getGTMaterial(), 8),
                    ItemList.QuantumStar.get(1),
                    new Object[] { OrePrefixes.circuit.get(Materials.LuV), 4 },
                    GTOreDictUnificator.get(OrePrefixes.foil, Materials.Gallium, 64),
                    GTOreDictUnificator.get(OrePrefixes.foil, Materials.Gallium, 64),
                    GTOreDictUnificator.get(OrePrefixes.foil, Materials.Gallium, 64),
                    GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.YttriumBariumCuprate, 7))
                .itemOutputs(ItemList.Sensor_LuV.get(1))
                .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(4 * INGOTS))
                .metadata(RESEARCH_ITEM, ItemList.Sensor_IV.get(1))
                .metadata(SCANNING, new Scanning(1 * MINUTES, TierEU.RECIPE_EV))
                .duration(30 * SECONDS)
                .eut(TierEU.RECIPE_IV));

        RecipeModifiers.modify("assemblyLine/luv_sensor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                ItemList.Electric_Motor_LuV.get(1),
                new Object[] { OrePrefixes.circuit.get(Materials.LuV), 4 });
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assemblyLine/luv_field_gen",
            () -> RecipeDraft.of(AssemblyLine)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.HSSS, 1),
                    GTOreDictUnificator.get(OrePrefixes.plate, Materials.HSSS, 6),
                    ItemList.QuantumStar.get(2),
                    ItemList.Emitter_LuV.get(4),
                    new Object[] { OrePrefixes.circuit.get(Materials.ZPM), 4 },
                    GTOreDictUnificator.get(OrePrefixes.wireFine, WerkstoffLoader.Ruridit.getGTMaterial(), 64),
                    GTOreDictUnificator.get(OrePrefixes.wireFine, WerkstoffLoader.Ruridit.getGTMaterial(), 64),
                    GTOreDictUnificator.get(OrePrefixes.wireFine, WerkstoffLoader.Ruridit.getGTMaterial(), 64),
                    GTOreDictUnificator.get(OrePrefixes.wireFine, WerkstoffLoader.Ruridit.getGTMaterial(), 64),
                    GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.YttriumBariumCuprate, 8))
                .itemOutputs(ItemList.Field_Generator_LuV.get(1))
                .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(4 * INGOTS))
                .metadata(RESEARCH_ITEM, ItemList.Field_Generator_IV.get(1))
                .metadata(SCANNING, new Scanning(1 * MINUTES, TierEU.RECIPE_EV))
                .duration(30 * SECONDS)
                .eut(TierEU.RECIPE_IV));

        RecipeModifiers.modify("assemblyLine/luv_field_gen", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                ItemList.Emitter_LuV.get(1),
                new Object[] { OrePrefixes.circuit.get(Materials.ZPM), 4 });
            d.itemIn()
                .condenseAll();
        });
    }

    private static void zpmParts() {
        RecipeModifiers.defineBase(
            "assemblyLine/zpm_motor",
            () -> RecipeDraft.of(AssemblyLine)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.stick, Materials.SamariumMagnetic, 2),
                    GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.NaquadahAlloy, 4),
                    GTOreDictUnificator.get(OrePrefixes.ring, Materials.NaquadahAlloy, 4),
                    GTOreDictUnificator.get(OrePrefixes.round, Materials.NaquadahAlloy, 16),
                    GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Europium, 64),
                    GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Europium, 64),
                    GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Europium, 64),
                    GTOreDictUnificator.get(OrePrefixes.cableGt04, Materials.VanadiumGallium, 2))
                .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(2 * INGOTS), Materials.Lubricant.getFluid(750))
                .itemOutputs(ItemList.Electric_Motor_ZPM.get(1))
                .metadata(RESEARCH_ITEM, ItemList.Electric_Motor_LuV.get(1))
                .metadata(SCANNING, new Scanning(1 * MINUTES, TierEU.RECIPE_IV))
                .eut(TierEU.RECIPE_LuV)
                .duration(30 * SECONDS));

        RecipeModifiers.modify("assemblyLine/zpm_motor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assemblyLine/zpm_pump",
            () -> RecipeDraft.of(AssemblyLine)
                .itemInputs(
                    ItemList.Electric_Motor_ZPM.get(1),
                    GTOreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Enderium, 2),
                    GTOreDictUnificator.get(OrePrefixes.plate, Materials.NaquadahAlloy, 2),
                    GTOreDictUnificator.get(OrePrefixes.screw, Materials.NaquadahAlloy, 8),
                    new Object[] { OrePrefixes.ring.get(Materials.AnySyntheticRubber), 8 },
                    GTOreDictUnificator.get(OrePrefixes.rotor, Materials.NaquadahAlloy, 2),
                    GTOreDictUnificator.get(OrePrefixes.cableGt04, Materials.VanadiumGallium, 2))
                .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(2 * INGOTS), Materials.Lubricant.getFluid(750))
                .itemOutputs(ItemList.Electric_Pump_ZPM.get(1))
                .metadata(RESEARCH_ITEM, ItemList.Electric_Pump_LuV.get(1))
                .metadata(SCANNING, new Scanning(1 * MINUTES, TierEU.RECIPE_IV))
                .eut(TierEU.RECIPE_LuV)
                .duration(30 * SECONDS));

        RecipeModifiers.modify("assemblyLine/zpm_pump", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Electric_Motor_ZPM.get(1));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assemblyLine/zpm_conveyor",
            () -> RecipeDraft.of(AssemblyLine)
                .itemInputs(
                    ItemList.Electric_Motor_ZPM.get(2),
                    GTOreDictUnificator.get(OrePrefixes.plate, Materials.NaquadahAlloy, 2),
                    GTOreDictUnificator.get(OrePrefixes.ring, Materials.NaquadahAlloy, 4),
                    GTOreDictUnificator.get(OrePrefixes.round, Materials.NaquadahAlloy, 32),
                    GTOreDictUnificator.get(OrePrefixes.cableGt04, Materials.VanadiumGallium, 2),
                    new Object[] { OrePrefixes.plate.get(Materials.AnySyntheticRubber), 20 })
                .itemOutputs(ItemList.Conveyor_Module_ZPM.get(1))
                .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(2 * INGOTS), Materials.Lubricant.getFluid(750))
                .metadata(RESEARCH_ITEM, ItemList.Conveyor_Module_LuV.get(1))
                .metadata(SCANNING, new Scanning(1 * MINUTES, TierEU.RECIPE_IV))
                .duration(30 * SECONDS)
                .eut(TierEU.RECIPE_LuV));

        RecipeModifiers.modify("assemblyLine/zpm_conveyor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Electric_Motor_ZPM.get(1));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assemblyLine/zpm_piston",
            () -> RecipeDraft.of(AssemblyLine)
                .itemInputs(
                    ItemList.Electric_Motor_ZPM.get(1),
                    GTOreDictUnificator.get(OrePrefixes.plate, Materials.NaquadahAlloy, 6),
                    GTOreDictUnificator.get(OrePrefixes.ring, Materials.NaquadahAlloy, 4),
                    GTOreDictUnificator.get(OrePrefixes.round, Materials.NaquadahAlloy, 32),
                    GTOreDictUnificator.get(OrePrefixes.stick, Materials.NaquadahAlloy, 4),
                    GTOreDictUnificator.get(OrePrefixes.gear, Materials.NaquadahAlloy, 1),
                    GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.NaquadahAlloy, 2),
                    GTOreDictUnificator.get(OrePrefixes.cableGt04, Materials.VanadiumGallium, 4))
                .itemOutputs(ItemList.Electric_Piston_ZPM.get(1))
                .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(2 * INGOTS), Materials.Lubricant.getFluid(750))
                .metadata(RESEARCH_ITEM, ItemList.Electric_Piston_LuV.get(1))
                .metadata(SCANNING, new Scanning(1 * MINUTES, TierEU.RECIPE_IV))
                .duration(30 * SECONDS)
                .eut(TierEU.RECIPE_LuV));

        RecipeModifiers.modify("assemblyLine/zpm_piston", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Electric_Motor_ZPM.get(1));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assemblyLine/zpm_robot_arm",
            () -> RecipeDraft.of(AssemblyLine)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.NaquadahAlloy, 4),
                    GTOreDictUnificator.get(OrePrefixes.gear, Materials.NaquadahAlloy, 1),
                    GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.NaquadahAlloy, 3),
                    ItemList.Electric_Motor_ZPM.get(2),
                    ItemList.Electric_Piston_ZPM.get(1),
                    new Object[] { OrePrefixes.circuit.get(Materials.ZPM), 2 },
                    new Object[] { OrePrefixes.circuit.get(Materials.LuV), 4 },
                    new Object[] { OrePrefixes.circuit.get(Materials.IV), 8 },
                    GTOreDictUnificator.get(OrePrefixes.cableGt04, Materials.VanadiumGallium, 6))
                .itemOutputs(ItemList.Robot_Arm_ZPM.get(1))
                .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(8 * INGOTS), Materials.Lubricant.getFluid(750))
                .metadata(RESEARCH_ITEM, ItemList.Robot_Arm_LuV.get(1))
                .metadata(SCANNING, new Scanning(1 * MINUTES, TierEU.RECIPE_IV))
                .duration(30 * SECONDS)
                .eut(TierEU.RECIPE_LuV));

        RecipeModifiers.modify("assemblyLine/zpm_robot_arm", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                ItemList.Electric_Motor_ZPM.get(2),
                ItemList.Electric_Piston_ZPM.get(1),
                new Object[] { OrePrefixes.circuit.get(Materials.ZPM), 2 },
                new Object[] { OrePrefixes.circuit.get(Materials.LuV), 4 },
                new Object[] { OrePrefixes.circuit.get(Materials.IV), 8 });
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assemblyLine/zpm_emitter",
            () -> RecipeDraft.of(AssemblyLine)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.NaquadahAlloy, 1),
                    ItemList.Electric_Motor_ZPM.get(1),
                    GTOreDictUnificator.get(OrePrefixes.stick, Materials.Osmiridium, 8),
                    ItemList.QuantumStar.get(2),
                    new Object[] { OrePrefixes.circuit.get(Materials.ZPM), 4 },
                    GTOreDictUnificator.get(OrePrefixes.foil, Materials.Trinium, 64),
                    GTOreDictUnificator.get(OrePrefixes.foil, Materials.Trinium, 64),
                    GTOreDictUnificator.get(OrePrefixes.foil, Materials.Trinium, 64),
                    GTOreDictUnificator.get(OrePrefixes.cableGt04, Materials.VanadiumGallium, 7))
                .itemOutputs(ItemList.Emitter_ZPM.get(1))
                .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(8 * INGOTS))
                .metadata(RESEARCH_ITEM, ItemList.Emitter_LuV.get(1))
                .metadata(SCANNING, new Scanning(1 * MINUTES, TierEU.RECIPE_IV))
                .duration(30 * SECONDS)
                .eut(TierEU.RECIPE_LuV));

        RecipeModifiers.modify("assemblyLine/zpm_emitter", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                ItemList.Electric_Motor_ZPM.get(1),
                new Object[] { OrePrefixes.circuit.get(Materials.ZPM), 4 });
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assemblyLine/zpm_sensor",
            () -> RecipeDraft.of(AssemblyLine)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.NaquadahAlloy, 1),
                    ItemList.Electric_Motor_ZPM.get(1),
                    GTOreDictUnificator.get(OrePrefixes.plate, Materials.Osmiridium, 8),
                    ItemList.QuantumStar.get(2),
                    new Object[] { OrePrefixes.circuit.get(Materials.ZPM), 4 },
                    GTOreDictUnificator.get(OrePrefixes.foil, Materials.Trinium, 64),
                    GTOreDictUnificator.get(OrePrefixes.foil, Materials.Trinium, 64),
                    GTOreDictUnificator.get(OrePrefixes.foil, Materials.Trinium, 64),
                    GTOreDictUnificator.get(OrePrefixes.cableGt04, Materials.VanadiumGallium, 7))
                .itemOutputs(ItemList.Sensor_ZPM.get(1))
                .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(8 * INGOTS))
                .metadata(RESEARCH_ITEM, ItemList.Sensor_LuV.get(1))
                .metadata(SCANNING, new Scanning(1 * MINUTES, TierEU.RECIPE_IV))
                .duration(30 * SECONDS)
                .eut(TierEU.RECIPE_LuV));

        RecipeModifiers.modify("assemblyLine/zpm_sensor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                ItemList.Electric_Motor_ZPM.get(1),
                new Object[] { OrePrefixes.circuit.get(Materials.ZPM), 4 });
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assemblyLine/zpm_field_gen",
            () -> RecipeDraft.of(AssemblyLine)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.NaquadahAlloy, 1),
                    GTOreDictUnificator.get(OrePrefixes.plate, Materials.NaquadahAlloy, 6),
                    ItemList.QuantumStar.get(2),
                    ItemList.Emitter_ZPM.get(4),
                    new Object[] { OrePrefixes.circuit.get(Materials.UV), 4 },
                    GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Europium, 64),
                    GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Europium, 64),
                    GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Europium, 64),
                    GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Europium, 64),
                    GTOreDictUnificator.get(OrePrefixes.cableGt04, Materials.VanadiumGallium, 8))
                .itemOutputs(ItemList.Field_Generator_ZPM.get(1))
                .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(8 * INGOTS))
                .metadata(RESEARCH_ITEM, ItemList.Field_Generator_LuV.get(1))
                .metadata(SCANNING, new Scanning(1 * MINUTES, TierEU.RECIPE_IV))
                .duration(30 * SECONDS)
                .eut(TierEU.RECIPE_LuV));

        RecipeModifiers.modify("assemblyLine/zpm_field_gen", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                ItemList.Emitter_ZPM.get(1),
                new Object[] { OrePrefixes.circuit.get(Materials.UV), 4 });
            d.itemIn()
                .condenseAll();
        });
    }

    private static void uvParts() {
        RecipeModifiers.defineBase(
            "assemblyLine/uv_motor",
            () -> RecipeDraft.of(AssemblyLine)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.SamariumMagnetic, 2),
                    GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Neutronium, 4),
                    GTOreDictUnificator.get(OrePrefixes.ring, Materials.Neutronium, 4),
                    GTOreDictUnificator.get(OrePrefixes.round, Materials.Neutronium, 16),
                    GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Americium, 64),
                    GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Americium, 64),
                    GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Americium, 64),
                    GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Americium, 64),
                    GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Americium, 64),
                    GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Americium, 64),
                    GTOreDictUnificator.get(OrePrefixes.cableGt04, Materials.NaquadahAlloy, 2))
                .fluidInputs(
                    Materials.Naquadria.getMolten(9 * INGOTS),
                    MaterialsAlloy.INDALLOY_140.getFluidStack(9 * INGOTS),
                    Materials.Lubricant.getFluid(2_000))
                .itemOutputs(ItemList.Electric_Motor_UV.get(1))
                .metadata(RESEARCH_ITEM, ItemList.Electric_Motor_ZPM.get(1))
                .metadata(SCANNING, new Scanning(1 * MINUTES, TierEU.RECIPE_LuV))
                .eut(TierEU.RECIPE_ZPM)
                .duration(30 * SECONDS));

        RecipeModifiers.modify("assemblyLine/uv_motor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier);
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assemblyLine/uv_pump",
            () -> RecipeDraft.of(AssemblyLine)
                .itemInputs(
                    ItemList.Electric_Motor_UV.get(1),
                    GTOreDictUnificator.get(OrePrefixes.pipeLarge, Materials.Naquadah, 2),
                    GTOreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 2),
                    GTOreDictUnificator.get(OrePrefixes.screw, Materials.Neutronium, 8),
                    new Object[] { OrePrefixes.ring.get(Materials.AnySyntheticRubber), 16 },
                    GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Neutronium, 2),
                    GTOreDictUnificator.get(OrePrefixes.cableGt04, Materials.NaquadahAlloy, 2))
                .itemOutputs(ItemList.Electric_Pump_UV.get(1))
                .fluidInputs(
                    Materials.Naquadria.getMolten(9 * INGOTS),
                    MaterialsAlloy.INDALLOY_140.getFluidStack(9 * INGOTS),
                    Materials.Lubricant.getFluid(2_000))
                .metadata(RESEARCH_ITEM, ItemList.Electric_Pump_ZPM.get(1))
                .metadata(SCANNING, new Scanning(1 * MINUTES, TierEU.RECIPE_LuV))
                .duration(30 * SECONDS)
                .eut(TierEU.RECIPE_ZPM));

        RecipeModifiers.modify("assemblyLine/uv_pump", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Electric_Motor_UV.get(1));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assemblyLine/uv_conveyor",
            () -> RecipeDraft.of(AssemblyLine)
                .itemInputs(
                    ItemList.Electric_Motor_UV.get(2),
                    GTOreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 2),
                    GTOreDictUnificator.get(OrePrefixes.ring, Materials.Neutronium, 4),
                    GTOreDictUnificator.get(OrePrefixes.round, Materials.Neutronium, 32),
                    GTOreDictUnificator.get(OrePrefixes.cableGt04, Materials.NaquadahAlloy, 2),
                    new Object[] { OrePrefixes.plate.get(Materials.AnySyntheticRubber), 40 })
                .itemOutputs(ItemList.Conveyor_Module_UV.get(1))
                .fluidInputs(
                    Materials.Naquadria.getMolten(9 * INGOTS),
                    MaterialsAlloy.INDALLOY_140.getFluidStack(9 * INGOTS),
                    Materials.Lubricant.getFluid(2_000))
                .metadata(RESEARCH_ITEM, ItemList.Conveyor_Module_ZPM.get(1))
                .metadata(SCANNING, new Scanning(1 * MINUTES, TierEU.RECIPE_LuV))
                .duration(30 * SECONDS)
                .eut(TierEU.RECIPE_ZPM));

        RecipeModifiers.modify("assemblyLine/uv_conveyor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Electric_Motor_UV.get(1));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assemblyLine/uv_piston",
            () -> RecipeDraft.of(AssemblyLine)
                .itemInputs(
                    ItemList.Electric_Motor_UV.get(1),
                    GTOreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 6),
                    GTOreDictUnificator.get(OrePrefixes.ring, Materials.Neutronium, 4),
                    GTOreDictUnificator.get(OrePrefixes.round, Materials.Neutronium, 32),
                    GTOreDictUnificator.get(OrePrefixes.stick, Materials.Neutronium, 4),
                    GTOreDictUnificator.get(OrePrefixes.gear, Materials.Neutronium, 1),
                    GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Neutronium, 2),
                    GTOreDictUnificator.get(OrePrefixes.cableGt04, Materials.NaquadahAlloy, 4))
                .itemOutputs(ItemList.Electric_Piston_UV.get(1))
                .fluidInputs(
                    Materials.Naquadria.getMolten(9 * INGOTS),
                    MaterialsAlloy.INDALLOY_140.getFluidStack(9 * INGOTS),
                    Materials.Lubricant.getFluid(2_000))
                .metadata(RESEARCH_ITEM, ItemList.Electric_Piston_ZPM.get(1))
                .metadata(SCANNING, new Scanning(1 * MINUTES, TierEU.RECIPE_LuV))
                .duration(30 * SECONDS)
                .eut(TierEU.RECIPE_ZPM));

        RecipeModifiers.modify("assemblyLine/uv_piston", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, ItemList.Electric_Motor_UV.get(1));
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assemblyLine/uv_robot_arm",
            () -> RecipeDraft.of(AssemblyLine)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Neutronium, 4),
                    GTOreDictUnificator.get(OrePrefixes.gear, Materials.Neutronium, 1),
                    GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Neutronium, 3),
                    ItemList.Electric_Motor_UV.get(2),
                    ItemList.Electric_Piston_UV.get(1),
                    new Object[] { OrePrefixes.circuit.get(Materials.UV), 2 },
                    new Object[] { OrePrefixes.circuit.get(Materials.ZPM), 4 },
                    new Object[] { OrePrefixes.circuit.get(Materials.LuV), 8 },
                    GTOreDictUnificator.get(OrePrefixes.cableGt04, Materials.NaquadahAlloy, 6))
                .itemOutputs(ItemList.Robot_Arm_UV.get(1))
                .fluidInputs(
                    Materials.Naquadria.getMolten(9 * INGOTS),
                    MaterialsAlloy.INDALLOY_140.getFluidStack(16 * INGOTS),
                    Materials.Lubricant.getFluid(2_000))
                .metadata(RESEARCH_ITEM, ItemList.Robot_Arm_ZPM.get(1))
                .metadata(SCANNING, new Scanning(1 * MINUTES, TierEU.RECIPE_LuV))
                .duration(30 * SECONDS)
                .eut(TierEU.RECIPE_ZPM));

        RecipeModifiers.modify("assemblyLine/uv_robot_arm", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                ItemList.Electric_Motor_UV.get(2),
                ItemList.Electric_Piston_UV.get(1),
                new Object[] { OrePrefixes.circuit.get(Materials.UV), 2 },
                new Object[] { OrePrefixes.circuit.get(Materials.ZPM), 4 },
                new Object[] { OrePrefixes.circuit.get(Materials.LuV), 8 });
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assemblyLine/uv_emitter",
            () -> RecipeDraft.of(AssemblyLine)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Neutronium, 1),
                    ItemList.Electric_Motor_UV.get(1),
                    GTOreDictUnificator.get(OrePrefixes.stick, Materials.Neutronium, 8),
                    ItemList.Gravistar.get(4),
                    new Object[] { OrePrefixes.circuit.get(Materials.UV), 4 },
                    GTOreDictUnificator.get(OrePrefixes.foil, Materials.Naquadria, 64),
                    GTOreDictUnificator.get(OrePrefixes.foil, Materials.Naquadria, 64),
                    GTOreDictUnificator.get(OrePrefixes.foil, Materials.Naquadria, 64),
                    GTOreDictUnificator.get(OrePrefixes.cableGt04, Materials.NaquadahAlloy, 7))
                .itemOutputs(ItemList.Emitter_UV.get(1))
                .fluidInputs(
                    Materials.Naquadria.getMolten(9 * INGOTS),
                    MaterialsAlloy.INDALLOY_140.getFluidStack(16 * INGOTS))
                .metadata(RESEARCH_ITEM, ItemList.Emitter_ZPM.get(1))
                .metadata(SCANNING, new Scanning(1 * MINUTES, TierEU.RECIPE_LuV))
                .duration(30 * SECONDS)
                .eut((int) TierEU.RECIPE_ZPM));

        RecipeModifiers.modify("assemblyLine/uv_emitter", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                ItemList.Electric_Motor_UV.get(1),
                new Object[] { OrePrefixes.circuit.get(Materials.UV), 4 });
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assemblyLine/uv_sensor",
            () -> RecipeDraft.of(AssemblyLine)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Neutronium, 1),
                    ItemList.Electric_Motor_UV.get(1),
                    GTOreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 8),
                    ItemList.Gravistar.get(4),
                    new Object[] { OrePrefixes.circuit.get(Materials.UV), 4 },
                    GTOreDictUnificator.get(OrePrefixes.foil, Materials.Naquadria, 64),
                    GTOreDictUnificator.get(OrePrefixes.foil, Materials.Naquadria, 64),
                    GTOreDictUnificator.get(OrePrefixes.foil, Materials.Naquadria, 64),
                    GTOreDictUnificator.get(OrePrefixes.cableGt04, Materials.NaquadahAlloy, 7))
                .itemOutputs(ItemList.Sensor_UV.get(1))
                .fluidInputs(
                    Materials.Naquadria.getMolten(9 * INGOTS),
                    MaterialsAlloy.INDALLOY_140.getFluidStack(16 * INGOTS))
                .metadata(RESEARCH_ITEM, ItemList.Sensor_ZPM.get(1))
                .metadata(SCANNING, new Scanning(1 * MINUTES, TierEU.RECIPE_LuV))
                .duration(30 * SECONDS)
                .eut((int) TierEU.RECIPE_ZPM));

        RecipeModifiers.modify("assemblyLine/uv_sensor", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                ItemList.Electric_Motor_UV.get(1),
                new Object[] { OrePrefixes.circuit.get(Materials.UV), 4 });
            d.itemIn()
                .condenseAll();
        });

        RecipeModifiers.defineBase(
            "assemblyLine/uv_field_gen",
            () -> RecipeDraft.of(AssemblyLine)
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Neutronium, 1),
                    GTOreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 6),
                    ItemList.Gravistar.get(2),
                    ItemList.Emitter_UV.get(4),
                    new Object[] { OrePrefixes.circuit.get(Materials.UHV), 4 },
                    GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Americium, 64),
                    GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Americium, 64),
                    GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Americium, 64),
                    GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Americium, 64),
                    GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Americium, 64),
                    GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Americium, 64),
                    GTOreDictUnificator.get(OrePrefixes.cableGt04, Materials.NaquadahAlloy, 8))
                .itemOutputs(ItemList.Field_Generator_UV.get(1))
                .fluidInputs(
                    Materials.Naquadria.getMolten(9 * INGOTS),
                    MaterialsAlloy.INDALLOY_140.getFluidStack(16 * INGOTS))
                .metadata(RESEARCH_ITEM, ItemList.Field_Generator_ZPM.get(1))
                .metadata(SCANNING, new Scanning(1 * MINUTES, TierEU.RECIPE_LuV))
                .duration(30 * SECONDS)
                .eut(TierEU.RECIPE_ZPM));

        RecipeModifiers.modify("assemblyLine/uv_field_gen", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                ItemList.Emitter_UV.get(1),
                new Object[] { OrePrefixes.circuit.get(Materials.UHV), 4 });
            d.itemIn()
                .condenseAll();
        });
    }
}
