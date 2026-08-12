package com.gremo.argentum.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class CuchilloItem extends SwordItem {

    public CuchilloItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack,
                                TooltipContext context,
                                List<Component> tooltipComponents,
                                TooltipFlag tooltipFlag) {

        tooltipComponents.add(Component.translatable("tooltip.cuchillo.tooltip"));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    /**
     * El cuchillo no se consume al craftear.
     */
    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {

        ItemStack copy = stack.copy();

        copy.setDamageValue(copy.getDamageValue() + 1);

        return copy;
    }

}