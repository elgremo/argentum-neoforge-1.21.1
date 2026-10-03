package com.gremo.argentum.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class PaqueteYerbaMateItem extends Item {

    public PaqueteYerbaMateItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        ItemStack remaining = stack.copy();
        remaining.setDamageValue(remaining.getDamageValue() + 1);

        if (remaining.getDamageValue() >= remaining.getMaxDamage()) {
            return ItemStack.EMPTY;
        }

        return remaining;
    }

}