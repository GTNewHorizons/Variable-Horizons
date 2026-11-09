package com.LazyFlesh.variablehorizons.recipes.modifiableRecipes.recipeMaps;

import static gregtech.api.util.GTRecipeBuilder.INGOTS;
import static gregtech.api.util.GTRecipeBuilder.MINUTES;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;

import net.minecraft.item.ItemStack;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.util.recipeBuilder.AsslineDraft;
import com.LazyFlesh.variablehorizons.util.recipeBuilder.RecipeModifiers;
import com.LazyFlesh.variablehorizons.util.recipeBuilder.RecipePhase;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTOreDictUnificator;
import gtPlusPlus.core.material.MaterialMisc;
import gtPlusPlus.core.material.MaterialsAlloy;

public class AssemblyLine implements Runnable {

    @Override
    public void run() {
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
                    new ItemStack[] { GTOreDictUnificator.get(OrePrefixes.foil, Materials.StyreneButadieneRubber, 64L),
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.RubberSilicone, 64L) })
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
                    new ItemStack[] { GTOreDictUnificator.get(OrePrefixes.foil, Materials.StyreneButadieneRubber, 64L),
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.RubberSilicone, 64L) })
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
                    new ItemStack[] { GTOreDictUnificator.get(OrePrefixes.foil, Materials.StyreneButadieneRubber, 64L),
                        GTOreDictUnificator.get(OrePrefixes.foil, Materials.RubberSilicone, 64L) },
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
    }
}
