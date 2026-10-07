package com.gremo.argentum.particle;

import com.gremo.argentum.Argentum;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Argentum.MOD_ID);



    public static final Supplier<SimpleParticleType> CEIBO_PARTICLES =
            PARTICLE_TYPES.register("ceibo_particles", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> JACARANDA_PARTICLES =
            PARTICLE_TYPES.register("jacaranda_particles", () -> new SimpleParticleType(false));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
