package com.gremo.argentum.entity.custom;

import com.gremo.argentum.block.ModBlocks;
import com.gremo.argentum.entity.ModEntities;
import com.gremo.argentum.entity.ai.goal.HorneroFlyGoal;
import com.gremo.argentum.item.ModItems;
import com.gremo.argentum.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class HorneroEntity extends Animal {

    private static final EntityDataAccessor<Boolean> FLYING =
            SynchedEntityData.defineId(HorneroEntity.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState flyAnimationState = new AnimationState();

    private int idleAnimationTimeout = 0;
    private int flyAnimationTimeout = 0;

    private BlockPos anchorPos;
    private boolean anchorSet = false;

    // --- SISTEMA DE HUEVOS (como pollo) ---
    private int eggLayTimer = 0;
    private static final int MIN_EGG_LAY_TIME = 6000;
    private static final int MAX_EGG_LAY_TIME = 12000;

    public HorneroEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(FLYING, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25,
                Ingredient.of(Items.WHEAT_SEEDS), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new HorneroFlyGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FOLLOW_RANGE, 16.0D);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.WHEAT_SEEDS);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return ModEntities.HORNERO.get().create(level);
    }

    // --- ANCLA ---
    public BlockPos getAnchorPos() {
        return this.anchorPos;
    }

    public void setAnchorPos(BlockPos pos) {
        this.anchorPos = pos;
        this.anchorSet = true;
    }

    public boolean hasAnchor() {
        return this.anchorPos != null;
    }

    // --- FLYING ---
    public boolean isFlying() {
        return this.entityData.get(FLYING);
    }

    public void setFlying(boolean flying) {
        this.entityData.set(FLYING, flying);
        this.setNoGravity(flying);
        if (!flying && this.getDeltaMovement().y < 0) {
            this.setDeltaMovement(this.getDeltaMovement().x, -0.05, this.getDeltaMovement().z);
        }
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    // --- INTERACCIÓN CON NIDO ---
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.is(ModBlocks.NIDO.get().asItem()) && !this.isFlying()) {
            if (!this.level().isClientSide) {
                BlockPos pos = this.blockPosition();
                BlockPos placePos = pos.below();
                if (!this.level().getBlockState(placePos).isAir()) {
                    placePos = pos;
                }
                if (this.level().getBlockState(placePos).isAir()) {
                    this.level().setBlockAndUpdate(placePos, ModBlocks.NIDO.get().defaultBlockState());
                }
                this.setAnchorPos(placePos);
                player.displayClientMessage(
                        net.minecraft.network.chat.Component.literal("✨ El hornero ha marcado su nuevo hogar ✨"),
                        true
                );
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    // --- ANIMACIONES ---
    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

        boolean isInAir = this.isFlying() || !this.onGround();
        if (isInAir) {
            if (this.flyAnimationTimeout <= 0) {
                this.flyAnimationTimeout = 40;
                this.flyAnimationState.start(this.tickCount);
            } else {
                --this.flyAnimationTimeout;
            }
        } else {
            this.flyAnimationState.stop();
            this.flyAnimationTimeout = 0;
        }
    }

    // --- SISTEMA DE HUEVOS (como pollo) ---
    private void tryLayEgg() {
        if (!this.level().isClientSide && this.isAlive() && this.onGround() && !this.isBaby()) {
            if (this.eggLayTimer <= 0) {
                this.spawnAtLocation(ModItems.HUEVO_HORNERO.get(), 1);
                this.eggLayTimer = MIN_EGG_LAY_TIME + this.random.nextInt(MAX_EGG_LAY_TIME - MIN_EGG_LAY_TIME + 1);
            } else {
                this.eggLayTimer--;
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide && !this.anchorSet) {
            this.setAnchorPos(this.blockPosition());
        }
        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        } else {
            this.playRandomSound();
            if (!this.isFlying() && !this.onGround() && this.getDeltaMovement().y < -0.15) {
                this.setDeltaMovement(this.getDeltaMovement().x, -0.15, this.getDeltaMovement().z);
            }
            // Intentar poner huevo
            this.tryLayEgg();
        }
    }

    // --- SONIDOS ---
    private void playRandomSound() {
        if (this.random.nextInt(100) == 0) {
            this.playSound(ModSounds.IDLE_HORNERO.get(), 0.6F, 1.3F);
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return super.getHurtSound(damageSource);
    }

    @Override
    protected SoundEvent getDeathSound() {
        return super.getDeathSound();
    }

    // --- NBT ---
    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Flying", this.isFlying());
        compound.putBoolean("AnchorSet", this.anchorSet);
        compound.putInt("EggLayTimer", this.eggLayTimer);
        if (this.anchorPos != null) {
            compound.putInt("AnchorX", this.anchorPos.getX());
            compound.putInt("AnchorY", this.anchorPos.getY());
            compound.putInt("AnchorZ", this.anchorPos.getZ());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setFlying(compound.getBoolean("Flying"));
        this.anchorSet = compound.getBoolean("AnchorSet");
        this.eggLayTimer = compound.getInt("EggLayTimer");
        if (compound.contains("AnchorX")) {
            int x = compound.getInt("AnchorX");
            int y = compound.getInt("AnchorY");
            int z = compound.getInt("AnchorZ");
            this.anchorPos = new BlockPos(x, y, z);
        }
    }
}