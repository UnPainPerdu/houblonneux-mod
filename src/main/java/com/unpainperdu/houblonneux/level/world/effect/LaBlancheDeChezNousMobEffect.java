package com.unpainperdu.houblonneux.level.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

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
}