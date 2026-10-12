package com.unpainperdu.houblonneux.neoevent;

import com.unpainperdu.houblonneux.level.world.effect.helper.ScaleEffectHelper;
import com.unpainperdu.houblonneux.register.ModDataAttachmentRegister;
import com.unpainperdu.houblonneux.register.ModTriggerRegister;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber
public class ModLivingDamageEvent
{
    @SubscribeEvent
    public static void onLivingDamagePre(LivingDamageEvent.Pre event)
    {
        // damage reduction/boost from scale
        LivingEntity entityHurt = event.getEntity();
        DamageSource source = event.getSource();
        Entity entitySource = source.getEntity();
        if (entitySource != null && (entityHurt.hasData(ModDataAttachmentRegister.SCALE_FACTOR) || entitySource.hasData(ModDataAttachmentRegister.SCALE_FACTOR))) //for pied de géants and real dwarve effect
        {
            ScaleEffectHelper.handleDamageChangeFromScale(event, entityHurt, entitySource);
        }
        float damageAmount = event.getContainer().getNewDamage();//after real dwarve and pied de géants scale
        // criterion trigger for achievement link to scale
        if (entityHurt instanceof ServerPlayer serverPlayer)
        {
            ModTriggerRegister.DAMAGE_WITH_EFFECT.get().triggerAsTarget(serverPlayer, damageAmount);
        }
        if (entitySource instanceof ServerPlayer serverPlayer)
        {
            ModTriggerRegister.DAMAGE_WITH_EFFECT.get().triggerAsKiller(serverPlayer, damageAmount);
        }
    }
}
