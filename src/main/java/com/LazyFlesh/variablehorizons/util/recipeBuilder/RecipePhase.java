package com.LazyFlesh.variablehorizons.util.recipeBuilder;

public enum RecipePhase {
    SUBSTITUTE, // replace(), swaps ingredients, keeps amounts
    SCALE, // scale(), multiplicative changes
    STRUCTURE, // add()/remove(), changes which inputs/outputs exist
    FINAL // absolute "set" operations, anything order-sensitive
}
