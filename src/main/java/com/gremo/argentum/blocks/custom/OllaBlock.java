package com.gremo.argentum.blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class OllaBlock extends Block {
    public static final BooleanProperty ON = BooleanProperty.create("on");
    // 7 píxeles de alto
    private static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D);

    public OllaBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ON, false));
    }

    // Click derecho sin item (toggle si estás agachado)
    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
                                               Player player, BlockHitResult hitResult) {
        if (player.isCrouching()) {
            if (!level.isClientSide()) {
                boolean nowOn = !state.getValue(ON);
                level.setBlock(pos, state.setValue(ON, nowOn), 3);

                // Sonido: uso de olla (brewing) al prender, apagar fuego al apagar
                if (nowOn) {
                    level.playSound(null, pos, SoundEvents.BREWING_STAND_BREW, SoundSource.BLOCKS, 1.0F, 1.0F);
                } else {
                    level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.8F, 1.0F);
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide());
        }
        return InteractionResult.PASS;
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

    // Daña / prende fuego a entidades que pasen por encima si está on


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