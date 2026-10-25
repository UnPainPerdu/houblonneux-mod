package com.unpainperdu.houblonneux.level.world.worldgen.feature.vegetation;

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
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractVegetationPatchFeature extends AbstractFeature
{
    protected final int spread;
    protected final int minFlowerNumber;
    protected final int maxFlowerNumber;
    protected final List<BlockStateProvider> states;
    protected final List<TagKey<Block>> groundAllowed;

    public AbstractVegetationPatchFeature(int spread, int minFlowerNumber, int maxFlowerNumber, List<BlockStateProvider> states, List<TagKey<Block>> groundAllowed)
    {
        super();
        this.spread = spread;
        this.minFlowerNumber = minFlowerNumber;
        this.maxFlowerNumber = maxFlowerNumber;
        this.states = states;
        this.groundAllowed = groundAllowed;
    }

    @Override
    public boolean canGenerate(WorldGenLevel worldGenLevel, ChunkGenerator chunkGenerator, RandomSource rand, BlockPos posOrigin)
    {
        return isValidPlacementLocation(worldGenLevel, posOrigin, this.groundAllowed);
    }

    @Override
    public void generate(WorldGenLevel worldGenLevel, ChunkGenerator chunkGenerator, RandomSource rand, BlockPos posOrigin)
    {
        ChunkPos originChunk = ChunkPos.containing(posOrigin);

        ArrayList<BlockPos> listPos = PosHelper.getRandomPosWithSameY(posOrigin, this.minFlowerNumber, this.maxFlowerNumber, this.spread, rand);
        listPos = PosHelper.setAllPosToTheGround(listPos, worldGenLevel);

        for (BlockPos pos1 : listPos)
        {
            ChunkPos placementChunk = ChunkPos.containing(pos1);
            if (isValidPlacementLocation(worldGenLevel, pos1, groundAllowed) && isInGeneratedChunks(originChunk, placementChunk))
            {
                placeFeature(worldGenLevel, chunkGenerator, rand, pos1);
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

    public abstract void placeFeature(WorldGenLevel worldGenLevel, ChunkGenerator chunkGenerator, RandomSource rand, BlockPos posToPlace);
}