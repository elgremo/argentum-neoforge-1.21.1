package com.gremo.argentum.entity.ai.goal;

import com.gremo.argentum.entity.custom.ChorroEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class ChorroShootGoal extends Goal {

    private final ChorroEntity chorro;
    private int attackCooldown;

    public ChorroShootGoal(ChorroEntity chorro) {
        this.chorro = chorro;
        this.setFlags(EnumSet.of(Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = chorro.getTarget();
        return target != null && target.isAlive();
    }

    @Override
    public void tick() {

        LivingEntity target = chorro.getTarget();

        if (target == null) {
            return;
        }

        chorro.getLookControl().setLookAt(target, 30.0F, 30.0F);

        double distance = chorro.distanceToSqr(target);

        // Más de 10 bloques -> acercarse
        if (distance > 100.0D) {
            chorro.getNavigation().moveTo(target, 1.2D);
        }
        // Menos de 5 bloques -> retroceder
        else if (distance < 25.0D) {

            double dx = chorro.getX() - target.getX();
            double dz = chorro.getZ() - target.getZ();

            double length = Math.sqrt(dx * dx + dz * dz);

            if (length > 0.0D) {
                double x = chorro.getX() + dx / length * 4.0D;
                double z = chorro.getZ() + dz / length * 4.0D;

                chorro.getNavigation().moveTo(x, chorro.getY(), z, 1.2D);
            }
        }
        // Distancia ideal -> quedarse quieto
        else {
            chorro.getNavigation().stop();
        }

        if (--attackCooldown <= 0) {
            attackCooldown = 20;
            chorro.performRangedAttack(target, 1.0F);
        }
    }
}