package com.unpainperdu.houblonneux.neoevent.effect;

import com.unpainperdu.houblonneux.register.ModDataAttachmentRegister;
import com.unpainperdu.houblonneux.register.entity.effect.ModMobEffectRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;

public class CommonEffect
{
    public static void handleWormHoleEndEffect(MobEffectInstance currentMobEffectInstance, LivingEntity entity)
    {
        if (currentMobEffectInstance != null)
        {
            Holder<MobEffect> currentMobEffectHolder = currentMobEffectInstance.getEffect();
            if (currentMobEffectHolder.is(ModMobEffectRegister.WORM_HOLE.getId()))
            {
                BlockPos originalMobPos = getOriginalUserPos(entity);
                double originalMobX = originalMobPos.getX() + 0.5;
                double originalMobY = originalMobPos.getY();
                double originalMobZ = originalMobPos.getZ() + 0.5;
                //TODO sound + particle
                entity.teleportTo(originalMobX, originalMobY, originalMobZ);
            }
        }
    }

    public static BlockPos getOriginalUserPos(LivingEntity mob)
    {
        if (mob.hasData(ModDataAttachmentRegister.ORIGINAL_BLOCK_POS))
        {
            BlockPos returnPos = mob.getData(ModDataAttachmentRegister.ORIGINAL_BLOCK_POS).getOriginalBlockPos();
            mob.removeData(ModDataAttachmentRegister.ORIGINAL_BLOCK_POS);
            return returnPos;
        }
        else
        {
            return getSafePosition(mob);
        }
    }

    public static BlockPos getSafePosition(LivingEntity mob)
    {
        Level level = mob.level();
        BlockPos mobPos = mob.blockPosition();
        BlockPos posProposal = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, mobPos);
        BlockState stateOn = level.getBlockState(posProposal);

        if (stateOn.is(Blocks.LAVA) && mob instanceof ServerPlayer serverPlayer)
        {
            ServerPlayer.RespawnConfig respawnConfig = serverPlayer.getRespawnConfig();
            if (respawnConfig != null)
            {
                posProposal = respawnConfig.respawnData().pos();
            }
        }
        return posProposal;
    }
}