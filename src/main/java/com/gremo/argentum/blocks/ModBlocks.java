package com.gremo.argentum.blocks;

import com.gremo.argentum.Argentum;
import com.gremo.argentum.blocks.custom.*;
import com.gremo.argentum.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(Argentum.MOD_ID);

    public static final DeferredBlock<Block> UNO = registerBlock("uno",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(1f).sound(SoundType.AMETHYST)));
    public static final DeferredBlock<Block> DOS = registerBlock("dos",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(1f).sound(SoundType.AMETHYST)));
    public static final DeferredBlock<Block> TRES = registerBlock("tres",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(1f).sound(SoundType.AMETHYST)));
    public static final DeferredBlock<Block> CUATRO = registerBlock("cuatro",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(1f).sound(SoundType.AMETHYST)));
    public static final DeferredBlock<Block> CINCO = registerBlock("cinco",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(1f).sound(SoundType.AMETHYST)));
    public static final DeferredBlock<Block> SEIS = registerBlock("seis",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(1f).sound(SoundType.AMETHYST)));


    public static final DeferredBlock<Block> PARRILLA = registerBlock("parrilla",
            () -> new ParrillaBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .noOcclusion()
                    .sound(SoundType.NETHERITE_BLOCK)));
public static final DeferredBlock<Block> OLLA = registerBlock("olla",
            () -> new OllaBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .noOcclusion()
                    .sound(SoundType.NETHERITE_BLOCK)));

    public static final DeferredBlock<Block> YERBA_PLANTA = BLOCKS.register("yerba_planta",
        () -> new YerbaPlanta(BlockBehaviour.Properties.ofFullCopy(Blocks.BEETROOTS)));
    public static final DeferredBlock<Block> TE_PLANTA = BLOCKS.register("te_planta",
            () -> new TePlanta(BlockBehaviour.Properties.ofFullCopy(Blocks.BEETROOTS)));
    public static final DeferredBlock<Block> MEMBRILLO_PLANTA = BLOCKS.register("membrillo_planta",
            () -> new MembrilloPlanta(BlockBehaviour.Properties.ofFullCopy(Blocks.BEETROOTS)));
    public static final DeferredBlock<Block> BATATA_PLANTA = BLOCKS.register("batata_planta",
            () -> new BatataPlanta(BlockBehaviour.Properties.ofFullCopy(Blocks.BEETROOTS)));


    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register (IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
