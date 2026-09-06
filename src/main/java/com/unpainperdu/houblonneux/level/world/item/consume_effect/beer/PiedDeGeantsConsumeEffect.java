package com.unpainperdu.houblonneux.level.world.item.consume_effect.beer;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.unpainperdu.houblonneux.register.entity.effect.ModMobEffectRegister;
import com.unpainperdu.houblonneux.register.item.ModConsumeEffectTypeRegister;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
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

    public static final String BOTTLE_TOOLTIP_KEY = "item.pied_de_geants.bottle.tooltip";
    public static final String GLASS_TOOLTIP_KEY = "item.pied_de_geants.glass.tooltip";
    public static final String MUG_TOOLTIP_KEY = "item.pied_de_geants.mug.tooltip";

    public static final Component BOTTLE_TOOLTIP = Component.translatable(BOTTLE_TOOLTIP_KEY);
    public static final Component GLASS_TOOLTIP = Component.translatable(GLASS_TOOLTIP_KEY);
    public static final Component MUG_TOOLTIP = Component.translatable(MUG_TOOLTIP_KEY);

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
        Holder<MobEffect> effect = ModMobEffectRegister.PIED_DE_GEANTS;
        if (!user.hasEffect(effect))
        {
            int power = this.beerType.getPowerLevel();
            double duration = 20.0 * (7.5 * power * power - 12.5 * power + 15);
            user.addEffect(new MobEffectInstance(effect, (int) duration, power));
            return true;
        }
        return false;
    }

    @Override
    public Type<? extends ConsumeEffect> getType()
    {
        return ModConsumeEffectTypeRegister.PIED_DE_GEANTS_BEER.get();
    }
}