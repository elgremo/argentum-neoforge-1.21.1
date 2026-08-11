package com.gremo.argentum.entity.client;

import com.gremo.argentum.Argentum;
import com.gremo.argentum.entity.custom.ChorroEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.world.entity.HumanoidArm;
import com.gremo.argentum.entity.client.ChorroAnimations;

public class ChorroModel extends HierarchicalModel<ChorroEntity> implements ArmedModel {

    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(
                    ResourceLocation.fromNamespaceAndPath(Argentum.MOD_ID, "chorro"),
                    "main"
            );

    private final ModelPart bone;
    private final ModelPart cabeza;
    private final ModelPart brazo_derecho;
    private final ModelPart placeholder;
    private final ModelPart brazo_izquierdo;
    private final ModelPart cuerpo;
    private final ModelPart pie_derecho;
    private final ModelPart pie_izquierdo;

    public ChorroModel(ModelPart root) {
        this.bone = root.getChild("bone");
        this.cabeza = this.bone.getChild("cabeza");
        this.brazo_derecho = this.bone.getChild("brazo_derecho");
        this.placeholder = this.brazo_derecho.getChild("placeholder");
        this.brazo_izquierdo = this.bone.getChild("brazo_izquierdo");
        this.cuerpo = this.bone.getChild("cuerpo");
        this.pie_derecho = this.bone.getChild("pie_derecho");
        this.pie_izquierdo = this.bone.getChild("pie_izquierdo");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cabeza = bone.addOrReplaceChild("cabeza", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -24.0F, 0.0F));

        PartDefinition brazo_derecho = bone.addOrReplaceChild("brazo_derecho", CubeListBuilder.create().texOffs(40, 16).addBox(-4.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -24.0F, 0.0F));

        PartDefinition placeholder = brazo_derecho.addOrReplaceChild("placeholder", CubeListBuilder.create(), PartPose.offset(-2.0F, 12.5F, -2.0F));

        PartDefinition placeholder_r1 = placeholder.addOrReplaceChild("placeholder_r1", CubeListBuilder.create().texOffs(71, 25).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(71, 25).addBox(-2.5F, 0.0F, -1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.25F, -1.25F, -1.0F, 0.3054F, 0.0F, 0.0F));

        PartDefinition placeholder_r2 = placeholder.addOrReplaceChild("placeholder_r2", CubeListBuilder.create().texOffs(63, 25).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.75F, -1.0F, 0.3054F, 0.0F, 0.0F));

        PartDefinition cube_r1 = placeholder.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(60, 22).addBox(-1.0F, -1.75F, -3.25F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, -2.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition brazo_izquierdo = bone.addOrReplaceChild("brazo_izquierdo", CubeListBuilder.create().texOffs(32, 48).addBox(0.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -24.0F, 0.0F));

        PartDefinition cuerpo = bone.addOrReplaceChild("cuerpo", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, -6.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -18.0F, 0.0F));

        PartDefinition pie_derecho = bone.addOrReplaceChild("pie_derecho", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, 0.0F));

        PartDefinition pie_izquierdo = bone.addOrReplaceChild("pie_izquierdo", CubeListBuilder.create().texOffs(16, 48).addBox(0.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(ChorroEntity entity,
                          float limbSwing,
                          float limbSwingAmount,
                          float ageInTicks,
                          float netHeadYaw,
                          float headPitch) {

        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.cabeza.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.cabeza.xRot = headPitch * ((float)Math.PI / 180F);

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

        this.animate(
                entity.shootAnimationState,
                ChorroAnimations.SHOOT,
                ageInTicks,
                1.0F
        );

        this.animate(
                entity.hurtAnimationState,
                ChorroAnimations.HURT,
                ageInTicks,
                1.0F
        );

        this.animate(
                entity.deathAnimationState,
                ChorroAnimations.DEATH,
                ageInTicks,
                1.0F
        );
    }


    @Override
    public ModelPart root() {
        return bone;
    }

    @Override
    public void translateToHand(HumanoidArm arm, PoseStack poseStack) {

        if (arm == HumanoidArm.RIGHT) {

            this.placeholder.translateAndRotate(poseStack);

        } else {

            this.brazo_izquierdo.translateAndRotate(poseStack);

        }
    }
}
