package com.gremo.argentum.item.custom;

import com.gremo.argentum.Argentum;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;

public class ModArmorMaterials {
    // ⭐ DeferredRegister para ARMOR_MATERIAL (DEBE SER PUBLICO)
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
            DeferredRegister.create(Registries.ARMOR_MATERIAL, Argentum.MOD_ID);

    // ⭐ MÉTODO AUXILIAR para crear materiales con valores fijos
    private static DeferredHolder<ArmorMaterial, ArmorMaterial> registerRemeraMaterial(String name) {
        return ARMOR_MATERIALS.register(name, () -> {
            // Valores fijos para todas las camisetas
            EnumMap<ArmorItem.Type, Integer> protection = new EnumMap<>(ArmorItem.Type.class);
            protection.put(ArmorItem.Type.CHESTPLATE, 1);
            protection.put(ArmorItem.Type.BODY, 1);

            int enchantability = 16;
            float toughness = 0f;
            float knockbackResistance = 0f;

            List<ArmorMaterial.Layer> layers = List.of(
                    new ArmorMaterial.Layer(
                            ResourceLocation.fromNamespaceAndPath(Argentum.MOD_ID, name)
                    )
            );

            return new ArmorMaterial(
                    protection,
                    enchantability,
                    SoundEvents.ARMOR_EQUIP_GOLD,
                    () -> Ingredient.of(Items.LEATHER), // reparación
                    layers,
                    toughness,
                    knockbackResistance
            );
        });
    }

    // ⭐ TODOS LOS MATERIALES (una línea cada uno)
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> CAMISETA_ARGENTINA = registerRemeraMaterial("camiseta_argentina");
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> CAMISETA_ARGENTINA_ALTERNATIVA = registerRemeraMaterial("camiseta_argentina_alternativa");
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> CAMISETA_DIEGO = registerRemeraMaterial("camiseta_diego");
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> CAMISETA_RIVER = registerRemeraMaterial("camiseta_river");
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> CAMISETA_BOCA = registerRemeraMaterial("camiseta_boca");
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> CAMISETA_INDEPENDIENTE = registerRemeraMaterial("camiseta_independiente");
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> CAMISETA_ARGENTINA_94 = registerRemeraMaterial("camiseta_argentina_94");
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> CAMISETA_ARGENTINA_78 = registerRemeraMaterial("camiseta_argentina_78");
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> CAMISETA_RACING = registerRemeraMaterial("camiseta_racing");
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> CAMISETA_ARGENTINA_2022 = registerRemeraMaterial("camiseta_argentina_2022");
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> CAMISETA_ARGENTINOS_JR = registerRemeraMaterial("camiseta_argentinos_jr");
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> CAMISETA_ESTUDIANTES_PLATA = registerRemeraMaterial("camiseta_estudiantes_plata");
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> CAMISETA_PLATENSE = registerRemeraMaterial("camiseta_platense");
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> CAMISETA_SPREEN = registerRemeraMaterial("camiseta_spreen");
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> CAMISETA_TALLERES_CORDOBA = registerRemeraMaterial("camiseta_talleres_cordoba");
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> CAMISETA_TIGRE = registerRemeraMaterial("camiseta_tigre");
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> CAMISETA_VELEZ = registerRemeraMaterial("camiseta_velez");
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> CAMISETA_CENTRAL = registerRemeraMaterial("camiseta_central");
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> CAMISETA_TEST = registerRemeraMaterial("camiseta_test");
}