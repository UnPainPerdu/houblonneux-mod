package com.unpainperdu.houblonneux.neoevent;


import com.unpainperdu.houblonneux.Houblonneux;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@EventBusSubscriber(modid = Houblonneux.MOD_ID)
public class CommonSetupEvent
{
    @SubscribeEvent
    public static void event(final FMLCommonSetupEvent event)
    {
        printWelcomeMessage();
    }

    private static void printWelcomeMessage()
    {
        Houblonneux.LOGGER.info("""
                
                -----------------------------------------------------
                ---------------------Houblonneux---------------------
                ---A beer mod for Premier Pain and other villager!---
                -----------------------------------------------------""");
    }
}
