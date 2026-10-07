package com.gremo.argentum.block.renderer;

import com.gremo.argentum.block.custom.MesaTrucoBlock;
import com.gremo.argentum.block.entity.MesaTrucoBlockEntity;
import com.gremo.argentum.item.ModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;
import net.minecraft.util.Mth;

public class MesaTrucoBlockEntityRenderer implements BlockEntityRenderer<MesaTrucoBlockEntity> {

    public MesaTrucoBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(MesaTrucoBlockEntity be, float partialTick, PoseStack poseStack,
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

        // FACING y ángulo
        Direction facing = be.getBlockState().getValue(MesaTrucoBlock.FACING);
        float facingAngle = switch (facing) {
            case SOUTH -> 0f;
            case NORTH -> 180f;
            case EAST -> 90f;
            case WEST -> -90f;
            default -> 0f;
        };

        // ============================================================
        // 1) ITEMS (con rotación del FACING)
        // ============================================================
        poseStack.pushPose();

        poseStack.translate(0.5, 0, 0.5);
        switch (facing) {
            case SOUTH -> {
            }
            case NORTH -> poseStack.mulPose(Axis.YP.rotationDegrees(180));
            case EAST -> poseStack.mulPose(Axis.YP.rotationDegrees(90));
            case WEST -> poseStack.mulPose(Axis.YP.rotationDegrees(-90));
        }
        poseStack.translate(-0.5, 0, -0.5);

        for (int i = 0; i < MesaTrucoBlockEntity.TOTAL_SLOTS; i++) {
            ItemStack stack = be.getItem(i);
            if (stack.isEmpty()) continue;

            float[] pos = getSlotPosition(i);

            poseStack.pushPose();
            poseStack.translate(pos[0], pos[1], pos[2]);
            poseStack.mulPose(Axis.XP.rotationDegrees(180));

            if (i == MesaTrucoBlockEntity.ARRIBA_IZQ
                    || i == MesaTrucoBlockEntity.ARRIBA_CENTRO
                    || i == MesaTrucoBlockEntity.ARRIBA_DER) {
                poseStack.mulPose(Axis.YP.rotationDegrees(180));
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

        // ============================================================
        // 2) TEXTOS (fijos, rotados según FACING)
        // ============================================================

        // Ángulo para que el texto mire al JUGADOR (opuesto al FACING)
        float jugadorAngle = switch (facing) {
            case SOUTH -> 0f;
            case NORTH -> 180f;
            case WEST  -> 90f;
            case EAST  -> -90f;
            default -> 0f;
        };

        // Ángulo para que el texto mire al RIVAL (opuesto al del jugador)
        float rivalAngle = jugadorAngle + 180f;

        for (int i = 0; i < MesaTrucoBlockEntity.TOTAL_SLOTS; i++) {
            ItemStack stack = be.getItem(i);
            if (stack.isEmpty()) continue;

            if (i != MesaTrucoBlockEntity.CENTRO_IZQ
                    && i != MesaTrucoBlockEntity.CENTRO_DER) continue;

            int valor = getFichaValue(stack);
            if (valor <= 0) continue;

            float[] pos = getSlotPosition(i);

            // Rotar la posición (x, z) alrededor del centro (0.5, 0.5)
            double rad = Math.toRadians(facingAngle);
            double dx = pos[0] - 0.5;
            double dz = pos[2] - 0.5;
            double cos = Math.cos(rad);
            double sin = Math.sin(rad);
            double rotX = 0.5 + dx * cos + dz * sin;
            double rotZ = 0.5 - dx * sin + dz * cos;

            int color = getFichaColor(stack);
            String texto = "x" + valor;

            // Texto 1: mira al jugador
            renderFloatingText(poseStack, buffer, packedLight, texto,
                    (float) rotX, pos[1], (float) rotZ, color, jugadorAngle);

            // Texto 2: mira al rival
            renderFloatingText(poseStack, buffer, packedLight, texto,
                    (float) rotX, pos[1], (float) rotZ, color, rivalAngle);
        }
    }



    /** Color según el ítem. */
    private int getFichaColor(ItemStack stack) {
        if (stack.is(ModItems.FICHA_CASINO_2.get()))        return 0x5599FF; // azul
        if (stack.is(ModItems.FICHA_CASINO_4.get()))        return 0x55FFFF; // verde agua
        if (stack.is(ModItems.FICHA_CASINO_8.get()))        return 0xCC88FF; // lila
        if (stack.is(ModItems.FICHA_CASINO_16.get()))       return 0xFF5555; // rojo
        if (stack.is(ModItems.FICHA_CASINO_32.get()))       return 0x55FF55; // verde
        if (stack.is(ModItems.FICHA_CASINO_64.get()))       return 0xFFFF55; // amarillo
        if (stack.is(ModItems.FICHA_CASINO_ESPECIAL.get())) {
            float hue = (System.currentTimeMillis() % 3000L) / 3000f;
            return Mth.hsvToRgb(hue, 1f, 1f);
        }
        return 0xFFFFFF;
    }

    /**
     * Posiciones en coords locales del master (bloque en worldPosition).
     * Cálculo: pixelCentro / 16 para X y Z (con Z - 1 porque el master es la
     * esquina superior-izquierda de la mesa 2x2).
     * Base: mesa de 32x32 px con origen (0,0) arriba-izquierda.
     */
    private float[] getSlotPosition(int slot) {
        return switch (slot) {
            // Fila superior (rival)
            case MesaTrucoBlockEntity.ARRIBA_IZQ    -> new float[]{0.399f, 1.0f, -0.621f};
            case MesaTrucoBlockEntity.ARRIBA_CENTRO -> new float[]{0.965f,    1.0f, -0.621f};
            case MesaTrucoBlockEntity.ARRIBA_DER    -> new float[]{1.525f, 1.0f, -0.621f};

            // Fila del medio (fichas + mazo)
            case MesaTrucoBlockEntity.CENTRO_IZQ    -> new float[]{0.4745f, 0.95f, 0.0f};
            case MesaTrucoBlockEntity.CENTRO        -> new float[]{1.038f, 1.0f, 0.0f};
            case MesaTrucoBlockEntity.CENTRO_DER    -> new float[]{1.53f, 0.95f, 0.0f};

            // Fila inferior (jugador)
            case MesaTrucoBlockEntity.ABAJO_IZQ     -> new float[]{0.475f, 1.0f, 0.619f};
            case MesaTrucoBlockEntity.ABAJO_CENTRO  -> new float[]{1.038f,    1.0f, 0.619f};
            case MesaTrucoBlockEntity.ABAJO_DER     -> new float[]{1.6f, 1.0f, 0.619f};

            default -> new float[]{1.0f, 1.0f, 0.0f};
        };
    }
    private void renderFloatingText(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                                    String text, float x, float y, float z, int color, float angle) {
        Minecraft mc = Minecraft.getInstance();
        Font font = mc.font;

        poseStack.pushPose();
        poseStack.translate(x, y + 0.4f + 0.1875f, z);

        // Rotación fija según el ángulo recibido (NO sigue a la cámara)
        poseStack.mulPose(Axis.YP.rotationDegrees(angle));

        poseStack.scale(-0.025f, -0.025f, 0.025f);

        Matrix4f matrix = poseStack.last().pose();
        float opacity = mc.options.getBackgroundOpacity(0.25f);
        int bgColor = ((int)(opacity * 255) << 24);

        font.drawInBatch(
                text,
                -font.width(text) / 2f,
                0,
                color,
                false,
                matrix,
                buffer,
                Font.DisplayMode.SEE_THROUGH,
                bgColor,
                packedLight
        );

        poseStack.popPose();
    }

    private int getFichaValue(ItemStack stack) {
        if (stack.is(ModItems.FICHA_CASINO_2.get()))        return 2;
        if (stack.is(ModItems.FICHA_CASINO_4.get()))        return 4;
        if (stack.is(ModItems.FICHA_CASINO_8.get()))        return 8;
        if (stack.is(ModItems.FICHA_CASINO_16.get()))       return 16;
        if (stack.is(ModItems.FICHA_CASINO_32.get()))       return 32;
        if (stack.is(ModItems.FICHA_CASINO_64.get()))       return 64;
        if (stack.is(ModItems.FICHA_CASINO_ESPECIAL.get())) return 100; // o lo que sea
        return 0;
    }
}