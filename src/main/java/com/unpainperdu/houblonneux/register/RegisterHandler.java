package com.unpainperdu.houblonneux.register;

import com.unpainperdu.houblonneux.register.block.ModBlockEntityRegister;
import com.unpainperdu.houblonneux.register.block.ModBlockRegister;
import com.unpainperdu.houblonneux.register.codec.ModGLMCodecRegister;
import com.unpainperdu.houblonneux.register.gen.structure.ModStructureProcessorRegister;
import com.unpainperdu.houblonneux.register.item.ModConsumeEffectTypeRegister;
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
        ModConsumeEffectTypeRegister.register(event);
        ModSoundRegister.register(event);
        ModBlockEntityRegister.register(event);
        ModMenuTypeRegister.register(event);
        ModDataComponentRegister.register(event);
        ModStructureProcessorRegister.register(event);
    }
}