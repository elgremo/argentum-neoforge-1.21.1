package com.gremo.argentum.item.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CrudoItem extends Item {
    public CrudoItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, world, entity);

        if (!world.isClientSide) {
            // Hunger: 15 segundos (300 ticks), amplificador 0 (Hunger I)
            entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 20 * 5, 0));
            // Nausea (Confusion): 6 segundos (120 ticks), amplificador 0
            entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20 * 2, 0));
        }

        return result;
    }
}