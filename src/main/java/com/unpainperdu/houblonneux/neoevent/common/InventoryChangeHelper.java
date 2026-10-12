package com.unpainperdu.houblonneux.neoevent.common;

import com.unpainperdu.houblonneux.register.ModTriggerRegister;
import net.minecraft.server.level.ServerPlayer;

public class InventoryChangeHelper
{
    public static void trigger(ServerPlayer player)
    {
        ModTriggerRegister.ALL_ITEM_ITEM_TAG_TRIGGER.get().trigger(player, player.getInventory());
    }
}