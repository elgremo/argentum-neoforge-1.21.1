package com.gremo.argentum.datagen;

import com.gremo.argentum.Argentum;
import com.gremo.argentum.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Argentum.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent("jacaranda_valla", mcLoc("block/fence_inventory"))
                .texture("texture", modLoc("block/jacaranda_madera"));

        withExistingParent("jacaranda_boton", mcLoc("block/button_inventory"))
                .texture("texture", modLoc("block/jacaranda_madera"));

        withExistingParent("ceibo_valla", mcLoc("block/fence_inventory"))
                .texture("texture", modLoc("block/ceibo_madera"));

        withExistingParent("ceibo_boton", mcLoc("block/button_inventory"))
                .texture("texture", modLoc("block/ceibo_madera"));

        withExistingParent(ModItems.CHORRO_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.TERO_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.HORNERO_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.ZORRO_GRIS_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

    }
}