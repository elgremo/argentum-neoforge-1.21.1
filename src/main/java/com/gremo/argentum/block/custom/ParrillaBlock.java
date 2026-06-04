package com.gremo.argentum.block.custom;

import com.gremo.argentum.block.entity.ParrillaBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ParrillaBlock extends BaseEntityBlock {
    public static final BooleanProperty ON = BooleanProperty.create("on");
    private static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D);

    public static final MapCodec<ParrillaBlock> CODEC = simpleCodec(ParrillaBlock::new);

    public ParrillaBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ON, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ON);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return SHAPE;
    }

    @Override
    protected boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
        return state.getValue(ON) ? 14 : 0;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ParrillaBlockEntity(blockPos, blockState);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.getBlock() != newState.getBlock()) {
            if (level.getBlockEntity(pos) instanceof ParrillaBlockEntity parrillaBlockEntity) {
                parrillaBlockEntity.drops();
                level.updateNeighbourForOutputSignal(pos, this);
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                              Player player, InteractionHand hand, BlockHitResult hitResult) {

        // 1) Toggle ON/OFF con Shift + Click
        if (player.isCrouching()) {
            if (!level.isClientSide()) {
                boolean nowOn = !state.getValue(ON);
                level.setBlock(pos, state.setValue(ON, nowOn), 3);
                level.playSound(null, pos, nowOn ? SoundEvents.FLINTANDSTEEL_USE : SoundEvents.FIRE_EXTINGUISH,
                        SoundSource.BLOCKS, nowOn ? 1.0F : 0.7F, 1.0F);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide());
        }

        ItemStack handStack = player.getItemInHand(hand);
        if (handStack.isEmpty()) return ItemInteractionResult.SUCCESS;

        // 2) AQUI ESTÁ EL CAMBIO: Usamos el método isGrillable del Entity en vez del TAG
        if (!ParrillaBlockEntity.isGrillable(handStack.getItem())) {
            return ItemInteractionResult.SUCCESS;
        }

        if (!(level.getBlockEntity(pos) instanceof ParrillaBlockEntity parrilla)) {
            return ItemInteractionResult.SUCCESS;
        }

        if (!level.isClientSide()) {
            boolean inserted = parrilla.tryInsertOne(handStack);
            player.setItemInHand(hand, handStack);
            if (inserted) {
                level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 1f);
                level.sendBlockUpdated(pos, state, state, 3);
                return ItemInteractionResult.sidedSuccess(false);
            }
        }

        return ItemInteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, com.gremo.argentum.block.entity.ModBlockEntities.PARRILLA_BE.get(),
                (lvl, pos, st, be) -> com.gremo.argentum.block.entity.ParrillaBlockEntity.tick(lvl, pos, st, (ParrillaBlockEntity) be));
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tool.argentum.parrilla.tooltip"));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}