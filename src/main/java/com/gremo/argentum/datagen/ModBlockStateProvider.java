package com.gremo.argentum.datagen;

import com.gremo.argentum.Argentum;
import com.gremo.argentum.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import com.gremo.argentum.block.custom.MesaTrucoBlock;
import com.gremo.argentum.block.custom.SillaBlock;

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

        axisBlock(
                (RotatedPillarBlock) ModBlocks.CEIBO_TRONCO.get(),
                modLoc("block/ceibo_tronco_side"),
                modLoc("block/ceibo_tronco_top")
        );

        axisBlock(
                (RotatedPillarBlock) ModBlocks.CEIBO_COMPLETO.get(),
                modLoc("block/ceibo_tronco_side"),
                modLoc("block/ceibo_tronco_side")
        );

        axisBlock(
                (RotatedPillarBlock) ModBlocks.PELADO_CEIBO_TRONCO.get(),
                modLoc("block/pelado_ceibo_tronco_side"),
                modLoc("block/pelado_ceibo_tronco_top")
        );

        axisBlock(
                (RotatedPillarBlock) ModBlocks.PELADO_CEIBO_COMPLETO.get(),
                modLoc("block/pelado_ceibo_tronco_side"),
                modLoc("block/pelado_ceibo_tronco_side")
        );

        blockItem(ModBlocks.CEIBO_TRONCO);
        blockItem(ModBlocks.CEIBO_COMPLETO);
        blockItem(ModBlocks.PELADO_CEIBO_TRONCO);
        blockItem(ModBlocks.PELADO_CEIBO_COMPLETO);
        blockWithItem(ModBlocks.CEIBO_MADERA);

        // =========================
        // CEIBO MADERA
        // =========================

        stairsBlock(
                (StairBlock) ModBlocks.CEIBO_ESCALERAS.get(),
                blockTexture(ModBlocks.CEIBO_MADERA.get())
        );
        blockItem(ModBlocks.CEIBO_ESCALERAS);

        slabBlock(
                (SlabBlock) ModBlocks.CEIBO_LOSA.get(),
                blockTexture(ModBlocks.CEIBO_MADERA.get()),
                blockTexture(ModBlocks.CEIBO_MADERA.get())
        );
        blockItem(ModBlocks.CEIBO_LOSA);

        models().fencePost(
                "ceibo_valla_post",
                blockTexture(ModBlocks.CEIBO_MADERA.get())
        );

        models().fenceSide(
                "ceibo_valla_side",
                blockTexture(ModBlocks.CEIBO_MADERA.get())
        );

        models().fenceInventory(
                "ceibo_valla_inventory",
                blockTexture(ModBlocks.CEIBO_MADERA.get())
        );

        fenceBlock(
                (FenceBlock) ModBlocks.CEIBO_VALLA.get(),
                blockTexture(ModBlocks.CEIBO_MADERA.get())
        );

        fenceGateBlock(
                (FenceGateBlock) ModBlocks.CEIBO_PORTON.get(),
                blockTexture(ModBlocks.CEIBO_MADERA.get())
        );
        blockItem(ModBlocks.CEIBO_PORTON);

        buttonBlock(
                (ButtonBlock) ModBlocks.CEIBO_BOTON.get(),
                blockTexture(ModBlocks.CEIBO_MADERA.get())
        );

        simpleBlockItem(
                ModBlocks.CEIBO_BOTON.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/ceibo_boton_inventory"))
        );

        pressurePlateBlock(
                (PressurePlateBlock) ModBlocks.CEIBO_PLACA_PRESION.get(),
                blockTexture(ModBlocks.CEIBO_MADERA.get())
        );
        blockItem(ModBlocks.CEIBO_PLACA_PRESION);

        doorBlockWithRenderType(
                (DoorBlock) ModBlocks.CEIBO_PUERTA.get(),
                modLoc("block/ceibo_puerta_abajo"),
                modLoc("block/ceibo_puerta_arriba"),
                "cutout"
        );

        trapdoorBlockWithRenderType(
                (TrapDoorBlock) ModBlocks.CEIBO_TRAMPILLA.get(),
                modLoc("block/ceibo_trampilla"),
                true,
                "cutout"
        );

        blockItem(ModBlocks.CEIBO_TRAMPILLA, "_bottom");

        wallBlock(
                (WallBlock) ModBlocks.CEIBO_MURO.get(),
                blockTexture(ModBlocks.CEIBO_MADERA.get())
        );
        blockItem(ModBlocks.CEIBO_MURO, "_inventory");

        wallBlock(
                (WallBlock) ModBlocks.JACARANDA_MURO.get(),
                blockTexture(ModBlocks.JACARANDA_MADERA.get())
        );
        blockItem(ModBlocks.JACARANDA_MURO, "_inventory");

        // ============================================================
        // MESAS Y SILLAS DE TRUCO
        // ============================================================
        registerMesasTruco();
        registerSillas();
    }

    // ============================================================
    // MESAS DE TRUCO
    // ============================================================
    private void mesaTrucoBlock(DeferredBlock<MesaTrucoBlock> block) {
        String name = block.getId().getPath();
        ModelFile model = new ModelFile.UncheckedModelFile(modLoc("block/" + name));
        horizontalBlock(block.get(), model);
    }

    private void registerMesasTruco() {
        for (String tinte : ModBlocks.TINTES) {
            String base = "mesa_truco_" + tinte;
            DeferredBlock<MesaTrucoBlock>[] fam = ModBlocks.getFamilia(base);
            if (fam == null) continue;

            mesaTrucoBlock(fam[0]);
            mesaTrucoBlock(fam[1]);
            mesaTrucoBlock(fam[2]);
            mesaTrucoBlock(fam[3]);
        }
    }

    // ============================================================
    // SILLAS
    // ============================================================
    private void sillaBlock(DeferredBlock<SillaBlock> block) {
        String name = block.getId().getPath();
        ModelFile model = new ModelFile.UncheckedModelFile(modLoc("block/" + name));
        horizontalBlock(block.get(), model);
    }

    private void registerSillas() {
        for (String madera : ModBlocks.TINTES) {
            String base = "silla_" + madera;
            DeferredBlock<SillaBlock>[] fam = ModBlocks.getFamiliaSilla(base);
            if (fam == null) continue;

            sillaBlock(fam[0]);
            sillaBlock(fam[1]);
            // NO blockItem: el item model lo tenés vos a mano
        }
    }

    // ============================================================
    // HELPERS
    // ============================================================
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