package com.unpainperdu.houblonneux.server.packs.ressources.trade.dispenser;

import com.unpainperdu.houblonneux.Houblonneux;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.HashMap;
import java.util.Map;

public class DispenserTradeTableLoader extends SimpleJsonResourceReloadListener<DispenserTradeTable>
{
    public static final Map<Identifier, DispenserTradeTable> TRADES = new HashMap<>();

    public DispenserTradeTableLoader()
    {
        super(
                DispenserTradeTable.CODEC,
                FileToIdConverter.json(
                        Houblonneux.MOD_ID + "/dispenser_trade_table"
                )
        );
    }

    @Override
    protected void apply(Map<Identifier, DispenserTradeTable> preparations, ResourceManager manager, ProfilerFiller profiler)
    {
        TRADES.clear();
        TRADES.putAll(preparations);
    }
}