package com.unpainperdu.houblonneux.level.world.block.entity;

import com.unpainperdu.houblonneux.level.menu.block.entity.BeerDispenserMenu;
import com.unpainperdu.houblonneux.level.world.component.ImmediateRollTable;
import com.unpainperdu.houblonneux.level.world.component.WrappedDispenserTradeTableKey;
import com.unpainperdu.houblonneux.level.world.item.trading.DispenserTrade;
import com.unpainperdu.houblonneux.register.ModDataComponentRegister;
import com.unpainperdu.houblonneux.register.block.ModBlockEntityRegister;
import com.unpainperdu.houblonneux.server.packs.ressources.trade.dispenser.DispenserTradeTable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.JukeboxSongPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class BeerDispenserBlockEntity extends RandomizableContainerBlockEntity implements RandomizableDispenserTrade
{
    private NonNullList<ItemStack> items;
    public static final int CONTAINER_SIZE = 10;
    public static final String TITLE_KEY = "block.container.houblonneux.beer_dispenser";
    private DispenserTrade trade;
    public static final int BASIC_MAX_CD = 9000;
    public static final int BASIC_MAX_CD_ON_TRADE = 6000;
    private int musicCooldown;
    private final JukeboxSongPlayer jukeboxSongPlayer = new JukeboxSongPlayer(this::onSongChanged, this.getBlockPos());
    protected @Nullable ResourceKey<DispenserTradeTable> dispenserTradeTable;
    private boolean isImmediatelyRollingTable;

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
    public void onLoad()
    {
        super.onLoad();
        if (this.isImmediatelyRollingTable())
        {
            this.unpackLootTable(null);
            this.unpackDispenserTradeTable(this.level);
            this.updateBeAndBlock();
        }
    }

    @Override
    public boolean isEmpty()
    {
        this.unpackDispenserTradeTable(this.level);
        this.updateBeAndBlock();
        return super.isEmpty();
    }

    @Override
    public ItemStack getItem(int slot)
    {
        this.unpackDispenserTradeTable(this.level);
        this.updateBeAndBlock();
        return super.getItem(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int count)
    {
        this.unpackDispenserTradeTable(this.level);
        this.updateBeAndBlock();
        return super.removeItem(slot, count);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot)
    {
        this.unpackDispenserTradeTable(this.level);
        this.updateBeAndBlock();
        return super.removeItemNoUpdate(slot);
    }

    @Override
    public void setItem(int slot, ItemStack itemStack)
    {
        this.unpackDispenserTradeTable(this.level);
        this.updateBeAndBlock();
        super.setItem(slot, itemStack);
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player)
    {
        if (this.canOpen(player))
        {
            this.unpackLootTable(inventory.player);
            this.unpackDispenserTradeTable(this.level);
            this.updateBeAndBlock();
            return this.createMenu(containerId, inventory);
        }
        else
        {
            if (!player.isSpectator())
            {
                BaseContainerBlockEntity.sendChestLockedNotifications(this.getBlockPos().getCenter(), player, this.getDisplayName());
            }

            return null;
        }
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
        if (!this.tryLoadDispenserTradeTable(input))
        {
            input.read("trade", DispenserTrade.CODEC).ifPresent(this::setDispenserTrade);
        }
        this.isImmediatelyRollingTable = input.getBooleanOr(DEFAULT_NBT_NAME, true);
    }

    @Override
    protected void saveAdditional(ValueOutput output)
    {
        super.saveAdditional(output);
        if (!this.trySaveLootTable(output))
        {
            ContainerHelper.saveAllItems(output, this.items);
        }
        if (!this.trySaveDispenserTradeTable(output))
        {
            output.storeNullable("trade", DispenserTrade.CODEC, this.getTrade());
        }
        output.putInt("music_cooldown", this.musicCooldown);
        output.putBoolean(DEFAULT_NBT_NAME, this.isImmediatelyRollingTable);
    }

    @Override
    protected void applyImplicitComponents(DataComponentGetter components)
    {
        super.applyImplicitComponents(components);
        WrappedDispenserTradeTableKey wrappedDispenserTradeTableKey = components.get(ModDataComponentRegister.WRAPPED_DISPENSER_TRADE_TABLE_KEY);
        if (wrappedDispenserTradeTableKey != null)
        {
            this.setDispenserTradeTable(wrappedDispenserTradeTableKey.dispenserTradeTable());
        }
        ImmediateRollTable immediateRollTable = components.get(ModDataComponentRegister.IMMEDIATE_ROLL_TABLE);
        if (immediateRollTable != null)
        {
            this.setImmediatelyRollingTable(immediateRollTable.isImmediatelyRollingTable());
        }
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder components)
    {
        super.collectImplicitComponents(components);
        if (this.dispenserTradeTable != null)
        {
            components.set(ModDataComponentRegister.WRAPPED_DISPENSER_TRADE_TABLE_KEY, new WrappedDispenserTradeTableKey(this.getDispenserTradeTable()));
        }
        components.set(ModDataComponentRegister.IMMEDIATE_ROLL_TABLE, new ImmediateRollTable(this.isImmediatelyRollingTable()));
    }

    @Override
    public void removeComponentsFromTag(ValueOutput output)
    {
        super.removeComponentsFromTag(output);
        output.discard("DispenserTradeTable");
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries)
    {
        return this.saveWithoutMetadata(registries);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
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

    public boolean isImmediatelyRollingTable()
    {
        return this.isImmediatelyRollingTable;
    }

    public void setImmediatelyRollingTable(boolean isImmediatelyRollingTable)
    {
        this.isImmediatelyRollingTable = isImmediatelyRollingTable;
    }

    @Override
    public int getContainerSize()
    {
        return CONTAINER_SIZE;
    }

    @Override
    public void setDispenserTrade(DispenserTrade trade)
    {
        this.trade = trade;
    }

    public DispenserTrade getTrade()
    {
        return this.trade;
    }

    @Override
    public @Nullable ResourceKey<DispenserTradeTable> getDispenserTradeTable()
    {
        return this.dispenserTradeTable;
    }

    @Override
    public void setDispenserTradeTable(@Nullable ResourceKey<DispenserTradeTable> dispenserTradeTable)
    {
        this.dispenserTradeTable = dispenserTradeTable;
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

    public void trade(ServerPlayer serverPlayer, Level level)
    {
        ItemStack result = this.trade.result().create();
        if (!serverPlayer.addItem(result))
        {
            BlockPos playerPos = serverPlayer.getOnPos().above();
            level.addFreshEntity(new ItemEntity(level, playerPos.getX(), playerPos.getY(), playerPos.getZ(), result));
        }
        if (this.getMusicCooldown() >= BASIC_MAX_CD_ON_TRADE)
        {
            this.playDisc();
        }
    }

    private List<ItemStack> getAvailableDiscs()
    {
        return this.getItems().stream().filter(itemStack -> itemStack.getItem().components().has(DataComponents.JUKEBOX_PLAYABLE)).toList();
    }

    private void updateBeAndBlock()
    {
        this.setChanged();
        if (level != null && !level.isClientSide())
        {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }
}