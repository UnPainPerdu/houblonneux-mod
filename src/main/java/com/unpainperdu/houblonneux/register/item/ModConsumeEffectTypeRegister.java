package com.unpainperdu.houblonneux.register.item;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.world.item.consume_effect.beer.*;
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
            () -> new ConsumeEffect.Type<>(EmeraldCallBeerConsumeEffect.CODEC, EmeraldCallBeerConsumeEffect.STREAM_CODEC)
    );
    public static final DeferredHolder<ConsumeEffect.Type<?>, ConsumeEffect.Type<GrosGueuletonBeerConsumeEffect>> GROS_GUEULETON_BEER = CONSUME_EFFECT.register(
            "gros_geuleton_beer",
            () -> new ConsumeEffect.Type<>(GrosGueuletonBeerConsumeEffect.CODEC, GrosGueuletonBeerConsumeEffect.STREAM_CODEC)
    );

    public static final DeferredHolder<ConsumeEffect.Type<?>, ConsumeEffect.Type<WormHoleBeerConsumeEffect>> WORM_HOLE_BEER = CONSUME_EFFECT.register(
            "worm_hole_beer",
            () -> new ConsumeEffect.Type<>(WormHoleBeerConsumeEffect.CODEC, WormHoleBeerConsumeEffect.STREAM_CODEC)
    );

    public static final DeferredHolder<ConsumeEffect.Type<?>, ConsumeEffect.Type<PainDieuxConsumeEffect>> PAIN_DIEUX_BEER = CONSUME_EFFECT.register(
            "pain_dieux_beer",
            () -> new ConsumeEffect.Type<>(PainDieuxConsumeEffect.CODEC, PainDieuxConsumeEffect.STREAM_CODEC)
    );

    public static final DeferredHolder<ConsumeEffect.Type<?>, ConsumeEffect.Type<RealDwarveConsumeEffect>> REAL_DWARVE_BEER = CONSUME_EFFECT.register(
            "real_dwarve_beer",
            () -> new ConsumeEffect.Type<>(RealDwarveConsumeEffect.CODEC, RealDwarveConsumeEffect.STREAM_CODEC)
    );

    public static final DeferredHolder<ConsumeEffect.Type<?>, ConsumeEffect.Type<PiedDeGeantsConsumeEffect>> PIED_DE_GEANTS_BEER = CONSUME_EFFECT.register(
            "pied_de_geants_beer",
            () -> new ConsumeEffect.Type<>(PiedDeGeantsConsumeEffect.CODEC, PiedDeGeantsConsumeEffect.STREAM_CODEC)
    );

    public static final DeferredHolder<ConsumeEffect.Type<?>, ConsumeEffect.Type<LaBlancheDeChezNousConsumeEffect>> LA_BLANCHE_DE_CHEZ_NOUS_BEER = CONSUME_EFFECT.register(
            "la_blanche_de_chez_nous_beer",
            () -> new ConsumeEffect.Type<>(LaBlancheDeChezNousConsumeEffect.CODEC, LaBlancheDeChezNousConsumeEffect.STREAM_CODEC)
    );

    public static void register(IEventBus event)
    {
        CONSUME_EFFECT.register(event);
    }
}