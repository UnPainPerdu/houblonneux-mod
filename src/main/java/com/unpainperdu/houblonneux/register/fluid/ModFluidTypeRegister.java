package com.unpainperdu.houblonneux.register.fluid;

import com.unpainperdu.houblonneux.Houblonneux;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModFluidTypeRegister
{
    public static final DeferredRegister<FluidType> FLUID_TYPE = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, Houblonneux.MOD_ID);

    public static final DeferredHolder<FluidType, FluidType> EMERALD_CALL = FLUID_TYPE.register("emerald_call", () -> new FluidType(FluidType.Properties.create()));
    public static final DeferredHolder<FluidType, FluidType> WORM_HOLE = FLUID_TYPE.register("worm_hole", () -> new FluidType(FluidType.Properties.create()));
    public static final DeferredHolder<FluidType, FluidType> GROS_GUEULETON = FLUID_TYPE.register("gros_gueuleton", () -> new FluidType(FluidType.Properties.create()));

    public static void register(IEventBus event)
    {
        FLUID_TYPE.register(event);
    }
}