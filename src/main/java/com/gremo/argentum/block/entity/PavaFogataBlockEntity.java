package com.gremo.argentum.block.entity;

import com.gremo.argentum.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;

public class PavaFogataBlockEntity extends BlockEntity {

    private int timer = 0;

    public PavaFogataBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PAVA_FOGATA.get(), pos, state);
    }

    public static void tick(Level level,
                            BlockPos pos,
                            BlockState state,
                            PavaFogataBlockEntity be) {

        if (level.isClientSide()) return;

        if (state.is(ModBlocks.PAVA_FOGATA_LLENA.get())) {

            be.timer++;

            if (be.timer >= 60) {
                level.setBlockAndUpdate(
                        pos,
                        ModBlocks.PAVA_FOGATA_CALENTANDO.get().defaultBlockState()
                );
            }

            return;
        }

        if (state.is(ModBlocks.PAVA_FOGATA_CALENTANDO.get())) {

            be.timer++;

            if (be.timer >= 180) {
                level.setBlockAndUpdate(
                        pos,
                        ModBlocks.PAVA_FOGATA_CALIENTE.get().defaultBlockState()
                );
            }
        }
    }

}