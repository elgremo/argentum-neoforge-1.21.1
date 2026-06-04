package com.gremo.argentum.item;

import com.gremo.argentum.Argentum;
import com.gremo.argentum.block.ModBlocks;
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
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.MATE.get()))
                    .title(Component.translatable("creativetab.argentum.bala"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.OLLA);
                        output.accept(ModBlocks.PARRILLA);
                        output.accept(ModItems.MATE);
                        output.accept(ModItems.TERMO);
                        output.accept(ModItems.TERMO_ARGENTO);
                        output.accept(ModItems.MATE_LISTO_AMARILLO);
                        output.accept(ModItems.MATE_LISTO_ARGENTO);
                        output.accept(ModItems.MATE_LISTO_AZUL);
                        output.accept(ModItems.MATE_LISTO_BLANCO);
                        output.accept(ModItems.MATE_LISTO_CELESTE);
                        output.accept(ModItems.MATE_LISTO_CYAN);
                        output.accept(ModItems.MATE_LISTO_GRIS1);
                        output.accept(ModItems.MATE_LISTO_GRIS2);
                        output.accept(ModItems.MATE_LISTO_MAGENTA);
                        output.accept(ModItems.MATE_LISTO_MARRON);
                        output.accept(ModItems.MATE_LISTO_NARANJA);
                        output.accept(ModItems.MATE_LISTO_NEGRO);
                        output.accept(ModItems.MATE_LISTO_ROJO);
                        output.accept(ModItems.MATE_LISTO_ROSA);
                        output.accept(ModItems.MATE_LISTO_VERDE1);
                        output.accept(ModItems.MATE_LISTO_VERDE2);
                        output.accept(ModItems.MATE_LISTO_VIOLETA);
                        output.accept(ModItems.BOMBILLA);
                        output.accept(ModItems.YERBA);
                        output.accept(ModItems.YERBA_SEMILLA);
                        output.accept(ModItems.CALABAZA_MATE);
                        output.accept(ModItems.SAQUITO_MATECOCIDO);
                        output.accept(ModItems.SAQUITO_TE);
                        output.accept(ModItems.TE);
                        output.accept(ModItems.TE_SEMILLA);
                        output.accept(ModItems.DULCE_LECHE);
                        output.accept(ModItems.LECHE);
                        output.accept(ModItems.BIFE_ASADO);
                        output.accept(ModItems.CHINCHULIN_ASADO);
                        output.accept(ModItems.CHURRO_FRITO);
                        output.accept(ModItems.CHURRO_FRITO_DULCE);
                        output.accept(ModItems.COSTILLA_ASADA);
                        output.accept(ModItems.EMPANADA_FRITA);
                        output.accept(ModItems.ENTRANA_ASADA);
                        output.accept(ModItems.MATAMBRE_ASADO);
                        output.accept(ModItems.MEDIALUNA_COCINADA);
                        output.accept(ModItems.MILANESA_FRITA);
                        output.accept(ModItems.LOMO_ASADO);
                        output.accept(ModItems.MOLLEJA_ASADA);
                        output.accept(ModItems.PASTELITO_BATATA_FRITO);
                        output.accept(ModItems.PASTELITO_MEMBRILLO_FRITO);
                        output.accept(ModItems.TORTAFRITA);
                        output.accept(ModItems.BIFE_CRUDO);
                        output.accept(ModItems.CHINCHULIN_CRUDO);
                        output.accept(ModItems.CHURRO_CRUDO);
                        output.accept(ModItems.COSTILLA_CRUDA);
                        output.accept(ModItems.EMPANADA_CRUDA);
                        output.accept(ModItems.ENTRANA_CRUDA);
                        output.accept(ModItems.LOMO_CRUDO);
                        output.accept(ModItems.MATAMBRE_CRUDO);
                        output.accept(ModItems.MEDIALUNA_CRUDA);
                        output.accept(ModItems.MILANESA_CRUDA);
                        output.accept(ModItems.MOLLEJA_CRUDA);
                        output.accept(ModItems.PASTELITO_BATATA_CRUDO);
                        output.accept(ModItems.PASTELITO_MEMBRILLO_CRUDO);
                        output.accept(ModItems.TORTAFRITA_CRUDA);
                        output.accept(ModItems.BATATA);
                        output.accept(ModItems.CARNE_CORTADA_CRUDA);
                        output.accept(ModItems.DULCE_BATATA);
                        output.accept(ModItems.DULCE_MEMBRILLO);
                        output.accept(ModItems.FERNET);
                        output.accept(ModItems.GRASA);
                        output.accept(ModItems.HARINA);
                        output.accept(ModItems.MANTECA);
                        output.accept(ModItems.MEMBRILLO);
                        output.accept(ModItems.MEMBRILLO_SEMILLA);
                        output.accept(ModItems.PAN_RALLADO);
                        output.accept(ModItems.PELOTA);
                        output.accept(ModItems.TAPAS_EMPANADA);
                        output.accept(ModItems.TAPAS_HOJALDRE);
                        output.accept(ModItems.TAZA);
                        output.accept(ModItems.TAZA_DE_ARCILLA);
                        output.accept(ModItems.TAZA_DE_MATECOCIDO);
                        output.accept(ModItems.TAZA_DE_TE);
                        output.accept(ModItems.ACEITE);
                        output.accept(ModItems.BALA);
                        output.accept(ModItems.CUCHILLO);

                    }).build());

    public static final Supplier<CreativeModeTab> CASINO_ARGENTO_TAB = CREATIVE_MODE_TAB.register("casino_argento_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.BARAJA_SELLADA.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(Argentum.MOD_ID, "mate_argento_tab"))
                    .title(Component.translatable("creativetab.argentum.casino"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.CHORRO_SPAWN_EGG);
                        output.accept(ModBlocks.UNO);
                        output.accept(ModBlocks.DOS);
                        output.accept(ModBlocks.TRES);
                        output.accept(ModBlocks.CUATRO);
                        output.accept(ModBlocks.CINCO);
                        output.accept(ModBlocks.SEIS);
                        output.accept(ModItems.CUADRO_ARGENTO);
                        output.accept(ModItems.PISTOLA);
                        output.accept(ModItems.FICHA_CASINO_1);
                        output.accept(ModItems.FICHA_CASINO_5);
                        output.accept(ModItems.FICHA_CASINO_32);
                        output.accept(ModItems.FICHA_CASINO_64);
                        output.accept(ModItems.FICHA_CASINO_ESPECIAL);
                        output.accept(ModItems.FICHA_CASINO_OTRA);
                        output.accept(ModItems.BARAJA_SELLADA);
                        output.accept(ModItems.BARAJA_SELLADA);

// Cartas de Copa
                        output.accept(ModItems.CARTA_COPA_1);
                        output.accept(ModItems.CARTA_COPA_2);
                        output.accept(ModItems.CARTA_COPA_3);
                        output.accept(ModItems.CARTA_COPA_4);
                        output.accept(ModItems.CARTA_COPA_5);
                        output.accept(ModItems.CARTA_COPA_6);
                        output.accept(ModItems.CARTA_COPA_7);
                        output.accept(ModItems.CARTA_COPA_8);
                        output.accept(ModItems.CARTA_COPA_9);
                        output.accept(ModItems.CARTA_COPA_10);
                        output.accept(ModItems.CARTA_COPA_11);
                        output.accept(ModItems.CARTA_COPA_12);

// Cartas de Espada
                        output.accept(ModItems.CARTA_ESPADA_1);
                        output.accept(ModItems.CARTA_ESPADA_2);
                        output.accept(ModItems.CARTA_ESPADA_3);
                        output.accept(ModItems.CARTA_ESPADA_4);
                        output.accept(ModItems.CARTA_ESPADA_5);
                        output.accept(ModItems.CARTA_ESPADA_6);
                        output.accept(ModItems.CARTA_ESPADA_7);
                        output.accept(ModItems.CARTA_ESPADA_8);
                        output.accept(ModItems.CARTA_ESPADA_9);
                        output.accept(ModItems.CARTA_ESPADA_10);
                        output.accept(ModItems.CARTA_ESPADA_11);
                        output.accept(ModItems.CARTA_ESPADA_12);

// Cartas de Oro
                        output.accept(ModItems.CARTA_ORO_1);
                        output.accept(ModItems.CARTA_ORO_2);
                        output.accept(ModItems.CARTA_ORO_3);
                        output.accept(ModItems.CARTA_ORO_4);
                        output.accept(ModItems.CARTA_ORO_5);
                        output.accept(ModItems.CARTA_ORO_6);
                        output.accept(ModItems.CARTA_ORO_7);
                        output.accept(ModItems.CARTA_ORO_8);
                        output.accept(ModItems.CARTA_ORO_9);
                        output.accept(ModItems.CARTA_ORO_10);
                        output.accept(ModItems.CARTA_ORO_11);
                        output.accept(ModItems.CARTA_ORO_12);

// Cartas de Palo
                        output.accept(ModItems.CARTA_PALO_1);
                        output.accept(ModItems.CARTA_PALO_2);
                        output.accept(ModItems.CARTA_PALO_3);
                        output.accept(ModItems.CARTA_PALO_4);
                        output.accept(ModItems.CARTA_PALO_5);
                        output.accept(ModItems.CARTA_PALO_6);
                        output.accept(ModItems.CARTA_PALO_7);
                        output.accept(ModItems.CARTA_PALO_8);
                        output.accept(ModItems.CARTA_PALO_9);
                        output.accept(ModItems.CARTA_PALO_10);
                        output.accept(ModItems.CARTA_PALO_11);
                        output.accept(ModItems.CARTA_PALO_12);

                    }).build());


    public static void register (IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
