package com.gremo.argentum.entity;

import com.gremo.argentum.Argentum;
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
                            .sized(0.5f, 0.5f)
                            .build(ResourceLocation.fromNamespaceAndPath(Argentum.MOD_ID, "pelota").toString())
            );

    // Método para registrar en el bus del mod (lo llamamos desde Argentum.java)
    public static void register(IEventBus modEventBus) {
        ENTITIES.register(modEventBus);
    }
}