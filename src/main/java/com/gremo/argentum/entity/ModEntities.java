package com.gremo.argentum.entity;

import com.gremo.argentum.Argentum;
import com.gremo.argentum.entity.custom.BalaEntity;
import com.gremo.argentum.entity.custom.ChorroEntity;
import com.gremo.argentum.entity.custom.PelotaEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    // Usamos el registro diferido de NeoForge
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(Registries.ENTITY_TYPE, Argentum.MOD_ID);

    // Registramos la pelota como un DeferredHolder
    public static final DeferredHolder<EntityType<?>, EntityType<PelotaEntity>> PELOTA =
            ENTITIES.register("pelota",
                    () -> EntityType.Builder.<PelotaEntity>of(PelotaEntity::new, MobCategory.MISC)
                            .sized(2.0f, 2.0f)
                            .build(ResourceLocation.fromNamespaceAndPath(Argentum.MOD_ID, "pelota").toString())
            );

    public static final DeferredHolder<EntityType<?>, EntityType<ChorroEntity>> CHORRO =
            ENTITIES.register("chorro",
                    () -> EntityType.Builder.<ChorroEntity>of(ChorroEntity::new, MobCategory.MONSTER)
                            .sized(0.6f, 1.95f)
                            .build(ResourceLocation.fromNamespaceAndPath(Argentum.MOD_ID, "chorro").toString())
            );

    public static final DeferredHolder<EntityType<?>, EntityType<BalaEntity>> BALA =
            ENTITIES.register("bala",
                    () -> EntityType.Builder.<BalaEntity>of(BalaEntity::new, MobCategory.MISC)
                            .sized(1.0F, 1.0F)
                            .clientTrackingRange(4)
                            .updateInterval(1)
                            .build(ResourceLocation.fromNamespaceAndPath(Argentum.MOD_ID, "bala").toString())
            );

    // Método para registrar en el bus del mod (lo llamamos desde Argentum.java)
    public static void register(IEventBus modEventBus) {
        ENTITIES.register(modEventBus);
    }
}