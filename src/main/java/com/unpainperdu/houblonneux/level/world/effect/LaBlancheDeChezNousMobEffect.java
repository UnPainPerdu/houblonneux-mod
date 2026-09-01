package com.unpainperdu.houblonneux.level.world.effect;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Random;

public class LaBlancheDeChezNousMobEffect extends MobEffect
{
    public LaBlancheDeChezNousMobEffect(MobEffectCategory category, int color)
    {
        super(category, color);
    }

    @Override
    public void onEffectAdded(LivingEntity mob, int amplifier)
    {
        super.onEffectAdded(mob, amplifier);
    }

    @Override
    public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity mob, int amplification)
    {
        if (amplification > 3)
        {
            amplification = 2;
        }
        BlockPos entityOnPos = mob.getOnPos();
            if (mob.getY() > entityOnPos.getY())
        {
            entityOnPos = entityOnPos.below();
        }

        Random rand = new Random();
        serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, mob.getX(), mob.getY() + 1.0, mob.getZ(), 3, (rand.nextDouble(1)) * (rand.nextBoolean() ? 1 : -1), (rand.nextDouble(1)) * (rand.nextBoolean() ? 1 : -1), (rand.nextDouble(1)) * (rand.nextBoolean() ? 1 : -1), rand.nextDouble(0.3));

        applySnowAndIce(serverLevel, entityOnPos, amplification);
        if (amplification > 0)
        {
            applySnowAndIce(serverLevel, entityOnPos.north(), amplification);
            applySnowAndIce(serverLevel, entityOnPos.south(), amplification);
            applySnowAndIce(serverLevel, entityOnPos.east(), amplification);
            applySnowAndIce(serverLevel, entityOnPos.west(), amplification);
        }
        if (amplification > 1)
        {
            applySnowAndIce(serverLevel, entityOnPos.north().east(), amplification);
            applySnowAndIce(serverLevel, entityOnPos.north().west(), amplification);
            applySnowAndIce(serverLevel, entityOnPos.south().east(), amplification);
            applySnowAndIce(serverLevel, entityOnPos.south().west(), amplification);
            applySnowAndIce(serverLevel, entityOnPos.north(2), amplification);
            applySnowAndIce(serverLevel, entityOnPos.south(2), amplification);
            applySnowAndIce(serverLevel, entityOnPos.east(2), amplification);
            applySnowAndIce(serverLevel, entityOnPos.west(2), amplification);
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplification)
    {
        return true;
    }

    private void applySnowAndIce(ServerLevel serverLevel, BlockPos onPos, int amplification)
    {
        RandomSource rand = serverLevel.getRandom();
        BlockState onPosBlockstate = serverLevel.getBlockState(onPos);
        BlockState aboveOnPosBlockstate = serverLevel.getBlockState(onPos.above());
        BlockState basicSnowBlockState = Blocks.SNOW.defaultBlockState();
        if (basicSnowBlockState.canSurvive(serverLevel, onPos.above()) && aboveOnPosBlockstate.isAir())
        {
            serverLevel.setBlockAndUpdate(onPos.above(), basicSnowBlockState.setValue(BlockStateProperties.LAYERS, getSnowLayer(rand, 2 + amplification, 0.1F + amplification*0.1F)));
        }
        else if (onPosBlockstate.is(Blocks.WATER))
        {
            serverLevel.setBlockAndUpdate(onPos, Blocks.ICE.defaultBlockState());
        }
    }

    public int getSnowLayer(RandomSource rand, int size, float chance)
    {
        int result = 1;

        for (int i = 1; i < size; i++)
        {
            if (rand.nextFloat() < chance)
            {
                result++;
            }
        }

        return Math.min(result, 8);
    }
}