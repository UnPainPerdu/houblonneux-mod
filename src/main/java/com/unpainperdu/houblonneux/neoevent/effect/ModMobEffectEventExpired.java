package com.unpainperdu.houblonneux.neoevent.effect;

import com.unpainperdu.houblonneux.Houblonneux;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

@EventBusSubscriber(modid = Houblonneux.MOD_ID)
public class ModMobEffectEventExpired
{
    @SubscribeEvent
    public static void event(MobEffectEvent.Expired event)
    {
        MobEffectInstance currentMobEffectInstance = event.getEffectInstance();
        LivingEntity currentLivingEntity = event.getEntity();
        CommonEffect.handleWormHoleEndEffect(currentMobEffectInstance, currentLivingEntity);
    }
}