package com.gremo.argentum.datagen;

import com.gremo.argentum.block.ModBlocks;
import com.gremo.argentum.block.custom.MesaTrucoBlock;
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
        // Solo los MASTERS dropean ítem.
        for (String tinte : ModBlocks.TINTES) {
            DeferredBlock<MesaTrucoBlock>[] fam =
                    ModBlocks.getFamilia("mesa_truco_" + tinte);
            if (fam == null) continue;
            dropSelf(fam[0].get()); // solo el master
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        // Solo los masters son "conocidos" para el datagen
        List<Block> lista = new ArrayList<>();
        for (String tinte : ModBlocks.TINTES) {
            DeferredBlock<MesaTrucoBlock>[] fam =
                    ModBlocks.getFamilia("mesa_truco_" + tinte);
            if (fam != null) lista.add(fam[0].get());
        }
        return lista;
    }
}