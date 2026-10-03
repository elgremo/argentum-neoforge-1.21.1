package com.gremo.argentum.block.entity;

import com.gremo.argentum.block.custom.BarrilFermentoTintoBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BarrilFermentoBlockEntity extends BlockEntity {

    private int progreso = 0;

    public BarrilFermentoBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BARRIL_FERMENTO_BE.get(), pos, state);
    }

    public int getProgreso() {
        return progreso;
    }

    public void setProgreso(int progreso) {
        this.progreso = progreso;
        setChanged();
    }

    public static void tick(
            Level level,
            BlockPos pos,
            BlockState state,
            BarrilFermentoBlockEntity be) {

        if (level.isClientSide) {
            return;
        }

        int etapa = state.getValue(BarrilFermentoTintoBlock.ETAPA);

        // ---------------- ETAPA 7 ----------------
        if (etapa == 7) {

            be.setProgreso(be.getProgreso() + 1);

            if (be.getProgreso() >= 60) {

                level.setBlock(
                        pos,
                        state.setValue(BarrilFermentoTintoBlock.ETAPA, 8),
                        Block.UPDATE_ALL
                );

                be.setProgreso(0);
            }

            return;
        }

        // ---------------- ETAPA 8 ----------------
        if (etapa == 8) {

            be.setProgreso(be.getProgreso() + 1);
            // Burbujas cada segundo

            if (level.getGameTime() % 20 == 0) {

                ((ServerLevel) level).sendParticles(
                        ParticleTypes.BUBBLE_POP,
                        pos.getX() + 0.5,
                        pos.getY() + 1.02,
                        pos.getZ() + 0.5,
                        3,
                        0.12,
                        0.02,
                        0.12,
                        0.0
                );

                // Cada 5 segundos aprox. hace "glup"
                if (level.getGameTime() % 100 == 0) {
                    level.playSound(
                            null,
                            pos,
                            SoundEvents.BUBBLE_COLUMN_BUBBLE_POP,
                            SoundSource.BLOCKS,
                            1.0F,
                            1.0F
                    );
                }
                if (level.getGameTime() % 100 == 0) {
                    level.playSound(
                            null,
                            pos,
                            SoundEvents.BUBBLE_COLUMN_UPWARDS_AMBIENT,
                            SoundSource.BLOCKS,
                            1.0F,
                            1.0F
                    );
                }
            }

            // Burbujas cada segundo
            if (level.getGameTime() % 20 == 0) {

                ((ServerLevel) level).sendParticles(
                        ParticleTypes.BUBBLE,
                        pos.getX() + 0.5,
                        pos.getY() + 1.02,
                        pos.getZ() + 0.5,
                        3,
                        0.12,
                        0.02,
                        0.12,
                        0.0
                );
            }

            // "Glup..." cada tanto
            if (level.random.nextInt(120) == 0) {

                level.playSound(
                        null,
                        pos,
                        SoundEvents.BUBBLE_COLUMN_BUBBLE_POP,
                        SoundSource.BLOCKS,
                        0.20F,
                        1.0F
                );
            }

            // Para probar: 200 ticks 24000 son 3 dias
            if (be.getProgreso() >= 72000) {

                level.setBlock(
                        pos,
                        state.setValue(BarrilFermentoTintoBlock.ETAPA, 9),
                        Block.UPDATE_ALL
                );

                // Clinck de vino terminado
                level.setBlock(
                        pos,
                        state.setValue(BarrilFermentoTintoBlock.ETAPA, 9),
                        Block.UPDATE_ALL
                );

                level.playSound(
                        null,
                        pos,
                        SoundEvents.BREWING_STAND_BREW,
                        SoundSource.BLOCKS,
                        0.7F,
                        0.8F
                );

                level.playSound(
                        null,
                        pos,
                        SoundEvents.AMETHYST_BLOCK_RESONATE,
                        SoundSource.BLOCKS,
                        1.0F,
                        1.1F
                );

                // Partículas de "¡listo!"
                ((ServerLevel) level).sendParticles(
                        ParticleTypes.HAPPY_VILLAGER,
                        pos.getX() + 0.5,
                        pos.getY() + 1.1,
                        pos.getZ() + 0.5,
                        8,
                        0.25,
                        0.15,
                        0.25,
                        0.02
                );

                be.setProgreso(0);
            }
            be.setRestante(6);
        }
    }
    private int restante = 6;
    public int getRestante() {
        return restante;
    }

    public void setRestante(int restante) {
        this.restante = restante;
        setChanged();
    }

    public void consumirBotella() {
        if (restante > 0) {
            restante--;
            setChanged();
        }
    }
    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        tag.putInt("Progreso", progreso);
        tag.putInt("Restante", restante);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        progreso = tag.getInt("Progreso");
        restante = tag.getInt("Restante");
    }
}
