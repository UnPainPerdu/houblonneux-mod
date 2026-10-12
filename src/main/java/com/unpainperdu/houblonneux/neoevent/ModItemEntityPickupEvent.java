package com.unpainperdu.houblonneux.neoevent;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.neoevent.common.InventoryChangeHelper;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;

@EventBusSubscriber(modid = Houblonneux.MOD_ID)
public class ModItemEntityPickupEvent
{
    @SubscribeEvent
    public static void event(ItemEntityPickupEvent.Post event)
    {
        if (event.getPlayer() instanceof ServerPlayer serverPlayer)
        {
            InventoryChangeHelper.trigger(serverPlayer);
        }
    }
}
