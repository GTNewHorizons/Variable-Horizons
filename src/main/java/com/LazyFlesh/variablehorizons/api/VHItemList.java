package com.LazyFlesh.variablehorizons.api;

import static gregtech.GTLoggers.GT_FML_LOGGER;
import static gregtech.api.enums.GTValues.NI;
import static gregtech.api.util.GTRecipeBuilder.WILDCARD;

import java.util.Locale;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.IItemRenderer;

import com.LazyFlesh.variablehorizons.VariableHorizons;

import gregtech.GTMod;
import gregtech.api.interfaces.IItemContainer;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;

public enum VHItemList implements IItemContainer {

    ManufacturingTable;

    private ItemStack stack;
    private boolean hasNotBeenSet;
    private boolean deprecated;
    private boolean warned;

    VHItemList() {
        hasNotBeenSet = true;
    }

    VHItemList(boolean isDeprecated) {
        if (isDeprecated) {
            deprecated = true;
            hasNotBeenSet = true;
        }
    }

    @Override
    public IItemContainer set(Item item) {
        hasNotBeenSet = false;
        if (item == null) return this;
        ItemStack aStack = new ItemStack(item, 1, 0);
        stack = GTUtility.copyAmount(1, aStack);
        return this;
    }

    @Override
    public IItemContainer set(ItemStack aStack) {
        hasNotBeenSet = false;
        stack = GTUtility.copyAmount(1, aStack);
        return this;
    }

    @Override
    public IItemContainer hidden() {
        codechicken.nei.api.API.hideItem(get(1L));
        return this;
    }

    @Override
    public Item getItem() {
        sanityCheck();
        if (GTUtility.isStackInvalid(stack)) return null;
        return stack.getItem();
    }

    @Override
    public Block getBlock() {
        sanityCheck();
        return GTUtility.getBlockFromItem(getItem());
    }

    @Override
    public final boolean hasBeenSet() {
        return !hasNotBeenSet;
    }

    @Override
    public boolean isStackEqual(Object stack) {
        return isStackEqual(stack, false, false);
    }

    @Override
    public boolean isStackEqual(Object stack, boolean wildcard, boolean ignoreNBT) {
        if (deprecated && !warned) {
            GT_FML_LOGGER.error(new Exception(this + " is now deprecated"));
            // warn only once
            warned = true;
        }
        if (GTUtility.isStackInvalid(stack)) return false;
        return GTUtility.areUnificationsEqual((ItemStack) stack, wildcard ? getWildcard(1) : get(1), ignoreNBT);
    }

    @Override
    public ItemStack get(long amount, Object... replacements) {
        sanityCheck();
        if (GTUtility.isStackInvalid(stack)) {
            GT_FML_LOGGER.debug("Object in the ItemList is null at:");
            GT_FML_LOGGER.debug(new NullPointerException());
            return GTUtility.copyAmount(amount, replacements);
        }
        return GTUtility.copyAmount(amount, GTOreDictUnificator.get(stack));
    }

    @Override
    public ItemStack getWildcard(long amount, Object... replacements) {
        sanityCheck();
        if (GTUtility.isStackInvalid(stack)) return GTUtility.copyAmount(amount, replacements);
        return GTUtility.copyAmountAndMetaData(amount, WILDCARD, GTOreDictUnificator.get(stack));
    }

    @Override
    public ItemStack getUndamaged(long amount, Object... replacements) {
        sanityCheck();
        if (GTUtility.isStackInvalid(stack)) return GTUtility.copyAmount(amount, replacements);
        return GTUtility.copyAmountAndMetaData(amount, 0, GTOreDictUnificator.get(stack));
    }

    @Override
    public ItemStack getAlmostBroken(long amount, Object... replacements) {
        sanityCheck();
        if (GTUtility.isStackInvalid(stack)) return GTUtility.copyAmount(amount, replacements);
        return GTUtility.copyAmountAndMetaData(amount, stack.getMaxDamage() - 1, GTOreDictUnificator.get(stack));
    }

    @Override
    public ItemStack getWithName(long amount, String displayName, Object... replacements) {
        ItemStack stack = get(1, replacements);
        if (GTUtility.isStackInvalid(stack)) return NI;

        // CamelCase alphanumeric words from aDisplayName
        StringBuilder camelCasedDisplayNameBuilder = new StringBuilder();
        final String[] displayNameWords = displayName.split("\\W");
        for (String word : displayNameWords) {
            if (!word.isEmpty()) camelCasedDisplayNameBuilder.append(
                word.substring(0, 1)
                    .toUpperCase(Locale.US));
            if (word.length() > 1) camelCasedDisplayNameBuilder.append(
                word.substring(1)
                    .toLowerCase(Locale.US));
        }
        if (camelCasedDisplayNameBuilder.length() == 0) {
            // CamelCased DisplayName is empty, so use hash of aDisplayName
            camelCasedDisplayNameBuilder.append(((Long) (long) displayName.hashCode()));
        }

        // Construct a translation key from UnlocalizedName and CamelCased DisplayName
        final String key = stack.getUnlocalizedName() + ".with." + camelCasedDisplayNameBuilder + ".name";

        stack.setStackDisplayName(StatCollector.translateToLocalFormatted(key, displayName));
        return GTUtility.copyAmount((int) amount, stack);
    }

    @Override
    public ItemStack getWithCharge(long aAmount, int aEnergy, Object... aReplacements) {
        ItemStack stack = get(1, aReplacements);
        if (GTUtility.isStackInvalid(stack)) return null;
        GTModHandler.chargeElectricItem(stack, aEnergy, Integer.MAX_VALUE, true, false);
        return GTUtility.copyAmount(aAmount, stack);
    }

    @Override
    public ItemStack getWithDamage(long amount, long metaValue, Object... replacements) {
        sanityCheck();
        if (GTUtility.isStackInvalid(stack)) return GTUtility.copyAmount(amount, replacements);
        return GTUtility.copyAmountAndMetaData(amount, metaValue, GTOreDictUnificator.get(stack));
    }

    @Override
    public IItemContainer registerOre(Object... oreNames) {
        sanityCheck();
        for (Object oreName : oreNames) GTOreDictUnificator.registerOre(oreName, get(1));
        return this;
    }

    @Override
    public IItemContainer registerWildcardAsOre(Object... oreNames) {
        sanityCheck();
        for (Object oreName : oreNames) GTOreDictUnificator.registerOre(oreName, getWildcard(1));
        return this;
    }

    /**
     * Returns the internal stack. This method is unsafe. It's here only for quick operations. DON'T CHANGE THE RETURNED
     * VALUE!
     */
    public ItemStack getInternalStack_unsafe() {
        return stack;
    }

    @Override
    public IItemContainer setRender(IItemRenderer renderer) {
        if (GTMod.proxy.isClientSide()) {
            GTMod.clientProxy().metaItemRenderer
                .registerSpecialRenderer(this.getItem(), this.getInternalStack_unsafe(), renderer);
        }
        return this;
    }

    private void sanityCheck() {
        if (hasNotBeenSet)
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        if (deprecated && !warned) {
            VariableHorizons.LOG.error(new Exception(this + " is now deprecated"));
            // warn only once
            warned = true;
        }
    }
}
