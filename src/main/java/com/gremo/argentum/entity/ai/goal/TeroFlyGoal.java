package com.gremo.argentum.entity.ai.goal;

import com.gremo.argentum.entity.custom.TeroEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class TeroFlyGoal extends Goal {
    private final TeroEntity tero;
    private final Level level;
    private final double speed = 0.35;
    private Vec3 targetPos;
    private int flyTime = 0;
    private int cooldown = 0;
    private int timeUntilNewTarget = 0;
    private double baseY;

    // Radio máximo de vuelo alrededor del ancla (en bloques)
    private static final int MAX_RADIUS = 20;

    public TeroFlyGoal(TeroEntity tero) {
        this.tero = tero;
        this.level = tero.level();
    }

    @Override
    public boolean canUse() {
        if (this.cooldown > 0) {
            this.cooldown--;
            return false;
        }
        // Solo puede volar si tiene un ancla definida
        if (!this.tero.hasAnchor()) return false;

        // Si está demasiado lejos del ancla, volar para regresar
        double distToAnchor = this.tero.distanceToSqr(
                this.tero.getAnchorPos().getX() + 0.5,
                this.tero.getAnchorPos().getY() + 0.5,
                this.tero.getAnchorPos().getZ() + 0.5);
        if (distToAnchor > MAX_RADIUS * MAX_RADIUS) {
            return true; // forzar vuelo de regreso
        }

        return this.tero.isFlying() ||
                (this.tero.onGround() && this.tero.getRandom().nextInt(40) == 0);
    }

    @Override
    public boolean canContinueToUse() {
        if (!this.tero.isFlying()) return false;
        if (this.flyTime >= 300) return false; // 15 segundos máx.

        // Si está fuera del radio, continúa hasta estar dentro
        double distToAnchor = this.tero.distanceToSqr(
                this.tero.getAnchorPos().getX() + 0.5,
                this.tero.getAnchorPos().getY() + 0.5,
                this.tero.getAnchorPos().getZ() + 0.5);
        if (distToAnchor > MAX_RADIUS * MAX_RADIUS) {
            return true;
        }

        return this.flyTime < 300;
    }

    @Override
    public void start() {
        this.tero.setFlying(true);
        this.flyTime = 0;
        this.cooldown = 100;
        this.baseY = this.tero.getY();

        RandomSource random = this.tero.getRandom();
        float yaw = random.nextFloat() * 360.0F;
        this.tero.setYRot(yaw);
        this.tero.setDeltaMovement(
                Mth.sin(yaw * Mth.DEG_TO_RAD) * 0.5,
                0.6,
                Mth.cos(yaw * Mth.DEG_TO_RAD) * 0.5
        );

        this.targetPos = this.getRandomTargetWithinRadius();
        this.timeUntilNewTarget = 0;
    }

    @Override
    public void tick() {
        this.flyTime++;
        this.timeUntilNewTarget--;

        if (this.timeUntilNewTarget <= 0 || this.targetPos == null) {
            this.targetPos = this.getRandomTargetWithinRadius();
            this.timeUntilNewTarget = 120 + this.tero.getRandom().nextInt(80);
        }

        Vec3 currentPos = this.tero.position();
        Vec3 targetVec = new Vec3(targetPos.x, targetPos.y, targetPos.z);
        Vec3 direction = targetVec.subtract(currentPos).normalize();

        double speedY = direction.y * 0.15 + 0.02;
        this.tero.setDeltaMovement(
                direction.x * this.speed,
                speedY,
                direction.z * this.speed
        );

        // Mirar hacia la dirección de movimiento
        if (direction.horizontalDistanceSqr() > 0.001) {
            float targetYaw = (float) (Mth.atan2(direction.x, direction.z) * Mth.RAD_TO_DEG);
            float currentYaw = this.tero.getYRot();
            float delta = Mth.degreesDifference(currentYaw, targetYaw);
            if (Math.abs(delta) > 5.0F) {
                this.tero.setYRot(currentYaw + Mth.clamp(delta, -15.0F, 15.0F));
            } else {
                this.tero.setYRot(targetYaw);
            }
            this.tero.yBodyRot = this.tero.getYRot();
        }

        if (currentPos.distanceToSqr(targetVec) < 4.0) {
            this.targetPos = null;
            this.timeUntilNewTarget = 0;
        }

        // Últimos 2 segundos: descender suavemente
        if (this.flyTime >= 280) {
            this.tero.setDeltaMovement(
                    this.tero.getDeltaMovement().x * 0.9,
                    -0.06,
                    this.tero.getDeltaMovement().z * 0.9
            );
        }
    }

    @Override
    public void stop() {
        this.tero.setFlying(false);
        this.targetPos = null;
        this.cooldown = 100;
        this.flyTime = 0;
        this.timeUntilNewTarget = 0;
        this.baseY = 0;
    }

    private Vec3 getRandomTargetWithinRadius() {
        BlockPos anchor = this.tero.getAnchorPos();
        if (anchor == null) return this.tero.position();

        RandomSource random = this.tero.getRandom();
        float angle = random.nextFloat() * 2.0F * (float) Math.PI;
        double distance = 3.0 + random.nextDouble() * (MAX_RADIUS - 6);
        double x = anchor.getX() + 0.5 + Math.cos(angle) * distance;
        double z = anchor.getZ() + 0.5 + Math.sin(angle) * distance;
        double y = anchor.getY() + 4.0 + random.nextDouble() * 8.0;

        return new Vec3(x, y, z);
    }
}