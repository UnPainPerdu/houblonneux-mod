package com.unpainperdu.houblonneux.register.fluid;

import com.unpainperdu.houblonneux.Houblonneux;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModFluidRegister
{
    public static final DeferredRegister<Fluid> FLUID = DeferredRegister.create(Registries.FLUID, Houblonneux.MOD_ID);

    public static final DeferredHolder<Fluid, BaseFlowingFluid> EMERALD_CALL = FLUID.register("emerald_call", () -> new BaseFlowingFluid.Source(ModFluidProperties.EMERALD_CALL));
    public static final DeferredHolder<Fluid, BaseFlowingFluid> FLOWING_EMERALD_CALL = FLUID.register("flowing_emerald_call", () -> new BaseFlowingFluid.Flowing(ModFluidProperties.EMERALD_CALL));

    public static final DeferredHolder<Fluid, BaseFlowingFluid> WORM_HOLE = FLUID.register("worm_hole", () -> new BaseFlowingFluid.Source(ModFluidProperties.WORM_HOLE));
    public static final DeferredHolder<Fluid, BaseFlowingFluid> FLOWING_WORM_HOLE = FLUID.register("flowing_worm_hole", () -> new BaseFlowingFluid.Flowing(ModFluidProperties.WORM_HOLE));

    public static final DeferredHolder<Fluid, BaseFlowingFluid> GROS_GUEULETON = FLUID.register("gros_gueuleton", () -> new BaseFlowingFluid.Source(ModFluidProperties.GROS_GUEULETON));
    public static final DeferredHolder<Fluid, BaseFlowingFluid> FLOWING_GROS_GUEULETON = FLUID.register("flowing_gros_gueuleton", () -> new BaseFlowingFluid.Flowing(ModFluidProperties.GROS_GUEULETON));

    public static void register(IEventBus event)
    {
        FLUID.register(event);
    }
}