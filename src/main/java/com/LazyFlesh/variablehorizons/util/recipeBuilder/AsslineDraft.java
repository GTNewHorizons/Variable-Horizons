package com.LazyFlesh.variablehorizons.util.recipeBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import gregtech.api.objects.OreDictItemStack;
import tectech.recipe.TTRecipeAdder;

public class AsslineDraft implements Registrable {

    private final Ingredients<Object> itemIn = new Ingredients<>(Ingredients.Kinds.ITEM_INPUT);
    private final Ingredients<FluidStack> fluidIn = new Ingredients<>(Ingredients.Kinds.FLUID);
    private final Ingredients<ItemStack> itemOut = new Ingredients<>(Ingredients.Kinds.ITEM_OUTPUT);

    private ItemStack researchItem;
    private long totalComputation;
    private long computationPerSec;
    private int researchEut;
    private int researchAmperage;
    private int duration;
    private int eut;
    private double balance = 1d;

    private AsslineDraft() {}

    public static AsslineDraft of() {
        return new AsslineDraft();
    }

    // builder side
    public AsslineDraft research(ItemStack item, long totalComputation, long computationPerSec, int researchEut,
        int researchAmperage) {
        this.researchItem = item;
        this.totalComputation = totalComputation;
        this.computationPerSec = computationPerSec;
        this.researchEut = researchEut;
        this.researchAmperage = researchAmperage;
        return this;
    }

    public AsslineDraft itemInputs(Object... stacks) {
        for (Object stack : stacks) {
            itemIn.add(stack);
        }
        return this;
    }

    public AsslineDraft fluidInputs(FluidStack... stacks) {
        for (FluidStack stack : stacks) {
            fluidIn.add(stack);
        }
        return this;
    }

    public AsslineDraft itemOutput(ItemStack stack) {
        itemOut.add(stack);
        return this;
    }

    public AsslineDraft duration(int ticks) {
        this.duration = ticks;
        return this;
    }

    public AsslineDraft eut(long eut) {
        this.eut = Math.toIntExact(eut);
        return this;
    }

    /**
     * Scale item output
     */
    public AsslineDraft scaleOutput(double factor) {
        itemOut.scaleAll(factor);
        return this;
    }

    /**
     * Scale all inputs
     */
    public AsslineDraft scaleAllInputs(double factor) {
        itemIn.scaleAll(factor);
        fluidIn.scaleAll(factor);
        return this;
    }

    /**
     * Scale all inputs except the listed ones
     */
    public AsslineDraft scaleAllInputsExcept(double factor, Object... excluded) {
        List<Object> items = new ArrayList<>();
        List<FluidStack> fluids = new ArrayList<>();
        for (Object object : excluded) {
            if (object instanceof FluidStack) {
                fluids.add((FluidStack) object);
            } else {
                items.add(object);
            }
        }
        itemIn.scaleAllExcept(factor, items.toArray());
        fluidIn.scaleAllExcept(factor, fluids.toArray(new FluidStack[0]));
        return this;
    }

    /**
     * Scale all inputs (except the listed ones) and, if the combined factor ends up below 1, raise the output by
     * 1/factor
     */
    public AsslineDraft scaleAllInputsBalanced(double factor, Object... excluded) {
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

    // modifier side
    public Ingredients<Object> itemIn() {
        return itemIn;
    }

    public Ingredients<FluidStack> fluidIn() {
        return fluidIn;
    }

    public Ingredients<ItemStack> itemOut() {
        return itemOut;
    }

    public AsslineDraft researchItem(ItemStack item) {
        this.researchItem = item;
        return this;
    }

    public AsslineDraft mapDuration(IntUnaryOperator fn) {
        this.duration = fn.applyAsInt(duration);
        return this;
    }

    public AsslineDraft mapEut(IntUnaryOperator fn) {
        this.eut = fn.applyAsInt(eut);
        return this;
    }

    public AsslineDraft mapTotalComputation(LongUnaryOperator fn) {
        this.totalComputation = fn.applyAsLong(totalComputation);
        return this;
    }

    public AsslineDraft mapComputationPerSec(LongUnaryOperator fn) {
        this.computationPerSec = fn.applyAsLong(computationPerSec);
        return this;
    }

    public AsslineDraft mapResearchEut(IntUnaryOperator fn) {
        this.researchEut = fn.applyAsInt(researchEut);
        return this;
    }

    public AsslineDraft mapResearchAmperage(IntUnaryOperator fn) {
        this.researchAmperage = fn.applyAsInt(researchAmperage);
        return this;
    }

    private double outputCompensation() {
        return balance < 1d ? 1d / balance : 1d;
    }

    @Override
    public void register() {
        ItemStack[] output = itemOut.build(ItemStack[]::new, outputCompensation());
        if (output.length != 1) {
            throw new IllegalStateException("Assembly line recipe needs exactly one output, has " + output.length);
        }

        Object[] inputs = itemIn.build(Object[]::new);
        for (int i = 0; i < inputs.length; i++) {
            if (inputs[i] instanceof OreDictItemStack oreStack) {
                inputs[i] = new Object[] { oreStack.mOreName, oreStack.mAmount };
            }
        }

        FluidStack[] fluids = fluidIn.build(FluidStack[]::new);

        boolean ok = TTRecipeAdder.addResearchableAssemblylineRecipeLong(
            researchItem,
            totalComputation,
            computationPerSec,
            researchEut,
            researchAmperage,
            inputs,
            fluids,
            output[0],
            duration,
            eut);

        if (!ok) {
            throw new IllegalStateException(
                "Assembly line recipe rejected for output " + output[0].getDisplayName()
                    + " (inputs="
                    + inputs.length
                    + ", fluids="
                    + fluids.length
                    + ", duration="
                    + duration
                    + ", eut="
                    + eut
                    + ")");
        }
    }
}
