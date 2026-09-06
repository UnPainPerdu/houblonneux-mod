package com.unpainperdu.houblonneux.register.item;

import com.unpainperdu.houblonneux.level.world.item.consume_effect.beer.*;
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

    //beer
    //  emerald_call
    public static final Item.Properties EMERALD_CALL_BOTTLE_PROPERTIES = new Item.Properties()
            .food(new FoodProperties(5, 0.6F, true))
            .craftRemainder(new ItemStackTemplate(ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE))
            .component(DataComponents.USE_REMAINDER, new UseRemainder(new ItemStackTemplate(ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE)))
            .component(DataComponents.CONSUMABLE, getGenericBeerConsumable(new EmeraldCallBeerConsumeEffect(BeerType.BOTTLE)))
            .component(DataComponents.LORE, new ItemLore(List.of(EmeraldCallBeerConsumeEffect.BOTTLE_TOOLTIP)))
            .stacksTo(64);
    public static final Item.Properties EMERALD_CALL_GLASS_PROPERTIES = new Item.Properties()
            .food(new FoodProperties(6, 0.65F, true))
            .component(DataComponents.USE_REMAINDER, new UseRemainder(new ItemStackTemplate(ModItemRegister.EMPTY_POLYMORPHIC_GLASS)))
            .component(DataComponents.CONSUMABLE, getGenericBeerConsumable(new EmeraldCallBeerConsumeEffect(BeerType.GLASS)))
            .component(DataComponents.LORE, new ItemLore(List.of(EmeraldCallBeerConsumeEffect.GLASS_TOOLTIP)))
            .stacksTo(16);
    public static final Item.Properties EMERALD_CALL_MUG_PROPERTIES = new Item.Properties()
            .food(new FoodProperties(7, 0.7F, true))
            .component(DataComponents.USE_REMAINDER, new UseRemainder(new ItemStackTemplate(ModItemRegister.EMPTY_POLYMORPHIC_MUG)))
            .component(DataComponents.CONSUMABLE, getGenericBeerConsumable(new EmeraldCallBeerConsumeEffect(BeerType.MUG)))
            .component(DataComponents.LORE, new ItemLore(List.of(EmeraldCallBeerConsumeEffect.MUG_TOOLTIP)))
            .stacksTo(4);
    //  worm_hole
    public static final Item.Properties WORM_HOLE_BOTTLE_PROPERTIES = new Item.Properties()
            .food(new FoodProperties(4, 0.55F, true))
            .craftRemainder(new ItemStackTemplate(ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE))
            .component(DataComponents.USE_REMAINDER, new UseRemainder(new ItemStackTemplate(ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE)))
            .component(DataComponents.CONSUMABLE, getGenericBeerConsumable(new WormHoleBeerConsumeEffect(BeerType.BOTTLE)))
            .component(DataComponents.LORE, new ItemLore(List.of(WormHoleBeerConsumeEffect.BOTTLE_TOOLTIP)))
            .stacksTo(64);
    public static final Item.Properties WORM_HOLE_GLASS_PROPERTIES = new Item.Properties()
            .food(new FoodProperties(5, 0.6F, true))
            .component(DataComponents.USE_REMAINDER, new UseRemainder(new ItemStackTemplate(ModItemRegister.EMPTY_POLYMORPHIC_GLASS)))
            .component(DataComponents.CONSUMABLE, getGenericBeerConsumable(new WormHoleBeerConsumeEffect(BeerType.GLASS)))
            .component(DataComponents.LORE, new ItemLore(List.of(WormHoleBeerConsumeEffect.GLASS_TOOLTIP)))
            .stacksTo(16);
    public static final Item.Properties WORM_HOLE_MUG_PROPERTIES = new Item.Properties()
            .food(new FoodProperties(6, 0.65F, true))
            .component(DataComponents.USE_REMAINDER, new UseRemainder(new ItemStackTemplate(ModItemRegister.EMPTY_POLYMORPHIC_MUG)))
            .component(DataComponents.CONSUMABLE, getGenericBeerConsumable(new WormHoleBeerConsumeEffect(BeerType.MUG)))
            .component(DataComponents.LORE, new ItemLore(List.of(WormHoleBeerConsumeEffect.MUG_TOOLTIP)))
            .stacksTo(4);
    //  gros_gueuleton
    public static final Item.Properties GROS_GUEULETON_BOTTLE_PROPERTIES = new Item.Properties()
            .food(new FoodProperties(5, 0.6F, true))
            .craftRemainder(new ItemStackTemplate(ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE))
            .component(DataComponents.USE_REMAINDER, new UseRemainder(new ItemStackTemplate(ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE)))
            .component(DataComponents.CONSUMABLE, getGenericBeerConsumable(new GrosGueuletonBeerConsumeEffect(BeerType.BOTTLE)))
            .component(DataComponents.LORE, new ItemLore(List.of(GrosGueuletonBeerConsumeEffect.BOTTLE_TOOLTIP)))
            .stacksTo(64);
    public static final Item.Properties GROS_GUEULETON_GLASS_PROPERTIES = new Item.Properties()
            .food(new FoodProperties(6, 0.6F, true))
            .component(DataComponents.USE_REMAINDER, new UseRemainder(new ItemStackTemplate(ModItemRegister.EMPTY_POLYMORPHIC_GLASS)))
            .component(DataComponents.CONSUMABLE, getGenericBeerConsumable(new GrosGueuletonBeerConsumeEffect(BeerType.GLASS)))
            .component(DataComponents.LORE, new ItemLore(List.of(GrosGueuletonBeerConsumeEffect.GLASS_TOOLTIP)))
            .stacksTo(16);
    public static final Item.Properties GROS_GUEULETON_MUG_PROPERTIES = new Item.Properties()
            .food(new FoodProperties(7, 0.65F, true))
            .component(DataComponents.USE_REMAINDER, new UseRemainder(new ItemStackTemplate(ModItemRegister.EMPTY_POLYMORPHIC_MUG)))
            .component(DataComponents.CONSUMABLE, getGenericBeerConsumable(new GrosGueuletonBeerConsumeEffect(BeerType.MUG)))
            .component(DataComponents.LORE, new ItemLore(List.of(GrosGueuletonBeerConsumeEffect.MUG_TOOLTIP)))
            .stacksTo(4);
    public static final Item.Properties PAIN_DIEUX_BOTTLE_PROPERTIES = new Item.Properties()
            .food(new FoodProperties(5, 0.6F, true))
            .craftRemainder(new ItemStackTemplate(ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE))
            .component(DataComponents.USE_REMAINDER, new UseRemainder(new ItemStackTemplate(ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE)))
            .component(DataComponents.CONSUMABLE, getGenericBeerConsumable(new PainDieuxConsumeEffect(BeerType.BOTTLE)))
            .component(DataComponents.LORE, new ItemLore(List.of(PainDieuxConsumeEffect.BOTTLE_TOOLTIP)))
            .stacksTo(64);
    public static final Item.Properties PAIN_DIEUX_GLASS_PROPERTIES = new Item.Properties()
            .food(new FoodProperties(6, 0.65F, true))
            .component(DataComponents.USE_REMAINDER, new UseRemainder(new ItemStackTemplate(ModItemRegister.EMPTY_POLYMORPHIC_GLASS)))
            .component(DataComponents.CONSUMABLE, getGenericBeerConsumable(new PainDieuxConsumeEffect(BeerType.GLASS)))
            .component(DataComponents.LORE, new ItemLore(List.of(PainDieuxConsumeEffect.GLASS_TOOLTIP)))
            .stacksTo(16);
    public static final Item.Properties PAIN_DIEUX_MUG_PROPERTIES = new Item.Properties()
            .food(new FoodProperties(7, 0.7F, true))
            .component(DataComponents.USE_REMAINDER, new UseRemainder(new ItemStackTemplate(ModItemRegister.EMPTY_POLYMORPHIC_MUG)))
            .component(DataComponents.CONSUMABLE, getGenericBeerConsumable(new PainDieuxConsumeEffect(BeerType.MUG)))
            .component(DataComponents.LORE, new ItemLore(List.of(PainDieuxConsumeEffect.MUG_TOOLTIP)))
            .stacksTo(4);
    public static final Item.Properties REAL_DWARVE_BOTTLE_PROPERTIES = new Item.Properties()
            .food(new FoodProperties(5, 0.6F, true))
            .craftRemainder(new ItemStackTemplate(ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE))
            .component(DataComponents.USE_REMAINDER, new UseRemainder(new ItemStackTemplate(ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE)))
            .component(DataComponents.CONSUMABLE, getGenericBeerConsumable(new RealDwarveConsumeEffect(BeerType.BOTTLE)))
            .component(DataComponents.LORE, new ItemLore(List.of(RealDwarveConsumeEffect.BOTTLE_TOOLTIP)))
            .stacksTo(64);
    public static final Item.Properties REAL_DWARVE_GLASS_PROPERTIES = new Item.Properties()
            .food(new FoodProperties(6, 0.65F, true))
            .component(DataComponents.USE_REMAINDER, new UseRemainder(new ItemStackTemplate(ModItemRegister.EMPTY_POLYMORPHIC_GLASS)))
            .component(DataComponents.CONSUMABLE, getGenericBeerConsumable(new RealDwarveConsumeEffect(BeerType.GLASS)))
            .component(DataComponents.LORE, new ItemLore(List.of(RealDwarveConsumeEffect.GLASS_TOOLTIP)))
            .stacksTo(16);
    public static final Item.Properties REAL_DWARVE_MUG_PROPERTIES = new Item.Properties()
            .food(new FoodProperties(7, 0.7F, true))
            .component(DataComponents.USE_REMAINDER, new UseRemainder(new ItemStackTemplate(ModItemRegister.EMPTY_POLYMORPHIC_MUG)))
            .component(DataComponents.CONSUMABLE, getGenericBeerConsumable(new RealDwarveConsumeEffect(BeerType.MUG)))
            .component(DataComponents.LORE, new ItemLore(List.of(RealDwarveConsumeEffect.MUG_TOOLTIP)))
            .stacksTo(4);
    public static final Item.Properties PIED_DE_GEANTS_BOTTLE_PROPERTIES = new Item.Properties()
            .food(new FoodProperties(5, 0.6F, true))
            .craftRemainder(new ItemStackTemplate(ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE))
            .component(DataComponents.USE_REMAINDER, new UseRemainder(new ItemStackTemplate(ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE)))
            .component(DataComponents.CONSUMABLE, getGenericBeerConsumable(new PiedDeGeantsConsumeEffect(BeerType.BOTTLE)))
            .component(DataComponents.LORE, new ItemLore(List.of(PiedDeGeantsConsumeEffect.BOTTLE_TOOLTIP)))
            .stacksTo(64);
    public static final Item.Properties PIED_DE_GEANTS_GLASS_PROPERTIES = new Item.Properties()
            .food(new FoodProperties(6, 0.65F, true))
            .component(DataComponents.USE_REMAINDER, new UseRemainder(new ItemStackTemplate(ModItemRegister.EMPTY_POLYMORPHIC_GLASS)))
            .component(DataComponents.CONSUMABLE, getGenericBeerConsumable(new PiedDeGeantsConsumeEffect(BeerType.GLASS)))
            .component(DataComponents.LORE, new ItemLore(List.of(PiedDeGeantsConsumeEffect.GLASS_TOOLTIP)))
            .stacksTo(16);
    public static final Item.Properties PIED_DE_GEANTS_MUG_PROPERTIES = new Item.Properties()
            .food(new FoodProperties(7, 0.7F, true))
            .component(DataComponents.USE_REMAINDER, new UseRemainder(new ItemStackTemplate(ModItemRegister.EMPTY_POLYMORPHIC_MUG)))
            .component(DataComponents.CONSUMABLE, getGenericBeerConsumable(new PiedDeGeantsConsumeEffect(BeerType.MUG)))
            .component(DataComponents.LORE, new ItemLore(List.of(PiedDeGeantsConsumeEffect.MUG_TOOLTIP)))
            .stacksTo(4);
    public static final Item.Properties LA_BLANCHE_DE_CHEZ_NOUS_BOTTLE_PROPERTIES = new Item.Properties()
            .food(new FoodProperties(5, 0.6F, true))
            .craftRemainder(new ItemStackTemplate(ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE))
            .component(DataComponents.USE_REMAINDER, new UseRemainder(new ItemStackTemplate(ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE)))
            .component(DataComponents.CONSUMABLE, getGenericBeerConsumable(new LaBlancheDeChezNousConsumeEffect(BeerType.BOTTLE)))
            .component(DataComponents.LORE, new ItemLore(List.of(LaBlancheDeChezNousConsumeEffect.BOTTLE_TOOLTIP)))
            .stacksTo(64);
    public static final Item.Properties LA_BLANCHE_DE_CHEZ_NOUS_GLASS_PROPERTIES = new Item.Properties()
            .food(new FoodProperties(6, 0.65F, true))
            .component(DataComponents.USE_REMAINDER, new UseRemainder(new ItemStackTemplate(ModItemRegister.EMPTY_POLYMORPHIC_GLASS)))
            .component(DataComponents.CONSUMABLE, getGenericBeerConsumable(new LaBlancheDeChezNousConsumeEffect(BeerType.GLASS)))
            .component(DataComponents.LORE, new ItemLore(List.of(LaBlancheDeChezNousConsumeEffect.GLASS_TOOLTIP)))
            .stacksTo(16);
    public static final Item.Properties LA_BLANCHE_DE_CHEZ_NOUS_MUG_PROPERTIES = new Item.Properties()
            .food(new FoodProperties(7, 0.7F, true))
            .component(DataComponents.USE_REMAINDER, new UseRemainder(new ItemStackTemplate(ModItemRegister.EMPTY_POLYMORPHIC_MUG)))
            .component(DataComponents.CONSUMABLE, getGenericBeerConsumable(new LaBlancheDeChezNousConsumeEffect(BeerType.MUG)))
            .component(DataComponents.LORE, new ItemLore(List.of(LaBlancheDeChezNousConsumeEffect.MUG_TOOLTIP)))
            .stacksTo(4);

    private static Consumable getGenericBeerConsumable(AbstractBeerConsumeEffect consumeEffect)
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

    public static class ModBlockItemProperties
    {
        public static final Item.Properties BREWING_BARREL_PROPERTIES = new Item.Properties()
                .stacksTo(1);
    }
}