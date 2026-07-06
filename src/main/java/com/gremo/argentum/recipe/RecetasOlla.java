package com.gremo.argentum.recipe;

import com.gremo.argentum.block.entity.OllaBlockEntity;
import com.gremo.argentum.item.ModItems;

public class RecetasOlla {


    public static void register() {

        OllaBlockEntity.addRecipe(
                ModItems.CHURRO_CRUDO.get(),
                ModItems.CHURRO_FRITO.get(),
                60
        );

        OllaBlockEntity.addRecipe(
                ModItems.EMPANADA_CRUDA.get(),
                ModItems.EMPANADA_FRITA.get(),
                60
        );

        OllaBlockEntity.addRecipe(
                ModItems.MILANESA_CRUDA.get(),
                ModItems.MILANESA_FRITA.get(),
                100
        );

        OllaBlockEntity.addRecipe(
                ModItems.PASTELITO_BATATA_CRUDO.get(),
                ModItems.PASTELITO_BATATA_FRITO.get(),
                60
        );

        OllaBlockEntity.addRecipe(
                ModItems.PASTELITO_MEMBRILLO_CRUDO.get(),
                ModItems.PASTELITO_MEMBRILLO_FRITO.get(),
                60
        );

        OllaBlockEntity.addRecipe(
                ModItems.TORTAFRITA_CRUDA.get(),
                ModItems.TORTAFRITA.get(),
                60
        );
    }
}
