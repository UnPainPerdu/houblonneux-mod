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

public class PainDieuxConsumeEffect extends AbstractBeerConsumeEffect
{
    private final BeerType beerType;

    public static final MapCodec<PainDieuxConsumeEffect> CODEC = RecordCodecBuilder.mapCodec(
            i -> i.group(
                            BeerType.CODEC.fieldOf("beer_type").forGetter(PainDieuxConsumeEffect::getBeerType)
                    )
                    .apply(i, PainDieuxConsumeEffect::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, PainDieuxConsumeEffect> STREAM_CODEC = StreamCodec.composite(
            NeoForgeStreamCodecs.enumCodec(BeerType.class), PainDieuxConsumeEffect::getBeerType,
            PainDieuxConsumeEffect::new
    );

    public static final String BOTTLE_TOOLTIP_KEY = "item.pain_dieux.bottle.tooltip";
    public static final String GLASS_TOOLTIP_KEY = "item.pain_dieux.glass.tooltip";
    public static final String MUG_TOOLTIP_KEY = "item.pain_dieux.mug.tooltip";

    public static final Component BOTTLE_TOOLTIP = Component.translatable(BOTTLE_TOOLTIP_KEY);
    public static final Component GLASS_TOOLTIP = Component.translatable(GLASS_TOOLTIP_KEY);
    public static final Component MUG_TOOLTIP = Component.translatable(MUG_TOOLTIP_KEY);

    public PainDieuxConsumeEffect(BeerType beerType)
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
        Holder<MobEffect> effect = ModMobEffectRegister.PAIN_DIEUX;
        if (!user.hasEffect(effect))
        {
            int power = this.beerType.getPowerLevel();
            user.addEffect(new MobEffectInstance(effect, 60, power));
            return true;
        }
        return false;
    }

    @Override
    public Type<? extends ConsumeEffect> getType()
    {
        return ModConsumeEffectTypeRegister.PAIN_DIEUX_BEER.get();
    }
}