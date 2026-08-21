package com.unpainperdu.houblonneux.level.menu.block.entity;

import com.unpainperdu.houblonneux.client.screen.BrewingBarrelScreen;
import com.unpainperdu.houblonneux.level.menu.slot.WaterInputSlot;
import com.unpainperdu.houblonneux.level.world.block.entity.BeerDispenserBlockEntity;
import com.unpainperdu.houblonneux.level.world.block.entity.BrewingBarrelBlockEntity;
import com.unpainperdu.houblonneux.register.ModMenuTypeRegister;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.fluids.FluidStack;

public class BrewingBarrelMenu extends AbstractContainerMenu
{
    private final BrewingBarrelBlockEntity brewingBarrelBlockEntity;

    private final ContainerData data;

    public BrewingBarrelMenu(int containerId, Inventory inventory, RegistryFriendlyByteBuf data)
    {
        this(containerId, inventory, (BrewingBarrelBlockEntity) inventory.player.level().getBlockEntity(data.readBlockPos()), new SimpleContainerData(2));
    }

    public BrewingBarrelMenu(int containerId, Inventory inventory, BrewingBarrelBlockEntity brewingBarrelBlockEntity, ContainerData dataMultiple)
    {
        super(ModMenuTypeRegister.BREWING_BARREL.get(), containerId);

        checkContainerSize(brewingBarrelBlockEntity, BeerDispenserBlockEntity.CONTAINER_SIZE);
        this.brewingBarrelBlockEntity = brewingBarrelBlockEntity;
        this.brewingBarrelBlockEntity.startOpen(inventory.player);

        checkContainerDataCount(dataMultiple, 2);
        this.data = dataMultiple;
        this.addDataSlots(dataMultiple);

        this.addSlot(new WaterInputSlot(this.brewingBarrelBlockEntity, BrewingBarrelBlockEntity.WATER_INPUT_SLOT, 52, 69));

        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                this.addSlot(new Slot(this.brewingBarrelBlockEntity, 1 + (j + (i * 4)), 98 + (j * 18), 18 + (i * 18)));
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
        return this.brewingBarrelBlockEntity.stillValid(player);
    }

    public FluidStack getFluidStack()
    {
        if (this.brewingBarrelBlockEntity != null)
        {
            return this.brewingBarrelBlockEntity.getFluidStack();
        }
        return FluidStack.EMPTY;
    }

    public int getBrewingTime()
    {
        return this.data.get(0);
    }

    /**
     * @return -1 if no recipe
     */
    public Integer getCurrentRecipeMaxBrewingTime()
    {
        return this.data.get(1);
    }

    @Override
    public boolean clickMenuButton(Player player, int buttonId)
    {
        if (buttonId == BrewingBarrelScreen.TRASH_BUTTON_ID && player.gameMode() != GameType.SPECTATOR)
        {
            return this.brewingBarrelBlockEntity.handleTrashButtonClicked(player);
        }
        return super.clickMenuButton(player, buttonId);
    }
}