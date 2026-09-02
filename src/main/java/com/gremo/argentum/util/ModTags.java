package com.gremo.argentum.util;

import com.gremo.argentum.Argentum;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {
    public static class Items {
        public static final TagKey<Item> HUEVOS = create("huevos");

        private static TagKey<Item> create(String path) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Argentum.MOD_ID, path));
        }
    }
}