package com.unpainperdu.houblonneux.neoevent;

import com.unpainperdu.houblonneux.client.consumable_client_item.ConsumableBeerDrinkExtensions;
import com.unpainperdu.houblonneux.level.world.item.beer.BeerItem;
import com.unpainperdu.houblonneux.register.item.list.ItemList;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

public class ModRegisterClientExtensionsEvent
{
    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event)
    {
        event.registerItem(
                new ConsumableBeerDrinkExtensions(),
                ItemList.getAllItemsFromClass(BeerItem.class).toArray(new Item[0])
        );
    }
}