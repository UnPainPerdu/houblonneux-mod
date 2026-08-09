package com.unpainperdu.houblonneux.level.menu.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class WaterInputSlot extends Slot
{
    public WaterInputSlot(Container container, int slot, int x, int y)
    {
        super(container, slot, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack itemStack)
    {
        return itemStack.is(Items.WATER_BUCKET);
    }
}