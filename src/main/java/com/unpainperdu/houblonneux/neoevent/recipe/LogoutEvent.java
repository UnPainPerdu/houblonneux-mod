package com.unpainperdu.houblonneux.neoevent.recipe;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;

@EventBusSubscriber(value = Dist.CLIENT)
public class LogoutEvent
{
    @SubscribeEvent
    public static void logoutEvent(ClientPlayerNetworkEvent.LoggingOut event)
    {
        BrewingRecipeCache.clear();
        PumpingRecipeCache.clear();
    }
}