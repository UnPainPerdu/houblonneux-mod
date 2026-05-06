package com.unpainperdu.houblonneux.level.world.item.beer.event;

import com.unpainperdu.houblonneux.level.world.item.beer.BeerType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public abstract class BeerEvent
{
    /**
     *
     * @return name used to easy build item id with @com.unpainperdu.houblonneux.level.world.item.beer.BeerType.getItemName
     */
    public abstract String getName();

    public abstract void playEvent(Level level, BlockPos pos, Entity drinker, BeerType type);
}
