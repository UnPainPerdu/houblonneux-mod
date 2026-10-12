package com.unpainperdu.houblonneux.neoevent;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.neoevent.common.InventoryChangeHelper;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;

@EventBusSubscriber(modid = Houblonneux.MOD_ID)
public class ModPlayerContainerEvent
{
    @SubscribeEvent
    public static void event(PlayerContainerEvent.Close event)
    {
        if (event.getEntity() instanceof ServerPlayer serverPlayer)
        {
            InventoryChangeHelper.trigger(serverPlayer);
        }
    }
}