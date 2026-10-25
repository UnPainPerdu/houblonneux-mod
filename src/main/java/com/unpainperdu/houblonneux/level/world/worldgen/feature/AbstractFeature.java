package com.unpainperdu.houblonneux.level.world.worldgen.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;

public abstract class AbstractFeature implements Feature
{
    public AbstractFeature()
    {
    }

    @Override
    public boolean place(WorldGenLevel worldGenLevel, ChunkGenerator chunkGenerator, RandomSource rand, BlockPos posOrigin)
    {
        if (canGenerate(worldGenLevel, chunkGenerator, rand, posOrigin))
        {
            generate(worldGenLevel, chunkGenerator, rand, posOrigin);
            return true;
        }
        else
        {
            return false;
        }
    }

    public abstract boolean canGenerate(WorldGenLevel worldGenLevel, ChunkGenerator chunkGenerator, RandomSource rand, BlockPos posOrigin);

    public abstract void generate(WorldGenLevel worldGenLevel, ChunkGenerator chunkGenerator, RandomSource rand, BlockPos posOrigin);

    public static boolean isInGeneratedChunks(ChunkPos originChunk, ChunkPos generationChunk)
    {
        return Math.abs(originChunk.x() - generationChunk.x()) < 2 && Math.abs(originChunk.z() - generationChunk.z()) < 2;
    }
}