package com.LazyFlesh.variablehorizons.variants.invasive;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import cpw.mods.fml.common.registry.GameRegistry;

public class VoidIsland extends VariantLoader {

    // spotless:off
    private static Object[][] createIslandOW() {
        return new Object[][] { { -7, 6, -3 }, // offset x, y, z
            {   'L', Blocks.leaves, 0,
                'W', Blocks.log, 0,
                'G', Blocks.grass, 0,
                'D', Blocks.dirt, 0,
                'C', Blocks.chest, 0}, // key, block, meta
            { "        ", "        ", "LLLL    ", " LLL    ", "        ", "        ", "        ", "        ", "        " },
            { "  L     ", "  LL    ", "LLLLL   ", "LLLLL   ", "        ", "        ", "        ", "        ", "        " },
            { " LLL    ", " LWL    ", "LLWLL   ", "LLWLL   ", "  W     ", "  W     ", "  GGG   ", "  DDD   ", "  DDD   " },
            { "  L     ", "  LL    ", "LLLLL   ", "LLLLL   ", "        ", "        ", "  GGG   ", "  DDD   ", "  DDD   " },
            { "        ", "        ", "LLLL    ", "LLLL    ", "        ", "        ", "  GGG   ", "  DDD   ", "  DDD   " },
            { "        ", "        ", "        ", "        ", "        ", "        ", "  GGGGGG", "  DDDDDD", "  DDDDDD" },
            { "        ", "        ", "        ", "        ", "        ", "       C", "  GGGGGG", "  DDDDDD", "  DDDDDD" },
            { "        ", "        ", "        ", "        ", "        ", "        ", "  GGGGGG", "  DDDDDD", "  DDDDDD" }};
    }

    private static Object[][] createIslandNether() {
        return new Object[][]{{-9, 0, -7}, // offset x, y, z
            {'A', GameRegistry.findBlock("BiomesOPlenty", "ash"), 1,
                'B', GameRegistry.findBlock("Natura", "bloodwood"), 15,
                'C', GameRegistry.findBlock("BiomesOPlenty", "bopGrass"), 1,
                'D', GameRegistry.findBlock("BiomesOPlenty", "flesh"), 3,
                'E', Blocks.netherrack, 0,
                'F', GameRegistry.findBlock("Natura", "bloodwood"), 3,
                'G', GameRegistry.findBlock("Natura", "floraleavesnocolor"), 2,
                'H', GameRegistry.findBlock("Natura", "bloodwood"), 2,
                'I', GameRegistry.findBlock("Natura", "bloodwood"), 0,
                'J', GameRegistry.findBlock("Natura", "bloodwood"), 1,
                'K', Blocks.chest, 0}, // key, block, meta
            {"             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","   GGG  GGG  ","             ","        GGG  ","             ","             ","             "},
            {"             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","   GGG  GGG  ","  GGGGGGGGGG ","   GGGGGGGG  ","        GGGG ","        GGG  ","             ","             "},
            {"             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","   GGG  GGG  ","  GGBGGGGBGG ","   GGGGGGGG  ","    GGGGGBGG ","        GGG  ","             ","             "},
            {"             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","   GGG GGGG  ","  GGGGGGGGGG ","   GGBGGBGG  ","    GGGGGGGG ","     GGGGGG  ","             ","             "},
            {"             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","       GGG   ","   GGGGGBGG  ","  GGGGGGGGGG ","    GGBBGGG  ","     GGGGG   ","      GGG    ","             "},
            {"             ","      CAA    ","      AAA    ","      AEE    ","      IH     ","      IH     ","      IH     ","      IH     ","      IH     ","      IH     ","      IH     ","      IH     ","      IH     ","      IHGGG  ","  GGGGIHBGGG "," GGGGGIBGGGGG"," GGGGGGBBGGG ","    GGGBGG   ","    GGGGG    ","             "},
            {"             ","      CCC    ","      AEA    ","      AEE    ","      JF     ","      JF     ","      JF     ","      JF     ","      JF     ","      JF     ","      JF     ","      JF     ","      JF     ","     GJFGGG  ","  GGGGJFGBGG "," GGBGGBBGGBGG","GGGGGBGGGGGG "," GGGGGGBGGG  ","   GGGGGG    ","    GGGGG    "},
            {"             ","      CEA    ","      EEE    ","      EEA    ","             ","             ","             ","             ","             ","             ","             ","             ","      GGG    ","     GGGGGG  ","  GGGGBGGGGG "," GGGGGGGGGGGG","GGBGBGGGBBGG "," GGGGGGGGGG  ","   GGBBBG    ","    GGGGG    "},
            {"             ","      ECCDCE ","      EEADDD ","      EEADDE ","             ","             ","             ","             ","             ","             ","             ","             ","      GGG    ","     GGBGG   ","    GGGGGGG  "," GGGGGGGGGGG ","GGGGGGGGGGGG "," GGGGGGGGGG  ","   GGGGGG    ","    GGGGG    "},
            {"           K ","      CEEDDD ","      EEAEEE ","      EEEDED ","             ","             ","             ","             ","             ","             ","             ","             ","      GGG    ","     GGGGG   ","    GGBGG    ","     GGG     "," GGGGG GGGG  ","             ","    GGGGG    ","             "},
            {"             ","      ECECCD ","      DEEDED ","      DEEDDE ","             ","             ","             ","             ","             ","             ","             ","             ","             ","     GGGG    ","    GGGG     ","     GGG     ","             ","             ","             ","             "},
            {"             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","             ","     GGG     ","             ","             ","             ","             ","             "}};
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
            case (-1) -> {
                return createIslandNether();
            }
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
}
