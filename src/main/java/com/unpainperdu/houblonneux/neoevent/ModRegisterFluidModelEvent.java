package com.unpainperdu.houblonneux.neoevent;

import com.unpainperdu.houblonneux.register.fluid.ModFluidRegister;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterFluidModelsEvent;

@EventBusSubscriber(value = {Dist.CLIENT})
public class ModRegisterFluidModelEvent
{
    private static final Material WATER_MATERIAL = new Material(Identifier.withDefaultNamespace("block/water_still"));
    private static final Material FLOWING_WATER_MATERIAL = new Material(Identifier.withDefaultNamespace("block/water_flow"));

    @SubscribeEvent
    private static void registerFluidModels(RegisterFluidModelsEvent event)
    {
        event.register(new FluidModel.Unbaked(
                        WATER_MATERIAL,
                        FLOWING_WATER_MATERIAL,
                        null,
                        _ -> ModFluidRegister.EMERALD_CALL_COLOR,
                        null),
                ModFluidRegister.EMERALD_CALL,
                ModFluidRegister.FLOWING_EMERALD_CALL);

        event.register(new FluidModel.Unbaked(
                        WATER_MATERIAL,
                        FLOWING_WATER_MATERIAL,
                        null,
                        _ -> ModFluidRegister.WORM_HOLE_COLOR,
                        null),
                ModFluidRegister.WORM_HOLE,
                ModFluidRegister.FLOWING_WORM_HOLE);

        event.register(new FluidModel.Unbaked(
                        WATER_MATERIAL,
                        FLOWING_WATER_MATERIAL,
                        null,
                        _ -> ModFluidRegister.GROS_GUEULETON_COLOR,
                        null),
                ModFluidRegister.GROS_GUEULETON,
                ModFluidRegister.FLOWING_GROS_GUEULETON);

        event.register(new FluidModel.Unbaked(
                        WATER_MATERIAL,
                        FLOWING_WATER_MATERIAL,
                        null,
                        _ -> ModFluidRegister.PAIN_DIEUX_COLOR,
                        null),
                ModFluidRegister.PAIN_DIEUX,
                ModFluidRegister.FLOWING_PAIN_DIEUX);

        event.register(new FluidModel.Unbaked(
                        WATER_MATERIAL,
                        FLOWING_WATER_MATERIAL,
                        null,
                        _ -> ModFluidRegister.REAL_DWARVE_COLOR,
                        null),
                ModFluidRegister.REAL_DWARVE,
                ModFluidRegister.FLOWING_REAL_DWARVE);

        event.register(new FluidModel.Unbaked(
                        WATER_MATERIAL,
                        FLOWING_WATER_MATERIAL,
                        null,
                        _ -> ModFluidRegister.PIED_DE_GEANTS_COLOR,
                        null),
                ModFluidRegister.PIED_DE_GEANTS,
                ModFluidRegister.FLOWING_PIED_DE_GEANTS);
        event.register(new FluidModel.Unbaked(
                        WATER_MATERIAL,
                        FLOWING_WATER_MATERIAL,
                        null,
                        _ -> ModFluidRegister.LA_BLANCHE_DE_CHEZ_NOUS_COLOR,
                        null),
                ModFluidRegister.LA_BLANCHE_DE_CHEZ_NOUS,
                ModFluidRegister.FLOWING_LA_BLANCHE_DE_CHEZ_NOUS);

    }
}