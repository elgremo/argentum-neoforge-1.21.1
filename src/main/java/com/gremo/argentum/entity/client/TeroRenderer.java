package com.gremo.argentum.entity.client;

import com.gremo.argentum.Argentum;
import com.gremo.argentum.entity.custom.TeroEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;


public class TeroRenderer extends MobRenderer<TeroEntity, TeroModel> {

    public TeroRenderer(EntityRendererProvider.Context context) {
        super(context, new TeroModel(context.bakeLayer(TeroModel.LAYER_LOCATION)), 0.20F);
    }

    @Override
    public ResourceLocation getTextureLocation(TeroEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(Argentum.MOD_ID, "textures/entity/tero/tero.png");
    }

    @Override
    public void render(TeroEntity entity,
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
