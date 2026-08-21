package com.unpainperdu.houblonneux.level.world.effect;

import com.unpainperdu.houblonneux.register.ModSoundRegister;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

import java.util.Random;

public class PainDieuxMobEffect extends MobEffect
{
    public PainDieuxMobEffect(MobEffectCategory category, int color)
    {
        super(category, color);
    }

    @Override
    public void onEffectAdded(LivingEntity mob, int amplifier)
    {
        super.onEffectAdded(mob, amplifier);
        ServerLevel level = (ServerLevel) mob.level();
        level.playSound(null, mob.blockPosition().getX(), mob.blockPosition().getY(), mob.blockPosition().getZ(), ModSoundRegister.HOLY_GRENADE, SoundSource.NEUTRAL, 1.0F, 0.9F);
    }

    @Override
    public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity mob, int amplification)
    {
        Random rand = new Random();
        serverLevel.sendParticles(ParticleTypes.WHITE_SMOKE, mob.getX(), mob.getY() + 1.0, mob.getZ(), 3, (rand.nextDouble(1)) * (rand.nextBoolean() ? 1 : -1), (rand.nextDouble(1)) * (rand.nextBoolean() ? 1 : -1), (rand.nextDouble(1)) * (rand.nextBoolean() ? 1 : -1), rand.nextDouble(0.5));
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplification)
    {
        int baseModulo = 5 - amplification;
        return tickCount % (Math.max(baseModulo, 1)) == 0;
    }
}