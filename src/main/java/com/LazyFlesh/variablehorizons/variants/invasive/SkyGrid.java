package com.LazyFlesh.variablehorizons.variants.invasive;

import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

public class SkyGrid extends VariantLoader {

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        VariantNames.SKYGRID.hasLoaded = true;
    }

    @Override
    public void variantRecipes(VariantNames... activeVariants) {
        // none I can think of, for now
    }
}
