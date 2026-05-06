package com.unpainperdu.houblonneux.level.world.item.beer.event;

import com.unpainperdu.houblonneux.level.world.item.beer.BeerType;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.wanderingtrader.WanderingTrader;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class EmeraldCallBeerEvent extends BeerEvent
{
    public static final String ID = "emerald_call";

    @Override
    public String getName()
    {
        return ID;
    }

    @Override
    public void playEvent(Level level, BlockPos pos, Entity drinker, BeerType type)
    {
        if (!level.isClientSide())
        {
            BlockPos spawnPos = pos.above(1);
            List<WanderingTrader> wanderingTraders = new ArrayList<>();
            for (int i = 0; i < type.getPowerLevel(); i++)
            {
                WanderingTrader trader = EntityType.WANDERING_TRADER.spawn((ServerLevel) level, spawnPos, EntitySpawnReason.EVENT);
                if (trader != null)
                {
                    trader.setDespawnDelay((level.getRandom().nextInt(10,21))*20);
                    wanderingTraders.add(trader);
                }
            }
            wanderingTraders.forEach(level::addFreshEntity);
            level.playSound(null, spawnPos, SoundEvents.VILLAGER_CELEBRATE, SoundSource.NEUTRAL, 16F, 1.0F);
        }
    }
}