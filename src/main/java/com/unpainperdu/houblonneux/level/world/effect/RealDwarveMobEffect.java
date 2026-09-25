package com.unpainperdu.houblonneux.level.world.effect;

import com.unpainperdu.houblonneux.level.world.effect.helper.ScaleEffectHelper;
import com.unpainperdu.houblonneux.register.ModSoundRegister;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class RealDwarveMobEffect extends MobEffect
{
    public RealDwarveMobEffect(MobEffectCategory category, int color)
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
        mob.level().playSound(null, mob.blockPosition().getX(), mob.blockPosition().getY(), mob.blockPosition().getZ(), ModSoundRegister.SHRINK, SoundSource.NEUTRAL, 1.0F, 1.5F);
        ScaleEffectHelper.scaleTo(mob, 0.8F - ( 0.25F * amplifier));
        super.onEffectAdded(mob, amplifier);
    }
}