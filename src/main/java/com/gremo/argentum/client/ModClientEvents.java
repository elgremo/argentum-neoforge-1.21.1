package com.gremo.argentum.client;

import com.gremo.argentum.Argentum;
import com.gremo.argentum.block.ModBlocks;
import com.gremo.argentum.block.entity.ModBlockEntities;
import com.gremo.argentum.block.entity.renderer.OllaBlockEntityRenderer;
import com.gremo.argentum.block.entity.renderer.ParrillaBlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;

@EventBusSubscriber(modid = Argentum.MOD_ID, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.YERBA_PLANTA.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.BATATA_PLANTA.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.MEMBRILLO_PLANTA.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.TE_PLANTA.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.OLLA.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.PARRILLA.get(), RenderType.cutout());
            BlockEntityRenderers.register(ModBlockEntities.OLLA_BE.get(), OllaBlockEntityRenderer::new);
            BlockEntityRenderers.register(ModBlockEntities.PARRILLA_BE.get(), ParrillaBlockEntityRenderer::new);
        });
    }
}