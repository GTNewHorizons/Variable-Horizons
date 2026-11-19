package com.LazyFlesh.variablehorizons.recipes.modifiableRecipes.recipeMaps;

import static gregtech.api.recipe.RecipeMaps.blastFurnaceRecipes;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeBuilder.TICKS;
import static gregtech.api.util.GTRecipeConstants.COIL_HEAT;

import net.minecraft.item.ItemStack;

import com.LazyFlesh.variablehorizons.util.recipeBuilder.AsslineDraft;
import com.LazyFlesh.variablehorizons.util.recipeBuilder.CraftingDraft;
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

public class Examples implements Runnable {

    @Override
    public void run() {

        // Regular Crafting
        {
            RecipeModifiers.defineBase(
                "test/lv_motor",
                () -> CraftingDraft.shaped(ItemList.Electric_Motor_LV.get(1))
                    .pattern("CWR", "WMW", "R C")
                    .where('C', GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1))
                    .where('W', GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 1))
                    .where('R', GTOreDictUnificator.get(OrePrefixes.stick, Materials.Iron, 1))
                    .where('M', GTOreDictUnificator.get(OrePrefixes.stick, Materials.IronMagnetic, 1L)));

            RecipeModifiers.modifyCrafting(
                "test/lv_motor",
                VariantNames.NORMAL,
                RecipePhase.SUBSTITUTE,
                d -> d
                    .replaceAll(
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.Iron, 1),
                        GTOreDictUnificator.get(OrePrefixes.stick, Materials.AnyCopper, 1L))
                    .fill(2, 1, ItemList.SpaceElevatorMotorT5.get(1)));

            RecipeModifiers
                .modifyCrafting("test/lv_motor", VariantNames.NORMAL, RecipePhase.SCALE, d -> d.scaleOutput(3));

            RecipeModifiers.defineBase(
                "test/lv_motor2",
                () -> CraftingDraft.shapeless(ItemList.Electric_Motor_LV.get(1))
                    .ingredients(ItemList.AcceleratorMV.get(1), ItemList.Armor_Chip_T1.get(1)));

            RecipeModifiers.modifyCrafting(
                "test/lv_motor2",
                VariantNames.NORMAL,
                RecipePhase.SCALE,
                d -> d.setCount(ItemList.AcceleratorMV.get(1), 5));
        }

        // Regular machine recipe
        {
            RecipeModifiers.defineBase(
                "test/ebf",
                () -> RecipeDraft.of(blastFurnaceRecipes)
                    .itemInputs(
                        ItemList.QuantumEye.get(1L),
                        GTOreDictUnificator.get(OrePrefixes.circuit, Materials.UV, 4L),
                        GTOreDictUnificator.get(OrePrefixes.plate, Materials.Eternity, 50L),
                        GTUtility.getIntegratedCircuit(1))
                    .fluidInputs(Materials.NiobiumTitanium.getMolten(576L))
                    .itemOutputs(ItemList.Transdimensional_Alignment_Matrix.get(1L))
                    .fluidOutputs(Materials.Desh.getMolten(34))
                    .metadata(COIL_HEAT, 3200)
                    .duration(30 * SECONDS)
                    .eut(TierEU.RECIPE_HV));

            RecipeModifiers.modify("test/ebf", VariantNames.NORMAL, RecipePhase.SCALE, d -> {
                d.itemIn()
                    .add(ItemList.MacerationStack.get(1))
                    .addAfter(ItemList.QuantumEye.get(1L), ItemList.AcceleratorEV.get(1));
            });

            RecipeModifiers.modify("test/ebf", VariantNames.NORMAL, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(5, GTOreDictUnificator.get(OrePrefixes.circuit, Materials.UV, 1L));
                d.outputChances(5000);
                d.itemIn()
                    .scaleAllChances(0.2)
                    .condenseAll();
            });
        }

        // Assline with research
        {
            RecipeModifiers.defineBase(
                "test/assline",
                () -> AsslineDraft.of()
                    .itemInputs(ItemList.Circuit_Wafer_APIC.get(1))
                    .fluidInputs(Materials.RawStarMatter.getFluid(50000))
                    .itemOutput(ItemList.Electric_Motor_MV.get(1L))
                    .research(ItemList.Circuit_Board_Cosmic.get(1), 50000, 456, (int) TierEU.RECIPE_UMV, 45)
                    .duration(20 * TICKS)
                    .eut(TierEU.RECIPE_LV));

            RecipeModifiers.modifyAssemblyLine("test/assline", VariantNames.NORMAL, RecipePhase.SUBSTITUTE, d -> {
                d.itemIn()
                    .addFirst(ItemList.ATLASCasing.get(23))
                    .addFirst(
                        new ItemStack[] {
                            GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorUEV, 64L),
                            GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorUIV, 32L),
                            GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorUMV, 16L) });
                d.researchItem(ItemList.GigaChad.get(1));
                d.mapResearchEut(a -> (int) TierEU.EV);
            });

            RecipeModifiers.modifyAssemblyLine("test/assline", VariantNames.NORMAL, RecipePhase.SCALE, d -> {
                d.itemOut()
                    .scaleAll(5)
                    .condenseAll();
                d.scaleOutput(3);
            });
        }
    }
}
