package com.gremo.argentum.client.renderer;

import com.gremo.argentum.entity.custom.PelotaEntity;
import com.gremo.argentum.item.ModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;

public class PelotaRenderer extends EntityRenderer<PelotaEntity> {
    private final ItemRenderer itemRenderer;

    public PelotaRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
        this.itemRenderer = ctx.getItemRenderer();
    }

    @Override
    public void render(PelotaEntity entity, float yaw, float partialTicks, PoseStack matrix, MultiBufferSource buffer, int packedLight) {
        matrix.pushPose();

        ItemStack stack = entity.getDisplay();
        if (stack == null || stack.isEmpty()) {
            stack = new ItemStack(ModItems.PELOTA.get());
        }

        // Renderizamos el ItemStack en el mundo (GROUND funciona bien para objetos sobre el suelo)
        itemRenderer.renderStatic(
                /* livingEntity */ null,
                /* stack */ stack,
                /* transformType */ ItemDisplayContext.GROUND,
                /* leftHand */ false,
                /* poseStack */ matrix,
                /* bufferSource */ buffer,
                /* level */ entity.level(),
                /* packedLight */ packedLight,
                /* overlay */ OverlayTexture.NO_OVERLAY,
                /* model */ 0
        );

        matrix.popPose();
        super.render(entity, yaw, partialTicks, matrix, buffer, packedLight);
    }


    @Override
    public ResourceLocation getTextureLocation(PelotaEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}