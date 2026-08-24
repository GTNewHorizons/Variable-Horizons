package com.LazyFlesh.variablehorizons.variants.runtime;

import java.math.BigInteger;

import net.minecraft.item.crafting.CraftingManager;

import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import gregtech.api.enums.ItemList;
import gregtech.common.misc.GlobalVariableStorage;

public class InfinitePower extends VariantLoader implements IRuntimeVariant {

    public final static BigInteger infinitePowaaaaaaahhhhh = BigInteger.TEN.pow(50);

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        VariantNames.INFINITE_POWER.hasLoaded = true;

        // runs every restart, so... I doubt it will *ever* deplete
        for (java.util.UUID uuid : GlobalVariableStorage.GlobalEnergy.keySet()) {
            GlobalVariableStorage.GlobalEnergy.replace(uuid, infinitePowaaaaaaahhhhh);
        }
    }

    @Override
    public void variantRecipes(VariantNames... activeVariants) {
        // recipes for each wireless hatch, 1 to 1 conversion from normal e hatch
        // skip adding dynamo to wireless dynamo, since pointless
        CraftingManager.getInstance()
            .addShapelessRecipe(ItemList.Wireless_Hatch_Energy_ULV.get(1), ItemList.Hatch_Energy_ULV.get(1));
        CraftingManager.getInstance()
            .addShapelessRecipe(ItemList.Wireless_Hatch_Energy_LV.get(1), ItemList.Hatch_Energy_LV.get(1));
        CraftingManager.getInstance()
            .addShapelessRecipe(ItemList.Wireless_Hatch_Energy_MV.get(1), ItemList.Hatch_Energy_MV.get(1));
        CraftingManager.getInstance()
            .addShapelessRecipe(ItemList.Wireless_Hatch_Energy_HV.get(1), ItemList.Hatch_Energy_HV.get(1));
        CraftingManager.getInstance()
            .addShapelessRecipe(ItemList.Wireless_Hatch_Energy_EV.get(1), ItemList.Hatch_Energy_EV.get(1));
        CraftingManager.getInstance()
            .addShapelessRecipe(ItemList.Wireless_Hatch_Energy_IV.get(1), ItemList.Hatch_Energy_IV.get(1));
        CraftingManager.getInstance()
            .addShapelessRecipe(ItemList.Wireless_Hatch_Energy_LuV.get(1), ItemList.Hatch_Energy_LuV.get(1));
        CraftingManager.getInstance()
            .addShapelessRecipe(ItemList.Wireless_Hatch_Energy_ZPM.get(1), ItemList.Hatch_Energy_ZPM.get(1));
        CraftingManager.getInstance()
            .addShapelessRecipe(ItemList.Wireless_Hatch_Energy_UV.get(1), ItemList.Hatch_Energy_UV.get(1));
        CraftingManager.getInstance()
            .addShapelessRecipe(ItemList.Wireless_Hatch_Energy_UHV.get(1), ItemList.Hatch_Energy_UHV.get(1));
        CraftingManager.getInstance()
            .addShapelessRecipe(ItemList.Wireless_Hatch_Energy_UEV.get(1), ItemList.Hatch_Energy_UEV.get(1));
        CraftingManager.getInstance()
            .addShapelessRecipe(ItemList.Wireless_Hatch_Energy_UIV.get(1), ItemList.Hatch_Energy_UIV.get(1));
        CraftingManager.getInstance()
            .addShapelessRecipe(ItemList.Wireless_Hatch_Energy_UMV.get(1), ItemList.Hatch_Energy_UMV.get(1));
        CraftingManager.getInstance()
            .addShapelessRecipe(ItemList.Wireless_Hatch_Energy_UXV.get(1), ItemList.Hatch_Energy_UXV.get(1));

    }
}
