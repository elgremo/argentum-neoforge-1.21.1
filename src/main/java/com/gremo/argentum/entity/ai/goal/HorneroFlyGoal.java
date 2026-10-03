package com.gremo.argentum.entity.ai.goal;

import com.gremo.argentum.entity.custom.HorneroEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class HorneroFlyGoal extends Goal {
    private final HorneroEntity hornero;
    private final Level level;
    private final double speed = 0.35;
    private Vec3 targetPos;
    private int flyTime = 0;
    private int cooldown = 0;
    private int timeUntilNewTarget = 0;
    private double baseY;
    private static final int MAX_RADIUS = 20;

    public HorneroFlyGoal(HorneroEntity hornero) {
        this.hornero = hornero;
        this.level = hornero.level();
    }

    @Override
    public boolean canUse() {
        if (this.cooldown > 0) {
            this.cooldown--;
            return false;
        }
        if (!this.hornero.hasAnchor()) return false;

        double distToAnchor = this.hornero.distanceToSqr(
                this.hornero.getAnchorPos().getX() + 0.5,
                this.hornero.getAnchorPos().getY() + 0.5,
                this.hornero.getAnchorPos().getZ() + 0.5);
        if (distToAnchor > MAX_RADIUS * MAX_RADIUS) {
            return true;
        }

        return this.hornero.isFlying() ||
                (this.hornero.onGround() && this.hornero.getRandom().nextInt(40) == 0);
    }

    @Override
    public boolean canContinueToUse() {
        if (!this.hornero.isFlying()) return false;
        if (this.flyTime >= 300) return false;

        double distToAnchor = this.hornero.distanceToSqr(
                this.hornero.getAnchorPos().getX() + 0.5,
                this.hornero.getAnchorPos().getY() + 0.5,
                this.hornero.getAnchorPos().getZ() + 0.5);
        if (distToAnchor > MAX_RADIUS * MAX_RADIUS) {
            return true;
        }

        return this.flyTime < 300;
    }

    @Override
    public void start() {
        this.hornero.setFlying(true);
        this.flyTime = 0;
        this.cooldown = 100;
        this.baseY = this.hornero.getY();

        RandomSource random = this.hornero.getRandom();
        float yaw = random.nextFloat() * 360.0F;
        this.hornero.setYRot(yaw);
        this.hornero.setDeltaMovement(
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
            this.timeUntilNewTarget = 120 + this.hornero.getRandom().nextInt(80);
        }

        Vec3 currentPos = this.hornero.position();
        Vec3 targetVec = new Vec3(targetPos.x, targetPos.y, targetPos.z);
        Vec3 direction = targetVec.subtract(currentPos).normalize();

        double speedY = direction.y * 0.15 + 0.02;
        this.hornero.setDeltaMovement(
                direction.x * this.speed,
                speedY,
                direction.z * this.speed
        );

        if (direction.horizontalDistanceSqr() > 0.001) {
            float targetYaw = (float) (Mth.atan2(direction.x, direction.z) * Mth.RAD_TO_DEG);
            float currentYaw = this.hornero.getYRot();
            float delta = Mth.degreesDifference(currentYaw, targetYaw);
            if (Math.abs(delta) > 5.0F) {
                this.hornero.setYRot(currentYaw + Mth.clamp(delta, -15.0F, 15.0F));
            } else {
                this.hornero.setYRot(targetYaw);
            }
            this.hornero.yBodyRot = this.hornero.getYRot();
        }

        if (currentPos.distanceToSqr(targetVec) < 4.0) {
            this.targetPos = null;
            this.timeUntilNewTarget = 0;
        }

        if (this.flyTime >= 280) {
            this.hornero.setDeltaMovement(
                    this.hornero.getDeltaMovement().x * 0.9,
                    -0.06,
                    this.hornero.getDeltaMovement().z * 0.9
            );
        }
    }

    @Override
    public void stop() {
        this.hornero.setFlying(false);
        this.targetPos = null;
        this.cooldown = 100;
        this.flyTime = 0;
        this.timeUntilNewTarget = 0;
        this.baseY = 0;
    }

    private Vec3 getRandomTargetWithinRadius() {
        BlockPos anchor = this.hornero.getAnchorPos();
        if (anchor == null) return this.hornero.position();

        RandomSource random = this.hornero.getRandom();
        float angle = random.nextFloat() * 2.0F * (float) Math.PI;
        double distance = 3.0 + random.nextDouble() * (MAX_RADIUS - 6);
        double x = anchor.getX() + 0.5 + Math.cos(angle) * distance;
        double z = anchor.getZ() + 0.5 + Math.sin(angle) * distance;
        double y = anchor.getY() + 4.0 + random.nextDouble() * 8.0;

        return new Vec3(x, y, z);
    }
}