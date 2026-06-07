package com.unpainperdu.houblonneux.neoevent;

import com.unpainperdu.houblonneux.client.consumable_client_item.ConsumableBeerDrinkExtensions;
import com.unpainperdu.houblonneux.register.item.ModItemRegister;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

public class ModRegisterClientExtensionsEvent
{
    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event)
    {
        event.registerItem(
                new ConsumableBeerDrinkExtensions(),
                ModItemRegister.EMERALD_CALL_BOTTLE,
                ModItemRegister.EMERALD_CALL_GLASS,
                ModItemRegister.EMERALD_CALL_MUG
        );
    }
}