package com.unpainperdu.houblonneux.level.world.effect;

import com.unpainperdu.houblonneux.level.world.effect.helper.ScaleEffectHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class PiedDeGeantsMobEffect extends MobEffect
{
    public PiedDeGeantsMobEffect(MobEffectCategory category, int color)
    {
        super(category, color);
    }

    @Override
    public void onEffectAdded(LivingEntity mob, int amplifier)
    {
        if (amplifier > 2)
        {
            amplifier = 2;
        }
        ScaleEffectHelper.scaleTo(mob, 1.2F + ( 0.75F * amplifier));
        super.onEffectAdded(mob, amplifier);
    }
}