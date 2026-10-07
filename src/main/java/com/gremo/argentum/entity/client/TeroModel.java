package com.gremo.argentum.entity.client;

import com.gremo.argentum.Argentum;
import com.gremo.argentum.entity.custom.TeroEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import static com.gremo.argentum.entity.client.TeroAnimations.*;

public class TeroModel extends HierarchicalModel<TeroEntity>{

    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(
                    ResourceLocation.fromNamespaceAndPath(Argentum.MOD_ID, "tero"),
                    "main"
            );

    private final ModelPart bone;
    private final ModelPart pata_izquierda;
    private final ModelPart pata_derecha;
    private final ModelPart cuerpo;
    private final ModelPart cuello;
    private final ModelPart cabeza;
    private final ModelPart pelo;
    private final ModelPart pico;
    private final ModelPart ala_izquierda;
    private final ModelPart mano_izquierda;
    private final ModelPart ala_derecha;
    private final ModelPart mano_derecha;

    public TeroModel(ModelPart root) {
        this.bone = root.getChild("bone");
        this.pata_izquierda = this.bone.getChild("pata_izquierda");
        this.pata_derecha = this.bone.getChild("pata_derecha");
        this.cuerpo = this.bone.getChild("cuerpo");
        this.cuello = this.cuerpo.getChild("cuello");
        this.cabeza = this.cuello.getChild("cabeza");
        this.pelo = this.cabeza.getChild("pelo");
        this.pico = this.cabeza.getChild("pico");
        this.ala_izquierda = this.cuerpo.getChild("ala_izquierda");
        this.mano_izquierda = this.ala_izquierda.getChild("mano_izquierda");
        this.ala_derecha = this.cuerpo.getChild("ala_derecha");
        this.mano_derecha = this.ala_derecha.getChild("mano_derecha");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 23.0F, 0.0F));

        PartDefinition pata_izquierda = bone.addOrReplaceChild("pata_izquierda", CubeListBuilder.create(), PartPose.offset(1.0F, -3.0F, 0.75F));

        PartDefinition cube_r1 = pata_izquierda.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 27).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, -1.35F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r2 = pata_izquierda.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 28).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, -0.1F, -0.1309F, 0.0F, 0.0F));

        PartDefinition cube_r3 = pata_izquierda.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 31).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition pata_derecha = bone.addOrReplaceChild("pata_derecha", CubeListBuilder.create(), PartPose.offset(-1.0F, -3.0F, 0.75F));

        PartDefinition cube_r4 = pata_derecha.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 27).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, -1.35F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r5 = pata_derecha.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 31).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r6 = pata_derecha.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 28).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, -0.1F, -0.1309F, 0.0F, 0.0F));

        PartDefinition cuerpo = bone.addOrReplaceChild("cuerpo", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 0.5F));

        PartDefinition cube_r7 = cuerpo.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(16, 27).addBox(-2.0F, -0.139F, -0.0787F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.75F, -0.6981F, 0.0F, 0.0F));

        PartDefinition cube_r8 = cuerpo.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(12, 19).addBox(-2.0F, -2.0F, -4.0F, 4.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -0.5F, -0.3927F, 0.0F, 0.0F));

        PartDefinition cuello = cuerpo.addOrReplaceChild("cuello", CubeListBuilder.create().texOffs(18, 13).addBox(-1.0F, -2.5F, -0.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -3.5F));

        PartDefinition cabeza = cuello.addOrReplaceChild("cabeza", CubeListBuilder.create(), PartPose.offset(0.0F, -2.25F, 0.5F));

        PartDefinition cube_r9 = cabeza.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(18, 9).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition pelo = cabeza.addOrReplaceChild("pelo", CubeListBuilder.create(), PartPose.offset(0.0F, -2.25F, 0.5F));

        PartDefinition cube_r10 = pelo.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(19, 7).addBox(-0.5F, 0.0439F, -0.0653F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition pico = cabeza.addOrReplaceChild("pico", CubeListBuilder.create().texOffs(20, 5).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -1.0F));

        PartDefinition ala_izquierda = cuerpo.addOrReplaceChild("ala_izquierda", CubeListBuilder.create(), PartPose.offset(2.0F, -2.0F, -3.75F));

        PartDefinition cube_r11 = ala_izquierda.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -0.8384F, -0.2717F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.5F, -0.3927F, 0.0F, 0.0F));

        PartDefinition mano_izquierda = ala_izquierda.addOrReplaceChild("mano_izquierda", CubeListBuilder.create(), PartPose.offset(0.0F, 0.75F, 3.75F));

        PartDefinition cube_r12 = mano_izquierda.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 7).addBox(0.0F, -0.8384F, -0.2717F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.25F, 0.5F, -0.3927F, 0.0F, 0.0F));

        PartDefinition ala_derecha = cuerpo.addOrReplaceChild("ala_derecha", CubeListBuilder.create(), PartPose.offset(-2.0F, -2.0F, -3.75F));

        PartDefinition cube_r13 = ala_derecha.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.0F, -0.8384F, -0.2717F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -1.0F, 0.5F, -0.3927F, 0.0F, 0.0F));

        PartDefinition mano_derecha = ala_derecha.addOrReplaceChild("mano_derecha", CubeListBuilder.create(), PartPose.offset(0.0F, 0.75F, 3.75F));

        PartDefinition cube_r14 = mano_derecha.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(0, 7).mirror().addBox(-1.0F, -0.8384F, -0.2717F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -0.25F, 0.5F, -0.3927F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(TeroEntity entity,
                          float limbSwing,
                          float limbSwingAmount,
                          float ageInTicks,
                          float netHeadYaw,
                          float headPitch) {

        // 1. Reiniciar todas las partes del modelo
        this.root().getAllParts().forEach(ModelPart::resetPose);

        // 2. Aplicar rotación de cabeza (igual que el youtuber, pero sin método auxiliar)
        this.applyHeadRotation(netHeadYaw, headPitch); // ← Lo definimos abajo

        // 3. Animación de caminar (si no está volando)
        if (!entity.isFlying()) {
            this.animateWalk(
                    CAMINAR,           // tu constante de walk
                    limbSwing,
                    limbSwingAmount,
                    2.0F,
                    2.5F
            );
        }

        // 4. Animación de idle (siempre que no esté haciendo otra cosa)
        this.animate(
                entity.idleAnimationState,
                IDLE,                // tu constante de idle
                ageInTicks,
                1.0F
        );

        // 5. Animación de vuelo (si está volando)
        if (entity.isFlying()) {
            this.animate(
                    entity.flyAnimationState,
                    VOLAR,            // tu constante de fly
                    ageInTicks,
                    1.0F
            );
        }

        // 6. Animación de picoteo (se activa cuando corresponda)
        this.animate(
                entity.peckAnimationState,
                PICOTEAR,            // tu constante de peck
                ageInTicks,
                1.0F
        );
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
