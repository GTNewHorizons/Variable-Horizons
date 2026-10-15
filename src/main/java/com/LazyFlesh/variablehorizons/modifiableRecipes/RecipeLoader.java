package com.LazyFlesh.variablehorizons.modifiableRecipes;

import com.LazyFlesh.variablehorizons.modifiableRecipes.recipeMaps.Assembler;

public class RecipeLoader implements Runnable {

    @Override
    public void run() {
        new Assembler().run();
    }
}
