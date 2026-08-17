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
                        _ -> 0xff85d185,
                        null),
                ModFluidRegister.EMERALD_CALL,
                ModFluidRegister.FLOWING_EMERALD_CALL);

        event.register(new FluidModel.Unbaked(
                        WATER_MATERIAL,
                        FLOWING_WATER_MATERIAL,
                        null,
                        _ -> 0xff3b094b,
                        null),
                ModFluidRegister.WORM_HOLE,
                ModFluidRegister.FLOWING_WORM_HOLE);

        event.register(new FluidModel.Unbaked(
                        WATER_MATERIAL,
                        FLOWING_WATER_MATERIAL,
                        null,
                        _ -> 0xfff19323,
                        null),
                ModFluidRegister.GROS_GUEULETON,
                ModFluidRegister.FLOWING_GROS_GUEULETON);
    }
}