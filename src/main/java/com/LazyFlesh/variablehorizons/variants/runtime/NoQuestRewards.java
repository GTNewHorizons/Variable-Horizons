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
}
