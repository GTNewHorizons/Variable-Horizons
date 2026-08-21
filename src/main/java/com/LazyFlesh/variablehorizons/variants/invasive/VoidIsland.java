package com.LazyFlesh.variablehorizons.variants.invasive;

import static gregtech.api.enums.Mods.BiomesOPlenty;
import static gregtech.api.enums.Mods.GalacticraftCore;
import static gregtech.api.enums.Mods.HardcoreEnderExpansion;
import static gregtech.api.enums.Mods.Minecraft;
import static gregtech.api.enums.Mods.TinkerConstruct;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ItemFluidContainer;

import com.LazyFlesh.variablehorizons.util.skyIslands;
import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import gregtech.api.util.GTModHandler;

public class VoidIsland extends VariantLoader {

    private static ItemStack[] createChestOW() {
        return new ItemStack[] { new ItemStack(Items.lava_bucket), new ItemStack(Blocks.ice, 2),
            new ItemStack(Items.reeds), new ItemStack(Items.melon_seeds), new ItemStack(Items.pumpkin_seeds),
            new ItemStack(Blocks.cactus), new ItemStack(Items.flint, 5) };
    }

    private static ItemStack[] createChestNether() {
        ItemStack bopBucket = GTModHandler.getModItem(BiomesOPlenty.ID, "bopBucket");
        ((ItemFluidContainer) bopBucket.getItem())
            .fill(bopBucket, new FluidStack(FluidRegistry.getFluid("hell_blood"), 1000), true);
        return new ItemStack[] { new ItemStack(Items.lava_bucket), bopBucket, bopBucket, new ItemStack(Items.reeds),
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

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        VariantNames.VOID_ISLAND.hasLoaded = true;
    }

    public static Object[][] getIsland(int dimID) {
        switch (dimID) {
            case (-1) -> {
                return skyIslands.createIslandNether();
            }
            case 100 -> {
                return skyIslands.createIslandDD();
            }
            case (1) -> {
                return skyIslands.createIslandEnd();
            }
            case -30, -29, -28, 25, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 51, 52, 53, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88 -> {
                return skyIslands.createIslandSpaceStation();
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
            case -30, -29, -28, 25, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 51, 52, 53, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88 -> {
                return createChestSpaceStation();
            }
            default -> {
                return createChestOW();
            }
        }
    }
}
