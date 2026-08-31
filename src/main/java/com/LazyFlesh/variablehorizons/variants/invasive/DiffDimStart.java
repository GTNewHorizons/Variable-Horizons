package com.LazyFlesh.variablehorizons.variants.invasive;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import serverutils.ServerUtilitiesConfig;

public class DiffDimStart extends VariantLoader {

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        VariantNames.CUSTOM_DIM_START.hasLoaded = true;

        // disable /spawn cuz it tp's you to OW
        if (GeneralConfig.startingDimID != 0) ServerUtilitiesConfig.commands.spawn = false;
    }

    @Override
    public void variantRecipes(VariantNames... activeVariants) {
        // none to add
    }
}
