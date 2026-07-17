package com.unpainperdu.houblonneux.register.entity.effect;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.world.effect.WormHoleMobEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMobEffectRegister
{
    public static final DeferredRegister<MobEffect> MOB_EFFECT = DeferredRegister.create(Registries.MOB_EFFECT, Houblonneux.MOD_ID);

    public static final DeferredHolder<MobEffect, WormHoleMobEffect> WORM_HOLE = MOB_EFFECT.register(
            "worm_hole",
            () -> new WormHoleMobEffect(MobEffectCategory.NEUTRAL, 0x000000)
    );

    public static void register(IEventBus event)
    {
        MOB_EFFECT.register(event);
    }
}