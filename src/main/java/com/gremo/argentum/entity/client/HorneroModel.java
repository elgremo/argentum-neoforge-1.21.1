package com.gremo.argentum.entity.client;

import com.gremo.argentum.Argentum;
import com.gremo.argentum.entity.custom.HorneroEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import static com.gremo.argentum.entity.client.HorneroAnimations.*;

public class HorneroModel extends HierarchicalModel<HorneroEntity> { // ← Cambiado a HorneroEntity

    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(
                    ResourceLocation.fromNamespaceAndPath(Argentum.MOD_ID, "hornero"),
                    "main"
            );

    private final ModelPart bone;
    private final ModelPart cuerpo;
    private final ModelPart ala_izquierda_horne;
    private final ModelPart ala_derecha_horne;
    private final ModelPart cabeza;
    private final ModelPart pata_izquierda_horne;
    private final ModelPart pata_derecha_horne;

    public HorneroModel(ModelPart root) {
        this.bone = root.getChild("bone");
        this.cuerpo = this.bone.getChild("cuerpo");
        this.ala_izquierda_horne = this.cuerpo.getChild("ala_izquierda_horne");
        this.ala_derecha_horne = this.cuerpo.getChild("ala_derecha_horne");
        this.cabeza = this.cuerpo.getChild("cabeza");
        this.pata_izquierda_horne = this.bone.getChild("pata_izquierda_horne");
        this.pata_derecha_horne = this.bone.getChild("pata_derecha_horne");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cuerpo = bone.addOrReplaceChild("cuerpo", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition cube_r1 = cuerpo.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(-1.0F, 0.0F, 1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition ala_izquierda_horne = cuerpo.addOrReplaceChild("ala_izquierda_horne", CubeListBuilder.create().texOffs(6, 5).addBox(0.0F, -0.7268F, -0.0962F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -2.0F, -1.75F));

        PartDefinition ala_derecha_horne = cuerpo.addOrReplaceChild("ala_derecha_horne", CubeListBuilder.create().texOffs(0, 5).addBox(0.0F, -0.7268F, -0.0962F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -2.0F, -1.75F));

        PartDefinition cabeza = cuerpo.addOrReplaceChild("cabeza", CubeListBuilder.create().texOffs(10, 0).addBox(-0.5F, -0.9001F, -0.7315F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.25F, -1.5F));

        PartDefinition pata_izquierda_horne = bone.addOrReplaceChild("pata_izquierda_horne", CubeListBuilder.create().texOffs(10, 4).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(10, 2).addBox(-0.5F, 1.0F, -1.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.75F, -1.0F, 0.0F));

        PartDefinition pata_derecha_horne = bone.addOrReplaceChild("pata_derecha_horne", CubeListBuilder.create().texOffs(6, 10).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(10, 3).addBox(-0.5F, 1.0F, -1.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.75F, -1.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Override
    public void setupAnim(HorneroEntity entity,
                          float limbSwing,
                          float limbSwingAmount,
                          float ageInTicks,
                          float netHeadYaw,
                          float headPitch) {

        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch);

        if (!entity.isFlying()) {
            this.animateWalk(WALK_HORNERO, limbSwing, limbSwingAmount, 2.0F, 2.5F);
        }

        this.animate(entity.idleAnimationState, IDLE_HORNERO, ageInTicks, 1.0F);

        if (entity.isFlying()) {
            this.animate(entity.flyAnimationState, FLY_HORNERO, ageInTicks, 1.0F);
        }
    }

    private void applyHeadRotation(float netHeadYaw, float headPitch) {
        netHeadYaw = Mth.clamp(netHeadYaw, -30f, 30f);
        headPitch = Mth.clamp(headPitch, -25f, 45f);
        this.cabeza.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.cabeza.xRot = headPitch * ((float) Math.PI / 180F);
    }

    @Override
    public ModelPart root() {
        return bone;
    }
}