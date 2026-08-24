package com.LazyFlesh.variablehorizons.variants.runtime;

import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

public class NoRocket extends VariantLoader implements IRuntimeVariant {

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        VariantNames.NO_ROCKET.hasLoaded = true;
    }
}
