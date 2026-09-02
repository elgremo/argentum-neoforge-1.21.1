package com.gremo.argentum.entity.custom;

import com.gremo.argentum.item.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DadoEntity extends Entity {
    private static final EntityDataAccessor<Integer> DATA_RESULTADO =
            SynchedEntityData.defineId(DadoEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_VIDA =
            SynchedEntityData.defineId(DadoEntity.class, EntityDataSerializers.INT);

    private boolean dropped = false; // Para evitar que dropee más de una vez

    public DadoEntity(EntityType<?> type, Level level) {
        super(type, level);
        this.noPhysics = true;
    }

    public DadoEntity(EntityType<?> type, Level level, int resultado) {
        super(type, level);
        this.noPhysics = true;
        this.entityData.set(DATA_RESULTADO, resultado);
        this.entityData.set(DATA_VIDA, 60); // 3 segundos
    }

    public int getResultado() {
        return this.entityData.get(DATA_RESULTADO);
    }

    public int getVida() {
        return this.entityData.get(DATA_VIDA);
    }

    @Override
    public void tick() {
        super.tick();
        int vida = this.entityData.get(DATA_VIDA) - 1;
        this.entityData.set(DATA_VIDA, vida);

        if (vida <= 0 && !dropped) {
            // ⭐ DROPEAR EL ÍTEM DE DADO CUANDO MUERE
            this.spawnAtLocation(new ItemStack(ModItems.DADO.get()));
            dropped = true;
            this.discard();
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_RESULTADO, 1);
        builder.define(DATA_VIDA, 60);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        if (compound.contains("Resultado")) {
            this.entityData.set(DATA_RESULTADO, compound.getInt("Resultado"));
        }
        if (compound.contains("Vida")) {
            this.entityData.set(DATA_VIDA, compound.getInt("Vida"));
        }
        if (compound.contains("Dropped")) {
            this.dropped = compound.getBoolean("Dropped");
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.putInt("Resultado", this.entityData.get(DATA_RESULTADO));
        compound.putInt("Vida", this.entityData.get(DATA_VIDA));
        compound.putBoolean("Dropped", this.dropped);
    }
}