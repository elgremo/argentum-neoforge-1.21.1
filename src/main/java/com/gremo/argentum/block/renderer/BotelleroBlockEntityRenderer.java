package com.gremo.argentum.block.renderer;

import com.gremo.argentum.block.custom.BotelleroBlock;
import com.gremo.argentum.block.entity.BotelleroBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class BotelleroBlockEntityRenderer implements BlockEntityRenderer<BotelleroBlockEntity> {

    public BotelleroBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(
            BotelleroBlockEntity be,
            float partialTick,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            int packedOverlay) {

        poseStack.pushPose();

        transformarBotellero(be, poseStack);

        for (int slot = 0; slot < 18; slot++) {
            ItemStack stack = be.getItem(slot);
            if (stack.isEmpty()) {
                continue;
            }

            Vec3 pos = getPosicion(slot);

            pos = rotarPosicion(
                    pos,
                    be.getBlockState().getValue(BotelleroBlock.FACING)
            );

            renderBotella(
                    be,
                    stack,
                    poseStack,
                    buffer,
                    packedLight,
                    packedOverlay,
                    (float) pos.x,
                    (float) pos.y,
                    (float) pos.z
            );
        }

        poseStack.popPose();
    }

    private void renderBotella(
            BotelleroBlockEntity be,
            ItemStack stack,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            int packedOverlay,
            float x,
            float y,
            float z) {

        poseStack.pushPose();

        poseStack.translate(
                x,
                y - 0.3F,
                z
        );
        poseStack.mulPose(Axis.XP.rotationDegrees(90));

        Minecraft.getInstance()
                .getItemRenderer()
                .renderStatic(
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

    private void transformarBotellero(
            BotelleroBlockEntity be,
            PoseStack poseStack) {

        Direction facing =
                be.getBlockState().getValue(BotelleroBlock.FACING);

        poseStack.translate(
                0.5,
                0.22,
                0.5
        );

        switch (facing) {

            case SOUTH -> {}

            case NORTH ->
                    poseStack.mulPose(Axis.YP.rotationDegrees(180));

            case EAST ->
                    poseStack.mulPose(Axis.YP.rotationDegrees(90));

            case WEST ->
                    poseStack.mulPose(Axis.YP.rotationDegrees(-90));
        }

        poseStack.scale(
                1.2F,
                1.2F,
                1.2F
        );
    }
    private Vec3 rotarPosicion(Vec3 pos, Direction facing) {

        return switch (facing) {

            case NORTH -> pos;

            case SOUTH -> pos;

            case EAST -> new Vec3(
                    pos.x,
                    pos.y,
                    pos.z
            );

            case WEST -> new Vec3(
                    pos.x,
                    pos.y,
                    pos.z
            );

            default -> pos;
        };
    }

    private Vec3 getPosicion(int slot) {

        return switch (slot) {

            // Repisa 1
            case 0  -> new Vec3(-0.205, 0.300, -0.30);
            case 1  -> new Vec3( 0.000, 0.300, -0.30);
            case 2  -> new Vec3( 0.205, 0.300, -0.30);

// Repisa 2
            case 3  -> new Vec3(-0.205, 0.574, -0.30);
            case 4  -> new Vec3( 0.000, 0.574, -0.30);
            case 5  -> new Vec3( 0.205, 0.574, -0.30);

// Repisa 3
            case 6  -> new Vec3(-0.205, 0.85, -0.30);
            case 7  -> new Vec3( 0.000, 0.85, -0.30);
            case 8  -> new Vec3( 0.205, 0.85, -0.30);

// Repisa 4
            case 9  -> new Vec3(-0.205, 1.122, -0.30);
            case 10 -> new Vec3( 0.000, 1.122, -0.30);
            case 11 -> new Vec3( 0.205, 1.122, -0.30);

// Repisa 5
            case 12 -> new Vec3(-0.205, 1.4, -0.30);
            case 13 -> new Vec3( 0.000, 1.4, -0.30);
            case 14 -> new Vec3( 0.205, 1.4, -0.30);

// Repisa 6
            case 15 -> new Vec3(-0.205, 1.670, -0.30);
            case 16 -> new Vec3( 0.000, 1.670, -0.30);
            case 17 -> new Vec3( 0.205, 1.670, -0.30);

            default -> Vec3.ZERO;
        };
    }



}