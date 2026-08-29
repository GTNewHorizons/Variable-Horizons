package com.LazyFlesh.variablehorizons.variants.runtime;

import java.util.Map;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTRecipe;

public class AlteredRecipeTime extends VariantLoader implements IRuntimeVariant {

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        VariantNames.ALTERED_RECIPE_TIME.hasLoaded = true;
    }

    private static float cachedMultiplier = 1;

    @SubscribeEvent
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        modifyRecipesDuration(GeneralConfig.recipeTimeMultiplier);
    }

    private static void modifyRecipesDuration(float multiplier) {
        // Do the work
        for (Map.Entry<String, RecipeMap<?>> entry : RecipeMap.ALL_RECIPE_MAPS.entrySet()) {

            for (GTRecipe recipe : entry.getValue()
                .getAllRecipes()) {
                if (recipe.mDuration > 0) {
                    recipe.mDuration = Math.max(1, (int) (recipe.mDuration * multiplier / cachedMultiplier));
                }
            }
        }

        for (GTRecipe.RecipeAssemblyLine recipe : GTRecipe.RecipeAssemblyLine.sAssemblylineRecipes) {
            if (recipe.mDuration > 0) {
                recipe.mDuration = Math.max(1, (int) (recipe.mDuration * multiplier / cachedMultiplier));
            }
            if (recipe.mResearchTime > 0) {
                recipe.mResearchTime = Math.max(1, (int) (recipe.mDuration * multiplier / cachedMultiplier));
            }
        }

        cachedMultiplier = multiplier;
    }

    @Override
    public void variantRecipes(VariantNames... activeVariants) {
        // none to add
    }

    public static class AlteredRecipeTimeCommand extends CommandBase {

        @Override
        public String getCommandName() {
            return "alterrecipetimes";
        }

        @Override
        public String getCommandUsage(ICommandSender sender) {
            return "/alterrecipetimes <multiplier>";
        }

        @Override
        public void processCommand(ICommandSender sender, String[] args) {
            if (args.length != 1) {
                sender.addChatMessage(new ChatComponentText("Usage: /alterrecipetimes <multiplier>"));
                return;
            }
            float multiplier = Float.parseFloat(args[0]);
            modifyRecipesDuration(multiplier);
            sender.addChatMessage(new ChatComponentText("Changes applied"));
        }

        @Override
        public int getRequiredPermissionLevel() {
            return 1;
        }
    }
}
