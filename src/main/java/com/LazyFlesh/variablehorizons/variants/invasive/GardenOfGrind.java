package com.LazyFlesh.variablehorizons.variants.invasive;

import static gregtech.api.recipe.RecipeMaps.autoclaveRecipes;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;

import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import gregtech.GTMod;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.common.config.Worldgen;

public class GardenOfGrind extends VariantLoader {

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        // gog doesn't need anything loaded (early mixins load on restart)
        VariantNames.GARDEN_OF_GRIND.hasLoaded = true;

        // but it does need to overwrite some things
        // the hodgepodge mixins have been ported here, because early mixins are hard to toggle config on.
        Worldgen.endAsteroids.generateEndAsteroids = false;
        // disable entity cramming (makes mob farms behave better)
        GTMod.proxy.mMaxEqualEntitiesAtOneSpot = -1;
    }

    @Override
    public void variantRecipes(VariantNames... activeVariants) {
        GTValues.RA.stdBuilder()
            .itemInputs(GTOreDictUnificator.get(OrePrefixes.dust, Materials.Olivine, 15))
            .itemOutputs(
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Asbestos, 6),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Magnetite, 2))
            .fluidInputs(GTModHandler.getDistilledWater(9_000))
            .fluidOutputs(Materials.Hydrogen.getGas(4_000))
            .duration(60 * SECONDS)
            .eut(TierEU.RECIPE_MV)
            .addTo(autoclaveRecipes);
        // maybe chaos shard, but there's a command to turn on chaos islands back on when you get around to it.
    }
}
