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

        InteractionHand otherHand = hand == InteractionHand.MAIN_HAND
                ? InteractionHand.OFF_HAND
                : InteractionHand.MAIN_HAND;

        ItemStack waterStack = player.getItemInHand(otherHand);

        boolean hasValidWater =
                !waterStack.isEmpty() &&
                        (
                                waterStack.is(ModItems.PAVA_CALIENTE.get()) ||
                                        waterStack.is(ModItems.TERMO.get()) ||
                                        waterStack.is(ModItems.TERMO_ARGENTO.get())
                        );

        if (!hasValidWater) {

            if (!world.isClientSide) {
                player.displayClientMessage(
                        Component.translatable("message.argentum.need_termo")
                                .withStyle(ChatFormatting.RED),
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

        if (!world.isClientSide) {

            // Alimentar jugador
            player.getFoodData().eat(8, 0.8f);

            InteractionHand usedHand = player.getUsedItemHand();
            InteractionHand otherHand = usedHand == InteractionHand.MAIN_HAND
                    ? InteractionHand.OFF_HAND
                    : InteractionHand.MAIN_HAND;

            ItemStack waterStack = player.getItemInHand(otherHand);

            // Consumir un uso del agua
            int waterDamage = waterStack.getDamageValue() + 1;
            waterStack.setDamageValue(waterDamage);

            if (waterDamage >= waterStack.getMaxDamage()) {

                if (waterStack.is(ModItems.PAVA_CALIENTE.get())) {

                    player.setItemInHand(otherHand,
                            new ItemStack(ModItems.PAVA.get()));

                    player.displayClientMessage(
                            Component.translatable("message.argentum.pava_empty"),
                            true
                    );

                } else if (waterStack.is(ModItems.TERMO.get())) {

                    player.setItemInHand(otherHand,
                            new ItemStack(ModItems.TERMO_VACIO.get()));

                    player.displayClientMessage(
                            Component.translatable("message.argentum.termo_empty"),
                            true
                    );

                } else if (waterStack.is(ModItems.TERMO_ARGENTO.get())) {

                    player.setItemInHand(otherHand,
                            new ItemStack(ModItems.TERMO_ARGENTO_VACIO.get()));

                    player.displayClientMessage(
                            Component.translatable("message.argentum.termo_argento_empty"),
                            true
                    );
                }
            }
        }

        // Consumir una cebada del mate
        int mateDamage = stack.getDamageValue() + 1;

        if (mateDamage >= stack.getMaxDamage()) {

            stack = new ItemStack(ModItems.MATE_VACIO.get());

            player.displayClientMessage(
                    Component.translatable("message.argentum.mate_washed"),
                    true
            );

        } else {
            stack.setDamageValue(mateDamage);
        }

        world.playSound(
                null,
                entity.getX(),
                entity.getY(),
                entity.getZ(),
                SoundEvents.GENERIC_DRINK,
                SoundSource.PLAYERS,
                1.0F,
                1.0F
        );

        return stack;
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