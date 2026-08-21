package com.unpainperdu.houblonneux.register;

import com.unpainperdu.houblonneux.Houblonneux;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * a sound definition must be datagened too, see {@link com.unpainperdu.houblonneux.datagen.asset.ModSoundDefinitionsProvider}
 */
public class ModSoundRegister
{
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Houblonneux.MOD_ID);

    public static final DeferredHolder<SoundEvent, SoundEvent> BURP = register("burp");
    public static final String BURP_SUBTITLE = "sound." + Houblonneux.MOD_ID + ".burp";

    public static final DeferredHolder<SoundEvent, SoundEvent> HOLY_GRENADE = register("holy_grenade");
    public static final String HOLY_GRENADE_SUBTITLE = "sound." + Houblonneux.MOD_ID + ".holy_grenade";

    private static DeferredHolder<SoundEvent, SoundEvent> register(String name)
    {
        return SOUND_EVENTS.register(name, SoundEvent::createVariableRangeEvent);
    }

    public static void register(IEventBus event)
    {
        SOUND_EVENTS.register(event);
    }
}
