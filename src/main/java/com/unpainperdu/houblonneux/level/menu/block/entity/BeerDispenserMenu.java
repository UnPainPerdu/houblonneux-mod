package com.unpainperdu.houblonneux.level.menu.block.entity;

import com.unpainperdu.houblonneux.level.menu.slot.DiscSlot;
import com.unpainperdu.houblonneux.level.world.block.entity.BeerDispenserBlockEntity;
import com.unpainperdu.houblonneux.register.ModMenuTypeRegister;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class BeerDispenserMenu extends AbstractContainerMenu
{
    private final Container container;

    public BeerDispenserMenu(int containerId, Inventory inventory)
    {
        this(containerId, inventory, new SimpleContainer(10));
    }

    public BeerDispenserMenu(int containerId, Inventory inventory, Container container)
    {
        super(ModMenuTypeRegister.BEER_DISPENSER.get(), containerId);

        checkContainerSize(container, BeerDispenserBlockEntity.CONTAINER_SIZE);
        this.container = container;
        this.container.startOpen(inventory.player);

        for (int i = 0; i < 2; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                this.addSlot(new DiscSlot(this.container, j + i * 5, 44 + j * 18, 36 + i * 18));
            }
        }

        this.addStandardInventorySlots(inventory, 8, 84);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem())
        {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < BeerDispenserBlockEntity.CONTAINER_SIZE)
            {
                if (!this.moveItemStackTo(itemstack1, BeerDispenserBlockEntity.CONTAINER_SIZE, this.slots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (index < 37) // 37 last inventory slot after it's hotbar
            {
                if (!this.moveItemStackTo(itemstack1, 37, this.slots.size(), false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else
            {
                if (!this.moveItemStackTo(itemstack1, 0, 37, false))
                {
                    return ItemStack.EMPTY;
                }
            }

            if (itemstack1.isEmpty())
            {
                slot.setByPlayer(ItemStack.EMPTY);
            }
            else
            {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player pPlayer)
    {
        return this.container.stillValid(pPlayer);
    }

    @Override
    public void removed(Player pPlayer)
    {
        super.removed(pPlayer);
        this.container.stopOpen(pPlayer);
    }
}