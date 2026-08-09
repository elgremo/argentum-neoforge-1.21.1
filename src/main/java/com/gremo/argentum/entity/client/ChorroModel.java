package com.gremo.argentum.entity.client;

import com.gremo.argentum.Argentum;
import com.gremo.argentum.entity.custom.ChorroEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class ChorroModel <T extends ChorroEntity> extends HierarchicalModel <T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Argentum.MOD_ID, "chorro"), "main");

    private final ModelPart Cuerpo;
    private final ModelPart Cabeza;
    private final ModelPart root;

    public ChorroModel(ModelPart root) {

        this.root = root.getChild("bone");
        this.Cuerpo = this.root.getChild("cuerpo");
        this.Cabeza = this.root.getChild("cabeza");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition pie_izq = bone.addOrReplaceChild("pie izq", CubeListBuilder.create().texOffs(0, 4).addBox(0.0F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));

        PartDefinition cuerpo = bone.addOrReplaceChild("cuerpo", CubeListBuilder.create().texOffs(4, 4).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 0.5F));

        PartDefinition pie_der = bone.addOrReplaceChild("pie der", CubeListBuilder.create().texOffs(0, 4).addBox(-1.0F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));

        PartDefinition br_izq = bone.addOrReplaceChild("br izq", CubeListBuilder.create().texOffs(10, 4).addBox(0.0F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -6.0F, 0.0F));

        PartDefinition br_der = bone.addOrReplaceChild("br der", CubeListBuilder.create().texOffs(10, 4).addBox(-1.0F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -6.0F, 0.0F));

        PartDefinition cabeza = bone.addOrReplaceChild("cabeza", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Override
    public void setupAnim(ChorroEntity entity,
                          float limbSwing,
                          float limbSwingAmount,
                          float ageInTicks,
                          float netHeadYaw,
                          float headPitch) {

        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.animateWalk(
                ChorroAnimations.WALK,
                limbSwing,
                limbSwingAmount,
                2.0F,
                2.5F
        );

        this.animate(
                entity.idleAnimationState,
                ChorroAnimations.IDLE,
                ageInTicks,
                1.0F
        );

        this.Cabeza.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.Cabeza.xRot = headPitch * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer,
                               int packedLight, int packedOverlay, int color) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return root;
    }


}
