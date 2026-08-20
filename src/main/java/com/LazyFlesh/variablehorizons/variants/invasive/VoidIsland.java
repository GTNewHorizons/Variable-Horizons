package com.LazyFlesh.variablehorizons.variants.invasive;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;

import com.LazyFlesh.variablehorizons.util.randomUtil;
import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

public class VoidIsland extends VariantLoader {

    // spotless:off
    private static Object[][] createIslandOW() {
        return new Object[][] { { -7, 6, -3 }, // offset x, y, z
            { 'L', Blocks.leaves, 'W', Blocks.log, 'G', Blocks.grass, 'D', Blocks.dirt, 'C', Blocks.chest }, // block key
            { "        ", "        ", "LLLL    ", " LLL    ", "        ", "        ", "        ", "        ", "        " },
            { "  L     ", "  LL    ", "LLLLL   ", "LLLLL   ", "        ", "        ", "        ", "        ", "        " },
            { " LLL    ", " LWL    ", "LLWLL   ", "LLWLL   ", "  W     ", "  W     ", "  GGG   ", "  DDD   ", "  DDD   " },
            { "  L     ", "  LL    ", "LLLLL   ", "LLLLL   ", "        ", "        ", "  GGG   ", "  DDD   ", "  DDD   " },
            { "        ", "        ", "LLLL    ", "LLLL    ", "        ", "        ", "  GGG   ", "  DDD   ", "  DDD   " },
            { "        ", "        ", "        ", "        ", "        ", "        ", "  GGGGGG", "  DDDDDD", "  DDDDDD" },
            { "        ", "        ", "        ", "        ", "        ", "       C", "  GGGGGG", "  DDDDDD", "  DDDDDD" },
            { "        ", "        ", "        ", "        ", "        ", "        ", "  GGGGGG", "  DDDDDD", "  DDDDDD" }};
    }
    // spotless:on

    private static ItemStack[] createChestOW() {
        return new ItemStack[] { new ItemStack(Items.lava_bucket, 1), new ItemStack(Blocks.ice, 2),
            new ItemStack(Items.reeds, 1), new ItemStack(Items.melon_seeds, 1), new ItemStack(Items.pumpkin_seeds, 1),
            new ItemStack(Blocks.cactus, 1), new ItemStack(Items.flint, 5) };
    }

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        VariantNames.VOID_ISLAND.hasLoaded = true;
    }

    public static Object[][] getIsland(int dimID) {
        switch (dimID) {
            // case (non-breathable space dims) -> { return VoidIsland.spaceStation}
            default -> {
                return createIslandOW();
            }
        }
    }

    public static ItemStack[] getChestLoot(int dimID) {
        switch (dimID) {
            // case (non-breathable space dims) -> { return VoidIsland.spaceStation}
            /*
             * case 1 -> {
             * ItemStack[] loot = createChestOW();
             * loot[1] = new ItemStack(Items.); // replace ice with a bucket or cell of distilled water or blood
             * return loot;}
             */
            default -> {
                return createChestOW();
            }
        }
    }

    public static void chestLoader() {
        if (randomUtil.chestLoad != null) {
            generateChestLoot(
                (World) randomUtil.chestLoad[0],
                (Integer) randomUtil.chestLoad[1],
                (Integer) randomUtil.chestLoad[2],
                (Integer) randomUtil.chestLoad[3],
                (Integer) randomUtil.chestLoad[4]);
            randomUtil.chestLoad = null;
        }
    }

    public static void generateChestLoot(World world, int x, int y, int z, int dimID) {
        if (world.getTileEntity(x, y, z) == null) {
            world.setTileEntity(x, y, z, new TileEntityChest());
        }
        TileEntityChest chest = (TileEntityChest) world.getTileEntity(x, y, z);
        ItemStack[] loot = VoidIsland.getChestLoot(dimID);
        for (int j = 0; j < loot.length; j++) {
            chest.setInventorySlotContents(j, loot[j]);
        }
    }
}
