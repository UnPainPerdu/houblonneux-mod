package com.unpainperdu.houblonneux.level.world.worldgen.feature.vegetation;

import com.mojang.serialization.Codec;
import com.unpainperdu.houblonneux.level.world.worldgen.feature.AbstractFeature;
import com.unpainperdu.houblonneux.util.pos.PosHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractVegetationPatchFeature extends AbstractFeature<PatchConfiguration>
{
    public AbstractVegetationPatchFeature(Codec<PatchConfiguration> codec)
    {
        super(codec);
    }

    @Override
    public boolean canGenerate(FeaturePlaceContext<PatchConfiguration> context)
    {
        PatchConfiguration config = context.config();
        List<TagKey<Block>> groundAllowed = config.groundAllowed();

        WorldGenLevel worldIn = context.level();
        BlockPos pos = context.origin();

        return isValidPlacementLocation(worldIn, pos, groundAllowed);
    }

    @Override
    public void generate(FeaturePlaceContext<PatchConfiguration> context)
    {
        PatchConfiguration config = context.config();
        int spread = config.spread();
        int minFlowerNumber = config.minFlowerNumber();
        int maxFlowerNumber = config.maxFlowerNumber();
        List<TagKey<Block>> groundAllowed = config.groundAllowed();

        WorldGenLevel worldIn = context.level();
        RandomSource rand = context.random();
        BlockPos pos = context.origin();

        ChunkPos originChunk = ChunkPos.containing(pos);

        ArrayList<BlockPos> listPos = PosHelper.getRandomPosWithSameY(pos, minFlowerNumber, maxFlowerNumber, spread, rand);
        listPos = PosHelper.setAllPosToTheGround(listPos, worldIn);

        for (BlockPos pos1 : listPos)
        {
            ChunkPos placementChunk = ChunkPos.containing(pos1);
            if (isValidPlacementLocation(worldIn, pos1, groundAllowed) && isInGeneratedChunks(originChunk, placementChunk))
            {
                placeFeature(context, pos1);
            }
        }
    }

    private static boolean isValidPlacementLocation(LevelAccessor levelAccessor, BlockPos pos, List<TagKey<Block>> groundAllowed)
    {
        boolean isValidGround = false;
        Block block = levelAccessor.getBlockState(pos).getBlock();
        Block blockBelow = levelAccessor.getBlockState(pos.below()).getBlock();
        for (TagKey<Block> tag : groundAllowed)
        {
            if (blockBelow.defaultBlockState().is(tag))
            {
                isValidGround = true;
                break;
            }
        }
        return (block instanceof AirBlock) && isValidGround;
    }

    public abstract void placeFeature(FeaturePlaceContext<PatchConfiguration> context, BlockPos posToPlace);
}