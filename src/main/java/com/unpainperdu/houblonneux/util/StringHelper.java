package com.unpainperdu.houblonneux.util;

import com.unpainperdu.houblonneux.Houblonneux;
import net.minecraft.world.item.Item;

public class StringHelper
{
    public static String getSimpleName(Item item)
    {
        return item.toString().replace(Houblonneux.MOD_ID + ":", "");
    }
}
