package com.gremo.argentum.item.custom;

import com.gremo.argentum.item.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class CopaVinoVaciaItem extends Item {

    public CopaVinoVaciaItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 5;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {

        InteractionHand otherHand = hand == InteractionHand.MAIN_HAND
                ? InteractionHand.OFF_HAND
                : InteractionHand.MAIN_HAND;

        ItemStack bottleStack = player.getItemInHand(otherHand);

        boolean hasBottle =
                !bottleStack.isEmpty() &&
                        bottleStack.is(ModItems.BOTELLA_VINO_LLENA.get());

        if (!hasBottle) {

            if (!world.isClientSide) {
                player.displayClientMessage(
                        Component.translatable("message.argentum.need_bottle"),
                        true
                );
            }

            return InteractionResultHolder.fail(player.getItemInHand(hand));
        }

        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {

        if (!(entity instanceof Player player)) {
            return stack;
        }

        InteractionHand usedHand = player.getUsedItemHand();
        InteractionHand otherHand = usedHand == InteractionHand.MAIN_HAND
                ? InteractionHand.OFF_HAND
                : InteractionHand.MAIN_HAND;

        ItemStack bottleStack = player.getItemInHand(otherHand);

        if (!world.isClientSide) {

            // La copa vacía pasa a ser copa con vino
            ItemStack copaLlena = new ItemStack(ModItems.COPA_VINO.get());

            if (stack.getCount() == 1) {

                player.setItemInHand(usedHand, copaLlena);

            } else {

                stack.shrink(1);

                if (!player.getInventory().add(copaLlena)) {
                    player.drop(copaLlena, false);
                }
            }

            // Consumir un uso de la botella
            int bottleDamage = bottleStack.getDamageValue() + 1;
            bottleStack.setDamageValue(bottleDamage);

            // Si se terminó el vino, devolver botella vacía
            if (bottleDamage >= bottleStack.getMaxDamage()) {

                player.setItemInHand(
                        otherHand,
                        new ItemStack(ModItems.BOTELLA_VINO_VACIA.get())
                );
            }
        }

        world.playSound(
                null,
                entity.getX(),
                entity.getY(),
                entity.getZ(),
                SoundEvents.BOTTLE_FILL,
                SoundSource.PLAYERS,
                1.0F,
                1.0F
        );

        return player.getItemInHand(usedHand);
    }
}