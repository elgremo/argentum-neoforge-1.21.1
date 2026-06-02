package com.gremo.argentum.item.custom;

import com.gremo.argentum.item.ModItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class InfusionItem extends Item {
    private final SoundEvent customDrinkSound;

    public InfusionItem(Properties properties) {
        this(properties, null);
    }

    public InfusionItem(Properties properties, SoundEvent customDrinkSound) {
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
        ItemStack stack = player.getItemInHand(hand);
        player.startUsingItem(hand); // fuerza la animación de beber
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        ItemStack result = stack;

        if (!world.isClientSide && entity instanceof Player player) {
            // Aplica los mismos stats que CocidoItem
            player.getFoodData().eat(8, 0.8F);

            // Reproducir sonido personalizado (si existe). Si es null, no hace sonido.
            if (customDrinkSound != null) {
                world.playSound(null, player.blockPosition(), customDrinkSound, SoundSource.PLAYERS, 1.0F, 1.0F);
            }

            if (!player.getAbilities().instabuild) {
                // Consumir 1 unidad
                stack.shrink(1);

                // Crear el ItemStack de la taza (reemplazá si tu ModItems tiene otro nombre)
                ItemStack taza = new ItemStack(ModItems.TAZA.get());

                // Si la pila quedó vacía, devolvemos la taza directamente
                if (stack.isEmpty()) {
                    return taza;
                } else {
                    // Intentamos agregar al inventario, si no cabe lo dropeamos
                    if (!player.getInventory().add(taza)) {
                        player.drop(taza, false);
                    }
                }
            }
        }

        return result;
    }
}