package com.gremo.argentum.block.custom;

import com.gremo.argentum.block.entity.OllaBlockEntity;
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
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OllaFogataBlock extends BaseEntityBlock {

    private static final VoxelShape SHAPE = Shapes.or(
            Block.box(0, 0, 0, 16, 7, 16),     // fogata
            Block.box(1, 7, 1, 15, 14, 15)     // olla
    );

    public static final MapCodec<OllaFogataBlock> CODEC =
            simpleCodec(OllaFogataBlock::new);

    public OllaFogataBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }


    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return SHAPE;
    }

    @Override
    protected boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }



    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new OllaBlockEntity(blockPos, blockState);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    /**
     * Interacción:
     * - Shift + click (con cualquier mano) togglea ON/OFF
     * - Click con item fryable lo inserta (1 unidad)
     * - Click con mano vacía extrae el contenido si lo hay
     */
    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                              Player player, InteractionHand hand, BlockHitResult hitResult) {
        // SHIFT: toggle
        if (player.isCrouching()) {
            if (!level.isClientSide()) {
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide());
        }

        ItemStack handStack = player.getItemInHand(hand);

        BlockEntity beRaw = level.getBlockEntity(pos);
        if (!(beRaw instanceof OllaBlockEntity olla)) {
            return ItemInteractionResult.SUCCESS;
        }

        // Mano vacía: extraer
        if (handStack.isEmpty()) {
            if (!level.isClientSide()) {
                ItemStack extracted = olla.extractOne();
                if (!extracted.isEmpty()) {
                    if (!player.addItem(extracted)) {
                        // si no pudo añadirse, dropear en mundo
                        net.minecraft.world.level.block.Block.popResource(level, pos, extracted);
                    }
                    level.playSound(null, pos, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 0.8F, 1.0F);
                }
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide());
        }

        // Si no es fryable -> no hacemos nada
        if (!OllaBlockEntity.isFryable(handStack.getItem())) {
            return ItemInteractionResult.SUCCESS;
        }

        // intentar insertar 1 unidad
        if (!level.isClientSide()) {
            boolean inserted = olla.tryInsertOne(handStack);
            if (inserted) {
                player.setItemInHand(hand, handStack);
                level.playSound(null, pos, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 0.8F, 1.0F);
                level.sendBlockUpdated(pos, state, state, 3);
                return ItemInteractionResult.sidedSuccess(false);
            }
        }

        return ItemInteractionResult.sidedSuccess(level.isClientSide());
    }


    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);

        BlockPos below = pos.below();
        BlockState belowState = level.getBlockState(below);

        if (belowState.getBlock() instanceof CampfireBlock
                && belowState.hasProperty(CampfireBlock.LIT)
                && belowState.getValue(CampfireBlock.LIT)) {

            /*level.setBlock(
                    below,
                    belowState.setValue(CampfireBlock.LIT, false),
                    3
            );*/
        }
    }

    /*@Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {

        if (!state.is(newState.getBlock())) {

            level.setBlock(
                    pos,
                    Blocks.CAMPFIRE.defaultBlockState(),
                    3
            );
        }

        super.onRemove(state, level, pos, newState, movedByPiston);
    }*/


    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, com.gremo.argentum.block.entity.ModBlockEntities.OLLA_BE.get(),
                (lvl, pos, st, be) -> OllaBlockEntity.tick(lvl, pos, st, (OllaBlockEntity) be));
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tool.argentum.olla.tooltip"));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

}