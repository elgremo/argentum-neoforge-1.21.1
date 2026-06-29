package com.gremo.argentum.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.awt.*;

public class CopaMundoItem extends Item {

    public CopaMundoItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack,
                                TooltipContext context,
                                List<Component> tooltipComponents,
                                TooltipFlag tooltipFlag) {

        tooltipComponents.add(
                Component.literal("Copa del Mundo obtenida por Kempes, Maradona y Messi")
                        .withStyle(ChatFormatting.DARK_PURPLE)
        );

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}

