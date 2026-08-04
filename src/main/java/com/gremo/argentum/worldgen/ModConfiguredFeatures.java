package com.gremo.argentum.worldgen;

import com.gremo.argentum.Argentum;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import com.gremo.argentum.block.ModBlocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.MegaJungleTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

import static net.minecraft.data.worldgen.features.FeatureUtils.register;

public class ModConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> JACARANDA_KEY = registerKey("jacaranda");

    public static final ResourceKey<ConfiguredFeature<?, ?>> YERBA_PATCH =
            ResourceKey.create(
                    Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(
                            Argentum.MOD_ID,
                            "yerba_patch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> TE_PATCH =
            ResourceKey.create(
                    Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(
                            Argentum.MOD_ID,
                            "te_patch"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> BATATA_PATCH =
            ResourceKey.create(
                    Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(
                            Argentum.MOD_ID,
                            "batata_patch"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> VID_PATCH =
            ResourceKey.create(
                    Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(
                            Argentum.MOD_ID,
                            "vid_patch"));

    public static final ResourceKey<ConfiguredFeature<?, ?>> MEMBRILLO_PATCH =
            ResourceKey.create(
                    Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(
                            Argentum.MOD_ID,
                            "membrillo_patch"));

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {

        context.register(
                YERBA_PATCH,

                new ConfiguredFeature<>(
                        Feature.RANDOM_PATCH,

                        FeatureUtils.simpleRandomPatchConfiguration(
                                32,

                                PlacementUtils.onlyWhenEmpty(
                                        Feature.SIMPLE_BLOCK,

                                        new SimpleBlockConfiguration(
                                                BlockStateProvider.simple(
                                                        ModBlocks.YERBA_SILVESTRE.get()
                                                )
                                        )
                                )
                        )
                )
        );
        context.register(
                TE_PATCH,

                new ConfiguredFeature<>(
                        Feature.RANDOM_PATCH,

                        FeatureUtils.simpleRandomPatchConfiguration(
                                32,

                                PlacementUtils.onlyWhenEmpty(
                                        Feature.SIMPLE_BLOCK,

                                        new SimpleBlockConfiguration(
                                                BlockStateProvider.simple(
                                                        ModBlocks.TE_SILVESTRE.get()
                                                )
                                        )
                                )
                        )
                )
        );

        context.register(
                BATATA_PATCH,

                new ConfiguredFeature<>(
                        Feature.RANDOM_PATCH,

                        FeatureUtils.simpleRandomPatchConfiguration(
                                32,

                                PlacementUtils.onlyWhenEmpty(
                                        Feature.SIMPLE_BLOCK,

                                        new SimpleBlockConfiguration(
                                                BlockStateProvider.simple(
                                                        ModBlocks.BATATA_SILVESTRE.get()
                                                )
                                        )
                                )
                        )
                )
        );


        context.register(
                VID_PATCH,

                new ConfiguredFeature<>(
                        Feature.RANDOM_PATCH,

                        FeatureUtils.simpleRandomPatchConfiguration(
                                32,

                                PlacementUtils.onlyWhenEmpty(
                                        Feature.SIMPLE_BLOCK,

                                        new SimpleBlockConfiguration(
                                                BlockStateProvider.simple(
                                                        ModBlocks.VID_SILVESTRE.get()
                                                )
                                        )
                                )
                        )
                )
        );
        context.register(
                MEMBRILLO_PATCH,

                new ConfiguredFeature<>(
                        Feature.RANDOM_PATCH,

                        FeatureUtils.simpleRandomPatchConfiguration(
                                32,

                                PlacementUtils.onlyWhenEmpty(
                                        Feature.SIMPLE_BLOCK,

                                        new SimpleBlockConfiguration(
                                                BlockStateProvider.simple(
                                                        ModBlocks.MEMBRILLO_SILVESTRE.get()
                                                )
                                        )
                                )
                        )
                )
        );
        register(context, JACARANDA_KEY, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(ModBlocks.JACARANDA_TRONCO.get()),
                        new MegaJungleTrunkPlacer(3, 2, 1),

                        BlockStateProvider.simple(ModBlocks.JACARANDA_HOJAS.get()),
                        new BlobFoliagePlacer(
                                ConstantInt.of(3),
                                ConstantInt.of(0),
                                5),

                        new TwoLayersFeatureSize(1, 0, 2)
                ).ignoreVines().build());
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(
                Registries.CONFIGURED_FEATURE,
                ResourceLocation.fromNamespaceAndPath(Argentum.MOD_ID, name)
        );
    }
}
