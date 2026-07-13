package com.unpainperdu.houblonneux.level.world.block.entity;

import com.mojang.logging.LogUtils;
import com.unpainperdu.houblonneux.register.block.ModBlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.slf4j.Logger;

public class CoasterBlockEntity extends BlockEntity implements Container
{
    public static final int CONTAINER_SIZE = 4;
    private static final Logger LOGGER = LogUtils.getLogger();

    private NonNullList<ItemStack> items;
    int interactionCD;

    public CoasterBlockEntity(BlockPos worldPosition, BlockState blockState)
    {
        super(ModBlockEntityRegister.COASTER.get(), worldPosition, blockState);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
    }

    @Override
    public int getContainerSize()
    {
        return CONTAINER_SIZE;
    }

    @Override
    public boolean isEmpty()
    {
        for (ItemStack itemStack : this.getItems())
        {
            if (!itemStack.isEmpty())
            {
                return false;
            }
        }
        return true;
    }

    public NonNullList<ItemStack> getItems()
    {
        return this.items;
    }

    public void setItems(NonNullList<ItemStack> items)
    {
        this.items = items;
    }

    @Override
    public ItemStack getItem(int slot)
    {
        return this.getItems().get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int count)
    {
        ItemStack result = ContainerHelper.removeItem(this.getItems(), slot, count);
        if (!result.isEmpty())
        {
            this.setChanged();
        }

        return result;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot)
    {
        return ContainerHelper.takeItem(this.getItems(), slot);
    }

    @Override
    public void setItem(int slot, ItemStack itemStack)
    {
        this.setItem(slot, itemStack, false);
    }

    public void setItem(int slot, ItemStack itemStack, boolean insideTransaction)
    {
        this.getItems().set(slot, itemStack);
        itemStack.limitSize(this.getMaxStackSize(itemStack));
        if (!insideTransaction)
        {
            this.setChanged();
        }
    }

    @Override
    public boolean stillValid(Player player)
    {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent()
    {
        this.getItems().clear();
    }

    @Override
    protected void loadAdditional(ValueInput input)
    {
        super.loadAdditional(input);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(input, this.items);
        this.interactionCD = input.getIntOr("interaction_cd", 0);
    }

    @Override
    protected void saveAdditional(ValueOutput output)
    {
        super.saveAdditional(output);
        ContainerHelper.saveAllItems(output, this.items);
        output.putInt("interaction_cd", this.interactionCD);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries)
    {
        CompoundTag compoundTag;
        try (ProblemReporter.ScopedCollector reporter = new ProblemReporter.ScopedCollector(this.problemPath(), LOGGER))
        {
            TagValueOutput output = TagValueOutput.createWithContext(reporter, registries);
            ContainerHelper.saveAllItems(output, this.items, true);
            compoundTag = output.buildResult();
        }
        return compoundTag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    protected void applyImplicitComponents(DataComponentGetter components)
    {
        super.applyImplicitComponents(components);
        components.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY).copyInto(this.getItems());
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder components)
    {
        super.collectImplicitComponents(components);
        components.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(this.getItems()));
    }

    public void tryPlaceItem(Level level, ItemStack stackToPlace, int slot)
    {
        ItemStack stackInSlot = this.getItem(slot);
        ItemStack copyStackToPlace = stackToPlace.copy();
        if (stackInSlot.isEmpty())
        {
            copyStackToPlace.setCount(1);
            this.setItem(slot, copyStackToPlace);
            stackToPlace.shrink(1);
            this.markUpdated(level);
        }
    }

    public void tryTakeItem(Level level, Player player, int slot)
    {
        ItemStack stackInSlot = this.getItem(slot);
        if (!stackInSlot.isEmpty())
        {
            if (!player.addItem(stackInSlot))
            {
                level.addFreshEntity(new ItemEntity(level, player.getOnPos().getX(), player.getOnPos().getY(), player.getOnPos().getZ(), stackInSlot));
            }
            stackInSlot.shrink(1);
            this.markUpdated(level);
        }
    }

    private void markUpdated(Level level)
    {
        this.setChanged();
        level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }
}