package com.gremo.argentum.client;

import com.gremo.argentum.Argentum;
import com.gremo.argentum.block.ModBlocks;
import com.gremo.argentum.block.entity.ModBlockEntities;
import com.gremo.argentum.block.renderer.*;
import com.gremo.argentum.client.renderer.PelotaRenderer;
import com.gremo.argentum.entity.ModEntities;
import com.gremo.argentum.entity.client.BalaRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = Argentum.MOD_ID, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.YERBA_PLANTA.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.BATATA_PLANTA.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.MEMBRILLO_PLANTA.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.TE_PLANTA.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.OLLA.get(), RenderType.translucent());

            ItemBlockRenderTypes.setRenderLayer(ModBlocks.ARCO_UNO.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.ARCO_DOS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.ARCO_TRES.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.ARCO_CUATRO.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.ARCO_CINCO.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.ARCO_SEIS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.ARCO_SIETE.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.ARCO_OCHO.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.ARCO_NUEVE.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.ARCO_DIEZ.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.ARCO_ONCE.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.ARCO_DOCE.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.ARCO_FULBO.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.ARCO_CATORCE.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.ARCO_QUINCE.get(), RenderType.translucent());

            ItemBlockRenderTypes.setRenderLayer(ModBlocks.OLLA_FOGATA.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.PARRILLA.get(), RenderType.cutout());
            BlockEntityRenderers.register(ModBlockEntities.OLLA_BE.get(), OllaBlockEntityRenderer::new);
            BlockEntityRenderers.register(ModBlockEntities.PARRILLA_BE.get(), ParrillaBlockEntityRenderer::new);
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.PRENSA_MOSTO.get(), RenderType.cutout());
            BlockEntityRenderers.register(ModBlockEntities.MOSTO_BE.get(), PrensaMostoBlockEntityRenderer::new);

            EntityRenderers.register(ModEntities.PELOTA.get(), PelotaRenderer::new);

            EntityRenderers.register(ModEntities.BALA.get(), BalaRenderer::new);

            BlockEntityRenderers.register(
                    ModBlockEntities.BOTELLERO_BE.get(),
                    BotelleroBlockEntityRenderer::new
            );
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.JACARANDA_BROTE.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.JACARANDA_HOJAS.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.CEIBO_BROTE.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.CEIBO_HOJAS.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.CEIBO_PILA_HOJAS.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.JACARANDA_PILA_HOJAS.get(), RenderType.cutout());

            BlockEntityRenderers.register(
                    ModBlockEntities.NIDO_BE.get(),
                    NidoBlockEntityRenderer::new);

        });
    }
}
