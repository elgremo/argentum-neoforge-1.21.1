package com.gremo.argentum.block.renderer;

import com.gremo.argentum.block.custom.MesaTrucoCompaBlock;
import com.gremo.argentum.block.entity.MesaTrucoCompaBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class MesaTrucoCompaBlockEntityRenderer implements BlockEntityRenderer<MesaTrucoCompaBlockEntity> {

    public MesaTrucoCompaBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(MesaTrucoCompaBlockEntity be, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight, int packedOverlay) {

        // Solo renderizar si el jugador está a menos de 7 bloques
        var player = Minecraft.getInstance().player;
        if (player == null) return;

        double dist = player.distanceToSqr(
                be.getBlockPos().getX() + 0.5,
                be.getBlockPos().getY() + 0.5,
                be.getBlockPos().getZ() + 0.5
        );
        if (dist > 7 * 7) return;

        poseStack.pushPose();

        // Rotación según FACING (SOUTH = 0°, caso base)
        Direction facing = be.getBlockState().getValue(MesaTrucoCompaBlock.FACING);
        poseStack.translate(0.5, 0, 0.5);
        switch (facing) {
            case SOUTH -> {}
            case NORTH -> poseStack.mulPose(Axis.YP.rotationDegrees(180));
            case EAST  -> poseStack.mulPose(Axis.YP.rotationDegrees(90));
            case WEST  -> poseStack.mulPose(Axis.YP.rotationDegrees(-90));
        }
        poseStack.translate(-0.5, 0, -0.5);

        // Dibujar los 12 slots visibles
        for (int i = 0; i < MesaTrucoCompaBlockEntity.TOTAL_SLOTS; i++) {
            ItemStack stack = be.getItem(i);
            if (stack.isEmpty()) continue;

            float[] pos = getSlotPosition(i);

            poseStack.pushPose();
            poseStack.translate(pos[0], pos[1], pos[2]);

            // Acostado
            poseStack.mulPose(Axis.XP.rotationDegrees(180));

            // Rotar según el lado (para que cada jugador vea sus cartas de frente)
            float yRot = getSideRotation(i);
            if (yRot != 0f) {
                poseStack.mulPose(Axis.YP.rotationDegrees(yRot));
            }

            poseStack.scale(0.8f, 0.8f, 0.8f);

            Minecraft.getInstance().getItemRenderer().renderStatic(
                    stack,
                    ItemDisplayContext.GROUND,
                    packedLight,
                    packedOverlay,
                    poseStack,
                    buffer,
                    be.getLevel(),
                    0
            );

            poseStack.popPose();
        }

        poseStack.popPose();
    }

    /**
     * Rotación en Y según el lado:
     *   NORTE → 180° (mira al sur, hacia el centro/jugador del sur)
     *   SUR   → 0°   (mira al norte, hacia el centro)
     *   OESTE → 90°  (mira al este)
     *   ESTE  → -90° (mira al oeste)
     */
    private float getSideRotation(int slot) {
        if (slot >= MesaTrucoCompaBlockEntity.NORTE_1
                && slot <= MesaTrucoCompaBlockEntity.NORTE_3) {
            return 180f;
        }
        if (slot >= MesaTrucoCompaBlockEntity.SUR_1
                && slot <= MesaTrucoCompaBlockEntity.SUR_3) {
            return 0f;
        }
        if (slot >= MesaTrucoCompaBlockEntity.OESTE_1
                && slot <= MesaTrucoCompaBlockEntity.OESTE_3) {
            return 90f;
        }
        if (slot >= MesaTrucoCompaBlockEntity.ESTE_1
                && slot <= MesaTrucoCompaBlockEntity.ESTE_3) {
            return -90f;
        }
        return 0f;
    }

    /**
     * Posiciones en coords locales del master.
     * El master está en (0,0,0) y la mesa 2x2 se extiende en un radio de 2 bloques.
     *
     * Layout:
     *        N1 N2 N3
     *   O1              E1
     *   O2     MAZO     E2
     *   O3              E3
     *        S1 S2 S3
     */
    private float[] getSlotPosition(int slot) {
        return switch (slot) {
            // NORTE (arriba) - menos separadas + 0.9px al sur
            case MesaTrucoCompaBlockEntity.NORTE_1 -> new float[]{0.652f, 1.0f, -0.68f};
            case MesaTrucoCompaBlockEntity.NORTE_2 -> new float[]{0.964f, 1.0f, -0.68f};
            case MesaTrucoCompaBlockEntity.NORTE_3 -> new float[]{1.275f, 1.0f, -0.68f};

            // OESTE (izquierda) - 0.1px a la derecha + 0.5px al sur
            case MesaTrucoCompaBlockEntity.OESTE_1 -> new float[]{0.318f, 1.0f, -0.275f};
            case MesaTrucoCompaBlockEntity.OESTE_2 -> new float[]{0.318f, 1.0f,  0.035f};
            case MesaTrucoCompaBlockEntity.OESTE_3 -> new float[]{0.318f, 1.0f,  0.35f};

            // SUR (abajo) - menos separadas + 0.9px al norte
            case MesaTrucoCompaBlockEntity.SUR_1 -> new float[]{0.727f, 1.0f, 0.68f};
            case MesaTrucoCompaBlockEntity.SUR_2 -> new float[]{1.038f, 1.0f, 0.68f};
            case MesaTrucoCompaBlockEntity.SUR_3 -> new float[]{1.35f, 1.0f, 0.68f};

            // ESTE (derecha) - 0.1px a la izquierda + 0.5px al norte
            case MesaTrucoCompaBlockEntity.ESTE_1 -> new float[]{1.683f, 1.0f, -0.35f};
            case MesaTrucoCompaBlockEntity.ESTE_2 -> new float[]{1.683f, 1.0f, -0.035f};
            case MesaTrucoCompaBlockEntity.ESTE_3 -> new float[]{1.683f, 1.0f,  0.275f};

            default -> new float[]{1.0f, 1.0f, 0.0f};
        };
    }
}