package com.LazyFlesh.variablehorizons.variants.runtime;

import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import galaxyspace.core.recipe.RocketRecipes;
import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;

public class NoRocket extends VariantLoader implements IRuntimeVariant {

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        VariantNames.NO_ROCKET.hasLoaded = true;

        // remove rocket recipes
        RocketRecipes.getRocketT1Recipes()
            .clear();
        RocketRecipes.getRocketT2Recipes()
            .clear();
        RocketRecipes.getRocketT3Recipes()
            .clear();
        RocketRecipes.getRocketT4Recipes()
            .clear();
        RocketRecipes.getRocketT5Recipes()
            .clear();
        RocketRecipes.getRocketT6Recipes()
            .clear();
        RocketRecipes.getRocketT7Recipes()
            .clear();
        RocketRecipes.getRocketT8Recipes()
            .clear();

        GalacticraftRegistry.getRocketT1Recipes()
            .clear();
        GalacticraftRegistry.getRocketT2Recipes()
            .clear();
        GalacticraftRegistry.getRocketT3Recipes()
            .clear();
        GalacticraftRegistry.getCargoRocketRecipes()
            .clear();
    }

    @Override
    public void variantRecipes(VariantNames... activeVariants) {

    }
}
