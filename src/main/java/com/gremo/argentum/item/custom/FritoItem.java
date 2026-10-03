package com.gremo.argentum.item.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FritoItem extends Item {
    public FritoItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, world, entity);

        if (!world.isClientSide) {
            // Velocidad I por 20s: comida callejera, salís disparado
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 20, 0));
        }
        return result;
    }
}