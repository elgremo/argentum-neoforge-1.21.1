package com.gremo.argentum.entity.client;

import com.gremo.argentum.Argentum;
import com.gremo.argentum.entity.custom.ChorroEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ChorroRenderer extends MobRenderer<ChorroEntity, ChorroModel<ChorroEntity>> {
    public ChorroRenderer(EntityRendererProvider.Context context) {
        super(context, new ChorroModel<>(context.bakeLayer(ChorroModel.LAYER_LOCATION)), 0.20f);
    }

    @Override
    public ResourceLocation getTextureLocation(ChorroEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(Argentum.MOD_ID, "textures/entity/chorro/chorro.png");
    }

    @Override
    public void render(ChorroEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (entity.isBaby()) {
            poseStack.scale(1.5f,1.5f,1.5f);
        }else {
            poseStack.scale(3.8f,3.8f,3.8f);
        }
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}
