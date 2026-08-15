package com.gremo.argentum.worldgen;

import com.gremo.argentum.Argentum;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;
import java.util.List;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> YERBA_PATCH =
            ResourceKey.create(
                    Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(
                            Argentum.MOD_ID,
                            "yerba_patch"));
    public static final ResourceKey<PlacedFeature> TE_PATCH =
            ResourceKey.create(
                    Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(
                            Argentum.MOD_ID,
                            "te_patch"));

    public static final ResourceKey<PlacedFeature> BATATA_PATCH =
            ResourceKey.create(
                    Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(
                            Argentum.MOD_ID,
                            "batata_patch"));

    public static final ResourceKey<PlacedFeature> MEMBRILLO_PATCH =
            ResourceKey.create(
                    Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(
                            Argentum.MOD_ID,
                            "membrillo_patch"));

    public static final ResourceKey<PlacedFeature> VID_PATCH =
            ResourceKey.create(
                    Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(
                            Argentum.MOD_ID,
                            "vid_patch"));

    public static final ResourceKey<PlacedFeature> JACARANDA =
            ResourceKey.create(
                    Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(
                            Argentum.MOD_ID,
                            "jacaranda"));

    public static final ResourceKey<PlacedFeature> CEIBO =
            ResourceKey.create(
                    Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(
                            Argentum.MOD_ID,
                            "ceibo"));

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {

        Holder<ConfiguredFeature<?, ?>> yerba =
                context.lookup(Registries.CONFIGURED_FEATURE)
                        .getOrThrow(ModConfiguredFeatures.YERBA_PATCH);
        Holder<ConfiguredFeature<?, ?>> te =
                context.lookup(Registries.CONFIGURED_FEATURE)
                        .getOrThrow(ModConfiguredFeatures.TE_PATCH);

        Holder<ConfiguredFeature<?, ?>> batata =
                context.lookup(Registries.CONFIGURED_FEATURE)
                        .getOrThrow(ModConfiguredFeatures.BATATA_PATCH);

        Holder<ConfiguredFeature<?, ?>> membrillo =
                context.lookup(Registries.CONFIGURED_FEATURE)
                        .getOrThrow(ModConfiguredFeatures.MEMBRILLO_PATCH);

        Holder<ConfiguredFeature<?, ?>> vid =
                context.lookup(Registries.CONFIGURED_FEATURE)
                        .getOrThrow(ModConfiguredFeatures.VID_PATCH);

        context.register(
                YERBA_PATCH,

                new PlacedFeature(
                        yerba,

                        List.of(
                                RarityFilter.onAverageOnceEvery(6),
                                InSquarePlacement.spread(),
                                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                                BiomeFilter.biome()
                        )
                )
        );
        context.register(
                TE_PATCH,
                new PlacedFeature(
                        te,
                        List.of(
                                RarityFilter.onAverageOnceEvery(6),
                                InSquarePlacement.spread(),
                                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                                BiomeFilter.biome()
                        )
                )
        );

        context.register(
                BATATA_PATCH,
                new PlacedFeature(
                        batata,
                        List.of(
                                RarityFilter.onAverageOnceEvery(6),
                                InSquarePlacement.spread(),
                                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                                BiomeFilter.biome()
                        )
                )
        );

        context.register(
                MEMBRILLO_PATCH,
                new PlacedFeature(
                        membrillo,
                        List.of(
                                RarityFilter.onAverageOnceEvery(6),
                                InSquarePlacement.spread(),
                                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                                BiomeFilter.biome()
                        )
                )
        );
        context.register(
                VID_PATCH,

                new PlacedFeature(
                        vid,

                        List.of(
                                RarityFilter.onAverageOnceEvery(1),
                                InSquarePlacement.spread(),
                                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                                BiomeFilter.biome()
                        )
                )
        );

        Holder<ConfiguredFeature<?, ?>> jacaranda =
                context.lookup(Registries.CONFIGURED_FEATURE)
                        .getOrThrow(ModConfiguredFeatures.JACARANDA_KEY);

        context.register(
                JACARANDA,

                new PlacedFeature(
                        jacaranda,

                        List.of(
                                RarityFilter.onAverageOnceEvery(8),
                                InSquarePlacement.spread(),
                                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                                BiomeFilter.biome()
                        )
                )
        );

        Holder<ConfiguredFeature<?, ?>> ceibo =
                context.lookup(Registries.CONFIGURED_FEATURE)
                        .getOrThrow(ModConfiguredFeatures.CEIBO_KEY);

        context.register(
                CEIBO,

                new PlacedFeature(
                        ceibo,

                        List.of(
                                RarityFilter.onAverageOnceEvery(8),
                                InSquarePlacement.spread(),
                                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                                BiomeFilter.biome()
                        )
                )
        );

    }
}