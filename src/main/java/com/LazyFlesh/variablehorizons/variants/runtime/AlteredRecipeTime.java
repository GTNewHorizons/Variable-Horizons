package com.LazyFlesh.variablehorizons.variants.runtime;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;
import com.gtnewhorizon.gtnhlib.config.ConfigurationManager;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTRecipe;

public class AlteredRecipeTime extends VariantLoader implements IRuntimeVariant {

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        VariantNames.ALTERED_RECIPE_TIME.hasLoaded = true;
    }

    private static final Map<GTRecipe, Integer> originalRecipeTimes = new HashMap<>();
    private static final Map<GTRecipe.RecipeAssemblyLine, Integer> originalAsslineRecipeTimes = new HashMap<>();
    private static final Map<GTRecipe.RecipeAssemblyLine, Integer> originalResearchTimes = new HashMap<>();

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
                    int original = originalRecipeTimes.computeIfAbsent(recipe, r -> r.mDuration);
                    recipe.mDuration = scale(original, multiplier);
                }
            }
        }

        for (GTRecipe.RecipeAssemblyLine recipe : GTRecipe.RecipeAssemblyLine.sAssemblylineRecipes) {
            if (recipe.mDuration > 0) {
                int originalDuration = originalAsslineRecipeTimes.computeIfAbsent(recipe, r -> r.mDuration);
                recipe.mDuration = scale(originalDuration, multiplier);
            }
            if (recipe.mResearchTime > 0) {
                int originalResearch = originalResearchTimes.computeIfAbsent(recipe, r -> r.mResearchTime);
                recipe.mResearchTime = scale(originalResearch, multiplier);
            }
        }
    }

    private static int scale(int original, float multiplier) {
        long result = Math.round(original * (double) multiplier);
        return (int) Math.max(1, Math.min(Integer.MAX_VALUE, result));
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
            GeneralConfig.recipeTimeMultiplier = multiplier;
            ConfigurationManager.save(GeneralConfig.class);
            sender.addChatMessage(new ChatComponentText("Changes applied"));
        }

        @Override
        public int getRequiredPermissionLevel() {
            return 1;
        }
    }
}
