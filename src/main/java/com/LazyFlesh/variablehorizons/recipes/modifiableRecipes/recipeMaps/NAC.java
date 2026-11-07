package com.LazyFlesh.variablehorizons.recipes.modifiableRecipes.recipeMaps;

import static gregtech.api.recipe.RecipeMaps.nanochipAssemblyMatrixRecipes;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;

import java.util.Arrays;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.util.recipeBuilder.RecipeDraft;
import com.LazyFlesh.variablehorizons.util.recipeBuilder.RecipeModifiers;
import com.LazyFlesh.variablehorizons.util.recipeBuilder.RecipePhase;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import gregtech.api.enums.Materials;
import gregtech.api.enums.TierEU;
import gregtech.api.enums.VoltageIndex;
import gregtech.api.recipe.metadata.NanochipAssemblyMatrixTierKey;
import gregtech.api.util.GTRecipeConstants;
import gregtech.common.tileentities.machines.multi.nanochip.util.CircuitComponent;
import gregtech.common.tileentities.machines.multi.nanochip.util.CircuitComponent.CircuitComponentStack;

public class NAC implements Runnable {

    private static void addModifiableAssemblyMatrixRecipe(String id, List<Object> input, List<FluidStack> fluidInputs,
        CircuitComponentStack output, int duration, long eut, int recipeTier) {
        if (output.getCircuitComponent().realComponent == null) {
            throw new IllegalArgumentException("No real circuit was defined for given output!");
        }

        Object[] inputs = new Object[input.size()];
        for (int i = 0; i < input.size(); i++) {
            Object inputThing = input.get(i);
            if (inputThing instanceof CircuitComponent.CircuitComponentStack ccStack) {
                inputs[i] = ccStack.getCircuitComponent()
                    .getFakeStack(ccStack.getSize());
            } else if (inputThing instanceof Object[]alts) {
                inputs[i] = Arrays.stream(alts)
                    .map(c -> {
                        if (c instanceof CircuitComponent.CircuitComponentStack ccStack) {
                            return ccStack.getCircuitComponent()
                                .getFakeStack(ccStack.getSize());
                        } else if (c instanceof ItemStack stack) {
                            return stack;
                        }
                        throw new IllegalArgumentException("Must pass a CircuitComponentStack or ItemStack array!");
                    })
                    .toArray(ItemStack[]::new);
            }
        }

        RecipeModifiers.defineBase(
            id,
            () -> RecipeDraft.of(nanochipAssemblyMatrixRecipes)
                .metadata(NanochipAssemblyMatrixTierKey.INSTANCE, recipeTier)
                .metadata(GTRecipeConstants.CIRCUIT_CALIBRATION_TYPE, output.getCircuitComponent().circuitType)
                .itemInputs(inputs)
                .fluidInputs(fluidInputs.toArray(new FluidStack[0]))
                .itemOutputs(
                    output.getCircuitComponent()
                        .getFakeStack(output.getSize()))
                .duration(duration)
                .eut(eut));
    }

    @Override
    public void run() {
        addModifiableAssemblyMatrixRecipe(
            "NAC/integrated_processor",
            Arrays.asList(
                new CircuitComponentStack(CircuitComponent.PrimedUnattunedCircuitry, 1),
                new CircuitComponentStack(CircuitComponent.ProcessedChipASOC, 1),
                new CircuitComponentStack(CircuitComponent.ProcessedBoltCopper, 16),
                new CircuitComponentStack(CircuitComponent.ProcessedWireRedAlloy, 16)),
            Arrays.asList(Materials.SolderingAlloy.getMolten(8)),
            new CircuitComponentStack(CircuitComponent.IntegratedProcessor, 16),
            25 * SECONDS,
            TierEU.RECIPE_UV,
            VoltageIndex.UV);

        RecipeModifiers.modify(
            "NAC/integrated_processor",
            VariantNames.ALTERED_PRICING,
            RecipePhase.STRUCTURE,
            d -> d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier));
    }
}
