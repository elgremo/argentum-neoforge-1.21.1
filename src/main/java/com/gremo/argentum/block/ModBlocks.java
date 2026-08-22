package com.gremo.argentum.block;

import com.gremo.argentum.Argentum;
import com.gremo.argentum.block.custom.*;
import com.gremo.argentum.block.custom.OllaBlock;
import com.gremo.argentum.item.ModItems;
import com.gremo.argentum.worldgen.tree.ModTreeGrowers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.PushReaction;
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

    public static final DeferredBlock<Block> OLLA_FOGATA = BLOCKS.register("olla_fogata",
            () -> new OllaFogataBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .noOcclusion()
                    .lightLevel(state -> 13)
                    .sound(SoundType.NETHERITE_BLOCK)));

    public static final DeferredBlock<Block> PAVA_FOGATA_VACIA = BLOCKS.register("pava_fogata_vacia",
            () -> new PavaFogataBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .noOcclusion()
                    .randomTicks()
                    .lightLevel(state -> 13)
                    .sound(SoundType.NETHERITE_BLOCK)));
    public static final DeferredBlock<Block> PAVA_FOGATA_LLENA = BLOCKS.register("pava_fogata_llena",
            () -> new PavaFogataBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .noOcclusion()
                    .randomTicks()
                    .lightLevel(state -> 13)
                    .sound(SoundType.NETHERITE_BLOCK)));
    public static final DeferredBlock<Block> PAVA_FOGATA_CALENTANDO = BLOCKS.register("pava_fogata_calentando",
            () -> new PavaFogataBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .noOcclusion()
                    .randomTicks()
                    .lightLevel(state -> 13)
                    .sound(SoundType.NETHERITE_BLOCK)));
    public static final DeferredBlock<Block> PAVA_FOGATA_CALIENTE = BLOCKS.register("pava_fogata_caliente",
            () -> new PavaFogataBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .noOcclusion()
                    .randomTicks()
                    .lightLevel(state -> 13)
                    .sound(SoundType.NETHERITE_BLOCK)));


    public static final DeferredBlock<Block> YERBA_PLANTA = BLOCKS.register("yerba_planta",
            () -> new YerbaPlanta(BlockBehaviour.Properties.of()
                    .strength(0.0f)
                    .instabreak()
                    .noOcclusion()
                    .sound(SoundType.CROP)));

    public static final DeferredBlock<Block> VID = BLOCKS.register("vid",
            () -> new VidPlanta(BlockBehaviour.Properties.of()
                    .noCollission()
                    .instabreak()
                    .noOcclusion()
                    .sound(SoundType.CROP)
                    .randomTicks()));




    public static final DeferredBlock<Block> TE_PLANTA = BLOCKS.register("te_planta",
            () -> new TePlanta(BlockBehaviour.Properties.ofFullCopy(Blocks.BEETROOTS)));

    public static final DeferredBlock<Block> MEMBRILLO_PLANTA = BLOCKS.register("membrillo_planta",
            () -> new MembrilloPlanta(BlockBehaviour.Properties.ofFullCopy(Blocks.BEETROOTS)));

    public static final DeferredBlock<Block> BATATA_PLANTA = BLOCKS.register("batata_planta",
            () -> new BatataPlanta(BlockBehaviour.Properties.ofFullCopy(Blocks.BEETROOTS)));


    public static final DeferredBlock<Block> YERBA_SILVESTRE = BLOCKS.register("yerba_silvestre",
            () -> new YerbaSilvestrePlanta(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)));

    public static final DeferredBlock<Block> TE_SILVESTRE = BLOCKS.register("te_silvestre",
            () -> new TeSilvestrePlanta(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)));

    public static final DeferredBlock<Block> MEMBRILLO_SILVESTRE = BLOCKS.register("membrillo_silvestre",
            () -> new MembrilloSilvestrePlanta(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)));

    public static final DeferredBlock<Block> BATATA_SILVESTRE = BLOCKS.register("batata_silvestre",
            () -> new BatataSilvestrePlanta(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)));

    public static final DeferredBlock<Block> VID_SILVESTRE = BLOCKS.register("vid_silvestre",
            () -> new VidSilvestrePlanta(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)));



    public static final DeferredBlock<Block> PRENSA_MOSTO = registerBlock("prensa_mosto",
            () -> new PrensaMostoBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .noOcclusion()
                    .sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> PRENSA_MOSTO_LISTA = registerBlock("prensa_mosto_lista",
            () -> new PrensaMostoListaBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .noOcclusion()
                    .sound(SoundType.WOOD)));



    public static final DeferredBlock<Block> BARRIL_FERMENTO = registerBlock("barril_fermento",
            () -> new BarrilFermentoBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .noOcclusion()
                    .randomTicks()
                    .sound(SoundType.WOOD)));



    //-------BOTELLEROS--------------

    public static final DeferredBlock<Block> BOTELLERO_ABEDUL = registerBlock("botellero_abedul",
            () -> new BotelleroBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .noOcclusion()
                    .sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> BOTELLERO_ABETO = registerBlock("botellero_abeto",
            () -> new BotelleroBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .noOcclusion()
                    .sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> BOTELLERO_ACACIA = registerBlock("botellero_acacia",
            () -> new BotelleroBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .noOcclusion()
                    .sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> BOTELLERO_CARMESI = registerBlock("botellero_carmesi",
            () -> new BotelleroBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .noOcclusion()
                    .sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> BOTELLERO_CEREZO = registerBlock("botellero_cerezo",
            () -> new BotelleroBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .noOcclusion()
                    .sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> BOTELLERO_DISTORCIONADO = registerBlock("botellero_distorcionado",
            () -> new BotelleroBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .noOcclusion()
                    .sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> BOTELLERO_JUNGLA = registerBlock("botellero_jungla",
            () -> new BotelleroBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .noOcclusion()
                    .sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> BOTELLERO_MANGLAR = registerBlock("botellero_manglar",
            () -> new BotelleroBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .noOcclusion()
                    .sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> BOTELLERO_ROBLE = registerBlock("botellero_roble",
            () -> new BotelleroBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .noOcclusion()
                    .sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> BOTELLERO_ROBLE_OSCURO = registerBlock("botellero_roble_oscuro",
            () -> new BotelleroBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .noOcclusion()
                    .sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> BOTELLERO_JACARANDA = registerBlock("botellero_jacaranda",
            () -> new BotelleroBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .noOcclusion()
                    .sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> BOTELLERO_CEIBO = registerBlock("botellero_ceibo",
            () -> new BotelleroBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .noOcclusion()
                    .sound(SoundType.WOOD)));


    public static final DeferredBlock<Block> JACARANDA_TRONCO = registerBlock("jacaranda_tronco",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)));
    public static final DeferredBlock<Block> JACARANDA_COMPLETO = registerBlock("jacaranda_completo",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));
    public static final DeferredBlock<Block> PELADO_JACARANDA_TRONCO = registerBlock("pelado_jacaranda_tronco",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG)));
    public static final DeferredBlock<Block> PELADO_JACARANDA_COMPLETO = registerBlock("pelado_jacaranda_completo",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)));

    public static final DeferredBlock<Block> JACARANDA_MADERA = registerBlock("jacaranda_madera",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            });
    public static final DeferredBlock<Block> JACARANDA_PILA_HOJAS = registerBlock("jacaranda_pila_hojas",
            () -> new JacarandaLeavesPileBlock(BlockBehaviour.Properties.of()
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .pushReaction(PushReaction.DESTROY)
            ));

    public static final DeferredBlock<Block> JACARANDA_HOJAS = registerBlock("jacaranda_hojas",
            () -> new JacarandaLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_LEAVES))
    );
    public static final DeferredBlock<Block> JACARANDA_BROTE = registerBlock("jacaranda_brote",
            () -> new SaplingBlock(ModTreeGrowers.JACARANDA, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)));
    public static final DeferredBlock<Block> JACARANDA_ESCALERAS = registerBlock("jacaranda_escaleras",
            () -> new StairBlock(
                    Blocks.OAK_PLANKS.defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS)
            ));

    public static final DeferredBlock<Block> JACARANDA_LOSA = registerBlock("jacaranda_losa",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB)));

    public static final DeferredBlock<FenceBlock> JACARANDA_VALLA =
            registerBlock("jacaranda_valla",
                    () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE)));


    public static final DeferredBlock<Block> JACARANDA_PORTON = registerBlock("jacaranda_porton",
            () -> new FenceGateBlock(WoodType.OAK,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE)));

    public static final DeferredBlock<Block> JACARANDA_BOTON = registerBlock("jacaranda_boton",
            () -> new ButtonBlock(BlockSetType.OAK, 30,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON)));

    public static final DeferredBlock<Block> JACARANDA_PLACA_PRESION = registerBlock("jacaranda_placa_presion",
            () -> new PressurePlateBlock(BlockSetType.OAK,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE)));

    public static final DeferredBlock<Block> JACARANDA_PUERTA = registerBlock("jacaranda_puerta",
            () -> new DoorBlock(BlockSetType.OAK,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).noOcclusion()));

    public static final DeferredBlock<Block> JACARANDA_TRAMPILLA = registerBlock("jacaranda_trampilla",
            () -> new TrapDoorBlock(BlockSetType.OAK,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).noOcclusion()));


    public static final DeferredBlock<Block> CEIBO_TRONCO = registerBlock("ceibo_tronco",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)));

    public static final DeferredBlock<Block> CEIBO_COMPLETO = registerBlock("ceibo_completo",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));

    public static final DeferredBlock<Block> PELADO_CEIBO_TRONCO = registerBlock("pelado_ceibo_tronco",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG)));

    public static final DeferredBlock<Block> PELADO_CEIBO_COMPLETO = registerBlock("pelado_ceibo_completo",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)));

    public static final DeferredBlock<Block> CEIBO_MADERA = registerBlock("ceibo_madera",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            });

    public static final DeferredBlock<Block> CEIBO_PILA_HOJAS = registerBlock("ceibo_pila_hojas",
            () -> new CeiboLeavesPileBlock(BlockBehaviour.Properties.of()
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .pushReaction(PushReaction.DESTROY)
            ));

    public static final DeferredBlock<Block> CEIBO_HOJAS = registerBlock("ceibo_hojas",
            () -> new CeiboLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_LEAVES))
    );

    public static final DeferredBlock<Block> CEIBO_BROTE = registerBlock("ceibo_brote",
            () -> new SaplingBlock(ModTreeGrowers.CEIBO, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)));

    public static final DeferredBlock<Block> CEIBO_ESCALERAS = registerBlock("ceibo_escaleras",
            () -> new StairBlock(
                    Blocks.OAK_PLANKS.defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS)
            ));

    public static final DeferredBlock<Block> CEIBO_LOSA = registerBlock("ceibo_losa",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB)));

    public static final DeferredBlock<FenceBlock> CEIBO_VALLA =
            registerBlock("ceibo_valla",
                    () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE)));

    public static final DeferredBlock<Block> CEIBO_PORTON = registerBlock("ceibo_porton",
            () -> new FenceGateBlock(WoodType.OAK,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE)));

    public static final DeferredBlock<Block> CEIBO_BOTON = registerBlock("ceibo_boton",
            () -> new ButtonBlock(BlockSetType.OAK, 30,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON)));

    public static final DeferredBlock<Block> CEIBO_PLACA_PRESION = registerBlock("ceibo_placa_presion",
            () -> new PressurePlateBlock(BlockSetType.OAK,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE)));

    public static final DeferredBlock<Block> CEIBO_PUERTA = registerBlock("ceibo_puerta",
            () -> new DoorBlock(BlockSetType.OAK,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).noOcclusion()));

    public static final DeferredBlock<Block> CEIBO_TRAMPILLA = registerBlock("ceibo_trampilla",
            () -> new TrapDoorBlock(BlockSetType.OAK,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).noOcclusion()));



    public static final DeferredBlock<ArcoBlock> ARCO_UNO = registerBlockNoItem("arco_uno",
            () -> new ArcoBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .sound(SoundType.METAL)
                    .noOcclusion()));

    public static final DeferredBlock<ArcoBlock> ARCO_DOS = registerBlockNoItem("arco_dos",
            () -> new ArcoBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .sound(SoundType.METAL)
                    .noOcclusion()));

    public static final DeferredBlock<ArcoBlock> ARCO_TRES = registerBlockNoItem("arco_tres",
            () -> new ArcoBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .sound(SoundType.METAL)
                    .noOcclusion()));

    public static final DeferredBlock<ArcoBlock> ARCO_CUATRO = registerBlockNoItem("arco_cuatro",
            () -> new ArcoBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .sound(SoundType.METAL)
                    .noOcclusion()));

    public static final DeferredBlock<ArcoBlock> ARCO_CINCO = registerBlockNoItem("arco_cinco",
            () -> new ArcoBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .sound(SoundType.METAL)
                    .noOcclusion()));

    public static final DeferredBlock<ArcoBlock> ARCO_SEIS = registerBlockNoItem("arco_seis",
            () -> new ArcoBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .sound(SoundType.METAL)
                    .noOcclusion()));

    public static final DeferredBlock<ArcoBlock> ARCO_SIETE = registerBlockNoItem("arco_siete",
            () -> new ArcoBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .sound(SoundType.METAL)
                    .noOcclusion()));

    public static final DeferredBlock<ArcoBlock> ARCO_OCHO = registerBlockNoItem("arco_ocho",
            () -> new ArcoBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .sound(SoundType.METAL)
                    .noOcclusion()));

    public static final DeferredBlock<ArcoBlock> ARCO_NUEVE = registerBlockNoItem("arco_nueve",
            () -> new ArcoBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .sound(SoundType.METAL)
                    .noOcclusion()));

    public static final DeferredBlock<ArcoBlock> ARCO_DIEZ = registerBlockNoItem("arco_diez",
            () -> new ArcoBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .sound(SoundType.METAL)
                    .noOcclusion()));

    public static final DeferredBlock<ArcoBlock> ARCO_ONCE = registerBlockNoItem("arco_once",
            () -> new ArcoBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .sound(SoundType.METAL)
                    .noOcclusion()));

    public static final DeferredBlock<ArcoBlock> ARCO_DOCE = registerBlockNoItem("arco_doce",
            () -> new ArcoBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .sound(SoundType.METAL)
                    .noOcclusion()));

    public static final DeferredBlock<ArcoBlock> ARCO_FULBO = registerBlock(
            "arco_fulbo",
            () -> new ArcoBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .sound(SoundType.METAL)
                    .noOcclusion()));

    public static final DeferredBlock<ArcoBlock> ARCO_CATORCE = registerBlockNoItem("arco_catorce",
            () -> new ArcoBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .sound(SoundType.METAL)
                    .noOcclusion()));

    public static final DeferredBlock<ArcoBlock> ARCO_QUINCE = registerBlockNoItem("arco_quince",
            () -> new ArcoBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .sound(SoundType.METAL)
                    .noOcclusion()));


    public static final DeferredBlock<Block> NIDO = registerBlock("nido",
            () -> new NidoBlock(BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .strength(0.5f)));


    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
    private static <T extends Block> DeferredBlock<T> registerBlockNoItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    public static void register (IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
