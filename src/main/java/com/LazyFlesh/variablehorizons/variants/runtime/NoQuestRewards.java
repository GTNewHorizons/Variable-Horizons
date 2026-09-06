package com.LazyFlesh.variablehorizons.variants.runtime;

import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;
import com.gtnewhorizon.gtnhlib.config.ConfigurationManager;

import betterquesting.api.storage.BQ_Settings;

public class NoQuestRewards extends VariantLoader implements IRuntimeVariant {

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        VariantNames.NO_QUEST_REWARDS.hasLoaded = true;

        // disable rewards
        BQ_Settings.noRewards = true;
        ConfigurationManager.save(BQ_Settings.class);
    }

    @Override
    public void variantRecipes(VariantNames... activeVariants) {}

    @Override
    public void undoVariant(VariantNames... activeVariants) {
        VariantNames.NO_QUEST_REWARDS.hasLoaded = false;

        // reset and save to config
        BQ_Settings.noRewards = false;
        ConfigurationManager.save(BQ_Settings.class);
    }
}
