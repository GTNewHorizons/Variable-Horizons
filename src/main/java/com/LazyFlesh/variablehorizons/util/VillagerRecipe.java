package com.LazyFlesh.variablehorizons.util;

import java.util.Random;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.registry.VillagerRegistry.IVillageTradeHandler;
import forestry.api.apiculture.EnumBeeType;
import forestry.plugins.PluginApiculture;
import magicbees.bees.BeeSpecies;

public class VillagerRecipe implements IVillageTradeHandler {

    @Override
    public void manipulateTradesForVillager(final EntityVillager villager, final MerchantRecipeList recipeList,
        final Random random) {
        ItemStack wildcardDrone = new ItemStack(PluginApiculture.items.beeDroneGE, 4, OreDictionary.WILDCARD_VALUE);
        ItemStack AttunedPrincess = BeeSpecies.ATTUNED.getBeeItem(EnumBeeType.PRINCESS);

        recipeList.add(
            new MerchantRecipe(wildcardDrone, new ItemStack(Items.emerald, 8 + random.nextInt(8)), AttunedPrincess));
    }
}
