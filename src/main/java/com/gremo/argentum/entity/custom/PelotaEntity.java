package com.gremo.argentum.entity.custom;

import com.gremo.argentum.entity.ModEntities;
import com.gremo.argentum.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.phys.AABB;
import net.minecraft.network.syncher.SynchedEntityData;

public class PelotaEntity extends Entity {
    private ItemStack display = ItemStack.EMPTY;

    // Física / Ajustes - puedes cambiar estos valores para ajustar comportamiento
    private static final double GRAVITY = 0.06;      // gravedad aplicada cada tick
    private static final double RESTITUTION = 0.55;   // rebote vertical al chocar con el suelo
    private static final double AIR_FRICTION = 0.995; // fricción en aire (cerca de 1 -> muy poco)
    private static final double GROUND_FRICTION = 0.98; // fricción sobre suelo (0.92 = rueda bastante)
    private static final double ROLL_DECEL = 0.005;    // freno adicional por rodado
    private static final double STOP_THRESHOLD = 0.0001; // umbral para detenerse (horizontal^2)
    private static final double MAX_SPEED = 5.0;      // velocidad máxima permitida

    public PelotaEntity(EntityType<?> type, Level world) {
        super(type, world);
        this.noPhysics = false;
        this.setNoGravity(false);
        // Forzamos refrescar dimensiones para que use .sized(...) del EntityType
        this.refreshDimensions();
    }

    // Constructor conveniencia para spawnear: usa ModEntities.PELOTA.get()
    public PelotaEntity(Level world, double x, double y, double z) {
        this(ModEntities.PELOTA.get(), world);
        this.setPos(x, y, z);
    }

    // Getter / Setter para el ItemStack mostrado (usado por el renderer)
    public void setDisplay(ItemStack stack) {
        this.display = (stack == null) ? ItemStack.EMPTY : stack.copy();
    }

    public ItemStack getDisplay() {
        return (this.display == null) ? ItemStack.EMPTY : this.display;
    }

    @Override
    public void tick() {
        super.tick();

        // 1) Aplicar gravedad directamente sobre la delta actual y guardarla
        Vec3 current = this.getDeltaMovement();
        if (!this.isNoGravity()) {
            current = current.add(0, -GRAVITY, 0);
        }
        // Guardamos la velocidad antes de mover — esto asegura consistencia con move(...)
        this.setDeltaMovement(current);

        // 2) Mover usando la delta actual
        this.move(MoverType.SELF, this.getDeltaMovement());

        // 3) Leer la velocidad resultante tras mover (move puede modificarla por colisiones)
        Vec3 postVel = this.getDeltaMovement();

        // 4) Si está en el suelo, aplicar rebote/fricción/rodado; si está en aire, fricción aérea
        if (this.touchingGround()) {
            // Rebote vertical si venía hacia abajo
            if (postVel.y < 0) {
                double newY = -postVel.y * RESTITUTION;
                postVel = new Vec3(postVel.x, newY, postVel.z);
            }

            // Fricción lateral
            postVel = new Vec3(postVel.x * GROUND_FRICTION, postVel.y, postVel.z * GROUND_FRICTION);

            // Freno adicional de rodado
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

            // Si la velocidad horizontal es muy pequeña, detenerla totalmente (evita vibración)
            if (postVel.horizontalDistanceSqr() < STOP_THRESHOLD) {
                postVel = new Vec3(0.0, postVel.y, 0.0);
            }

            // Si la componente Y es mínima, asentarla a cero
            if (Math.abs(postVel.y) < 1e-3) {
                postVel = new Vec3(postVel.x, 0.0, postVel.z);
            }
        } else {
            // En el aire: fricción ligera
            postVel = new Vec3(postVel.x * AIR_FRICTION, postVel.y, postVel.z * AIR_FRICTION);
        }

        // 5) Limitar velocidad máxima
        double speed = postVel.length();
        if (speed > MAX_SPEED) {
            postVel = postVel.scale(MAX_SPEED / speed);
        }

        // 6) Aplicar la velocidad final
        this.setDeltaMovement(postVel);

        // 7) Eliminar si cae fuera del mundo
        if (this.getY() < -64) {
            this.remove(Entity.RemovalReason.DISCARDED);
        }
    }

    private boolean touchingGround() {
        double checkY = this.getBoundingBox().minY - 0.01;
        BlockPos posBelow = BlockPos.containing(this.getX(), checkY, this.getZ());
        BlockState state = this.getCommandSenderWorld().getBlockState(posBelow);
        VoxelShape shape = state.getCollisionShape(this.getCommandSenderWorld(), posBelow);
        return !shape.isEmpty();
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if (player.isCrouching()) {
            if (!this.getCommandSenderWorld().isClientSide) {
                ItemStack give = new ItemStack(ModItems.PELOTA.get());
                boolean added = player.getInventory().add(give);
                if (!added) player.drop(give, false);
                this.remove(Entity.RemovalReason.DISCARDED);
            }
            return InteractionResult.sidedSuccess(this.getCommandSenderWorld().isClientSide);
        } else {
            if (!this.getCommandSenderWorld().isClientSide) {
                Vec3 dir = player.getLookAngle().normalize();
                double force = player.isSprinting() ? 3.5 : 1.8; // ajustar si querés más o menos
                Vec3 kick = new Vec3(dir.x * force, 0.4, dir.z * force);
                this.setDeltaMovement(kick);
                // Sonidos eliminados para comportamiento silencioso
            }
            return InteractionResult.sidedSuccess(this.getCommandSenderWorld().isClientSide);
        }
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        // Debe concordar con el .sized(...) del EntityType registrado
        return EntityDimensions.scalable(0.7f, 0.7f);
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
    public void push(Entity entity) {
        super.push(entity);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        // Si querés persistir el item mostrado:
        // if (this.display != null && !this.display.isEmpty()) {
        //     compound.put("display", this.display.save(new CompoundTag()));
        // }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        // if (compound.contains("display")) {
        //     this.display = ItemStack.of(compound.getCompound("display"));
        // } else {
        //     this.display = ItemStack.EMPTY;
        // }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        // no synched data por ahora
    }
}