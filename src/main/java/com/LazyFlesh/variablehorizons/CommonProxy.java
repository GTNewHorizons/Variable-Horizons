package com.LazyFlesh.variablehorizons;

import static gregtech.api.enums.Mods.DraconicEvolution;
import static gregtech.api.enums.Mods.ExtraUtilities;
import static gregtech.api.enums.Mods.GalaxySpace;
import static gregtech.api.enums.Mods.RandomThings;
import static gregtech.api.enums.Mods.ThaumicTinkerer;
import static gregtech.api.enums.Mods.TwilightForest;
import static gregtech.api.enums.Mods.UniversalSingularities;
import static gregtech.api.recipe.RecipeMaps.blastFurnaceRecipes;
import static gregtech.api.recipe.RecipeMaps.centrifugeNonCellRecipes;
import static gregtech.api.recipe.RecipeMaps.multiblockChemicalReactorRecipes;
import static gregtech.api.util.GTModHandler.getModItem;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeConstants.COIL_HEAT;
import static gregtech.api.util.GTRecipeConstants.DEFC_CASING_TIER;
import static kubatech.loaders.DEFCRecipes.fusionCraftingRecipes;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.variants.VariantCommands;
import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import bartworks.common.loaders.ItemRegistry;
import bartworks.system.material.WerkstoffLoader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.recipe.metadata.CentrifugeRecipeKey;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;
import gregtech.common.items.CombType;
import gregtech.loaders.misc.GTBees;

public class CommonProxy {

    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        VariableHorizons.LOG.info("Variable Horizons, version " + Tags.VERSION);
    }

    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {}

    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {

        // recipes for nether air if dim locked to dimensions not nether
        if (VariantNames.activeContains(VariantNames.DIMLOCKED.id) && GeneralConfig.startingDimID != -1
            && !GeneralConfig.disableVariants) {

            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Netherrack, 64L),
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.HellishMetal, 64L))
                .fluidInputs(Materials.Air.getGas(10_000))
                .fluidOutputs(Materials.NetherAir.getFluid(1_000))
                .duration(2 * SECONDS)
                .eut(TierEU.RECIPE_LuV)
                .metadata(COIL_HEAT, 8600)
                .addTo(blastFurnaceRecipes);

            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Netherrack, 16L),
                    getModItem(ThaumicTinkerer.ID, "kamiResource", 16, 6))
                .fluidInputs(Materials.Air.getGas(10_000))
                .fluidOutputs(Materials.NetherAir.getFluid(10_000))
                .duration(1 * SECONDS)
                .eut(TierEU.RECIPE_ZPM)
                .metadata(COIL_HEAT, 10800)
                .addTo(blastFurnaceRecipes);
        }

        // recipes for chaos shard if dim locked to dimensions not the end
        if (VariantNames.activeContains(VariantNames.DIMLOCKED.id) && GeneralConfig.startingDimID != 1
            && !GeneralConfig.disableVariants) {

            GTValues.RA.stdBuilder()
                .itemInputs(
                    ItemList.ChaosLocator.get(1),
                    // Awakened Draconium Singularity
                    getModItem(UniversalSingularities.ID, "universal.draconicEvolution.singularity", 1, 1))
                .itemOutputs(getModItem(DraconicEvolution.ID, "chaosShard", 1, 0))
                .duration(100 * SECONDS)
                .eut(TierEU.RECIPE_UMV)
                .metadata(DEFC_CASING_TIER, 3)
                .addTo(fusionCraftingRecipes);
        }

        // recipes when dimlock and GotG both activated
        if (VariantNames.activeContains(VariantNames.DIMLOCKED.id)
            && VariantNames.activeContains(VariantNames.GARDEN_OF_GRIND.id)
            && !GeneralConfig.disableVariants) {

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

            // dd Iron + forcicium + forcillium
            GTValues.RA.stdBuilder()
                .itemInputs(
                    GTUtility.copyAmount(0, ItemRegistry.voidminer[2]),
                    getModItem(ExtraUtilities.ID, "dark_portal", 1, 0))
                .fluidInputs(WerkstoffLoader.Oganesson.getFluidOrGas(2000))
                .itemOutputs(
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.DarkIron, 64),
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Forcicium, 64),
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Forcillium, 64))
                .metadata(CentrifugeRecipeKey.INSTANCE, true)
                .duration(1000 * SECONDS)
                .eut(TierEU.RECIPE_UXV)
                .addTo(centrifugeNonCellRecipes);
        }
    }

    // damn nhcoremod...
    public void completeLoad(FMLLoadCompleteEvent event) {
        VariantLoader.loadActiveVariants();
    }

    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new VariantCommands());
    }
}
