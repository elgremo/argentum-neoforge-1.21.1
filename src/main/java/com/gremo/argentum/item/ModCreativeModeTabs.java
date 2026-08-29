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
                        // ======================================================
                        // 🌳 JACARANDÁ
                        // ======================================================

                        output.accept(ModBlocks.JACARANDA_BROTE);
                        output.accept(ModBlocks.JACARANDA_HOJAS);
                        output.accept(ModBlocks.JACARANDA_PILA_HOJAS);

                        output.accept(ModBlocks.JACARANDA_TRONCO);
                        output.accept(ModBlocks.PELADO_JACARANDA_TRONCO);

                        output.accept(ModBlocks.JACARANDA_COMPLETO);
                        output.accept(ModBlocks.PELADO_JACARANDA_COMPLETO);

                        output.accept(ModBlocks.JACARANDA_MADERA);

                        output.accept(ModBlocks.JACARANDA_ESCALERAS);
                        output.accept(ModBlocks.JACARANDA_LOSA);
                        output.accept(ModBlocks.JACARANDA_VALLA);
                        output.accept(ModBlocks.JACARANDA_PORTON);
                        output.accept(ModBlocks.JACARANDA_PUERTA);
                        output.accept(ModBlocks.JACARANDA_TRAMPILLA);
                        output.accept(ModBlocks.JACARANDA_BOTON);
                        output.accept(ModBlocks.JACARANDA_PLACA_PRESION);
                        //output.accept(ModItems.JACARANDA_BOTE);

// ======================================================
// 🌺 CEIBO
// ======================================================

                        output.accept(ModBlocks.CEIBO_BROTE);
                        output.accept(ModBlocks.CEIBO_HOJAS);
                        output.accept(ModBlocks.CEIBO_PILA_HOJAS);

                        output.accept(ModBlocks.CEIBO_TRONCO);
                        output.accept(ModBlocks.PELADO_CEIBO_TRONCO);

                        output.accept(ModBlocks.CEIBO_COMPLETO);
                        output.accept(ModBlocks.PELADO_CEIBO_COMPLETO);

                        output.accept(ModBlocks.CEIBO_MADERA);

                        output.accept(ModBlocks.CEIBO_ESCALERAS);
                        output.accept(ModBlocks.CEIBO_LOSA);
                        output.accept(ModBlocks.CEIBO_VALLA);
                        output.accept(ModBlocks.CEIBO_PORTON);
                        output.accept(ModBlocks.CEIBO_PUERTA);
                        output.accept(ModBlocks.CEIBO_TRAMPILLA);
                        output.accept(ModBlocks.CEIBO_BOTON);
                        output.accept(ModBlocks.CEIBO_PLACA_PRESION);


// ======================================================
// TERO
// ======================================================
                        output.accept(ModBlocks.NIDO);

// ======================================================
// 🍳 COCINA
// ======================================================

                        output.accept(ModBlocks.OLLA);
                        output.accept(ModBlocks.PARRILLA);
                        output.accept(ModItems.CUCHILLO);


// ======================================================
// 🧉 SISTEMA DEL MATE
// ======================================================

                        output.accept(ModItems.BOMBILLA);
                        output.accept(ModItems.CALABAZA_MATE);

                        output.accept(ModItems.MATE_VACIO);
                        output.accept(ModItems.MATE);

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

                        output.accept(ModItems.PAVA);
                        output.accept(ModItems.PAVA_CALIENTE);

                        output.accept(ModItems.TERMO);
                        output.accept(ModItems.TERMO_VACIO);

                        output.accept(ModItems.TERMO_ARGENTO);
                        output.accept(ModItems.TERMO_ARGENTO_VACIO);


// ======================================================
// 🌱 CULTIVOS
// ======================================================

                        output.accept(ModItems.YERBA_SEMILLA);
                        output.accept(ModItems.YERBA);
                        output.accept(ModItems.YERBA_AHUMADA);
                        output.accept(ModItems.PAQUETE_YERBA_MATE);

                        output.accept(ModItems.TE_SEMILLA);
                        output.accept(ModItems.TE);

                        output.accept(ModItems.UVA_SEMILLA);
                        output.accept(ModItems.UVA);
                        output.accept(ModItems.UVA_BLANCA_SEMILLA);
                        output.accept(ModItems.UVA_BLANCA);

                        output.accept(ModItems.MEMBRILLO_SEMILLA);
                        output.accept(ModItems.MEMBRILLO);

                        output.accept(ModItems.BATATA);


// ======================================================
// 🍷 VINOS
// ======================================================

                        output.accept(ModBlocks.PRENSA_MOSTO);
                        output.accept(ModBlocks.BARRIL_FERMENTO_TINTO);
                        output.accept(ModBlocks.BARRIL_FERMENTO_ROSADO);
                        output.accept(ModBlocks.BARRIL_FERMENTO_BLANCO);

                        output.accept(ModItems.BALDE_MOSTO);
                        output.accept(ModItems.BALDE_MOSTO_BLANCO);
                        output.accept(ModItems.BALDE_MOSTO_ROSADO);
                        output.accept(ModItems.BALDE_MOSTO_TURBIO);

                        output.accept(ModItems.BOTELLA_VINO_VACIA);
                        output.accept(ModItems.BOTELLA_VINO_TINTO_LLENA);
                        output.accept(ModItems.BOTELLA_VINO_BLANCO_LLENA);
                        output.accept(ModItems.BOTELLA_VINO_ROSADO_LLENA);

                        output.accept(ModItems.COPA_VINO_VACIA);
                        output.accept(ModItems.COPA_VINO_TINTO);

                        output.accept(ModBlocks.BOTELLERO_JACARANDA);
                        output.accept(ModBlocks.BOTELLERO_CEIBO);
                        output.accept(ModBlocks.BOTELLERO_ROBLE);
                        output.accept(ModBlocks.BOTELLERO_ROBLE_OSCURO);
                        output.accept(ModBlocks.BOTELLERO_ABEDUL);
                        output.accept(ModBlocks.BOTELLERO_ABETO);
                        output.accept(ModBlocks.BOTELLERO_ACACIA);
                        output.accept(ModBlocks.BOTELLERO_JUNGLA);
                        output.accept(ModBlocks.BOTELLERO_MANGLAR);
                        output.accept(ModBlocks.BOTELLERO_CEREZO);
                        output.accept(ModBlocks.BOTELLERO_CARMESI);
                        output.accept(ModBlocks.BOTELLERO_DISTORCIONADO);


// ======================================================
// 🥩 INGREDIENTES
// ======================================================

                        output.accept(ModItems.SAL);
                        output.accept(ModItems.HARINA);
                        output.accept(ModItems.ACEITE);
                        output.accept(ModItems.MANTECA);

                        output.accept(ModItems.LECHE);

                        output.accept(ModItems.PAN_RALLADO);

                        output.accept(ModItems.GRASA);
                        output.accept(ModItems.TRIPIN_CERDO);


// ======================================================
// 🥩 CARNES CRUDAS
// ======================================================

                        output.accept(ModItems.BIFE_CRUDO);
                        output.accept(ModItems.COSTILLA_CRUDA);
                        output.accept(ModItems.LOMO_CRUDO);
                        output.accept(ModItems.MATAMBRE_CRUDO);
                        output.accept(ModItems.ENTRANA_CRUDA);
                        output.accept(ModItems.MOLLEJA_CRUDA);
                        output.accept(ModItems.CHINCHULIN_CRUDO);

                        output.accept(ModItems.CHORIZO_PARRILLERO_CRUDO);

                        output.accept(ModItems.CARNE_CORTADA_CRUDA);


// ======================================================
// 🔥 CARNES COCIDAS
// ======================================================

                        output.accept(ModItems.BIFE_ASADO);
                        output.accept(ModItems.COSTILLA_ASADA);
                        output.accept(ModItems.LOMO_ASADO);
                        output.accept(ModItems.MATAMBRE_ASADO);
                        output.accept(ModItems.ENTRANA_ASADA);
                        output.accept(ModItems.MOLLEJA_ASADA);
                        output.accept(ModItems.CHINCHULIN_ASADO);

                        output.accept(ModItems.CHORIZO_PARRILLERO_COCIDO);

                        output.accept(ModItems.CARNE_CORTADA_COCIDA);


// ======================================================
// 🥐 PANADERÍA
// ======================================================

                        output.accept(ModItems.TAPAS_EMPANADA);
                        output.accept(ModItems.TAPAS_HOJALDRE);

                        output.accept(ModItems.MEDIALUNA_CRUDA);
                        output.accept(ModItems.MEDIALUNA_COCINADA);

                        output.accept(ModItems.EMPANADA_CRUDA);
                        output.accept(ModItems.EMPANADA_FRITA);

                        output.accept(ModItems.PASTELITO_BATATA_CRUDO);
                        output.accept(ModItems.PASTELITO_BATATA_FRITO);

                        output.accept(ModItems.PASTELITO_MEMBRILLO_CRUDO);
                        output.accept(ModItems.PASTELITO_MEMBRILLO_FRITO);

                        output.accept(ModItems.TORTAFRITA_CRUDA);
                        output.accept(ModItems.TORTAFRITA);

                        output.accept(ModItems.CHURRO_CRUDO);
                        output.accept(ModItems.CHURRO_FRITO);
                        output.accept(ModItems.CHURRO_FRITO_DULCE);


// ======================================================
// 🍽️ COMIDAS
// ======================================================

                        output.accept(ModItems.CHORIPAN);

                        output.accept(ModItems.HUEVO_TERO);
                        output.accept(ModItems.HUEVO_HORNERO);

                        output.accept(ModItems.MILANESA_CRUDA);
                        output.accept(ModItems.MILANESA_FRITA);

                        output.accept(ModItems.ALFAJOR);

                        output.accept(ModItems.DULCE_LECHE);
                        output.accept(ModItems.DULCE_BATATA);
                        output.accept(ModItems.DULCE_MEMBRILLO);

                        output.accept(ModItems.FERNET);


// ======================================================
// ☕ INFUSIONES
// ======================================================

                        output.accept(ModItems.SAQUITO_MATECOCIDO);
                        output.accept(ModItems.SAQUITO_TE);

                        output.accept(ModItems.TAZA_DE_ARCILLA);
                        output.accept(ModItems.TAZA);

                        output.accept(ModItems.TAZA_DE_MATECOCIDO);
                        output.accept(ModItems.TAZA_DE_TE);

                    }).build());

    public static final Supplier<CreativeModeTab> CASINO_ARGENTO_TAB = CREATIVE_MODE_TAB.register("casino_argento_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.BARAJA_SELLADA.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(Argentum.MOD_ID, "mate_argento_tab"))
                    .title(Component.translatable("creativetab.argentum.casino"))
                    .displayItems((itemDisplayParameters, output) -> {
                        //output.accept(ModItems.CHORRO_SPAWN_EGG);
// ======================================================
// 🎲 DADOS
// ======================================================

                        output.accept(ModItems.DADO);
                        output.accept(ModBlocks.UNO);
                        output.accept(ModBlocks.DOS);
                        output.accept(ModBlocks.TRES);
                        output.accept(ModBlocks.CUATRO);
                        output.accept(ModBlocks.CINCO);
                        output.accept(ModBlocks.SEIS);


// ======================================================
// 🪙 FICHAS
// ======================================================

                        output.accept(ModItems.FICHA_CASINO_2);
                        output.accept(ModItems.FICHA_CASINO_4);
                        output.accept(ModItems.FICHA_CASINO_8);
                        output.accept(ModItems.FICHA_CASINO_16);
                        output.accept(ModItems.FICHA_CASINO_32);
                        output.accept(ModItems.FICHA_CASINO_64);
                        output.accept(ModItems.FICHA_CASINO_ESPECIAL);


// ======================================================
// 🃏 BARAJA
// ======================================================

                        output.accept(ModItems.BARAJA_SELLADA);


// ======================================================
// 💿 DISCOS
// ======================================================

                        output.accept(ModItems.MUCHACHOS_DISCO_MUSICA);
                        output.accept(ModItems.LA_CUARTA_DISCO_MUSICA);


// ======================================================
// ♥ COPA
// ======================================================

                        output.accept(ModItems.CARTA_COMODIN);
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

// ======================================================
// ♠ ESPADA
// ======================================================

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

// ======================================================
// ♦ ORO
// ======================================================

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

// ======================================================
// ♣ BASTO
// ======================================================

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
                        output.accept(ModItems.CARTA_COMODIN);

                    }).build());



    public static final Supplier<CreativeModeTab> EQUIPO_ARGENTO_TAB = CREATIVE_MODE_TAB.register("equipo_argento_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.CAMISETA_ARGENTINA.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(Argentum.MOD_ID, "casino_argento_tab"))
                    .title(Component.translatable("creativetab.argentum.equipo"))
                    .displayItems((itemDisplayParameters, output) -> {

// ======================================================
// 👤 NPC
// ======================================================

                        output.accept(ModItems.CHORRO_SPAWN_EGG);
                        output.accept(ModItems.TERO_SPAWN_EGG);
                        output.accept(ModItems.HORNERO_SPAWN_EGG);
                        output.accept(ModItems.ZORRO_GRIS_SPAWN_EGG);

// ======================================================
// 🔫 ARMAS
// ======================================================

                        output.accept(ModItems.BALA);
                        output.accept(ModItems.CHUNGO);
                        output.accept(ModItems.RECORTADA);
                        output.accept(ModItems.FAL);


// ======================================================
// 🏆 TROFEOS
// ======================================================

                        output.accept(ModBlocks.COPA_MUNDO);
                        output.accept(ModBlocks.COPA_AMERICA);


// ======================================================
// ⚽ PELOTAS
// ======================================================

                        output.accept(ModItems.PELOTA);
                        output.accept(ModItems.PELOTA_TELSTAR);
                        output.accept(ModItems.PELOTA_AZTECA);
                        output.accept(ModItems.PELOTA_JABULANI);
                        output.accept(ModItems.PELOTA_AL_RIHLA);
                        output.accept(ModBlocks.ARCO_FULBO);


// ======================================================
// 👕 CAMISETAS
// ======================================================

                        output.accept(ModItems.CAMISETA);

                        output.accept(ModItems.CAMISETA_ARGENTINA_78);
                        output.accept(ModItems.CAMISETA_ARGENTINA_94);
                        output.accept(ModItems.CAMISETA_DIEGO);
                        output.accept(ModItems.CAMISETA_ARGENTINA_2022);

                        output.accept(ModItems.CAMISETA_ARGENTINA);
                        output.accept(ModItems.CAMISETA_ARGENTINA_ALTERNATIVA);

                        output.accept(ModItems.CAMISETA_RIVER);
                        output.accept(ModItems.CAMISETA_BOCA);
                        output.accept(ModItems.CAMISETA_INDEPENDIENTE);
                        output.accept(ModItems.CAMISETA_RACING);

                        output.accept(ModItems.CAMISETA_ARGENTINOS_JR);
                        output.accept(ModItems.CAMISETA_ESTUDIANTES_PLATA);
                        output.accept(ModItems.CAMISETA_PLATENSE);
                        output.accept(ModItems.CAMISETA_SPREEN);
                        output.accept(ModItems.CAMISETA_TALLERES_CORDOBA);
                        output.accept(ModItems.CAMISETA_TIGRE);
                        output.accept(ModItems.CAMISETA_VELEZ);
                        output.accept(ModItems.CAMISETA_CENTRAL);


                    }).build());


    public static void register (IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
