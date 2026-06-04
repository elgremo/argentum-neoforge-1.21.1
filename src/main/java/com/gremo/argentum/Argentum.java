package com.gremo.argentum;

import com.gremo.argentum.block.ModBlocks;
import com.gremo.argentum.entity.ModEntities;
import com.gremo.argentum.item.ModCreativeModeTabs;
import com.gremo.argentum.item.ModItems;
import com.gremo.argentum.sound.ModSounds;
import com.gremo.argentum.villager.ModVillagers;
import net.minecraft.world.item.CreativeModeTabs;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import com.gremo.argentum.block.entity.ModBlockEntities;

@Mod(Argentum.MOD_ID)
public class Argentum {
    public static final String MOD_ID = "argentum";
    public static final Logger LOGGER = LogUtils.getLogger();

    // -- BORRAR EL BLOQUE STATIC QUE ESTABA AQUÍ --

    public Argentum(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::addCreative);

        NeoForge.EVENT_BUS.register(this);

        ModCreativeModeTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        // AGREGAR ESTA LÍNEA AQUÍ:
        ModEntities.register(modEventBus);

        ModSounds.register(modEventBus);
        ModVillagers.register(modEventBus);
        ModBlockEntities.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // --- Recetas para la PARRILLA (lo que ya tenías) ---
            com.gremo.argentum.block.entity.ParrillaBlockEntity.addRecipe(
                    ModItems.BIFE_CRUDO.get(),
                    ModItems.BIFE_ASADO.get(),
                    220
            );

            com.gremo.argentum.block.entity.ParrillaBlockEntity.addRecipe(
                    ModItems.CHINCHULIN_CRUDO.get(),
                    ModItems.CHINCHULIN_ASADO.get(),
                    180
            );

            com.gremo.argentum.block.entity.ParrillaBlockEntity.addRecipe(
                    ModItems.COSTILLA_CRUDA.get(),
                    ModItems.COSTILLA_ASADA.get(),
                    240
            );

            com.gremo.argentum.block.entity.ParrillaBlockEntity.addRecipe(
                    ModItems.ENTRANA_CRUDA.get(),
                    ModItems.ENTRANA_ASADA.get(),
                    200
            );

            com.gremo.argentum.block.entity.ParrillaBlockEntity.addRecipe(
                    ModItems.LOMO_CRUDO.get(),
                    ModItems.LOMO_ASADO.get(),
                    220
            );

            com.gremo.argentum.block.entity.ParrillaBlockEntity.addRecipe(
                    ModItems.MATAMBRE_CRUDO.get(),
                    ModItems.MATAMBRE_ASADO.get(),
                    230
            );

            com.gremo.argentum.block.entity.ParrillaBlockEntity.addRecipe(
                    ModItems.MOLLEJA_CRUDA.get(),
                    ModItems.MOLLEJA_ASADA.get(),
                    200
            );

            // --- Recetas para la OLLA (solo tus items "fritos") ---
            com.gremo.argentum.block.entity.OllaBlockEntity.addRecipe(
                    ModItems.CHURRO_CRUDO.get(),
                    ModItems.CHURRO_FRITO.get(),
                    180
            );

            com.gremo.argentum.block.entity.OllaBlockEntity.addRecipe(
                    ModItems.EMPANADA_CRUDA.get(),
                    ModItems.EMPANADA_FRITA.get(),
                    200
            );

            com.gremo.argentum.block.entity.OllaBlockEntity.addRecipe(
                    ModItems.MILANESA_CRUDA.get(),
                    ModItems.MILANESA_FRITA.get(),
                    220
            );

            com.gremo.argentum.block.entity.OllaBlockEntity.addRecipe(
                    ModItems.PASTELITO_BATATA_CRUDO.get(),
                    ModItems.PASTELITO_BATATA_FRITO.get(),
                    200
            );

            com.gremo.argentum.block.entity.OllaBlockEntity.addRecipe(
                    ModItems.PASTELITO_MEMBRILLO_CRUDO.get(),
                    ModItems.PASTELITO_MEMBRILLO_FRITO.get(),
                    200
            );

            com.gremo.argentum.block.entity.OllaBlockEntity.addRecipe(
                    ModItems.TORTAFRITA_CRUDA.get(),
                    ModItems.TORTAFRITA.get(),
                    160
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