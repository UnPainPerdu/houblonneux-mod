package com.unpainperdu.houblonneux.neoevent;

import net.neoforged.bus.api.IEventBus;

public class EventDispatcher
{
    public static void dispatchEvent(IEventBus modEventBus)
    {
        modEventBus.addListener(CommonSetupEvent::event);
        modEventBus.addListener(ModRegisterClientExtensionsEvent::registerClientExtensions);
    }
}