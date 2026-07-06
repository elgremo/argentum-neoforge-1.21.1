package com.gremo.argentum.recipe;

import com.gremo.argentum.block.entity.ParrillaBlockEntity;
import com.gremo.argentum.item.ModItems;

public class RecetasParrilla {

    public static void register() {

        ParrillaBlockEntity.addRecipe(
                ModItems.BIFE_CRUDO.get(),
                ModItems.BIFE_ASADO.get(),
                220
        );

        ParrillaBlockEntity.addRecipe(
                ModItems.CHINCHULIN_CRUDO.get(),
                ModItems.CHINCHULIN_ASADO.get(),
                180
        );

        ParrillaBlockEntity.addRecipe(
                ModItems.COSTILLA_CRUDA.get(),
                ModItems.COSTILLA_ASADA.get(),
                240
        );

        ParrillaBlockEntity.addRecipe(
                ModItems.ENTRANA_CRUDA.get(),
                ModItems.ENTRANA_ASADA.get(),
                200
        );

        ParrillaBlockEntity.addRecipe(
                ModItems.LOMO_CRUDO.get(),
                ModItems.LOMO_ASADO.get(),
                220
        );

        ParrillaBlockEntity.addRecipe(
                ModItems.MATAMBRE_CRUDO.get(),
                ModItems.MATAMBRE_ASADO.get(),
                230
        );

        ParrillaBlockEntity.addRecipe(
                ModItems.MOLLEJA_CRUDA.get(),
                ModItems.MOLLEJA_ASADA.get(),
                200
        );
    }
}