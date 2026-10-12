package com.unpainperdu.houblonneux.datagen.data.tag;

import com.unpainperdu.houblonneux.Houblonneux;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags
{
    public static final TagKey<Item> COASTER_POSABLE = create("coaster_posable");
    public static final TagKey<Item> BREWING_BARREL = create("brewing_barrel");
    public static final TagKey<Item> BEER_BOTTLE = create("beer_bottle");
    public static final TagKey<Item> BEER_GLASS = create("beer_glass");
    public static final TagKey<Item> BEER_MUG = create("beer_mug");
    public static final TagKey<Item> BEER = create("beer");

    public static TagKey<Item> create(String name)
    {
        return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, name));
    }
}