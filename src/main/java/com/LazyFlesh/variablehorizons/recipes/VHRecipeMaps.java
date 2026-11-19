package com.LazyFlesh.variablehorizons.recipes;

import gregtech.api.gui.modularui.GTUITextures;
import gregtech.api.modularui2.GTGuiTextures;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMapBackend;
import gregtech.api.recipe.RecipeMapBuilder;

public class VHRecipeMaps {

    public static final RecipeMap<RecipeMapBackend> manufacturingTableRecipes = RecipeMapBuilder
        .of("vh.recipe.manufacturingTable")
        .maxIO(9, 1, 0, 0)
        .minInputs(1, 0)
        .slotOverlays(
            (index, isFluid, isOutput, isSpecial) -> !isFluid && !isOutput ? GTUITextures.OVERLAY_SLOT_CIRCUIT : null)
        .slotOverlaysMUI2(
            (index, isFluid, isOutput, isSpecial) -> !isFluid && !isOutput ? GTGuiTextures.OVERLAY_SLOT_CIRCUIT : null)
        .progressBar(GTUITextures.PROGRESSBAR_ASSEMBLE)
        .progressBarMUI2(GTGuiTextures.PROGRESSBAR_ASSEMBLE)
        .build();
}
