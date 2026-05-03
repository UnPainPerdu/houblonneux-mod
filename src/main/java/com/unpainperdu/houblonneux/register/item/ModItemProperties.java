package com.unpainperdu.houblonneux.register.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.Consumable;

public class ModItemProperties
{
    public static final Item.Properties HOP_FLOWER_PROPERTIES = new Item.Properties()
            .food(new FoodProperties(1,0.1F, false))
            .component(DataComponents.CONSUMABLE, Consumable.builder()
                    .consumeSeconds(1.0F)
                    .build());
}
