package com.unpainperdu.houblonneux.register.item;

import com.unpainperdu.houblonneux.level.world.item.consume_effect.beer.AbstractBeerConsumeEffect;
import com.unpainperdu.houblonneux.level.world.item.consume_effect.beer.BeerType;
import com.unpainperdu.houblonneux.register.ModSoundRegister;
import com.unpainperdu.houblonneux.register.extensible_enum.ModItemUseAnimation;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.component.UseRemainder;

import java.util.List;

public class ModItemProperties
{
    public static final Item.Properties HOP_FLOWER_PROPERTIES = new Item.Properties()
            .food(new FoodProperties(1, 0.1F, false))
            .component(DataComponents.CONSUMABLE, Consumable.builder()
                    .consumeSeconds(1.0F)
                    .build());

    public static Item.Properties getBeerProperties(AbstractBeerConsumeEffect consumeEffect)
    {
        Item.Properties p = new Item.Properties();
        BeerType beerType = consumeEffect.getBeerType();
        switch (beerType)
        {
            case BOTTLE -> p.food(new FoodProperties(5, 0.6F, true))
                    .craftRemainder(new ItemStackTemplate(ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE))
                    .component(DataComponents.USE_REMAINDER, new UseRemainder(new ItemStackTemplate(ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE)));
            case GLASS -> p.food(new FoodProperties(5, 0.75F, true))
                    .component(DataComponents.USE_REMAINDER, new UseRemainder(new ItemStackTemplate(ModItemRegister.EMPTY_POLYMORPHIC_GLASS)));
            case MUG -> p.food(new FoodProperties(5, 0.9F, true))
                    .component(DataComponents.USE_REMAINDER, new UseRemainder(new ItemStackTemplate(ModItemRegister.EMPTY_POLYMORPHIC_MUG)));
        }
        return p.component(DataComponents.CONSUMABLE, getBeerConsumable(consumeEffect))
                .component(DataComponents.LORE, new ItemLore(List.of(consumeEffect.getTooltip())));
    }

    private static Consumable getBeerConsumable(AbstractBeerConsumeEffect consumeEffect)
    {
        return Consumable.builder()
                .consumeSeconds(2.0F)
                .animation(ModItemUseAnimation.BEER_DRINK.get())
                .sound(SoundEvents.HONEY_DRINK)
                .soundAfterConsume(ModSoundRegister.BURP)
                .hasConsumeParticles(false)
                .onConsume(consumeEffect)
                .build();
    }
}