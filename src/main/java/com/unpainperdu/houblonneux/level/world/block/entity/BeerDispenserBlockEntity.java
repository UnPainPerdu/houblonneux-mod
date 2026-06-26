package com.unpainperdu.houblonneux.level.world.block.entity;

import com.unpainperdu.houblonneux.level.menu.block.entity.BeerDispenserMenu;
import com.unpainperdu.houblonneux.register.block.ModBlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class BeerDispenserBlockEntity extends RandomizableContainerBlockEntity
{
    private NonNullList<ItemStack> items;
    public static final int CONTAINER_SIZE = 10;
    public static final String TITLE_KEY = "block.container.houblonneux.beer_dispenser";

    public BeerDispenserBlockEntity(BlockPos worldPosition, BlockState blockState)
    {
        super(ModBlockEntityRegister.BEER_DISPENSER.get(), worldPosition, blockState);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
    }

    @Override
    protected NonNullList<ItemStack> getItems()
    {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList)
    {
        this.items = nonNullList;
    }

    @Override
    protected void loadAdditional(ValueInput input)
    {
        super.loadAdditional(input);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(input)) {
            ContainerHelper.loadAllItems(input, this.items);
        }
    }

    @Override
    protected void saveAdditional(ValueOutput output)
    {
        super.saveAdditional(output);
        if (!this.trySaveLootTable(output)) {
            ContainerHelper.saveAllItems(output, this.items);
        }
    }

    @Override
    protected Component getDefaultName()
    {
        return Component.translatable(TITLE_KEY);
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory)
    {
        return new BeerDispenserMenu(i, inventory, this);
    }

    @Override
    public int getContainerSize()
    {
        return CONTAINER_SIZE;
    }
}