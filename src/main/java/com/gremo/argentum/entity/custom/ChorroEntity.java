package com.gremo.argentum.entity.custom;


import com.gremo.argentum.sound.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import com.gremo.argentum.item.ModItems;
import com.gremo.argentum.entity.custom.BalaEntity;
import com.gremo.argentum.entity.ai.goal.ChorroShootGoal;

public class ChorroEntity extends Monster implements RangedAttackMob {

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;


    public ChorroEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);

        this.setItemSlot(
                EquipmentSlot.MAINHAND,
                new ItemStack(ModItems.CHUNGO.get())
        );

        this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.CHORRO_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.CHORRO_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.CHORRO_DEATH.get();
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        System.out.println("DISPARANDO");
        BalaEntity bala = new BalaEntity(level(), this);

        bala.setDamage(2.0F);

        double dx = target.getX() - this.getX();
        double dy = target.getEyeY() - bala.getY();
        double dz = target.getZ() - this.getZ();

        bala.shoot(dx, dy, dz, 1.0F, 0.0F);

        level().addFreshEntity(bala);

        playSound(ModSounds.CHORRO_SHOOT.get(), 1.0F, 1.0F);
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 40;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));

        // Ataque a distancia
        this.goalSelector.addGoal(
                1,
                new ChorroShootGoal(this)
        );
        this.playSound(ModSounds.CHORRO_DETECT.get(), 1.0F, 1.0F);
        // Caminar hasta el jugador
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, false));
        this.playSound(ModSounds.CHORRO_SHOOT.get(), 1.0F, 1.0F);
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.ATTACK_DAMAGE, 4.0D);
    }
}
