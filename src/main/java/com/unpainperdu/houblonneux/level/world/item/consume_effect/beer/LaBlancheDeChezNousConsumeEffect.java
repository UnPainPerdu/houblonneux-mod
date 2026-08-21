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

public class LaBlancheDeChezNousConsumeEffect extends AbstractBeerConsumeEffect
{
    private final BeerType beerType;

    public static final MapCodec<LaBlancheDeChezNousConsumeEffect> CODEC = RecordCodecBuilder.mapCodec(
            i -> i.group(
                            BeerType.CODEC.fieldOf("beer_type").forGetter(LaBlancheDeChezNousConsumeEffect::getBeerType)
                    )
                    .apply(i, LaBlancheDeChezNousConsumeEffect::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, LaBlancheDeChezNousConsumeEffect> STREAM_CODEC = StreamCodec.composite(
            NeoForgeStreamCodecs.enumCodec(BeerType.class), LaBlancheDeChezNousConsumeEffect::getBeerType,
            LaBlancheDeChezNousConsumeEffect::new
    );

    public LaBlancheDeChezNousConsumeEffect(BeerType beerType)
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
        return ModConsumeEffectTypeRegister.LA_BLANCHE_DE_CHEZ_NOUS_BEER.get();
    }
}