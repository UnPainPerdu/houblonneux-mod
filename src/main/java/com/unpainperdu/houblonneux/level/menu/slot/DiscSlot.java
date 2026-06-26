package com.unpainperdu.houblonneux.level.menu.slot;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class DiscSlot extends Slot
{
    public DiscSlot(Container container, int slot, int x, int y)
    {
        super(container, slot, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack itemStack)
    {
        return itemStack.getItem().components().has(DataComponents.JUKEBOX_PLAYABLE);
    }
}