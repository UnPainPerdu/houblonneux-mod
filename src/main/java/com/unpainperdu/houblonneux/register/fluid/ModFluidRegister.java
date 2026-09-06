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

    public static final int EMERALD_CALL_COLOR = 0xFF85D185;
    public static final DeferredHolder<Fluid, BaseFlowingFluid> EMERALD_CALL = FLUID.register("emerald_call", () -> new BaseFlowingFluid.Source(ModFluidProperties.EMERALD_CALL));
    public static final DeferredHolder<Fluid, BaseFlowingFluid> FLOWING_EMERALD_CALL = FLUID.register("flowing_emerald_call", () -> new BaseFlowingFluid.Flowing(ModFluidProperties.EMERALD_CALL));

    public static final int WORM_HOLE_COLOR = 0xFF3B094B;
    public static final DeferredHolder<Fluid, BaseFlowingFluid> WORM_HOLE = FLUID.register("worm_hole", () -> new BaseFlowingFluid.Source(ModFluidProperties.WORM_HOLE));
    public static final DeferredHolder<Fluid, BaseFlowingFluid> FLOWING_WORM_HOLE = FLUID.register("flowing_worm_hole", () -> new BaseFlowingFluid.Flowing(ModFluidProperties.WORM_HOLE));

    public static final int GROS_GUEULETON_COLOR = 0xFFF7B261;
    public static final DeferredHolder<Fluid, BaseFlowingFluid> GROS_GUEULETON = FLUID.register("gros_gueuleton", () -> new BaseFlowingFluid.Source(ModFluidProperties.GROS_GUEULETON));
    public static final DeferredHolder<Fluid, BaseFlowingFluid> FLOWING_GROS_GUEULETON = FLUID.register("flowing_gros_gueuleton", () -> new BaseFlowingFluid.Flowing(ModFluidProperties.GROS_GUEULETON));

    public static final int PAIN_DIEUX_COLOR = 0xFFFFFFFF;
    public static final DeferredHolder<Fluid, BaseFlowingFluid> PAIN_DIEUX = FLUID.register("pain_dieux", () -> new BaseFlowingFluid.Source(ModFluidProperties.PAIN_DIEUX));
    public static final DeferredHolder<Fluid, BaseFlowingFluid> FLOWING_PAIN_DIEUX = FLUID.register("flowing_pain_dieux", () -> new BaseFlowingFluid.Flowing(ModFluidProperties.PAIN_DIEUX));

    public static final int REAL_DWARVE_COLOR = 0xFFFFD700;
    public static final DeferredHolder<Fluid, BaseFlowingFluid> REAL_DWARVE = FLUID.register("real_dwarve", () -> new BaseFlowingFluid.Source(ModFluidProperties.REAL_DWARVE));
    public static final DeferredHolder<Fluid, BaseFlowingFluid> FLOWING_REAL_DWARVE = FLUID.register("flowing_real_dwarve", () -> new BaseFlowingFluid.Flowing(ModFluidProperties.REAL_DWARVE));

    public static final int PIED_DE_GEANTS_COLOR = 0xFFAC7C00;
    public static final DeferredHolder<Fluid, BaseFlowingFluid> PIED_DE_GEANTS = FLUID.register("pied_de_geants", () -> new BaseFlowingFluid.Source(ModFluidProperties.PIED_DE_GEANTS));
    public static final DeferredHolder<Fluid, BaseFlowingFluid> FLOWING_PIED_DE_GEANTS = FLUID.register("flowing_pied_de_geants", () -> new BaseFlowingFluid.Flowing(ModFluidProperties.PIED_DE_GEANTS));

    public static final int LA_BLANCHE_DE_CHEZ_NOUS_COLOR = 0xFFE6F8FE;
    public static final DeferredHolder<Fluid, BaseFlowingFluid> LA_BLANCHE_DE_CHEZ_NOUS = FLUID.register("la_blanche_de_chez_nous", () -> new BaseFlowingFluid.Source(ModFluidProperties.LA_BLANCHE_DE_CHEZ_NOUS));
    public static final DeferredHolder<Fluid, BaseFlowingFluid> FLOWING_LA_BLANCHE_DE_CHEZ_NOUS = FLUID.register("flowing_la_blanche_de_chez_nous", () -> new BaseFlowingFluid.Flowing(ModFluidProperties.LA_BLANCHE_DE_CHEZ_NOUS));

    public static void register(IEventBus event)
    {
        FLUID.register(event);
    }
}