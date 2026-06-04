package com.gremo.argentum.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.gremo.argentum.block.entity.ParrillaBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

public class ParrillaBlockEntityRenderer implements BlockEntityRenderer<ParrillaBlockEntity> {
    public ParrillaBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(ParrillaBlockEntity be, float partialTick, PoseStack ps, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        Level level = be.getLevel();
        BlockPos pos = be.getBlockPos();

        float[][] offsets = {
                {-0.25f, -0.25f}, {0f, -0.25f}, {0.25f, -0.25f},
                {-0.25f, 0f},     {0f, 0f},     {0.25f, 0f},
                {-0.25f, 0.25f},  {0f, 0.25f},  {0.25f, 0.25f}
        };

        for (int slot = 0; slot < be.inventory.getSlots(); slot++) {
            ItemStack stack = be.inventory.getStackInSlot(slot);
            if (stack.isEmpty()) continue;

            ps.pushPose();

            float offX = offsets[slot][0];
            float offZ = offsets[slot][1];

            ps.translate(0.5f + offX, 0.9f, 0.5f + offZ);
            ps.scale(0.5f, 0.5f, 0.5f);

            ps.mulPose(Axis.YP.rotationDegrees(be.getRenderingRotation()));

            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, getLightLevel(level, pos),
                    OverlayTexture.NO_OVERLAY, ps, buffer, level, 1);

            ps.popPose();
        }
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}