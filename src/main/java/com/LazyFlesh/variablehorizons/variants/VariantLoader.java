package com.LazyFlesh.variablehorizons.variants;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.LazyFlesh.variablehorizons.Config.GeneralConfig;
import com.LazyFlesh.variablehorizons.VariableHorizons;
import com.LazyFlesh.variablehorizons.variants.runtime.IRuntimeVariant;
import com.gtnewhorizon.gtnhlib.config.ConfigurationManager;

public abstract class VariantLoader {

    public static void loadActiveVariants() {
        Set<String> active = VariantNames.getActiveVariantNames();
        List<String> toRemove = new ArrayList<>();
        List<String> toAdd = new ArrayList<>();

        VariableHorizons.LOG.info("Loading Variants!");

        for (String var : active) {
            VariantNames variant = VariantNames.getVariantFromID(var);
            if (variant == null) {
                VariableHorizons.LOG.warn("Turning off undefined variant: {}", var);
                active.remove(var);
                toRemove.add(var);
                continue;
            }
            if (toRemove.contains(var)) {
                continue;
            }
            if (variant.incompatible != null) {
                for (VariantNames incompatible : variant.incompatible) {
                    if (active.contains(incompatible.id)) {
                        VariableHorizons.LOG.warn("A variant incompatible with another active variant was detected.");
                        VariableHorizons.LOG.warn("Turning off incompatible variant: {}", incompatible.id);
                        toRemove.add(incompatible.id);
                        // don't break, so all incompatible variants are removed.
                    }
                }
            }

            VariableHorizons.LOG.info("Loading {}", variant.id);
            if (variant.compositionVariant) {
                for (VariantNames name : variant.composedOf) {
                    // make sure it hasn't been loaded already, or it might make duplicate recipes/list elements
                    if (!name.hasLoaded) {
                        toAdd.add(name.id);
                        name.hasLoaded = true;
                        if (name.loaderClass instanceof VariantLoader) name.loaderClass.loadVariant();
                    }
                }
            }
            if (variant.loaderClass instanceof VariantLoader && !variant.hasLoaded) {
                variant.loaderClass.loadVariant();
                // if composition, add composites to list and load them
            } else if (variant.loaderClass == null) {
                // nothing to load, so just mark it as loaded.
                variant.hasLoaded = true;
            }
        }
        // actually remove skipped variants
        for (String s : toRemove) active.remove(s);
        active.addAll(toAdd);
        GeneralConfig.activeVariants = active.toArray(new String[0]);
        ConfigurationManager.save(GeneralConfig.class);
    }

    public static void loadVariantRecipes() {
        for (String var : VariantNames.getActiveVariantNames()) {
            VariantNames variant = VariantNames.getVariantFromID(var);

            if (variant.hasLoaded && variant.loaderClass instanceof VariantLoader) {
                // load recipes from all active variants
                variant.loaderClass.variantRecipes();
            }
        }
    }

    public abstract void loadVariant(VariantNames... activeVariants);

    public abstract void variantRecipes(VariantNames... activeVariants);

    public static String toggleVariant(VariantNames name, boolean state) {
        if (state) {
            if (VariantNames.activeContains(name.id)) {
                return "Variant already active.";
            } else {
                if (name.incompatible != null) {
                    for (String variant : VariantNames.getActiveVariantNames()) {
                        if (VariantNames.checkIncompatibility(VariantNames.getVariantFromID(variant), name)) {
                            return "Variant is incompatible with an active variant.";
                        }
                    }
                }
                // since variants add themselves to their incompatible variants' list of incompatible variants, we don't
                // have to check twice.
                Set<String> active = VariantNames.getActiveVariantNames();
                active.add(name.id);
                // add the composites, too
                if (name.compositionVariant) {
                    for (VariantNames n : name.composedOf) active.add(n.id);
                }
                GeneralConfig.activeVariants = active.toArray(new String[0]);
                ConfigurationManager.save(GeneralConfig.class);
                if (name.loaderClass instanceof IRuntimeVariant) {
                    name.loaderClass.loadVariant();
                    return "Client/world restart may be required for change to take effect.";
                }
                return "Server/instance restart required for change to take effect.";
            }
        } else {
            if (!VariantNames.activeContains(name.id)) {
                return "Variant already inactive.";
            } else {
                Set<String> active = VariantNames.getActiveVariantNames();
                active.remove(name.id);
                // and the composites, too
                if (name.compositionVariant) {
                    for (VariantNames n : name.composedOf) active.remove(n.id);
                }
                GeneralConfig.activeVariants = active.toArray(new String[0]);
                ConfigurationManager.save(GeneralConfig.class);
                return "Server/instance restart required for change to take effect.";
            }
        }
    }
}
