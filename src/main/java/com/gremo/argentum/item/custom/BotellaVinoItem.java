package com.gremo.argentum.item.custom;

import com.gremo.argentum.item.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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

public class BotellaVinoItem extends Item {

    public BotellaVinoItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {

        if (!level.isClientSide) {

            // Fuerza I - 30 minutos
            entity.addEffect(new MobEffectInstance(
                    MobEffects.DAMAGE_BOOST,
                    20 * 60 * 30,
                    0
            ));

            // Saturación - 1 minuto
            entity.addEffect(new MobEffectInstance(
                    MobEffects.SATURATION,
                    20 * 60,
                    0
            ));

            // Regeneración I - 10 segundos
            entity.addEffect(new MobEffectInstance(
                    MobEffects.REGENERATION,
                    20 * 10,
                    0
            ));

            // 5% de probabilidad de mareo - 10 segundos
            if (level.random.nextFloat() < 0.05f) {
                entity.addEffect(new MobEffectInstance(
                        MobEffects.CONFUSION,
                        20 * 10,
                        0
                ));
            }
        }

        // Sonido al beber
        level.playSound(
                null,
                entity.getX(),
                entity.getY(),
                entity.getZ(),
                SoundEvents.GENERIC_DRINK,
                SoundSource.PLAYERS,
                1.0F,
                1.0F
        );

        // Consumir la botella
        if (entity instanceof Player player && !player.getAbilities().instabuild) {
            stack.shrink(1);

            player.getInventory().add(
                    new ItemStack(ModItems.BOTELLA_VINO_VACIA.get())
            );
        }

        return stack;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }
}