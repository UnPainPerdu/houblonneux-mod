package com.unpainperdu.houblonneux.level.world.item.consume_effect.beer;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.unpainperdu.houblonneux.register.item.ModConsumeEffectTypeRegister;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;

public class PiedDeGeantsConsumeEffect extends AbstractBeerConsumeEffect
{
    private final BeerType beerType;

    public static final MapCodec<PiedDeGeantsConsumeEffect> CODEC = RecordCodecBuilder.mapCodec(
            i -> i.group(
                            BeerType.CODEC.fieldOf("beer_type").forGetter(PiedDeGeantsConsumeEffect::getBeerType)
                    )
                    .apply(i, PiedDeGeantsConsumeEffect::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, PiedDeGeantsConsumeEffect> STREAM_CODEC = StreamCodec.composite(
            NeoForgeStreamCodecs.enumCodec(BeerType.class), PiedDeGeantsConsumeEffect::getBeerType,
            PiedDeGeantsConsumeEffect::new
    );

    public PiedDeGeantsConsumeEffect(BeerType beerType)
    {
        this.beerType = beerType;
    }

    @Override
    public BeerType getBeerType()
    {
        return this.beerType;
    }

    @Override
    public boolean apply(ServerLevel level, ItemStack stack, LivingEntity user, BeerType beerType)
    {
        return false;
    }

    @Override
    public Type<? extends ConsumeEffect> getType()
    {
        return ModConsumeEffectTypeRegister.PIED_DE_GEANTS_BEER.get();
    }
}