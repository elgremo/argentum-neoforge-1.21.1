package com.gremo.argentum.item.custom;

import com.gremo.argentum.entity.ModEntities;
import com.gremo.argentum.entity.custom.PelotaBasquetEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PelotaBasquetItem extends Item {

    public PelotaBasquetItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            if (player.isCrouching()) {
                // Agachado + click derecho → DEJAR la pelota en el suelo (sin impulso)
                double px = player.getX();
                double py = player.getY() + player.getEyeHeight() - 0.2;
                double pz = player.getZ();

                PelotaBasquetEntity pelota = new PelotaBasquetEntity(ModEntities.PELOTA_BASQUET.get(), level);
                pelota.setPos(px, py, pz);
                pelota.setDisplay(stack.copy());
                pelota.setDeltaMovement(0, 0, 0); // sin movimiento

                level.addFreshEntity(pelota);

                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
                return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
            } else {
                // Sin agachar → LANZAR la pelota (tiro largo)
                double px = player.getX() + player.getLookAngle().x * 1.0;
                double py = player.getY() + player.getEyeHeight() - 0.2;
                double pz = player.getZ() + player.getLookAngle().z * 1.0;

                PelotaBasquetEntity pelota = new PelotaBasquetEntity(ModEntities.PELOTA_BASQUET.get(), level);
                pelota.setPos(px, py, pz);
                pelota.setDisplay(stack.copy());
                pelota.setDeltaMovement(player.getLookAngle().scale(1.8).add(0, 0.4, 0)); // impulso fuerte

                level.addFreshEntity(pelota);

                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
                return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
            }
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }
}