package com.gremo.argentum.block.custom;

import com.gremo.argentum.block.entity.OllaBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OllaBlock extends BaseEntityBlock {
    public static final BooleanProperty ON = BooleanProperty.create("on");
    // 7 píxeles de alto
    private static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D);

    // Añadimos el MapCodec y el codec() requerido por BaseEntityBlock
    public static final MapCodec<OllaBlock> CODEC = simpleCodec(OllaBlock::new);

    public OllaBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ON, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                              Player player, InteractionHand hand, BlockHitResult hitResult) {
        // 1) Si el jugador está agachado: toggle ON/OFF aquí mismo
        if (player.isCrouching()) {
            if (!level.isClientSide()) {
                boolean nowOn = !state.getValue(ON);
                level.setBlock(pos, state.setValue(ON, nowOn), 3);
                if (nowOn) {
                    level.playSound(null, pos, SoundEvents.BREWING_STAND_BREW, SoundSource.BLOCKS, 1.0F, 1.0F);
                } else {
                    level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.8F, 1.0F);
                }
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide());
        }

        // 2) Si no está agachado: manejar colocar / extraer ítems
        if (level.getBlockEntity(pos) instanceof com.gremo.argentum.block.entity.OllaBlockEntity ollaBlockEntity) {
            // Colocar item si la olla está vacía y el jugador tiene un item
            if (ollaBlockEntity.inventory.getStackInSlot(0).isEmpty() && !stack.isEmpty()) {
                if (!level.isClientSide()) {
                    ollaBlockEntity.inventory.insertItem(0, stack.copy(), false);
                    stack.shrink(1);
                    level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 1f);
                }
                return ItemInteractionResult.sidedSuccess(level.isClientSide());
            }

            // Extraer item si la olla tiene item y el jugador tiene la mano vacía
            if (!ollaBlockEntity.inventory.getStackInSlot(0).isEmpty() && stack.isEmpty()) {
                if (!level.isClientSide()) {
                    ItemStack stackOnOlla = ollaBlockEntity.inventory.extractItem(0, 1, false);
                    player.setItemInHand(hand, stackOnOlla);
                    ollaBlockEntity.clearContents();
                    level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 1f);
                }
                return ItemInteractionResult.sidedSuccess(level.isClientSide());
            }
        }

        // Si no pasó nada relevante, devolvemos SUCCESS para que compile en tus mappings.
        // (Si más adelante confirmás que `pass()` está disponible, podés cambiar esto a ItemInteractionResult.pass() para permitir otras rutas.)
        return ItemInteractionResult.SUCCESS;
    }

    // Forma (7 px)
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return SHAPE;
    }

    @Override
    protected boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    // Luz cuando está on
    @Override
    public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
        return state.getValue(ON) ? 10 : 0; // ajustá el valor (0..15) según prefieras
    }

    // Entidad de bloque
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new OllaBlockEntity(blockPos, blockState);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.getBlock() != newState.getBlock()) {
            if (level.getBlockEntity(pos) instanceof OllaBlockEntity ollaBlockEntity) {
                ollaBlockEntity.drops();
                level.updateNeighbourForOutputSignal(pos, this);
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ON);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tool.argentum.olla.tooltip"));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}