package com.unpainperdu.houblonneux.level.world.block.entity;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.menu.block.entity.BrewingBarrelMenu;
import com.unpainperdu.houblonneux.register.block.ModBlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.fluid.FluidStacksResourceHandler;
import org.jspecify.annotations.Nullable;

import java.util.Arrays;

public class BrewingBarrelBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer
{
    public static final int WATER_INPUT_SLOT = 0;
    public static final int[] SLOTS_FOR_INGREDIENT = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
    public static final int CONTAINER_SIZE = 17;
    public static final String TITLE_KEY = "container." + Houblonneux.MOD_ID + ".brewing_barrel";
    public static int TANK_CAPACITY = 4000;

    private NonNullList<ItemStack> items = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
    private final FluidStacksResourceHandler fluidStacksResourceHandler = new FluidStacksResourceHandler(1, TANK_CAPACITY)
    {
        @Override
        protected void onContentsChanged(int index, FluidStack previousContents)
        {
            BrewingBarrelBlockEntity.this.setChanged();
        }
    };

    public BrewingBarrelBlockEntity(BlockPos worldPosition, BlockState blockState)
    {
        super(ModBlockEntityRegister.BREWING_BARREL.get(), worldPosition, blockState);
    }

    @Override
    protected Component getDefaultName()
    {
        return Component.translatable(TITLE_KEY);
    }

    @Override
    protected NonNullList<ItemStack> getItems()
    {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items)
    {
        this.items = items;
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory)
    {
        return new BrewingBarrelMenu(containerId, inventory, this);
    }

    @Override
    public int getContainerSize()
    {
        return CONTAINER_SIZE;
    }

    @Override
    protected void saveAdditional(ValueOutput output)
    {
        super.saveAdditional(output);
        ContainerHelper.saveAllItems(output, this.items);
        this.fluidStacksResourceHandler.serialize(output);
    }

    @Override
    protected void loadAdditional(ValueInput input)
    {
        super.loadAdditional(input);
        ContainerHelper.loadAllItems(input, this.items);
        this.fluidStacksResourceHandler.deserialize(input);
    }

    public static void tick(ServerLevel level, BlockPos pos, BlockState state, BrewingBarrelBlockEntity blockEntity)
    {
        blockEntity.handleWaterSlot();
    }

    public void handleWaterSlot()
    {
        ItemStack waterInputStack = this.getItem(WATER_INPUT_SLOT);
        if (waterInputStack.is(Tags.Items.BUCKETS_WATER) && this.getFluidStack().getAmount() <= TANK_CAPACITY - 1000)
        {
            if (this.getFluidStack().isEmpty())
            {
                this.setFluidStack(new FluidStack(Fluids.WATER, 1000));
            }
            else if (this.getFluidStack().is(FluidTags.WATER))
            {
                this.getFluidStack().grow(1000);
            }
            this.setItem(WATER_INPUT_SLOT, new ItemStack(Items.BUCKET));
            this.setChanged();
        }
    }

    public FluidStack getFluidStack()
    {
        return fluidStacksResourceHandler.copyToList().getFirst();
    }

    public void setFluidStack(FluidStack fluidStack)
    {
        this.fluidStacksResourceHandler.set(0, FluidResource.of(fluidStack), fluidStack.getAmount());
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries)
    {
        return saveWithoutMetadata(registries);
    }

    @Override
    public void writeClientSideData(AbstractContainerMenu menu, RegistryFriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(this.getBlockPos());
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void setChanged()
    {
        if (level instanceof ServerLevel serverLevel)
        {
            serverLevel.getChunkSource().blockChanged(getBlockPos());
        }
        super.setChanged();
    }

    public boolean handleTrashButtonClicked(Player player)
    {
        boolean isUsed = false;
        Level level = this.getLevel();
        if (level != null)
        {
            if (!this.getFluidStack().isEmpty())
            {
                this.setFluidStack(new FluidStack(Fluids.EMPTY, 0));
                isUsed = true;
            }
            if (level.isClientSide())
            {
                level.playSound(player, this.getBlockPos(), SoundEvents.UI_BUTTON_CLICK.value(), SoundSource.BLOCKS, 0.5F, 1.0F);
            }
        }
        return isUsed;
    }

    @Override
    public int[] getSlotsForFace(Direction direction)
    {
        if (direction == Direction.UP || direction == Direction.DOWN)
        {
            return new int[]{WATER_INPUT_SLOT};
        }
        return SLOTS_FOR_INGREDIENT;
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack itemStack, @Nullable Direction direction)
    {
        return this.canPlaceItem(slot, itemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack itemStack, Direction direction)
    {
        return (slot == WATER_INPUT_SLOT && itemStack.is(Items.BUCKET));
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack itemStack)
    {
        return (
                slot == WATER_INPUT_SLOT && itemStack.is(Items.WATER_BUCKET)
                || Arrays.stream(SLOTS_FOR_INGREDIENT).anyMatch(x -> x == slot)
        );
    }
}