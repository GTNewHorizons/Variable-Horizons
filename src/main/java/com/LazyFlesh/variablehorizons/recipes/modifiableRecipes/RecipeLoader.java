package com.LazyFlesh.variablehorizons.recipes.modifiableRecipes;

import com.LazyFlesh.variablehorizons.recipes.modifiableRecipes.recipeMaps.Assembler;
import com.LazyFlesh.variablehorizons.recipes.modifiableRecipes.recipeMaps.AssemblyLine;
import com.LazyFlesh.variablehorizons.recipes.modifiableRecipes.recipeMaps.Autoclave;
import com.LazyFlesh.variablehorizons.recipes.modifiableRecipes.recipeMaps.CAL;
import com.LazyFlesh.variablehorizons.recipes.modifiableRecipes.recipeMaps.CircuitAssembler;
import com.LazyFlesh.variablehorizons.recipes.modifiableRecipes.recipeMaps.ManufacturingTable;
import com.LazyFlesh.variablehorizons.recipes.modifiableRecipes.recipeMaps.NAC;

public class RecipeLoader implements Runnable {

    @Override
    public void run() {
        new Assembler().run();
        new AssemblyLine().run();
        new Autoclave().run();
        new CAL().run();
        new CircuitAssembler().run();
        new NAC().run();
        new ManufacturingTable().run();
    }
}
