package com.gremo.argentum.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class JacarandaParticles extends TextureSheetParticle {

    private float rotSpeed; // ❌ ¡Ya NO es final!
    private final float spinAcceleration; // Esta SÍ puede ser final

    protected JacarandaParticles(ClientLevel level, double x, double y, double z, SpriteSet spriteSet) {
        super(level, x, y, z);
        this.setSprite(spriteSet.get(this.random.nextInt(12), 12));
        this.rotSpeed = (float) Math.toRadians(this.random.nextBoolean() ? -30.0 : 30.0);
        this.spinAcceleration = (float) Math.toRadians(this.random.nextBoolean() ? -5.0 : 5.0);
        this.lifetime = 200;
        this.gravity = 0.001F;
        float f = 0.04F + this.random.nextFloat() * 0.03F; // Tamaño aleatorio
        this.quadSize = f;
        this.setSize(f, f);
        this.friction = 0.98F;
        // Velocidad inicial ligeramente aleatoria (solo en Y)
        this.yd = -0.02 - this.random.nextFloat() * 0.03;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.zo;

        if (this.lifetime-- <= 0) {
            this.remove();
            return;
        }

        if (!this.removed) {
            // Solo gravedad (sin movimiento lateral)
            this.yd -= this.gravity;

            // Rotación (mejor que antes, ahora sí se modifica)
            this.rotSpeed += this.spinAcceleration / 20.0F;
            this.oRoll = this.roll;
            this.roll += this.rotSpeed / 20.0F;

            // Movimiento
            this.move(this.xd, this.yd, this.zd);

            // Eliminar al tocar el suelo
            if (this.onGround) {
                this.remove();
                return;
            }

            // Fricción (solo en X y Z para evitar deriva lateral, Y no se frena)
            this.xd *= this.friction;
            this.zd *= this.friction;
            // Y no se frena para que caiga naturalmente
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                       double x, double y, double z,
                                       double xSpeed, double ySpeed, double zSpeed) {
            return new JacarandaParticles(level, x, y, z, this.spriteSet);
        }
    }
}