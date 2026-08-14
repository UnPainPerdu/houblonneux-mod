package com.unpainperdu.houblonneux.level.world.item.crafting.brewing_barrel.pumping;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.fluids.FluidStack;

public record PumpingInput(FluidStack fluidStack, ItemStack itemStack) implements RecipeInput
{
    @Override
    public ItemStack getItem(int index)
    {
        if (index != 0)
        {
            throw new IndexOutOfBoundsException("Index out of bounds for PumpingInput, should always be 1");
        }
        return this.itemStack;
    }

    @Override
    public int size()
    {
        return 1;
    }
}