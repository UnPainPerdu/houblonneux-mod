package com.unpainperdu.houblonneux.level.world.item.beer;

import com.unpainperdu.houblonneux.level.world.item.beer.event.BeerEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class BeerItem extends Item
{
    private final BeerType type;
    private final Supplier<BeerEvent> eventSupplier;

    public BeerItem(Properties properties, BeerType type, Supplier<BeerEvent> eventSupplier)
    {
        super(properties);
        this.type = type;
        this.eventSupplier = eventSupplier;
    }

    public BeerType getType()
    {
        return type;
    }

    public BeerEvent getEvent()
    {
        return this.eventSupplier.get();
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity)
    {
        this.getEvent().playEvent(level, entity.getOnPos(), entity, this.getType());
        return super.finishUsingItem(itemStack, level, entity);
    }
}