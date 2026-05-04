package com.unpainperdu.houblonneux.level.world.worldgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public abstract class AbstractFeature<T extends FeatureConfiguration> extends Feature<T>
{
    public AbstractFeature(Codec<T> codec)
    {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<T> context)
    {
        if (canGenerate(context))
        {
            generate(context);
            return true;
        }
        else
        {
            return false;
        }
    }

    public abstract boolean canGenerate(FeaturePlaceContext<T> context);

    public abstract void generate(FeaturePlaceContext<T> context);

    public static boolean isInGeneratedChunks(ChunkPos originChunk, ChunkPos generationChunk)
    {
        return Math.abs(originChunk.x() - generationChunk.x()) < 2 && Math.abs(originChunk.z() - generationChunk.z()) < 2;
    }
}