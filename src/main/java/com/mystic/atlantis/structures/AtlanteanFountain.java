package com.mystic.atlantis.structures;


import java.util.Optional;

import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasLookup;
import org.jetbrains.annotations.NotNull;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class AtlanteanFountain extends Structure {

    public static final Codec<AtlanteanFountain> CODEC = RecordCodecBuilder.<AtlanteanFountain>mapCodec(instance ->
            instance.group(AtlanteanFountain.settingsCodec(instance),
                    StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
                    ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.startJigsawName),
                    Codec.intRange(0, 30).fieldOf("size").forGetter(structure -> structure.size),
                    HeightProvider.CODEC.fieldOf("start_height").forGetter(structure -> structure.startHeight),
                    Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter(structure -> structure.projectStartToHeightmap),
                    Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistanceFromCenter)
            ).apply(instance, AtlanteanFountain::new)).codec();


    private final Holder<StructureTemplatePool> startPool;
    private final Optional<ResourceLocation> startJigsawName;
    private final int size;
    private final HeightProvider startHeight;
    private final Optional<Heightmap.Types> projectStartToHeightmap;
    private final int maxDistanceFromCenter;

    public AtlanteanFountain(Structure.StructureSettings config,
                            Holder<StructureTemplatePool> startPool,
                            Optional<ResourceLocation> startJigsawName,
                            int size,
                            HeightProvider startHeight,
                            Optional<Heightmap.Types> projectStartToHeightmap,
                            int maxDistanceFromCenter) {
        super(config);
        this.startPool = startPool;
        this.startJigsawName = startJigsawName;
        this.size = size;
        this.startHeight = startHeight;
        this.projectStartToHeightmap = projectStartToHeightmap;
        this.maxDistanceFromCenter = maxDistanceFromCenter;
    }

    @Override
    public @NotNull Optional<Structure.GenerationStub> findGenerationPoint(Structure.@NotNull GenerationContext context) {
        ChunkPos chunkPos = context.chunkPos();
        BlockPos blockPos = new BlockPos(chunkPos.getMinBlockX(), context.heightAccessor().getHeight() - context.chunkGenerator().getSeaLevel(), chunkPos.getMinBlockZ());

        Optional<Structure.GenerationStub> structurePiecesGenerator =
                JigsawPlacement.addPieces(
                        context,
                        this.startPool, this.startJigsawName, this.size, blockPos,
                        false, this.projectStartToHeightmap, this.maxDistanceFromCenter, PoolAliasLookup.EMPTY);
        return structurePiecesGenerator;
    }

    @Override
    public @NotNull StructureType<?> type() {
        return AtlantisStructures.ATLANTEAN_FOUNTAIN.get();
    }
}