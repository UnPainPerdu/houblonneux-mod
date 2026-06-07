package com.unpainperdu.houblonneux.register;

import com.unpainperdu.houblonneux.register.block.ModBlockRegister;
import com.unpainperdu.houblonneux.register.codec.ModGLMCodecRegister;
import com.unpainperdu.houblonneux.register.item.ModConsumeEffectRegister;
import com.unpainperdu.houblonneux.register.item.ModItemRegister;
import com.unpainperdu.houblonneux.register.worldgen.ModFeatureRegister;
import net.neoforged.bus.api.IEventBus;

public class RegisterHandler
{
    public static void register(IEventBus event)
    {
        ModBlockRegister.register(event);
        ModItemRegister.register(event);
        ModCreativeTabRegister.register(event);
        ModGLMCodecRegister.register(event);
        ModFeatureRegister.register(event);
        ModConsumeEffectRegister.register(event);
    }
}