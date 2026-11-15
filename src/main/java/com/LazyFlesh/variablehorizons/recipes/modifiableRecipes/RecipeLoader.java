package com.LazyFlesh.variablehorizons.recipes.modifiableRecipes;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.recipes.modifiableRecipes.recipeMaps.Assembler;
import com.LazyFlesh.variablehorizons.recipes.modifiableRecipes.recipeMaps.AssemblyLine;
import com.LazyFlesh.variablehorizons.recipes.modifiableRecipes.recipeMaps.Autoclave;
import com.LazyFlesh.variablehorizons.recipes.modifiableRecipes.recipeMaps.CAL;
import com.LazyFlesh.variablehorizons.recipes.modifiableRecipes.recipeMaps.CircuitAssembler;
import com.LazyFlesh.variablehorizons.recipes.modifiableRecipes.recipeMaps.CoAL;
import com.LazyFlesh.variablehorizons.recipes.modifiableRecipes.recipeMaps.EIC;
import com.LazyFlesh.variablehorizons.recipes.modifiableRecipes.recipeMaps.ManufacturingTable;
import com.LazyFlesh.variablehorizons.recipes.modifiableRecipes.recipeMaps.NAC;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

public class RecipeLoader implements Runnable {

    public static double partCostMultiplier;
    public static double circuitCostMultiplier;
    public static double miscCostMultiplier;

    @Override
    public void run() {
        partCostMultiplier = (VariantNames.ALTERED_PRICING.hasLoaded && GeneralConfig.changePartCosts)
            ? GeneralConfig.partCostMultiplier
            : 1;
        circuitCostMultiplier = (VariantNames.ALTERED_PRICING.hasLoaded && GeneralConfig.changeCircuitCosts)
            ? GeneralConfig.partCostMultiplier
            : 1;
        miscCostMultiplier = (VariantNames.ALTERED_PRICING.hasLoaded && GeneralConfig.changeMiscCosts)
            ? GeneralConfig.partCostMultiplier
            : 1;

        new Assembler().run();
        new AssemblyLine().run();
        new Autoclave().run();
        new CAL().run();
        new CircuitAssembler().run();
        new CoAL().run();
        new EIC().run();
        new NAC().run();
        new ManufacturingTable().run();
    }
}
