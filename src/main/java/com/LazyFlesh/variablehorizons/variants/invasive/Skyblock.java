package com.LazyFlesh.variablehorizons.variants.invasive;

import static gregtech.api.enums.Mods.AppliedEnergistics2;
import static gregtech.api.recipe.RecipeMaps.centrifugeRecipes;
import static gregtech.api.recipe.RecipeMaps.multiblockRockBreakerRecipes;
import static gregtech.api.util.GTModHandler.getModItem;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeBuilder.TICKS;
import static gregtech.api.util.GTRecipeBuilder.WILDCARD;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import gregtech.GTMod;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Mods;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.common.config.Worldgen;
import gregtech.common.tileentities.machines.basic.MTERockBreaker;

public class Skyblock extends VariantLoader {

    @Override
    public void loadVariant(VariantNames... activeVariants) {

        // the hodgepodge mixins have been ported here, because early mixins are hard to toggle config on.
        // disable asteroids in end
        Worldgen.endAsteroids.generateEndAsteroids = false;
        // disable entity cramming (makes mob farms behave better)
        GTMod.proxy.mMaxEqualEntitiesAtOneSpot = -1;
    }

    @Override
    public void variantRecipes(VariantNames... activeVariants) {

        { // custom ore generation recipes
            GTValues.RA.stdBuilder()
                .itemInputs(new ItemStack(Blocks.gravel, 1, WILDCARD))
                .itemOutputs(
                    GTOreDictUnificator.get(OrePrefixes.crushed, Materials.YellowLimonite, 1),
                    GTOreDictUnificator.get(OrePrefixes.crushed, Materials.Tetrahedrite, 1),
                    GTOreDictUnificator.get(OrePrefixes.dustImpure, Materials.CassiteriteSand, 1),
                    GTOreDictUnificator.get(OrePrefixes.crushed, Materials.Redstone, 1),
                    GTOreDictUnificator.get(OrePrefixes.crushed, Materials.Zinc, 1),
                    GTOreDictUnificator.get(OrePrefixes.dustImpure, Materials.Silver, 1))
                .outputChances(50_00, 40_00, 25_00, 15_00, 10_00, 5_00)
                .circuit(1)
                .duration(1 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(centrifugeRecipes);

            GTValues.RA.stdBuilder()
                .itemInputs(new ItemStack(Blocks.gravel, 1, WILDCARD))
                .itemOutputs(
                    GTOreDictUnificator.get(OrePrefixes.crushed, Materials.Magnetite, 1),
                    GTOreDictUnificator.get(OrePrefixes.crushed, Materials.Chalcopyrite, 1),
                    GTOreDictUnificator.get(OrePrefixes.dustImpure, Materials.Galena, 1),
                    GTOreDictUnificator.get(OrePrefixes.crushed, Materials.Coal, 1),
                    GTOreDictUnificator.get(OrePrefixes.crushed, Materials.Diamond, 1),
                    GTOreDictUnificator.get(OrePrefixes.dustImpure, Materials.VanadiumMagnetite, 1))
                .outputChances(50_00, 40_00, 25_00, 20_00, 10_00, 10_00)
                .circuit(2)
                .duration(1 * SECONDS)
                .eut(TierEU.RECIPE_MV / 2)
                .addTo(centrifugeRecipes);

            GTValues.RA.stdBuilder()
                .itemInputs(new ItemStack(Blocks.gravel, 1, WILDCARD))
                .itemOutputs(
                    GTOreDictUnificator.get(OrePrefixes.crushed, Materials.Aluminium, 1),
                    GTOreDictUnificator.get(OrePrefixes.crushed, Materials.Galena, 1),
                    GTOreDictUnificator.get(OrePrefixes.crushed, Materials.Sphalerite, 1),
                    GTOreDictUnificator.get(OrePrefixes.crushed, Materials.Bauxite, 1),
                    GTOreDictUnificator.get(OrePrefixes.crushed, Materials.Ilmenite, 1),
                    GTOreDictUnificator.get(OrePrefixes.dustImpure, Materials.Neodymium, 1))
                .outputChances(50_00, 40_00, 25_00, 25_00, 5_00, 5_00)
                .circuit(3)
                .duration(50 * TICKS)
                .eut(TierEU.RECIPE_HV)
                .addTo(centrifugeRecipes);

            GTValues.RA.stdBuilder()
                .itemInputs(new ItemStack(Blocks.sand, 1, WILDCARD))
                .itemOutputs(
                    GTOreDictUnificator.get(OrePrefixes.dustImpure, Materials.CassiteriteSand, 1),
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Sulfur, 1),
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.GarnetSand, 1),
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Silver, 1),
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.GraniticMineralSand, 1),
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.BasalticMineralSand, 1))
                .outputChances(50_00, 40_00, 25_00, 15_00, 10_00, 10_00)
                .circuit(1)
                .duration(1 * SECONDS)
                .eut(TierEU.RECIPE_LV)
                .addTo(centrifugeRecipes);

            GTValues.RA.stdBuilder()
                .itemInputs(new ItemStack(Blocks.sand, 1, WILDCARD))
                .itemOutputs(
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.CertusQuartz, 1),
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Spessartine, 1),
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Electrotine, 1),
                    GTOreDictUnificator.get(OrePrefixes.dust, Materials.Graphite, 1),
                    GTOreDictUnificator.get(OrePrefixes.dustImpure, Materials.Asbestos, 1),
                    GTOreDictUnificator.get(OrePrefixes.dustImpure, Materials.Mica, 1))
                .outputChances(50_00, 30_00, 25_00, 25_00, 5_00, 5_00)
                .circuit(2)
                .duration(1 * SECONDS)
                .eut(TierEU.RECIPE_MV)
                .addTo(centrifugeRecipes);

            if (Mods.AppliedEnergistics2.isModLoaded()) {
                GTValues.RA.stdBuilder()
                    .itemInputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.CertusQuartz, 1L))
                    .circuit(6)
                    .itemOutputs(getModItem(AppliedEnergistics2.ID, "tile.BlockSkyStone", 1L))
                    .duration(16 * TICKS)
                    .eut(TierEU.RECIPE_LV)
                    .addTo(multiblockRockBreakerRecipes);

                MTERockBreaker.addRockBreakerRecipe(
                    b -> b.sideBlocks(Blocks.water)
                        .anywhereBlocks(Blocks.lava)
                        .inputItem(GTOreDictUnificator.get(OrePrefixes.dust, Materials.CertusQuartz, 1L), true)
                        .circuit(6)
                        .outputItem(getModItem(AppliedEnergistics2.ID, "tile.BlockSkyStone", 1L))
                        .duration(16 * TICKS));

            }
        }
    }
}
