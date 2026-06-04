package com.gremo.argentum.item.custom;

import com.gremo.argentum.item.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class MateItem extends Item {

    public MateItem(Properties properties) {
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
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        InteractionHand otherHand = hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        ItemStack termoStack = player.getItemInHand(otherHand);

        boolean hasValidTermo = termoStack != null && !termoStack.isEmpty()
                && (termoStack.getItem() == ModItems.TERMO.get() || termoStack.getItem() == ModItems.TERMO_ARGENTO.get());

        if (!hasValidTermo) {
            return InteractionResultHolder.fail(player.getItemInHand(hand));
        }

        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        // NO llamar a super.finishUsingItem(...) para evitar que el item se consuma.
        if (entity instanceof Player player) {
            if (!world.isClientSide) {
                // Aplicamos alimentación manualmente: igual stats que el bife vanilla
                // Nutrition 8, saturation 0.8f
                player.getFoodData().eat(8, 0.8f);

                // Desgastar el TERMO en la otra mano (si existe y es válido)
                InteractionHand usedHand = player.getUsedItemHand();
                InteractionHand otherHand = usedHand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
                ItemStack termoStack = player.getItemInHand(otherHand);

                if (termoStack != null && !termoStack.isEmpty()
                        && (termoStack.getItem() == ModItems.TERMO.get() || termoStack.getItem() == ModItems.TERMO_ARGENTO.get())) {

                    termoStack.hurtAndBreak(1, player,
                            otherHand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);

                    // Evitamos que quede un termo "roto" en la mano y que dropee
                    if (termoStack.getMaxDamage() > 0 && termoStack.getDamageValue() >= termoStack.getMaxDamage()) {
                        termoStack.setCount(0);
                    }
                }
            }

            // Sonido de beber en cliente y servidor
            world.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                    SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS, 1.0f, 1.0f);
        }

        // Retornamos el mismo stack sin modificar su cantidad ni durabilidad (el mate NO se rompe)
        return stack;
    }
}