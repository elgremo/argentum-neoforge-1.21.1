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

            int damage = stack.getDamageValue() + 1;

            stack.setDamageValue(damage);

            if (damage >= stack.getMaxDamage()) {
                return new ItemStack(ModItems.BOTELLA_VINO_VACIA.get());
            }

            return stack;
        }

        return stack;
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {

        ItemStack remaining = stack.copy();

        remaining.setDamageValue(
                remaining.getDamageValue() + 1
        );

        if (remaining.getDamageValue() >= remaining.getMaxDamage()) {
            return new ItemStack(ModItems.BOTELLA_VINO_VACIA.get());
        }

        return remaining;
    }


    @Override
    public boolean isBarVisible(ItemStack stack) {
        return stack.getDamageValue() > 0;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Math.round(
                13.0F - (float) stack.getDamageValue() * 13.0F
                        / (float) stack.getMaxDamage()
        );
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 0x8A2BE2;
    }
}