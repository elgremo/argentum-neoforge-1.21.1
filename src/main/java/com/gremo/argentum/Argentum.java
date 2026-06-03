package com.gremo.argentum;

import com.gremo.argentum.blocks.ModBlocks;
import com.gremo.argentum.item.ModCreativeModeTabs;
import com.gremo.argentum.item.ModItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Argentum.MOD_ID)
public class Argentum {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "argentum";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public Argentum(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        // Register the clientSetup method for client-side rendering
        modEventBus.addListener(this::clientSetup);

        NeoForge.EVENT_BUS.register(this);

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        modEventBus.addListener(this::addCreative);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
    }

    // Método para configuración del cliente
    @OnlyIn(Dist.CLIENT)
    private void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            // Registrar el tipo de renderizado para que las texturas transparentes funcionen
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(
                    ModBlocks.YERBA_PLANTA.get(),
                    net.minecraft.client.renderer.RenderType.cutout()
            );
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(
                    ModBlocks.TE_PLANTA.get(),
                    net.minecraft.client.renderer.RenderType.cutout()
            );
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(
                    ModBlocks.BATATA_PLANTA.get(),
                    net.minecraft.client.renderer.RenderType.cutout()
            );
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(
                    ModBlocks.MEMBRILLO_PLANTA.get(),
                    net.minecraft.client.renderer.RenderType.cutout()
            );
        });
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS){
        }
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS){
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }
}