package com.unpainperdu.houblonneux.neoevent.effect;

import com.unpainperdu.houblonneux.level.world.effect.helper.ScaleEffectHelper;
import com.unpainperdu.houblonneux.register.entity.effect.ModMobEffectRegister;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class NeoRealDwarveEffect
{
    public static void handleRealDwarveEndEffect(MobEffectInstance currentMobEffectInstance, LivingEntity entity)
    {
        if (currentMobEffectInstance != null)
        {
            Holder<MobEffect> currentMobEffectHolder = currentMobEffectInstance.getEffect();
            if (currentMobEffectHolder.is(ModMobEffectRegister.REAL_DWARVE.getId()))
            {
                ScaleEffectHelper.resetToBaseScale(entity);
            }
        }
    }
}