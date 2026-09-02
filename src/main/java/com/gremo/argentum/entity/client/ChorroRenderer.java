package com.gremo.argentum.entity.client;

import com.gremo.argentum.Argentum;
import com.gremo.argentum.entity.custom.ChorroEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class ChorroRenderer extends MobRenderer<ChorroEntity, ChorroModel> {

    public ChorroRenderer(EntityRendererProvider.Context context) {
        super(context, new ChorroModel(context.bakeLayer(ChorroModel.LAYER_LOCATION)), 0.20F);
    }



    @Override
    public ResourceLocation getTextureLocation(ChorroEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(Argentum.MOD_ID, "textures/entity/chorro/chorro.png");
    }

    @Override
    public void render(ChorroEntity entity,
                       float entityYaw,
                       float partialTicks,
                       PoseStack poseStack,
                       MultiBufferSource buffer,
                       int packedLight) {

        poseStack.pushPose();

        if (entity.isBaby()) {
            poseStack.scale(1.5F, 1.5F, 1.5F);
        } else {
            poseStack.scale(1F, 1F, 1F);
        }

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);

        poseStack.popPose();
    }
}
