package com.unpainperdu.houblonneux.level.world.item.consume_effect.beer;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.Level;

public abstract class AbstractBeerConsumeEffect implements ConsumeEffect
{
    public abstract BeerType getBeerType();

    @Override
    public boolean apply(Level level, ItemStack stack, LivingEntity user)
    {
        return apply(level, stack, user, this.getBeerType());
    }

    public abstract boolean apply(Level level, ItemStack stack, LivingEntity user, BeerType beerType);

    public static String getBeerDrinkSubtitle()
    {
        return "sound.houblonneux.item.beer_drink";
    }

    public static String getBeerDrinkBurpSubtitle()
    {
        return "sound.houblonneux.item.beer_drink_burp";
    }
}