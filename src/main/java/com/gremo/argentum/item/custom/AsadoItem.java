package com.gremo.argentum.item.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AsadoItem extends Item {
    public AsadoItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, world, entity);

        if (!world.isClientSide) {
            // Fuerza I por 15s: se siente como "proteína + parrilla"
            entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 * 15, 0));
        }
        return result;
    }
}