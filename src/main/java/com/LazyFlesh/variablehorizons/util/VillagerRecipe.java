package com.LazyFlesh.variablehorizons.util;

import static gregtech.api.enums.Mods.NewHorizonsCoreMod;
import static gregtech.api.util.GTModHandler.getModItem;

import java.util.Random;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.registry.VillagerRegistry.IVillageTradeHandler;
import forestry.api.apiculture.EnumBeeType;
import forestry.apiculture.genetics.BeeDefinition;
import forestry.plugins.PluginApiculture;
import magicbees.bees.BeeSpecies;

public class VillagerRecipe implements IVillageTradeHandler {

    @Override
    public void manipulateTradesForVillager(final EntityVillager villager, final MerchantRecipeList recipeList,
        final Random random) {
        ItemStack wildcardDrone = new ItemStack(PluginApiculture.items.beeDroneGE, 4, OreDictionary.WILDCARD_VALUE);
        ItemStack emernald = new ItemStack(Items.emerald, 8 + random.nextInt(8));

        ItemStack[] princesses = new ItemStack[] { BeeSpecies.ATTUNED.getBeeItem(EnumBeeType.PRINCESS),
            BeeDefinition.FOREST.getMemberStack(EnumBeeType.PRINCESS),
            BeeDefinition.MEADOWS.getMemberStack(EnumBeeType.PRINCESS),
            BeeDefinition.WINTRY.getMemberStack(EnumBeeType.PRINCESS),
            BeeDefinition.TROPICAL.getMemberStack(EnumBeeType.PRINCESS),
            BeeDefinition.MODEST.getMemberStack(EnumBeeType.PRINCESS),
            BeeDefinition.MARSHY.getMemberStack(EnumBeeType.PRINCESS) };

        for (ItemStack princess : princesses) {
            recipeList.add(new MerchantRecipe(wildcardDrone, emernald, princess));
        }
    }
}
