package com.LazyFlesh.variablehorizons.variants.runtime;

import com.LazyFlesh.variablehorizons.variants.VariantNames;

public interface IRuntimeVariant {
    // variant is able to be applied during runtime, such as on world load, with little issue.
    // No rocket removes 1 recipe, Infinite Power sets wireless network to inf, etc.

    void undoVariant(VariantNames... activeVariants);

}
