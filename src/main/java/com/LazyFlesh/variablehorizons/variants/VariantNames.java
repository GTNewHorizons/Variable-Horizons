package com.LazyFlesh.variablehorizons.variants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.variants.invasive.GardenOfGrind;
import com.LazyFlesh.variablehorizons.variants.runtime.NoRocket;

public enum VariantNames {

    // sub-variants/modifiers
    // modifies one thing, can be stacked with each other (barring incompats)
    // i.e. turns off quests; makes it hardcore; halves all processing time, etc.
    NO_RECIPE_ADDITIONS("NO_RECIPE_ADDITIONS"), // Specifies additions, since no rocket removes
                                                // nasa bench recipe.
    VOID_WORLD("VOID_WORLD" /* no class, just mixin */), // no land anywhere
    VOID_ISLAND("VOID_ISLAND"), // OW is a sky island. Think Botania Garden of Glass or normal Skyblock
    NO_ROCKET("NO_ROCKET", new NoRocket()), // disable nasa bench
    NO_QUEST_REWARDS("NO_QUEST_REWARDS"),
    HALF_TIME("HALF_TIME"),
    QUARTER_TIME("QUARTER_TIME"),
    DOUBLE_TIME("DOUBLE_TIME"),
    HALF_EFFICIENCY("HALF_EFFICIENCY"),
    INFINITE_POWER("INFINITE_POWER"),
    NETHER_START("NETHER_START"), // sets Nether as the spawn dimension instead of OW

    // full variants
    // i.e. defines both world type and recipes
    // unlikely to be compatible with each other
    // I swear to god DO NOT NEST COMPOSITES
    NORMAL("NORMAL", true, new VariantNames[] {}, new VariantNames[] {}), // does nothing

    GARDEN_OF_GRIND("GARDEN_OF_GRIND", new GardenOfGrind(),
        new VariantNames[] { VOID_WORLD, NO_RECIPE_ADDITIONS, NO_ROCKET }, new VariantNames[] {}),

    NETHER_ONLY("NETHER_ONLY", true, new VariantNames[] { NETHER_START, NO_ROCKET },
        new VariantNames[] { NO_RECIPE_ADDITIONS }),

    SKYBLOCK("SKYBLOCK", true, new VariantNames[] { VOID_ISLAND }, new VariantNames[] { NO_RECIPE_ADDITIONS }),
    // only OW is void, w/ sky island
    // if you want Skyblock with no recipe additions, do Garden of Grind + Void Island.

    ;

    public final String id;
    public final boolean compositionVariant; // is it made of several modifications
    public List<VariantNames> incompatible = new ArrayList<>();
    public List<VariantNames> composedOf = new ArrayList<>();
    public final List<VariantNames> partOf = new ArrayList<>(); // composition variant it is part of
    public VariantLoader loaderClass;
    public boolean hasLoaded = false;

    private static Set<String> activeVariantsCache;
    private static boolean variantsCacheRefresh;

    private static final Map<String, VariantNames> allVariants = new HashMap<>();
    private static final VariantNames[] VALUES = values();
    private static final Set<String> allVariantIDs;
    public static final List<VariantNames> allCompositionVariants = new ArrayList<>();
    public static final List<VariantNames> allSubVariants = new ArrayList<>();

    static {
        for (VariantNames name : VALUES) {
            allVariants.put(name.id, name);
            if (name.compositionVariant) {
                allCompositionVariants.add(name);
            } else {
                allSubVariants.add(name);
            }
        }
        allVariantIDs = allVariants.keySet();
    }

    VariantNames(String id) {
        this.id = id;
        this.compositionVariant = false;
    }

    VariantNames(String id, VariantLoader loaderClass) {
        this.loaderClass = loaderClass;
        this.id = id;
        this.compositionVariant = false;
    }

    VariantNames(String id, boolean compositionVariant, VariantNames[] composedOf, VariantNames[] incompatible) {
        this.id = id;
        this.compositionVariant = compositionVariant;

        if (incompatible.length != 0) {
            this.incompatible = Arrays.asList(incompatible);
            for (VariantNames i : incompatible) {
                addIncompatibility(i, this);
            }
        }
        if (composedOf.length != 0) {
            this.composedOf = Arrays.asList(composedOf);
            for (VariantNames i : composedOf) {
                i.partOf.add(this);
                // make sure to add the incompatibles of the composites
                for (VariantNames name : i.incompatible) {
                    addIncompatibility(this, name);
                }
            }
        }
    }

    VariantNames(String id, VariantLoader loaderClass, VariantNames[] composedOf, VariantNames[] incompatible) {
        this(id, true, composedOf, incompatible);
        if (loaderClass != null) {
            this.loaderClass = loaderClass;
        }
    }

    public static Set<String> getVariantNames() {
        return allVariantIDs;
    }

    public static String getVariantNamesFormatted() {
        StringBuilder names = new StringBuilder();
        for (VariantNames name : VALUES) {
            names.append(name.id)
                .append(", ");
        }
        return names.toString();
    }

    public static Set<String> getActiveVariantNames() {
        if (activeVariantsCache == null || variantsCacheRefresh) {
            activeVariantsCache = new HashSet<>(Arrays.asList(GeneralConfig.activeVariants));
            variantsCacheRefresh = false;
        }
        return activeVariantsCache;
    }

    public static VariantNames getVariantFromID(String id) {
        return allVariants.getOrDefault(id, null);
    }

    // does the id match a variant's id
    public static boolean contains(String... id) {
        for (String s : id) {
            if (allVariants.containsKey(s)) return true;
        }
        return false;
    }

    // does the id match an active variant's id
    public static boolean activeContains(String... id) {
        Set<String> active = getActiveVariantNames();
        for (String s : id) {
            // if contained directly
            if (active.contains(s)) return true;
            // now check if it's contained as a part of a composite variant
            VariantNames v = getVariantFromID(s);
            if (v != null && !v.compositionVariant && !v.partOf.isEmpty()) {
                for (VariantNames va : v.partOf) {
                    if (active.contains(va.id)) return true;
                }
            }
        }
        return false;
    }

    public static void addIncompatibility(VariantNames first, VariantNames second) {
        if (first != null && second != null) {
            if (!first.incompatible.contains(second)) first.incompatible.add(second);
            if (!second.incompatible.contains(first)) second.incompatible.add(first);
        }
    }

    public static boolean checkIncompatibility(VariantNames first, VariantNames second) {
        if (first != null && second != null) {
            if (first.incompatible.contains(second)) return false;
            else return !second.incompatible.contains(first);
        }
        return false;
    }

}
