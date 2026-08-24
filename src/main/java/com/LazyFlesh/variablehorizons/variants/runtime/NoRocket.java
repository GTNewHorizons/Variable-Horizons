package com.LazyFlesh.variablehorizons.variants.runtime;

import net.minecraft.item.ItemStack;

import com.LazyFlesh.variablehorizons.util.RecipeRemover;
import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import micdoodle8.mods.galacticraft.core.blocks.GCBlocks;

public class NoRocket extends VariantLoader implements IRuntimeVariant {

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        VariantNames.NO_ROCKET.hasLoaded = true;

        // Remove nasa bench recipe. Easist way to stop rocketing.
        RecipeRemover.recipesToRemoveByItem.add(new ItemStack(GCBlocks.nasaWorkbench));
    }

    @Override
    public void variantRecipes(VariantNames... activeVariants) {

    }
}
