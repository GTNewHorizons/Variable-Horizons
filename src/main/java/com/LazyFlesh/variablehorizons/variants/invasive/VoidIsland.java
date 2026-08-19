package com.LazyFlesh.variablehorizons.variants.invasive;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

public class VoidIsland extends VariantLoader {

    private static final Object[][] islandSkyblock = { { -7, 6, -3 }, // offset x, y, z
        { 'L', Blocks.leaves, 'W', Blocks.log, 'G', Blocks.grass, 'D', Blocks.dirt, 'C', Blocks.chest }, // block key
        { "        ", "        ", "LLLL    ", " LLL    ", "        ", "        ", "        ", "        ", "        " },
        { "  L     ", "  LL    ", "LLLLL   ", "LLLLL   ", "        ", "        ", "        ", "        ", "        " },
        { " LLL    ", " LWL    ", "LLWLL   ", "LLWLL   ", "  W     ", "  W     ", "  GGG   ", "  DDD   ", "  DDD   " },
        { "  L     ", "  LL    ", "LLLLL   ", "LLLLL   ", "        ", "        ", "  GGG   ", "  DDD   ", "  DDD   " },
        { "        ", "        ", "LLLL    ", "LLLL    ", "        ", "        ", "  GGG   ", "  DDD   ", "  DDD   " },
        { "        ", "        ", "        ", "        ", "        ", "        ", "  GGG   ", "  DDD   ", "  DDD   " },
        { "        ", "        ", "        ", "        ", "        ", "        ", "  GGGGGG", "  DDDDDD", "  DDDDDD" },
        { "        ", "        ", "        ", "        ", "        ", "      C ", "  GGGGGG", "  DDDDDD", "  DDDDDD" },
        { "        ", "        ", "        ", "        ", "        ", "        ", "  GGGGGG", "  DDDDDD",
            "  DDDDDD" } };

    private static final ItemStack[] chestSkyblock = { new ItemStack(Items.lava_bucket, 1),
        new ItemStack(Blocks.ice, 2), new ItemStack(Items.reeds, 1), new ItemStack(Items.melon_seeds, 1),
        new ItemStack(Items.pumpkin_seeds, 1), new ItemStack(Blocks.cactus, 1), new ItemStack(Items.flint, 5) };

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        VariantNames.VOID_ISLAND.hasLoaded = true;
    }

    public static Object[][] getIsland(int dimID) {
        switch (dimID) {
            // case (non-breathable space dims) -> { return VoidIsland.spaceStation}
            default -> {
                return VoidIsland.islandSkyblock;
            }
        }
    }

    public static ItemStack[] getChestLoot(int dimID) {
        switch (dimID) {
            // case (non-breathable space dims) -> { return VoidIsland.spaceStation}
            /*
             * case 1 -> {
             * ItemStack[] loot = VoidIsland.chestSkyblock;
             * loot[1] = new ItemStack(Items.); // replace ice with a bucket or cell of distilled water or blood
             * return loot;}
             */
            default -> {
                return VoidIsland.chestSkyblock;
            }
        }
    }

}
