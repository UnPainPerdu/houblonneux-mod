package com.unpainperdu.houblonneux.register;

import com.unpainperdu.houblonneux.Houblonneux;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModParticleTypeRegister
{
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPE = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Houblonneux.MOD_ID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> WORM_HOLE_PORTAL = PARTICLE_TYPE.register(
            "worm_hole_portal",
            () -> new SimpleParticleType(false)
    );

    public static void register(IEventBus event)
    {
        PARTICLE_TYPE.register(event);
    }
}