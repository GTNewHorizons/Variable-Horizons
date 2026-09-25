package com.LazyFlesh.variablehorizons.variants.runtime;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.util.randomUtil;
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
        if (!VariantNames.ALTERED_RECIPE_TIME.hasLoaded) {
            return;
        }
        modifyRecipesDuration(
            GeneralConfig.recipeTimeMultiplier,
            event.player.getEntityWorld()
                .getSeed(),
            GeneralConfig.recipeTimeRandom);
    }

    public static void applyToServer() {
        if (!VariantNames.ALTERED_RECIPE_TIME.hasLoaded) {
            return;
        }
        MinecraftServer server = MinecraftServer.getServer();
        if (server == null) {
            return;
        }
        long seed = server.getEntityWorld()
            .getSeed();
        modifyRecipesDuration(GeneralConfig.recipeTimeMultiplier, seed, GeneralConfig.recipeTimeRandom);
    }

    private static void modifyRecipesDuration(float multiplier, long worldSeed, boolean randomize) {
        // Do the work
        for (Map.Entry<String, RecipeMap<?>> entry : RecipeMap.ALL_RECIPE_MAPS.entrySet()) {
            for (GTRecipe recipe : entry.getValue()
                .getAllRecipes()) {
                if (recipe.mDuration > 0) {
                    int original = originalRecipeTimes.computeIfAbsent(recipe, r -> r.mDuration);
                    recipe.mDuration = scale(
                        original,
                        multiplier,
                        worldSeed + randomUtil.persistentRecipeSeed(recipe, 25258927368899L),
                        randomize);
                }
            }
        }

        for (GTRecipe.RecipeAssemblyLine recipe : GTRecipe.RecipeAssemblyLine.sAssemblylineRecipes) {
            if (recipe.mDuration > 0) {
                int originalDuration = originalAsslineRecipeTimes.computeIfAbsent(recipe, r -> r.mDuration);
                recipe.mDuration = scale(
                    originalDuration,
                    multiplier,
                    worldSeed + recipe.getPersistentHash(),
                    randomize);
            }
            if (recipe.mResearchTime > 0) {
                int originalResearch = originalResearchTimes.computeIfAbsent(recipe, r -> r.mResearchTime);
                recipe.mResearchTime = scale(
                    originalResearch,
                    multiplier,
                    worldSeed + recipe.getPersistentHash(),
                    randomize);
            }
        }
    }

    private static int scale(int original, float multiplier, long seed, boolean randomize) {
        if (randomize) {
            Random factorRandom = new Random(seed);
            float factor = 1 - factorRandom.nextFloat();
            boolean multiply = factorRandom.nextBoolean();
            multiplier = multiply ? GeneralConfig.recipeTimeRandomBounds * factor
                : 1 / (GeneralConfig.recipeTimeRandomBounds * factor);
        }
        long result = Math.round(original * (double) multiplier);
        return (int) Math.max(1, Math.min(Integer.MAX_VALUE, result));
    }

    @Override
    public void variantRecipes(VariantNames... activeVariants) {
        // none to add
    }

    @Override
    public void undoVariant(VariantNames... activeVariants) {
        VariantNames.ALTERED_RECIPE_TIME.hasLoaded = false;
        modifyRecipesDuration(1, 0, false);
    }

    public static class AlteredRecipeTimeCommand extends CommandBase {

        @Override
        public String getCommandName() {
            return "alterrecipetimes";
        }

        @Override
        public String getCommandUsage(ICommandSender sender) {
            return "/alterrecipetimes <multiplier|randomize> <true|false>";
        }

        @Override
        public void processCommand(ICommandSender sender, String[] args) {
            if (args.length < 1 || args.length > 2) {
                sender.addChatMessage(
                    new ChatComponentText("Usage: /alterrecipetimes <multiplier|randomize> <true|false>"));
                return;
            }
            float multiplier = 1;
            boolean badInput = false;
            if (args.length == 1) {
                if (args[0].trim()
                    .equalsIgnoreCase("randomize")) {
                    GeneralConfig.recipeTimeRandom = !GeneralConfig.recipeTimeRandom;
                } else {
                    try {
                        multiplier = Float.parseFloat(args[0]);
                    } catch (NumberFormatException exception) {
                        badInput = true;
                    }
                }
            } else {
                if (args[0].trim()
                    .equalsIgnoreCase("randomize")) {
                    switch (args[1].trim()
                        .toLowerCase()) {
                        case "active", "true", "1", "t" -> {
                            GeneralConfig.recipeTimeRandom = true;
                        }
                        case "inactive", "false", "0", "f" -> {
                            GeneralConfig.recipeTimeRandom = false;
                        }
                        default -> {
                            badInput = true;
                        }
                    }
                } else {
                    badInput = true;
                }
            }
            if (badInput) {
                sender.addChatMessage(
                    new ChatComponentText(
                        "Wrong arguments, use a multiplier or 'randomize' (+ true/false) to modify recipe times"));
                return;
            }
            modifyRecipesDuration(
                multiplier,
                sender.getEntityWorld()
                    .getSeed(),
                GeneralConfig.recipeTimeRandom);
            GeneralConfig.recipeTimeMultiplier = multiplier;
            ConfigurationManager.save(GeneralConfig.class);
            sender.addChatMessage(new ChatComponentText("Changes applied"));
        }

        @Override
        public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
            if (args.length == 1) {
                return getListOfStringsMatchingLastWord(args, "randomize", "1");
            } else if (args.length == 2) {
                return getListOfStringsMatchingLastWord(args, "true", "false");
            }
            return Collections.emptyList();
        }

        @Override
        public int getRequiredPermissionLevel() {
            return 1;
        }
    }
}
