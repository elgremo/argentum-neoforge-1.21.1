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
        return 5; // casi instantáneo
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        InteractionHand otherHand = hand == InteractionHand.MAIN_HAND
                ? InteractionHand.OFF_HAND
                : InteractionHand.MAIN_HAND;

        ItemStack bottleStack = player.getItemInHand(otherHand);

        // Verificar si la otra mano tiene una botella llena de algún tipo
        boolean hasBottle = !bottleStack.isEmpty() &&
                (bottleStack.is(ModItems.BOTELLA_VINO_TINTO_LLENA.get()) ||
                        bottleStack.is(ModItems.BOTELLA_VINO_BLANCO_LLENA.get()) ||
                        bottleStack.is(ModItems.BOTELLA_VINO_ROSADO_LLENA.get()));

        if (!hasBottle) {
            if (!level.isClientSide) {
                player.displayClientMessage(
                        Component.translatable("message.argentum.need_bottle")
                                .withStyle(ChatFormatting.RED),
                        true
                );
            }
            return InteractionResultHolder.fail(player.getItemInHand(hand));
        }

        // Iniciar el "uso" (llenado)
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!(entity instanceof Player player)) {
            return stack;
        }

        InteractionHand usedHand = player.getUsedItemHand();
        InteractionHand otherHand = usedHand == InteractionHand.MAIN_HAND
                ? InteractionHand.OFF_HAND
                : InteractionHand.MAIN_HAND;

        ItemStack bottleStack = player.getItemInHand(otherHand);

        if (!level.isClientSide) {
            // Determinar qué copa llena devolver según el tipo de botella
            ItemStack copaLlena = ItemStack.EMPTY;
            if (bottleStack.is(ModItems.BOTELLA_VINO_TINTO_LLENA.get())) {
                copaLlena = new ItemStack(ModItems.COPA_VINO_TINTO.get());
            } else if (bottleStack.is(ModItems.BOTELLA_VINO_BLANCO_LLENA.get())) {
                copaLlena = new ItemStack(ModItems.COPA_VINO_BLANCO.get());
            } else if (bottleStack.is(ModItems.BOTELLA_VINO_ROSADO_LLENA.get())) {
                copaLlena = new ItemStack(ModItems.COPA_VINO_ROSADO.get());
            }

            if (!copaLlena.isEmpty()) {
                // Reemplazar la copa vacía con la copa llena
                if (stack.getCount() == 1) {
                    player.setItemInHand(usedHand, copaLlena);
                } else {
                    stack.shrink(1);
                    if (!player.getInventory().add(copaLlena)) {
                        player.drop(copaLlena, false);
                    }
                }

                // Consumir un uso de la botella (durabilidad)
                int bottleDamage = bottleStack.getDamageValue() + 1;
                bottleStack.setDamageValue(bottleDamage);

                // Si la botella se gastó, devolver botella vacía
                if (bottleDamage >= bottleStack.getMaxDamage()) {
                    player.setItemInHand(
                            otherHand,
                            new ItemStack(ModItems.BOTELLA_VINO_VACIA.get())
                    );
                }
            }
        }

        // Sonido de llenado
        level.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS, 1.0F, 1.0F);

        return player.getItemInHand(usedHand);
    }
}