package com.unpainperdu.houblonneux.register.registry;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.world.item.trading.DispenserTrade;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

@EventBusSubscriber(modid = Houblonneux.MOD_ID)
public class ModDataPackRegistriesRegister
{
    public static final ResourceKey<Registry<DispenserTrade>> DISPENSER_TRADE = ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "dispenser_trade"));

    @SubscribeEvent
    public static void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event)
    {
        event.dataPackRegistry(
                DISPENSER_TRADE,
                DispenserTrade.CODEC,
                DispenserTrade.CODEC
        );
    }
}