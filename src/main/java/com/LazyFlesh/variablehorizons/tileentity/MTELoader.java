package com.LazyFlesh.variablehorizons.tileentity;

import com.LazyFlesh.variablehorizons.api.VHItemList;
import com.LazyFlesh.variablehorizons.tileentity.singleblock.MTEManufacturingTable;
import com.LazyFlesh.variablehorizons.variants.VariantNames;

public class MTELoader {

    public final static int MANUFACTURING_TABLE_ID = 12072;

    public static void init() {
        if (VariantNames.activeContains(VariantNames.ALTERED_PRICING.id)) {
            VHItemList.ManufacturingTable.set(
                new MTEManufacturingTable(MANUFACTURING_TABLE_ID, "manufacturingtable", "Manufacturing Table", 0)
                    .getStackForm(1));
        }
    }
}
