package com.gremo.argentum.event;

import com.gremo.argentum.Argentum;
import com.gremo.argentum.block.ModBlocks;
import com.gremo.argentum.item.ModItems;
import com.gremo.argentum.villager.ModVillagers;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import java.util.List;

@EventBusSubscriber(modid = Argentum.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvents {

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        // ======================================================
        // VENDEDOR: TRADICIÓN (mate, comida, vino)
        // ======================================================
        if (event.getType() == ModVillagers.VENDEDOR.value()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // Nivel 1 - Novato
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 2),
                    new ItemStack(ModItems.YERBA_SEMILLA.get(), 1), 8, 2, 0.05f));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 2),
                    new ItemStack(ModItems.TE_SEMILLA.get(), 1), 8, 2, 0.05f));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ModItems.SAL.get(), 2), 12, 2, 0.05f));

            // Nivel 2 - Aprendiz
            trades.get(2).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 3),
                    new ItemStack(ModItems.BATATA.get(), 1), 8, 5, 0.05f));
            trades.get(2).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 4),
                    new ItemStack(ModItems.YERBA.get(), 2), 8, 5, 0.05f));
            trades.get(2).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 5),
                    new ItemStack(ModItems.PAQUETE_YERBA_MATE.get(), 1), 6, 5, 0.05f));

            // Nivel 3 - Oficial
            trades.get(3).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 6),
                    new ItemStack(ModItems.MATE_VACIO.get(), 1), 4, 10, 0.05f));
            trades.get(3).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 5),
                    new ItemStack(ModItems.BOMBILLA.get(), 1), 4, 10, 0.05f));
            trades.get(3).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 8),
                    new ItemStack(ModItems.CALABAZA_MATE.get(), 1), 4, 10, 0.05f));

            // Nivel 4 - Experto
            trades.get(4).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 10),
                    new ItemStack(ModItems.TERMO_VACIO.get(), 1), 3, 15, 0.05f));
            trades.get(4).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 12),
                    new ItemStack(ModItems.PAVA.get(), 1), 3, 15, 0.05f));
            trades.get(4).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 15),
                    new ItemStack(ModItems.MATE_LISTO_ARGENTO.get(), 1), 2, 15, 0.05f));

            // Nivel 5 - Maestro
            trades.get(5).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 20),
                    new ItemStack(ModItems.BOTELLA_VINO_TINTO_LLENA.get(), 1), 2, 20, 0.05f));
            trades.get(5).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 18),
                    new ItemStack(ModItems.EMPANADA_FRITA.get(), 2), 4, 20, 0.05f));
            trades.get(5).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 25),
                    new ItemStack(ModItems.ALFAJOR.get(), 2), 4, 20, 0.05f));
        }

        // ======================================================
        // VENDEDORB: FULBO
        // ======================================================
        if (event.getType() == ModVillagers.VENDEDORB.value()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // Nivel 1
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 8),
                    new ItemStack(ModItems.PELOTA.get(), 1), 4, 2, 0.05f));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 5),
                    new ItemStack(ModItems.GORRO_FANGIO.get(), 1), 6, 2, 0.05f));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 10),
                    new ItemStack(ModItems.CAMISETA.get(), 1), 4, 2, 0.05f));

            // Nivel 2
            trades.get(2).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 15),
                    new ItemStack(ModItems.PELOTA_TELSTAR.get(), 1), 3, 5, 0.05f));
            trades.get(2).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 15),
                    new ItemStack(ModItems.PELOTA_AZTECA.get(), 1), 3, 5, 0.05f));
            trades.get(2).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 20),
                    new ItemStack(ModItems.CAMISETA_ARGENTINA.get(), 1), 3, 5, 0.05f));

            // Nivel 3
            trades.get(3).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 25),
                    new ItemStack(ModItems.PELOTA_TEAMGEIST.get(), 1), 2, 10, 0.05f));
            trades.get(3).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 25),
                    new ItemStack(ModItems.PELOTA_JABULANI.get(), 1), 2, 10, 0.05f));
            trades.get(3).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 30),
                    new ItemStack(ModItems.CAMISETA_ARGENTINA_78.get(), 1), 2, 10, 0.05f));

            // Nivel 4
            trades.get(4).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 35),
                    new ItemStack(ModItems.PELOTA_AL_RIHLA.get(), 1), 2, 15, 0.05f));
            trades.get(4).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 40),
                    new ItemStack(ModItems.CAMISETA_DIEGO.get(), 1), 2, 15, 0.05f));
            trades.get(4).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 45),
                    new ItemStack(ModItems.CAMISETA_ARGENTINA_2022.get(), 1), 2, 15, 0.05f));

            // Nivel 5
            trades.get(5).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 50),
                    new ItemStack(ModBlocks.COPA_AMERICA.get(), 1), 1, 20, 0.05f));
            trades.get(5).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 50),
                    new ItemStack(ModBlocks.COPA_LIBERTADORES.get(), 1), 1, 20, 0.05f));
            trades.get(5).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 60),
                    new ItemStack(ModItems.COPA_MUNDO_ITEM.get(), 1), 1, 20, 0.05f));
        }

        // ======================================================
        // VENDEDORC: CASINO
        // ======================================================
        if (event.getType() == ModVillagers.VENDEDORC.value()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // Constante para trades "infinitos"
            final int INFINITO = 999999;

            // ==========================================
            // Nivel 1 - Novato (fichas chicas)
            // ==========================================
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ModItems.FICHA_CASINO_2.get(), 2), INFINITO, 2, 0f));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ModItems.FICHA_CASINO_4.get(), 1), INFINITO, 2, 0f));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 2),
                    new ItemStack(ModItems.FICHA_CASINO_8.get(), 1), INFINITO, 2, 0f));

            // ==========================================
            // Nivel 2 - Aprendiz (ficha mediana + escalado chico)
            // ==========================================
            trades.get(2).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 3),
                    new ItemStack(ModItems.FICHA_CASINO_16.get(), 1), INFINITO, 5, 0f));
            trades.get(2).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(ModItems.FICHA_CASINO_2.get(), 2),
                    new ItemStack(ModItems.FICHA_CASINO_4.get(), 1), INFINITO, 5, 0f));
            trades.get(2).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 5),
                    new ItemStack(ModItems.DADO.get(), 1), INFINITO, 5, 0f));

            // ==========================================
            // Nivel 3 - Oficial (ficha grande + escalado mediano + baraja)
            // ==========================================
            trades.get(3).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 5),
                    new ItemStack(ModItems.FICHA_CASINO_32.get(), 1), INFINITO, 10, 0f));
            trades.get(3).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(ModItems.FICHA_CASINO_4.get(), 2),
                    new ItemStack(ModItems.FICHA_CASINO_8.get(), 1), INFINITO, 10, 0f));
            trades.get(3).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(ModItems.FICHA_CASINO_8.get(), 2),
                    new ItemStack(ModItems.FICHA_CASINO_16.get(), 1), INFINITO, 10, 0f));
            trades.get(3).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 13),
                    new ItemStack(ModItems.BARAJA_SELLADA.get(), 1), INFINITO, 10, 0f));

            // ==========================================
            // Nivel 4 - Experto (ficha grande + escalado grande)
            // ==========================================
            trades.get(4).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 10),
                    new ItemStack(ModItems.FICHA_CASINO_64.get(), 1), INFINITO, 15, 0f));
            trades.get(4).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(ModItems.FICHA_CASINO_16.get(), 2),
                    new ItemStack(ModItems.FICHA_CASINO_32.get(), 1), INFINITO, 15, 0f));
            trades.get(4).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(ModItems.FICHA_CASINO_32.get(), 2),
                    new ItemStack(ModItems.FICHA_CASINO_64.get(), 1), INFINITO, 15, 0f));

            // ==========================================
            // Nivel 5 - Maestro (especial + discos)
            // ==========================================
            trades.get(5).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 20),
                    new ItemStack(ModItems.FICHA_CASINO_ESPECIAL.get(), 1), INFINITO, 30, 0f));
            trades.get(5).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(ModItems.FICHA_CASINO_64.get(), 2),
                    new ItemStack(ModItems.FICHA_CASINO_ESPECIAL.get(), 1), INFINITO, 30, 0f));

            // Discos de música (nivel maestro)
            trades.get(5).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 32),
                    new ItemStack(ModItems.MUCHACHOS_DISCO_MUSICA.get(), 1), INFINITO, 30, 0f));
            trades.get(5).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 32),
                    new ItemStack(ModItems.LA_CUARTA_DISCO_MUSICA.get(), 1), INFINITO, 30, 0f));
            trades.get(5).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 32),
                    new ItemStack(ModItems.ROSAROSA_DISCO_MUSICA.get(), 1), INFINITO, 30, 0f));
        }
    }
}