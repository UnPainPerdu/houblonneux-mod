package com.unpainperdu.houblonneux.level.world.item.consume_effect.beer;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.unpainperdu.houblonneux.register.entity.effect.ModMobEffectRegister;
import com.unpainperdu.houblonneux.register.item.ModConsumeEffectTypeRegister;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;

public class WormHoleBeerConsumeEffect extends AbstractBeerConsumeEffect
{
    private final BeerType beerType;

    public static final MapCodec<WormHoleBeerConsumeEffect> CODEC = RecordCodecBuilder.mapCodec(
            i -> i.group(
                            BeerType.CODEC.fieldOf("beer_type").forGetter(WormHoleBeerConsumeEffect::getBeerType)
                    )
                    .apply(i, WormHoleBeerConsumeEffect::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, WormHoleBeerConsumeEffect> STREAM_CODEC = StreamCodec.composite(
            NeoForgeStreamCodecs.enumCodec(BeerType.class),
            WormHoleBeerConsumeEffect::getBeerType,
            WormHoleBeerConsumeEffect::new
    );

    public WormHoleBeerConsumeEffect(BeerType beerType)
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
        return "worm_hole";
    }

    @Override
    public boolean apply(ServerLevel level, ItemStack stack, LivingEntity user, BeerType beerType)
    {
        Holder<MobEffect> levitation = MobEffects.LEVITATION;
        Holder<MobEffect> wormHole = ModMobEffectRegister.WORM_HOLE;
        if (!user.hasEffect(levitation) && !user.hasEffect(wormHole))
        {
            int power = this.beerType.getPowerLevel();
            double duration = 20.0 * (7.5 * power * power - 12.5 * power + 15);
            user.addEffect(new MobEffectInstance(levitation, (int) duration, 0));
            user.addEffect(new MobEffectInstance(wormHole, (int) duration, 0));
            return true;
        }
        return false;
    }

    @Override
    public Type<? extends ConsumeEffect> getType()
    {
        return ModConsumeEffectTypeRegister.WORM_HOLE_BEER.get();
    }
}