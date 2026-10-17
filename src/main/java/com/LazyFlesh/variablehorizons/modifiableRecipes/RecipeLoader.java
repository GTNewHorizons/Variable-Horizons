package com.LazyFlesh.variablehorizons.modifiableRecipes;

import com.LazyFlesh.variablehorizons.modifiableRecipes.recipeMaps.Assembler;
import com.LazyFlesh.variablehorizons.modifiableRecipes.recipeMaps.NAC;

public class RecipeLoader implements Runnable {

    @Override
    public void run() {
        new Assembler().run();
        new NAC().run();
    }
}
