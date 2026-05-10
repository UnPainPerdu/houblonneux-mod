package com.unpainperdu.houblonneux.register.extensible_enum;

import net.minecraft.world.item.ItemUseAnimation;

public enum ModItemUseAnimation
{
    //Remember, need definition in ./templates/META-INF/enumextensions.json
    BEER_DRINK("HOUBLONNEUX_BEER_DRINK");
    private final ItemUseAnimation itemUseAnimation;

    ModItemUseAnimation(String realEnumName)
    {
        this.itemUseAnimation = ItemUseAnimation.valueOf(realEnumName);
    }

    public ItemUseAnimation get()
    {
        return itemUseAnimation;
    }
}