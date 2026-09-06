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

    public static final String BOTTLE_TOOLTIP_KEY = "item.la_blanche_de_chez_nous.bottle.tooltip";
    public static final String GLASS_TOOLTIP_KEY = "item.la_blanche_de_chez_nous.glass.tooltip";
    public static final String MUG_TOOLTIP_KEY = "item.la_blanche_de_chez_nous.mug.tooltip";

    public static final Component BOTTLE_TOOLTIP = Component.translatable(BOTTLE_TOOLTIP_KEY);
    public static final Component GLASS_TOOLTIP = Component.translatable(GLASS_TOOLTIP_KEY);
    public static final Component MUG_TOOLTIP = Component.translatable(MUG_TOOLTIP_KEY);

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
        Holder<MobEffect> effect = ModMobEffectRegister.LA_BLANCHE_DE_CHEZ_NOUS;
        int power = this.beerType.getPowerLevel();
        double duration = 2400;
        user.addEffect(new MobEffectInstance(effect, (int) duration, power));
        return true;
    }

    @Override
    public Type<? extends ConsumeEffect> getType()
    {
        return ModConsumeEffectTypeRegister.LA_BLANCHE_DE_CHEZ_NOUS_BEER.get();
    }
}