package com.gremo.argentum.entity.client;

import com.gremo.argentum.entity.custom.BalaEntity;
import com.gremo.argentum.item.ModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class BalaRenderer extends EntityRenderer<BalaEntity> {

    private final ItemRenderer itemRenderer;

    public BalaRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(BalaEntity entity,
                       float entityYaw,
                       float partialTicks,
                       PoseStack poseStack,
                       MultiBufferSource buffer,
                       int packedLight) {

        poseStack.pushPose();

        // Mirar hacia donde viaja
        float yaw = entity.yRotO + (entity.getYRot() - entity.yRotO) * partialTicks;
        float pitch = entity.xRotO + (entity.getXRot() - entity.xRotO) * partialTicks;

        poseStack.mulPose(Axis.YP.rotationDegrees(yaw));
        poseStack.mulPose(Axis.XP.rotationDegrees(-pitch));
        poseStack.scale(1.5F, 1.5F, 1.5F);
        // Acostar la bala
        poseStack.mulPose(Axis.ZP.rotationDegrees(90));
        poseStack.mulPose(Axis.XP.rotationDegrees(90));

        itemRenderer.renderStatic(
                new ItemStack(ModItems.BALA.get()),
                ItemDisplayContext.GROUND,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                buffer,
                entity.level(),
                entity.getId()
        );

        poseStack.popPose();

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(BalaEntity entity) {
        return null;
    }
}