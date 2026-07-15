package com.unpainperdu.houblonneux.server.packs.ressources.trade.dispenser;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.unpainperdu.houblonneux.level.world.item.trading.DispenserTrade;
import com.unpainperdu.houblonneux.register.registry.ModDataPackRegistriesRegister;
import net.minecraft.tags.TagKey;

public record DispenserTradeTagEntry(TagKey<DispenserTrade> dispenserTradeTag,
                                     int weight)
{
    public static final Codec<DispenserTradeTagEntry> CODEC = RecordCodecBuilder.create(
            (instance) -> instance.group(
                            TagKey.codec(ModDataPackRegistriesRegister.DISPENSER_TRADE).fieldOf("dispenser_trade_tag").forGetter((dispenserTradeTagEntry) -> dispenserTradeTagEntry.dispenserTradeTag),
                            Codec.INT.fieldOf("weight").forGetter((dispenserTradeTagEntry) -> dispenserTradeTagEntry.weight)
                    )
                    .apply(instance, DispenserTradeTagEntry::new)
    );
}