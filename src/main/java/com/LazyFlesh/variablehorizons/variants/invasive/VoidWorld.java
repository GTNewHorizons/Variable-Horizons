package com.LazyFlesh.variablehorizons.variants.invasive;

import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import gregtech.common.config.Gregtech;

public class VoidWorld extends VariantLoader {

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        VariantNames.VOID_WORLD.hasLoaded = true;
        Gregtech.general.oreveinPercentage = 0;
    }

    @Override
    public void variantRecipes(VariantNames... activeVariants) {
        // none
    }
}
