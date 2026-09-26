package com.LazyFlesh.variablehorizons.variants.runtime;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fluids.FluidStack;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.util.randomUtil;
import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTRecipeConstants;
import gregtech.api.util.GTUtility;
import toast.specialMobs.entity.ISpecialMob;
import toast.specialMobs.entity.SpecialMobData;
import toast.specialMobs.entity.creeper.Entity_SpecialCreeper;

public class Chaos extends VariantLoader implements IRuntimeVariant {

    private static final Map<GTRecipe, int[]> originalInputAmounts = new IdentityHashMap<>();
    private static final Map<GTRecipe, int[]> originalOutputAmounts = new IdentityHashMap<>();
    private static final Map<GTRecipe, int[]> originalFluidInputAmounts = new IdentityHashMap<>();
    private static final Map<GTRecipe, int[]> originalFluidOutputAmounts = new IdentityHashMap<>();
    private static final Map<GTRecipe.RecipeAssemblyLine, int[]> originalAsslineInputAmounts = new IdentityHashMap<>();
    private static final Map<GTRecipe.RecipeAssemblyLine, int[]> originalAsslineOutputAmounts = new IdentityHashMap<>();
    private static final Map<GTRecipe.RecipeAssemblyLine, int[]> originalAsslineFluidInputAmounts = new IdentityHashMap<>();

    private static boolean saved = false;

    private static void saveOriginalValues() {
        if (saved) {
            return;
        }
        for (Map.Entry<String, RecipeMap<?>> entry : RecipeMap.ALL_RECIPE_MAPS.entrySet()) {
            for (GTRecipe recipe : entry.getValue()
                .getAllRecipes()) {
                originalInputAmounts.put(recipe, snapshotItems(recipe.mInputs));
                originalOutputAmounts.put(recipe, snapshotItems(recipe.mOutputs));
                if (entry.getKey()
                    .equals("gt.recipe.assemble-condensate")) {
                    originalFluidInputAmounts.put(
                        recipe,
                        snapshotFluids(Objects.requireNonNull(recipe.getMetadata(GTRecipeConstants.CONDENSATE_INPUT))));
                } else {
                    originalFluidInputAmounts.put(recipe, snapshotFluids(recipe.mFluidInputs));
                }
                originalFluidOutputAmounts.put(recipe, snapshotFluids(recipe.mFluidOutputs));
            }
        }
        for (GTRecipe.RecipeAssemblyLine recipe : GTRecipe.RecipeAssemblyLine.sAssemblylineRecipes) {
            originalAsslineInputAmounts.put(recipe, snapshotItems(recipe.mInputs));
            originalAsslineOutputAmounts.put(recipe, snapshotItems(new ItemStack[] { recipe.mOutput }));
            originalAsslineFluidInputAmounts.put(recipe, snapshotFluids(recipe.mFluidInputs));
        }

        saved = true;
    }

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        VariantNames.CHAOS.hasLoaded = true;
        GeneralConfig.recipeTimeRandom = true;
        GeneralConfig.inputChanceRandom = true;
        GeneralConfig.outputChanceRandom = true;
    }

    @SubscribeEvent
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        if (!VariantNames.CHAOS.hasLoaded) {
            return;
        }
        modifyRecipeAmounts(
            1,
            event.player.getEntityWorld()
                .getSeed());
    }

    public static void applyToServer() {
        if (!VariantNames.CHAOS.hasLoaded) {
            return;
        }
        MinecraftServer server = MinecraftServer.getServer();
        if (server == null) {
            return;
        }
        long seed = server.getEntityWorld()
            .getSeed();
        modifyRecipeAmounts(1, seed);
    }

    private static void modifyRecipeAmounts(float multiplier, long worldSeed) {
        saveOriginalValues();

        for (Map.Entry<String, RecipeMap<?>> entry : RecipeMap.ALL_RECIPE_MAPS.entrySet()) {
            for (GTRecipe recipe : entry.getValue()
                .getAllRecipes()) {
                scaleItems(
                    recipe.mInputs,
                    originalInputAmounts.get(recipe),
                    multiplier,
                    worldSeed + randomUtil.persistentRecipeSeed(recipe, 88375192837465L));
                scaleItems(
                    recipe.mOutputs,
                    originalOutputAmounts.get(recipe),
                    multiplier,
                    worldSeed - randomUtil.persistentRecipeSeed(recipe, 88375192837465L));
                if (entry.getKey()
                    .equals("gt.recipe.assemble-condensate")) {
                    scaleFluids(
                        recipe.getMetadata(GTRecipeConstants.CONDENSATE_INPUT),
                        originalFluidInputAmounts.get(recipe),
                        multiplier,
                        worldSeed + randomUtil.persistentRecipeSeed(recipe, 76592769028753L));
                } else {
                    scaleFluids(
                        recipe.mFluidInputs,
                        originalFluidInputAmounts.get(recipe),
                        multiplier,
                        worldSeed + randomUtil.persistentRecipeSeed(recipe, 76592769028753L));
                }
                scaleFluids(
                    recipe.mFluidOutputs,
                    originalFluidOutputAmounts.get(recipe),
                    multiplier,
                    worldSeed - randomUtil.persistentRecipeSeed(recipe, 76592769028753L));
            }
        }

        for (GTRecipe.RecipeAssemblyLine recipe : GTRecipe.RecipeAssemblyLine.sAssemblylineRecipes) {
            scaleItems(
                recipe.mInputs,
                originalAsslineInputAmounts.get(recipe),
                multiplier,
                worldSeed + recipe.getPersistentHash());
            scaleItems(
                new ItemStack[] { recipe.mOutput },
                originalAsslineOutputAmounts.get(recipe),
                multiplier,
                worldSeed - recipe.getPersistentHash());
            scaleFluids(
                recipe.mFluidInputs,
                originalAsslineFluidInputAmounts.get(recipe),
                multiplier,
                worldSeed + recipe.getPersistentHash() + 1);
        }
    }

    private static void scaleItems(ItemStack[] stacks, int[] originals, float multiplier, long seed) {
        if (originals == null) {
            return;
        }
        Random randomizer = new Random(seed);
        for (int i = 0; i < stacks.length; i++) {
            ItemStack stack = stacks[i];
            if (stack == null || originals[i] == 0) {
                continue;
            }
            long scaled = Math
                .round(originals[i] * (VariantNames.CHAOS.hasLoaded ? getRandomizedFactor(randomizer) : multiplier));
            stack.stackSize = (int) GTUtility.clamp(scaled, 1, Integer.MAX_VALUE);
        }
    }

    private static void scaleFluids(FluidStack[] stacks, int[] originals, float multiplier, long seed) {
        if (originals == null) {
            return;
        }
        Random randomizer = new Random(seed);
        for (int i = 0; i < stacks.length; i++) {
            FluidStack stack = stacks[i];
            if (stack == null || originals[i] == 0) {
                continue;
            }
            long scaled = Math
                .round(originals[i] * (VariantNames.CHAOS.hasLoaded ? getRandomizedFactor(randomizer) : multiplier));
            stack.amount = (int) GTUtility.clamp(scaled, 1, Integer.MAX_VALUE);
        }
    }

    private static int[] snapshotItems(ItemStack[] stacks) {
        int[] amounts = new int[stacks.length];
        for (int i = 0; i < stacks.length; i++) {
            amounts[i] = stacks[i] != null ? stacks[i].stackSize : 0;
        }
        return amounts;
    }

    private static int[] snapshotFluids(FluidStack[] stacks) {
        int[] amounts = new int[stacks.length];
        for (int i = 0; i < stacks.length; i++) {
            amounts[i] = stacks[i] != null ? stacks[i].amount : 0;
        }
        return amounts;
    }

    private static float getRandomizedFactor(Random rand) {
        float factor = 1 - rand.nextFloat();
        boolean multiply = rand.nextBoolean();
        return multiply ? GeneralConfig.recipeInOutRandomBounds * factor
            : 1 / (GeneralConfig.recipeInOutRandomBounds * factor);
    }

    @Override
    public void variantRecipes(VariantNames... activeVariants) {
        // none to add
    }

    @SubscribeEvent
    public void onEntityUpdate(LivingEvent.LivingUpdateEvent event) {
        if (!VariantNames.CHAOS.hasLoaded) {
            return;
        }

        EntityLivingBase entity = event.entityLiving;
        if (entity.worldObj.isRemote) {
            return;
        }

        if (entity.ticksExisted == 2) {
            NBTTagCompound entityData = entity.getEntityData();
            if (!entityData.getBoolean("ChaosModified")) {
                Random statRandomizer = new Random();
                IAttributeInstance maxHealth = entity.getEntityAttribute(SharedMonsterAttributes.maxHealth);
                if (maxHealth != null) {
                    float factor = 1 - statRandomizer.nextFloat();
                    float factor2 = (1 - statRandomizer.nextFloat()) * 100;
                    float healthModifier = statRandomizer.nextBoolean() ? factor2 * factor : 1 / (factor2 * factor);
                    double newMaxHealth = maxHealth.getBaseValue() * healthModifier;
                    maxHealth.setBaseValue(newMaxHealth);
                    entity.setHealth((float) newMaxHealth);
                }
                IAttributeInstance damage = entity.getEntityAttribute(SharedMonsterAttributes.attackDamage);
                if (damage != null) {
                    float factor = 1 - statRandomizer.nextFloat();
                    float factor2 = (1 - statRandomizer.nextFloat()) * 100;
                    float damageModifier = statRandomizer.nextBoolean() ? factor2 * factor : 1 / (factor2 * factor);
                    double newDamage = damage.getBaseValue() * damageModifier;
                    damage.setBaseValue(newDamage);
                }
                IAttributeInstance speed = entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
                if (speed != null) {
                    float factor = 1 - statRandomizer.nextFloat();
                    float factor2 = (1 - statRandomizer.nextFloat()) * 3;
                    float speedModifier = statRandomizer.nextBoolean() ? factor2 * factor : 1 / (factor2 * factor);
                    double newSpeed = speed.getBaseValue() * speedModifier;
                    speed.setBaseValue(newSpeed);
                }
                if (entity instanceof ISpecialMob) {
                    SpecialMobData specialData = ((ISpecialMob) entity).getSpecialData();
                    if (specialData != null) {
                        float factor = 1 - statRandomizer.nextFloat();
                        float factor2 = (1 - statRandomizer.nextFloat()) * 100;
                        float arrowDamageModifier = statRandomizer.nextBoolean() ? factor2 * factor
                            : 1 / (factor2 * factor);
                        specialData.arrowDamage *= arrowDamageModifier;
                    }
                    if (entity instanceof Entity_SpecialCreeper) {
                        float factor = 1 - statRandomizer.nextFloat();
                        float factor2 = (1 - statRandomizer.nextFloat()) * 5;
                        float explosionModifier = statRandomizer.nextBoolean() ? factor2 * factor
                            : 1 / (factor2 * factor);
                        ((Entity_SpecialCreeper) entity).explosionRadius = (int) (((Entity_SpecialCreeper) entity).explosionRadius
                            * explosionModifier);
                    }
                }
                entityData.setBoolean("ChaosModified", true);
            }
        }
    }

    @Override
    public void undoVariant(VariantNames... activeVariants) {
        VariantNames.CHAOS.hasLoaded = false;
        modifyRecipeAmounts(1, 0);
    }
}
