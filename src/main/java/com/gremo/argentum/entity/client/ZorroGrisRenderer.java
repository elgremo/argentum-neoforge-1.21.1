package com.gremo.argentum.entity.client;

import com.gremo.argentum.Argentum;
import com.gremo.argentum.entity.custom.ZorroGrisEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ZorroGrisRenderer extends MobRenderer<ZorroGrisEntity, ZorroGrisModel> {

    private static final ResourceLocation TEXTURE_BASE =
            ResourceLocation.fromNamespaceAndPath(Argentum.MOD_ID, "textures/entity/zorro_gris/zorro_gris.png");

    public ZorroGrisRenderer(EntityRendererProvider.Context context) {
        super(context, new ZorroGrisModel(context.bakeLayer(ZorroGrisModel.LAYER_LOCATION)), 0.4F);
        // Agregar la capa del collar
        this.addLayer(new ZorroGrisCollarLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(ZorroGrisEntity entity) {
        // Usamos siempre la textura base (el collar se pinta por separado)
        return TEXTURE_BASE;
    }

    @Override
    public void render(ZorroGrisEntity entity, float entityYaw, float partialTicks,
                       PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        // Escala si es bebé
        if (entity.isBaby()) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
        }
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}