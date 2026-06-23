package com.gremo.argentum.sound;

import com.gremo.argentum.Argentum;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.util.DeferredSoundType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Argentum.MOD_ID);

    public static final Supplier<SoundEvent> ALDEANO_USE = registerSoundEvent("aldeano_use");

    public static final Supplier<SoundEvent> ALDEANO_BREAK = registerSoundEvent("aldeano_break");
    public static final Supplier<SoundEvent> ALDEANO_STEP = registerSoundEvent("aldeano_step");
    public static final Supplier<SoundEvent> ALDEANO_PLACE = registerSoundEvent("aldeano_place");
    public static final Supplier<SoundEvent> ALDEANO_HIT = registerSoundEvent("aldeano_block_hit");
    public static final Supplier<SoundEvent> ALDEANO_FALL = registerSoundEvent("aldeano_block_fall");

    public static final DeferredSoundType ALDEANO_SONIDO = new DeferredSoundType(1f, 1f,
            ModSounds.ALDEANO_BREAK, ModSounds.ALDEANO_STEP, ModSounds.ALDEANO_PLACE,
            ModSounds.ALDEANO_HIT, ModSounds.ALDEANO_FALL);



    public static final Supplier<SoundEvent> MUCHACHOS = registerSoundEvent("muchachos");
    public static final ResourceKey<JukeboxSong> MUCHACHOS_KEY = createSong("muchachos");
    public static final Supplier<SoundEvent> LA_CUARTA = registerSoundEvent("la_cuarta");
    public static final ResourceKey<JukeboxSong> LA_CUARTA_KEY = createSong("la_cuarta");

    private static ResourceKey<JukeboxSong> createSong(String name) {
         return ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath(Argentum.MOD_ID, name));
    }


    private static Supplier<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(Argentum.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}