package com.unpainperdu.houblonneux.register.item.list;

import com.unpainperdu.houblonneux.Houblonneux;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemList
{
    /**
     * Little reminder that's ALL items, so item from blocks included
     **/
    public static List<Item> ALL_ITEMS = generateAllItemsList();

    private static List<Item> generateAllItemsList()
    {
        Object[] allBlocksRegistered = BuiltInRegistries.ITEM.stream().toArray();

        List<Item> allItems = new ArrayList<>();

        for (Object obj : allBlocksRegistered)
        {
            if (obj instanceof Item item)
            {
                if (BuiltInRegistries.ITEM.getKey(item).getNamespace().equals(Houblonneux.MOD_ID))
                {
                    allItems.add(item);
                }
            }
        }

        allItems.sort(new ItemComparator());

        return allItems;
    }

    public static List<Item> getAllItemsFromClass(Class<?>... cList)
    {
        List<Item> list = new ArrayList<>();
        for (Item item : ALL_ITEMS)
        {
            for (Class<?> c : cList)
            {
                if (c.isInstance(item))
                {
                    list.add(item);
                }
            }

        }
        return list;
    }
}