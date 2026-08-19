package com.LazyFlesh.variablehorizons.variants.runtime;

import java.math.BigInteger;

import com.LazyFlesh.variablehorizons.variants.VariantLoader;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

import gregtech.common.misc.GlobalVariableStorage;

public class InfinitePower extends VariantLoader implements IRuntimeVariant {

    public final static BigInteger infinitePowaaaaaaahhhhh = new BigInteger(
        "999_999_999_999_999_999_999_999_999_999_999_999_999_999_999_999_999_999_999_999_999");

    @Override
    public void loadVariant(VariantNames... activeVariants) {
        VariantNames.INFINITE_POWER.hasLoaded = true;

        // runs every restart, so... I doubt it will *ever* deplete
        for (java.util.UUID uuid : GlobalVariableStorage.GlobalEnergy.keySet()) {
            GlobalVariableStorage.GlobalEnergy.replace(uuid, infinitePowaaaaaaahhhhh);
        }
    }
}
