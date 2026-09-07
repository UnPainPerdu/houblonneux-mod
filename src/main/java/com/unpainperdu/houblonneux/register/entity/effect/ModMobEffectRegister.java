package com.unpainperdu.houblonneux.register.entity.effect;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.world.effect.*;
import com.unpainperdu.houblonneux.register.fluid.ModFluidRegister;
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
            () -> new WormHoleMobEffect(MobEffectCategory.NEUTRAL, ModFluidRegister.WORM_HOLE_COLOR)
    );

    public static final DeferredHolder<MobEffect, PainDieuxMobEffect> PAIN_DIEUX = MOB_EFFECT.register(
            "pain_dieux",
            () -> new PainDieuxMobEffect(MobEffectCategory.NEUTRAL, ModFluidRegister.PAIN_DIEUX_COLOR)
    );

    public static final DeferredHolder<MobEffect, RealDwarveMobEffect> REAL_DWARVE = MOB_EFFECT.register(
            "real_dwarve",
            () -> new RealDwarveMobEffect(MobEffectCategory.NEUTRAL, ModFluidRegister.REAL_DWARVE_COLOR)
    );

    public static final DeferredHolder<MobEffect, PiedDeGeantsMobEffect> PIED_DE_GEANTS = MOB_EFFECT.register(
            "pied_de_geants",
            () -> new PiedDeGeantsMobEffect(MobEffectCategory.NEUTRAL, ModFluidRegister.PIED_DE_GEANTS_COLOR)
    );
    public static final DeferredHolder<MobEffect, LaBlancheDeChezNousMobEffect> LA_BLANCHE_DE_CHEZ_NOUS = MOB_EFFECT.register(
            "la_blanche_de_chez_nous",
            () -> new LaBlancheDeChezNousMobEffect(MobEffectCategory.NEUTRAL, ModFluidRegister.LA_BLANCHE_DE_CHEZ_NOUS_COLOR)
    );

    public static void register(IEventBus event)
    {
        MOB_EFFECT.register(event);
    }
}