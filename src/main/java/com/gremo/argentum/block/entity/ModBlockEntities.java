// ModBlockEntities.java
package com.gremo.argentum.block.entity;

import com.gremo.argentum.Argentum;
import com.gremo.argentum.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Argentum.MOD_ID);

    public static final Supplier<BlockEntityType<ParrillaBlockEntity>> PARRILLA_BE =
            BLOCK_ENTITIES.register("parrilla_be", () -> BlockEntityType.Builder.of(
                    ParrillaBlockEntity::new, ModBlocks.PARRILLA.get()).build(null));

    // Nueva entidad para la olla
    public static final Supplier<BlockEntityType<OllaBlockEntity>> OLLA_BE =
            BLOCK_ENTITIES.register("olla_be", () -> BlockEntityType.Builder.of(
                    OllaBlockEntity::new,
                    ModBlocks.OLLA.get(),
                    ModBlocks.OLLA_FOGATA.get()
            ).build(null));

    public static final Supplier<BlockEntityType<PavaFogataBlockEntity>> PAVA_FOGATA =
            BLOCK_ENTITIES.register("pava_fogata", () -> BlockEntityType.Builder.of(
                    PavaFogataBlockEntity::new,
                    ModBlocks.PAVA_FOGATA_VACIA.get(),
                    ModBlocks.PAVA_FOGATA_LLENA.get(),
                    ModBlocks.PAVA_FOGATA_CALENTANDO.get(),
                    ModBlocks.PAVA_FOGATA_CALIENTE.get()
            ).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}