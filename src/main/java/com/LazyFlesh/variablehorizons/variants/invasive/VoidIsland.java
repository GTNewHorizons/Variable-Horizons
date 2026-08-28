package com.LazyFlesh.variablehorizons.variants.invasive;

import static gregtech.api.enums.Mods.BiomesOPlenty;
import static gregtech.api.enums.Mods.DraconicEvolution;
import static gregtech.api.enums.Mods.GalacticraftCore;
import static gregtech.api.enums.Mods.HardcoreEnderExpansion;
import static gregtech.api.enums.Mods.Minecraft;
import static gregtech.api.enums.Mods.Thaumcraft;
import static gregtech.api.enums.Mods.TinkerConstruct;
import static gregtech.api.enums.Mods.UniversalSingularities;
import static gregtech.api.util.GTModHandler.getModItem;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeConstants.DEFC_CASING_TIER;
import static kubatech.loaders.DEFCRecipes.fusionCraftingRecipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ItemFluidContainer;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.util.islands.skyIslands;
import com.LazyFlesh.variablehorizons.util.randomUtil;
import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GTModHandler;

public class VoidIsland extends VariantLoader {

    private static ItemStack[] createChestOW() {
        return new ItemStack[] { new ItemStack(Items.lava_bucket), new ItemStack(Blocks.ice, 2),
            new ItemStack(Items.reeds), new ItemStack(Items.melon_seeds), new ItemStack(Items.pumpkin_seeds),
            new ItemStack(Blocks.cactus), new ItemStack(Items.flint, 5) };

    }

    private static ItemStack[] createChestTwilight() {
        return new ItemStack[] { new ItemStack(Items.lava_bucket),
            GTModHandler.getModItem(Minecraft.ID, "red_mushroom"),
            GTModHandler.getModItem(Minecraft.ID, "brown_mushroom"), new ItemStack(Items.reeds),
            new ItemStack(Blocks.cactus), new ItemStack(Items.flint, 5) };

    }

    private static ItemStack[] createChestNether() {
        ItemStack bopBucket = GTModHandler.getModItem(BiomesOPlenty.ID, "bopBucket");
        ((ItemFluidContainer) bopBucket.getItem())
            .fill(bopBucket, new FluidStack(FluidRegistry.getFluid("hell_blood"), 1000), true);
        return new ItemStack[] { bopBucket, bopBucket, new ItemStack(Items.reeds),
            GTModHandler.getModItem(Minecraft.ID, "red_mushroom"),
            GTModHandler.getModItem(Minecraft.ID, "brown_mushroom"), new ItemStack(Blocks.cactus),
            new ItemStack(Items.flint, 5) };
    }

    private static ItemStack[] createChestDD() {
        return new ItemStack[] { new ItemStack(Items.lava_bucket), new ItemStack(Blocks.ice, 2),
            GTModHandler.getModItem(Minecraft.ID, "red_mushroom"),
            GTModHandler.getModItem(Minecraft.ID, "brown_mushroom"), new ItemStack(Items.flint, 5) };
    }

    private static ItemStack[] createChestEnd() {
        return new ItemStack[] { new ItemStack(Items.lava_bucket, 1),
            GTModHandler.getModItem(TinkerConstruct.ID, "buckets", 1, 23),
            GTModHandler.getModItem(TinkerConstruct.ID, "buckets", 1, 23), new ItemStack(Items.reeds),
            GTModHandler.getModItem(HardcoreEnderExpansion.ID, "death_flower"),
            GTModHandler.getModItem(HardcoreEnderExpansion.ID, "spooky_leaves"), new ItemStack(Blocks.cactus),
            new ItemStack(Items.flint, 5) };
    }

    private static ItemStack[] createChestSpaceStation() {
        return new ItemStack[] { GTModHandler.getModItem(GalacticraftCore.ID, "item.oxygenTankLightFull"),
            GTModHandler.getModItem(GalacticraftCore.ID, "item.oxygenGear"),
            GTModHandler.getModItem(GalacticraftCore.ID, "item.oxygenMask"),
            GTModHandler.getModItem(GalacticraftCore.ID, "item.basicItem", 1, 20) };
    }

    private static ItemStack[] createChestThaum() {
        return new ItemStack[] { new ItemStack(Items.lava_bucket), new ItemStack(Blocks.ice, 2),
            new ItemStack(Items.reeds), GTModHandler.getModItem(Thaumcraft.ID, "blockCustomPlant", 2, 4),
            GTModHandler.getModItem(Thaumcraft.ID, "blockCustomPlant", 4, 5), new ItemStack(Blocks.cactus),
            new ItemStack(Items.flint, 5) };
    }

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        VariantNames.VOID_ISLAND.hasLoaded = true;
    }

    @Override
    public void variantRecipes(VariantNames... activeVariants) {
        // recipes for chaos shard if end is a void (the other dim resources are not block-dependent)
        // don't double up on recipe since if Dimlocked in a starting dim that is not the end, it adds the recipe too.
        if (randomUtil.generateVoidInThisDim(1)
            && (!VariantNames.activeContains(VariantNames.DIMLOCKED.id) && GeneralConfig.startingDimID == 1)) {
            GTValues.RA.stdBuilder()
                .itemInputs(
                    ItemList.ChaosLocator.get(1),
                    // Awakened Draconium Singularity
                    getModItem(UniversalSingularities.ID, "universal.draconicEvolution.singularity", 1, 1))
                .itemOutputs(getModItem(DraconicEvolution.ID, "chaosShard", 1, 0))
                .duration(100 * SECONDS)
                .eut(TierEU.RECIPE_UMV)
                .metadata(DEFC_CASING_TIER, 4)
                .addTo(fusionCraftingRecipes);
        }
    }

    public static Object[][] getIsland(int dimID) {
        switch (dimID) {
            case -1 -> {
                return skyIslands.createIslandNether();
            }
            case 100 -> {
                return skyIslands.createIslandDD();
            }
            case 1 -> {
                return skyIslands.createIslandEnd();
            }
            case 7 -> {
                return skyIslands.createIslandTwilight();
            }
            case 25, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 51, 52, 53, 63, 64, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 90, 91, 92, 93, 94, 95 -> {
                return skyIslands.createIslandSpaceStation();
            }
            case 50, 60, 69, 173 -> {
                return skyIslands.createIslandThaum();
            }
            default -> {
                return skyIslands.createIslandOW();
            }
        }
    }

    public static ItemStack[] getChestLoot(int dimID) {
        switch (dimID) {
            case -1 -> {
                return createChestNether();
            }
            case 100 -> {
                return createChestDD();
            }
            case 1 -> {
                return createChestEnd();
            }
            case 7 -> {
                return createChestTwilight();
            }
            case 25, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 51, 52, 53, 63, 64, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 90, 91, 92, 93, 94, 95 -> {
                return createChestSpaceStation();
            }
            case 50, 60, 69, 173 -> {
                return createChestThaum();
            }
            default -> {
                return createChestOW();
            }
        }
    }
}
