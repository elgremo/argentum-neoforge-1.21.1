package com.gremo.argentum.item.custom;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

public class RemeraItem extends ArmorItem {

    private final String textureName;

    public RemeraItem(Holder<ArmorMaterial> material,
                      Type type,
                      Item.Properties properties,
                      String textureName) {
        super(material, type, properties);
        this.textureName = textureName;
    }

    public String getTextureName() {
        return textureName;
    }
}