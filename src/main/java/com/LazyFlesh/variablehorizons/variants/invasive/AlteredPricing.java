package com.LazyFlesh.variablehorizons.variants.invasive;

import static gregtech.api.enums.Mods.EtFuturumRequiem;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import com.LazyFlesh.variablehorizons.api.VHItemList;
import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GTModHandler;

public class AlteredPricing extends VariantLoader {

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        VariantNames.ALTERED_PRICING.hasLoaded = true;
    }

    @Override
    public void variantRecipes(VariantNames... activeVariants) {
        GTModHandler.addCraftingRecipe(
            VHItemList.ManufacturingTable.get(1),
            GTModHandler.RecipeBits.BUFFERED | GTModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS,
            new Object[] { "SPS", "GTG", "SCS", 'S', OrePrefixes.screw.get(Materials.Tin), 'P',
                OrePrefixes.plate.get(Materials.Iron), 'G', OrePrefixes.gearGt.get(Materials.Wood), 'T',
                new ItemStack(Blocks.crafting_table, 1), 'C',
                GTModHandler.getModItem(EtFuturumRequiem.ID, "barrel", 1, 0) });
    }
}
