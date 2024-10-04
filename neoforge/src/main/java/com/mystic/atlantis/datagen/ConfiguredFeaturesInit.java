package com.mystic.atlantis.datagen;

import com.mystic.atlantis.AtlantisForge;
import com.mystic.atlantis.blocks.base.PalmLeavesBlock;
import com.mystic.atlantis.blocks.base.PalmLog;
import com.mystic.atlantis.feature.FeaturesInit;
import com.mystic.atlantis.init.BlockInit;
import net.minecraft.core.Direction;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.AttachedToLeavesDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public class ConfiguredFeaturesInit {
    public static final ResourceKey<ConfiguredFeature<?, ?>> ANCIENT_METAL_CONFIGURED = AtlantisForge.configuredFeatureKey("ancient_metal_configured");
    public static final ResourceKey<ConfiguredFeature<?, ?>> AQUAMARINE_CONFIGURED = AtlantisForge.configuredFeatureKey("aquamarine_configured");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SUNKEN_GRAVEL_CONFIGURED = AtlantisForge.configuredFeatureKey("ore_sunken_gravel_configured");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SALT_ROCK_GEODE_CONFIGURED = AtlantisForge.configuredFeatureKey("salt_rock_geode_configured");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ATLANTEAN_PALM_TREE_CONFIGURED = AtlantisForge.configuredFeatureKey("atlantean_palm_tree_configured");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ATLANTEAN_TREE_CONFIGURED = AtlantisForge.configuredFeatureKey("atlantean_tree_configured");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ATLANTEAN_GLOWSTONES_CONFIGURED = AtlantisForge.configuredFeatureKey("atlantean_glowstones_configured");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SALTY_SEA_LAKE_CONFIGURED = AtlantisForge.configuredFeatureKey("salty_sea_lake_configured");
    public static final ResourceKey<ConfiguredFeature<?, ?>> UNDERWATER_FLOWER_CONFIGURED = AtlantisForge.configuredFeatureKey("underwater_flower_configured");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ATLANTEAN_VOLCANO_CONFiGURED = AtlantisForge.configuredFeatureKey("atlantean_volcano_configured");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ATLANTEAN_ISLANDS_CONFIGURED = AtlantisForge.configuredFeatureKey("atlantean_islands_configured");
    public static final ResourceKey<ConfiguredFeature<?, ?>> JETSTREAM_LAKE_CONFIGURED = AtlantisForge.configuredFeatureKey("jetstream_lake_configured");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHELL_BLOCK_CONFIGURED = AtlantisForge.configuredFeatureKey("shell_block_configured");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GARDEN_FOLIAGE_CONFIGURED = AtlantisForge.configuredFeatureKey("garden_foliage_configured");
    public static final ResourceKey<ConfiguredFeature<?, ?>> UNDERWATER_MUSHROOM_CONFIGURED = AtlantisForge.configuredFeatureKey("underwater_mushroom_configured");

    public ConfiguredFeaturesInit(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        registerOre(context, ANCIENT_METAL_CONFIGURED, BlockInit.ANCIENT_METAL_ORE, BlockInit.DEEPSLATE_ANCIENT_METAL_ORE, 12, 0);
        registerOre(context, AQUAMARINE_CONFIGURED, BlockInit.AQUAMARINE_ORE, BlockInit.AQUAMARINE_DEEPSLATE_ORE, 10, 0);
        registerOre(context, ORE_SUNKEN_GRAVEL_CONFIGURED, BlockInit.SUNKEN_GRAVEL, BlockInit.SUNKEN_GRAVEL, 40, 0);
        register(context, SALT_ROCK_GEODE_CONFIGURED, Feature.GEODE, new GeodeConfiguration(
                new GeodeBlockSettings(
                        BlockStateProvider.simple(BlockInit.SEA_SALT_CHUNK.get()),
                        BlockStateProvider.simple(BlockInit.SEA_SALT_CHUNK.get()),
                        BlockStateProvider.simple(BlockInit.SEA_SALT_CHUNK.get()),
                        BlockStateProvider.simple(Blocks.CALCITE),
                        BlockStateProvider.simple(BlockInit.CALCITE_BLOCK.get()),
                        List.of(BlockInit.SEA_SALT_CHUNK.get().defaultBlockState()),
                        BlockTags.ANCIENT_CITY_REPLACEABLE,
                        BlockTags.REDSTONE_ORES
                ), new GeodeLayerSettings(3.7, 4.2, 5.3, 6.2),
                new GeodeCrackSettings(0.95, 2, 2),
                0.35, 0.085, false,
                ConstantInt.of(1), ConstantInt.of(1), ConstantInt.ZERO,
                -16, 16, 0.05, 1
        ));
        register(context, ATLANTEAN_TREE_CONFIGURED, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(BlockInit.ATLANTEAN_LOGS.get()),
                new FancyTrunkPlacer(25, 20, 22),
                BlockStateProvider.simple(BlockInit.ATLANTEAN_LEAVES.get()),

                new RandomSpreadFoliagePlacer(ConstantInt.of(3), ConstantInt.of(3), ConstantInt.of(3), 256),
                Optional.empty(),
                new TwoLayersFeatureSize(1, 0, 1, OptionalInt.of(5)))
                        .dirt(BlockStateProvider.simple(BlockInit.SEABED.get())).ignoreVines().build()
        );

        register(context, ATLANTEAN_PALM_TREE_CONFIGURED, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(BlockInit.PALM_LOG.get().defaultBlockState().setValue(PalmLog.AXIS, Direction.Axis.Y)),
                new StraightTrunkPlacer(5, 2, 2),
                BlockStateProvider.simple(BlockInit.PALM_LEAVES.get().defaultBlockState().setValue(PalmLeavesBlock.DISTANCE, 7).setValue(PalmLeavesBlock.PERSISTENT, false).setValue(PalmLeavesBlock.WATERLOGGED, false)),
                new AcaciaFoliagePlacer(ConstantInt.of(3), ConstantInt.ZERO),
                Optional.empty(),
                new TwoLayersFeatureSize(1, 0, 1)).dirt(BlockStateProvider.simple(Blocks.SANDSTONE))
                        .decorators(List.of(new AttachedToLeavesDecorator(0.25f, 0, 0, BlockStateProvider.simple(BlockInit.COCONUT.get().defaultBlockState()), 1, List.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST))))
                .ignoreVines().build());
        registerNone(context, ATLANTEAN_GLOWSTONES_CONFIGURED, FeaturesInit.ATLANTEAN_GLOWSTONES_FEATURE);
        registerNone(context, ATLANTEAN_ISLANDS_CONFIGURED, FeaturesInit.ATLANTEAN_ISLANDS);
        registerNone(context, ATLANTEAN_VOLCANO_CONFiGURED, FeaturesInit.ATLANTEAN_VOLCANO);
        registerNone(context, UNDERWATER_FLOWER_CONFIGURED, FeaturesInit.UNDERWATER_FLOWER_ATLANTIS);
        registerNone(context, GARDEN_FOLIAGE_CONFIGURED, FeaturesInit.GARDEN_FOLIAGE_PLACER_ATLANTIS);
        registerLake(context, JETSTREAM_LAKE_CONFIGURED, BlockInit.JETSTREAM_WATER_BLOCK.get(), Blocks.STONE);
        registerNone(context, UNDERWATER_MUSHROOM_CONFIGURED, FeaturesInit.UNDERWATER_MUSHROOM_ATLANTIS);
        registerLake(context, SALTY_SEA_LAKE_CONFIGURED, BlockInit.SALTY_SEA_WATER_BLOCK.get(), Blocks.STONE);
        registerNone(context, SHELL_BLOCK_CONFIGURED, FeaturesInit.SHELL_BLOCK_FEATURE);

    }

    private static void registerLake(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, Block fluid, Block barrier) {
        register(context, key, Feature.LAKE, new LakeFeature.Configuration(BlockStateProvider.simple(fluid), BlockStateProvider.simple(barrier)));
    }

    private static <T extends Block, V extends Block> void registerOre(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, DeferredHolder<Block, T> regular, DeferredHolder<Block, V> deepslate, int size, int discardChanceOnAirExposure) {
        register(context, key, Feature.ORE, new OreConfiguration(List.of(
                OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), regular.get().defaultBlockState()),
                OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), deepslate.get().defaultBlockState())

        ), size, discardChanceOnAirExposure));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<FC, F>(feature, configuration));
    }

    private static <T extends Feature<NoneFeatureConfiguration>> void registerNone(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, DeferredHolder<Feature<?>, T> holder) {
        context.register(key, new ConfiguredFeature<NoneFeatureConfiguration, T>(holder.get(), FeatureConfiguration.NONE));
    }
}
