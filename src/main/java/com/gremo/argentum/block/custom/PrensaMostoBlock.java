package com.gremo.argentum.block.custom;

import com.gremo.argentum.block.entity.ModBlockEntities;
import com.gremo.argentum.block.entity.PrensaMostoBlockEntity;
import com.gremo.argentum.item.ModItems;
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
import net.minecraft.world.item.Items;
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
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import java.util.List;

public class PrensaMostoBlock extends BaseEntityBlock {
    public static final BooleanProperty ON = BooleanProperty.create("on");
    public static final DirectionProperty FACING =
            HorizontalDirectionalBlock.FACING;
    private static final VoxelShape SHAPE = Block.box(
            2.0D, 0.0D, 2.0D,
            14.0D, 16.0D, 14.0D
    );


    public static final MapCodec<PrensaMostoBlock> CODEC = simpleCodec(PrensaMostoBlock::new);

    public PrensaMostoBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(ON, false)
                        .setValue(FACING, Direction.NORTH)
        );
    }


    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ON, FACING);
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
        return new PrensaMostoBlockEntity(blockPos, blockState);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.getBlock() != newState.getBlock()) {
            if (level.getBlockEntity(pos) instanceof PrensaMostoBlockEntity PrensaMostoBlockEntity) {
                PrensaMostoBlockEntity.drops();
                level.updateNeighbourForOutputSignal(pos, this);
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                              Player player, InteractionHand hand, BlockHitResult hitResult) {


        ItemStack handStack = player.getItemInHand(hand);
        if (handStack.isEmpty()) return ItemInteractionResult.SUCCESS;

        // Balde vacío
        if (handStack.is(Items.BUCKET)) {

            if (!(level.getBlockEntity(pos) instanceof PrensaMostoBlockEntity prensa)) {
                return ItemInteractionResult.SUCCESS;
            }

            if (!state.getValue(ON)) {
                return ItemInteractionResult.SUCCESS;
            }

            if (!level.isClientSide()) {

                // TODO: reemplazar por tu item real
                ItemStack mosto = new ItemStack(ModItems.BALDE_MOSTO.get());

                handStack.shrink(1);

                if (!player.getInventory().add(mosto)) {
                    player.drop(mosto, false);
                }

                prensa.clearContents();

                level.setBlock(pos, state.setValue(ON, false), 3);

                level.playSound(null, pos,
                        SoundEvents.BUCKET_FILL,
                        SoundSource.BLOCKS,
                        1F, 1F);
            }

            return ItemInteractionResult.sidedSuccess(level.isClientSide());
        }

        if (handStack.getItem() != ModItems.UVA.get()) {
            return ItemInteractionResult.SUCCESS;
        }

        if (!(level.getBlockEntity(pos) instanceof PrensaMostoBlockEntity prensa)) {
            return ItemInteractionResult.SUCCESS;
        }

        if (!level.isClientSide()) {
            boolean inserted = prensa.tryInsertOne(handStack);
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
        return createTickerHelper(type, ModBlockEntities.MOSTO_BE.get(),
                (lvl, pos, st, be) -> PrensaMostoBlockEntity.tick(lvl, pos, st, (PrensaMostoBlockEntity) be));
    }

    @Override
    public void appendHoverText(ItemStack stack,
                                Item.TooltipContext context,
                                List<Component> tooltip,
                                TooltipFlag flag) {

        tooltip.add(Component.translatable("tool.argentum.prensa_mosto.tooltip"));
        super.appendHoverText(stack, context, tooltip, flag);
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