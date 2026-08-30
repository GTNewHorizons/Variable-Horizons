package com.LazyFlesh.variablehorizons.variants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.minecraft.util.StatCollector;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.variants.invasive.DimLocked;
import com.LazyFlesh.variablehorizons.variants.invasive.GardenOfGrind;
import com.LazyFlesh.variablehorizons.variants.invasive.SkyGrid;
import com.LazyFlesh.variablehorizons.variants.invasive.VoidIsland;
import com.LazyFlesh.variablehorizons.variants.runtime.AlteredRecipeTime;
import com.LazyFlesh.variablehorizons.variants.runtime.DiffDimStart;
import com.LazyFlesh.variablehorizons.variants.runtime.InfinitePower;
import com.LazyFlesh.variablehorizons.variants.runtime.NoQuestRewards;
import com.LazyFlesh.variablehorizons.variants.runtime.NoRocket;

public enum VariantNames {
    // spotless:off

    // sub-variants/modifiers
    // modifies one thing, can be stacked with each other (barring incompats)
    // i.e. turns off quests; makes it hardcore; halves all processing time, etc.
    NO_RECIPE_ADDITIONS("NO_RECIPE_ADDITIONS"), // Specifies additions, since, i.e. NoRocket removes rocket recipes.
    VOID_WORLD("VOID_WORLD"), // no land anywhere
    VOID_ISLAND("VOID_ISLAND", new VoidIsland()), // Starting Dim is a sky island.
    NO_ROCKET("NO_ROCKET", new NoRocket()), // removes rocket recipes
    NO_QUEST_REWARDS("NO_QUEST_REWARDS", new NoQuestRewards()),
    ALTERED_RECIPE_TIME("ALTERED_TIME", new AlteredRecipeTime()),
    ALTERED_EFFICIENCY("ALTERED_EFFICIENCY"),
    CHEAP_MODE("CHEAP_MODE"),
    EXPENSIVE_MODE("EXPENSIVE_MODE"),
    INFINITE_POWER("INFINITE_POWER", new InfinitePower()),
    CUSTOM_DIM_START("CUSTOM_DIM_START", new DiffDimStart()), // sets a different dim as the spawn dimension instead of OW
    SUPERFLAT("SUPERFLAT", new VariantNames[]{ VOID_WORLD, VOID_ISLAND }),
    SKYGRID("SKYGRID", new SkyGrid(), new VariantNames[]{}, new VariantNames[]{ VOID_WORLD, VOID_ISLAND, SUPERFLAT }),

    // full variants
    // i.e. defines both world type and recipes
    // unlikely to be compatible with each other
    // I swear to god DO NOT NEST COMPOSITES
    NORMAL("NORMAL", true, new VariantNames[] {}, new VariantNames[] {}), // does nothing

    GARDEN_OF_GRIND("GARDEN_OF_GRIND", new GardenOfGrind(),
        new VariantNames[] { VOID_WORLD, NO_RECIPE_ADDITIONS, NO_ROCKET }, new VariantNames[] { SUPERFLAT }),

    SKYBLOCK("SKYBLOCK", true, new VariantNames[] { VOID_WORLD, VOID_ISLAND },
        new VariantNames[] { NO_RECIPE_ADDITIONS, SUPERFLAT }),
    // only OW is void, w/ sky island
    // if you want Skyblock with no recipe additions, do Garden of Grind + Void Island.
    DIMLOCKED("DIMLOCKED", new DimLocked(), new VariantNames[] { CUSTOM_DIM_START, NO_ROCKET },
        new VariantNames[] { NO_RECIPE_ADDITIONS }),


    ;
    // spotless:on

    public final String id;
    public final boolean compositionVariant; // is it made of several modifications
    public final Set<VariantNames> incompatible = new HashSet<>();
    public final Set<VariantNames> composedOf = new HashSet<>();
    public final Set<VariantNames> partOf = new HashSet<>(); // composition variant it is part of
    public VariantLoader loaderClass;
    public boolean hasLoaded = false;

    private static Set<String> activeVariantsCache;
    private static boolean variantsCacheRefresh;

    private static final Map<String, VariantNames> allVariants = new HashMap<>();
    private static final VariantNames[] VALUES = values();
    private static final Set<String> allVariantIDs;
    public static final Set<VariantNames> allCompositionVariants = new HashSet<>();
    public static final Set<VariantNames> allSubVariants = new HashSet<>();

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

    VariantNames(String id, VariantNames[] incompatible) {
        this.id = id;
        this.compositionVariant = false;
        if (incompatible.length != 0) {
            this.incompatible.addAll(Arrays.asList(incompatible));
            for (VariantNames i : incompatible) {
                addIncompatibility(i, this);
            }
        }
    }

    VariantNames(String id, boolean compositionVariant, VariantNames[] composedOf, VariantNames[] incompatible) {
        this.id = id;
        this.compositionVariant = compositionVariant;

        if (incompatible.length != 0) {
            this.incompatible.addAll(Arrays.asList(incompatible));
            for (VariantNames i : incompatible) {
                addIncompatibility(i, this);
            }
        }
        if (composedOf.length != 0) {
            this.composedOf.addAll(Arrays.asList(composedOf));
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

    public static String getTranslatedVariantName(VariantNames variant) {
        return StatCollector.translateToLocal("variants." + variant.id + ".name");
    }

    public static String getTranslatedVariantName(String ID) {
        return StatCollector.translateToLocal("variants." + ID + ".name");
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
            // sets can't have duplicates, no need to do a contains check.
            first.incompatible.add(second);
            second.incompatible.add(first);
        }
    }

    public static boolean checkIncompatibility(VariantNames first, VariantNames second) {
        if (first != null && second != null) {
            if (first.incompatible.contains(second) || second.incompatible.contains(first)) {
                return true;
            }

            for (VariantNames subVariantsFirst : first.composedOf) {
                if (second.incompatible.contains(subVariantsFirst)) {
                    return true;
                }
            }

            for (VariantNames subVariantsSecond : second.composedOf) {
                if (first.incompatible.contains(subVariantsSecond)) {
                    return true;
                }
            }
        }
        return false;
    }

}
