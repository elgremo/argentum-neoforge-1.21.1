package com.gremo.argentum.item.custom;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class FernetItem extends Item {

    public FernetItem(Properties properties) {
        super(properties);
    }

    // Define cuánto tiempo tarda en consumirse (20 ticks = 1 segundo)
    // 32 es el estándar de la comida/bebida de Minecraft (aprox 1.6 segundos)
    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 32;
    }

    // Define la animación (DRINK para que se lo lleve a la boca)
    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    // Qué pasa cuando termina de "beber"
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        if (!world.isClientSide) {
            // Aplicar efectos
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 30, 0));
            entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 * 20, 0));

            if (world.random.nextFloat() < 0.10f) {
                entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20 * 5, 0));
            }

            // Desgaste de durabilidad
            if (entity instanceof Player player) {
                stack.hurtAndBreak(
                        1,
                        player,
                        player.getUsedItemHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND
                );

                // Si se rompe, el stack ya se encarga, pero aseguramos que desaparezca si llega a 0
                if (stack.getDamageValue() >= stack.getMaxDamage()) {
                    stack.setCount(0);
                }
            }
        }

        // Sonido de beber al terminar
        world.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS, 1.0f, 1.0f);

        return stack;
    }

    // Inicia la acción de beber al hacer click derecho
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }
}