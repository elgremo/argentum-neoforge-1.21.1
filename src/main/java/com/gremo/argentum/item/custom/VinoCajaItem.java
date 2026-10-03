package com.gremo.argentum.item.custom;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class VinoCajaItem extends Item {
    public VinoCajaItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        if (!world.isClientSide) {
            if (entity instanceof Player player) {
                int nutrition = 3;
                float saturationModifier = 0.5f;
                float saturation = nutrition * saturationModifier * 2.0f; // = 3.0
                player.getFoodData().eat(nutrition, saturation);
            }

            if (world.random.nextFloat() < 0.5f) {
                entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20 * 5, 0));
            }
        }

        if (!(entity instanceof Player player) || !player.hasInfiniteMaterials()) {
            stack.hurtAndBreak(1, entity, EquipmentSlot.MAINHAND);
        }

        return stack;
    }

    // Animación de beber (no de comer)
    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    // Duración del trago (32 ticks = 1.6s, igual que una poción)
    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 32;
    }

    // Sonido al beber (se reproduce automáticamente mientras usás el ítem)
    @Override
    public SoundEvent getDrinkingSound() {
        return SoundEvents.GENERIC_DRINK;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }
}