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

    public static final Supplier<BlockEntityType<PrensaMostoBlockEntity>> MOSTO_BE =
            BLOCK_ENTITIES.register("mosto_be", () -> BlockEntityType.Builder.of(
                    PrensaMostoBlockEntity::new, ModBlocks.PRENSA_MOSTO.get()).build(null));

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

    public static final Supplier<BlockEntityType<BarrilFermentoBlockEntity>> BARRIL_FERMENTO_BE =
            BLOCK_ENTITIES.register("barril_fermento_be", () -> BlockEntityType.Builder.of(
                    BarrilFermentoBlockEntity::new,
                    ModBlocks.BARRIL_FERMENTO.get()
            ).build(null));

    public static final Supplier<BlockEntityType<BotelleroBlockEntity>> BOTELLERO_BE =
            BLOCK_ENTITIES.register("botellero_be", () -> BlockEntityType.Builder.of(
                    BotelleroBlockEntity::new,
                    ModBlocks.BOTELLERO_ABEDUL.get(),
                    ModBlocks.BOTELLERO_ABETO.get(),
                    ModBlocks.BOTELLERO_ACACIA.get(),
                    ModBlocks.BOTELLERO_CARMESI.get(),
                    ModBlocks.BOTELLERO_CEREZO.get(),
                    ModBlocks.BOTELLERO_DISTORCIONADO.get(),
                    ModBlocks.BOTELLERO_JUNGLA.get(),
                    ModBlocks.BOTELLERO_MANGLAR.get(),
                    ModBlocks.BOTELLERO_ROBLE.get(),
                    ModBlocks.BOTELLERO_ROBLE_OSCURO.get(),
                    ModBlocks.BOTELLERO_JACARANDA.get(),
                    ModBlocks.BOTELLERO_CEIBO.get()
            ).build(null));

    // ✅ CORREGIDO: Ahora es Supplier
    public static final Supplier<BlockEntityType<NidoBlockEntity>> NIDO_BE =
            BLOCK_ENTITIES.register("nido_be", () -> BlockEntityType.Builder.of(NidoBlockEntity::new, ModBlocks.NIDO.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}