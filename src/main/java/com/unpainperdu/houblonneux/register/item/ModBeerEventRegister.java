package com.unpainperdu.houblonneux.register.item;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.world.item.beer.event.BeerEvent;
import com.unpainperdu.houblonneux.level.world.item.beer.event.EmeraldCallBeerEvent;
import com.unpainperdu.houblonneux.neoevent.RegistriesRegister;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBeerEventRegister
{
    public static final DeferredRegister<BeerEvent> BEER_EVENT = DeferredRegister.create(RegistriesRegister.BEER_EVENT_REGISTRY_KEY, Houblonneux.MOD_ID);

    public static final DeferredHolder<BeerEvent, BeerEvent> EMERALD_CALL = BEER_EVENT.register(EmeraldCallBeerEvent.ID, EmeraldCallBeerEvent::new);

    public static void register(IEventBus event)
    {
        BEER_EVENT.register(event);
    }
}