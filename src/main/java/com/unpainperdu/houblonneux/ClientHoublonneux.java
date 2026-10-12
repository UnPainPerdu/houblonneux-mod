package com.unpainperdu.houblonneux;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLModContainer;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = Houblonneux.MOD_ID, dist = Dist.CLIENT)
public class ClientHoublonneux
{
    public ClientHoublonneux(FMLModContainer container, IEventBus modBus, Dist dist)
    {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}