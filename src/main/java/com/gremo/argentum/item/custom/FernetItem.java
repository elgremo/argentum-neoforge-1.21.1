package com.gremo.argentum.item.custom;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class FernetItem extends Item {
    private final SoundEvent customDrinkSound;

    // CONSTRUCTOR 1: El que usas en ModItems (solo con properties)
    public FernetItem(Properties properties) {
        super(properties);
        this.customDrinkSound = null;
    }

    // CONSTRUCTOR 2: Por si algun dia queres pasarle un sonido custom
    public FernetItem(Properties properties, SoundEvent customDrinkSound) {
        super(properties);
        this.customDrinkSound = customDrinkSound;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 32;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        if (!world.isClientSide && entity instanceof Player player) {
            // Nutrición
            player.getFoodData().eat(8, 0.8f);

            // Efectos fijos 10s
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 0));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 0));

            // 10% Chance de Nausea
            RandomSource rnd = world.getRandom();
            if (rnd.nextFloat() < 0.10f) {
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
            }

            // Sonido custom si existe
            if (customDrinkSound != null) {
                world.playSound(null, player.blockPosition(), customDrinkSound, SoundSource.PLAYERS, 1.0F, 1.0F);
            }

            // Daño de durabilidad (3 usos)
            stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(player.getUsedItemHand()));
        }
        return stack;
    }
}