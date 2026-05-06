package com.unpainperdu.houblonneux.neoevent;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.world.item.beer.event.BeerEvent;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class RegistriesRegister
{
    public static final ResourceKey<Registry<BeerEvent>> BEER_EVENT_REGISTRY_KEY = ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "beer_event"));
    public static final Registry<BeerEvent> BEER_EVENT_REGISTRY = new RegistryBuilder<>(BEER_EVENT_REGISTRY_KEY).create();

    public static void registerRegistries(NewRegistryEvent event) {
        event.register(BEER_EVENT_REGISTRY);
    }
}