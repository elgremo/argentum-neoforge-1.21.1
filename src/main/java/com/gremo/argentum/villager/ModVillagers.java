package com.gremo.argentum.villager;

import com.google.common.collect.ImmutableSet;
import com.gremo.argentum.Argentum;
import com.gremo.argentum.block.ModBlocks;
import com.gremo.argentum.sound.ModSounds;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModVillagers {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, Argentum.MOD_ID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(BuiltInRegistries.VILLAGER_PROFESSION, Argentum.MOD_ID);

    public static final Holder<PoiType> KEVIN_POI = POI_TYPES.register("kevin_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.PARRILLA.get().getStateDefinition().getPossibleStates()), 1, 1));

    public static final Holder<VillagerProfession> VENDEDOR = VILLAGER_PROFESSIONS.register("vendedor",
            () -> new VillagerProfession("vendedor", holder -> holder.value() == KEVIN_POI.value(),
                    poiTypeHolder -> poiTypeHolder.value() == KEVIN_POI.value(),
                    ImmutableSet.of(), ImmutableSet.of(), ModSounds.ALDEANO_BREAK.get()));

    public static final Holder<PoiType> JONI_POI = POI_TYPES.register("joni_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.OLLA.get().getStateDefinition().getPossibleStates()), 1, 1));

    public static final Holder<VillagerProfession> VENDEDORB = VILLAGER_PROFESSIONS.register("vendedorb",
            () -> new VillagerProfession("vendedorb", holder -> holder.value() == JONI_POI.value(),
                    poiTypeHolder -> poiTypeHolder.value() == JONI_POI.value(),
                    ImmutableSet.of(), ImmutableSet.of(), ModSounds.ALDEANO_BREAK.get()));




    public static void register(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}