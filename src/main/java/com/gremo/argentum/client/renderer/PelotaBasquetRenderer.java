package com.gremo.argentum.client.renderer;

import com.gremo.argentum.entity.custom.PelotaBasquetEntity;
import com.gremo.argentum.item.ModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class PelotaBasquetRenderer extends EntityRenderer<PelotaBasquetEntity> {

    private final ItemRenderer itemRenderer;

    public PelotaBasquetRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
        this.itemRenderer = ctx.getItemRenderer();
    }

    @Override
    public void render(PelotaBasquetEntity entity, float yaw, float partialTicks, PoseStack matrix,
                       MultiBufferSource buffer, int packedLight) {
        matrix.pushPose();

        ItemStack stack = entity.getDisplay();
        if (stack == null || stack.isEmpty()) {
            stack = new ItemStack(ModItems.PELOTA_BASQUET.get());
        }
        matrix.scale(1.5F, 1.5F, 1.5F);
        itemRenderer.renderStatic(
                null,
                stack,
                ItemDisplayContext.GROUND,
                false,
                matrix,
                buffer,
                entity.level(),
                packedLight,
                OverlayTexture.NO_OVERLAY,
                0
        );

        matrix.popPose();
        super.render(entity, yaw, partialTicks, matrix, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(PelotaBasquetEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}