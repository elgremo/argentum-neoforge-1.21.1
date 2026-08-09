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

        if (--attackCooldown <= 0) {

            attackCooldown = 20;

            chorro.performRangedAttack(target, 1.0F);
        }
    }
}