package com.gremo.argentum.datagen;

import com.gremo.argentum.Argentum;
import com.gremo.argentum.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {

    public ModBlockTagProvider(PackOutput output,
                               CompletableFuture<HolderLookup.Provider> lookupProvider,
                               ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Argentum.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        // ==========================================================
        // 🔨 HERRAMIENTAS
        // ==========================================================

        // --- Hacha ---
        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.JACARANDA_TRONCO.get())
                .add(ModBlocks.JACARANDA_COMPLETO.get())
                .add(ModBlocks.PELADO_JACARANDA_TRONCO.get())
                .add(ModBlocks.PELADO_JACARANDA_COMPLETO.get())
                .add(ModBlocks.JACARANDA_MADERA.get())
                .add(ModBlocks.JACARANDA_ESCALERAS.get())
                .add(ModBlocks.JACARANDA_LOSA.get())
                .add(ModBlocks.JACARANDA_VALLA.get())
                .add(ModBlocks.JACARANDA_PORTON.get())
                .add(ModBlocks.JACARANDA_BOTON.get())
                .add(ModBlocks.JACARANDA_PLACA_PRESION.get())
                .add(ModBlocks.JACARANDA_PUERTA.get())
                .add(ModBlocks.JACARANDA_TRAMPILLA.get())
                .add(ModBlocks.CEIBO_TRONCO.get())
                .add(ModBlocks.CEIBO_COMPLETO.get())
                .add(ModBlocks.PELADO_CEIBO_TRONCO.get())
                .add(ModBlocks.PELADO_CEIBO_COMPLETO.get())
                .add(ModBlocks.CEIBO_MADERA.get())
                .add(ModBlocks.CEIBO_ESCALERAS.get())
                .add(ModBlocks.CEIBO_LOSA.get())
                .add(ModBlocks.CEIBO_VALLA.get())
                .add(ModBlocks.CEIBO_PORTON.get())
                .add(ModBlocks.CEIBO_BOTON.get())
                .add(ModBlocks.CEIBO_PLACA_PRESION.get())
                .add(ModBlocks.CEIBO_PUERTA.get())
                .add(ModBlocks.CEIBO_TRAMPILLA.get())
                .add(ModBlocks.BARRIL_FERMENTO_TINTO.get())
                .add(ModBlocks.BARRIL_FERMENTO_BLANCO.get())
                .add(ModBlocks.BARRIL_FERMENTO_ROSADO.get())
                .add(ModBlocks.BOTELLERO_ABEDUL.get())
                .add(ModBlocks.BOTELLERO_ABETO.get())
                .add(ModBlocks.BOTELLERO_ACACIA.get())
                .add(ModBlocks.BOTELLERO_CARMESI.get())
                .add(ModBlocks.BOTELLERO_CEREZO.get())
                .add(ModBlocks.BOTELLERO_DISTORCIONADO.get())
                .add(ModBlocks.BOTELLERO_JUNGLA.get())
                .add(ModBlocks.BOTELLERO_MANGLAR.get())
                .add(ModBlocks.BOTELLERO_ROBLE.get())
                .add(ModBlocks.BOTELLERO_ROBLE_OSCURO.get())
                .add(ModBlocks.BOTELLERO_JACARANDA.get())
                .add(ModBlocks.BOTELLERO_CEIBO.get());

        // --- Pico ---
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.PRENSA_MOSTO.get())
                .add(ModBlocks.PRENSA_MOSTO_LISTA_TINTO.get())
                .add(ModBlocks.PRENSA_MOSTO_LISTA_BLANCO.get())
                .add(ModBlocks.PRENSA_MOSTO_LISTA_ROSADO.get())
                .add(ModBlocks.PRENSA_MOSTO_LISTA_TURBIO.get())
                .add(ModBlocks.ARCO_UNO.get())
                .add(ModBlocks.ARCO_DOS.get())
                .add(ModBlocks.ARCO_TRES.get())
                .add(ModBlocks.ARCO_CUATRO.get())
                .add(ModBlocks.ARCO_CINCO.get())
                .add(ModBlocks.ARCO_SEIS.get())
                .add(ModBlocks.ARCO_SIETE.get())
                .add(ModBlocks.ARCO_OCHO.get())
                .add(ModBlocks.ARCO_NUEVE.get())
                .add(ModBlocks.ARCO_DIEZ.get())
                .add(ModBlocks.ARCO_ONCE.get())
                .add(ModBlocks.ARCO_DOCE.get())
                .add(ModBlocks.ARCO_FULBO.get())
                .add(ModBlocks.ARCO_CATORCE.get())
                .add(ModBlocks.ARCO_QUINCE.get())
                .add(ModBlocks.COPA_MUNDO.get())
                .add(ModBlocks.COPA_AMERICA.get())
                .add(ModBlocks.UNO.get())
                .add(ModBlocks.DOS.get())
                .add(ModBlocks.TRES.get())
                .add(ModBlocks.CUATRO.get())
                .add(ModBlocks.CINCO.get())
                .add(ModBlocks.SEIS.get())
                .add(ModBlocks.BARRIL_FERMENTO_TINTO.get())   // Si quieres que también se rompa con pico
                .add(ModBlocks.BARRIL_FERMENTO_BLANCO.get())
                .add(ModBlocks.BARRIL_FERMENTO_ROSADO.get());

        // --- Azada ---
        tag(BlockTags.MINEABLE_WITH_HOE)
                .add(ModBlocks.JACARANDA_HOJAS.get())
                .add(ModBlocks.JACARANDA_PILA_HOJAS.get())
                .add(ModBlocks.CEIBO_HOJAS.get())
                .add(ModBlocks.CEIBO_PILA_HOJAS.get())
                .add(ModBlocks.YERBA_PLANTA.get())
                .add(ModBlocks.VID.get())
                .add(ModBlocks.VID_BLANCA.get())
                .add(ModBlocks.TE_PLANTA.get())
                .add(ModBlocks.MEMBRILLO_PLANTA.get())
                .add(ModBlocks.BATATA_PLANTA.get())
                .add(ModBlocks.YERBA_SILVESTRE.get())
                .add(ModBlocks.TE_SILVESTRE.get())
                .add(ModBlocks.MEMBRILLO_SILVESTRE.get())
                .add(ModBlocks.BATATA_SILVESTRE.get())
                .add(ModBlocks.VID_SILVESTRE.get())
                .add(ModBlocks.VID_BLANCA_SILVESTRE.get());

        // ==========================================================
        // 🌲 TAGS DE MADERA (para recetas, combustión, etc.)
        // ==========================================================

        tag(BlockTags.LOGS)
                .add(ModBlocks.JACARANDA_TRONCO.get())
                .add(ModBlocks.JACARANDA_COMPLETO.get())
                .add(ModBlocks.PELADO_JACARANDA_TRONCO.get())
                .add(ModBlocks.PELADO_JACARANDA_COMPLETO.get())
                .add(ModBlocks.CEIBO_TRONCO.get())
                .add(ModBlocks.CEIBO_COMPLETO.get())
                .add(ModBlocks.PELADO_CEIBO_TRONCO.get())
                .add(ModBlocks.PELADO_CEIBO_COMPLETO.get());

        tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.JACARANDA_TRONCO.get())
                .add(ModBlocks.JACARANDA_COMPLETO.get())
                .add(ModBlocks.PELADO_JACARANDA_TRONCO.get())
                .add(ModBlocks.PELADO_JACARANDA_COMPLETO.get())
                .add(ModBlocks.CEIBO_TRONCO.get())
                .add(ModBlocks.CEIBO_COMPLETO.get())
                .add(ModBlocks.PELADO_CEIBO_TRONCO.get())
                .add(ModBlocks.PELADO_CEIBO_COMPLETO.get());

        tag(BlockTags.PLANKS)
                .add(ModBlocks.JACARANDA_MADERA.get())
                .add(ModBlocks.CEIBO_MADERA.get());

        tag(BlockTags.LEAVES)
                .add(ModBlocks.JACARANDA_HOJAS.get())
                .add(ModBlocks.CEIBO_HOJAS.get());

        tag(BlockTags.SAPLINGS)
                .add(ModBlocks.JACARANDA_BROTE.get())
                .add(ModBlocks.CEIBO_BROTE.get());

        // ==========================================================
        // 🧱 ESTRUCTURAS DE MADERA (vallas, puertas, etc.)
        // ==========================================================

        tag(BlockTags.FENCES)
                .add(ModBlocks.JACARANDA_VALLA.get())
                .add(ModBlocks.CEIBO_VALLA.get());

        tag(BlockTags.WOODEN_FENCES)
                .add(ModBlocks.JACARANDA_VALLA.get())
                .add(ModBlocks.CEIBO_VALLA.get());

        tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.JACARANDA_PORTON.get())
                .add(ModBlocks.CEIBO_PORTON.get());

        tag(BlockTags.WOODEN_DOORS)
                .add(ModBlocks.JACARANDA_PUERTA.get())
                .add(ModBlocks.CEIBO_PUERTA.get());

        tag(BlockTags.WOODEN_TRAPDOORS)
                .add(ModBlocks.JACARANDA_TRAMPILLA.get())
                .add(ModBlocks.CEIBO_TRAMPILLA.get());

        tag(BlockTags.WOODEN_PRESSURE_PLATES)
                .add(ModBlocks.JACARANDA_PLACA_PRESION.get())
                .add(ModBlocks.CEIBO_PLACA_PRESION.get());

        tag(BlockTags.WOODEN_BUTTONS)
                .add(ModBlocks.JACARANDA_BOTON.get())
                .add(ModBlocks.CEIBO_BOTON.get());

        tag(BlockTags.STAIRS)
                .add(ModBlocks.JACARANDA_ESCALERAS.get())
                .add(ModBlocks.CEIBO_ESCALERAS.get());

        tag(BlockTags.SLABS)
                .add(ModBlocks.JACARANDA_LOSA.get())
                .add(ModBlocks.CEIBO_LOSA.get());

        // 🌾 Cultivos (para que los aldeanos granjeros interactúen)
        tag(BlockTags.CROPS)
                .add(ModBlocks.YERBA_PLANTA.get())
                .add(ModBlocks.TE_PLANTA.get())
                .add(ModBlocks.MEMBRILLO_PLANTA.get())
                .add(ModBlocks.BATATA_PLANTA.get())
                .add(ModBlocks.VID.get())
                .add(ModBlocks.VID_BLANCA.get());

// 🐝 Plantas que las abejas pueden polinizar
        tag(BlockTags.BEE_GROWABLES)
                .add(ModBlocks.YERBA_PLANTA.get())
                .add(ModBlocks.TE_PLANTA.get())
                .add(ModBlocks.MEMBRILLO_PLANTA.get())
                .add(ModBlocks.BATATA_PLANTA.get())
                .add(ModBlocks.VID.get())
                .add(ModBlocks.VID_BLANCA.get());

// 🌼 Flores (si tienes plantas decorativas con flores, agrégalas aquí)
// tag(BlockTags.FLOWERS)
//         .add(ModBlocks.TU_FLOR_DECORATIVA.get());

        // 🌸 Flores (para macetas)
        tag(BlockTags.SMALL_FLOWERS)
                .add(ModBlocks.JACARANDA_BROTE.get())
                .add(ModBlocks.CEIBO_BROTE.get());
    }
}