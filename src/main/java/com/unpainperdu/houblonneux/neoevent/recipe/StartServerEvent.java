package com.unpainperdu.houblonneux.neoevent.recipe;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartedEvent;

@EventBusSubscriber
public class StartServerEvent
{
    @SubscribeEvent
    public static void started(ServerStartedEvent event)
    {
        BrewingRecipeCache.rebuildFromServer(event.getServer());
        PumpingRecipeCache.rebuildFromServer(event.getServer());
    }
}
