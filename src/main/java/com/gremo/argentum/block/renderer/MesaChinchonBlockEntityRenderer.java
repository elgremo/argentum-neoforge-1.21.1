package com.gremo.argentum.block.renderer;

import com.gremo.argentum.block.custom.MesaChinchonBlock;
import com.gremo.argentum.block.entity.MesaChinchonBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class MesaChinchonBlockEntityRenderer implements BlockEntityRenderer<MesaChinchonBlockEntity> {

    public MesaChinchonBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(MesaChinchonBlockEntity be, float partialTick, PoseStack poseStack,
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
        Direction facing = be.getBlockState().getValue(MesaChinchonBlock.FACING);
        poseStack.translate(0.5, 0, 0.5);
        switch (facing) {
            case SOUTH -> {}
            case NORTH -> poseStack.mulPose(Axis.YP.rotationDegrees(180));
            case EAST  -> poseStack.mulPose(Axis.YP.rotationDegrees(90));
            case WEST  -> poseStack.mulPose(Axis.YP.rotationDegrees(-90));
        }
        poseStack.translate(-0.5, 0, -0.5);

        // Dibujar los 14 slots visibles
        for (int i = 0; i < MesaChinchonBlockEntity.TOTAL_SLOTS; i++) {
            ItemStack stack = be.getItem(i);
            if (stack.isEmpty()) continue;

            float[] pos = getSlotPosition(i);

            poseStack.pushPose();
            poseStack.translate(pos[0], pos[1], pos[2]);

            // Acostado
            poseStack.mulPose(Axis.XP.rotationDegrees(180));

            // Rotar según el lado
            float yRot = getSideRotation(i);
            if (yRot != 0f) {
                poseStack.mulPose(Axis.YP.rotationDegrees(yRot));
            }

            poseStack.scale(0.6f, 0.8f, 0.6f);

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
        // ============================================================
        // DESCARTE (2 cartas visibles, apiladas)
        // ============================================================
        ItemStack topDescarte = be.peekDescarte(0);
        ItemStack secondDescarte = be.peekDescarte(1);

        if (!secondDescarte.isEmpty()) {
            int owner = be.getDescarteOwner(1);
            renderDescarteCard(poseStack, buffer, packedLight, packedOverlay,
                    secondDescarte, 1.0f, owner, be.getLevel());
        }

        if (!topDescarte.isEmpty()) {
            int owner = be.getDescarteOwner(0);
            renderDescarteCard(poseStack, buffer, packedLight, packedOverlay,
                    topDescarte, 1.014f, owner, be.getLevel());
        }

        poseStack.popPose();
    }

    /**
     * Rotación en Y según el lado:
     *   RIVAL   → 180° (mira al sur, hacia el centro)
     *   JUGADOR → 0°   (mira al norte, hacia el centro)
     */
    private float getSideRotation(int slot) {
        if (slot >= MesaChinchonBlockEntity.RIVAL_1
                && slot <= MesaChinchonBlockEntity.RIVAL_7) {
            return 180f;
        }
        return 0f; // jugador
    }

    /**
     * Posiciones en coords locales del master.
     * El master es el bloque SUROESTE del 2x2.
     */
    private float[] getSlotPosition(int slot) {
        float y = 1f;

        return switch (slot) {
            // RIVAL (norte, Z ~-0.47)
            case MesaChinchonBlockEntity.RIVAL_1 -> new float[]{0.315f, y, -0.46f};
            case MesaChinchonBlockEntity.RIVAL_2 -> new float[]{0.533f, y, -0.46f};
            case MesaChinchonBlockEntity.RIVAL_3 -> new float[]{0.751f, y, -0.46f};
            case MesaChinchonBlockEntity.RIVAL_4 -> new float[]{0.974f, y, -0.46f};
            case MesaChinchonBlockEntity.RIVAL_5 -> new float[]{1.189f, y, -0.46f};
            case MesaChinchonBlockEntity.RIVAL_6 -> new float[]{1.41f, y, -0.46f};
            case MesaChinchonBlockEntity.RIVAL_7 -> new float[]{1.62f, y, -0.46f};

            // JUGADOR (sur, Z ~0.47)
            case MesaChinchonBlockEntity.JUGADOR_1 -> new float[]{0.372f, y, 0.46f};
            case MesaChinchonBlockEntity.JUGADOR_2 -> new float[]{0.59f, y, 0.46f};
            case MesaChinchonBlockEntity.JUGADOR_3 -> new float[]{0.808f, y, 0.46f};
            case MesaChinchonBlockEntity.JUGADOR_4 -> new float[]{1.031f, y, 0.46f};
            case MesaChinchonBlockEntity.JUGADOR_5 -> new float[]{1.246f, y, 0.46f};
            case MesaChinchonBlockEntity.JUGADOR_6 -> new float[]{1.467f, y, 0.46f};
            case MesaChinchonBlockEntity.JUGADOR_7 -> new float[]{1.686f, y, 0.46f};

            default -> new float[]{1.0f, y, 0.0f};
        };
    }

    /**
     * Renderiza una carta del descarte.
     * Posición: al lado del mazo, ligeramente al este.
     */
    private void renderDescarteCard(PoseStack poseStack, MultiBufferSource buffer,
                                    int packedLight, int packedOverlay,
                                    ItemStack stack, float y, int owner,
                                    net.minecraft.world.level.Level level) {

        poseStack.pushPose();

        poseStack.translate(1.31f, y, 0.0f);

        // Acostada
        poseStack.mulPose(Axis.XP.rotationDegrees(180));

        // Rotación según quién la tiró
        if (owner == 1) {
            poseStack.mulPose(Axis.YP.rotationDegrees(180));
        }

        poseStack.scale(0.6f, 0.8f, 0.6f);

        Minecraft.getInstance().getItemRenderer().renderStatic(
                stack,
                ItemDisplayContext.GROUND,
                packedLight,
                packedOverlay,
                poseStack,
                buffer,
                level,
                0
        );

        poseStack.popPose();
    }
}