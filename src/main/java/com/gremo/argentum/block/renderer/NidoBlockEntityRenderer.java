package com.gremo.argentum.block.renderer;

import com.gremo.argentum.block.entity.NidoBlockEntity;
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

public class NidoBlockEntityRenderer implements BlockEntityRenderer<NidoBlockEntity> {
    public NidoBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(NidoBlockEntity be, float partialTick, PoseStack ps, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        Level level = be.getLevel();
        BlockPos pos = be.getBlockPos();

        // Posiciones relativas para cada huevo (4 huevos en forma de cuadrado)
        float[][] offsets = {
                {-0.22f, -0.22f}, // slot 0
                { 0.22f, -0.22f}, // slot 1
                {-0.22f,  0.22f}, // slot 2
                { 0.22f,  0.22f}  // slot 3
        };

        for (int slot = 0; slot < be.inventory.getSlots(); slot++) {
            ItemStack stack = be.getHuevoInSlot(slot);
            if (stack.isEmpty()) continue;

            ps.pushPose();

            float offX = offsets[slot][0];
            float offZ = offsets[slot][1];

            // Posicionar el huevo sobre el nido (a media altura)
            ps.translate(0.5f + offX, 0.85f, 0.5f + offZ);
            // Escala para que sea un huevo pequeño
            ps.scale(1.5f, 1.5f, 1.5f);

            // ❌ ELIMINAR O COMENTAR ESTAS LÍNEAS PARA QUE NO GIREN
            // long gameTime = level.getGameTime() + slot * 10;
            // ps.mulPose(Axis.YP.rotationDegrees((gameTime * 2) % 360));

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