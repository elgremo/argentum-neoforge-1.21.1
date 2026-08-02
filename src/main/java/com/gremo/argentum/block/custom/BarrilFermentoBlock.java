package com.gremo.argentum.block.custom;

import com.gremo.argentum.block.entity.BarrilFermentoBlockEntity;
import com.gremo.argentum.block.entity.ModBlockEntities;
import com.gremo.argentum.item.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BarrilFermentoBlock extends BaseEntityBlock {

    public static final MapCodec<BarrilFermentoBlock> CODEC =
            simpleCodec(BarrilFermentoBlock::new);

    // 0-7 = llenándose
    // 8 = fermentando
    // 9 = listo
    public static final IntegerProperty ETAPA =
            IntegerProperty.create("etapa", 0, 9);

    public static final DirectionProperty FACING =
            HorizontalDirectionalBlock.FACING;

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            Level level,
            BlockState state,
            BlockEntityType<T> type) {

        return createTickerHelper(
                type,
                ModBlockEntities.BARRIL_FERMENTO_BE.get(),
                BarrilFermentoBlockEntity::tick
        );
    }

    public BarrilFermentoBlock(BlockBehaviour.Properties properties) {
        super(properties);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(ETAPA, 0)
                .setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ETAPA, FACING);
    }

    @Override
    protected ItemInteractionResult useItemOn(
            ItemStack stack,
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            BlockHitResult hit) {

        // ===========================
        // LLENAR EL BARRIL CON MOSTO
        // ===========================
        if (stack.is(ModItems.BALDE_MOSTO.get())) {

            int etapa = state.getValue(ETAPA);

            if (etapa < 7) {

                if (!level.isClientSide) {

                    level.setBlock(
                            pos,
                            state.setValue(ETAPA, etapa + 1),
                            Block.UPDATE_ALL
                    );

                    // Sonido
                    level.playSound(
                            null,
                            pos,
                            SoundEvents.BUCKET_EMPTY,
                            SoundSource.BLOCKS,
                            1.0F,
                            0.95F
                    );

                    // Partículas
                    ((ServerLevel) level).sendParticles(
                            ParticleTypes.SPLASH,
                            pos.getX() + 0.5,
                            pos.getY() + 1.02,
                            pos.getZ() + 0.5,
                            4,
                            0.15,
                            0.02,
                            0.15,
                            0.0
                    );

                    if (!player.getAbilities().instabuild) {
                        stack.shrink(1);

                        player.getInventory().add(
                                new ItemStack(Items.BUCKET)
                        );
                    }
                }

                return ItemInteractionResult.SUCCESS;
            }
        }

        // ===========================
// LLENAR BOTELLA DE VINO
// ===========================
        if (stack.is(ModItems.BOTELLA_VINO_VACIA.get())
                && state.getValue(ETAPA) == 9) {

            if (!level.isClientSide) {

                BarrilFermentoBlockEntity blockEntity =
                        (BarrilFermentoBlockEntity) level.getBlockEntity(pos);

                if (blockEntity != null && blockEntity.getRestante() > 0) {

                    level.playSound(
                            null,
                            pos,
                            SoundEvents.BOTTLE_FILL,
                            SoundSource.BLOCKS,
                            1.0F,
                            1.0F
                    );

                    ((ServerLevel) level).sendParticles(
                            ParticleTypes.SPLASH,
                            pos.getX() + 0.5,
                            pos.getY() + 1.02,
                            pos.getZ() + 0.5,
                            4,
                            0.15,
                            0.02,
                            0.15,
                            0.0
                    );

                    if (!player.getAbilities().instabuild) {
                        stack.shrink(1);

                        ItemStack botella = new ItemStack(ModItems.BOTELLA_VINO_LLENA.get());

                        if (!player.getInventory().add(botella)) {
                            player.drop(botella, false);
                        }
                    }

                    blockEntity.consumirBotella();

                    if (blockEntity.getRestante() == 0) {
                        level.setBlock(
                                pos,
                                state.setValue(ETAPA, 0),
                                Block.UPDATE_ALL
                        );
                    }
                }
            }

            return ItemInteractionResult.SUCCESS;
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hit);
    }

    private static final VoxelShape SHAPE =
            Block.box(
                    1.0D,
                    0.0D,
                    1.0D,
                    15.0D,
                    16.0D,
                    15.0D
            );
    @Override
    protected VoxelShape getShape(
            BlockState state,
            BlockGetter level,
            BlockPos pos,
            CollisionContext context) {

        return SHAPE;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BarrilFermentoBlockEntity(pos, state);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {

        return defaultBlockState()
                .setValue(
                        FACING,
                        context.getHorizontalDirection().getOpposite()
                );
    }
}