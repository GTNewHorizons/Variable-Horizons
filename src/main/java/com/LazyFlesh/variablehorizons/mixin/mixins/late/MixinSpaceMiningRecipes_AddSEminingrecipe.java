package com.LazyFlesh.variablehorizons.mixin.mixins.late;

import static gregtech.api.enums.Mods.ExtraUtilities;
import static gregtech.api.util.GTModHandler.getModItem;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gtnhintergalactic.item.ItemMiningDrones;

@Mixin(gtnhintergalactic.recipe.SpaceMiningRecipes.class)
public class MixinSpaceMiningRecipes_AddSEminingrecipe {

    @Invoker(value = "addRecipesToDrones", remap = false)
    public static void invokeAddRecipesToDronesWithMaterials(String asteroidName, ItemStack[] aItemInputs,
        FluidStack[] aFluidInputs, int[] aChances, Materials[] ores, OrePrefixes orePrefixes, int minSize, int maxSize,
        int minDistance, int maxDistance, int computationRequiredPerSec, int minModuleTier, int duration, int EUt,
        int startDroneTier, int endDroneTier, int recipeWeight) {
        throw new AssertionError();
    }

    @Inject(
        method = "addAsteroids",
        at = @At(
            value = "INVOKE",
            target = "Lgtnhintergalactic/recipe/SpaceMiningRecipes$WeightedAsteroidList;<init>(Ljava/util/stream/Stream;)V",
            remap = false),
        remap = false)
    private static void gtih$addCustomAsteroids(CallbackInfo ci) {
        registerCustomAsteroids();
    }

    private static boolean customAsteroidsRegistered = false;

    private static void registerCustomAsteroids() {
        if (customAsteroidsRegistered) {
            return;
        }
        customAsteroidsRegistered = true;

        invokeAddRecipesToDronesWithMaterials(
            "DeepDarkAsteroid",
            new ItemStack[] { getModItem(ExtraUtilities.ID, "dark_portal", 1, 0) },
            null,
            new int[] { 8000, 8000, 8000 },
            new Materials[] { Materials.DarkIron, Materials.Forcicium, Materials.Forcillium },
            OrePrefixes.ore,
            1,
            1,
            50,
            100,
            100,
            3,
            100 * 20,
            (int) TierEU.RECIPE_UEV,
            ItemMiningDrones.DroneTiers.UIV.ordinal(),
            ItemMiningDrones.DroneTiers.UMV.ordinal(),
            5);
    }
}
