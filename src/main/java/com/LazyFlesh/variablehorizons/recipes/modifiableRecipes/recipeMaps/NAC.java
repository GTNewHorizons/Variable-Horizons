package com.LazyFlesh.variablehorizons.recipes.modifiableRecipes.recipeMaps;

import static gregtech.api.recipe.RecipeMaps.nanochipAssemblyMatrixRecipes;
import static gregtech.api.recipe.RecipeMaps.nanochipEncasementWrapper;
import static gregtech.api.recipe.RecipeMaps.nanochipEtchingArray;
import static gregtech.api.util.GTRecipeBuilder.INGOTS;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeBuilder.TICKS;

import java.util.Arrays;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.util.randomUtil;
import com.LazyFlesh.variablehorizons.util.recipeBuilder.RecipeDraft;
import com.LazyFlesh.variablehorizons.util.recipeBuilder.RecipeModifiers;
import com.LazyFlesh.variablehorizons.util.recipeBuilder.RecipePhase;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import bartworks.system.material.WerkstoffLoader;
import goodgenerator.items.GGMaterial;
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

    private static final VariantNames[] deps = new VariantNames[] { VariantNames.ALTERED_PRICING };

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
        if (!randomUtil.checkDeps(deps)) {
            return;
        }

        assemblyMatrix();
        encasementWrapper();
        etchingArray();
    }

    private void assemblyMatrix() {
        addModifiableAssemblyMatrixRecipe(
            "NAC/nand_chip",
            Arrays.asList(
                new CircuitComponentStack(CircuitComponent.PrimedUnattunedCircuitry, 1),
                new CircuitComponentStack(CircuitComponent.ProcessedChipASOC, 1),
                new CircuitComponentStack(CircuitComponent.ProcessedBoltRedAlloy, 16),
                new CircuitComponentStack(CircuitComponent.ProcessedWireCopper, 16)),
            Arrays.asList(Materials.Lead.getMolten(16)),
            new CircuitComponentStack(CircuitComponent.NandChip, 64),
            25 * SECONDS,
            TierEU.RECIPE_UV,
            VoltageIndex.UV);

        RecipeModifiers.modify(
            "NAC/nand_chip",
            VariantNames.ALTERED_PRICING,
            RecipePhase.SCALE,
            d -> d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier));

        addModifiableAssemblyMatrixRecipe(
            "NAC/microprocessor",
            Arrays.asList(
                new CircuitComponentStack(CircuitComponent.PrimedUnattunedCircuitry, 1),
                new CircuitComponentStack(CircuitComponent.ProcessedChipASOC, 1),
                new CircuitComponentStack(CircuitComponent.ProcessedBoltCopper, 16),
                new CircuitComponentStack(CircuitComponent.ProcessedWireCopper, 16)),
            Arrays.asList(Materials.Tin.getMolten(8)),
            new CircuitComponentStack(CircuitComponent.Microprocessor, 32),
            25 * SECONDS,
            TierEU.RECIPE_UV,
            VoltageIndex.UV);

        RecipeModifiers.modify(
            "NAC/microprocessor",
            VariantNames.ALTERED_PRICING,
            RecipePhase.SCALE,
            d -> d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier));

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

        addModifiableAssemblyMatrixRecipe(
            "NAC/wetware_processor",
            Arrays.asList(
                new CircuitComponentStack(CircuitComponent.ProcessedBoardWetwareLifesupport, 1),
                new CircuitComponentStack(CircuitComponent.ProcessedChipLivingCrystal, 1),
                new CircuitComponentStack(CircuitComponent.ProcessedWireYttriumBariumCuprate, 8),
                new CircuitComponentStack(CircuitComponent.ProcessedBoltCosmicNeutronium, 4)),
            Arrays.asList(MaterialsAlloy.INDALLOY_140.getFluidStack(4)),
            new CircuitComponentStack(CircuitComponent.WetwareProcessor, 1),
            11 * SECONDS + 4 * TICKS,
            TierEU.RECIPE_UEV,
            VoltageIndex.UV);

        RecipeModifiers.modify(
            "NAC/wetware_processor",
            VariantNames.ALTERED_PRICING,
            RecipePhase.SCALE,
            d -> d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier));

        addModifiableAssemblyMatrixRecipe(
            "NAC/wetware_assembly",
            Arrays.asList(
                new CircuitComponentStack(CircuitComponent.ProcessedBoardWetwareLifesupport, 1),
                new CircuitComponentStack(CircuitComponent.WetwareProcessor, 2),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDInductor, 2),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDCapacitor, 3),
                new CircuitComponentStack(CircuitComponent.ProcessedChipRAM, 24),
                new CircuitComponentStack(CircuitComponent.ProcessedWireYttriumBariumCuprate, 16)),
            Arrays.asList(MaterialsAlloy.INDALLOY_140.getFluidStack(9)),
            new CircuitComponentStack(CircuitComponent.WetwareAssembly, 1),
            22 * SECONDS + 10 * TICKS,
            TierEU.RECIPE_UEV,
            VoltageIndex.UV);

        RecipeModifiers.modify(
            "NAC/wetware_assembly",
            VariantNames.ALTERED_PRICING,
            RecipePhase.SCALE,
            d -> d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                CircuitComponent.WetwareProcessor.getFakeStack(1)));

        addModifiableAssemblyMatrixRecipe(
            "NAC/wetware_computer",
            Arrays.asList(
                new CircuitComponentStack(CircuitComponent.ProcessedBoardWetwareLifesupport, 2),
                new CircuitComponentStack(CircuitComponent.WetwareAssembly, 2),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDDiode, 2),
                new CircuitComponentStack(CircuitComponent.ProcessedChipNOR, 16),
                new CircuitComponentStack(CircuitComponent.ProcessedChipRAM, 64),
                new CircuitComponentStack(CircuitComponent.ProcessedWireYttriumBariumCuprate, 24)),
            Arrays.asList(MaterialsAlloy.INDALLOY_140.getFluidStack(9)),
            new CircuitComponentStack(CircuitComponent.WetwareComputer, 1),
            45 * SECONDS,
            TierEU.RECIPE_UEV,
            VoltageIndex.UV);

        RecipeModifiers.modify(
            "NAC/wetware_computer",
            VariantNames.ALTERED_PRICING,
            RecipePhase.SCALE,
            d -> d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                CircuitComponent.WetwareAssembly.getFakeStack(1)));

        addModifiableAssemblyMatrixRecipe(
            "NAC/wetware_mainframe",
            Arrays.asList(
                new CircuitComponentStack(CircuitComponent.ProcessedAdvancedMainframeCasing, 2),
                new CircuitComponentStack(CircuitComponent.WetwareComputer, 2),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDInductor, 4),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDCapacitor, 4),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDResistor, 4),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDTransistor, 4),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDDiode, 4),
                new CircuitComponentStack(CircuitComponent.ProcessedChipRAM, 48),
                new Object[] { new CircuitComponentStack(CircuitComponent.ProcessedSuperconductorZPM, 64),
                    new CircuitComponentStack(CircuitComponent.ProcessedSuperconductorUV, 32),
                    new CircuitComponentStack(CircuitComponent.ProcessedSuperconductorUHV, 16),
                    new CircuitComponentStack(CircuitComponent.ProcessedSuperconductorUEV, 8),
                    new CircuitComponentStack(CircuitComponent.ProcessedSuperconductorUIV, 4),
                    new CircuitComponentStack(CircuitComponent.ProcessedSuperconductorUMV, 2), }),
            Arrays.asList(
                MaterialsAlloy.INDALLOY_140.getFluidStack(2 * INGOTS),
                FluidRegistry.getFluidStack("ic2coolant", 10000),
                Materials.Radon.getGas(2500)),
            new CircuitComponentStack(CircuitComponent.WetwareMainframe, 1),
            90 * SECONDS,
            TierEU.RECIPE_UEV,
            VoltageIndex.UV);

        RecipeModifiers.modify(
            "NAC/wetware_mainframe",
            VariantNames.ALTERED_PRICING,
            RecipePhase.SCALE,
            d -> d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                CircuitComponent.WetwareComputer.getFakeStack(1)));

        addModifiableAssemblyMatrixRecipe(
            "NAC/bio_processor",
            Arrays.asList(
                new CircuitComponentStack(CircuitComponent.ProcessedBioProcessingUnit, 1),
                new CircuitComponentStack(CircuitComponent.ProcessedChipRawAdvancedCrystal, 1),
                new CircuitComponentStack(CircuitComponent.ProcessedChipNanoCPU, 2),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDCapacitor, 3),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDTransistor, 3),
                new CircuitComponentStack(CircuitComponent.ProcessedWireNiobiumTitanium, 16)),
            Arrays.asList(MaterialsAlloy.INDALLOY_140.getFluidStack(4)),
            new CircuitComponentStack(CircuitComponent.BiowareProcessor, 1),
            11 * SECONDS + 5 * TICKS,
            TierEU.RECIPE_UIV,
            VoltageIndex.UHV);

        RecipeModifiers.modify(
            "NAC/bio_processor",
            VariantNames.ALTERED_PRICING,
            RecipePhase.SCALE,
            d -> d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier));

        addModifiableAssemblyMatrixRecipe(
            "NAC/bio_processor_soc",
            Arrays.asList(
                new CircuitComponentStack(CircuitComponent.ProcessedBoardBioMutated, 1),
                new CircuitComponentStack(CircuitComponent.ProcessedLivingBioChip, 1),
                new CircuitComponentStack(CircuitComponent.ProcessedWireNiobiumTitanium, 16),
                new CircuitComponentStack(CircuitComponent.ProcessedBoltChromaticGlass, 4)),
            Arrays.asList(MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(9)),
            new CircuitComponentStack(CircuitComponent.BiowareProcessor, 1),
            11 * SECONDS + 5 * TICKS,
            TierEU.RECIPE_UIV,
            VoltageIndex.UHV);

        RecipeModifiers.modify(
            "NAC/bio_processor_soc",
            VariantNames.ALTERED_PRICING,
            RecipePhase.SCALE,
            d -> d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier));

        addModifiableAssemblyMatrixRecipe(
            "NAC/bio_assembly",
            Arrays.asList(
                new CircuitComponentStack(CircuitComponent.ProcessedBoardBioMutated, 1),
                new CircuitComponentStack(CircuitComponent.BiowareProcessor, 2),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDInductor, 3),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDCapacitor, 4),
                new CircuitComponentStack(CircuitComponent.ProcessedChipRAM, 32),
                new CircuitComponentStack(CircuitComponent.ProcessedWireNiobiumTitanium, 24)),
            Arrays.asList(MaterialsAlloy.INDALLOY_140.getFluidStack(9)),
            new CircuitComponentStack(CircuitComponent.BiowareAssembly, 1),
            22 * SECONDS + 10 * TICKS,
            TierEU.RECIPE_UIV,
            VoltageIndex.UHV);

        RecipeModifiers.modify(
            "NAC/bio_assembly",
            VariantNames.ALTERED_PRICING,
            RecipePhase.SCALE,
            d -> d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                CircuitComponent.BiowareProcessor.getFakeStack(1)));

        addModifiableAssemblyMatrixRecipe(
            "NAC/bio_computer",
            Arrays.asList(
                new CircuitComponentStack(CircuitComponent.ProcessedBoardBioMutated, 2),
                new CircuitComponentStack(CircuitComponent.BiowareAssembly, 2),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDTransistor, 4),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDResistor, 4),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDCapacitor, 4),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDDiode, 4),
                new CircuitComponentStack(CircuitComponent.ProcessedChipNOR, 32),
                new CircuitComponentStack(CircuitComponent.ProcessedChipRAM, 64),
                new CircuitComponentStack(CircuitComponent.ProcessedWireNiobiumTitanium, 32),
                new Object[] { new CircuitComponentStack(CircuitComponent.ProcessedFoilSiliconeRubber, 64),
                    new CircuitComponentStack(CircuitComponent.ProcessedFoilStyreneRubber, 64) }),
            Arrays.asList(
                MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(10 * INGOTS),
                Materials.BioMediumSterilized.getFluid(5 * INGOTS),
                Materials.SuperCoolant.getFluid(10000)),
            new CircuitComponentStack(CircuitComponent.BiowareComputer, 1),
            45 * SECONDS,
            TierEU.RECIPE_UIV,
            VoltageIndex.UHV);

        RecipeModifiers.modify(
            "NAC/bio_computer",
            VariantNames.ALTERED_PRICING,
            RecipePhase.SCALE,
            d -> d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                CircuitComponent.BiowareAssembly.getFakeStack(1)));

        addModifiableAssemblyMatrixRecipe(
            "NAC/bio_mainframe",
            Arrays.asList(
                new CircuitComponentStack(CircuitComponent.ProcessedAdvancedMainframeCasing, 4),
                new CircuitComponentStack(CircuitComponent.BiowareComputer, 2),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDInductor, 6),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDCapacitor, 6),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDResistor, 6),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDTransistor, 6),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDDiode, 6),
                new CircuitComponentStack(CircuitComponent.ProcessedChipRAM, 64),
                new Object[] { new CircuitComponentStack(CircuitComponent.ProcessedSuperconductorUHV, 64),
                    new CircuitComponentStack(CircuitComponent.ProcessedSuperconductorUEV, 32),
                    new CircuitComponentStack(CircuitComponent.ProcessedSuperconductorUIV, 16),
                    new CircuitComponentStack(CircuitComponent.ProcessedSuperconductorUMV, 8), }),
            Arrays.asList(
                MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(20 * INGOTS),
                Materials.BioMediumSterilized.getFluid(10 * INGOTS),
                Materials.SuperCoolant.getFluid(20000)),
            new CircuitComponentStack(CircuitComponent.BiowareMainframe, 1),
            90 * SECONDS,
            TierEU.RECIPE_UIV,
            VoltageIndex.UHV);

        RecipeModifiers.modify(
            "NAC/bio_mainframe",
            VariantNames.ALTERED_PRICING,
            RecipePhase.SCALE,
            d -> d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                CircuitComponent.BiowareComputer.getFakeStack(1)));

        addModifiableAssemblyMatrixRecipe(
            "NAC/optical_processor",
            Arrays.asList(
                new CircuitComponentStack(CircuitComponent.ProcessedChipOpticalCPU, 1),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalRAM, 2),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDCapacitor, 16),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDDiode, 16),
                new CircuitComponentStack(CircuitComponent.ProcessedCableOpticalFiber, 4),
                new CircuitComponentStack(CircuitComponent.ProcessedBoltEnrichedHolmium, 4)),
            Arrays.asList(MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(18)),
            new CircuitComponentStack(CircuitComponent.OpticalProcessor, 1),
            25 * SECONDS,
            TierEU.RECIPE_UIV,
            VoltageIndex.UHV);

        RecipeModifiers.modify(
            "NAC/optical_processor",
            VariantNames.ALTERED_PRICING,
            RecipePhase.SCALE,
            d -> d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier));

        addModifiableAssemblyMatrixRecipe(
            "NAC/optical_assembly",
            Arrays.asList(
                new CircuitComponentStack(CircuitComponent.ProcessedBoardOptical, 1),
                new CircuitComponentStack(CircuitComponent.OpticalProcessor, 2),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDInductor, 16),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDCapacitor, 20),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDResistor, 20),
                new CircuitComponentStack(CircuitComponent.ProcessedChipNOR, 32),
                new CircuitComponentStack(CircuitComponent.ProcessedChipRAM, 64),
                new CircuitComponentStack(CircuitComponent.ProcessedWireLumiium, 24),
                new Object[] { new CircuitComponentStack(CircuitComponent.ProcessedFoilSiliconeRubber, 64),
                    new CircuitComponentStack(CircuitComponent.ProcessedFoilStyreneRubber, 64) }),
            Arrays.asList(
                MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(10 * INGOTS),
                Materials.Radon.getPlasma(10 * INGOTS),
                Materials.SuperCoolant.getFluid(10000),
                WerkstoffLoader.Oganesson.getFluidOrGas(500)),
            new CircuitComponentStack(CircuitComponent.OpticalAssembly, 1),
            50 * SECONDS,
            TierEU.RECIPE_UIV,
            VoltageIndex.UHV);

        RecipeModifiers.modify(
            "NAC/optical_assembly",
            VariantNames.ALTERED_PRICING,
            RecipePhase.SCALE,
            d -> d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                CircuitComponent.OpticalProcessor.getFakeStack(1)));

        addModifiableAssemblyMatrixRecipe(
            "NAC/optical_computer",
            Arrays.asList(
                new CircuitComponentStack(CircuitComponent.ProcessedBoardOptical, 2),
                new CircuitComponentStack(CircuitComponent.OpticalAssembly, 2),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDTransistor, 24),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDResistor, 24),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDCapacitor, 24),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDDiode, 24),
                new CircuitComponentStack(CircuitComponent.ProcessedChipNOR, 64),
                new CircuitComponentStack(CircuitComponent.ProcessedChipASOC, 32),
                new CircuitComponentStack(CircuitComponent.ProcessedWireLumiium, 32),
                new Object[] { new CircuitComponentStack(CircuitComponent.ProcessedFoilSiliconeRubber, 64),
                    new CircuitComponentStack(CircuitComponent.ProcessedFoilStyreneRubber, 64) },
                new CircuitComponentStack(CircuitComponent.ProcessedFoilPolybenzimidazole, 64)),
            Arrays.asList(
                MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(20 * INGOTS),
                Materials.Radon.getPlasma(20 * INGOTS),
                Materials.SuperCoolant.getFluid(20000),
                WerkstoffLoader.Oganesson.getFluidOrGas(1000)),
            new CircuitComponentStack(CircuitComponent.OpticalComputer, 1),
            100 * SECONDS,
            TierEU.RECIPE_UIV,
            VoltageIndex.UHV);

        RecipeModifiers.modify(
            "NAC/optical_computer",
            VariantNames.ALTERED_PRICING,
            RecipePhase.SCALE,
            d -> d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                CircuitComponent.OpticalAssembly.getFakeStack(1)));

        addModifiableAssemblyMatrixRecipe(
            "NAC/optical_mainframe",
            Arrays.asList(
                new CircuitComponentStack(CircuitComponent.ProcessedAdvancedMainframeCasing, 8),
                new CircuitComponentStack(CircuitComponent.OpticalComputer, 2),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDInductor, 32),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDCapacitor, 32),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDResistor, 32),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDTransistor, 32),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDDiode, 32),
                new CircuitComponentStack(CircuitComponent.ProcessedChipASOC, 64),
                new Object[] { new CircuitComponentStack(CircuitComponent.ProcessedSuperconductorUEV, 64),
                    new CircuitComponentStack(CircuitComponent.ProcessedSuperconductorUIV, 32),
                    new CircuitComponentStack(CircuitComponent.ProcessedSuperconductorUMV, 16), }),
            Arrays.asList(
                MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(40 * INGOTS),
                Materials.Radon.getPlasma(40 * INGOTS),
                Materials.SuperCoolant.getFluid(40000),
                WerkstoffLoader.Oganesson.getFluidOrGas(2000)),
            new CircuitComponentStack(CircuitComponent.OpticalMainframe, 1),
            200 * SECONDS,
            TierEU.RECIPE_UIV,
            VoltageIndex.UHV);

        RecipeModifiers.modify(
            "NAC/optical_mainframe",
            VariantNames.ALTERED_PRICING,
            RecipePhase.SCALE,
            d -> d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                CircuitComponent.OpticalComputer.getFakeStack(1)));

        addModifiableAssemblyMatrixRecipe(
            "NAC/pico_circuit",
            Arrays.asList(
                new CircuitComponentStack(CircuitComponent.ProcessedPicoCircuitCasing, 1),
                new CircuitComponentStack(CircuitComponent.CleansedBoardPico, 2),
                new CircuitComponentStack(CircuitComponent.PicoCPU, 1),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDInductor, 32),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDCapacitor, 32),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDResistor, 32),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDTransistor, 32),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDDiode, 32),
                new CircuitComponentStack(CircuitComponent.ProcessedWireProtoHalkonite, 64),
                new CircuitComponentStack(CircuitComponent.ProcessedBoltTranscendentMetal, 32),
                new CircuitComponentStack(CircuitComponent.ProcessedWireInfinity, 8)),
            Arrays.asList(
                MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(16 * INGOTS),
                Materials.UUMatter.getFluid(8000),
                GGMaterial.preciousMetalAlloy.getMolten(8 * INGOTS)),
            new CircuitComponentStack(CircuitComponent.PicoCircuit, 1),
            200 * SECONDS,
            TierEU.RECIPE_MAX,
            VoltageIndex.UEV);

        RecipeModifiers.modify(
            "NAC/pico_circuit",
            VariantNames.ALTERED_PRICING,
            RecipePhase.SCALE,
            d -> d.scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, CircuitComponent.PicoCPU.getFakeStack(1)));

        addModifiableAssemblyMatrixRecipe(
            "NAC/quantum_circuit",
            Arrays.asList(
                new CircuitComponentStack(CircuitComponent.ProcessedQuantumCircuitCasing, 1),
                new CircuitComponentStack(CircuitComponent.CleansedBoardQuantum, 2),
                new CircuitComponentStack(CircuitComponent.QuantumCPU, 1),
                new CircuitComponentStack(CircuitComponent.BundledUnityWire, 4),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDInductor, 64),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDCapacitor, 64),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDResistor, 64),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDTransistor, 64),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDDiode, 64),
                new CircuitComponentStack(CircuitComponent.ProcessedWireProtoHalkonite, 64),
                new CircuitComponentStack(CircuitComponent.ProcessedBoltUMVSuperconductor, 64),
                new CircuitComponentStack(CircuitComponent.ProcessedWireSpacetime, 8)),
            Arrays.asList(
                MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(32 * INGOTS),
                Materials.UUMatter.getFluid(16000),
                Materials.SixPhasedCopper.getMolten(1 * INGOTS),
                Materials.ExcitedDTEC.getFluid(1000)),
            new CircuitComponentStack(CircuitComponent.QuantumCircuit, 1),
            400 * SECONDS,
            TierEU.RECIPE_MAX,
            VoltageIndex.UIV);

        RecipeModifiers.modify(
            "NAC/quantum_circuit",
            VariantNames.ALTERED_PRICING,
            RecipePhase.SCALE,
            d -> d
                .scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, CircuitComponent.QuantumCPU.getFakeStack(1)));

        addModifiableAssemblyMatrixRecipe(
            "NAC/planck_circuit",
            Arrays.asList(
                new CircuitComponentStack(CircuitComponent.ProcessedPlanckCircuitCasing, 1),
                new CircuitComponentStack(CircuitComponent.CleansedBoardPlanck, 2),
                new CircuitComponentStack(CircuitComponent.PlanckCPU, 1),
                new CircuitComponentStack(CircuitComponent.BundledStellarHarmonyWire, 4),
                new CircuitComponentStack(CircuitComponent.CalabiYauManifold, 1),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDInductor, 64),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDCapacitor, 64),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDResistor, 64),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDTransistor, 64),
                new CircuitComponentStack(CircuitComponent.ProcessedOpticalSMDDiode, 64),
                new CircuitComponentStack(CircuitComponent.ProcessedWireHypogen, 64),
                new CircuitComponentStack(CircuitComponent.ProcessedBoltWhiteDwarfMatter, 32),
                new CircuitComponentStack(CircuitComponent.ProcessedCoiledThermalSuperconductor, 8)),
            Arrays.asList(
                Materials.BoundlessCosmicSolder.getFluid(4000),
                Materials.PrimordialMatter.getFluid(4000),
                Materials.PhononMedium.getFluid(1000),
                Materials.Space.getMolten(4000)),
            new CircuitComponentStack(CircuitComponent.PlanckCircuit, 1),
            600 * SECONDS,
            TierEU.RECIPE_MAX,
            VoltageIndex.UMV);

        RecipeModifiers.modify(
            "NAC/planck_circuit",
            VariantNames.ALTERED_PRICING,
            RecipePhase.SCALE,
            d -> d
                .scaleAllInputsBalanced(GeneralConfig.partCostMultiplier, CircuitComponent.PlanckCPU.getFakeStack(1)));
    }

    private void encasementWrapper() {
        RecipeModifiers.defineBase(
            "NAC/pico_cpu",
            () -> RecipeDraft.of(nanochipEncasementWrapper)
                .itemInputs(
                    CircuitComponent.OpticalMainframeRack.getFakeStack(1),
                    CircuitComponent.ProcessedPlateRhugnor.getFakeStack(2),
                    CircuitComponent.ProcessedChipAttoPIC.getFakeStack(8))
                .fluidInputs(Materials.Space.getMolten(250))
                .itemOutputs(CircuitComponent.PicoCPU.getFakeStack(1))
                .duration(20 * SECONDS)
                .eut(TierEU.RECIPE_UMV));

        RecipeModifiers.modify(
            "NAC/pico_cpu",
            VariantNames.ALTERED_PRICING,
            RecipePhase.SCALE,
            d -> d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                CircuitComponent.OpticalMainframeRack.getFakeStack(1)));

        RecipeModifiers.defineBase(
            "NAC/quantum_cpu",
            () -> RecipeDraft.of(nanochipEncasementWrapper)
                .itemInputs(
                    CircuitComponent.PicoCircuitRack.getFakeStack(1),
                    CircuitComponent.ProcessedPlateMetastableOganesson.getFakeStack(2),
                    CircuitComponent.ProcessedChipZeptoPIC.getFakeStack(8))
                .fluidInputs(Materials.Space.getMolten(500))
                .itemOutputs(CircuitComponent.QuantumCPU.getFakeStack(1))
                .duration(20 * SECONDS)
                .eut(TierEU.RECIPE_UXV));

        RecipeModifiers.modify(
            "NAC/quantum_cpu",
            VariantNames.ALTERED_PRICING,
            RecipePhase.SCALE,
            d -> d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                CircuitComponent.PicoCircuitRack.getFakeStack(1)));

        RecipeModifiers.defineBase(
            "NAC/planck_cpu",
            () -> RecipeDraft.of(nanochipEncasementWrapper)
                .itemInputs(
                    CircuitComponent.RealizedQuantumCircuitRack.getFakeStack(1),
                    CircuitComponent.ProcessedPlateHexanite.getFakeStack(2),
                    CircuitComponent.ProcessedChipYoctoPIC.getFakeStack(8))
                .fluidInputs(Materials.Space.getMolten(1000))
                .itemOutputs(CircuitComponent.PlanckCPU.getFakeStack(1))
                .duration(7 * SECONDS)
                .eut(TierEU.RECIPE_MAX));

        RecipeModifiers.modify(
            "NAC/planck_cpu",
            VariantNames.ALTERED_PRICING,
            RecipePhase.SCALE,
            d -> d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                CircuitComponent.RealizedQuantumCircuitRack.getFakeStack(1)));
    }

    private void etchingArray() {
        RecipeModifiers.defineBase(
            "NAC/optical_mainframe_rack",
            () -> RecipeDraft.of(nanochipEtchingArray)
                .itemInputs(
                    CircuitComponent.OpticalMainframe.getFakeStack(2),
                    CircuitComponent.ScrewEnrichedNaquadahAlloy.getFakeStack(2))
                .itemOutputs(CircuitComponent.OpticalMainframeRack.getFakeStack(1))
                .duration(20 * SECONDS)
                .eut(TierEU.RECIPE_UXV));

        RecipeModifiers.modify(
            "NAC/optical_mainframe_rack",
            VariantNames.ALTERED_PRICING,
            RecipePhase.SCALE,
            d -> d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                CircuitComponent.OpticalMainframe.getFakeStack(1)));

        RecipeModifiers.defineBase(
            "NAC/pico_circuit_rack",
            () -> RecipeDraft.of(nanochipEtchingArray)
                .itemInputs(
                    CircuitComponent.OrganizedPicoCircuit.getFakeStack(2),
                    CircuitComponent.ScrewQuantum.getFakeStack(2))
                .itemOutputs(
                    CircuitComponent.PicoCircuitRack.getFakeStack(1),
                    CircuitComponent.CompressionResidue.getFakeStack(4))
                .duration(20 * SECONDS)
                .eut(TierEU.RECIPE_UMV));

        RecipeModifiers.modify("NAC/pico_circuit_rack", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
            d.scaleAllInputsBalanced(
                GeneralConfig.partCostMultiplier,
                CircuitComponent.OrganizedPicoCircuit.getFakeStack(1));
            d.scaleAllOutputsExcept(GeneralConfig.partCostMultiplier, CircuitComponent.PicoCircuitRack.getFakeStack(1));
        });

        RecipeModifiers.defineBase(
            "NAC/lost_quantum_mainframe_rack",
            () -> RecipeDraft.of(nanochipEtchingArray)
                .itemInputs(
                    CircuitComponent.OrganizedQuantumCircuit.getFakeStack(2),
                    CircuitComponent.ScrewEternity.getFakeStack(2))
                .itemOutputs(
                    CircuitComponent.LostQuantumCircuitRack.getFakeStack(1),
                    CircuitComponent.CollapsingResidue.getFakeStack(4))
                .duration(13 * SECONDS)
                .eut(TierEU.RECIPE_MAX));

        RecipeModifiers
            .modify("NAC/lost_quantum_mainframe_rack", VariantNames.ALTERED_PRICING, RecipePhase.SCALE, d -> {
                d.scaleAllInputsBalanced(
                    GeneralConfig.partCostMultiplier,
                    CircuitComponent.OrganizedQuantumCircuit.getFakeStack(1));
                d.scaleAllOutputsExcept(
                    GeneralConfig.partCostMultiplier,
                    CircuitComponent.LostQuantumCircuitRack.getFakeStack(1));
            });
    }
}
