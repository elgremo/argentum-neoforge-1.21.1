package com.gremo.argentum.item.custom;

import com.gremo.argentum.item.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PavaCalienteItem extends Item {

    public PavaCalienteItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

        InteractionHand otherHand = hand == InteractionHand.MAIN_HAND
                ? InteractionHand.OFF_HAND
                : InteractionHand.MAIN_HAND;

        ItemStack otherStack = player.getItemInHand(otherHand);
        ItemStack pavaStack = player.getItemInHand(hand);

        if (!level.isClientSide) {

            // Termo común
            if (otherStack.is(ModItems.TERMO_VACIO.get())) {

                player.setItemInHand(otherHand,
                        new ItemStack(ModItems.TERMO.get()));

                player.setItemInHand(hand,
                        new ItemStack(ModItems.PAVA.get()));

                player.level().playSound(
                        null,
                        player.blockPosition(),
                        SoundEvents.BOTTLE_FILL,
                        SoundSource.PLAYERS,
                        1.0F,
                        1.0F
                );


                return InteractionResultHolder.success(player.getItemInHand(hand));
            }


            // Termo Argento
            if (otherStack.is(ModItems.TERMO_ARGENTO_VACIO.get())) {

                player.setItemInHand(otherHand,
                        new ItemStack(ModItems.TERMO_ARGENTO.get()));

                player.setItemInHand(hand,
                        new ItemStack(ModItems.PAVA.get()));

                player.level().playSound(
                        null,
                        player.blockPosition(),
                        SoundEvents.BOTTLE_FILL,
                        SoundSource.PLAYERS,
                        1.0F,
                        1.0F
                );

                return InteractionResultHolder.success(player.getItemInHand(hand));
            }

        }

        return InteractionResultHolder.pass(pavaStack);
    }
    @Override
    public boolean isBarVisible(ItemStack stack) {
        return stack.getDamageValue() > 0;
    }
    @Override
    public int getBarWidth(ItemStack stack) {
        return Math.round(13.0F - (float) stack.getDamageValue() * 13.0F / (float) stack.getMaxDamage());
    }
    @Override
    public int getBarColor(ItemStack stack) {
        return 0x3BA7FF;
    }
}