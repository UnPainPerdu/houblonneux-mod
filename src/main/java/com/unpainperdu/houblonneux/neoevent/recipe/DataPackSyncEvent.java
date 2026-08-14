package com.unpainperdu.houblonneux.neoevent.recipe;

import com.unpainperdu.houblonneux.register.recipe.ModRecipeTypeRegister;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;

public class DataPackSyncEvent
{
    @SubscribeEvent
    public static void sync(OnDatapackSyncEvent event)
    {
        BrewingRecipeCache.rebuildFromServer(event.getPlayerList().getServer());
        event.sendRecipes(ModRecipeTypeRegister.BREWING_RECIPE.get());
        PumpingRecipeCache.rebuildFromServer(event.getPlayerList().getServer());
        event.sendRecipes(ModRecipeTypeRegister.PUMPING_RECIPE.get());
    }
}
