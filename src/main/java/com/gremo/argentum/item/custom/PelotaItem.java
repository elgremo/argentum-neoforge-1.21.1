package com.gremo.argentum.item.custom;

import com.gremo.argentum.entity.ModEntities;
import com.gremo.argentum.entity.custom.PelotaEntity;
import com.gremo.argentum.item.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PelotaItem extends Item {
    public PelotaItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            // Posición delante de la cámara / jugador
            double px = player.getX() + player.getLookAngle().x * 1.0;
            double py = player.getY() + player.getEyeHeight() - 0.2;
            double pz = player.getZ() + player.getLookAngle().z * 1.0;

            PelotaEntity pelota = new PelotaEntity(ModEntities.PELOTA.get(), level);
            pelota.setPos(px, py, pz);
            level.addFreshEntity(pelota);

            // Guardamos una copia del ItemStack para que la entidad lo muestre (opcional)
            pelota.setDisplay(new ItemStack(ModItems.PELOTA.get()));

            // velocidad inicial hacia donde mira el jugador (pequeño impulso)
            pelota.setDeltaMovement(player.getLookAngle().scale(0.6).add(0, 0.2, 0));

            level.addFreshEntity(pelota);

            // Consumir el item en modo supervivencia (no en creativo)
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }
}