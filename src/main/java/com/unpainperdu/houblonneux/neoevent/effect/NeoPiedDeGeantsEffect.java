package com.unpainperdu.houblonneux.neoevent.effect;

import com.unpainperdu.houblonneux.level.world.effect.helper.ScaleEffectHelper;
import com.unpainperdu.houblonneux.register.ModSoundRegister;
import com.unpainperdu.houblonneux.register.entity.effect.ModMobEffectRegister;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class NeoPiedDeGeantsEffect
{
    public static void handlePiedDeGeantsEndEffect(MobEffectInstance currentMobEffectInstance, LivingEntity entity)
    {
        if (currentMobEffectInstance != null)
        {
            Holder<MobEffect> currentMobEffectHolder = currentMobEffectInstance.getEffect();
            if (currentMobEffectHolder.is(ModMobEffectRegister.PIED_DE_GEANTS.getId()))
            {
                entity.level().playSound(null, entity.blockPosition().getX(), entity.blockPosition().getY(), entity.blockPosition().getZ(), ModSoundRegister.SHRINK, SoundSource.NEUTRAL, 1.0F, 1.5F);
                ScaleEffectHelper.resetToBaseScale(entity);
            }
        }
    }
}