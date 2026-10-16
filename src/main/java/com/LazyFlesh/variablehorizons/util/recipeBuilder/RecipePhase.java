package com.LazyFlesh.variablehorizons.util.recipeBuilder;

public enum RecipePhase {
    SUBSTITUTE, // swap ingredients
    SCALE, // multiplicative changes
    STRUCTURE, // change which inputs/outputs exist
    FINAL // absolute "set" operations, anything order-sensitive
}
