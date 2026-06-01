package com.gremo.argentum.item;

import com.gremo.argentum.Argentum;
import com.gremo.argentum.blocks.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Argentum.MOD_ID);

    public static final Supplier<CreativeModeTab> MATE_ARGENTO_TAB = CREATIVE_MODE_TAB.register("mate_argento_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.BALA.get()))
                    .title(Component.translatable("creativetab.argentum.bala"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.ACEITE);
                        output.accept(ModItems.BALA);

                    }).build());

    public static final Supplier<CreativeModeTab> CASINO_ARGENTO_TAB = CREATIVE_MODE_TAB.register("casino_argento_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.UNO))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(Argentum.MOD_ID, "mate_argento_tab"))
                    .title(Component.translatable("creativetab.argentum.casino"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.UNO);
                        output.accept(ModBlocks.DOS);
                        output.accept(ModBlocks.TRES);
                        output.accept(ModBlocks.CUATRO);
                        output.accept(ModBlocks.CINCO);
                        output.accept(ModBlocks.SEIS);

                    }).build());


    public static void register (IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
