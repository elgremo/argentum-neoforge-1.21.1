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

        tag(BlockTags.FENCES)
                .add(ModBlocks.JACARANDA_VALLA.get());

        tag(BlockTags.WOODEN_FENCES)
                .add(ModBlocks.JACARANDA_VALLA.get());

        tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.JACARANDA_PORTON.get());

        tag(BlockTags.WOODEN_DOORS)
                .add(ModBlocks.JACARANDA_PUERTA.get());

        tag(BlockTags.WOODEN_TRAPDOORS)
                .add(ModBlocks.JACARANDA_TRAMPILLA.get());

        tag(BlockTags.WOODEN_PRESSURE_PLATES)
                .add(ModBlocks.JACARANDA_PLACA_PRESION.get());

        tag(BlockTags.WOODEN_BUTTONS)
                .add(ModBlocks.JACARANDA_BOTON.get());

        tag(BlockTags.STAIRS)
                .add(ModBlocks.JACARANDA_ESCALERAS.get());

        tag(BlockTags.SLABS)
                .add(ModBlocks.JACARANDA_LOSA.get());

        tag(BlockTags.LOGS)
                .add(ModBlocks.JACARANDA_TRONCO.get())
                .add(ModBlocks.PELADO_JACARANDA_TRONCO.get());

        tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.JACARANDA_TRONCO.get())
                .add(ModBlocks.PELADO_JACARANDA_TRONCO.get());

        tag(BlockTags.PLANKS)
                .add(ModBlocks.JACARANDA_MADERA.get());
    }
}