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

public class ParrillaBlock extends Block {
    public static final BooleanProperty ON = BooleanProperty.create("on");
    // 7 píxeles de alto = 7 / 16 bloques -> box con Y hasta 7
    private static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D);

    public ParrillaBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ON, false));
    }

    // Click derecho sin item (firma que tenés en tu entorno)
    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
                                               Player player, BlockHitResult hitResult) {
        if (player.isCrouching()) {
            if (!level.isClientSide()) {
                boolean nowOn = !state.getValue(ON);
                level.setBlock(pos, state.setValue(ON, nowOn), 3);

                // Sonido: mechero al prender, apagar al desactivar
                if (nowOn) {
                    level.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                } else {
                    level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.7F, 1.0F);
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide());
        }
        return InteractionResult.PASS;
    }

    // Forma del bloque (7 px)
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return SHAPE;
    }

    // Para que la oclusión de luz y la culling usen la forma (evita problemas de transparencia/caras)
    @Override
    protected boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    // Emisión de luz según el state (0..15)
    @Override
    public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
        return state.getValue(ON) ? 14 : 0; // 14 es un buen valor para parrilla encendida (ajustalo si querés)
    }

    // Si está ON, daña / prende fuego a entidades que estén sobre el bloque
    /*@Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (!level.isClientSide() && state.getValue(ON) && entity instanceof LivingEntity) {
            LivingEntity le = (LivingEntity) entity;

            if (!le.fireImmune()) {
                // daño inmediato genérico
                le.hurt(DamageSource.GENERIC, 1.0F);
                // prender fuego por 4 segundos (daño continuo)
                le.setSecondsOnFire(4);
            }
        }

        super.stepOn(level, pos, state, entity);
    }*/

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ON);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tool.argentum.parrilla.tooltip"));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}