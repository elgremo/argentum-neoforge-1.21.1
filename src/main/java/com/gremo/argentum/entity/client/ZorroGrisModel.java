package com.gremo.argentum.entity.client;

import com.gremo.argentum.Argentum;
import com.gremo.argentum.entity.custom.ZorroGrisEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import static com.gremo.argentum.entity.client.ZorroGrisAnimations.*;

public class ZorroGrisModel extends HierarchicalModel<ZorroGrisEntity> {

    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(
                    ResourceLocation.fromNamespaceAndPath(Argentum.MOD_ID, "zorro_gris"),
                    "main"
            );

    // Todas las partes del modelo
    private final ModelPart bone;
    private final ModelPart cuerpo;
    public final ModelPart collar;  // ⬅️ HACEMOS PÚBLICO para acceder desde la capa
    private final ModelPart cuello;
    private final ModelPart cabeza;
    private final ModelPart oreja_izquierda;
    private final ModelPart oreja_derecha;
    private final ModelPart pata_dela_izq;
    private final ModelPart pata_dela_der;
    private final ModelPart pata_tra_izq;
    private final ModelPart pata_tra_der;
    private final ModelPart cola;

    public ZorroGrisModel(ModelPart root) {
        this.bone = root.getChild("bone");
        this.cuerpo = this.bone.getChild("cuerpo");
        this.collar = this.cuerpo.getChild("collar");
        this.cuello = this.cuerpo.getChild("cuello");
        this.cabeza = this.cuello.getChild("cabeza");
        this.oreja_izquierda = this.cabeza.getChild("oreja_izquierda");
        this.oreja_derecha = this.cabeza.getChild("oreja_derecha");
        this.pata_dela_izq = this.cuerpo.getChild("pata_dela_izq");
        this.pata_dela_der = this.cuerpo.getChild("pata_dela_der");
        this.pata_tra_izq = this.cuerpo.getChild("pata_tra_izq");
        this.pata_tra_der = this.cuerpo.getChild("pata_tra_der");
        this.cola = this.cuerpo.getChild("cola");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 18.0F, -9.0F));

        PartDefinition cuerpo = bone.addOrReplaceChild("cuerpo", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 8.0F));

        PartDefinition cube_r1 = cuerpo.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(24, 15).addBox(-1.0F, -11.0F, -4.0F, 6.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -4.0F, 6.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition collar = cuerpo.addOrReplaceChild("collar", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, -5.0F));

        PartDefinition collar_blanco_r1 = collar.addOrReplaceChild("collar_blanco_r1", CubeListBuilder.create().texOffs(24, 15).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition cuello = cuerpo.addOrReplaceChild("cuello", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, -5.0F));

        PartDefinition cube_r2 = cuello.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(31, 23).addBox(-2.0F, -5.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cabeza = cuello.addOrReplaceChild("cabeza", CubeListBuilder.create().texOffs(0, 5).addBox(-4.0F, -3.0F, -6.0F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(6, 18).addBox(-2.0F, 1.0F, -9.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, -1.0F));

        PartDefinition oreja_izquierda = cabeza.addOrReplaceChild("oreja_izquierda", CubeListBuilder.create().texOffs(15, 1).addBox(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 0).addBox(-1.0F, -4.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -3.0F, -3.0F));

        PartDefinition oreja_derecha = cabeza.addOrReplaceChild("oreja_derecha", CubeListBuilder.create().texOffs(9, 0).addBox(0.0F, -4.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 1).addBox(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -3.0F, -3.0F));

        PartDefinition pata_dela_izq = cuerpo.addOrReplaceChild("pata_dela_izq", CubeListBuilder.create().texOffs(4, 23).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.0F, -4.0F));

        PartDefinition pata_dela_der = cuerpo.addOrReplaceChild("pata_dela_der", CubeListBuilder.create().texOffs(13, 23).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 0.0F, -4.0F));

        PartDefinition pata_tra_izq = cuerpo.addOrReplaceChild("pata_tra_izq", CubeListBuilder.create().texOffs(4, 23).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.0F, 5.0F));

        PartDefinition pata_tra_der = cuerpo.addOrReplaceChild("pata_tra_der", CubeListBuilder.create().texOffs(13, 23).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 0.0F, 5.0F));

        PartDefinition cola = cuerpo.addOrReplaceChild("cola", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 6.0F));

        PartDefinition cube_r3 = cola.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(30, 0).addBox(0.0F, -11.0F, -4.0F, 4.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -1.0F, 11.0F, 1.5708F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 48, 32);
    }

    @Override
    public ModelPart root() {
        return bone;
    }

    @Override
    public void setupAnim(ZorroGrisEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // Reiniciar todas las partes
        this.root().getAllParts().forEach(ModelPart::resetPose);

        // Rotación de cabeza (look)
        this.applyHeadRotation(netHeadYaw, headPitch);

        // Caminar
        this.animateWalk(CAMINAR, limbSwing, limbSwingAmount, 1.0F, 2.5F);

        // Idle (si no está sentado)
        if (!entity.isInSittingPose()) {
            this.animate(entity.idleAnimationState, IDLE, ageInTicks, 1.0F);
        }

        // Sentado
        if (entity.isInSittingPose()) {
            this.animate(entity.sitAnimationState, SENTADO, ageInTicks, 1.0F);
        }

        // 6. Ataque
        if (entity.isAttacking()) {
            this.animate(entity.attackAnimationState, ATAQUE, ageInTicks, 1.0F);
        }

        // Natación
        if (entity.isInWater()) {
            this.animate(entity.swimAnimationState, NADO, ageInTicks, 1.0F);
        }
    }

    private void applyHeadRotation(float netHeadYaw, float headPitch) {
        netHeadYaw = Mth.clamp(netHeadYaw, -30f, 30f);
        headPitch = Mth.clamp(headPitch, -25f, 45f);
        this.cabeza.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.cabeza.xRot = headPitch * ((float) Math.PI / 180F);
    }

    public void renderCollar(
            PoseStack poseStack,
            VertexConsumer vertexConsumer,
            int packedLight,
            int packedOverlay,
            int packedColor
    ) {
        poseStack.pushPose();

        this.bone.translateAndRotate(poseStack);
        this.cuerpo.translateAndRotate(poseStack);

        this.collar.render(
                poseStack,
                vertexConsumer,
                packedLight,
                packedOverlay,
                packedColor
        );

        poseStack.popPose();
    }
}