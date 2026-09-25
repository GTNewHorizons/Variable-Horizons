package com.LazyFlesh.variablehorizons.variants.runtime;

import java.util.IdentityHashMap;
import java.util.Map;
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
import gregtech.api.util.GTUtility;
import toast.specialMobs.entity.ISpecialMob;
import toast.specialMobs.entity.SpecialMobData;
import toast.specialMobs.entity.creeper.Entity_SpecialCreeper;

public class Chaos extends VariantLoader implements IRuntimeVariant {

    private static final Map<ItemStack[], int[]> originalItemAmounts = new IdentityHashMap<>();
    private static final Map<FluidStack[], int[]> originalFluidAmounts = new IdentityHashMap<>();
    private static final Map<ItemStack[], int[]> originalAsslineItemAmounts = new IdentityHashMap<>();
    private static final Map<FluidStack[], int[]> originalAsslineFluidAmounts = new IdentityHashMap<>();

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
        // Do the work
        for (Map.Entry<String, RecipeMap<?>> entry : RecipeMap.ALL_RECIPE_MAPS.entrySet()) {
            for (GTRecipe recipe : entry.getValue()
                .getAllRecipes()) {
                if (recipe.mInputs.length > 0) {
                    scaleItems(
                        recipe.mInputs,
                        multiplier,
                        worldSeed + randomUtil.persistentRecipeSeed(recipe, 88375192837465L),
                        false);
                }
                if (recipe.mOutputs.length > 0) {
                    scaleItems(
                        recipe.mOutputs,
                        multiplier,
                        worldSeed - randomUtil.persistentRecipeSeed(recipe, 88375192837465L),
                        false);
                }
                if (recipe.mFluidInputs.length > 0) {
                    scaleFluids(
                        recipe.mFluidInputs,
                        multiplier,
                        worldSeed + randomUtil.persistentRecipeSeed(recipe, 76592769028753L),
                        false);
                }
                if (recipe.mFluidOutputs.length > 0) {
                    scaleFluids(
                        recipe.mFluidOutputs,
                        multiplier,
                        worldSeed - randomUtil.persistentRecipeSeed(recipe, 76592769028753L),
                        false);
                }
            }
        }

        for (GTRecipe.RecipeAssemblyLine recipe : GTRecipe.RecipeAssemblyLine.sAssemblylineRecipes) {
            if (recipe.mInputs.length > 0) {
                scaleItems(recipe.mInputs, multiplier, worldSeed + recipe.getPersistentHash(), true);
            }
            scaleItems(new ItemStack[] { recipe.mOutput }, multiplier, worldSeed + recipe.getPersistentHash(), true);
            if (recipe.mFluidInputs.length > 0) {
                scaleFluids(recipe.mFluidInputs, multiplier, worldSeed + recipe.getPersistentHash(), true);
            }
        }
    }

    private static void scaleItems(ItemStack[] stacks, float multiplier, long seed, boolean assline) {
        int[] originals;
        if (assline) {
            originals = originalAsslineItemAmounts.computeIfAbsent(stacks, Chaos::snapshotItems);
        } else {
            originals = originalItemAmounts.computeIfAbsent(stacks, Chaos::snapshotItems);
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

    private static void scaleFluids(FluidStack[] stacks, float multiplier, long seed, boolean assline) {
        int[] originals;
        if (assline) {
            originals = originalAsslineFluidAmounts.computeIfAbsent(stacks, Chaos::snapshotFluids);
        } else {
            originals = originalFluidAmounts.computeIfAbsent(stacks, Chaos::snapshotFluids);
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
