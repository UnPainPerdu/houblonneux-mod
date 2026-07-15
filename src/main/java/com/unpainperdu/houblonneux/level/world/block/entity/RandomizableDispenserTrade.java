package com.unpainperdu.houblonneux.level.world.block.entity;

import com.unpainperdu.houblonneux.level.world.item.trading.DispenserTrade;
import com.unpainperdu.houblonneux.server.packs.ressources.trade.dispenser.DispenserTradeTable;
import com.unpainperdu.houblonneux.server.packs.ressources.trade.dispenser.DispenserTradeTableLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public interface RandomizableDispenserTrade
{
    @Nullable ResourceKey<DispenserTradeTable> getDispenserTradeTable();

    void setDispenserTradeTable(final @Nullable ResourceKey<DispenserTradeTable> dispenserTradeTable);

    void setDispenserTrade(DispenserTrade trade);

    static void setBlockEntityDispenserTradeTable(BlockGetter level, RandomSource random, BlockPos blockEntityPos, ResourceKey<DispenserTradeTable> dispenserTradeTable)
    {
        if (level.getBlockEntity(blockEntityPos) instanceof RandomizableDispenserTrade randomizableDispenserTrade)
        {
            randomizableDispenserTrade.setDispenserTradeTable(dispenserTradeTable);
        }
    }

    default boolean tryLoadDispenserTradeTable(ValueInput base)
    {
        ResourceKey<DispenserTradeTable> dispenserTradeTable = base.read("DispenserTradeTable", DispenserTradeTable.KEY_CODEC).orElse(null);
        this.setDispenserTradeTable(dispenserTradeTable);
        return dispenserTradeTable != null;
    }

    default boolean trySaveDispenserTradeTable(ValueOutput base)
    {
        ResourceKey<DispenserTradeTable> dispenserTradeTable = this.getDispenserTradeTable();
        if (dispenserTradeTable == null)
        {
            return false;
        }
        else
        {
            base.store("DispenserTradeTable", DispenserTradeTable.KEY_CODEC, dispenserTradeTable);
            return true;
        }
    }

    default void unpackDispenserTradeTable(Level level)
    {
        ResourceKey<DispenserTradeTable> dispenserTradeTableKey = this.getDispenserTradeTable();
        if (dispenserTradeTableKey != null && level != null && level.getServer() != null)
        {
            DispenserTradeTable dispenserTradeTable = DispenserTradeTableLoader.TRADES.get(dispenserTradeTableKey.identifier());
            this.setDispenserTradeTable(null);

            Optional<DispenserTrade> optionalDispenserTrade = dispenserTradeTable.getDispenserTrade(level);
            optionalDispenserTrade.ifPresent(this::setDispenserTrade);
        }
    }
}