package com.mystic.atlantis.datagen;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.TagsInit;
import com.mystic.atlantis.biomes.BiomeInit;
import com.mystic.atlantis.biomes.ConfiguredFeaturesInit;
import com.mystic.atlantis.blocks.ancient_metal.TrailsGroup;
import com.mystic.atlantis.blocks.ancient_metal.WeatheringMetal;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.EnchantmentInit;
import com.mystic.atlantis.init.FluidInit;
import com.mystic.atlantis.init.ItemInit;
import com.mystic.atlantis.recipes.WritingRecipe;
import com.mystic.atlantis.util.Reference;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.recipes.*;
import net.minecraft.data.registries.RegistryPatchGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Providers {
    public static void init(IEventBus bus) {
        bus.addListener(Providers::dataGather);
    }

    public static void dataGather(GatherDataEvent event) {
        var output = event.getGenerator().getPackOutput();

        var registryProvider = new DatapackBuiltinEntriesProvider(output, RegistryPatchGenerator.createLookup(event.getLookupProvider(), new RegistrySetBuilder()
                .add(Registries.ENCHANTMENT, EnchantmentInit::bootstrap)
                .add(Registries.BIOME, BiomeInit::bootstrap)
                .add(Registries.CONFIGURED_FEATURE, ConfiguredFeaturesInit::bootstrap)),
                Set.of(Reference.MODID));

        event.getGenerator().addProvider(true, registryProvider);
        event.getGenerator().addProvider(true, new AtlantisBlockModelProvider(output, event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new AtlantisMainProvider(output, event.getExistingFileHelper(), AtlantisBlockStateProvider::new));
        event.getGenerator().addProvider(true, new AtlantisItemModelProvider(output, event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new AtlantisEnglishLanguageProvider(output));
        event.getGenerator().addProvider(true, new RecipeProvider(output, event.getLookupProvider()) {
            @Override
            protected void buildRecipes(RecipeOutput recipeOutput) {
                var list = ItemInit.getScrolls();
                var ingredient = Ingredient.of(list.toArray(Item[]::new));

                for (var result : list) {
                    glyphScroll(recipeOutput, result, ingredient);
                }

                for (WeatheringMetal.WeatherState state : WeatheringMetal.WeatherState.values()) {
                    var group = BlockInit.ANCIENT_METALS.get(state);
                    registerGroup(group, recipeOutput);

                    if (WeatheringMetal.WeatherState.UNAFFECTED == group.block().get().getAge()) {
                        SimpleCookingRecipeBuilder.smelting(Ingredient.of(BlockInit.RAW_ANCIENT_METAL_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, group.block().get(), 0.7F, 200)
                                .unlockedBy(getHasName(group.block().get()), has(group.block().get()))
                                .save(recipeOutput, Atlantis.id(group.block().get().getDescriptionId().replace("block.atlantis.", "") + "_smelting"));

                        SimpleCookingRecipeBuilder.blasting(Ingredient.of(BlockInit.RAW_ANCIENT_METAL_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS, group.block().get(), 0.7F, 100)
                                .unlockedBy(getHasName(group.block().get()), has(group.block().get()))
                                .save(recipeOutput, Atlantis.id(group.block().get().getDescriptionId().replace("block.atlantis.", "") + "_blasting"));
                    }
                }

                SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemInit.RAW_ANCIENT_METAL_INGOT.get()), RecipeCategory.BUILDING_BLOCKS, ItemInit.ANCIENT_METAL_INGOT.get(), 0.7F, 200)
                        .unlockedBy(getHasName(ItemInit.RAW_ANCIENT_METAL_INGOT.get()), has(ItemInit.RAW_ANCIENT_METAL_INGOT.get()))
                        .save(recipeOutput, Atlantis.id(ItemInit.ANCIENT_METAL_INGOT.get().getDescriptionId().replace("item.atlantis.", "") + "_smelting"));

                SimpleCookingRecipeBuilder.blasting(Ingredient.of(ItemInit.RAW_ANCIENT_METAL_INGOT.get()), RecipeCategory.BUILDING_BLOCKS, ItemInit.ANCIENT_METAL_INGOT.get(), 0.7F, 100)
                        .unlockedBy(getHasName(ItemInit.RAW_ANCIENT_METAL_INGOT.get()), has(ItemInit.RAW_ANCIENT_METAL_INGOT.get()))
                        .save(recipeOutput, Atlantis.id(ItemInit.ANCIENT_METAL_INGOT.get().getDescriptionId().replace("item.atlantis.", "") + "_blasting"));

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockInit.RAW_ANCIENT_METAL_BLOCK.get(), 1)
                        .pattern("###")
                        .pattern("###")
                        .pattern("###")
                        .define('#', ItemInit.RAW_ANCIENT_METAL_INGOT.get())
                        .unlockedBy(getHasName(ItemInit.RAW_ANCIENT_METAL_INGOT.get()), has(ItemInit.RAW_ANCIENT_METAL_INGOT.get()))
                        .save(recipeOutput, Atlantis.id(BlockInit.RAW_ANCIENT_METAL_BLOCK.get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));
            }

            private static void registerGroup(TrailsGroup group, RecipeOutput recipeOutput) {
                ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, group.bulb().get(), 1)
                        .pattern(" # ")
                        .pattern("#X#")
                        .pattern(" R ")
                        .define('#', group.block().get())
                        .define('X', Items.BLAZE_ROD)
                        .define('R', Items.REDSTONE)
                        .unlockedBy(getHasName(ItemInit.ANCIENT_METAL_INGOT.get()), has(ItemInit.ANCIENT_METAL_INGOT.get()))
                        .save(recipeOutput, Atlantis.id(group.bulb().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, group.block().get(), 1)
                        .pattern("###")
                        .pattern("###")
                        .pattern("###")
                        .define('#', group.block().get())
                        .unlockedBy(getHasName(ItemInit.ANCIENT_METAL_INGOT.get()), has(ItemInit.ANCIENT_METAL_INGOT.get()))
                        .save(recipeOutput, Atlantis.id(group.block().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, group.cut().get(), 4)
                        .pattern("##")
                        .pattern("##")
                        .define('#', group.block().get())
                        .unlockedBy(getHasName(ItemInit.ANCIENT_METAL_INGOT.get()), has(ItemInit.ANCIENT_METAL_INGOT.get()))
                        .save(recipeOutput, Atlantis.id(group.cut().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, group.cut_slab().get(), 6)
                        .pattern("###")
                        .define('#', group.cut().get())
                        .unlockedBy(getHasName(ItemInit.ANCIENT_METAL_INGOT.get()), has(ItemInit.ANCIENT_METAL_INGOT.get()))
                        .save(recipeOutput, Atlantis.id(group.cut_slab().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, group.cut_stairs().get(), 4)
                        .pattern("#  ")
                        .pattern("## ")
                        .pattern("###")
                        .define('#', group.cut().get())
                        .unlockedBy(getHasName(ItemInit.ANCIENT_METAL_INGOT.get()), has(ItemInit.ANCIENT_METAL_INGOT.get()))
                        .save(recipeOutput, Atlantis.id(group.cut_stairs().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, group.chiseled().get(), 1)
                        .pattern(" # ")
                        .pattern(" # ")
                        .pattern("   ")
                        .define('#', group.cut_slab().get())
                        .unlockedBy(getHasName(ItemInit.ANCIENT_METAL_INGOT.get()), has(ItemInit.ANCIENT_METAL_INGOT.get()))
                        .save(recipeOutput, Atlantis.id(group.chiseled().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

                ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, group.door().get(), 3)
                        .pattern("##")
                        .pattern("##")
                        .pattern("##")
                        .define('#', ItemInit.ANCIENT_METAL_INGOT.get())
                        .unlockedBy(getHasName(ItemInit.ANCIENT_METAL_INGOT.get()), has(ItemInit.ANCIENT_METAL_INGOT.get()))
                        .save(recipeOutput, Atlantis.id(group.door().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

                ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, group.trapdoor().get(), 2)
                        .pattern("###")
                        .pattern("###")
                        .define('#', ItemInit.ANCIENT_METAL_INGOT.get())
                        .unlockedBy(getHasName(ItemInit.ANCIENT_METAL_INGOT.get()), has(ItemInit.ANCIENT_METAL_INGOT.get()))
                        .save(recipeOutput, Atlantis.id(group.trapdoor().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, group.grate().get(), 3)
                        .pattern(" # ")
                        .pattern("# #")
                        .pattern(" # ")
                        .define('#', group.block().get())
                        .unlockedBy(getHasName(ItemInit.ANCIENT_METAL_INGOT.get()), has(ItemInit.ANCIENT_METAL_INGOT.get()))
                        .save(recipeOutput, Atlantis.id(group.grate().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

                ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, group.waxed_block().get(), 1)
                        .requires(group.block().get())
                        .requires(Items.HONEYCOMB)
                        .unlockedBy(getHasName(group.block().get()), has(group.block().get()))
                        .save(recipeOutput, Atlantis.id(group.waxed_block().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

                ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, group.waxed_cut().get(), 1)
                        .requires(group.cut().get())
                        .requires(Items.HONEYCOMB)
                        .unlockedBy(getHasName(group.cut().get()), has(group.cut().get()))
                        .save(recipeOutput, Atlantis.id(group.waxed_cut().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

                ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, group.waxed_chiseled().get(), 1)
                        .requires(group.chiseled().get())
                        .requires(Items.HONEYCOMB)
                        .unlockedBy(getHasName(group.chiseled().get()), has(group.chiseled().get()))
                        .save(recipeOutput, Atlantis.id(group.waxed_chiseled().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

                ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, group.waxed_cut_slab().get(), 1)
                        .requires(group.cut_slab().get())
                        .requires(Items.HONEYCOMB)
                        .unlockedBy(getHasName(group.cut_slab().get()), has(group.cut_slab().get()))
                        .save(recipeOutput, Atlantis.id(group.waxed_cut_slab().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

                ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, group.waxed_cut_stairs().get(), 1)
                        .requires(group.cut_stairs().get())
                        .requires(Items.HONEYCOMB)
                        .unlockedBy(getHasName(group.cut_stairs().get()), has(group.cut_stairs().get()))
                        .save(recipeOutput, Atlantis.id(group.waxed_cut_stairs().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

                ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, group.waxed_door().get(), 1)
                        .requires(group.door().get())
                        .requires(Items.HONEYCOMB)
                        .unlockedBy(getHasName(group.door().get()), has(group.door().get()))
                        .save(recipeOutput, Atlantis.id(group.waxed_door().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

                ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, group.waxed_trapdoor().get(), 1)
                        .requires(group.trapdoor().get())
                        .requires(Items.HONEYCOMB)
                        .unlockedBy(getHasName(group.trapdoor().get()), has(group.trapdoor().get()))
                        .save(recipeOutput, Atlantis.id(group.waxed_trapdoor().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

                ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, group.waxed_grate().get(), 1)
                        .requires(group.grate().get())
                        .requires(Items.HONEYCOMB)
                        .unlockedBy(getHasName(group.grate().get()), has(group.grate().get()))
                        .save(recipeOutput, Atlantis.id(group.waxed_grate().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));

                ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, group.waxed_bulb().get(), 1)
                        .requires(group.bulb().get())
                        .requires(Items.HONEYCOMB)
                        .unlockedBy(getHasName(group.bulb().get()), has(group.bulb().get()))
                        .save(recipeOutput, Atlantis.id(group.waxed_bulb().get().getDescriptionId().replace("block.atlantis.", "") + "_recipe"));
            }

            private static void glyphScroll(RecipeOutput recipeOutput, ItemLike result, Ingredient material) {
                writing(material, RecipeCategory.MISC, result, 1)
                        .unlockedBy(getHasName(ItemInit.LINGUISTIC_GLYPH_SCROLL.get()), has(ItemInit.LINGUISTIC_GLYPH_SCROLL.get()))
                        .save(recipeOutput, getConversionRecipeName(result, ItemInit.LINGUISTIC_GLYPH_SCROLL.get()) + "_writing");
            }

            public static SingleItemRecipeBuilder writing(Ingredient ingredient, RecipeCategory category, ItemLike result, int count) {
                return new SingleItemRecipeBuilder(category, WritingRecipe::new, ingredient, result, count);
            }
        });

        LootTableProvider.SubProviderEntry lootTableProvider = new LootTableProvider.SubProviderEntry(provider -> p_249643_ -> {
            for (TrailsGroup group : BlockInit.ANCIENT_METALS.values()) {
                dropSelf(group.block().get(), p_249643_);
                dropSelf(group.bulb().get(), p_249643_);
                dropSelf(group.grate().get(), p_249643_);
                dropSelf(group.cut().get(), p_249643_);
                dropSelf(group.cut_slab().get(), p_249643_);
                dropSelf(group.cut_stairs().get(), p_249643_);
                dropSelf(group.chiseled().get(), p_249643_);
                dropSelf(group.door().get(), p_249643_);
                dropSelf(group.trapdoor().get(), p_249643_);
                dropSelf(group.waxed_block().get(), p_249643_);
                dropSelf(group.waxed_bulb().get(), p_249643_);
                dropSelf(group.waxed_grate().get(), p_249643_);
                dropSelf(group.waxed_cut().get(), p_249643_);
                dropSelf(group.waxed_cut_slab().get(), p_249643_);
                dropSelf(group.waxed_cut_stairs().get(), p_249643_);
                dropSelf(group.waxed_chiseled().get(), p_249643_);
                dropSelf(group.waxed_door().get(), p_249643_);
                dropSelf(group.waxed_trapdoor().get(), p_249643_);
            }
            dropSelf(BlockInit.ANCIENT_METAL_ORE.get(), p_249643_);
            dropSelf(BlockInit.DEEPSLATE_ANCIENT_METAL_ORE.get(), p_249643_);
            dropSelf(BlockInit.RAW_ANCIENT_METAL_BLOCK.get(), p_249643_);
        }, LootContextParamSets.BLOCK);


        event.getGenerator().addProvider(true, new LootTableProvider(output, Set.of(), List.of(), event.getLookupProvider()) {
            @Override
            public @NotNull List<SubProviderEntry> getTables() {
                return List.of(lootTableProvider);
            }
        });

        BlockTagsProvider blockTagsProvider = new BlockTagsProvider(output, event.getLookupProvider(), "atlantis", event.getExistingFileHelper()) {
            @Override
            protected void addTags(HolderLookup.Provider pProvider) {
                tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BlockInit.ORICHALCUM_BLOCK.get());
                tag(BlockTags.NEEDS_IRON_TOOL).add(BlockInit.ORICHALCUM_BLOCK.get());
                for (TrailsGroup group : BlockInit.ANCIENT_METALS.values()) {
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(group.block().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(group.bulb().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(group.grate().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(group.cut().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(group.cut_slab().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(group.cut_stairs().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(group.chiseled().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(group.door().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(group.trapdoor().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(group.waxed_block().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(group.waxed_bulb().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(group.waxed_grate().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(group.waxed_cut().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(group.waxed_cut_slab().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(group.waxed_cut_stairs().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(group.waxed_chiseled().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(group.waxed_door().get());
                    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(group.waxed_trapdoor().get());
                    tag(BlockTags.NEEDS_IRON_TOOL).add(group.block().get());
                    tag(BlockTags.NEEDS_IRON_TOOL).add(group.bulb().get());
                    tag(BlockTags.NEEDS_IRON_TOOL).add(group.grate().get());
                    tag(BlockTags.NEEDS_IRON_TOOL).add(group.cut().get());
                    tag(BlockTags.NEEDS_IRON_TOOL).add(group.cut_slab().get());
                    tag(BlockTags.NEEDS_IRON_TOOL).add(group.cut_stairs().get());
                    tag(BlockTags.NEEDS_IRON_TOOL).add(group.chiseled().get());
                    tag(BlockTags.NEEDS_IRON_TOOL).add(group.door().get());
                    tag(BlockTags.NEEDS_IRON_TOOL).add(group.trapdoor().get());
                    tag(BlockTags.NEEDS_IRON_TOOL).add(group.waxed_block().get());
                    tag(BlockTags.NEEDS_IRON_TOOL).add(group.waxed_bulb().get());
                    tag(BlockTags.NEEDS_IRON_TOOL).add(group.waxed_grate().get());
                    tag(BlockTags.NEEDS_IRON_TOOL).add(group.waxed_cut().get());
                    tag(BlockTags.NEEDS_IRON_TOOL).add(group.waxed_cut_slab().get());
                    tag(BlockTags.NEEDS_IRON_TOOL).add(group.waxed_cut_stairs().get());
                    tag(BlockTags.NEEDS_IRON_TOOL).add(group.waxed_chiseled().get());
                    tag(BlockTags.NEEDS_IRON_TOOL).add(group.waxed_door().get());
                    tag(BlockTags.NEEDS_IRON_TOOL).add(group.waxed_trapdoor().get());
                }
                tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BlockInit.RAW_ANCIENT_METAL_BLOCK.get());
                tag(BlockTags.NEEDS_IRON_TOOL).add(BlockInit.RAW_ANCIENT_METAL_BLOCK.get());
                tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BlockInit.ANCIENT_METAL_ORE.get());
                tag(BlockTags.NEEDS_IRON_TOOL).add(BlockInit.ANCIENT_METAL_ORE.get());
                tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BlockInit.DEEPSLATE_ANCIENT_METAL_ORE.get());
                tag(BlockTags.NEEDS_IRON_TOOL).add(BlockInit.DEEPSLATE_ANCIENT_METAL_ORE.get());
                tag(BlockTags.MINEABLE_WITH_AXE).add(
                        BlockInit.PALM_BUTTON.value(),
                        BlockInit.PALM_DOOR.value(),
                        BlockInit.PALM_FENCE.value(),
                        BlockInit.PALM_FENCE_GATE.value(),
                        BlockInit.PALM_PRESSURE_PLATE.value(),
                        BlockInit.PALM_SIGN.value(),
                        BlockInit.PALM_SLAB.value(),
                        BlockInit.PALM_STAIRS.value(),
                        BlockInit.PALM_TRAPDOOR.value(),
                        BlockInit.PALM_WALL_SIGN.value(),
                        BlockInit.PALM_PLANKS.value(),
                        BlockInit.STRIPPED_PALM_LOG.value(),
                        BlockInit.STRIPPED_ATLANTEAN_LOG.value(),
                        BlockInit.COCONUT_SLICE.value(),
                        BlockInit.SATIRE_LANTERN.value(),
                        BlockInit.CARVED_COCONUT.value(),
                        BlockInit.COCONUT.value(),
                        BlockInit.PALM_LOG.value(),
                        BlockInit.ATLANTEAN_BUTTON.value(),
                        BlockInit.ATLANTEAN_DOOR.value(),
                        BlockInit.ATLANTEAN_FENCE.value(),
                        BlockInit.ATLANTEAN_FENCE_GATE.value(),
                        BlockInit.ATLANTEAN_PLANKS.value(),
                        BlockInit.ATLANTEAN_PRESSURE_PLATE.value(),
                        BlockInit.ATLANTEAN_SIGN.value(),
                        BlockInit.ATLANTEAN_SLAB.value(),
                        BlockInit.ATLANTEAN_STAIRS.value(),
                        BlockInit.ATLANTEAN_TRAPDOOR.value(),
                        BlockInit.ATLANTEAN_WALL_SIGN.value(),
                        BlockInit.ANCIENT_DARK_OAK_WOOD_MOSS_TRAPDOOR.value(),
                        BlockInit.ANCIENT_BIRCH_WOOD_MOSS_TRAPDOOR.value(),
                        BlockInit.ANCIENT_SPRUCE_WOOD_MOSS_TRAPDOOR.value(),
                        BlockInit.ANCIENT_JUNGLE_WOOD_MOSS_TRAPDOOR.value(),
                        BlockInit.ANCIENT_OAK_WOOD_MOSS_TRAPDOOR.value(),
                        BlockInit.ANCIENT_ACACIA_WOOD_MOSS_TRAPDOOR.value(),
                        BlockInit.ANCIENT_ACACIA_WOOD_MOSS_TRAPDOOR.value(),
                        BlockInit.ANCIENT_DARK_OAK_WOOD_MOSS_STAIRS.value(),
                        BlockInit.ANCIENT_BIRCH_WOOD_MOSS_STAIRS.value(),
                        BlockInit.ANCIENT_SPRUCE_WOOD_MOSS_STAIRS.value(),
                        BlockInit.ANCIENT_JUNGLE_WOOD_MOSS_STAIRS.value(),
                        BlockInit.ANCIENT_OAK_WOOD_MOSS_STAIRS.value(),
                        BlockInit.ANCIENT_ACACIA_WOOD_MOSS_STAIRS.value(),
                        BlockInit.ANCIENT_DARK_OAK_WOOD_MOSS_FENCE.value(),
                        BlockInit.ANCIENT_BIRCH_WOOD_MOSS_FENCE.value(),
                        BlockInit.ANCIENT_SPRUCE_WOOD_MOSS_FENCE.value(),
                        BlockInit.ANCIENT_JUNGLE_WOOD_MOSS_FENCE.value(),
                        BlockInit.ANCIENT_OAK_WOOD_MOSS_FENCE.value(),
                        BlockInit.ANCIENT_ACACIA_WOOD_MOSS_FENCE.value(),
                        BlockInit.ANCIENT_DARK_OAK_WOOD_MOSS_DOOR.value(),
                        BlockInit.ANCIENT_BIRCH_WOOD_MOSS_DOOR.value(),
                        BlockInit.ANCIENT_SPRUCE_WOOD_MOSS_DOOR.value(),
                        BlockInit.ANCIENT_JUNGLE_WOOD_MOSS_DOOR.value(),
                        BlockInit.ANCIENT_OAK_WOOD_MOSS_DOOR.value(),
                        BlockInit.ANCIENT_ACACIA_WOOD_MOSS_DOOR.value(),
                        BlockInit.ATLANTEAN_LOGS.value(),
                        BlockInit.ANCIENT_ACACIA_WOOD_MOSS.value(),
                        BlockInit.ANCIENT_OAK_WOOD_MOSS.value(),
                        BlockInit.ANCIENT_JUNGLE_WOOD_MOSS.value(),
                        BlockInit.ANCIENT_SPRUCE_WOOD_MOSS.value(),
                        BlockInit.ANCIENT_BIRCH_WOOD_MOSS.value(),
                        BlockInit.ANCIENT_DARK_OAK_WOOD_MOSS.value(),
                        BlockInit.ANCIENT_ACACIA_WOOD_MOSS_SLAB.value(),
                        BlockInit.ANCIENT_OAK_WOOD_MOSS_SLAB.value(),
                        BlockInit.ANCIENT_JUNGLE_WOOD_MOSS_SLAB.value(),
                        BlockInit.ANCIENT_SPRUCE_WOOD_MOSS_SLAB.value(),
                        BlockInit.ANCIENT_BIRCH_WOOD_MOSS_SLAB.value(),
                        BlockInit.ANCIENT_DARK_OAK_WOOD_MOSS_SLAB.value()
                );

                tag(BlockTags.MINEABLE_WITH_HOE).add(
                        BlockInit.ATLANTEAN_LEAVES.get(),
                        BlockInit.PALM_LEAVES.get(),
                        BlockInit.UNDERWATER_FLOWER.get(),
                        BlockInit.RED_UNDERWATER_FLOWER.get(),
                        BlockInit.PURPLE_GLOWING_MUSHROOM.get(),
                        BlockInit.YELLOW_GLOWING_MUSHROOM.get(),
                        BlockInit.YELLOW_UNDERWATER_FLOWER.get(),
                        BlockInit.ALGAE.get(),
                        BlockInit.ATLANTEAN_FIRE_MELON_FRUIT_SPIKED.get(),
                        BlockInit.ATLANTEAN_FIRE_MELON_FRUIT.get(),
                        BlockInit.ATLANTEAN_FIRE_MELON_STEM.get(),
                        BlockInit.ATLANTEAN_FIRE_MELON_TOP.get(),
                        BlockInit.ATLANTEAN_SAPLING.get(),
                        BlockInit.UNDERWATER_SHROOM_BLOCK.get(),
                        BlockInit.TUBER_UP_BLOCK.get(),
                        BlockInit.BLUE_LILY_BLOCK.get(),
                        BlockInit.BURNT_DEEP_BLOCK.get(),
                        BlockInit.ANEMONE_BLOCK.get(),
                        BlockInit.ALGAE_BLOCK.get()
                );

                tag(BlockTags.CLIMBABLE).add(BlockInit.ALGAE.value());

                tag(BlockTags.LEAVES).add(
                        BlockInit.PALM_LEAVES.value(),
                        BlockInit.ATLANTEAN_LEAVES.get()
                );

                tag(BlockTags.LOGS).add(
                        BlockInit.ATLANTEAN_LOGS.value(),
                        BlockInit.PALM_LOG.get()
                );

                tag(BlockTags.WOODEN_FENCES).add(
                        BlockInit.ANCIENT_BIRCH_WOOD_MOSS_FENCE.get(),
                        BlockInit.ANCIENT_ACACIA_WOOD_MOSS_FENCE.get(),
                        BlockInit.ANCIENT_JUNGLE_WOOD_MOSS_FENCE.get(),
                        BlockInit.ANCIENT_OAK_WOOD_MOSS_FENCE.get(),
                        BlockInit.ANCIENT_DARK_OAK_WOOD_MOSS_FENCE.get(),
                        BlockInit.ANCIENT_SPRUCE_WOOD_MOSS_FENCE.get(),
                        BlockInit.ATLANTEAN_FENCE.get()
                );
            }
        };

        FluidTagsProvider fluidTagsProvider = new FluidTagsProvider(output, event.getLookupProvider(), Reference.MODID, event.getExistingFileHelper()) {
            @Override
            protected void addTags(HolderLookup.Provider provider) {
                tag(FluidTags.WATER).add(
                        FluidInit.JETSTREAM_WATER.get(),
                        FluidInit.SALTY_SEA_WATER.get(),
                        FluidInit.FLOWING_SALTY_SEA_WATER.get(),
                        FluidInit.FLOWING_JETSTREAM_WATER.get()
                );
            }
        };

        BiomeTagsProvider biomeTagsProvider = new BiomeTagsProvider(output, event.getLookupProvider(), "atlantis", event.getExistingFileHelper()) {
            @Override
            protected void addTags(HolderLookup.Provider pProvider) {
                Stream.of(BiomeTags.IS_DEEP_OCEAN, BiomeTags.IS_OCEAN).map(this::tag).forEach(new Consumer<TagAppender<Biome>>() {
                    @Override
                    public void accept(TagAppender<Biome> appender) {
                        appender.add(BiomeInit.ATLANTEAN_GARDEN,
                                BiomeInit.ATLANTEAN_ISLANDS_BIOME,
                                BiomeInit.ATLANTIS_BIOME,
                                BiomeInit.GOO_LAGOONS,
                                BiomeInit.JELLYFISH_FIELDS,
                                BiomeInit.VOLCANIC_DARKSEA);
                    }
                });
            }
        };

        event.getGenerator().addProvider(true, blockTagsProvider);
        event.getGenerator().addProvider(true, fluidTagsProvider);
        event.getGenerator().addProvider(true, biomeTagsProvider);

        event.getGenerator().addProvider(true, new ItemTagsProvider(output, event.getLookupProvider(), blockTagsProvider.contentsGetter(), "atlantis", event.getExistingFileHelper()) {
            @Override
            protected void addTags(HolderLookup.Provider pProvider) {
                TagAppender<Item> tag = tag(TagsInit.Item.CAN_ITEM_SINK);
                TagsInit.Item.getItemsThatCanSink().stream().map(ItemLike::asItem).map(Item::builtInRegistryHolder).map(Holder.Reference::key).forEach(tag::add);

                tag(ItemTags.TRIMMABLE_ARMOR).add(
                        ItemInit.AQUAMARINE_BOOTS.get(),
                        ItemInit.AQUAMARINE_CHESTPLATE.get(),
                        ItemInit.AQUAMARINE_HELMET.get(),
                        ItemInit.AQUAMARINE_LEGGINGS.get(),
                        ItemInit.BROWN_WROUGHT_BOOTS.get(),
                        ItemInit.BROWN_WROUGHT_CHESTPLATE.get(),
                        ItemInit.BROWN_WROUGHT_HELMET.get(),
                        ItemInit.BROWN_WROUGHT_LEGGINGS.get(),
                        ItemInit.ORICHALCUM_BOOTS.get(),
                        ItemInit.ORICHALCUM_CHESTPLATE.get(),
                        ItemInit.ORICHALCUM_HELMET.get(),
                        ItemInit.ORICHALCUM_LEGGINGS.get()
                );
            }
        });
    }



    private static void dropSelf(Block block, BiConsumer<ResourceKey<LootTable>, LootTable.Builder> builder){
        builder.accept(block.getLootTable(), LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(block).when(() -> new LootItemCondition() {
            @Override
            public LootItemConditionType getType() {
                return LootItemConditions.SURVIVES_EXPLOSION;
            }

            @Override
            public boolean test(LootContext lootContext) {
                return true;
            }
        })).add(LootItem.lootTableItem(block))));
    }
}
