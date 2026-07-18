package com.unpainperdu.houblonneux.level.world.item.consume_effect.beer;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.unpainperdu.houblonneux.register.ModSoundRegister;
import com.unpainperdu.houblonneux.register.item.ModConsumeEffectTypeRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;

public class GrosGueuletonBeerConsumeEffect extends AbstractBeerConsumeEffect
{
    private final BeerType beerType;

    public static final MapCodec<GrosGueuletonBeerConsumeEffect> CODEC = RecordCodecBuilder.mapCodec(
            i -> i.group(
                            BeerType.CODEC.fieldOf("beer_type").forGetter(GrosGueuletonBeerConsumeEffect::getBeerType)
                    )
                    .apply(i, GrosGueuletonBeerConsumeEffect::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, GrosGueuletonBeerConsumeEffect> STREAM_CODEC = StreamCodec.composite(
            NeoForgeStreamCodecs.enumCodec(BeerType.class), GrosGueuletonBeerConsumeEffect::getBeerType,
            GrosGueuletonBeerConsumeEffect::new
    );

    public GrosGueuletonBeerConsumeEffect(BeerType beerType)
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
        return "gros_gueuleton";
    }

    @Override
    public boolean apply(ServerLevel level, ItemStack stack, LivingEntity user, BeerType beerType)
    {
        BlockPos pos = user.blockPosition();
        int power = this.beerType.getPowerLevel();
        int duration = 20 * (2 * power - 1);
        user.addEffect(new MobEffectInstance(MobEffects.SATURATION, duration, 0));
        level.playSound(null, pos, ModSoundRegister.BURP.get(), SoundSource.NEUTRAL, 20F, 0.05F);
        return true;
    }

    @Override
    public Type<? extends ConsumeEffect> getType()
    {
        return ModConsumeEffectTypeRegister.GROS_GUEULETON_BEER.get();
    }
}