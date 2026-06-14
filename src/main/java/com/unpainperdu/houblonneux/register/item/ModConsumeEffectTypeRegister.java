package com.unpainperdu.houblonneux.register.item;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.world.item.consume_effect.beer.EmeraldCallBeerConsumeEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModConsumeEffectTypeRegister
{
    public static final DeferredRegister<ConsumeEffect.Type<?>> CONSUME_EFFECT = DeferredRegister.create(Registries.CONSUME_EFFECT_TYPE, Houblonneux.MOD_ID);

    public static final DeferredHolder<ConsumeEffect.Type<?>, ConsumeEffect.Type<EmeraldCallBeerConsumeEffect>> EMERALD_CALL_BEER = CONSUME_EFFECT.register(
            "emerald_call_beer",
            () -> new ConsumeEffect.Type<>(EmeraldCallBeerConsumeEffect.CODEC,EmeraldCallBeerConsumeEffect.STREAM_CODEC));

    public static void register(IEventBus event)
    {
        CONSUME_EFFECT.register(event);
    }
}