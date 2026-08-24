package com.LazyFlesh.variablehorizons.variants.runtime;

import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import betterquesting.api.storage.BQ_Settings;

public class NoQuestRewards extends VariantLoader implements IRuntimeVariant {

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        VariantNames.NO_QUEST_REWARDS.hasLoaded = true;

        // disable rewards, but *don't* save to config, otherwise have to undo when Variant is disabled.
        BQ_Settings.noRewards = true;
    }

    @Override
    public void variantRecipes(VariantNames... activeVariants) {
        // nothing to add. Maybe a bee princess, if the vending machine is ever inaccessible.
        // currently you can get to the Convocation of the Damned before you need bees, and get bee coins from there
    }
}
