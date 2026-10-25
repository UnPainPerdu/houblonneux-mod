package com.unpainperdu.houblonneux.level.world.worldgen.feature.vegetation;


import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.world.block.block.crop.HopBlock;
import com.unpainperdu.houblonneux.level.world.block.blockstate.HopBlockstate;
import com.unpainperdu.houblonneux.register.block.ModBlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.ArrayList;
import java.util.List;

public class HopPatchFeature extends AbstractVegetationPatchFeature
{
    public static final MapCodec<HopPatchFeature> CODEC = RecordCodecBuilder.mapCodec(
            builder -> builder.group(
                            Codec.INT.fieldOf("spread").forGetter(g -> g.spread),
                            Codec.INT.fieldOf("min_flower_number").forGetter(g -> g.minFlowerNumber),
                            Codec.INT.fieldOf("max_flower_number").forGetter(g -> g.maxFlowerNumber),
                            Codec.list(BlockStateProvider.TYPED_CODEC).fieldOf("states").forGetter(g -> g.states),
                            Codec.list(TagKey.codec(Registries.BLOCK)).fieldOf("ground_allowed").forGetter(g -> g.groundAllowed)
                    )
                    .apply(builder, HopPatchFeature::new)
    );

    public HopPatchFeature(int spread, int minFlowerNumber, int maxFlowerNumber, List<BlockStateProvider> states, List<TagKey<Block>> groundAllowed)
    {
        super(spread, minFlowerNumber, maxFlowerNumber, states, groundAllowed);
    }

    @Override
    public MapCodec<HopPatchFeature> codec()
    {
        return CODEC;
    }

    @Override
    public void placeFeature(WorldGenLevel worldGenLevel, ChunkGenerator chunkGenerator, RandomSource rand, BlockPos posToPlace)
    {
        printWarnStateIfNeeded();

        int randomHeight = getRealHeight(worldGenLevel, posToPlace, rand.nextInt(3, HopBlock.MAX_HEIGHT) + 1);
        if (randomHeight > 2)
        {
            for (int i = 0; i < randomHeight; i++)
            {
                if (i == randomHeight - 1)
                {
                    placeTop(worldGenLevel, posToPlace);
                }
                else
                {
                    placeMiddle(worldGenLevel, posToPlace, rand);
                }
                posToPlace = posToPlace.above();
            }
        }
    }

    private int getRealHeight(WorldGenLevel worldIn, BlockPos footPos, int initialHeight)
    {
        List<BlockPos> blockPosList = new ArrayList<>();
        for (int i = 0; i < initialHeight; i++)
        {
            BlockPos currentPos = footPos.above(i);
            if (worldIn.getBlockState(currentPos).isAir())
            {
                blockPosList.add(currentPos);
            }
            else
            {
                break;
            }
        }
        return blockPosList.size();
    }

    private void placeMiddle(WorldGenLevel worldIn, BlockPos pos, RandomSource rand)
    {
        BlockState flowered = ModBlockRegister.HOP.get().defaultBlockState().setValue(HopBlock.HOP_BLOCKSTATE, HopBlockstate.MIDDLE_FLOWERED);
        BlockState basic = ModBlockRegister.HOP.get().defaultBlockState().setValue(HopBlock.HOP_BLOCKSTATE, HopBlockstate.MIDDLE_GROW);

        worldIn.setBlock(pos, rand.nextFloat() < 0.33 ? flowered : basic, 2);
    }

    private void placeTop(WorldGenLevel worldIn, BlockPos pos)
    {
        worldIn.setBlock(pos, ModBlockRegister.HOP.get().defaultBlockState().setValue(HopBlock.HOP_BLOCKSTATE, HopBlockstate.TOP_GROW), 2);
    }

    private void printWarnStateIfNeeded()
    {
        if (!this.states.isEmpty())
        {
            Houblonneux.LOGGER.warn("HopPatchFeature should not have states given, feature already call them in java code");
        }
    }
}