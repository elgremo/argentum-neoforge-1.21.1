package com.gremo.argentum.item.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DulceItem extends Item {
    public DulceItem(Properties properties) { super(properties); }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, world, entity);
        if (!world.isClientSide) {
            // Absorción I por 30s: "azúcar te da aguante"
            entity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 20 * 30, 0));
            // Y un toque de Velocidad corto
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 10, 0));
        }
        return result;
    }
}