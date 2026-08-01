package com.gremo.argentum.block.custom;

import com.gremo.argentum.block.entity.BotelleroBlockEntity;
import com.gremo.argentum.item.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.Containers;

public class BotelleroBlock extends BaseEntityBlock {

    public static final MapCodec<BotelleroBlock> CODEC =
            simpleCodec(BotelleroBlock::new);

    public static final DirectionProperty FACING =
            HorizontalDirectionalBlock.FACING;

    public static final EnumProperty<DoubleBlockHalf> HALF =
            BlockStateProperties.DOUBLE_BLOCK_HALF;

    private static final VoxelShape SHAPE_SOUTH =
            Block.box(0, 0, 0, 16, 16, 10);

    private static final VoxelShape SHAPE_NORTH =
            Block.box(0, 0, 6, 16, 16, 16);

    private static final VoxelShape SHAPE_EAST =
            Block.box(0, 0, 0, 10, 16, 16);

    private static final VoxelShape SHAPE_WEST =
            Block.box(6, 0, 0, 16, 16, 16);
    @Override
    protected VoxelShape getShape(
            BlockState state,
            BlockGetter level,
            BlockPos pos,
            CollisionContext context) {

        return switch (state.getValue(FACING)) {
            case NORTH -> SHAPE_NORTH;
            case SOUTH -> SHAPE_SOUTH;
            case EAST -> SHAPE_EAST;
            case WEST -> SHAPE_WEST;
            default -> SHAPE_SOUTH;
        };
    }



    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BotelleroBlockEntity(pos, state);
    }
    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public BotelleroBlock(Properties properties) {
        super(properties);

        registerDefaultState(
                stateDefinition.any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(HALF, DoubleBlockHalf.LOWER)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, HALF);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {

        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();


        if (!level.getBlockState(pos.above()).canBeReplaced(context)) {
            return null;
        }


        return defaultBlockState()

                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(HALF, DoubleBlockHalf.LOWER);
    }

    @Override
    public void setPlacedBy(Level level,
                            BlockPos pos,
                            BlockState state,
                            LivingEntity placer,
                            ItemStack stack) {

        level.setBlock(
                pos.above(),
                state.setValue(HALF, DoubleBlockHalf.UPPER),
                Block.UPDATE_ALL
        );
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

        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            pos = pos.below();
            state = level.getBlockState(pos); // MUY IMPORTANTE
        }

        BlockEntity be = level.getBlockEntity(pos);

        if (!(be instanceof BotelleroBlockEntity botellero)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        System.out.println("FACING = " + state.getValue(FACING));

        int slot = getSlot(state, pos, hit);
        System.out.println("SLOT = " + slot);

        // ===== GUARDAR =====
        if (!stack.isEmpty() && stack.is(ModItems.BOTELLA_VINO_LLENA.get())) {

            if (botellero.getItem(slot).isEmpty()) {

                botellero.setItem(slot, stack.copyWithCount(1));

                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }

                return ItemInteractionResult.SUCCESS;
            }

            return ItemInteractionResult.SUCCESS;
        }

        // ===== SACAR =====
        ItemStack botella = botellero.getItem(slot);

        if (!botella.isEmpty()) {

            botellero.setItem(slot, ItemStack.EMPTY);

            if (!player.addItem(botella)) {
                player.drop(botella, false);
            }

            return ItemInteractionResult.SUCCESS;
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    private int getSlot(
            BlockState state,
            BlockPos pos,
            BlockHitResult hit) {

        // Altura del click
        double y = hit.getLocation().y - pos.getY();

        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            y += 1.0;
        }

        // Posición relativa del click
        Vec2 local = getRelativeHitPos(
                state,
                pos,
                hit
        );

        double localX = local.x;

        // -------------------------
        // COLUMNA
        // -------------------------

        int columna = (int)(localX * 3.0);

        columna = Math.max(0, Math.min(2, columna));

        // -------------------------
        // FILA
        // -------------------------

        int fila = (int)(y * 3.0);

        fila = Math.max(0, Math.min(5, fila));

        return fila * 3 + columna;
    }

    private Vec2 getRelativeHitPos(
            BlockState state,
            BlockPos pos,
            BlockHitResult hit) {

        double x = hit.getLocation().x - pos.getX();
        double z = hit.getLocation().z - pos.getZ();

        Direction facing = state.getValue(FACING);

        return switch (facing) {

            case SOUTH -> new Vec2(
                    (float)x,
                    0
            );

            case NORTH -> new Vec2(
                    (float)(1.0 - x),
                    0
            );

            case WEST -> new Vec2(
                    (float)z,
                    0
            );

            case EAST -> new Vec2(
                    (float)(1.0 - z),
                    0
            );

            default -> new Vec2((float)x, 0);
        };
    }

    @Override
    public BlockState playerWillDestroy(
            Level level,
            BlockPos pos,
            BlockState state,
            Player player) {

        // Siempre trabajamos con el bloque inferior
        BlockPos basePos = state.getValue(HALF) == DoubleBlockHalf.LOWER
                ? pos
                : pos.below();

        BlockEntity be = level.getBlockEntity(basePos);

        if (be instanceof BotelleroBlockEntity botellero) {

            for (int i = 0; i < 18; i++) {

                ItemStack stack = botellero.getItem(i);

                if (!stack.isEmpty()) {

                    Containers.dropItemStack(
                            level,
                            basePos.getX() + 0.5,
                            basePos.getY() + 0.5,
                            basePos.getZ() + 0.5,
                            stack
                    );
                }
            }
        }

        // Destruimos la otra mitad
        DoubleBlockHalf half = state.getValue(HALF);

        BlockPos otherPos = half == DoubleBlockHalf.LOWER
                ? pos.above()
                : pos.below();

        BlockState otherState = level.getBlockState(otherPos);

        if (otherState.is(this)) {
            level.destroyBlock(otherPos, false);
        }

        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {

            Containers.dropItemStack(
                    level,
                    basePos.getX() + 0.5,
                    basePos.getY() + 0.5,
                    basePos.getZ() + 0.5,
                    new ItemStack(this)
            );
        }

        return super.playerWillDestroy(level, pos, state, player);
    }
}
