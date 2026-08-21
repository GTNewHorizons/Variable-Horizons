package com.LazyFlesh.variablehorizons.variants.invasive;

import static gregtech.api.enums.Mods.BiomesOPlenty;
import static gregtech.api.enums.Mods.HardcoreEnderExpansion;
import static gregtech.api.enums.Mods.Minecraft;
import static gregtech.api.enums.Mods.TinkerConstruct;

import cpw.mods.fml.common.registry.GameRegistry;
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

    private static Object[][] createIslandEnd() {
        return new Object[][]{{-6, 21, -3}, // offset x, y, z
            {'A', GameRegistry.findBlock("HardcoreEnderExpansion", "crossed_decoration"), 2,
                'B', GameRegistry.findBlock("HardcoreEnderExpansion", "crossed_decoration"), 4,
                'C', GameRegistry.findBlock("HardcoreEnderExpansion", "crossed_decoration"), 5,
                'D', GameRegistry.findBlock("HardcoreEnderExpansion", "crossed_decoration"), 6,
                'E', GameRegistry.findBlock("HardcoreEnderExpansion", "crossed_decoration"), 7,
                'F', GameRegistry.findBlock("HardcoreEnderExpansion", "crossed_decoration"), 8,
                'G', GameRegistry.findBlock("HardcoreEnderExpansion", "crossed_decoration"), 10,
                'H', GameRegistry.findBlock("HardcoreEnderExpansion", "crossed_decoration"), 11,
                'I', GameRegistry.findBlock("HardcoreEnderExpansion", "crossed_decoration"), 12,
                'J', GameRegistry.findBlock("HardcoreEnderExpansion", "end_stone_terrain"), 0,
                'K', GameRegistry.findBlock("HardcoreEnderExpansion", "end_stone_terrain"), 1,
                'L', GameRegistry.findBlock("HardcoreEnderExpansion", "end_stone_terrain"), 2,
                'M', GameRegistry.findBlock("HardcoreEnderExpansion", "ender_goo"), 0,
                'N', GameRegistry.findBlock("etfuturum", "chorus_flower"), 0,
                'O', Blocks.glowstone, 0,
                'P', GameRegistry.findBlock("thaumicbases", "genLogs"), 2,
                'Q', GameRegistry.findBlock("thaumicbases", "genLeaves"), 3,
                'R', Blocks.obsidian, 0,
                'S', GameRegistry.findBlock("HardcoreEnderExpansion", "obsidian_end"), 0,
                'T', GameRegistry.findBlock("HardcoreEnderExpansion", "obsidian_special"), 0,
                'U', GameRegistry.findBlock("HardcoreEnderExpansion", "obsidian_special"), 1,
                'V', Blocks.end_stone, 0,
                'W', Blocks.chest, 0}, // key, block, meta
            {"                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "        O        ", "        S        ", "        S        ", "        S        ", "        S        ", "        S        ", "        S        ", "        S        ", "        S        ", "        S        ", "        R        ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 "},
            {"                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "        S        ", "        S        ", "       SSS       ", "       SSS       ", "       SSS       ", "       SSS       ", "       RSR       ", "        S        ", "        S        ", "        S        ", "        R        ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 "},
            {"                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "        S        ", "        S        ", "        S        ", "        S        ", "       SSS       ", "       SSS       ", "       SSS       ", "       SSS       ", "       RRR       ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 "},
            {"                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "        S        ", "        S        ", "        S        ", "        S        ", "        S        ", "      UUUUU      ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 "},
            {"                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "     UTTTTTU     ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 "},
            {"                 ", "                 ", "                 ", "        V        ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "         A       ", "    UTJJJJJTU    ", "       VVV       ", "       VV        ", "                 ", "                 ", "                 ", "                 ", "                 "},
            {"                 ", "       VVV       ", "      VVVVV      ", "      VVVVV      ", "      VVVVV      ", "       VVV       ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "       QQQ       ", "       QQQ       ", "                 ", "      B    G     ", "   UTJJJLJKKTU   ", "      VVVVV      ", "      VVVV       ", "       VV        ", "       VV        ", "        V        ", "        V        ", "                 "},
            {"                 ", "      VMMMV      ", "      VVVVV      ", "      VVVVV      ", "      VVVVV      ", "      VVVVV      ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", " S             S ", " S             S ", " S             S ", " R      Q      R ", " RS   QQQQQ   SR ", "  S   QQQQQ   S  ", "  S     Q     S  ", "  S      F    S  ", "  RUTJJLLLKKTUR  ", "     VVVVVV      ", "     V VVV       ", "     V VVV       ", "        VV       ", "        V        ", "                 ", "                 "},
            {"        N        ", "      VMVMV      ", "      VVVVV      ", "     VVVVVVV     ", "      VVVVV      ", "      VVVVV      ", "        V        ", "                 ", "O               O", "S               S", "S               S", "SS             SS", "SS             SS", "SSS           SSS", "SSS           SSS", "SSS           SSS", "RRRS   QQQ   SRRR", "SSSS  QQPQQ  SSSS", "RSSS  QQPQQ  SSSR", " SSS   QPQ   SSS ", " SSS   EP    SSS ", " RRUTJLLVLLKTURR ", "      VVVVVV     ", "      VVVVV      ", "      VVVVV      ", "      VVVVV      ", "       VV V      ", "        V V      ", "        V        "},
            {"                 ", "      VMMMV      ", "      VVVVV      ", "      VVVVV      ", "      VVVVV      ", "      VVVVV      ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", " S             S ", " S             S ", " S             S ", " R      Q      R ", " RS   QQQQQ   SR ", "  S   QQQQQ   S  ", "  S     Q     S  ", "  S     E  H  S  ", "  RUTJJLLLKKTUR  ", "     VVVVVV      ", "      VVVVV      ", "      VVVV       ", "       V V       ", "                 ", "                 ", "                 "},
            {"                 ", "       VVV       ", "      VVVVV      ", "      VVVVV      ", "      VVVVV      ", "       VVV       ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "       QQQ       ", "       QQQ       ", "                 ", "     C           ", "   UTJJKLKKKTU   ", "      VVVVV      ", "       VVVV      ", "        V        ", "        V        ", "        V        ", "                 ", "                 "},
            {"                 ", "                 ", "                 ", "        V        ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "       I  D      ", "    UTKKKKKTU    ", "       VVVV      ", "        V        ", "                 ", "                 ", "                 ", "                 ", "                 "},
            {"                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "        W        ", "     UTTTTTU     ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 "},
            {"                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "        S        ", "        S        ", "        S        ", "        S        ", "        S        ", "      UUUUU      ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 "},
            {"                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "        S        ", "        S        ", "        S        ", "        S        ", "       SSS       ", "       SSS       ", "       SSS       ", "       SSS       ", "       RRR       ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 "},
            {"                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "        S        ", "        R        ", "       SSS       ", "       SSS       ", "       SSS       ", "       RRR       ", "       RSR       ", "        S        ", "        S        ", "        S        ", "        R        ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 "},
            {"                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "        O        ", "        S        ", "        S        ", "        S        ", "        S        ", "        R        ", "        S        ", "        S        ", "        R        ", "        S        ", "        R        ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 ", "                 "}};
    }
    // spotless:on

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
                return createIslandEnd();
            }
            // case (non-breathable space dims) -> {
            // return skyIslands.createIslandSpaceStation();
            // }
            default -> {
                return skyIslands.createIslandOW();
            }
        }
    }

    public static ItemStack[] getChestLoot(int dimID) {
        switch (dimID) {
            case (-1) -> {
                return createChestNether();
            }
            case 100 -> {
                return createChestDD();
            }
            case (1) -> {
                return createChestEnd();
            }
            // case (non-breathable space dims) -> { return VoidIsland.spaceStation}
            default -> {
                return createChestOW();
            }
        }
    }
}
