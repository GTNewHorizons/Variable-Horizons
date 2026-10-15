package com.LazyFlesh.variablehorizons.util.recipeBuilder;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import com.LazyFlesh.variablehorizons.util.recipeBuilder.Ingredients.Kinds;

import gregtech.api.enums.GTValues;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMetadataKey;
import gregtech.api.util.GTRecipeBuilder;

public final class RecipeDraft {

    private final RecipeMap<?> map;
    private final Ingredients<Object> itemIn = new Ingredients<>(Kinds.ITEM_INPUT);
    private final Ingredients<ItemStack> itemOut = new Ingredients<>(Kinds.ITEM_OUTPUT);
    private final Ingredients<FluidStack> fluidIn = new Ingredients<>(Kinds.FLUID);
    private final Ingredients<FluidStack> fluidOut = new Ingredients<>(Kinds.FLUID);
    private final Map<RecipeMetadataKey<?>, Object> metadata = new LinkedHashMap<>();
    private int duration;
    private long eut;

    private RecipeDraft(RecipeMap<?> map) {
        this.map = map;
    }

    public static RecipeDraft of(RecipeMap<?> map) {
        return new RecipeDraft(map);
    }

    // builder side
    public RecipeDraft itemInputs(Object... stacks) {
        for (Object stack : stacks) {
            itemIn.add(stack);
        }
        return this;
    }

    public RecipeDraft itemOutputs(ItemStack... stacks) {
        for (ItemStack stack : stacks) {
            itemOut.add(stack);
        }
        return this;
    }

    public RecipeDraft fluidInputs(FluidStack... stacks) {
        for (FluidStack stack : stacks) {
            fluidIn.add(stack);
        }
        return this;
    }

    public RecipeDraft fluidOutputs(FluidStack... stacks) {
        for (FluidStack stack : stacks) {
            fluidOut.add(stack);
        }
        return this;
    }

    public RecipeDraft duration(int ticks) {
        this.duration = ticks;
        return this;
    }

    public RecipeDraft eut(long eut) {
        this.eut = eut;
        return this;
    }

    public <T> RecipeDraft metadata(RecipeMetadataKey<T> key, T value) {
        metadata.put(key, value);
        return this;
    }

    // modifier side
    public Ingredients<Object> itemIn() {
        return itemIn;
    }

    public Ingredients<ItemStack> itemOut() {
        return itemOut;
    }

    public Ingredients<FluidStack> fluidIn() {
        return fluidIn;
    }

    public Ingredients<FluidStack> fluidOut() {
        return fluidOut;
    }

    public RecipeDraft mapDuration(IntUnaryOperator fn) {
        this.duration = fn.applyAsInt(duration);
        return this;
    }

    public RecipeDraft mapEut(LongUnaryOperator fn) {
        this.eut = fn.applyAsLong(eut);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> RecipeDraft updateMetadata(RecipeMetadataKey<T> key, UnaryOperator<T> fn) {
        metadata.put(key, fn.apply((T) metadata.get(key))); // null if the key is absent
        return this;
    }

    public RecipeDraft removeMetadata(RecipeMetadataKey<?> key) {
        metadata.remove(key);
        return this;
    }

    public void register() {
        GTRecipeBuilder builder = GTValues.RA.stdBuilder();
        if (!itemIn.isEmpty()) builder.itemInputs(itemIn.build(Object[]::new));
        if (!fluidIn.isEmpty()) builder.fluidInputs(fluidIn.build(FluidStack[]::new));
        if (!itemOut.isEmpty()) builder.itemOutputs(itemOut.build(ItemStack[]::new));
        if (!fluidOut.isEmpty()) builder.fluidOutputs(fluidOut.build(FluidStack[]::new));
        builder.duration(duration)
            .eut(eut);
        for (Map.Entry<RecipeMetadataKey<?>, Object> entry : metadata.entrySet()) {
            applyMeta(builder, entry.getKey(), entry.getValue());
        }
        builder.addTo(map);
    }

    @SuppressWarnings("unchecked")
    private static <T> void applyMeta(GTRecipeBuilder builder, RecipeMetadataKey<T> key, Object value) {
        builder.metadata(key, (T) value);
    }
}
