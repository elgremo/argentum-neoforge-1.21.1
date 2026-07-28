package com.gremo.argentum.worldgen;

import com.gremo.argentum.Argentum;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;

public class ModBiomeModifiers {

    public static BiomeModifier addYerbaSilvestre() {
        return new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(),
                HolderSet.direct(),
                net.minecraft.world.level.levelgen.GenerationStep.Decoration.VEGETAL_DECORATION
        );
    }
}