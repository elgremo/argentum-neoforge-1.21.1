package com.gremo.argentum.entity.client;

import com.gremo.argentum.Argentum;
import com.gremo.argentum.entity.custom.ZorroGrisEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class ZorroGrisCollarLayer extends RenderLayer<ZorroGrisEntity, ZorroGrisModel> {

    private static final ResourceLocation COLLAR_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(
                    Argentum.MOD_ID,
                    "textures/entity/zorro_gris/zorro_gris_collar.png"
            );

    public ZorroGrisCollarLayer(RenderLayerParent<ZorroGrisEntity, ZorroGrisModel> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                       ZorroGrisEntity entity, float limbSwing, float limbSwingAmount,
                       float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {

        if (entity.isTame()) {
            int color = entity.getCollarColor();
            int packedColor = color | 0xFF000000;

            ZorroGrisModel model = this.getParentModel();

            VertexConsumer vertexConsumer =
                    buffer.getBuffer(RenderType.entityTranslucent(COLLAR_TEXTURE));

            model.renderCollar(
                    poseStack,
                    vertexConsumer,
                    packedLight,
                    OverlayTexture.NO_OVERLAY,
                    packedColor
            );
        }
    }
}