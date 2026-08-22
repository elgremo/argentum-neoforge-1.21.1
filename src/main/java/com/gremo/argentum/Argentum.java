package com.gremo.argentum;

import com.gremo.argentum.block.ModBlocks;
import com.gremo.argentum.entity.ModEntities;
import com.gremo.argentum.entity.client.ChorroRenderer;
import com.gremo.argentum.entity.client.HorneroRenderer;
import com.gremo.argentum.entity.client.TeroRenderer;
import com.gremo.argentum.event.*;
import com.gremo.argentum.item.ModCreativeModeTabs;
import com.gremo.argentum.item.ModItems;
import com.gremo.argentum.particle.CeiboParticles;
import com.gremo.argentum.particle.JacarandaParticles;
import com.gremo.argentum.particle.ModParticles;
import com.gremo.argentum.recipe.RecetasOlla;
import com.gremo.argentum.recipe.RecetasParrilla;
import com.gremo.argentum.sound.ModSounds;
import com.gremo.argentum.villager.ModVillagers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import com.gremo.argentum.block.entity.ModBlockEntities;

@Mod(Argentum.MOD_ID)
public class Argentum {
    public static final String MOD_ID = "argentum";
    public static final Logger LOGGER = LogUtils.getLogger();

    // -- BORRAR EL BLOQUE STATIC QUE ESTABA AQUÍ --

    public Argentum(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        NeoForge.EVENT_BUS.addListener(CraftingEvents::onCraft);
        NeoForge.EVENT_BUS.addListener(CuchilloDrops::onLivingDrops);
        NeoForge.EVENT_BUS.addListener(SunflowerExtractEvent::onRightClickBlock);
        NeoForge.EVENT_BUS.addListener(ModEvents::addCustomTrades);
        NeoForge.EVENT_BUS.addListener(CampfireEvents::onRightClickBlock);


        ModCreativeModeTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        // AGREGAR ESTA LÍNEA AQUÍ:
        ModEntities.register(modEventBus);

        ModSounds.register(modEventBus);
        ModVillagers.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModParticles.register(modEventBus);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }


    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            RecetasParrilla.register();
            RecetasOlla.register();
        });
    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

            EntityRenderers.register(ModEntities.CHORRO.get(), ChorroRenderer::new);
            EntityRenderers.register(ModEntities.HORNERO.get(), HorneroRenderer::new);
            EntityRenderers.register(ModEntities.TERO.get(), TeroRenderer::new);
        }

        @SubscribeEvent
        public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(ModParticles.CEIBO_PARTICLES.get(), CeiboParticles.Provider::new);
            event.registerSpriteSet(ModParticles.JACARANDA_PARTICLES.get(), JacarandaParticles.Provider::new);
        }
    }
}