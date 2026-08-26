package com.LazyFlesh.variablehorizons.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import com.LazyFlesh.variablehorizons.VariableHorizons;

public class RecipeRemover {

    private static final HashSet<ItemStack> recipesToRemoveByItem = new HashSet<>();

    public static void removeRecipesByOutput() {
        if (recipesToRemoveByItem.isEmpty()) return;

        VariableHorizons.LOG.info("Removing recipes!");
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

    public static void addItemsForRecipeRemoval(ItemStack... items) {
        recipesToRemoveByItem.addAll(Arrays.asList(items));
    }
}
