package com.unpainperdu.houblonneux.register.item;

import com.unpainperdu.houblonneux.register.extensible_enum.ModItemUseAnimation;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.UseRemainder;

public class ModItemProperties
{
    public static final Item.Properties HOP_FLOWER_PROPERTIES = new Item.Properties()
            .food(new FoodProperties(1, 0.1F, false))
            .component(DataComponents.CONSUMABLE, Consumable.builder()
                    .consumeSeconds(1.0F)
                    .build());

    private static final Consumable BEER_CONSUMABLE = Consumable.builder()
            .consumeSeconds(2.0F)
            .animation(ModItemUseAnimation.BEER_DRINK.get())
            .sound(SoundEvents.GENERIC_DRINK)
            .soundAfterConsume(SoundEvents.HONEY_DRINK)
            .build();

    public static final Item.Properties BOTTLE_BEER_PROPERTIES = new Item.Properties()
            .food(new FoodProperties(5, 0.6F, true))
            .craftRemainder(new ItemStackTemplate(ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE))
            .component(DataComponents.USE_REMAINDER, new UseRemainder(new ItemStackTemplate(ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE)))
            .component(DataComponents.CONSUMABLE, BEER_CONSUMABLE);
    public static final Item.Properties GLASS_BEER_PROPERTIES = new Item.Properties()
            .food(new FoodProperties(5, 0.75F, true))
            .component(DataComponents.USE_REMAINDER, new UseRemainder(new ItemStackTemplate(ModItemRegister.EMPTY_POLYMORPHIC_GLASS)))
            .component(DataComponents.CONSUMABLE, BEER_CONSUMABLE);
    public static final Item.Properties MUG_BEER_PROPERTIES = new Item.Properties()
            .food(new FoodProperties(5, 0.9F, true))
            .component(DataComponents.USE_REMAINDER, new UseRemainder(new ItemStackTemplate(ModItemRegister.EMPTY_POLYMORPHIC_MUG)))
            .component(DataComponents.CONSUMABLE, BEER_CONSUMABLE);
}