package com.unpainperdu.houblonneux.neoevent;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.server.packs.ressources.trade.dispenser.DispenserTradeTableLoader;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;

@EventBusSubscriber(modid = Houblonneux.MOD_ID)
public class ModAddServerReloadListenersEvent
{
    @SubscribeEvent
    public static void addServerReloadListenersEvent(AddServerReloadListenersEvent event)
    {
        event.addListener(Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "dispenser_trade_table"), new DispenserTradeTableLoader());
    }
}