package com.LazyFlesh.variablehorizons.variants.runtime;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;
import com.gtnewhorizon.gtnhlib.config.ConfigurationManager;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTRecipe;

public class ChancedRecipes extends VariantLoader implements IRuntimeVariant {

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        VariantNames.CHANCED_RECIPES.hasLoaded = true;
    }

    private static final Map<GTRecipe, int[]> originalRecipeOutputChances = new HashMap<>();
    private static final Map<GTRecipe, int[]> originalRecipeInputChances = new HashMap<>();

    @SubscribeEvent
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        ChancedRecipes.normalizeChanceArrays();
        modifyRecipeChances(GeneralConfig.inputChanceMultiplier, GeneralConfig.outputChanceMultiplier);
    }

    public static void normalizeChanceArrays() {
        for (Map.Entry<String, RecipeMap<?>> entry : RecipeMap.ALL_RECIPE_MAPS.entrySet()) {
            for (GTRecipe recipe : entry.getValue()
                .getAllRecipes()) {
                if (recipe.mOutputChances == null) {
                    recipe.mOutputChances = fullChanceArray(recipe.mOutputs.length);
                }
                if (recipe.mInputChances == null) {
                    recipe.mInputChances = fullChanceArray(recipe.mInputs.length);
                }
            }
        }
    }

    private static void modifyRecipeChances(float inputChanceMultiplier, float outputChanceMultiplier) {
        for (Map.Entry<String, RecipeMap<?>> entry : RecipeMap.ALL_RECIPE_MAPS.entrySet()) {
            for (GTRecipe recipe : entry.getValue()
                .getAllRecipes()) {

                int[] originalOutputChance = originalRecipeOutputChances
                    .computeIfAbsent(recipe, r -> r.mOutputChances.clone());
                for (int i = 0; i < recipe.mOutputChances.length; i++) {
                    recipe.mOutputChances[i] = scale(originalOutputChance[i], outputChanceMultiplier);
                }

                int[] originalInputChance = originalRecipeInputChances
                    .computeIfAbsent(recipe, r -> r.mInputChances.clone());
                for (int i = 0; i < recipe.mInputChances.length; i++) {
                    recipe.mInputChances[i] = scale(originalInputChance[i], inputChanceMultiplier);
                }
            }
        }
    }

    private static int[] fullChanceArray(int length) {
        int[] chances = new int[length];
        Arrays.fill(chances, 10000);
        return chances;
    }

    private static int scale(int original, float multiplier) {
        long result = Math.round(original * (double) multiplier);
        return (int) Math.min(10000, result);
    }

    @Override
    public void variantRecipes(VariantNames... activeVariants) {
        // none to add
    }

    @Override
    public void undoVariant(VariantNames... activeVariants) {
        VariantNames.CHANCED_RECIPES.hasLoaded = false;

        GeneralConfig.inputChanceMultiplier = 1;
        GeneralConfig.outputChanceMultiplier = 1;
        ConfigurationManager.save(GeneralConfig.class);
    }
}
