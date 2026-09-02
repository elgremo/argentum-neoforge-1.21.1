package com.gremo.argentum.block.renderer;

import com.gremo.argentum.block.entity.PrensaMostoBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
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

public class PrensaMostoBlockEntityRenderer implements BlockEntityRenderer<PrensaMostoBlockEntity> {
    public PrensaMostoBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(PrensaMostoBlockEntity be, float partialTick, PoseStack ps, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        Level level = be.getLevel();
        BlockPos pos = be.getBlockPos();

        // ⭐ 8 posiciones, omitiendo el centro (índice 4 de una grilla 3x3)
        float[][] offsets = {
                {-0.18f, -0.18f}, // slot 0: arriba-izquierda
                {0f, -0.18f},     // slot 1: arriba-centro
                {0.18f, -0.18f},  // slot 2: arriba-derecha
                {-0.18f, 0f},     // slot 3: medio-izquierda
                {0.18f, 0f},      // slot 4: medio-derecha (centro vacío)
                {-0.18f, 0.18f},  // slot 5: abajo-izquierda
                {0f, 0.18f},      // slot 6: abajo-centro
                {0.18f, 0.18f}    // slot 7: abajo-derecha
        };

        for (int slot = 0; slot < be.inventory.getSlots(); slot++) {
            ItemStack stack = be.inventory.getStackInSlot(slot);
            if (stack.isEmpty()) continue;

            ps.pushPose();

            float offX = offsets[slot][0];
            float offZ = offsets[slot][1];

            ps.translate(0.5f + offX, 0.8f, 0.5f + offZ);
            ps.scale(0.3f, 0.3f, 0.3f);

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