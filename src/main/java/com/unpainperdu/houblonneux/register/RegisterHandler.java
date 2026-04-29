package com.unpainperdu.houblonneux.register;

import com.unpainperdu.houblonneux.register.block.ModBlockRegister;
import net.neoforged.bus.api.IEventBus;

public class RegisterHandler
{
    public static void register(IEventBus event)
    {
        ModBlockRegister.register(event);
    }
}
