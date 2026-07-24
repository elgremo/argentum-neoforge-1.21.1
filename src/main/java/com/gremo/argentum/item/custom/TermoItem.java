package com.gremo.argentum.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class TermoItem extends Item {

    public TermoItem(Properties properties) {
        super(properties);
    }
    @Override
    public boolean isBarVisible(ItemStack stack) {
        return stack.getDamageValue() > 0;
    }
    @Override
    public int getBarWidth(ItemStack stack) {
        return Math.round(13.0F - (float) stack.getDamageValue() * 13.0F / (float) stack.getMaxDamage());
    }
    @Override
    public int getBarColor(ItemStack stack) {
        return 0x3BA7FF;
    }

}
