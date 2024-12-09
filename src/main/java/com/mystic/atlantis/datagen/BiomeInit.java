package com.mystic.atlantis.datagen;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.init.AtlantisEntityInit;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.AquaticPlacements;
import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class BiomeInit {
    public static final ResourceKey<Biome> ATLANTEAN_GARDEN_KEY = key("atlantean_garden");
    public static final ResourceKey<Biome> ATLANTEAN_ISLANDS_BIOME_KEY = key("atlantean_islands_biome");
    public static final ResourceKey<Biome> ATLANTIS_BIOME_KEY = key("atlantis_biome");
    public static final ResourceKey<Biome> COCONUT_ISLES_KEY = key("coconut_isles");
    public static final ResourceKey<Biome> GOO_LAGOONS_KEY = key("goo_lagoons");
    public static final ResourceKey<Biome> JELLYFISH_FIELDS_KEY = key("jellyfish_fields");
    public static final ResourceKey<Biome> VOLCANIC_DARKSEA_KEY = key("volcanic_darksea");
    private final BootstapContext<Biome> context;

    public static ResourceKey<Biome> key(String name) {
        return ResourceKey.create(Registries.BIOME, Atlantis.id(name));
    }

    public BiomeInit(BootstapContext<Biome> context) {
        this.context = context;
        HolderGetter<PlacedFeature> holdergetter = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> holdergetter1 = context.lookup(Registries.CONFIGURED_CARVER);

        register(ATLANTEAN_GARDEN_KEY, new Biome.BiomeBuilder()
                .temperature(0.4f)
                .downfall(0.0f)
                .hasPrecipitation(false)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .skyColor(27571)
                        .fogColor(59110)
                        .waterColor(3386879)
                        .waterFogColor(59110)
                        .grassColorOverride(6749952)
                        .foliageColorOverride(5373696)
                        .ambientMoodSound(new AmbientMoodSettings(
                                Holder.direct(SoundEvents.AMBIENT_UNDERWATER_LOOP),
                                6000, 8, 2))
                        .build()
                )
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        .addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SQUID, 25, 2, 3))
                        .addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.CRAB.get(), 25, 2, 4))
                        .addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.STARFISH.get(), 25, 2, 2))
                        .addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.SEAHORSE.get(), 1, 1, 2))
                        .addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.JELLYFISH.get(), 25, 2, 4))
                        .addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.SHRIMP.get(), 25, 2, 3))
                        .addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.TROPICAL_FISH, 25, 4, 6))
                        .build()
                )
                .generationSettings(new BiomeGenerationSettings.Builder(holdergetter, holdergetter1)
                        .addFeature(GenerationStep.Decoration.LAKES, PlacedFeatureInit.JETSTREAM_LAKE_PLACED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_COPPER_LARGE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_ANDESITE_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_CLAY)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_COAL_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_DIAMOND_BURIED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_DIORITE_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GOLD_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GOLD)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GOLD_EXTRA)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GRANITE_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_IRON_MIDDLE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_LAPIS_BURIED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_REDSTONE_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_TUFF)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.ANCIENT_METAL_PLACED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.AQUAMARINE_PLACED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.ORE_SUNKEN_GRAVEL_PLACED)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEA_PICKLE)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.WARM_OCEAN_VEGETATION)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_DEEP_WARM)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_SIMPLE)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.UNDERWATER_FLOWER_PLACED)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.GARDEN_FOLIAGE_PLACED)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.ATLANTEAN_TREE_PLACED)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, PlacedFeatureInit.SHELL_BLOCK_PLACED)
                        .build()
                )
                .build());

        register(ATLANTEAN_ISLANDS_BIOME_KEY, new Biome.BiomeBuilder()
                .temperature(0.5f)
                .downfall(0.0f)
                .hasPrecipitation(false)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .skyColor(27571)
                        .fogColor(59110)
                        .waterColor(3386879)
                        .waterFogColor(59110)
                        .grassColorOverride(6749952)
                        .foliageColorOverride(5373696)
                        .ambientMoodSound(new AmbientMoodSettings(
                                Holder.direct(SoundEvents.AMBIENT_UNDERWATER_LOOP),
                                6000, 8, 2))
                        .build()
                )
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        //.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.SEA_SERPENT.get(), 25, 1, 2))
                        .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 25, 4, 6))
                        .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.STARFISH_ZOM.get(), 25, 2, 2))
                        .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 25, 4, 6))
                        .addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SQUID, 25, 2, 3))
                        .addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.CRAB.get(), 25, 2, 4))
                        .addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.STARFISH.get(), 25, 2, 2))
                        .addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.SEAHORSE.get(), 1, 1, 2))
                        .addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.JELLYFISH.get(), 25, 2, 4))
                        .addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.SHRIMP.get(), 25, 2, 3))
                        .addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.TROPICAL_FISH, 25, 4, 6))
                        .build()
                )
                .generationSettings(new BiomeGenerationSettings.Builder(holdergetter, holdergetter1)
                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, PlacedFeatureInit.ATLANTEAN_ISLANDS_PLACED)
                        .addFeature(GenerationStep.Decoration.LAKES, PlacedFeatureInit.JETSTREAM_LAKE_PLACED)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, PlacedFeatureInit.SALT_ROCK_GEODE_PLACED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_COPPER_LARGE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_ANDESITE_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_CLAY)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_COAL_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_DIAMOND_BURIED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_DIORITE_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GOLD_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GOLD)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GOLD_EXTRA)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GRANITE_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_IRON_MIDDLE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_LAPIS_BURIED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_REDSTONE_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_TUFF)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.ANCIENT_METAL_PLACED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.AQUAMARINE_PLACED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.ORE_SUNKEN_GRAVEL_PLACED)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEA_PICKLE)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.WARM_OCEAN_VEGETATION)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_DEEP_WARM)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_SIMPLE)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.UNDERWATER_FLOWER_PLACED)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, PlacedFeatureInit.SHELL_BLOCK_PLACED)
                        .build()
                )
                .build());

        register(ATLANTIS_BIOME_KEY, new Biome.BiomeBuilder()
                .temperature(0.5f)
                .downfall(0.0f)
                .hasPrecipitation(false)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .skyColor(27571)
                        .fogColor(59110)
                        .waterColor(3386879)
                        .waterFogColor(59110)
                        .grassColorOverride(6749952)
                        .foliageColorOverride(5373696)
                        .ambientMoodSound(new AmbientMoodSettings(
                                Holder.direct(SoundEvents.AMBIENT_UNDERWATER_LOOP),
                                6000, 8, 2))
                        .build()
                )
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        //.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.SEA_SERPENT.get(), 25, 1, 2))
                        .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 25, 2, 2))
                        .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.STARFISH_ZOM.get(), 25, 2, 2))
                        .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 25, 2, 2))
                        .addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SQUID, 25, 2, 2))
                        .addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.CRAB.get(), 25, 2, 2))
                        .addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.STARFISH.get(), 25, 2, 2))
                        .addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.SEAHORSE.get(), 1, 1, 2))
                        .addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.JELLYFISH.get(), 25, 2, 4))
                        .addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.SHRIMP.get(), 25, 2, 2))
                        .addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.TROPICAL_FISH, 25, 2, 2))
                        .build()
                )
                .generationSettings(new BiomeGenerationSettings.Builder(holdergetter, holdergetter1)
                        .addFeature(GenerationStep.Decoration.LAKES, PlacedFeatureInit.JETSTREAM_LAKE_PLACED)
                        .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, PlacedFeatureInit.SALT_ROCK_GEODE_PLACED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_COPPER_LARGE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_ANDESITE_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_CLAY)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_COAL_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_DIAMOND_BURIED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_DIORITE_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GOLD_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GOLD)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GOLD_EXTRA)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GRANITE_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_IRON_MIDDLE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_LAPIS_BURIED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_REDSTONE_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_TUFF)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.ANCIENT_METAL_PLACED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.AQUAMARINE_PLACED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.ORE_SUNKEN_GRAVEL_PLACED)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEA_PICKLE)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.WARM_OCEAN_VEGETATION)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_DEEP_WARM)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_SIMPLE)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.UNDERWATER_FLOWER_PLACED)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, PlacedFeatureInit.SHELL_BLOCK_PLACED)
                        .build()
                )
                .build());

        register(COCONUT_ISLES_KEY, new Biome.BiomeBuilder()
                .temperature(0.7f)
                .downfall(0.0f)
                .hasPrecipitation(false)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .skyColor(27571)
                        .fogColor(59110)
                        .waterColor(3386879)
                        .waterFogColor(59110)
                        .grassColorOverride(6749952)
                        .foliageColorOverride(5373696)
                        .ambientMoodSound(new AmbientMoodSettings(
                                Holder.direct(SoundEvents.AMBIENT_UNDERWATER_LOOP),
                                6000, 8, 2))
                        .build()
                )
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        //.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.SEA_SERPENT.get(), 25, 1, 2))
                        .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 25, 2, 2))
                        .addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SQUID, 25, 2, 2))
                        .addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.CRAB.get(), 25, 2, 2))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.COCONUT_CRAB.get(), 25, 2, 2))
                        .addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.STARFISH.get(), 25, 2, 2))
                        .addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.SEAHORSE.get(), 1, 1, 2))
                        .addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.JELLYFISH.get(), 25, 2, 4))
                        .addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.SHRIMP.get(), 25, 2, 2))
                        .addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.TROPICAL_FISH, 25, 2, 2))
                        .build()
                )
                .generationSettings(new BiomeGenerationSettings.Builder(holdergetter, holdergetter1)
                        .addFeature(GenerationStep.Decoration.LAKES, PlacedFeatureInit.JETSTREAM_LAKE_PLACED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_COPPER_LARGE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_ANDESITE_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_CLAY)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_COAL_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_DIAMOND_BURIED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_DIORITE_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GOLD_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GOLD)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GOLD_EXTRA)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GRANITE_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_IRON_MIDDLE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_LAPIS_BURIED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_REDSTONE_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_TUFF)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.ANCIENT_METAL_PLACED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.AQUAMARINE_PLACED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.ORE_SUNKEN_GRAVEL_PLACED)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEA_PICKLE)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.WARM_OCEAN_VEGETATION)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_DEEP_WARM)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_SIMPLE)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.UNDERWATER_FLOWER_PLACED)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.ATLANTEAN_PALM_TREE_PLACED)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, PlacedFeatureInit.SHELL_BLOCK_PLACED)
                        .build()
                )
                .build());

        register(GOO_LAGOONS_KEY, new Biome.BiomeBuilder()
                .temperature(0.5f)
                .downfall(0.0f)
                .hasPrecipitation(false)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .skyColor(27571)
                        .fogColor(59110)
                        .waterColor(3386879)
                        .waterFogColor(59110)
                        .grassColorOverride(6749952)
                        .foliageColorOverride(5373696)
                        .ambientMoodSound(new AmbientMoodSettings(
                                Holder.direct(SoundEvents.AMBIENT_UNDERWATER_LOOP),
                                6000, 8, 2))
                        .build()
                )
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        //.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.SEA_SERPENT.get(), 25, 1, 2))
                        .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 25, 2, 2))
                        .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 25, 2, 2))
                        .addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SQUID, 25, 2, 2))
                        .addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.CRAB.get(), 25, 2, 2))
                        .addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.STARFISH.get(), 25, 2, 2))
                        .addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.SEAHORSE.get(), 1, 1, 2))
                        .addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.JELLYFISH.get(), 25, 2, 4))
                        .addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.SHRIMP.get(), 25, 2, 2))
                        .addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.TROPICAL_FISH, 25, 2, 2))
                        .build()
                )
                .generationSettings(new BiomeGenerationSettings.Builder(holdergetter, holdergetter1)
                        .addFeature(GenerationStep.Decoration.LAKES, PlacedFeatureInit.SALTY_SEA_LAKE_PLACED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_COPPER_LARGE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_ANDESITE_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_CLAY)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_COAL_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_DIAMOND_BURIED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_DIORITE_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GOLD_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GOLD)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GOLD_EXTRA)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GRANITE_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_IRON_MIDDLE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_LAPIS_BURIED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_REDSTONE_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_TUFF)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.ANCIENT_METAL_PLACED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.AQUAMARINE_PLACED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.ORE_SUNKEN_GRAVEL_PLACED)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEA_PICKLE)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.WARM_OCEAN_VEGETATION)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_DEEP_WARM)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_SIMPLE)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.UNDERWATER_FLOWER_PLACED)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, PlacedFeatureInit.SHELL_BLOCK_PLACED)
                        .build()
                )
                .build());

        register(JELLYFISH_FIELDS_KEY, new Biome.BiomeBuilder()
                .temperature(0.5f)
                .downfall(0.0f)
                .hasPrecipitation(false)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .skyColor(27571)
                        .fogColor(59110)
                        .waterColor(3386879)
                        .waterFogColor(59110)
                        .grassColorOverride(6749952)
                        .foliageColorOverride(5373696)
                        .ambientMoodSound(new AmbientMoodSettings(
                                Holder.direct(SoundEvents.AMBIENT_UNDERWATER_LOOP),
                                6000, 8, 2))
                        .build()
                )
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        //.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.SEA_SERPENT.get(), 25, 1, 2))
                        .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 25, 2, 2))
                        .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.STARFISH_ZOM.get(), 25, 2, 2))
                        .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 25, 2, 2))
                        .addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SQUID, 25, 2, 2))
                        .addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.CRAB.get(), 25, 2, 2))
                        .addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.STARFISH.get(), 25, 2, 2))
                        .addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.SEAHORSE.get(), 1, 1, 2))
                        .addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.JELLYFISH.get(), 25, 2, 4))
                        .addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.SHRIMP.get(), 25, 2, 2))
                        .addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.TROPICAL_FISH, 25, 2, 2))
                        .addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.LEVIATHAN.get(), 25, 2, 2))
                        .build()
                )
                .generationSettings(new BiomeGenerationSettings.Builder(holdergetter, holdergetter1)
                        .addFeature(GenerationStep.Decoration.LAKES, PlacedFeatureInit.JETSTREAM_LAKE_PLACED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_COPPER_LARGE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_ANDESITE_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_CLAY)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_COAL_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_DIAMOND_BURIED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_DIORITE_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GOLD_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GOLD)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GOLD_EXTRA)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GRANITE_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_IRON_MIDDLE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_LAPIS_BURIED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_REDSTONE_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_TUFF)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.ANCIENT_METAL_PLACED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.AQUAMARINE_PLACED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.ORE_SUNKEN_GRAVEL_PLACED)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEA_PICKLE)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.WARM_OCEAN_VEGETATION)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_DEEP_WARM)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_SIMPLE)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.UNDERWATER_FLOWER_PLACED)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, PlacedFeatureInit.SHELL_BLOCK_PLACED)
                        .build()
                )
                .build());

        register(VOLCANIC_DARKSEA_KEY, new Biome.BiomeBuilder()
                .temperature(0.4f)
                .downfall(0.0f)
                .hasPrecipitation(false)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .skyColor(27571)
                        .fogColor(59110)
                        .waterColor(3386879)
                        .waterFogColor(59110)
                        .grassColorOverride(6749952)
                        .foliageColorOverride(5373696)
                        .ambientMoodSound(new AmbientMoodSettings(
                                Holder.direct(SoundEvents.AMBIENT_UNDERWATER_LOOP),
                                6000, 8, 2))
                        .build()
                )
                .mobSpawnSettings(new MobSpawnSettings.Builder()
                        //.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.SEA_SERPENT.get(), 25, 1, 2))
                        .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 25, 2, 2))
                        .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.STARFISH_ZOM.get(), 25, 2, 2))
                        .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 25, 2, 2))
                        .addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SQUID, 25, 2, 2))
                        .addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.CRAB.get(), 25, 2, 2))
                        .addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.STARFISH.get(), 25, 2, 2))
                        .addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.JELLYFISH.get(), 25, 2, 4))
                        .addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(AtlantisEntityInit.LEVIATHAN.get(), 25, 2, 2))
                        .addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.TROPICAL_FISH, 25, 2, 2))
                        .build()
                )
                .generationSettings(new BiomeGenerationSettings.Builder(holdergetter, holdergetter1)
                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, PlacedFeatureInit.ATLANTEAN_VOLCANO_PLACED)
                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, PlacedFeatureInit.ATLANTEAN_GLOWSTONES_PLACED)
                        .addFeature(GenerationStep.Decoration.LAKES, PlacedFeatureInit.JETSTREAM_LAKE_PLACED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_COPPER_LARGE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_ANDESITE_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_CLAY)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_COAL_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_DIAMOND_BURIED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_DIORITE_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GOLD_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GOLD)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GOLD_EXTRA)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GRANITE_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_IRON_MIDDLE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_LAPIS_BURIED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_REDSTONE_LOWER)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_TUFF)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.ANCIENT_METAL_PLACED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.AQUAMARINE_PLACED)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureInit.ORE_SUNKEN_GRAVEL_PLACED)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEA_PICKLE)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.WARM_OCEAN_VEGETATION)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_DEEP_WARM)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_SIMPLE)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatureInit.UNDERWATER_MUSHROOM_PLACED)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, CavePlacements.UNDERWATER_MAGMA)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, CavePlacements.FOSSIL_UPPER)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, PlacedFeatureInit.SHELL_BLOCK_PLACED)
                        .build()
                )
                .build());

    }

    private void register(ResourceKey<Biome> key, Biome biome) {
        context.register(key, biome);
    }
}
