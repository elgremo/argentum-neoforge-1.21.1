package com.gremo.argentum.entity.client;

import com.gremo.argentum.Argentum;
import com.gremo.argentum.entity.custom.DadoEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class DadoRenderer extends EntityRenderer<DadoEntity> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Argentum.MOD_ID, "textures/entity/dado.png");
    private final DadoModel model;

    public DadoRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new DadoModel(context.bakeLayer(DadoModel.LAYER_LOCATION));
    }

    @Override
    public ResourceLocation getTextureLocation(DadoEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(DadoEntity entity, float entityYaw, float partialTick,
                       PoseStack poseStack, MultiBufferSource buffer, int packedLight) {

        poseStack.pushPose();

// Trasladar a la posición de la entidad + altura/2 para centrar visualmente
        poseStack.translate(0, entity.getBbHeight() / 2, 0);
        poseStack.scale(0.4f, 0.4f, 0.4f);

// 🔥 NUEVO: Trasladar el pivot al centro del cubo (que en tu modelo está en -8, -8, -8)
        poseStack.translate(0, 0, 0); // Esto no es necesario, el modelo ya está centrado en el origen

// Aplicar rotación sobre el centro del cubo
        float vida = entity.getVida();
        float rotacionTotal = (60 - vida) * 10f;
        float rotX = rotacionTotal * 0.7f + partialTick * 7f;
        float rotY = rotacionTotal * 1.0f + partialTick * 10f;
        float rotZ = rotacionTotal * 0.5f + partialTick * 5f;

        poseStack.mulPose(new org.joml.Quaternionf().rotationXYZ(
                (float)Math.toRadians(rotX),
                (float)Math.toRadians(rotY),
                (float)Math.toRadians(rotZ)
        ));

        // Renderizar el modelo
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutout(TEXTURE));
        model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);

        poseStack.popPose();
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }
}