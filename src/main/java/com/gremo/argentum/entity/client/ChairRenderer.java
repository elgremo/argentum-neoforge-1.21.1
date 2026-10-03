package com.gremo.argentum.entity.client;

import com.gremo.argentum.entity.custom.ChairEntity;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ChairRenderer extends EntityRenderer<ChairEntity> {

    public ChairRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(ChairEntity entity) {
        // No usamos textura (la entidad es invisible)
        return ResourceLocation.fromNamespaceAndPath("argentum", "textures/entity/chair.png");
    }

    @Override
    public boolean shouldRender(ChairEntity entity, Frustum frustum, double camX, double camY, double camZ) {
        // Siempre "renderiza" pero no dibuja nada (invisible)
        return true;
    }
}