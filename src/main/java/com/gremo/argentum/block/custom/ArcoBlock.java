package com.gremo.argentum.block.custom;

import com.gremo.argentum.block.ModBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ArcoBlock extends HorizontalDirectionalBlock {

    public static final MapCodec<ArcoBlock> CODEC = simpleCodec(ArcoBlock::new);

    @Override
    public MapCodec<ArcoBlock> codec() {
        return CODEC;
    }

    public ArcoBlock(Properties properties) {
        super(properties);

        registerDefaultState(
                stateDefinition.any()
                        .setValue(FACING, Direction.NORTH)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public void setPlacedBy(Level level,
                            BlockPos pos,
                            BlockState state,
                            LivingEntity placer,
                            ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);

        if (level.isClientSide) {
            return;
        }

        Direction facing = state.getValue(FACING);

        if (hasEnoughSpace(level, pos, facing)) {
            placeGoal(level, pos, facing);
        } else {
            popResource(level, pos, new ItemStack(ModBlocks.ARCO_FULBO.get()));
            level.destroyBlock(pos, false);
        }
    }

    private boolean hasEnoughSpace(Level level, BlockPos pos, Direction facing) {
        BlockPos r = right(facing);

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 5; column++) {
                BlockPos target = pos.above(2 - row).offset(r.multiply(column - 2));

                // El bloque central (ARCO_FULBO) ya está colocado cuando se ejecuta setPlacedBy.
                if (target.equals(pos)) {
                    continue;
                }

                if (!level.getBlockState(target).canBeReplaced()) {
                    return false;
                }
            }
        }

        return true;
    }

    private BlockPos right(Direction facing) {
        return switch (facing) {
            case NORTH -> new BlockPos(-1, 0, 0);
            case SOUTH -> new BlockPos(1, 0, 0);
            case EAST  -> new BlockPos(0, 0, -1);
            case WEST  -> new BlockPos(0, 0, 1);
            default -> BlockPos.ZERO;
        };
    }

    private void placeGoal(Level level, BlockPos pos, Direction facing) {
        BlockPos r = right(facing);

        BlockState[] pieces = {
                ModBlocks.ARCO_UNO.get().defaultBlockState().setValue(FACING, facing),
                ModBlocks.ARCO_DOS.get().defaultBlockState().setValue(FACING, facing),
                ModBlocks.ARCO_TRES.get().defaultBlockState().setValue(FACING, facing),
                ModBlocks.ARCO_CUATRO.get().defaultBlockState().setValue(FACING, facing),
                ModBlocks.ARCO_CINCO.get().defaultBlockState().setValue(FACING, facing),

                ModBlocks.ARCO_SEIS.get().defaultBlockState().setValue(FACING, facing),
                ModBlocks.ARCO_SIETE.get().defaultBlockState().setValue(FACING, facing),
                ModBlocks.ARCO_OCHO.get().defaultBlockState().setValue(FACING, facing),
                ModBlocks.ARCO_NUEVE.get().defaultBlockState().setValue(FACING, facing),
                ModBlocks.ARCO_DIEZ.get().defaultBlockState().setValue(FACING, facing),

                ModBlocks.ARCO_ONCE.get().defaultBlockState().setValue(FACING, facing),
                ModBlocks.ARCO_DOCE.get().defaultBlockState().setValue(FACING, facing),
                ModBlocks.ARCO_FULBO.get().defaultBlockState().setValue(FACING, facing),
                ModBlocks.ARCO_CATORCE.get().defaultBlockState().setValue(FACING, facing),
                ModBlocks.ARCO_QUINCE.get().defaultBlockState().setValue(FACING, facing)
        };

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 5; column++) {
                BlockPos target = pos.above(2 - row).offset(r.multiply(column - 2));
                level.setBlockAndUpdate(target, pieces[row * 5 + column]);
            }
        }
    }

    private int getPieceIndex(Block block) {
        if (block == ModBlocks.ARCO_UNO.get()) return 0;
        if (block == ModBlocks.ARCO_DOS.get()) return 1;
        if (block == ModBlocks.ARCO_TRES.get()) return 2;
        if (block == ModBlocks.ARCO_CUATRO.get()) return 3;
        if (block == ModBlocks.ARCO_CINCO.get()) return 4;
        if (block == ModBlocks.ARCO_SEIS.get()) return 5;
        if (block == ModBlocks.ARCO_SIETE.get()) return 6;
        if (block == ModBlocks.ARCO_OCHO.get()) return 7;
        if (block == ModBlocks.ARCO_NUEVE.get()) return 8;
        if (block == ModBlocks.ARCO_DIEZ.get()) return 9;
        if (block == ModBlocks.ARCO_ONCE.get()) return 10;
        if (block == ModBlocks.ARCO_DOCE.get()) return 11;
        if (block == ModBlocks.ARCO_FULBO.get()) return 12;
        if (block == ModBlocks.ARCO_CATORCE.get()) return 13;
        if (block == ModBlocks.ARCO_QUINCE.get()) return 14;

        return -1;
    }

    @Override
    public void playerDestroy(Level level,
                              Player player,
                              BlockPos pos,
                              BlockState state,
                              @Nullable BlockEntity blockEntity,
                              ItemStack tool) {

        if (!level.isClientSide) {

            BlockPos master = getMasterPos(pos, state);
            Direction facing = state.getValue(FACING);
            BlockPos r = right(facing);

            // Eliminamos las otras 14 piezas
            for (int row = 0; row < 3; row++) {
                for (int column = 0; column < 5; column++) {

                    BlockPos piece = master.above(2 - row).offset(r.multiply(column - 2));

                    if (!piece.equals(pos)) {
                        level.destroyBlock(piece, false);
                    }
                }
            }

            // Si rompieron una pieza secundaria, soltamos el único Arco de Fulbo
            if (!pos.equals(master)) {
                popResource(level, master, new ItemStack(ModBlocks.ARCO_FULBO.get()));
            }
        }

        super.playerDestroy(level, player, pos, state, blockEntity, tool);
    }

    private BlockPos getMasterPos(BlockPos pos, BlockState state) {
        int index = getPieceIndex(state.getBlock());

        if (index == -1) {
            return pos;
        }

        Direction facing = state.getValue(FACING);
        BlockPos r = right(facing);

        int row = index / 5;
        int column = index % 5;

        return pos.below(2 - row).offset(r.multiply(2 - column));
    }

    /*@Override
    public BlockState playerWillDestroy(Level level,
                                        BlockPos pos,
                                        BlockState state,
                                        Player player) {
        if (!level.isClientSide) {
            BlockPos master = getMasterPos(pos, state);
            Direction facing = state.getValue(FACING);
            BlockPos r = right(facing);

            for (int row = 0; row < 3; row++) {
                for (int column = 0; column < 5; column++) {
                    BlockPos piece = master.above(2 - row).offset(r.multiply(column - 2));

                    if (!piece.equals(pos) && !piece.equals(master)) {
                        level.destroyBlock(piece, false);
                    }
                }
            }

            // Si rompiste una pieza secundaria, el master da el único drop.
            if (!pos.equals(master)) {
                level.destroyBlock(master, true);
            }
        }

        return super.playerWillDestroy(level, pos, state, player);
    }*/

    @Override
    public VoxelShape getCollisionShape(BlockState state,
                                        BlockGetter level,
                                        BlockPos pos,
                                        CollisionContext context) {
        int index = getPieceIndex(state.getBlock());

        if (index == -1) {
            return Shapes.block();
        }

        Direction facing = state.getValue(FACING);
        int row = index / 5;
        int column = index % 5;

        // Las 15 piezas tienen red atrás.
        VoxelShape shape = backShape(facing);

        // ARCO_UNO a ARCO_CINCO: travesaño superior.
        if (row == 0) {
            shape = Shapes.or(shape, box(0, 14, 0, 16, 16, 16));
        }

        // ARCO_UNO, ARCO_SEIS y ARCO_ONCE: poste izquierdo.
        if (column == 0) {
            shape = Shapes.or(shape, firstColumnShape(facing));
        }

        // ARCO_CINCO, ARCO_DIEZ y ARCO_QUINCE: poste derecho.
        if (column == 4) {
            shape = Shapes.or(shape, lastColumnShape(facing));
        }

        return shape;
    }

    @Override
    public VoxelShape getShape(BlockState state,
                               BlockGetter level,
                               BlockPos pos,
                               CollisionContext context) {
        return getCollisionShape(state, level, pos, context);
    }

    private VoxelShape backShape(Direction facing) {
        return switch (facing) {
            case NORTH -> box(0, 0, 14, 16, 16, 16);
            case SOUTH -> box(0, 0, 0, 16, 16, 2);
            case EAST  -> box(0, 0, 0, 2, 16, 16);
            case WEST  -> box(14, 0, 0, 16, 16, 16);
            default -> Shapes.block();
        };
    }

    private VoxelShape firstColumnShape(Direction facing) {
        return switch (facing) {
            case NORTH -> box(14, 0, 0, 16, 16, 16);
            case SOUTH -> box(0, 0, 0, 2, 16, 16);
            case EAST  -> box(0, 0, 14, 16, 16, 16);
            case WEST  -> box(0, 0, 0, 16, 16, 2);
            default -> Shapes.empty();
        };
    }

    private VoxelShape lastColumnShape(Direction facing) {
        return switch (facing) {
            case NORTH -> box(0, 0, 0, 2, 16, 16);
            case SOUTH -> box(14, 0, 0, 16, 16, 16);
            case EAST  -> box(0, 0, 0, 16, 16, 2);
            case WEST  -> box(0, 0, 14, 16, 16, 16);
            default -> Shapes.empty();
        };
    }
}