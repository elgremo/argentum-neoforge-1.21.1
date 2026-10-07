package com.gremo.argentum.block.custom;

import com.gremo.argentum.Argentum;
import com.gremo.argentum.block.entity.NidoBlockEntity;
import com.gremo.argentum.block.entity.ModBlockEntities;
import com.gremo.argentum.util.ModTags;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NidoBlock extends BaseEntityBlock {


    public static final IntegerProperty HUEVOS = IntegerProperty.create("huevos", 0, 4);


    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        // Sin colisión (se puede atravesar completamente)
        return Shapes.empty();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        // Forma visual pequeña (como la caña de azúcar)
        return Block.box(3.0D, 0.0D, 3.0D, 13.0D, 6.0D, 13.0D);
    }

    public static final MapCodec<NidoBlock> CODEC = simpleCodec(NidoBlock::new);

    public NidoBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HUEVOS, 0));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HUEVOS);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new NidoBlockEntity(pos, state);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.getBlock() != newState.getBlock()) {
            if (level.getBlockEntity(pos) instanceof NidoBlockEntity nido) {
                nido.drops();
            }
            super.onRemove(state, level, pos, newState, movedByPiston);
        }
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                              Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide) return ItemInteractionResult.SUCCESS;

        if (!(level.getBlockEntity(pos) instanceof NidoBlockEntity nido)) {
            return ItemInteractionResult.SUCCESS;
        }

        // ✅ Intentar insertar cualquier item (el método interno decidirá si es válido)
        if (nido.tryInsertHuevo(stack)) {
            int count = nido.getHuevoCount();
            level.setBlock(pos, state.setValue(HUEVOS, count), 3);
            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.SUCCESS;
    }
    // NidoBlock.java
    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.argentum.nido")); // ✅ Usamos una clave de traducción
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }


    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null :
                createTickerHelper(type, ModBlockEntities.NIDO_BE.get(), NidoBlockEntity::tick);
    }
}