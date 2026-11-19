package com.LazyFlesh.variablehorizons.util.recipeBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.LazyFlesh.variablehorizons.variants.VariantNames;
import com.github.bsideup.jabel.Desugar;

public final class RecipeModifiers {

    @Desugar
    private record Entry(RecipePhase phase, VariantNames source, Consumer<Registrable> draftConsumer) {}

    private static final Map<String, Supplier<? extends Registrable>> BASE = new LinkedHashMap<>();
    private static final Map<String, List<Entry>> MODIFIERS = new HashMap<>();

    public static void defineBase(String id, Supplier<? extends Registrable> base) {
        if (BASE.put(id, base) != null) {
            throw new IllegalStateException("Duplicate recipe id: " + id);
        }
    }

    public static void modify(String id, VariantNames variant, RecipePhase phase, Consumer<RecipeDraft> draftConsumer) {
        add(id, variant, phase, RecipeDraft.class, draftConsumer);
    }

    public static void modifyAssemblyLine(String id, VariantNames variant, RecipePhase phase,
        Consumer<AsslineDraft> draftConsumer) {
        add(id, variant, phase, AsslineDraft.class, draftConsumer);
    }

    public static void modifyCrafting(String id, VariantNames variant, RecipePhase phase,
        Consumer<CraftingDraft> draftConsumer) {
        add(id, variant, phase, CraftingDraft.class, draftConsumer);
    }

    private static <D extends Registrable> void add(String id, VariantNames variant, RecipePhase phase, Class<D> type,
        Consumer<D> draftConsumer) {
        MODIFIERS.computeIfAbsent(id, k -> new ArrayList<>())
            .add(new Entry(phase, variant, d -> draftConsumer.accept(type.cast(d))));
    }

    public static Registrable resolve(String id, Set<VariantNames> active) {
        // new draft each time, so modifiers never accumulate
        Registrable draft = BASE.get(id)
            .get();
        List<Entry> list = new ArrayList<>(MODIFIERS.getOrDefault(id, Collections.emptyList()));
        list.removeIf(e -> !active.contains(e.source));
        list.sort(Comparator.comparing(e -> e.phase)); // registration order is kept within a phase
        for (Entry entry : list) {
            entry.draftConsumer.accept(draft);
        }
        return draft;
    }

    public static void registerAll(Set<VariantNames> active) {
        for (String id : BASE.keySet()) {
            resolve(id, active).register();
        }
    }
}
