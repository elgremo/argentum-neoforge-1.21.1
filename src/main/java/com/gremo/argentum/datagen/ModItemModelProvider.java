package com.gremo.argentum.datagen;

import com.gremo.argentum.Argentum;
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

    }
}