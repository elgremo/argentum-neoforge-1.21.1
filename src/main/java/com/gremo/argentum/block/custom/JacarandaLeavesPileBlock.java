package com.gremo.argentum.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class JacarandaLeavesPileBlock extends BushBlock {
    public static final IntegerProperty AMOUNT = IntegerProperty.create("amount", 1, 4);
    public static final IntegerProperty ROTATION = IntegerProperty.create("rotation", 0, 3);
    private static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);

    public JacarandaLeavesPileBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(AMOUNT, 1)
                .setValue(ROTATION, 0));
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return simpleCodec(JacarandaLeavesPileBlock::new);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.isSolid();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AMOUNT, ROTATION);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        int rotation = context.getLevel().getRandom().nextInt(4);
        return this.defaultBlockState().setValue(ROTATION, rotation);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        return false;
    }

    // ===== INTERACCIÓN DEL JUGADOR (como pink_petals) =====

    @Override
    protected ItemInteractionResult useItemOn(
            ItemStack stack,
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            BlockHitResult hitResult
    ) {
        // Solo funciona si el jugador tiene el mismo bloque en la mano
        if (!stack.is(this.asItem())) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        int currentAmount = state.getValue(AMOUNT);

        // Si ya tiene 4 capas, no se puede aumentar
        if (currentAmount >= 4) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (!level.isClientSide) {
            // Aumentar la cantidad en 1
            int newAmount = currentAmount + 1;
            level.setBlock(pos, state.setValue(AMOUNT, newAmount), Block.UPDATE_ALL);

            // Consumir el ítem de la mano (si no es en modo creativo)
            if (!player.hasInfiniteMaterials()) {
                stack.shrink(1);
            }
        }

        // Retornar éxito para que no se active el uso por defecto (ej. colocar bloque)
        return ItemInteractionResult.SUCCESS;
    }
}