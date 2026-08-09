package com.unpainperdu.houblonneux.level.menu.block.entity;

import com.unpainperdu.houblonneux.level.menu.slot.WaterInputSlot;
import com.unpainperdu.houblonneux.level.world.block.entity.BeerDispenserBlockEntity;
import com.unpainperdu.houblonneux.level.world.block.entity.BrewingBarrelBlockEntity;
import com.unpainperdu.houblonneux.register.ModMenuTypeRegister;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class BrewingBarrelMenu extends AbstractContainerMenu
{
    private final Container container;

    public BrewingBarrelMenu(int containerId, Inventory inventory)
    {
        this(containerId, inventory, new SimpleContainer(17));
    }

    public BrewingBarrelMenu(int containerId, Inventory inventory, Container container)
    {
        super(ModMenuTypeRegister.BREWING_BARREL.get(), containerId);

        checkContainerSize(container, BeerDispenserBlockEntity.CONTAINER_SIZE);
        this.container = container;
        this.container.startOpen(inventory.player);

        this.addSlot(new WaterInputSlot(this.container, BrewingBarrelBlockEntity.WATER_INPUT_SLOT, 52, 21));

        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                this.addSlot(new Slot(this.container, 1 + (j + (i * 4)), 98 + (j * 18), 18 + (i * 18)));
            }
        }

        this.addStandardInventorySlots(inventory, 8, 102);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex)
    {
        ItemStack clicked = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);
        if (slot.hasItem())
        {
            ItemStack stack = slot.getItem();
            clicked = stack.copy();
            if (slotIndex < BrewingBarrelBlockEntity.CONTAINER_SIZE)
            {
                if (!this.moveItemStackTo(stack, BrewingBarrelBlockEntity.CONTAINER_SIZE, this.slots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.moveItemStackTo(stack, 0, BrewingBarrelBlockEntity.CONTAINER_SIZE, false))
            {
                return ItemStack.EMPTY;
            }

            if (stack.isEmpty())
            {
                slot.setByPlayer(ItemStack.EMPTY);
            }
            else
            {
                slot.setChanged();
            }
        }

        return clicked;
    }

    @Override
    public boolean stillValid(Player player)
    {
        return this.container.stillValid(player);
    }
}