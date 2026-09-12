package com.gremo.argentum.entity.custom;

import com.gremo.argentum.entity.ModEntities;
import com.gremo.argentum.item.ModItems;
import com.gremo.argentum.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PelotaBasquetEntity extends Entity {

    private static final EntityDataAccessor<ItemStack> DISPLAY =
            SynchedEntityData.defineId(PelotaBasquetEntity.class, EntityDataSerializers.ITEM_STACK);

    // Parámetros de física
    private static final double GRAVITY = 0.08;
    private static final double AIR_FRICTION = 0.98;
    private static final double GROUND_FRICTION = 0.96;
    private static final double ROLL_DECEL = 0.006;
    private static final double STOP_THRESHOLD = 0.0005;
    private static final double MAX_SPEED = 6.0;

    // Rebote: fuerza vertical al tocar el suelo (valor fijo, no depende de la velocidad)
    private static final double BOUNCE_FORCE = 0.6;

    // Variable para saber si en el tick anterior estábamos en el suelo
    private boolean wasOnGround = false;

    public PelotaBasquetEntity(EntityType<?> type, Level world) {
        super(type, world);
        this.noPhysics = false;
        this.setNoGravity(false);
        this.refreshDimensions();
    }

    public PelotaBasquetEntity(Level world, double x, double y, double z) {
        this(ModEntities.PELOTA_BASQUET.get(), world);
        this.setPos(x, y, z);
    }

    // Interacciones: click derecho → recoger
    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if (!this.level().isClientSide) {
            ItemStack give = new ItemStack(ModItems.PELOTA_BASQUET.get());
            ItemStack display = getDisplay();
            if (!display.isEmpty()) give = display.copy();
            boolean added = player.getInventory().add(give);
            if (!added) player.drop(give, false);
            this.remove(RemovalReason.DISCARDED);
        }
        return InteractionResult.sidedSuccess(this.level().isClientSide);
    }

    // Detección de suelo mejorada
    private boolean touchingGround() {
        double checkY = this.getBoundingBox().minY - 0.05;
        BlockPos posBelow = BlockPos.containing(this.getX(), checkY, this.getZ());
        BlockState state = this.level().getBlockState(posBelow);
        VoxelShape shape = state.getCollisionShape(this.level(), posBelow);
        return !shape.isEmpty();
    }

    // Click izquierdo → pase corto
    @Override
    public boolean hurt(DamageSource source, float amount) {
        Entity attacker = source.getEntity();
        if (attacker instanceof Player player) {
            Vec3 dir = player.getLookAngle().normalize();
            double force = 0.9;
            this.setDeltaMovement(dir.x * force, 0.2, dir.z * force);
            return true;
        }
        return super.hurt(source, amount);
    }

    // Física principal
    @Override
    public void tick() {
        super.tick();

        // Gravedad
        Vec3 velocity = this.getDeltaMovement();
        if (!this.isNoGravity()) {
            velocity = velocity.add(0, -GRAVITY, 0);
        }
        this.setDeltaMovement(velocity);

        // Movimiento
        this.move(MoverType.SELF, this.getDeltaMovement());

        Vec3 postVel = this.getDeltaMovement();

        // Detectar si está en el suelo en este tick
        boolean onGroundNow = this.touchingGround();

        // --- REBOTE Y SONIDO ---
        // Si ACABA de tocar el suelo (antes no estaba) y la velocidad es hacia abajo
        if (!wasOnGround && onGroundNow && postVel.y <= 0) {
            // Forzar rebote vertical
            postVel = new Vec3(postVel.x, BOUNCE_FORCE, postVel.z);

            // Reproducir sonido de rebote
            if (!this.level().isClientSide) {
                this.level().playSound(
                        null, // jugador que oye (null = todos en el área)
                        this.blockPosition(), // posición
                        ModSounds.BASQUET.get(), // sonido (puedes cambiarlo)
                        SoundSource.BLOCKS, // categoría
                        0.8f, // volumen (0.0 - 1.0)
                        1.2f + this.random.nextFloat() * 0.4f // tono (variación)
                );
            }
        }

        // Guardar el estado para el próximo tick
        wasOnGround = onGroundNow;

        // Si está en el suelo, aplicar fricción lateral y rodado
        if (onGroundNow) {
            // Fricción lateral
            postVel = new Vec3(postVel.x * GROUND_FRICTION, postVel.y, postVel.z * GROUND_FRICTION);

            // Freno de rodado
            double horizontal = Math.hypot(postVel.x, postVel.z);
            if (horizontal > 1e-9) {
                double nx = postVel.x / horizontal;
                double nz = postVel.z / horizontal;
                double newHx = postVel.x - nx * ROLL_DECEL;
                double newHz = postVel.z - nz * ROLL_DECEL;
                if (Math.signum(newHx) != Math.signum(postVel.x)) newHx = 0;
                if (Math.signum(newHz) != Math.signum(postVel.z)) newHz = 0;
                postVel = new Vec3(newHx, postVel.y, newHz);
            }

            if (postVel.horizontalDistanceSqr() < STOP_THRESHOLD) {
                postVel = new Vec3(0.0, postVel.y, 0.0);
            }

            // Si la pelota se hunde en el suelo, la subimos ligeramente
            if (this.getY() < this.getBlockY() - 0.1) {
                this.setPos(this.getX(), this.getBlockY() + 0.01, this.getZ());
            }

        } else {
            // En el aire → fricción aérea
            postVel = new Vec3(postVel.x * AIR_FRICTION, postVel.y, postVel.z * AIR_FRICTION);
        }

        // Limitar velocidad máxima
        double speed = postVel.length();
        if (speed > MAX_SPEED) {
            postVel = postVel.scale(MAX_SPEED / speed);
        }

        this.setDeltaMovement(postVel);

        // Eliminar si se cae
        if (this.getY() < -64) {
            this.remove(RemovalReason.DISCARDED);
        }
    }

    // Impedir que los jugadores empujen la pelota
    @Override
    public void push(Entity entity) {
        // No hace nada
    }

    public void setDisplay(ItemStack stack) {
        this.entityData.set(DISPLAY, stack == null ? ItemStack.EMPTY : stack.copy());
    }

    public ItemStack getDisplay() {
        return this.entityData.get(DISPLAY);
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return EntityDimensions.scalable(1.0f, 1.0f);
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DISPLAY, ItemStack.EMPTY);
    }
}