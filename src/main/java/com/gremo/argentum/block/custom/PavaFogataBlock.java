package com.gremo.argentum.block.custom;

import com.gremo.argentum.block.ModBlocks;
import com.gremo.argentum.item.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.util.RandomSource;
import com.gremo.argentum.block.entity.PavaFogataBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;
import com.gremo.argentum.block.entity.ModBlockEntities;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class PavaFogataBlock extends BaseEntityBlock {

    public static final MapCodec<PavaFogataBlock> CODEC =
            simpleCodec(PavaFogataBlock::new);
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PavaFogataBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level,
                                                                  BlockState state,
                                                                  BlockEntityType<T> type) {

        return createTickerHelper(
                type,
                ModBlockEntities.PAVA_FOGATA.get(),
                PavaFogataBlockEntity::tick
        );
    }

    public PavaFogataBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack,
                                              BlockState state,
                                              Level level,
                                              BlockPos pos,
                                              Player player,
                                              InteractionHand hand,
                                              BlockHitResult hit) {

        // ==========================
        // SACAR LA PAVA CALIENTE
        // ==========================
        if (state.is(ModBlocks.PAVA_FOGATA_CALIENTE.get())) {

            if (!level.isClientSide()) {

                level.setBlock(
                        pos,
                        Blocks.CAMPFIRE.defaultBlockState()
                                .setValue(CampfireBlock.LIT, true),
                        3
                );

                ItemStack pava = new ItemStack(ModItems.PAVA_CALIENTE.get());

                if (!player.addItem(pava)) {
                    Block.popResource(level, pos, pava);
                }

                level.playSound(
                        null,
                        pos,
                        SoundEvents.ITEM_FRAME_REMOVE_ITEM,
                        SoundSource.BLOCKS,
                        1F,
                        1F
                );
            }

            return ItemInteractionResult.sidedSuccess(level.isClientSide());
        }

        // ==========================
        // LLENAR CON AGUA
        // ==========================
        if (state.is(ModBlocks.PAVA_FOGATA_VACIA.get()) &&
                stack.is(Items.WATER_BUCKET)) {

            if (!level.isClientSide()) {

                level.setBlock(
                        pos,
                        ModBlocks.PAVA_FOGATA_LLENA.get().defaultBlockState(),
                        3
                );


                if (!player.getAbilities().instabuild) {
                    player.setItemInHand(hand, new ItemStack(Items.BUCKET));
                }

                level.playSound(
                        null,
                        pos,
                        SoundEvents.BUCKET_EMPTY,
                        SoundSource.BLOCKS,
                        1F,
                        1F
                );
            }

            return ItemInteractionResult.sidedSuccess(level.isClientSide());
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }


    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {

        if (!state.is(ModBlocks.PAVA_FOGATA_CALIENTE.get())) {
            return;
        }

        double x = pos.getX() + 0.5;
        double y = pos.getY() + 13.0 / 16.0;
        double z = pos.getZ() + 2.5 / 16.0;

        if (random.nextInt(6) == 0) {
            level.addParticle(
                    ParticleTypes.SMOKE,
                    x, y, z,
                    0.0, 0.02, 0.0
            );
        }
        if (random.nextInt(2) == 0) {
            level.playLocalSound(
                    pos.getX() + 0.5,
                    pos.getY() + 0.5,
                    pos.getZ() + 0.5,
                    SoundEvents.FIRE_EXTINGUISH,
                    SoundSource.BLOCKS,
                    0.4F,
                    1.8F,
                    false
            );
        }
    }


    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}