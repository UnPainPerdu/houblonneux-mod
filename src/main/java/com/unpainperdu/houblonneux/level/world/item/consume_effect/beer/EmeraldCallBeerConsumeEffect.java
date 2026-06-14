package com.unpainperdu.houblonneux.level.world.item.consume_effect.beer;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.unpainperdu.houblonneux.register.item.ModConsumeEffectTypeRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.wanderingtrader.WanderingTrader;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;

import java.util.ArrayList;
import java.util.List;

public class EmeraldCallBeerConsumeEffect extends AbstractBeerConsumeEffect
{
    private final BeerType beerType;

    public static final MapCodec<EmeraldCallBeerConsumeEffect> CODEC = RecordCodecBuilder.mapCodec(
            i -> i.group(
                            BeerType.CODEC.fieldOf("beer_type").forGetter(EmeraldCallBeerConsumeEffect::getBeerType)
                    )
                    .apply(i, EmeraldCallBeerConsumeEffect::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, EmeraldCallBeerConsumeEffect> STREAM_CODEC = StreamCodec.composite(
            NeoForgeStreamCodecs.enumCodec(BeerType.class), EmeraldCallBeerConsumeEffect::getBeerType,
            EmeraldCallBeerConsumeEffect::new
    );

    public EmeraldCallBeerConsumeEffect(BeerType beerType)
    {
        this.beerType = beerType;
    }

    @Override
    public BeerType getBeerType()
    {
        return this.beerType;
    }

    @Override
    public String getName()
    {
        return "emerald_call";
    }

    @Override
    public boolean apply(Level level, ItemStack stack, LivingEntity user, BeerType beerType)
    {
        BlockPos spawnPos = user.blockPosition().above(1);
        List<WanderingTrader> wanderingTraders = new ArrayList<>();
        for (int i = 0; i < beerType.getPowerLevel(); i++)
        {
            WanderingTrader trader = EntityType.WANDERING_TRADER.spawn((ServerLevel) level, spawnPos, EntitySpawnReason.EVENT);
            if (trader != null)
            {
                trader.setDespawnDelay((level.getRandom().nextInt(10, 21)) * 20);
                wanderingTraders.add(trader);
            }
        }
        wanderingTraders.forEach(level::addFreshEntity);
        level.playSound(null, spawnPos, SoundEvents.VILLAGER_CELEBRATE, SoundSource.NEUTRAL, 16F, 1.0F);
        return true;
    }

    @Override
    public Type<? extends ConsumeEffect> getType()
    {
        return ModConsumeEffectTypeRegister.EMERALD_CALL_BEER.get();
    }
}