package com.LazyFlesh.variablehorizons.variants.runtime;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTUtility;

public class ChancedRecipes extends VariantLoader implements IRuntimeVariant {

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        VariantNames.CHANCED_RECIPES.hasLoaded = true;
    }

    private static final Map<GTRecipe, int[]> originalRecipeOutputChances = new HashMap<>();
    private static final Map<GTRecipe, int[]> originalRecipeInputChances = new HashMap<>();
    private static final Map<GTRecipe, int[]> originalRecipeFluidOutputChances = new HashMap<>();
    private static final Map<GTRecipe, int[]> originalRecipeFluidInputChances = new HashMap<>();

    @SubscribeEvent
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        if (!VariantNames.CHANCED_RECIPES.hasLoaded) {
            return;
        }
        long seed = event.player.getEntityWorld()
            .getSeed();
        ChancedRecipes.normalizeChanceArrays();
        modifyRecipeChances(
            GeneralConfig.inputChanceMultiplier,
            GeneralConfig.outputChanceMultiplier,
            GeneralConfig.fluidInputChanceMultiplier,
            GeneralConfig.fluidOutputChanceMultiplier,
            GeneralConfig.inputChanceRandom,
            GeneralConfig.outputChanceRandom,
            seed);
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
                if (recipe.mFluidOutputChances == null) {
                    recipe.mFluidOutputChances = fullChanceArray(recipe.mFluidOutputs.length);
                }
                if (recipe.mFluidInputChances == null) {
                    recipe.mFluidInputChances = fullChanceArray(recipe.mFluidInputs.length);
                }
            }
        }
    }

    private static void modifyRecipeChances(float inputChanceMultiplier, float outputChanceMultiplier,
        float fluidInputChanceMultiplier, float fluidOutputChanceMultiplier, boolean inputChanceRandom,
        boolean outputChanceRandom, long worldSeed) {
        for (Map.Entry<String, RecipeMap<?>> entry : RecipeMap.ALL_RECIPE_MAPS.entrySet()) {
            for (GTRecipe recipe : entry.getValue()
                .getAllRecipes()) {

                int[] originalOutputChance = originalRecipeOutputChances
                    .computeIfAbsent(recipe, r -> r.mOutputChances.clone());
                long recipeSeed = persistentRecipeSeed(recipe);
                Random outputRandom = new Random(worldSeed + recipeSeed);
                Random inputRandom = new Random(worldSeed - recipeSeed);
                for (int i = 0; i < recipe.mOutputChances.length; i++) {
                    recipe.mOutputChances[i] = scale(
                        originalOutputChance[i],
                        outputChanceRandom ? 1 - outputRandom.nextFloat() : outputChanceMultiplier);
                }

                int[] originalInputChance = originalRecipeInputChances
                    .computeIfAbsent(recipe, r -> r.mInputChances.clone());
                for (int i = 0; i < recipe.mInputChances.length; i++) {
                    recipe.mInputChances[i] = scale(
                        originalInputChance[i],
                        inputChanceRandom ? 1 - inputRandom.nextFloat() : inputChanceMultiplier);
                }

                int[] originalFluidOutputChance = originalRecipeFluidOutputChances
                    .computeIfAbsent(recipe, r -> r.mFluidOutputChances.clone());
                for (int i = 0; i < recipe.mFluidOutputChances.length; i++) {
                    recipe.mFluidOutputChances[i] = scale(
                        originalFluidOutputChance[i],
                        outputChanceRandom ? 1 - outputRandom.nextFloat() : fluidOutputChanceMultiplier);
                }

                int[] originalFluidInputChance = originalRecipeFluidInputChances
                    .computeIfAbsent(recipe, r -> r.mFluidInputChances.clone());
                for (int i = 0; i < recipe.mFluidInputChances.length; i++) {
                    recipe.mFluidInputChances[i] = scale(
                        originalFluidInputChance[i],
                        inputChanceRandom ? 1 - inputRandom.nextFloat() : fluidInputChanceMultiplier);
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

    private static long persistentRecipeSeed(GTRecipe recipe) {
        long recipeSeed = 11258999068425L;
        for (ItemStack stack : recipe.mInputs) {
            recipeSeed = 31 * recipeSeed + GTUtility.persistentHash(stack, true, false);
        }
        for (ItemStack stack : recipe.mOutputs) {
            recipeSeed = 31 * recipeSeed + GTUtility.persistentHash(stack, true, false);
        }
        for (FluidStack fluid : recipe.mFluidInputs) {
            recipeSeed = 31 * recipeSeed + GTUtility.persistentHash(fluid, true, false);
        }
        for (FluidStack fluid : recipe.mFluidOutputs) {
            recipeSeed = 31 * recipeSeed + GTUtility.persistentHash(fluid, true, false);
        }
        return recipeSeed;
    }

    @Override
    public void variantRecipes(VariantNames... activeVariants) {
        // none to add
    }

    @Override
    public void undoVariant(VariantNames... activeVariants) {
        VariantNames.CHANCED_RECIPES.hasLoaded = false;

        ChancedRecipes.normalizeChanceArrays();
        modifyRecipeChances(1, 1, 1, 1, false, false, 0);
    }
}
