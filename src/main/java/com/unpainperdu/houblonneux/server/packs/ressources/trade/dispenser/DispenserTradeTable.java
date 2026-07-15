package com.unpainperdu.houblonneux.server.packs.ressources.trade.dispenser;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.unpainperdu.houblonneux.level.world.item.trading.DispenserTrade;
import com.unpainperdu.houblonneux.register.registry.ModDataPackRegistriesRegister;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;

import java.util.*;

public record DispenserTradeTable(List<DispenserTradeEntry> dispenserTradeEntries,
                                  List<DispenserTradeTagEntry> dispenserTradeTagEntries)
{
    public static final Codec<DispenserTradeTable> CODEC = RecordCodecBuilder.create(
            (instance) -> instance.group(
                            Codec.list(DispenserTradeEntry.CODEC).fieldOf("dispenser_trade_entries").forGetter((dispenserTradeTable) -> dispenserTradeTable.dispenserTradeEntries),
                            Codec.list(DispenserTradeTagEntry.CODEC).fieldOf("dispenser_trade_tag_entiries").forGetter((dispenserTradeTable) -> dispenserTradeTable.dispenserTradeTagEntries)
                    )
                    .apply(instance, DispenserTradeTable::new)
    );
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final Codec<ResourceKey<DispenserTradeTable>> KEY_CODEC = ResourceKey.codec(ModDataPackRegistriesRegister.DISPENSER_TRADE_TABLE);

    public Optional<DispenserTrade> getDispenserTrade(Level level)
    {
        Map<DispenserTrade, Integer> tradeAndWeight = new HashMap<>();
        if (this.dispenserTradeEntries != null && !this.dispenserTradeEntries.isEmpty())
        {
            for (DispenserTradeEntry entry : this.dispenserTradeEntries)
            {
                tradeAndWeight.put(entry.dispenserTrade(), entry.weight());
            }
        }
        if (this.dispenserTradeTagEntries != null && !this.dispenserTradeTagEntries.isEmpty())
        {
            for (DispenserTradeTagEntry tagEntry : this.dispenserTradeTagEntries)
            {
                Registry<DispenserTrade> dispenserTradeRegistry = level.registryAccess().lookupOrThrow(ModDataPackRegistriesRegister.DISPENSER_TRADE);
                Iterable<Holder<DispenserTrade>> dtHolders = dispenserTradeRegistry.getTagOrEmpty(tagEntry.dispenserTradeTag());
                for (Holder<DispenserTrade> dtHolder : dtHolders)
                {
                    tradeAndWeight.put(dtHolder.value(), tagEntry.weight());
                }
            }
        }
        if (tradeAndWeight.isEmpty())
        {
            return Optional.empty();
        }

        int totalWeight = tradeAndWeight.values().stream().reduce(0, Integer::sum);

        int randomInt = level.getRandom().nextInt(totalWeight);

        int cumulative = 0;
        for (Map.Entry<DispenserTrade, Integer> entry : tradeAndWeight.entrySet())
        {
            cumulative += entry.getValue();
            if (randomInt < cumulative)
            {
                return Optional.ofNullable(entry.getKey());
            }
        }

        LOGGER.error("dispenserTradeTable note empty but failed getting dispenserTrade, return empty optional");
        return Optional.empty();
    }

    public static DispenserTradeTable.Builder getBuilder()
    {
        return new DispenserTradeTable.Builder();
    }

    public static class Builder
    {
        private final List<DispenserTradeEntry> dispenserTradeEntries = new ArrayList<>();
        private final List<DispenserTradeTagEntry> dispenserTradeTagEntries = new ArrayList<>();

        public Builder addDispenserTradeEntry(DispenserTrade dispenserTrade)
        {
            return this.addDispenserTradeEntry(dispenserTrade, 1);
        }

        public Builder addDispenserTradeEntry(DispenserTrade dispenserTrade, int weight)
        {
            dispenserTradeEntries.add(new DispenserTradeEntry(dispenserTrade, weight));
            return this;
        }

        public Builder addDispenserTradeTagEntry(TagKey<DispenserTrade> dispenserTradeTag)
        {
            return this.addDispenserTradeTagEntry(dispenserTradeTag, 1);
        }

        public Builder addDispenserTradeTagEntry(TagKey<DispenserTrade> dispenserTradeTag, int weight)
        {
            dispenserTradeTagEntries.add(new DispenserTradeTagEntry(dispenserTradeTag, weight));
            return this;
        }

        public DispenserTradeTable build()
        {
            if (dispenserTradeEntries.isEmpty() && dispenserTradeTagEntries.isEmpty())
            {
                throw new RuntimeException("dispenserTradeEntries or dispenserTradeTagEntries must contain at least one entry");
            }
            for (DispenserTradeEntry dispenserTradeEntry : dispenserTradeEntries)
            {
                if (dispenserTradeEntry.weight() < 1)
                {
                    throw new RuntimeException("dispenserTradeEntry weight must be greater than zero");
                }
            }
            for (DispenserTradeTagEntry dispenserTradeTagEntry : dispenserTradeTagEntries)
            {
                if (dispenserTradeTagEntry.weight() < 1)
                {
                    throw new RuntimeException("dispenserTradeTagEntry weight must be greater than zero");
                }
            }

            return new DispenserTradeTable(this.dispenserTradeEntries, this.dispenserTradeTagEntries);
        }
    }
}