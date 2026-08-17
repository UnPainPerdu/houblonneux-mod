package com.unpainperdu.houblonneux.level.world.block.entity;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.menu.block.entity.BrewingBarrelMenu;
import com.unpainperdu.houblonneux.level.world.item.crafting.brewing_barrel.brewing.BrewingInput;
import com.unpainperdu.houblonneux.level.world.item.crafting.brewing_barrel.brewing.BrewingRecipe;
import com.unpainperdu.houblonneux.level.world.item.crafting.brewing_barrel.pumping.PumpingInput;
import com.unpainperdu.houblonneux.level.world.item.crafting.brewing_barrel.pumping.PumpingRecipe;
import com.unpainperdu.houblonneux.register.ModDataComponentRegister;
import com.unpainperdu.houblonneux.register.block.ModBlockEntityRegister;
import com.unpainperdu.houblonneux.register.recipe.ModRecipeTypeRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
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

    private final FluidStacksResourceHandler fluidStacksResourceHandler = new FluidStacksResourceHandler(1, TANK_CAPACITY)
    {
        @Override
        protected void onContentsChanged(int index, FluidStack previousContents)
        {
            BrewingBarrelBlockEntity.this.setChanged();
        }
    };
    private final RecipeManager.CachedCheck<BrewingInput, ? extends BrewingRecipe> quickCheckBrewing;
    private final RecipeManager.CachedCheck<PumpingInput, ? extends PumpingRecipe> quickCheckPumping;

    private NonNullList<ItemStack> items = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
    private int brewingTime;

    public BrewingBarrelBlockEntity(BlockPos worldPosition, BlockState blockState)
    {
        super(ModBlockEntityRegister.BREWING_BARREL.get(), worldPosition, blockState);
        this.quickCheckBrewing = RecipeManager.createCheck(ModRecipeTypeRegister.BREWING_RECIPE.get());
        this.quickCheckPumping = RecipeManager.createCheck(ModRecipeTypeRegister.PUMPING_RECIPE.get());
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
        output.putInt("brewingTime", this.brewingTime);
    }

    @Override
    protected void loadAdditional(ValueInput input)
    {
        super.loadAdditional(input);
        ContainerHelper.loadAllItems(input, this.items);
        this.fluidStacksResourceHandler.deserialize(input);
        this.brewingTime = input.getIntOr("brewingTime", 0);
    }

    public static void tick(ServerLevel level, BlockPos pos, BlockState state, BrewingBarrelBlockEntity blockEntity)
    {
        blockEntity.handleWaterSlot();
        blockEntity.handleBrewing();
    }

    private void handleWaterSlot()
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
            level.playSound(null, this.getBlockPos(), SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS);
            this.setChanged();
        }
    }

    private void handleBrewing()
    {
        FluidStack fluidStack = this.getFluidStack();
        if (this.level instanceof ServerLevel serverLevel && !fluidStack.isEmpty() && this.getItems().stream().anyMatch(itemStack -> !itemStack.isEmpty()))
        {
            RecipeHolder<? extends BrewingRecipe> recipeholder = this.quickCheckBrewing.getRecipeFor(new BrewingInput(fluidStack, this.getItems()), serverLevel).orElse(null); //TODO, handle recipe change -> brewingTime reset
            if (recipeholder != null)
            {
                if (this.brewingTime >= recipeholder.value().brewingTime())
                {
                    this.brew(recipeholder.value());
                }
                else
                {
                    this.brewingTime += 1;
                }
                return;
            }
        }
        this.brewingTime = 0;
    }

    private void brew(BrewingRecipe recipe)
    {
        this.getItems().forEach(itemStack -> itemStack.shrink(1));
        this.setFluidStack(recipe.assembleFluidStack());
        level.playSound(null, this.getBlockPos(), SoundEvents.BREWING_STAND_BREW, SoundSource.BLOCKS, 1.0F, 0.4F);
        this.setChanged();
    }

    /**
     * @return true if item in hand successfully pump fluid in barrel
     */
    public boolean handlePumping(ItemStack stackUsed, ServerPlayer player)
    {
        FluidStack fluidStack = this.getFluidStack();
        if (this.level instanceof ServerLevel serverLevel && !fluidStack.isEmpty() && !stackUsed.isEmpty())
        {
            PumpingInput input = new PumpingInput(fluidStack, stackUsed);
            RecipeHolder<? extends PumpingRecipe> recipeholder = this.quickCheckPumping.getRecipeFor(input, serverLevel).orElse(null);
            if (recipeholder != null)
            {
                PumpingRecipe recipe = recipeholder.value();
                stackUsed.shrink(1);
                fluidStack.shrink(recipe.sizedFluidIngredient().amount());
                this.setChanged();
                ItemStack result = recipe.assemble(input);
                if (!player.addItem(result))
                {
                    ItemEntity entity = new ItemEntity(level, player.getX() + 0.5, player.getY() + 0.5, player.getZ() + 0.5, result);
                    entity.setDefaultPickUpDelay();
                    level.addFreshEntity(entity);
                }
                level.playSound(null, this.getBlockPos(), SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS);
                return true;
            }
        }
        return false;
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

    @Override
    protected void applyImplicitComponents(DataComponentGetter components)
    {
        super.applyImplicitComponents(components);
        SimpleFluidContent fluidStackDataComponent = components.getOrDefault(ModDataComponentRegister.FLUIDSTACK.get(), SimpleFluidContent.EMPTY);
        FluidStack fluidStack = fluidStackDataComponent.copy();
        if (!fluidStack.isEmpty())
        {
            this.setFluidStack(fluidStack);
        }
        Integer bt = components.get(ModDataComponentRegister.BREWING_TIME.get());
        if (bt != null)
        {
            this.brewingTime = bt;
        }
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder components)
    {
        super.collectImplicitComponents(components);
        FluidStack fluidStack = this.getFluidStack();
        if (!fluidStack.isEmpty())
        {
            components.set(ModDataComponentRegister.FLUIDSTACK.get(), SimpleFluidContent.copyOf(fluidStack));
        }
        if (this.brewingTime > 0)
        {
            components.set(ModDataComponentRegister.BREWING_TIME.get(), this.brewingTime);
        }
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state)
    {
    }
}