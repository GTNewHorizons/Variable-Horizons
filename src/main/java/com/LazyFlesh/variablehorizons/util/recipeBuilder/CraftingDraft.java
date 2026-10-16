package com.LazyFlesh.variablehorizons.util.recipeBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.item.ItemStack;

import com.LazyFlesh.variablehorizons.util.recipeBuilder.Ingredients.CraftIngredients;

import gregtech.api.util.GTModHandler;

public final class CraftingDraft implements Registrable {

    private static final String SYMBOLS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private static final class Slot {

        final Object origin;
        Object current;
        int count;

        Slot(Object ingredient, int count) {
            this.origin = ingredient;
            this.current = ingredient;
            this.count = count;
        }

        boolean matches(Object target) {
            return CraftIngredients.same(origin, target) || CraftIngredients.same(current, target);
        }
    }

    private final boolean shaped;
    private ItemStack output;
    private int outputBase;
    private double outputFactor = 1d;
    private long bits;

    private String[] pattern;
    private final Map<Character, Slot> symbols = new LinkedHashMap<>();
    private final List<Slot> slots = new ArrayList<>();

    private CraftingDraft(boolean shaped, ItemStack output) {
        this.shaped = shaped;
        this.output = output.copy();
        this.outputBase = output.stackSize;
    }

    public static CraftingDraft shaped(ItemStack output) {
        return new CraftingDraft(true, output);
    }

    public static CraftingDraft shapeless(ItemStack output) {
        return new CraftingDraft(false, output);
    }

    // builder side
    /** Shaped only, the recipe pattern as seen in the crafting table, divided into rows */
    public CraftingDraft pattern(String... rows) {
        requireShaped();
        this.pattern = rows.clone();
        return this;
    }

    /** Shaped only, defines what a character in the used recipe pattern is */
    public CraftingDraft where(char symbol, Object ingredient) {
        requireShaped();
        symbols.put(symbol, new Slot(ingredient, 1));
        return this;
    }

    /** Shapeless only, this defines what goes into the recipe */
    public CraftingDraft ingredients(Object... ings) {
        requireShapeless();
        for (Object ing : ings) {
            Slot existing = null;
            for (Slot slot : slots) {
                if (CraftIngredients.same(slot.current, ing)) {
                    existing = slot;
                    break;
                }
            }
            if (existing != null) {
                existing.count++;
            } else slots.add(new Slot(ing, 1));
        }
        return this;
    }

    /** Set GT crafting table recipe bits */
    public CraftingDraft recipeBits(long bits) {
        this.bits = bits;
        return this;
    }

    // modifier side
    /** Replace all copies of an input item with something else */
    public CraftingDraft replaceAll(Object target, Object newIngredient) {
        for (Slot slot : matching(target)) {
            slot.current = newIngredient;
        }
        return this;
    }

    /** Remove all copies of an input item */
    public CraftingDraft remove(Object target) {
        List<Slot> hit = matching(target);
        if (shaped) {
            for (Map.Entry<Character, Slot> entry : new ArrayList<>(symbols.entrySet())) {
                if (!hit.contains(entry.getValue())) {
                    continue;
                }
                for (int r = 0; r < pattern.length; r++) {
                    pattern[r] = pattern[r].replace(entry.getKey(), ' ');
                }
                symbols.remove(entry.getKey());
            }
        } else {
            slots.removeAll(hit);
        }
        return this;
    }

    /** Shapeless only, add an input item to the recipe */
    public CraftingDraft add(Object ingredient) {
        return add(ingredient, 1);
    }

    /** Shapeless only, add multiple copies of an input item to the recipe */
    public CraftingDraft add(Object ingredient, int count) {
        requireShapeless();
        slots.add(new Slot(ingredient, count));
        return this;
    }

    /** Shapeless only, set how many slots an input item should occupy */
    public CraftingDraft setCount(Object target, int count) {
        requireShapeless();
        if (count < 1) {
            throw new IllegalArgumentException("Count must be at least 1: " + count);
        }
        for (Slot slot : matching(target)) {
            slot.count = count;
        }
        return this;
    }

    /**
     * Shapeless only, change the item count by an amount (stays at least 1, use remove() to fully remove an ingredient)
     */
    public CraftingDraft addCount(Object target, int delta) {
        requireShapeless();
        for (Slot slot : matching(target)) {
            slot.count = Math.max(1, slot.count + delta);
        }
        return this;
    }

    /** Shaped only, put an ingredient into an empty cell of the pattern */
    public CraftingDraft fill(int row, int column, Object ingredient) {
        requireShaped();
        if (row < 0 || row >= pattern.length || column < 0 || column >= pattern[row].length()) {
            throw new IllegalArgumentException("Cell (" + row + "," + column + ") is outside the pattern");
        }
        if (pattern[row].charAt(column) != ' ') {
            throw new IllegalStateException("Cell (" + row + "," + column + ") is already occupied");
        }
        char symbol = freeSymbol();
        symbols.put(symbol, new Slot(ingredient, 1));
        StringBuilder builder = new StringBuilder(pattern[row]);
        builder.setCharAt(column, symbol);
        pattern[row] = builder.toString();
        return this;
    }

    /** Multiply the item output by some amount */
    public CraftingDraft scaleOutput(double factor) {
        if (!(factor > 0) || Double.isInfinite(factor)) {
            throw new IllegalArgumentException("Scale factor must be positive and finite: " + factor);
        }
        outputFactor *= factor;
        return this;
    }

    /** Set the item output to an amount */
    public CraftingDraft setOutputAmount(int amount) {
        outputBase = amount;
        outputFactor = 1d;
        return this;
    }

    /** Replace the item output with something else */
    public CraftingDraft replaceOutput(ItemStack stack) {
        output = stack.copy();
        outputBase = stack.stackSize;
        return this;
    }

    private List<Slot> allSlots() {
        return shaped ? new ArrayList<>(symbols.values()) : slots;
    }

    private List<Slot> matching(Object target) {
        List<Slot> hit = new ArrayList<>();
        for (Slot slot : allSlots()) {
            if (slot.matches(target)) {
                hit.add(slot);
            }
        }
        if (hit.isEmpty())
            throw new IllegalStateException("Ingredient not found in crafting draft: " + describe(target));
        return hit;
    }

    private char freeSymbol() {
        for (char c : SYMBOLS.toCharArray()) {
            if (!symbols.containsKey(c)) {
                return c;
            }
        }
        throw new IllegalStateException("No free pattern symbol left");
    }

    private void requireShaped() {
        if (!shaped) {
            throw new UnsupportedOperationException("Only for shaped recipes");
        }
    }

    private void requireShapeless() {
        if (shaped) {
            throw new UnsupportedOperationException("Only for shapeless recipes");
        }
    }

    private static String describe(Object object) {
        if (object instanceof ItemStack) {
            return ((ItemStack) object).getUnlocalizedName() + ":" + ((ItemStack) object).getItemDamage();
        }
        return String.valueOf(object);
    }

    private static Object copyForRecipe(Object ingredient) {
        if (ingredient instanceof ItemStack) {
            ItemStack copy = ((ItemStack) ingredient).copy();
            copy.stackSize = 1;
            return copy;
        }
        String oreDict = CraftIngredients.oreName(ingredient);
        if (oreDict != null) {
            return oreDict;
        }
        return ingredient;
    }

    private ItemStack buildOutput() {
        int amount = (int) Math.max(1, Math.round(outputBase * outputFactor));
        ItemStack out = output.copy();
        out.stackSize = Math.min(amount, out.getMaxStackSize());
        return out;
    }

    private String[] trimmedPattern() {
        int top = Integer.MAX_VALUE, bottom = -1, left = Integer.MAX_VALUE, right = -1;
        for (int r = 0; r < pattern.length; r++) {
            for (int c = 0; c < pattern[r].length(); c++) {
                if (pattern[r].charAt(c) != ' ') {
                    top = Math.min(top, r);
                    bottom = Math.max(bottom, r);
                    left = Math.min(left, c);
                    right = Math.max(right, c);
                }
            }
        }
        if (bottom < 0) {
            throw new IllegalStateException("Shaped recipe has no ingredients left");
        }
        String[] out = new String[bottom - top + 1];
        for (int r = top; r <= bottom; r++) {
            StringBuilder row = new StringBuilder(pattern[r]);
            while (row.length() <= right) {
                row.append(' ');
            }
            out[r - top] = row.substring(left, right + 1);
        }
        return out;
    }

    private Object[] buildShapedArgs() {
        String[] rows = trimmedPattern();
        Set<Character> used = new LinkedHashSet<>();
        for (String row : rows) {
            for (char c : row.toCharArray()) {
                if (c != ' ') {
                    used.add(c);
                }
            }
        }

        List<Object> args = new ArrayList<>(Arrays.asList((Object[]) rows));
        for (char c : used) {
            Slot slot = symbols.get(c);
            if (slot == null) {
                throw new IllegalStateException("Pattern symbol '" + c + "' has no ingredient");
            }
            args.add(c);
            args.add(copyForRecipe(slot.current));
        }
        return args.toArray();
    }

    private Object[] buildShapelessArgs() {
        List<Object> out = new ArrayList<>();
        for (Slot slot : slots) {
            Object ingredient = copyForRecipe(slot.current);
            for (int i = 0; i < slot.count; i++) {
                out.add(ingredient);
            }
        }
        if (out.isEmpty()) {
            throw new IllegalStateException("Shapeless recipe has no ingredients left");
        }
        if (out.size() > 9) {
            throw new IllegalStateException("Shapeless recipe needs " + out.size() + " slots (max 9)");
        }
        return out.toArray();
    }

    @Override
    public void register() {
        ItemStack result = buildOutput();
        Object[] recipe = shaped ? buildShapedArgs() : buildShapelessArgs();

        boolean ok = shaped ? GTModHandler.addCraftingRecipe(result, bits, recipe)
            : GTModHandler.addShapelessCraftingRecipe(result, bits, recipe);
        if (!ok) {
            throw new IllegalStateException("Crafting recipe for " + describe(result) + " was not added");
        }
    }
}
