package com.unpainperdu.houblonneux.level.world.item.crafting.brewing;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.List;

public record BrewingInput(FluidStack fluidStack, List<ItemStack> itemStacks) implements RecipeInput
{
    @Override
    public ItemStack getItem(int index)
    {
        return itemStacks.get(index);
    }

    @Override
    public int size()
    {
        return itemStacks.size();
    }
}