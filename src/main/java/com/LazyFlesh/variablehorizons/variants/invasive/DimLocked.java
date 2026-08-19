package com.LazyFlesh.variablehorizons.variants.invasive;

import static gregtech.api.enums.Mods.DraconicEvolution;
import static gregtech.api.enums.Mods.GalaxySpace;
import static gregtech.api.enums.Mods.RandomThings;
import static gregtech.api.enums.Mods.ThaumicTinkerer;
import static gregtech.api.enums.Mods.TwilightForest;
import static gregtech.api.enums.Mods.UniversalSingularities;
import static gregtech.api.recipe.RecipeMaps.blastFurnaceRecipes;
import static gregtech.api.recipe.RecipeMaps.multiblockChemicalReactorRecipes;
import static gregtech.api.util.GTModHandler.getModItem;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeConstants.COIL_HEAT;
import static gregtech.api.util.GTRecipeConstants.DEFC_CASING_TIER;
import static kubatech.loaders.DEFCRecipes.fusionCraftingRecipes;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.common.items.CombType;
import gregtech.loaders.misc.GTBees;

public class DimLocked extends VariantLoader {

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        // dimlocked doesn't need anything loaded (early mixins load on restart)
        VariantNames.DIMLOCKED.hasLoaded = true;

        registerDimlockedRecipes();
    }

    private void registerDimlockedRecipes() {

        // Barnarda C sapling
        GTValues.RA.stdBuilder()
            .itemInputs(
                getModItem(RandomThings.ID, "spectreKey", 0, 0),
                // Tree of Transformation sapling
                getModItem(TwilightForest.ID, "tile.TFSapling", 1, 6),
                GTBees.combs.getStackForType(CombType.BARNARDA, 64))
            .itemOutputs(getModItem(GalaxySpace.ID, "barnardaCsapling", 1, 0))
            .outputChances(100)
            .duration(60 * SECONDS)
            .eut(TierEU.RECIPE_UEV)
            .addTo(multiblockChemicalReactorRecipes);

        // recipes for nether air if dim locked to dimensions not nether
        if (GeneralConfig.startingDimID != -1) {
            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Netherrack, 64L),
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.HellishMetal, 16L))
                .fluidInputs(Materials.Air.getGas(1_000_000))
                .fluidOutputs(Materials.NetherAir.getFluid(100_000))
                .duration(2 * SECONDS)
                .eut(TierEU.RECIPE_LuV)
                .metadata(COIL_HEAT, 8600)
                .addTo(blastFurnaceRecipes);

            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Netherrack, 16L),
                    getModItem(ThaumicTinkerer.ID, "kamiResource", 16, 6))
                .fluidInputs(Materials.Air.getGas(1_000_000))
                .fluidOutputs(Materials.NetherAir.getFluid(1_000_000))
                .duration(1 * SECONDS)
                .eut(TierEU.RECIPE_ZPM)
                .metadata(COIL_HEAT, 10800)
                .addTo(blastFurnaceRecipes);
        }

        // recipes for chaos shard if dim locked to dimensions not the end
        if (GeneralConfig.startingDimID != 1) {
            GTValues.RA.stdBuilder()
                .itemInputs(
                    ItemList.ChaosLocator.get(1),
                    // Awakened Draconium Singularity
                    getModItem(UniversalSingularities.ID, "universal.draconicEvolution.singularity", 1, 1))
                .itemOutputs(getModItem(DraconicEvolution.ID, "chaosShard", 1, 0))
                .duration(100 * SECONDS)
                .eut(TierEU.RECIPE_UMV)
                .metadata(DEFC_CASING_TIER, 4)
                .addTo(fusionCraftingRecipes);
        }
    }
}
