package com.unpainperdu.houblonneux.server.packs.ressources.trade.dispenser;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.unpainperdu.houblonneux.level.world.item.trading.DispenserTrade;

public record DispenserTradeEntry(DispenserTrade dispenserTrade,
                                  int weight)
{
    public static final Codec<DispenserTradeEntry> CODEC = RecordCodecBuilder.create(
            (instance) -> instance.group(
                            DispenserTrade.CODEC.fieldOf("dispenser_trade").forGetter((dispenserTradeEntry) -> dispenserTradeEntry.dispenserTrade),
                            Codec.INT.fieldOf("weight").forGetter((dispenserTradeEntry) -> dispenserTradeEntry.weight)
                    )
                    .apply(instance, DispenserTradeEntry::new)
    );
}