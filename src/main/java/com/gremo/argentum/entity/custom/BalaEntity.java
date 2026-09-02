package com.gremo.argentum.entity.custom;

import com.gremo.argentum.entity.ModEntities;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import com.gremo.argentum.item.ModItems;

public class BalaEntity extends ThrowableProjectile implements ItemSupplier {

    public BalaEntity(EntityType<? extends ThrowableProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public BalaEntity(Level level, LivingEntity shooter) {
        super(ModEntities.BALA.get(), shooter, level);
    }

    private float damage = 5.0F;
    public void setDamage(float damage) {
        this.damage = damage;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);

        if (!level().isClientSide()) {
            result.getEntity().hurt(
                    damageSources().mobProjectile(this, (LivingEntity) this.getOwner()),
                    damage
            );

            discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);

        if (!level().isClientSide()) {
            discard();
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(ModItems.BALA.get());
    }
}
