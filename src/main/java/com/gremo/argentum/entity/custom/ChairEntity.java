package com.gremo.argentum.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class ChairEntity extends Entity {

    public ChairEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        // Sin data sincronizada
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        // No se guarda en NBT
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        // No se guarda en NBT
    }

    @Override
    protected void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
        // Cuando el jugador se baja, la entidad se elimina sola
        this.kill();
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }
}