package com.gremo.argentum.entity.custom;

import com.gremo.argentum.entity.ModEntities;
import com.gremo.argentum.item.ModItems;
import com.gremo.argentum.sound.ModSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ZorroGrisEntity extends TamableAnimal {

    // Datos sincronizados
    private static final EntityDataAccessor<Boolean> SITTING =
            SynchedEntityData.defineId(ZorroGrisEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> COLLAR_COLOR =
            SynchedEntityData.defineId(ZorroGrisEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> ATTACKING =
            SynchedEntityData.defineId(ZorroGrisEntity.class, EntityDataSerializers.BOOLEAN);

    // Estados de animación
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState sitAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState swimAnimationState = new AnimationState();

    private int idleAnimationTimeout = 0;
    private int sitAnimationTimeout = 0;
    private int attackAnimationTimeout = 0;

    public ZorroGrisEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SITTING, false);
        builder.define(ATTACKING, false);
        builder.define(COLLAR_COLOR, 0xFF0000);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new PanicGoal(this, 1.5D));
        this.goalSelector.addGoal(3, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F));
        this.goalSelector.addGoal(7, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));

        // ===== TARGETS =====

        // 0. Goal personalizado: atacar a lo que el dueño ataca (prioridad máxima)
        this.targetSelector.addGoal(0, new Goal() {
            private LivingEntity target;

            @Override
            public boolean canUse() {
                if (!ZorroGrisEntity.this.isTame()) return false;
                if (ZorroGrisEntity.this.isSitting()) return false;

                Player owner = (Player) ZorroGrisEntity.this.getOwner();
                if (owner == null) return false;

                LivingEntity lastHurt = owner.getLastHurtMob();
                if (lastHurt == null) return false;
                if (!lastHurt.isAlive()) return false;
                if (lastHurt == owner) return false;

                // No atacar a otros zorros, animales pasivos o jugadores si está domesticado
                if (lastHurt instanceof ZorroGrisEntity) return false;
                if (lastHurt instanceof Animal && !(lastHurt instanceof Enemy)) return false;
                if (lastHurt instanceof Player) return false;

                this.target = lastHurt;
                return true;
            }

            @Override
            public boolean canContinueToUse() {
                return this.target != null && this.target.isAlive() &&
                        ZorroGrisEntity.this.isTame() && !ZorroGrisEntity.this.isSitting();
            }

            @Override
            public void start() {
                ZorroGrisEntity.this.setTarget(this.target);
            }

            @Override
            public void stop() {
                if (ZorroGrisEntity.this.getTarget() == this.target) {
                    ZorroGrisEntity.this.setTarget(null);
                }
                this.target = null;
            }
        });

        // 1. Defensa del dueño (no ataca a zorros)
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this) {
            @Override
            public boolean canUse() {
                LivingEntity target = this.mob.getTarget();
                if (target instanceof ZorroGrisEntity) {
                    return false;
                }
                return super.canUse();
            }
        });

        // 2. Atacar a lo que el dueño ataca (no ataca a zorros)
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this) {
            @Override
            public boolean canUse() {
                LivingEntity target = this.mob.getTarget();
                if (target instanceof ZorroGrisEntity) {
                    return false;
                }
                return super.canUse();
            }
        });

        // 3. Atacar a quien lo lastimó (excluir zorros)
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this) {
            @Override
            public boolean canUse() {
                LivingEntity target = this.mob.getTarget();
                if (target instanceof ZorroGrisEntity) {
                    return false;
                }
                return super.canUse();
            }
        }.setAlertOthers());

        // 4. Atacar a jugadores si está enojado (excluir zorros)
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, (player) -> {
            return this.isAngryAt(player) && !(player instanceof ZorroGrisEntity);
        }));

        // 5. Atacar a animales pasivos SOLO si NO está domesticado (excluir zorros)
        this.targetSelector.addGoal(5, new NonTameRandomTargetGoal<>(this, Animal.class, false, (living) -> {
            return !this.isTame() && !this.isSitting() && !(living instanceof ZorroGrisEntity);
        }));
    }

    // ════════════════════════════════════════════════════════════════
    //  MÉTODOS DEL LOBO (copiados exactamente)
    // ════════════════════════════════════════════════════════════════

    public boolean isAngryAt(LivingEntity entity) {
        if (this.isTame()) return false;
        if (entity instanceof Player) {
            return this.getLastHurtByMob() == entity || this.getTarget() == entity;
        }
        return false;
    }

    public boolean wantsToAttack(LivingEntity target, LivingEntity owner) {
        if (target instanceof Creeper || target instanceof Ghast || target instanceof ArmorStand) {
            return false;
        }
        // No atacar a otros lobos/zorros domesticados o mascotas
        if (target instanceof Wolf wolf && wolf.isTame() && wolf.getOwner() == owner) {
            return false;
        }
        if (target instanceof TamableAnimal tamable && tamable.isTame()) {
            return false;
        }
        if (target instanceof Player player && owner instanceof Player ownerPlayer && !ownerPlayer.canHarmPlayer(player)) {
            return false;
        }
        // No atacar a animales pasivos si está domesticado
        if (this.isTame() && target instanceof Animal && !(target instanceof Enemy)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            if (!this.level().isClientSide) {
                this.setOrderedToSit(false);
            }
            return super.hurt(source, amount);
        }
    }

    @Override
    public boolean canMate(Animal otherAnimal) {
        if (otherAnimal == this) {
            return false;
        } else if (!this.isTame()) {
            return false;
        } else if (!(otherAnimal instanceof ZorroGrisEntity zorro)) {
            return false;
        } else if (!zorro.isTame()) {
            return false;
        } else {
            return zorro.isInSittingPose() ? false : this.isInLove() && zorro.isInLove();
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 4.0D)
                .add(Attributes.FOLLOW_RANGE, 30.0D);
    }

    // ════════════════════════════════════════════════════════════════
    //  SENTADO
    // ════════════════════════════════════════════════════════════════
    public boolean isSitting() {
        return this.entityData.get(SITTING);
    }

    public void setSitting(boolean sitting) {
        this.entityData.set(SITTING, sitting);
        this.setOrderedToSit(sitting);
    }

    @Override
    public boolean isInSittingPose() {
        return this.isSitting();
    }

    @Override
    public void setInSittingPose(boolean sitting) {
        this.setSitting(sitting);
    }

    // ════════════════════════════════════════════════════════════════
    //  INTERACCIÓN
    // ════════════════════════════════════════════════════════════════
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (isRawMeat(stack)) {
            if (!this.level().isClientSide) {
                if (!this.isTame()) {
                    if (this.random.nextInt(3) == 0) {
                        this.tame(player);
                        this.level().broadcastEntityEvent(this, (byte) 7);
                    } else {
                        this.level().broadcastEntityEvent(this, (byte) 6);
                    }
                } else {
                    this.heal(5.0F);
                    this.level().broadcastEntityEvent(this, (byte) 7);
                }
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.CONSUME;
        }

        if (this.isTame() && stack.getItem() instanceof DyeItem dye) {
            int color = dye.getDyeColor().getFireworkColor();
            if (color != this.getCollarColor()) {
                this.setCollarColor(color);
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
        }

        if (this.isTame() && hand == InteractionHand.MAIN_HAND && stack.isEmpty()) {
            this.setSitting(!this.isSitting());
            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }

    // ════════════════════════════════════════════════════════════════
    //  REPRODUCCIÓN
    // ════════════════════════════════════════════════════════════════
    @Override
    public boolean isFood(ItemStack stack) {
        return isCookedMeat(stack);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return ModEntities.ZORRO_GRIS.get().create(level);
    }

    // ════════════════════════════════════════════════════════════════
    //  ATAQUE
    // ════════════════════════════════════════════════════════════════
    @Override
    public boolean doHurtTarget(Entity target) {
        this.setAttacking(true);
        this.attackAnimationTimeout = 20;
        this.playSound(this.getAttackSound(), 1.0F, 1.0F);

        if (this.isTame()) {
            if (target instanceof Player) return false;
            if (target instanceof Animal && !(target instanceof Enemy)) return false;
            if (!(target instanceof Enemy)) return false;
        }

        return super.doHurtTarget(target);
    }

    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }

    public void setAttacking(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
        if (!attacking) {
            this.attackAnimationState.stop();
        }
    }

    // ════════════════════════════════════════════════════════════════
    //  ANIMACIONES
    // ════════════════════════════════════════════════════════════════
    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

        if (this.isAttacking() && !this.attackAnimationState.isStarted()) {
            this.attackAnimationState.start(this.tickCount);
        } else if (!this.isAttacking() && this.attackAnimationState.isStarted()) {
            this.attackAnimationState.stop();
        }

        if (this.isSitting()) {
            if (this.sitAnimationTimeout <= 0) {
                this.sitAnimationTimeout = 40;
                this.sitAnimationState.start(this.tickCount);
            } else {
                --this.sitAnimationTimeout;
            }
        } else {
            this.sitAnimationState.stop();
            this.sitAnimationTimeout = 0;
        }

        if (this.isInWater()) {
            if (!this.swimAnimationState.isStarted()) {
                this.swimAnimationState.start(this.tickCount);
            }
        } else {
            this.swimAnimationState.stop();
        }
    }

    // ════════════════════════════════════════════════════════════════
    //  TICK
    // ════════════════════════════════════════════════════════════════
    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            this.setupAnimationStates();
        } else {
            if (this.attackAnimationTimeout > 0) {
                this.attackAnimationTimeout--;
                if (this.attackAnimationTimeout == 0) {
                    this.setAttacking(false);
                }
            }
        }
    }

    // ════════════════════════════════════════════════════════════════
    //  SONIDOS
    // ════════════════════════════════════════════════════════════════
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.IDLE_ZORRO.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.HURT_ZORRO.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.DEATH_ZORRO.get();
    }

    protected SoundEvent getAttackSound() {
        return ModSounds.ATAQUE_ZORRO.get();
    }

    // ════════════════════════════════════════════════════════════════
    //  NBT
    // ════════════════════════════════════════════════════════════════
    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Sitting", this.isSitting());
        compound.putInt("CollarColor", this.getCollarColor());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setSitting(compound.getBoolean("Sitting"));
        if (compound.contains("CollarColor")) {
            this.setCollarColor(compound.getInt("CollarColor"));
        }
    }

    // ════════════════════════════════════════════════════════════════
    //  CARNES
    // ════════════════════════════════════════════════════════════════
    private boolean isRawMeat(ItemStack stack) {
        return stack.is(ModItems.BIFE_CRUDO.get()) ||
                stack.is(ModItems.CHINCHULIN_CRUDO.get()) ||
                stack.is(ModItems.COSTILLA_CRUDA.get()) ||
                stack.is(ModItems.ENTRANA_CRUDA.get()) ||
                stack.is(ModItems.LOMO_CRUDO.get()) ||
                stack.is(ModItems.MATAMBRE_CRUDO.get()) ||
                stack.is(ModItems.MOLLEJA_CRUDA.get()) ||
                stack.is(ModItems.CHORIZO_PARRILLERO_CRUDO.get());
    }

    private boolean isCookedMeat(ItemStack stack) {
        return stack.is(ModItems.BIFE_ASADO.get()) ||
                stack.is(ModItems.CHINCHULIN_ASADO.get()) ||
                stack.is(ModItems.COSTILLA_ASADA.get()) ||
                stack.is(ModItems.ENTRANA_ASADA.get()) ||
                stack.is(ModItems.LOMO_ASADO.get()) ||
                stack.is(ModItems.MATAMBRE_ASADO.get()) ||
                stack.is(ModItems.MOLLEJA_ASADA.get()) ||
                stack.is(ModItems.CHORIZO_PARRILLERO_COCIDO.get());
    }

    // ════════════════════════════════════════════════════════════════
    //  COLLAR
    // ════════════════════════════════════════════════════════════════
    public int getCollarColor() {
        return this.entityData.get(COLLAR_COLOR);
    }

    public void setCollarColor(int color) {
        this.entityData.set(COLLAR_COLOR, color);
    }

    @Override
    public void tame(Player player) {
        super.tame(player);
        int[] colors = {0xFF0000, 0x00FF00, 0x0000FF, 0xFFFF00, 0xFF00FF, 0x00FFFF, 0xFF8C00, 0x8B4513, 0x808080};
        this.setCollarColor(colors[this.random.nextInt(colors.length)]);
    }
}