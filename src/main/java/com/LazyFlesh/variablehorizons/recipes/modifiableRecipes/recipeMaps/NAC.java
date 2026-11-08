package com.LazyFlesh.variablehorizons.recipes.modifiableRecipes.recipeMaps;

import static gregtech.api.recipe.RecipeMaps.nanochipAssemblyMatrixRecipes;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeBuilder.TICKS;

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
import gtPlusPlus.core.material.MaterialMisc;
import gtPlusPlus.core.material.MaterialsAlloy;

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
            RecipePhase.SCALE,
            d -> d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier));

        addModifiableAssemblyMatrixRecipe(
            "NAC/nano_processor",
            Arrays.asList(
                new CircuitComponentStack(CircuitComponent.PrimedUnattunedCircuitry, 1),
                new CircuitComponentStack(CircuitComponent.ProcessedChipASOC, 1),
                new CircuitComponentStack(CircuitComponent.ProcessedBoltPlatinum, 16),
                new CircuitComponentStack(CircuitComponent.ProcessedWireElectrum, 16)),
            Arrays.asList(MaterialsAlloy.INDALLOY_140.getFluidStack(8)),
            new CircuitComponentStack(CircuitComponent.NanoProcessor, 8),
            25 * SECONDS,
            TierEU.RECIPE_UV,
            VoltageIndex.UV);

        RecipeModifiers.modify(
            "NAC/nano_processor",
            VariantNames.ALTERED_PRICING,
            RecipePhase.SCALE,
            d -> d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier));

        addModifiableAssemblyMatrixRecipe(
            "NAC/quantum_processor",
            Arrays.asList(
                new CircuitComponentStack(CircuitComponent.PrimedUnattunedCircuitry, 1),
                new CircuitComponentStack(CircuitComponent.ProcessedChipASOC, 1),
                new CircuitComponentStack(CircuitComponent.ProcessedBoltNiobiumTitanium, 16),
                new CircuitComponentStack(CircuitComponent.ProcessedWirePlatinum, 16)),
            Arrays.asList(MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(8)),
            new CircuitComponentStack(CircuitComponent.QuantumProcessor, 4),
            25 * SECONDS,
            TierEU.RECIPE_UV,
            VoltageIndex.UV);

        RecipeModifiers.modify(
            "NAC/quantum_processor",
            VariantNames.ALTERED_PRICING,
            RecipePhase.SCALE,
            d -> d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier));

        addModifiableAssemblyMatrixRecipe(
            "NAC/crystal_processor",
            Arrays.asList(
                new CircuitComponentStack(CircuitComponent.ProcessedBoardMultifiberglassElite, 1),
                new CircuitComponentStack(CircuitComponent.ProcessedChipCrystalSoC, 1),
                new CircuitComponentStack(CircuitComponent.ProcessedWireNiobiumTitanium, 8),
                new CircuitComponentStack(CircuitComponent.ProcessedBoltYttriumBariumCuprate, 4)),
            Arrays.asList(MaterialsAlloy.INDALLOY_140.getFluidStack(4)),
            new CircuitComponentStack(CircuitComponent.CrystalProcessor, 1),
            2 * SECONDS + 10 * TICKS,
            TierEU.RECIPE_UHV,
            VoltageIndex.UV);

        RecipeModifiers.modify(
            "NAC/crystal_processor",
            VariantNames.ALTERED_PRICING,
            RecipePhase.SCALE,
            d -> d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier));

        addModifiableAssemblyMatrixRecipe(
            "NAC/crystal_assembly",
            Arrays.asList(
                new CircuitComponentStack(CircuitComponent.ProcessedBoardMultifiberglassElite, 1),
                new CircuitComponentStack(CircuitComponent.CrystalProcessor, 2),
                new CircuitComponentStack(CircuitComponent.ProcessedAdvSMDInductor, 6),
                new CircuitComponentStack(CircuitComponent.ProcessedAdvSMDCapacitor, 8),
                new CircuitComponentStack(CircuitComponent.ProcessedChipRAM, 24),
                new CircuitComponentStack(CircuitComponent.ProcessedWireNiobiumTitanium, 16)),
            Arrays.asList(MaterialsAlloy.INDALLOY_140.getFluidStack(9)),
            new CircuitComponentStack(CircuitComponent.CrystalAssembly, 1),
            5 * SECONDS,
            TierEU.RECIPE_UHV,
            VoltageIndex.UV);

        RecipeModifiers.modify(
            "NAC/crystal_assembly",
            VariantNames.ALTERED_PRICING,
            RecipePhase.SCALE,
            d -> d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                CircuitComponent.CrystalProcessor.getFakeStack(2)));

        addModifiableAssemblyMatrixRecipe(
            "NAC/crystal_computer",
            Arrays.asList(
                new CircuitComponentStack(CircuitComponent.ProcessedBoardMultifiberglassElite, 1),
                new CircuitComponentStack(CircuitComponent.CrystalAssembly, 2),
                new CircuitComponentStack(CircuitComponent.ProcessedChipRAM, 6),
                new CircuitComponentStack(CircuitComponent.ProcessedChipNOR, 32),
                new CircuitComponentStack(CircuitComponent.ProcessedChipNAND, 64),
                new CircuitComponentStack(CircuitComponent.ProcessedWireNiobiumTitanium, 32)),
            Arrays.asList(MaterialsAlloy.INDALLOY_140.getFluidStack(9)),
            new CircuitComponentStack(CircuitComponent.CrystalComputer, 1),
            10 * SECONDS,
            TierEU.RECIPE_UHV,
            VoltageIndex.UV);

        RecipeModifiers.modify(
            "NAC/crystal_computer",
            VariantNames.ALTERED_PRICING,
            RecipePhase.SCALE,
            d -> d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                CircuitComponent.CrystalAssembly.getFakeStack(2)));

        addModifiableAssemblyMatrixRecipe(
            "NAC/crystal_mainframe",
            Arrays.asList(
                new CircuitComponentStack(CircuitComponent.ProcessedBasicMainframeCasing, 1),
                new CircuitComponentStack(CircuitComponent.CrystalComputer, 2),
                new CircuitComponentStack(CircuitComponent.ProcessedAdvSMDInductor, 8),
                new CircuitComponentStack(CircuitComponent.ProcessedAdvSMDCapacitor, 16),
                new CircuitComponentStack(CircuitComponent.ProcessedChipRAM, 32),
                new CircuitComponentStack(CircuitComponent.ProcessedSuperconductorLuV, 16)),
            Arrays.asList(MaterialsAlloy.INDALLOY_140.getFluidStack(18)),
            new CircuitComponentStack(CircuitComponent.CrystalMainframe, 1),
            20 * SECONDS,
            TierEU.RECIPE_UHV,
            VoltageIndex.UV);

        RecipeModifiers.modify(
            "NAC/crystal_mainframe",
            VariantNames.ALTERED_PRICING,
            RecipePhase.SCALE,
            d -> d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                CircuitComponent.CrystalComputer.getFakeStack(1)));
    }
}
