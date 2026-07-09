package com.unpainperdu.houblonneux.client;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.client.render.BeerDispenserBlockEntityRenderer;
import com.unpainperdu.houblonneux.client.screen.BeerDispenserScreen;
import com.unpainperdu.houblonneux.register.ModMenuTypeRegister;
import com.unpainperdu.houblonneux.register.block.ModBlockEntityRegister;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = Houblonneux.MOD_ID, value = {Dist.CLIENT})
public class ClientRegister
{
    @SubscribeEvent
    private static void registerMenuScreens(RegisterMenuScreensEvent event)
    {
        event.register(ModMenuTypeRegister.BEER_DISPENSER.get(), BeerDispenserScreen::new);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerBlockEntityRenderer(
                ModBlockEntityRegister.BEER_DISPENSER.get(),
                BeerDispenserBlockEntityRenderer::new
        );
    }
}