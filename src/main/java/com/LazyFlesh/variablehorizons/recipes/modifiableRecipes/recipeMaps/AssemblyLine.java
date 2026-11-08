package com.LazyFlesh.variablehorizons.recipes.modifiableRecipes.recipeMaps;

import static gregtech.api.util.GTRecipeBuilder.INGOTS;
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
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTOreDictUnificator;
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
    }
}
