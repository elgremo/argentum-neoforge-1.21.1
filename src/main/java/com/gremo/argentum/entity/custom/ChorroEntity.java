package com.gremo.argentum.entity.custom;


import com.gremo.argentum.sound.ModSounds;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import com.gremo.argentum.item.ModItems;
import com.gremo.argentum.entity.ai.goal.ChorroShootGoal;

public class ChorroEntity extends Monster implements RangedAttackMob {

    private int idleAnimationTimeout = 0;


    public ChorroEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
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

        this.level().broadcastEntityEvent(this, (byte)10);

        BalaEntity bala = new BalaEntity(level(), this);
        bala.setDamage(2.0F);

        double dx = target.getX() - this.getX();
        double dy = target.getEyeY() - bala.getY();
        double dz = target.getZ() - this.getZ();

        bala.shoot(dx, dy, dz, 1.0F, 0.0F);

        level().addFreshEntity(bala);

        playSound(ModSounds.CHORRO_SHOOT.get(), 4.0F, 1.0F);
        playSound(ModSounds.CHUNGO_SHOOT.get(), 4.0F, 1.0F);
    }

    @Override
    public void handleEntityEvent(byte id) {
        super.handleEntityEvent(id);

        if (id == 10) {
            this.shootAnimationState.start(this.tickCount);
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
        this.goalSelector.addGoal(1, new ChorroShootGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {

        if (this.level().isClientSide()) {
            this.hurtAnimationState.start(this.tickCount);
        }

        return super.hurt(source, amount);
    }

    @Override
    public void die(DamageSource source) {

        if (this.level().isClientSide()) {
            this.deathAnimationState.start(this.tickCount);
        }

        super.die(source);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.20D)
                .add(Attributes.FOLLOW_RANGE, 42.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState shootAnimationState = new AnimationState();
    public final AnimationState hurtAnimationState = new AnimationState();
    public final AnimationState deathAnimationState = new AnimationState();

    private void setupAnimationStates() {
        if (!this.idleAnimationState.isStarted()) {
            this.idleAnimationState.start(this.tickCount);
        }
    }


}
