package com.LazyFlesh.variablehorizons.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

public class RecipeRemover {

    public static HashSet<ItemStack> recipesToRemoveByItem = new HashSet<>();

    public static void removeRecipesByOutput() {
        List<IRecipe> recipes = CraftingManager.getInstance()
            .getRecipeList();
        Iterator<IRecipe> iterator = recipes.iterator();

        while (iterator.hasNext()) {
            IRecipe recipe = iterator.next();
            ItemStack output = recipe.getRecipeOutput();
            if (output != null && recipesToRemoveByItem.contains(output)) {
                iterator.remove();
            }
        }

    }
}
