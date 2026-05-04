package com.unpainperdu.houblonneux.level.world.worldgen.feature.vegetation;


import com.mojang.serialization.Codec;
import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.world.block.block.crop.HopBlock;
import com.unpainperdu.houblonneux.level.world.block.blockstate.HopBlockstate;
import com.unpainperdu.houblonneux.register.block.ModBlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.ArrayList;
import java.util.List;

public class HopPatchFeature extends AbstractVegetationPatchFeature
{
    public HopPatchFeature(Codec<PatchConfiguration> codec)
    {
        super(codec);
    }

    @Override
    public void placeFeature(FeaturePlaceContext<PatchConfiguration> context, BlockPos posToPlace)
    {
        printWarnStateIfNeeded(context.config());
        RandomSource rand = context.random();
        WorldGenLevel worldIn = context.level();

        int randomHeight = getRealHeight(worldIn, posToPlace, rand.nextInt(3, HopBlock.MAX_HEIGHT) + 1);
        if (randomHeight > 2)
        {
            for (int i = 0; i < randomHeight; i++)
            {
                if (i == randomHeight - 1)
                {
                    placeTop(worldIn, posToPlace);
                }
                else
                {
                    placeMiddle(worldIn, posToPlace, rand);
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

    private void printWarnStateIfNeeded(PatchConfiguration config)
    {
        List<BlockStateProvider> states = config.states();
        if (!states.isEmpty())
        {
            Houblonneux.LOGGER.warn("HopPatchFeature should not have states given : reused config with this not used parameter ");
        }
    }
}