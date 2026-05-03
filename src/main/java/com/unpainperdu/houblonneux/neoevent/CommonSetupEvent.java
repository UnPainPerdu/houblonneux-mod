package com.unpainperdu.houblonneux.neoevent;


import com.unpainperdu.houblonneux.Houblonneux;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

public class CommonSetupEvent
{
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
