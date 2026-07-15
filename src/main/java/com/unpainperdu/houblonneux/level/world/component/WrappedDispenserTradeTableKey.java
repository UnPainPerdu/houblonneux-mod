package com.unpainperdu.houblonneux.level.world.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.unpainperdu.houblonneux.server.packs.ressources.trade.dispenser.DispenserTradeTable;
import net.minecraft.resources.ResourceKey;

public record WrappedDispenserTradeTableKey(ResourceKey<DispenserTradeTable> dispenserTradeTable)
{
    public static final Codec<WrappedDispenserTradeTableKey> CODEC = RecordCodecBuilder.create(
            i -> i.group(
                            DispenserTradeTable.KEY_CODEC.fieldOf("dispenser_trade_table").forGetter(WrappedDispenserTradeTableKey::dispenserTradeTable)
                    )
                    .apply(i, WrappedDispenserTradeTableKey::new)
    );
}