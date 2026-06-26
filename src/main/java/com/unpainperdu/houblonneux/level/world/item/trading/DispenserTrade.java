package com.unpainperdu.houblonneux.level.world.item.trading;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.unpainperdu.houblonneux.register.registry.ModDataPackRegistriesRegister;
import net.minecraft.core.Registry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public record DispenserTrade(ItemStackTemplate cost, ItemStackTemplate result)
{
    public static final Codec<DispenserTrade> CODEC = RecordCodecBuilder.create(
            (instance) -> instance.group(
                            ItemStackTemplate.CODEC.fieldOf("cost").forGetter((dispenserTrade) -> dispenserTrade.cost),
                            ItemStackTemplate.CODEC.fieldOf("result").forGetter((dispenserTrade) -> dispenserTrade.result)
                    )
                    .apply(instance, DispenserTrade::new)
    );

    public boolean buy(ItemStack buy, boolean mustPay)
    {
        if (!this.satisfiedBy(buy))
        {
            return false;
        }
        else
        {
            if (mustPay)
            {
                buy.shrink(this.cost.count());
            }
            return true;
        }
    }

    public boolean satisfiedBy(ItemStack buy)
    {
        return buy.is(this.cost.item()) && buy.getCount() >= this.cost.count();
    }

    public static @Nullable DispenserTrade getTradeFromCost(Level level, ItemStack possibleCost)
    {
        Registry<DispenserTrade> registry = level.registryAccess().lookupOrThrow(ModDataPackRegistriesRegister.DISPENSER_TRADE);
        for (DispenserTrade dispenserTrade : registry.stream().toList())
        {
            if (dispenserTrade.result().create().is(possibleCost.getItem()))
            {
                return dispenserTrade;
            }
        }
        return null;
    }
}