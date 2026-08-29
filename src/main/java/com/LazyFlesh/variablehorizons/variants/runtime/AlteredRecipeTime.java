package com.LazyFlesh.variablehorizons.variants.runtime;

import java.util.Map;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTRecipe;

public class AlteredRecipeTime extends VariantLoader implements IRuntimeVariant {

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        VariantNames.ALTERED_RECIPE_TIME.hasLoaded = true;
    }

    public boolean modifiedRecipeTimes = false;
    private float cachedMultiplier = 1;

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (!modifiedRecipeTimes) {
            modifiedRecipeTimes = true;
            modifyRecipesDuration(GeneralConfig.recipeTimeMultiplier);
        }
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (!modifiedRecipeTimes) {
            modifiedRecipeTimes = true;
            modifyRecipesDuration(GeneralConfig.recipeTimeMultiplier);
        }
    }

    @SubscribeEvent
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        modifiedRecipeTimes = false;
        modifyRecipesDuration(1 / cachedMultiplier);
    }

    private void modifyRecipesDuration(float multiplier) {

        // Do the work
        for (Map.Entry<String, RecipeMap<?>> entry : RecipeMap.ALL_RECIPE_MAPS.entrySet()) {

            for (GTRecipe recipe : entry.getValue()
                .getAllRecipes()) {
                if (recipe.mDuration > 0) {
                    recipe.mDuration = (int) (recipe.mDuration * multiplier);
                }
            }
        }

        for (GTRecipe.RecipeAssemblyLine recipe : GTRecipe.RecipeAssemblyLine.sAssemblylineRecipes) {
            if (recipe.mDuration > 0) {
                recipe.mDuration = (int) (recipe.mDuration * multiplier);
            }
            if (recipe.mResearchTime > 0) {
                recipe.mResearchTime = (int) (recipe.mDuration * multiplier);
            }
        }

        cachedMultiplier = multiplier;
    }

    @Override
    public void variantRecipes(VariantNames... activeVariants) {
        // none to add
    }
}
