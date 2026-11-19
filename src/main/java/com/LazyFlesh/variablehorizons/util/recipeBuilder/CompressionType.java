package com.LazyFlesh.variablehorizons.util.recipeBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import gregtech.api.enums.OrePrefixes;

public enum CompressionType {

    PLATE(Arrays.asList(
        OrePrefixes.foil,
        OrePrefixes.plate,
        OrePrefixes.plateDouble,
        OrePrefixes.plateTriple,
        OrePrefixes.plateQuadruple,
        OrePrefixes.plateQuintuple,
        OrePrefixes.plateDense)),
    GEAR(Arrays.asList(OrePrefixes.gearGtSmall, OrePrefixes.gearGt)),
    ROD(Arrays.asList(OrePrefixes.bolt, OrePrefixes.stick, OrePrefixes.stickLong)),
    WIRE(Arrays.asList(
        OrePrefixes.wireFine,
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
    PIPE(Arrays.asList(
        OrePrefixes.pipeTiny,
        OrePrefixes.pipeSmall,
        OrePrefixes.pipeMedium,
        OrePrefixes.pipeLarge,
        OrePrefixes.pipeHuge)),
    SPRING(Arrays.asList(OrePrefixes.springSmall, OrePrefixes.spring)),
    GEM(Arrays.asList(
        OrePrefixes.gemChipped,
        OrePrefixes.gemFlawed,
        OrePrefixes.gem,
        OrePrefixes.gemFlawless,
        OrePrefixes.gemExquisite)),
    CIRCUIT(Collections.singletonList(OrePrefixes.circuit));

    public List<OrePrefixes> oredicts;

    CompressionType(List<OrePrefixes> oredicts) {
        this.oredicts = oredicts;
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
