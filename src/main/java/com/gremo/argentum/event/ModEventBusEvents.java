package com.gremo.argentum.event;

import com.gremo.argentum.Argentum;
import com.gremo.argentum.entity.ModEntities;
import com.gremo.argentum.entity.client.ChorroModel;
import com.gremo.argentum.entity.custom.ChorroEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = Argentum.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {


    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(
                ChorroModel.LAYER_LOCATION,
                ChorroModel::createBodyLayer
        );
    }

    @SubscribeEvent
    public static void registerAttributes (EntityAttributeCreationEvent event){
        event.put(ModEntities.CHORRO.get(), ChorroEntity.createAttributes().build());
    }
}
