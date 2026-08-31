package com.unpainperdu.houblonneux.neoevent;

import com.unpainperdu.houblonneux.level.world.effect.helper.ScaleEffectHelper;
import com.unpainperdu.houblonneux.register.entity.effect.ModMobEffectRegister;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber
public class ModLivingDeathEvent
{
    @SubscribeEvent
    public static void onLivingDeathEvent(LivingDeathEvent event)
    {
        LivingEntity entityHurt = event.getEntity();

        if (entityHurt.hasEffect(ModMobEffectRegister.PIED_DE_GEANTS) || entityHurt.hasEffect(ModMobEffectRegister.REAL_DWARVE))
        {
            ScaleEffectHelper.resetToBaseScale(entityHurt);
        }
    }
}