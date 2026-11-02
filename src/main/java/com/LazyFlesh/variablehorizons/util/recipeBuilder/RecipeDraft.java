package com.LazyFlesh.variablehorizons.util.recipeBuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import com.LazyFlesh.variablehorizons.util.recipeBuilder.Ingredients.Kinds;

import gregtech.api.enums.GTValues;
import gregtech.api.objects.SubstituteFluidStack;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMetadataKey;
import gregtech.api.util.GTRecipeBuilder;

public final class RecipeDraft implements Registrable {

    private final RecipeMap<?> map;
    private final Ingredients<Object> itemIn = new Ingredients<>(Kinds.ITEM_INPUT);
    private final Ingredients<ItemStack> itemOut = new Ingredients<>(Kinds.ITEM_OUTPUT);
    private final Ingredients<Object> fluidIn = new Ingredients<>(Ingredients.Kinds.FLUID_SUBSTITUTABLE);
    private final Ingredients<FluidStack> fluidOut = new Ingredients<>(Kinds.FLUID);
    private final Map<RecipeMetadataKey<?>, Object> metadata = new LinkedHashMap<>();
    private int duration;
    private long eut;
    private double balance = 1d;

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

    public RecipeDraft fluidInputs(Object... stacks) {
        for (Object stack : stacks) {
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

    /**
     * Scale all inputs
     */
    public RecipeDraft scaleAllInputs(double factor) {
        itemIn.scaleAll(factor);
        fluidIn.scaleAll(factor);
        return this;
    }

    /**
     * Scale all outputs
     */
    public RecipeDraft scaleAllOutputs(double factor) {
        itemOut.scaleAll(factor);
        fluidOut.scaleAll(factor);
        return this;
    }

    /**
     * Scale all inputs except the listed ones
     */
    public RecipeDraft scaleAllInputsExcept(double factor, Object... excluded) {
        List<Object> items = new ArrayList<>();
        List<Object> fluids = new ArrayList<>();
        for (Object object : excluded) {
            if (object instanceof FluidStack || object instanceof SubstituteFluidStack) {
                fluids.add(object);
            } else {
                items.add(object);
            }
        }
        itemIn.scaleAllExcept(factor, items.toArray());
        fluidIn.scaleAllExcept(factor, fluids.toArray());
        return this;
    }

    /**
     * Scale all outputs except the listed ones
     */
    public RecipeDraft scaleAllOutputsExcept(double factor, Object... excluded) {
        List<ItemStack> items = new ArrayList<>();
        List<FluidStack> fluids = new ArrayList<>();
        for (Object object : excluded) {
            if (object instanceof FluidStack) {
                fluids.add((FluidStack) object);
            } else {
                items.add((ItemStack) object);
            }
        }
        itemOut.scaleAllExcept(factor, items.toArray(new ItemStack[0]));
        fluidOut.scaleAllExcept(factor, fluids.toArray(new FluidStack[0]));
        return this;
    }

    /**
     * Scale all inputs (except the listed ones) and, if the combined factor ends up below 1, raise the outputs by
     * 1/factor
     */
    public RecipeDraft scaleAllInputsBalanced(double factor, Object... excluded) {
        List<Object> items = new ArrayList<>();
        List<FluidStack> fluids = new ArrayList<>();
        for (Object object : excluded) {
            if (object instanceof FluidStack) {
                fluids.add((FluidStack) object);
            } else {
                items.add(object);
            }
        }
        itemIn.scaleAllBalanced(factor, items.toArray());
        fluidIn.scaleAllBalanced(factor, fluids.toArray(new FluidStack[0]));
        balance *= factor;
        return this;
    }

    /**
     * Set item input chances
     */
    public RecipeDraft inputChances(int... chances) {
        itemIn.setChances(chances);
        return this;
    }

    /**
     * Set item output chances
     */
    public RecipeDraft outputChances(int... chances) {
        itemOut.setChances(chances);
        return this;
    }

    /**
     * Set fluid input chances
     */
    public RecipeDraft fluidInputChances(int... chances) {
        fluidIn.setChances(chances);
        return this;
    }

    /**
     * Set fluid output chances
     */
    public RecipeDraft fluidOutputChances(int... chances) {
        fluidOut.setChances(chances);
        return this;
    }

    /**
     * Set recipe time
     */
    public RecipeDraft mapDuration(IntUnaryOperator fn) {
        this.duration = fn.applyAsInt(duration);
        return this;
    }

    /**
     * Set power draw
     */
    public RecipeDraft mapEut(LongUnaryOperator fn) {
        this.eut = fn.applyAsLong(eut);
        return this;
    }

    // modifier side
    public Ingredients<Object> itemIn() {
        return itemIn;
    }

    public Ingredients<ItemStack> itemOut() {
        return itemOut;
    }

    public Ingredients<Object> fluidIn() {
        return fluidIn;
    }

    public Ingredients<FluidStack> fluidOut() {
        return fluidOut;
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

    private double outputCompensation() {
        return balance < 1d ? 1d / balance : 1d;
    }

    @Override
    public void register() {
        double compensation = outputCompensation();

        GTRecipeBuilder builder = GTValues.RA.stdBuilder();
        if (!itemIn.isEmpty()) {
            builder.itemInputs(itemIn.build(Object[]::new));
            if (itemIn.hasChances()) {
                builder.inputChances(itemIn.buildChances());
            }
        }
        if (!fluidIn.isEmpty()) {
            builder.fluidInputs(fluidIn.build(Object[]::new));
            if (fluidIn.hasChances()) {
                builder.fluidInputChances(fluidIn.buildChances());
            }
        }
        if (!itemOut.isEmpty()) {
            builder.itemOutputs(itemOut.build(ItemStack[]::new, compensation));
            if (itemOut.hasChances()) {
                builder.outputChances(itemOut.buildChances());
            }
        }
        if (!fluidOut.isEmpty()) {
            builder.fluidOutputs(fluidOut.build(FluidStack[]::new, compensation));
            if (fluidOut.hasChances()) {
                builder.fluidOutputChances(fluidOut.buildChances());
            }
        }

        builder.duration(duration)
            .eut(eut);
        for (Map.Entry<RecipeMetadataKey<?>, Object> e : metadata.entrySet())
            applyMeta(builder, e.getKey(), e.getValue());
        builder.addTo(map);
    }

    @SuppressWarnings("unchecked")
    private static <T> void applyMeta(GTRecipeBuilder builder, RecipeMetadataKey<T> key, Object value) {
        builder.metadata(key, (T) value);
    }
}
