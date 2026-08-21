package com.unpainperdu.houblonneux.neoevent.effect;

import com.unpainperdu.houblonneux.config.ModServerConfig;
import com.unpainperdu.houblonneux.register.entity.effect.ModMobEffectRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class NeoEventPainDieuxEffect
{
    public static void handlePainDieuxEndEffect(MobEffectInstance currentMobEffectInstance, LivingEntity entity)
    {
        if (currentMobEffectInstance != null)
        {
            Holder<MobEffect> currentMobEffectHolder = currentMobEffectInstance.getEffect();
            if (currentMobEffectHolder.is(ModMobEffectRegister.PAIN_DIEUX.getId()))
            {
                entity.level()
                        .explode(
                                entity,
                                Explosion.getDefaultDamageSource(entity.level(), entity),
                                new ExplosionDamageCalculator()
                                {
                                    @Override
                                    public boolean shouldBlockExplode(Explosion explosion, BlockGetter level, BlockPos pos, BlockState state, float power)
                                    {
                                        return ModServerConfig.CONFIG.PAIN_DIEUX_CAN_DESTROY_BLOCKS.get();
                                    }

                                    public boolean shouldDamageEntity(Explosion explosion, Entity entity1)
                                    {
                                        if (ModServerConfig.CONFIG.PAIN_DIEUX_CAN_DESTROY_BLOCKS.get())
                                        {
                                            if (ModServerConfig.CONFIG.PAIN_DIEUX_CAN_HURT_OTHER_PLAYER.get())
                                            {
                                                return !entity1.is(entity);
                                            }
                                            return !entity1.is(entity) && !(entity1 instanceof Player);
                                        }
                                        if (ModServerConfig.CONFIG.PAIN_DIEUX_CAN_HURT_OTHER_PLAYER.get())
                                        {
                                            return !entity1.is(entity) && (entity1 instanceof Mob || entity1 instanceof Player);
                                        }
                                        return !entity1.is(entity) && entity1 instanceof Mob;
                                    }
                                },
                                entity.getX(),
                                entity.getY(0.0625),
                                entity.getZ(),
                                0.7F * currentMobEffectInstance.getAmplifier(),
                                false,
                                Level.ExplosionInteraction.MOB
                        );
            }
        }
    }
}