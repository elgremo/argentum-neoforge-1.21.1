package com.gremo.argentum.entity.client;

import com.gremo.argentum.Argentum;
import com.gremo.argentum.entity.custom.HorneroEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class HorneroRenderer extends MobRenderer<HorneroEntity, HorneroModel> {

    public HorneroRenderer(EntityRendererProvider.Context context) {
        super(context, new HorneroModel(context.bakeLayer(HorneroModel.LAYER_LOCATION)), 0.20F);
    }

    @Override
    public ResourceLocation getTextureLocation(HorneroEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(Argentum.MOD_ID, "textures/entity/hornero/hornero.png");
    }

    @Override
    public void render(HorneroEntity entity,
                       float entityYaw,
                       float partialTicks,
                       PoseStack poseStack,
                       MultiBufferSource buffer,
                       int packedLight) {

        poseStack.pushPose();

        if (entity.isBaby()) {
            poseStack.scale(0.6F, 0.6F, 0.6F);
        } else {
            poseStack.scale(1F, 1F, 1F);
        }

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);

        poseStack.popPose();
    }
}