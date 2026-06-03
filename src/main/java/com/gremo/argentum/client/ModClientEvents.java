// ModClientEvents.java
package com.gremo.argentum.client;

import com.gremo.argentum.Argentum;
import com.gremo.argentum.blocks.ModBlocks;
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
            // Registrar el tipo de renderizado para que las texturas transparentes funcionen
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.YERBA_PLANTA.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.BATATA_PLANTA.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.MEMBRILLO_PLANTA.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.TE_PLANTA.get(), RenderType.cutout());
        });
    }
}