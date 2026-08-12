package com.gremo.argentum.datagen;

import com.gremo.argentum.Argentum;
import com.gremo.argentum.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.minecraft.tags.ItemTags;
import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {

    public ModItemTagProvider(PackOutput output,
                              CompletableFuture<HolderLookup.Provider> lookupProvider,
                              CompletableFuture<TagLookup<net.minecraft.world.level.block.Block>> blockTags,
                              ExistingFileHelper existingFileHelper) {

        super(output, lookupProvider, blockTags, Argentum.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        copy(BlockTags.PLANKS, ItemTags.PLANKS);

        copy(BlockTags.LOGS, ItemTags.LOGS);

        copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);

        copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);

        copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);

        copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);

        copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);

        copy(BlockTags.FENCES, ItemTags.FENCES);

        copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);

        copy(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES);

        copy(BlockTags.STAIRS, ItemTags.STAIRS);

        copy(BlockTags.SLABS, ItemTags.SLABS);

        tag(ItemTags.SWORDS)
                .add(ModItems.CUCHILLO.get());
    }
}