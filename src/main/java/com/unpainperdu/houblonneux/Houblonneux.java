package com.unpainperdu.houblonneux;

import com.mojang.logging.LogUtils;
import com.unpainperdu.houblonneux.datagen.DataGatherer;
import com.unpainperdu.houblonneux.neoevent.CommonSetupEvent;
import com.unpainperdu.houblonneux.register.RegisterHandler;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(Houblonneux.MOD_ID)
public class Houblonneux
{
    public static final String MOD_ID = "houblonneux";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Houblonneux(IEventBus modEventBus, ModContainer modContainer)
    {
        RegisterHandler.register(modEventBus);
        modEventBus.addListener(CommonSetupEvent::event);
        modEventBus.addListener(DataGatherer::gatherData);
    }
}