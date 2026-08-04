package com.gremo.argentum.datagen;

import com.gremo.argentum.Argentum;
import com.gremo.argentum.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Argentum.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        axisBlock(
                (RotatedPillarBlock) ModBlocks.JACARANDA_TRONCO.get(),
                modLoc("block/jacaranda_tronco_side"),
                modLoc("block/jacaranda_tronco_top")
        );

        axisBlock(
                (RotatedPillarBlock) ModBlocks.JACARANDA_COMPLETO.get(),
                modLoc("block/jacaranda_tronco_side"),
                modLoc("block/jacaranda_tronco_side")
        );

        axisBlock(
                (RotatedPillarBlock) ModBlocks.PELADO_JACARANDA_TRONCO.get(),
                modLoc("block/pelado_jacaranda_tronco_side"),
                modLoc("block/pelado_jacaranda_tronco_top")
        );

        axisBlock(
                (RotatedPillarBlock) ModBlocks.PELADO_JACARANDA_COMPLETO.get(),
                modLoc("block/pelado_jacaranda_tronco_side"),
                modLoc("block/pelado_jacaranda_tronco_side")
        );

        blockItem(ModBlocks.JACARANDA_TRONCO);
        blockItem(ModBlocks.JACARANDA_COMPLETO);
        blockItem(ModBlocks.PELADO_JACARANDA_TRONCO);
        blockItem(ModBlocks.PELADO_JACARANDA_COMPLETO);
        blockWithItem(ModBlocks.JACARANDA_MADERA);

        // =========================
// JACARANDA MADERA
// =========================

        stairsBlock(
                (StairBlock) ModBlocks.JACARANDA_ESCALERAS.get(),
                blockTexture(ModBlocks.JACARANDA_MADERA.get())
        );
        blockItem(ModBlocks.JACARANDA_ESCALERAS);

        slabBlock(
                (SlabBlock) ModBlocks.JACARANDA_LOSA.get(),
                blockTexture(ModBlocks.JACARANDA_MADERA.get()),
                blockTexture(ModBlocks.JACARANDA_MADERA.get())
        );
        blockItem(ModBlocks.JACARANDA_LOSA);

        models().fencePost(
                "jacaranda_valla_post",
                blockTexture(ModBlocks.JACARANDA_MADERA.get())
        );

        models().fenceSide(
                "jacaranda_valla_side",
                blockTexture(ModBlocks.JACARANDA_MADERA.get())
        );

        models().fenceInventory(
                "jacaranda_valla_inventory",
                blockTexture(ModBlocks.JACARANDA_MADERA.get())
        );

        fenceBlock(
                (FenceBlock) ModBlocks.JACARANDA_VALLA.get(),
                blockTexture(ModBlocks.JACARANDA_MADERA.get())
        );

        fenceGateBlock(
                (FenceGateBlock) ModBlocks.JACARANDA_PORTON.get(),
                blockTexture(ModBlocks.JACARANDA_MADERA.get())
        );
        blockItem(ModBlocks.JACARANDA_PORTON);

        buttonBlock(
                (ButtonBlock) ModBlocks.JACARANDA_BOTON.get(),
                blockTexture(ModBlocks.JACARANDA_MADERA.get())
        );

        simpleBlockItem(
                ModBlocks.JACARANDA_BOTON.get(),
                new ModelFile.UncheckedModelFile(mcLoc("item/button"))
        );

        simpleBlockItem(
                ModBlocks.JACARANDA_BOTON.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/jacaranda_boton_inventory"))
        );

        pressurePlateBlock(
                (PressurePlateBlock) ModBlocks.JACARANDA_PLACA_PRESION.get(),
                blockTexture(ModBlocks.JACARANDA_MADERA.get())
        );
        blockItem(ModBlocks.JACARANDA_PLACA_PRESION);

        doorBlockWithRenderType(
                (DoorBlock) ModBlocks.JACARANDA_PUERTA.get(),
                modLoc("block/jacaranda_puerta_abajo"),
                modLoc("block/jacaranda_puerta_arriba"),
                "cutout"
        );

        trapdoorBlockWithRenderType(
                (TrapDoorBlock) ModBlocks.JACARANDA_TRAMPILLA.get(),
                modLoc("block/jacaranda_trampilla"),
                true,
                "cutout"
        );

        blockItem(ModBlocks.JACARANDA_TRAMPILLA, "_bottom");
    }

    private void blockWithItem(DeferredBlock<? extends Block> block) {
        simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }

    private void blockItem(DeferredBlock<? extends Block> block) {
        simpleBlockItem(block.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/" + block.getId().getPath())));
    }

    private void blockItem(DeferredBlock<? extends Block> block, String suffix) {
        simpleBlockItem(block.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/" + block.getId().getPath() + suffix)));
    }
}