package com.gremo.argentum.item.custom;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public class RemeraItem extends ArmorItem {
    private final String textureName;

    public RemeraItem(Holder<ArmorMaterial> material, Type type, Properties properties, String textureName) {
        super(material, type, properties);
        this.textureName = textureName;
    }

    public String getTextureName() {
        return textureName;
    }

    @Override
    public int getBarColor(ItemStack stack) {
        // Devuelve un color celeste (ARGB: 0xFF + RGB)
        return 0xFF00D4FF; // Celeste brillante
        // Podés probar con: 0xFF00BFFF (DeepSkyBlue) o 0xFF87CEEB (SkyBlue)
    }
}