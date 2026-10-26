package com.LazyFlesh.variablehorizons.util.recipeBuilder;

import java.util.Arrays;
import java.util.List;

import gregtech.api.enums.OrePrefixes;

public enum CompressionType {

    PLATE(Arrays.asList(
        OrePrefixes.plate,
        OrePrefixes.plateDouble,
        OrePrefixes.plateTriple,
        OrePrefixes.plateQuadruple,
        OrePrefixes.plateQuintuple,
        OrePrefixes.plateDense)),
    GEAR(Arrays.asList(OrePrefixes.gearGtSmall, OrePrefixes.gearGt)),
    ROD(Arrays.asList(OrePrefixes.stick, OrePrefixes.stickLong)),
    WIRE(Arrays.asList(
        OrePrefixes.wireGt01,
        OrePrefixes.wireGt02,
        OrePrefixes.wireGt04,
        OrePrefixes.wireGt08,
        OrePrefixes.wireGt12,
        OrePrefixes.wireGt16)),
    CABLE(Arrays.asList(
        OrePrefixes.cableGt01,
        OrePrefixes.cableGt02,
        OrePrefixes.cableGt04,
        OrePrefixes.cableGt08,
        OrePrefixes.cableGt12,
        OrePrefixes.cableGt16)),
    WRAP(true); // put in circuit wrap

    public List<OrePrefixes> oredicts;
    private final boolean isWrap;

    CompressionType(List<OrePrefixes> oredicts) {
        this.oredicts = oredicts;
        this.isWrap = false;
    }

    CompressionType(Boolean wrap) {
        this.isWrap = wrap;
    }

    public static CompressionType getTypeForPrefix(OrePrefixes prefix) {
        for (CompressionType type : values()) {
            if (type.oredicts.contains(prefix)) {
                return type;
            }
        }
        return null;
    }
}
