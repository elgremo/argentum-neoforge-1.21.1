package com.gremo.argentum.datagen;

import com.gremo.argentum.Argentum;
import com.gremo.argentum.block.ModBlocks;
import com.gremo.argentum.item.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancements extends AdvancementProvider {

    public ModAdvancements(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, List.of(new ArgentumAdvancements()));
    }

    public static class ArgentumAdvancements implements AdvancementSubProvider {

        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver) {

            // ======================================================
            // ROOT
            // ======================================================
            AdvancementHolder root = Advancement.Builder.advancement()
                    .display(
                            ModItems.MATE.get(),
                            Component.literal("Argentum"),
                            Component.literal("La tradición argentina en Minecraft."),
                            // Cambia esto por tu textura personalizada:
                            ResourceLocation.fromNamespaceAndPath(Argentum.MOD_ID, "textures/gui/advancements/backgrounds/fondo.png"),
                            AdvancementType.TASK,
                            false, false, false
                    )
                    .addCriterion("tick", PlayerTrigger.TriggerInstance.tick())
                    .save(saver, Argentum.MOD_ID + ":root");

            // ======================================================
            // MATE
            // ======================================================
            AdvancementHolder mateRaiz = itemAdv(saver, root, "mate/raiz",
                    ModItems.YERBA.get(), "La Yerba Sagrada",
                    "Conseguí yerba mate recién cosechada.", AdvancementType.TASK);

            AdvancementHolder primerMate = itemAdv(saver, mateRaiz, "mate/primer_mate",
                    ModItems.MATE.get(), "Primer Mate",
                    "Cebate el primer mate del día.", AdvancementType.TASK);

            itemAdv(saver, primerMate, "mate/mate_imperial",
                    ModItems.MATE_IMPERIAL.get(), "Categoría Premium",
                    "Un mate imperial. Ahora sí, con clase.", AdvancementType.GOAL);

            itemAdv(saver, mateRaiz, "mate/termo",
                    ModItems.TERMO.get(), "El Termo No Se Presta",
                    "Un termo cargado es un amigo para toda la vida.", AdvancementType.TASK);

            itemAdv(saver, mateRaiz, "mate/pava",
                    ModItems.PAVA_CALIENTE.get(), "Esta el Agua!!",
                    "Agua caliente lista.", AdvancementType.TASK);

            // ======================================================
            // COMIDA
            // ======================================================
            AdvancementHolder comidaRaiz = itemAdv(saver, root, "comida/raiz",
                    ModItems.CUCHILLO.get(), "A Cocinar",
                    "Conseguí un cuchillo criollo.", AdvancementType.TASK);

            itemAdv(saver, comidaRaiz, "comida/empanada",
                    ModItems.EMPANADA_FRITA.get(), "Una Empanada No Alcanza",
                    "Fritá tu primera empanada.", AdvancementType.TASK);

            itemAdv(saver, comidaRaiz, "comida/milanesa",
                    ModItems.MILANESA_FRITA.get(), "La Milanesa Perfecta",
                    "Dorada, crocante, con limón.", AdvancementType.TASK);

            itemAdv(saver, comidaRaiz, "comida/choripan",
                    ModItems.CHORIPAN.get(), "Choripán de Cancha",
                    "La previa perfecta.", AdvancementType.TASK);

            itemAdv(saver, comidaRaiz, "comida/asado",
                    ModItems.COSTILLA_ASADA.get(), "Maestro Parrillero",
                    "El asado del domingo es sagrado.", AdvancementType.GOAL);

            itemAdv(saver, comidaRaiz, "comida/alfajor",
                    ModItems.ALFAJOR.get(), "El Postre",
                    "Más argentino imposible.", AdvancementType.GOAL);

            // ======================================================
            // VINO
            // ======================================================
            AdvancementHolder vinoRaiz = itemAdv(saver, root, "vino/raiz",
                    ModItems.UVA.get(), "Vendimia",
                    "Cosechá tu primera uva.", AdvancementType.TASK);

            AdvancementHolder mosto = itemAdv(saver, vinoRaiz, "vino/mosto",
                    ModItems.BALDE_MOSTO.get(), "El Mosto",
                    "Prensaste la uva. Ahora empieza la magia.", AdvancementType.TASK);

            AdvancementHolder botella = itemAdv(saver, mosto, "vino/botella",
                    ModItems.BOTELLA_VINO_TINTO_LLENA.get(), "Fermentación",
                    "Paciencia, que el vino se hace solo.", AdvancementType.GOAL);

            itemAdv(saver, botella, "vino/brindis",
                    ModItems.COPA_VINO_TINTO.get(), "¡Salud!",
                    "Un brindis como la gente.", AdvancementType.GOAL);

            // ======================================================
            // FAUNA
            // ======================================================
            AdvancementHolder faunaRaiz = itemAdv(saver, root, "fauna/raiz",
                    ModItems.TERO_SPAWN_EGG.get(), "Fauna Argentina",
                    "Descubrí los bichos autóctonos.", AdvancementType.TASK);

            itemAdv(saver, faunaRaiz, "fauna/tero",
                    ModItems.HUEVO_TERO.get(), "El Tero Guardián",
                    "Tero Tero Tero.", AdvancementType.TASK);

            itemAdv(saver, faunaRaiz, "fauna/hornero",
                    ModBlocks.NIDO.get().asItem(), "Hornero",
                    "1.000 pe.", AdvancementType.GOAL);

            // ======================================================
            // FULBO
            // ======================================================
            AdvancementHolder fulboRaiz = itemAdv(saver, root, "fulbo/raiz",
                    ModItems.PELOTA.get(), "Fulbo",
                    "La pelota no se mancha.", AdvancementType.TASK);

            itemAdv(saver, fulboRaiz, "fulbo/camiseta",
                    ModItems.CAMISETA_ARGENTINA.get(), "La Camiseta",
                    "Portando un legado.", AdvancementType.TASK);

            itemAdv(saver, fulboRaiz, "fulbo/copa_america",
                    ModBlocks.COPA_AMERICA.get().asItem(), "Copa América",
                    "Levantamos la Copa. Otra vez.", AdvancementType.GOAL);

            itemAdv(saver, fulboRaiz, "fulbo/libertadores",
                    ModBlocks.COPA_LIBERTADORES.get().asItem(), "La Gloria Eterna",
                    "La obsesión de todo club sudamericano.", AdvancementType.GOAL);

            itemAdv(saver, fulboRaiz, "fulbo/copa_mundo",
                    ModItems.COPA_MUNDO_ITEM.get(), "Campeones del Mundo",
                    "La tercera estrella. Muchachos...", AdvancementType.CHALLENGE);

            // ======================================================
            // CASINO
            // ======================================================
            AdvancementHolder casinoRaiz = itemAdv(saver, root, "casino/raiz",
                    ModItems.FICHA_CASINO_2.get(), "Bienvenido al Casino",
                    "Conseguí tu primera ficha.", AdvancementType.TASK);

            itemAdv(saver, casinoRaiz, "casino/dados",
                    ModItems.DADO.get(), "Los Dados Están Echados",
                    "Confiá en la suerte, nomás.", AdvancementType.TASK);

            itemAdv(saver, casinoRaiz, "casino/disco",
                    ModItems.MUCHACHOS_DISCO_MUSICA.get(), "Rock Nacional",
                    "Poné un disco y que suene fuerte.", AdvancementType.GOAL);
        }

        // ======================================================
        // HELPER: logro de item simple
        // ======================================================
        private static AdvancementHolder itemAdv(
                Consumer<AdvancementHolder> saver,
                AdvancementHolder parent,
                String path,
                ItemLike icon,
                String title,
                String description,
                AdvancementType type
        ) {
            return Advancement.Builder.advancement()
                    .parent(parent)
                    .display(
                            icon,
                            Component.literal(title),
                            Component.literal(description),
                            null,
                            type,
                            true,   // show_toast
                            true,   // announce_to_chat
                            false   // hidden
                    )
                    .addCriterion("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(icon))
                    .save(saver, Argentum.MOD_ID + ":" + path);
        }
    }
}