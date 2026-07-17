package com.unpainperdu.houblonneux.neoevent;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.client.consumable_client_item.ConsumableBeerDrinkExtensions;
import com.unpainperdu.houblonneux.register.item.list.ItemList;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.registries.DeferredItem;

@EventBusSubscriber(modid = Houblonneux.MOD_ID)
public class ModRegisterClientExtensionsEvent
{
    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event)
    {
        event.registerItem(
                new ConsumableBeerDrinkExtensions(),
                ItemList.BEER_ITEM.stream().map(DeferredItem::asItem).toList().toArray(new Item[0])
        );
    }
}