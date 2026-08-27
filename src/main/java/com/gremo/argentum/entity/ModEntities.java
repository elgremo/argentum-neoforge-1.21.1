package com.gremo.argentum.entity;

import com.gremo.argentum.Argentum;
import com.gremo.argentum.entity.custom.*;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
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

    public static final DeferredHolder<EntityType<?>, EntityType<TeroEntity>> TERO =
            ENTITIES.register("tero",
                    () -> EntityType.Builder.of(TeroEntity::new, MobCategory.CREATURE)
                            .sized(0.55F, 0.75F)
                            .build(ResourceLocation.fromNamespaceAndPath(
                                    Argentum.MOD_ID,
                                    "tero"
                            ).toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<HorneroEntity>> HORNERO =
            ENTITIES.register("hornero",
                    () -> EntityType.Builder.of(HorneroEntity::new, MobCategory.CREATURE)
                            .sized(0.55F, 0.75F)
                            .build(ResourceLocation.fromNamespaceAndPath(
                                    Argentum.MOD_ID,
                                    "hornero"
                            ).toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<ZorroGrisEntity>> ZORRO_GRIS =
            ENTITIES.register("zorro_gris",
                    () -> EntityType.Builder.of(ZorroGrisEntity::new, MobCategory.CREATURE)
                            .sized(0.6F, 0.8F)
                            .build(ResourceLocation.fromNamespaceAndPath(Argentum.MOD_ID, "zorro_gris").toString()));


    // Método para registrar en el bus del mod (lo llamamos desde Argentum.java)
    public static void register(IEventBus modEventBus) {
        ENTITIES.register(modEventBus);
    }
}