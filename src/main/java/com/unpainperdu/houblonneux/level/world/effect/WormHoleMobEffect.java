package com.unpainperdu.houblonneux.level.world.effect;

import com.unpainperdu.houblonneux.level.world.attachments.OriginalBlockPosAttach;
import com.unpainperdu.houblonneux.register.ModDataAttachmentRegister;
import com.unpainperdu.houblonneux.register.ModParticleTypeRegister;
import com.unpainperdu.houblonneux.util.PosHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class WormHoleMobEffect extends MobEffect
{
    private BlockPos originalUserPos;
    public WormHoleMobEffect(MobEffectCategory category, int color)
    {
        super(category, color);
    }

    @Override
    public void onEffectAdded(LivingEntity entity, int amplifier)
    {
        super.onEffectAdded(entity, amplifier);
        storeAndSaveOriginalBlockPos(entity);
        float r = entity.level().getRandom().nextFloat();
        BlockPos destinationPos;
        if (r < 0.15F) // sky
        {
            destinationPos = new BlockPos(originalUserPos.getX(), 1000, originalUserPos.getZ());
        }
        else if (r < 0.30F) // under world
        {
            destinationPos = new BlockPos(originalUserPos.getX(), -69, originalUserPos.getZ());
        }
        else // not so far
        {
            destinationPos = getNotSoFarSafePos(entity.level(), originalUserPos);
            if (destinationPos.equals(originalUserPos)) //fall back sky
            {
                destinationPos = new BlockPos(originalUserPos.getX(), 1000, originalUserPos.getZ());
            }
        }
        //TODO particle
        ServerLevel level = (ServerLevel) entity.level();
        level.playSound(null, entity.blockPosition().getX(), entity.blockPosition().getY(), entity.blockPosition().getZ(), SoundEvents.PLAYER_TELEPORT, SoundSource.NEUTRAL, 1.0F , 0.2F);
        level.sendParticles(ModParticleTypeRegister.WORM_HOLE_PORTAL.get(), entity.getX(), entity.getY() + 1.0, entity.getZ(), 1, 0, 0, 0, 0.0);
        entity.teleportTo(destinationPos.getX(), destinationPos.getY(), destinationPos.getZ());
        level.playSound(null, entity.blockPosition().getX(), entity.blockPosition().getY(), entity.blockPosition().getZ(), SoundEvents.PLAYER_TELEPORT, SoundSource.NEUTRAL, 1.0F , 0.2F);
        level.sendParticles(ModParticleTypeRegister.WORM_HOLE_PORTAL.get(), entity.getX(), entity.getY() + 1.0, entity.getZ(), 1, 0, 0, 0, 0.0);
    }

    private void storeAndSaveOriginalBlockPos(LivingEntity mob)
    {
        this.originalUserPos = mob.blockPosition();
        mob.setData(ModDataAttachmentRegister.ORIGINAL_BLOCK_POS, new OriginalBlockPosAttach(this.originalUserPos));
    }

    public BlockPos getNotSoFarSafePos(Level level, BlockPos pos)
    {
        for (BlockPos pos1 : PosHelper.setAllPosToTheGround(PosHelper.getRandomPosWithSameY(pos.above(10), 10, 15, level.getRandom(), false), (ServerLevel) level))
        {
            if (pos.getX() != pos1.getX() && pos.getZ() != pos1.getZ())
            {
                if (!level.getBlockState(pos1.below()).is(Blocks.LAVA))
                {
                    if (level.getBlockState(pos1).isAir() && level.getBlockState(pos1.above()).isAir())
                    {
                        return pos1;
                    }
                }
            }
        }
        return pos;
    }
}