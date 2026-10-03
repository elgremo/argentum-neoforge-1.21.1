package com.gremo.argentum.datagen;

import com.gremo.argentum.block.ModBlocks;
import com.gremo.argentum.block.custom.MesaTrucoBlock;
import com.gremo.argentum.block.custom.SillaBlock;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {

    protected ModBlockLootTables(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        // ============================================================
        // MESAS DE TRUCO (solo los MASTERS dropean)
        // ============================================================
        for (String tinte : ModBlocks.TINTES) {
            DeferredBlock<MesaTrucoBlock>[] fam =
                    ModBlocks.getFamilia("mesa_truco_" + tinte);
            if (fam == null) continue;
            dropSelf(fam[0].get());
        }

        // ============================================================
        // SILLAS (solo los MASTERS dropean)
        // ============================================================
        for (String madera : ModBlocks.TINTES) {
            DeferredBlock<SillaBlock>[] fam =
                    ModBlocks.getFamiliaSilla("silla_" + madera);
            if (fam == null) continue;
            dropSelf(fam[0].get());
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        List<Block> lista = new ArrayList<>();

        // Mesas
        for (String tinte : ModBlocks.TINTES) {
            DeferredBlock<MesaTrucoBlock>[] fam =
                    ModBlocks.getFamilia("mesa_truco_" + tinte);
            if (fam != null) lista.add(fam[0].get());
        }

        // Sillas
        for (String madera : ModBlocks.TINTES) {
            DeferredBlock<SillaBlock>[] fam =
                    ModBlocks.getFamiliaSilla("silla_" + madera);
            if (fam != null) lista.add(fam[0].get());
        }

        return lista;
    }
}