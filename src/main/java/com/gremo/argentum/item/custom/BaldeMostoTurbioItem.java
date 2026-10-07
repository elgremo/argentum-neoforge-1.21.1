package com.gremo.argentum.item.custom;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class BaldeMostoTurbioItem extends Item {
    public BaldeMostoTurbioItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        // Solo ejecutamos la lógica en el lado del servidor
        if (!level.isClientSide) {
            // 1. Reproducir sonido de cubo vaciándose
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS,
                    1.0F, 1.0F);

            // 2. Reemplazar el ítem por un cubo vacío
            //    (respetando el modo creativo)
            if (!player.hasInfiniteMaterials()) {
                player.setItemInHand(hand, new ItemStack(Items.BUCKET));
            }
        }

        // Devolvemos un resultado exitoso. El ItemStack que devolvemos aquí
        // no se usa para reemplazar el ítem (lo hicimos con setItemInHand),
        // pero sirve para que el juego sepa que la acción fue válida.
        return InteractionResultHolder.success(stack);
    }

    // Animación: usamos DRINK para que el brazo haga el gesto de verter/beber.
    // No hay un UseAnim específico para "vaciar cubo", así que DRINK es lo más cercano.
    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    // Duración de la animación: 32 ticks (1.6 s), igual que una poción.
    @Override
    public int getUseDuration(ItemStack stack, net.minecraft.world.entity.LivingEntity entity) {
        return 32;
    }
}