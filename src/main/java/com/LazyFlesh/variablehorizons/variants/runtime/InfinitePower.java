package com.LazyFlesh.variablehorizons.variants.runtime;

import java.math.BigInteger;

import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GTModHandler;
import gregtech.common.misc.WirelessNetworkManager;
import tectech.thing.CustomItemList;

public class InfinitePower extends VariantLoader implements IRuntimeVariant {

    public final static BigInteger infinitePowaaaaaaahhhhh = BigInteger.TEN.pow(100);

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        VariantNames.INFINITE_POWER.hasLoaded = true;
    }

    @SubscribeEvent
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        // runs every player login, so... I doubt it will *ever* deplete
        WirelessNetworkManager
            .addEUToGlobalEnergyMap(event.player.getUniqueID(), InfinitePower.infinitePowaaaaaaahhhhh);
    }

    @Override
    public void variantRecipes(VariantNames... activeVariants) {

        // debug gen recipe for infinite wired power
        GTModHandler.addCraftingRecipe(
            CustomItemList.Machine_DebugGenny.get(1),
            GTModHandler.RecipeBits.BUFFERED | GTModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS,
            new Object[] { "ERE", "CHC", "PBP", 'E', ItemList.Emitter_LV, 'R',
                OrePrefixes.cableGt16.get(Materials.RedAlloy), 'C', OrePrefixes.circuit.get(Materials.LV), 'H',
                ItemList.Hull_LV, 'P', OrePrefixes.plate.get(Materials.Steel), 'B', ItemList.Battery_Hull_LV });
    }

    @Override
    public void undoVariant(VariantNames... activeVariants) {
        // can't really turn off easily, so just... won't
        // hence also not toggling the .hasLoaded
    }
}
