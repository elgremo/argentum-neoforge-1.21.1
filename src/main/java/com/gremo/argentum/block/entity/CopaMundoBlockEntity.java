package com.gremo.argentum.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class CopaMundoBlockEntity extends BlockEntity {
    private int tickCounter = 0;

    public CopaMundoBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.COPA_MUNDO_BE.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, CopaMundoBlockEntity be) {
        if (level.isClientSide) return;

        be.tickCounter++;
        // Cada 20 ticks (1 segundo), aplicar el efecto a los jugadores cercanos
        if (be.tickCounter >= 20) {
            be.tickCounter = 0;

            // Buscar jugadores en un radio de 10 bloques
            AABB area = new AABB(pos).inflate(10);
            for (Player player : level.getEntitiesOfClass(Player.class, area)) {
                // Aplicar absorción si no la tiene o está por expirar
                if (!player.hasEffect(MobEffects.ABSORPTION)) {
                    player.addEffect(new MobEffectInstance(
                            MobEffects.ABSORPTION,
                            20 * 60 * 5, // 5 minutos
                            1, // Nivel 1 = 2 corazones extra
                            false, false, true
                    ));
                }
            }
        }
    }
}