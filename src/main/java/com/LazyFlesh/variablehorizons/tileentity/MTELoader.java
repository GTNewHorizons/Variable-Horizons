package com.LazyFlesh.variablehorizons.tileentity;

import com.LazyFlesh.variablehorizons.api.VHItemList;
import com.LazyFlesh.variablehorizons.tileentity.singleblock.MTEManufacturingTable;

public class MTELoader {

    public final static int MANUFACTURING_TABLE_ID = 12072;

    public static void init() {
        VHItemList.ManufacturingTable.set(
            new MTEManufacturingTable(MANUFACTURING_TABLE_ID, "manufacturingtable", "Manufacturing Table", 0)
                .getStackForm(1));
    }
}
