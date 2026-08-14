package com.unpainperdu.houblonneux.neoevent.recipe;

import com.unpainperdu.houblonneux.register.recipe.ModRecipeTypeRegister;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(value = Dist.CLIENT)
public class RecipesReceivedEvent
{
    @SubscribeEvent
    public static void recipesReceived(net.neoforged.neoforge.client.event.RecipesReceivedEvent event)
    {
        if (event.getRecipeTypes().contains(ModRecipeTypeRegister.BREWING_RECIPE.get()))
        {
            BrewingRecipeCache.rebuildFromMap(event.getRecipeMap());
        }
        if (event.getRecipeTypes().contains(ModRecipeTypeRegister.PUMPING_RECIPE.get()))
        {
            PumpingRecipeCache.rebuildFromMap(event.getRecipeMap());
        }
    }
}