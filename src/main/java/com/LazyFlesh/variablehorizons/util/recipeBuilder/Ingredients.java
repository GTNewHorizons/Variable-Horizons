package com.LazyFlesh.variablehorizons.util.recipeBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import gregtech.api.objects.OreDictItemStack;
import gregtech.api.util.GTUtility;

public final class Ingredients<T> {

    private static final class Entry<T> {

        final T origin;
        T current;
        double factor = 1d;
        int base;
        int chanceBase = 10000;
        double chanceFactor = 1d;

        Entry(T stack, int base) {
            this.origin = stack;
            this.current = stack;
            this.base = base;
        }

        int chance() {
            long chance = Math.round(chanceBase * chanceFactor);
            return (int) Math.max(0, Math.min(10000, chance));
        }
    }

    private final Kind<T> kind;
    private final List<Entry<T>> list = new ArrayList<>();

    Ingredients(Kind<T> kind) {
        this.kind = kind;
    }

    private int indexOf(T target) {
        for (int i = 0; i < list.size(); i++) {
            Entry<T> entry = list.get(i);
            if (kind.same(entry.origin, target) || kind.same(entry.current, target)) {
                return i;
            }
        }
        return -1;
    }

    private Entry<T> require(T target) {
        int i = indexOf(target);
        if (i < 0) {
            throw new IllegalStateException("Ingredient not found in recipe draft: " + target);
        }
        return list.get(i);
    }

    public Ingredients<T> setChance(T target, int chance) {
        Entry<T> entry = require(target);
        entry.chanceBase = chance;
        entry.chanceFactor = 1d;
        return this;
    }

    private Entry<T> make(T stack) {
        return new Entry<>(stack, kind.amount(stack));
    }

    public Ingredients<T> add(T stack) {
        list.add(make(stack));
        return this;
    } // used for base definition

    public Ingredients<T> add(T stack, int chance) {
        Entry<T> entry = make(stack);
        entry.chanceBase = chance;
        list.add(entry);
        return this;
    }

    public Ingredients<T> addFirst(T stack) {
        list.add(0, make(stack));
        return this;
    }

    public Ingredients<T> addFirst(T stack, int chance) {
        Entry<T> entry = make(stack);
        entry.chanceBase = chance;
        list.add(0, entry);
        return this;
    }

    public Ingredients<T> addLast(T stack) {
        list.add(make(stack));
        return this;
    }

    public Ingredients<T> addLast(T stack, int chance) {
        return add(stack, chance);
    }

    public Ingredients<T> addBefore(T anchor, T stack) {
        list.add(Math.max(indexOf(anchor), 0), make(stack));
        return this;
    }

    public Ingredients<T> addBefore(T anchor, T stack, int chance) {
        Entry<T> entry = make(stack);
        entry.chanceBase = chance;
        list.add(Math.max(indexOf(anchor), 0), entry);
        return this;
    }

    public Ingredients<T> addAfter(T anchor, T stack) {
        int i = indexOf(anchor);
        list.add(i < 0 ? list.size() : i + 1, make(stack));
        return this;
    }

    public Ingredients<T> addAfter(T anchor, T stack, int chance) {
        Entry<T> entry = make(stack);
        entry.chanceBase = chance;
        int i = indexOf(anchor);
        list.add(i < 0 ? list.size() : i + 1, entry);
        return this;
    }

    public Ingredients<T> replace(T target, T newStack) {
        Entry<T> entry = require(target);
        entry.current = newStack;
        entry.base = kind.amount(newStack);
        return this;
    }

    public Ingredients<T> replace(T target, T newStack, int chance) {
        replace(target, newStack);
        require(newStack).chanceBase = chance;
        return this;
    }

    public Ingredients<T> scale(T target, double factor) {
        checkFactor(factor);
        require(target).factor *= factor;
        return this;
    }

    public Ingredients<T> scaleChance(T target, double factor) {
        checkFactor(factor);
        require(target).chanceFactor *= factor;
        return this;
    }

    public Ingredients<T> scaleAll(double factor) {
        checkFactor(factor);
        for (Entry<T> entry : list) {
            entry.factor *= factor;
        }
        return this;
    }

    public Ingredients<T> scaleAllChances(double factor) {
        checkFactor(factor);
        for (Entry<T> entry : list) {
            entry.chanceFactor *= factor;
        }
        return this;
    }

    private static void checkFactor(double factor) {
        if (!(factor > 0) || Double.isInfinite(factor)) {
            throw new IllegalArgumentException("Scale factor must be positive and finite: " + factor);
        }
    }

    public Ingredients<T> setAmount(T target, int amount) {
        Entry<T> entry = require(target);
        entry.base = amount;
        entry.factor = 1d;
        return this;
    }

    public Ingredients<T> remove(T target) {
        int i = indexOf(target);
        if (i >= 0) {
            list.remove(i);
        }
        return this;
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public T[] build(IntFunction<T[]> gen) {
        T[] out = gen.apply(list.size());
        for (int i = 0; i < out.length; i++) {
            Entry<T> entry = list.get(i);
            out[i] = kind.withAmount(entry.current, kind.scaled(entry.base, entry.factor));
        }
        return out;
    }

    public Ingredients<T> setChances(int... chances) {
        if (chances.length != list.size()) {
            throw new IllegalArgumentException("Expected " + list.size() + " chances, got " + chances.length);
        }
        for (int i = 0; i < chances.length; i++) {
            list.get(i).chanceBase = chances[i];
        }
        return this;
    }

    public int[] buildChances() {
        int[] out = new int[list.size()];
        for (int i = 0; i < out.length; i++) out[i] = list.get(i)
            .chance();
        return out;
    }

    public boolean hasChances() {
        for (Entry<T> entry : list) {
            if (entry.chance() != 10000) {
                return true;
            }
        }
        return false;
    }

    interface Kind<T> {

        boolean same(T a, T b);

        int amount(T stack);

        T withAmount(T stack, int amount);

        default int scaled(int base, double factor) {
            long rounded = Math.round(base * factor);
            if (rounded > Integer.MAX_VALUE) {
                throw new ArithmeticException("Scaled amount overflows int: " + base + " * " + factor);
            }
            return (int) Math.max(1, rounded);
        }
    }

    static final class Kinds {

        // accepts ItemStack or OreDictItemStack
        static final Kind<Object> ITEM_INPUT = new Kind<>() {

            public boolean same(Object a, Object b) {
                if (a instanceof ItemStack && b instanceof ItemStack) {
                    return GTUtility.areStacksEqual((ItemStack) a, (ItemStack) b);
                }
                if (a instanceof OreDictItemStack && b instanceof OreDictItemStack) {
                    return ((OreDictItemStack) a).mOreName.equals(((OreDictItemStack) b).mOreName);
                }
                return false;
            }

            public int amount(Object stack) {
                return stack instanceof ItemStack ? ((ItemStack) stack).stackSize : ((OreDictItemStack) stack).mAmount;
            }

            public Object withAmount(Object stack, int amount) {
                if (stack instanceof ItemStack) {
                    ItemStack copy = ((ItemStack) stack).copy();
                    copy.stackSize = amount;
                    return copy;
                }
                return new OreDictItemStack(((OreDictItemStack) stack).mOreName, amount);
            }
        };

        static final Kind<ItemStack> ITEM_OUTPUT = new Kind<>() {

            public boolean same(ItemStack a, ItemStack b) {
                return GTUtility.areStacksEqual(a, b);
            }

            public int amount(ItemStack stack) {
                return stack.stackSize;
            }

            public ItemStack withAmount(ItemStack stack, int amount) {
                ItemStack copy = stack.copy();
                copy.stackSize = amount;
                return copy;
            }
        };

        static final Kind<FluidStack> FLUID = new Kind<>() {

            public boolean same(FluidStack a, FluidStack b) {
                return a.isFluidEqual(b);
            }

            public int amount(FluidStack stack) {
                return stack.amount;
            }

            public FluidStack withAmount(FluidStack stack, int amount) {
                return new FluidStack(stack, amount);
            }
        };

    }
}
