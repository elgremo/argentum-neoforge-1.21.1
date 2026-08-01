package com.gremo.argentum.block.custom;

import com.gremo.argentum.block.ModBlocks;
import com.gremo.argentum.item.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import java.util.List;

public class PrensaMostoListaBlock extends Block {

    public static final MapCodec<PrensaMostoListaBlock> CODEC =
            simpleCodec(PrensaMostoListaBlock::new);
    public static final DirectionProperty FACING =
            HorizontalDirectionalBlock.FACING;
    public PrensaMostoListaBlock(Properties properties) {
        super(properties);

        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(FACING, Direction.NORTH)
        );
    }

    @Override
    protected void createBlockStateDefinition(
            StateDefinition.Builder<Block, BlockState> builder) {

        builder.add(FACING);
    }
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {

        return defaultBlockState()
                .setValue(
                        FACING,
                        context.getHorizontalDirection().getOpposite()
                );
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack,
                                              BlockState state,
                                              Level level,
                                              BlockPos pos,
                                              Player player,
                                              InteractionHand hand,
                                              BlockHitResult hitResult) {

        ItemStack handStack = player.getItemInHand(hand);

        if (!handStack.is(Items.BUCKET)) {
            return ItemInteractionResult.SUCCESS;
        }

        if (!level.isClientSide()) {

            handStack.shrink(1);

            ItemStack mosto = new ItemStack(ModItems.BALDE_MOSTO.get());

            if (!player.getInventory().add(mosto)) {
                player.drop(mosto, false);
            }

            level.setBlock(
                    pos,
                    ModBlocks.PRENSA_MOSTO.get()
                            .defaultBlockState()
                            .setValue(
                                    PrensaMostoBlock.FACING,
                                    state.getValue(FACING)
                            ),
                    3
            );

            level.playSound(
                    null,
                    pos,
                    SoundEvents.BUCKET_FILL,
                    SoundSource.BLOCKS,
                    1F,
                    1F
            );
        }

        return ItemInteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack,
                                Item.TooltipContext context,
                                List<Component> tooltip,
                                TooltipFlag flag) {

        tooltip.add(Component.translatable("tool.argentum.prensa_mosto_lista.tooltip"));
        super.appendHoverText(stack, context, tooltip, flag);
    }

    private static final VoxelShape SHAPE = Block.box(
            2.0D, 0.0D, 2.0D,
            14.0D, 16.0D, 14.0D
    );

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}