package com.unpainperdu.houblonneux.level.world.block.entity;

import com.unpainperdu.houblonneux.level.menu.block.entity.BeerDispenserMenu;
import com.unpainperdu.houblonneux.level.world.item.trading.DispenserTrade;
import com.unpainperdu.houblonneux.register.block.ModBlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.JukeboxSongPlayer;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.List;

public class BeerDispenserBlockEntity extends RandomizableContainerBlockEntity
{
    private NonNullList<ItemStack> items;
    public static final int CONTAINER_SIZE = 10;
    public static final String TITLE_KEY = "block.container.houblonneux.beer_dispenser";
    private DispenserTrade trade;
    public static final int BASIC_MAX_CD = 9000;
    public static final int BASIC_MAX_CD_ON_TRADE = 6000;
    private int musicCooldown;
    private final JukeboxSongPlayer jukeboxSongPlayer = new JukeboxSongPlayer(this::onSongChanged, this.getBlockPos());

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
        if (!this.tryLoadLootTable(input))
        {
            ContainerHelper.loadAllItems(input, this.items);
        }
        this.musicCooldown = input.getIntOr("music_cooldown", 0);
        input.read("trade", DispenserTrade.CODEC).ifPresent(this::setTrade);
    }

    @Override
    protected void saveAdditional(ValueOutput output)
    {
        super.saveAdditional(output);
        if (!this.trySaveLootTable(output))
        {
            ContainerHelper.saveAllItems(output, this.items);
        }
        output.storeNullable("trade", DispenserTrade.CODEC, this.getTrade());
        output.putInt("music_cooldown", this.musicCooldown);
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

    public void setTrade(DispenserTrade trade)
    {
        this.trade = trade;
    }

    public DispenserTrade getTrade()
    {
        return this.trade;
    }

    public int getMusicCooldown()
    {
        return this.musicCooldown;
    }

    public void onSongChanged()
    {
        if (this.level != null)
        {
            this.level.updateNeighborsAt(this.getBlockPos(), this.getBlockState().getBlock());
        }
        this.setChanged();
    }

    public static void tick(ServerLevel level, BlockPos pos, BlockState state, BeerDispenserBlockEntity blockEntity)
    {
        if (blockEntity.musicCooldown < BeerDispenserBlockEntity.BASIC_MAX_CD)
        {
            blockEntity.musicCooldown++;
        }
        else if (level.getRandom().nextFloat() < 0.00005F)
        {
            blockEntity.playDisc();
        }
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state)
    {
        super.preRemoveSideEffects(pos, state);
        if (this.level != null)
        {
            this.jukeboxSongPlayer.stop(this.level, state);
        }
    }

    public void playDisc()
    {
        if (this.level != null)
        {
            List<ItemStack> discs = getAvailableDiscs();
            if (!discs.isEmpty())
            {
                JukeboxSong.fromStack(discs.get(this.level.getRandom().nextInt(discs.size()))).ifPresent(holder ->
                {
                    this.jukeboxSongPlayer.play(this.level, holder);
                    this.musicCooldown = 0;
                });
            }
        }
    }

    private List<ItemStack> getAvailableDiscs()
    {
        return this.getItems().stream().filter(itemStack -> itemStack.getItem().components().has(DataComponents.JUKEBOX_PLAYABLE)).toList();
    }
}