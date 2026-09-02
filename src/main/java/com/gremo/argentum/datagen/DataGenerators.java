package com.gremo.argentum.datagen;

import com.gremo.argentum.Argentum;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Argentum.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {

        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(
                event.includeClient(),
                new ModBlockStateProvider(output, existingFileHelper)
        );

        generator.addProvider(
                event.includeClient(),
                new ModItemModelProvider(output, existingFileHelper)
        );

        ModBlockTagProvider blockTagProvider = generator.addProvider(
                event.includeServer(),
                new ModBlockTagProvider(output, lookupProvider, existingFileHelper)
        );

        generator.addProvider(
                event.includeServer(),
                new ModItemTagProvider(
                        output,
                        lookupProvider,
                        blockTagProvider.contentsGetter(),
                        existingFileHelper
                )
        );
    }
}