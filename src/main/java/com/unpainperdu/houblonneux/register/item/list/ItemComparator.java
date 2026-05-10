package com.unpainperdu.houblonneux.register.item.list;


import net.minecraft.world.item.Item;

import java.util.Comparator;

public class ItemComparator implements Comparator<Item>
{

    @Override
    public int compare(Item o1, Item o2)
    {
        String id1 = o1.toString();
        String id2 = o2.toString();

        return id1.compareTo(id2);
    }
}