package com.unpainperdu.houblonneux.datagen.data.tag;

import com.unpainperdu.houblonneux.Houblonneux;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags
{
    public static final TagKey<Item> COASTER_POSABLE = create("coaster_posable");

    public static TagKey<Item> create(String name) {
        return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, name));
    }
}