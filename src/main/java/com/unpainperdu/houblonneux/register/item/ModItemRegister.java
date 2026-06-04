package com.unpainperdu.houblonneux.register.item;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.world.item.beer.BeerItem;
import com.unpainperdu.houblonneux.level.world.item.beer.BeerType;
import com.unpainperdu.houblonneux.register.block.ModBlockRegister;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItemRegister
{
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Houblonneux.MOD_ID);

    public static final DeferredItem<BlockItem> HOP_FLOWER = ITEMS.registerSimpleBlockItem("hop_flower", ModBlockRegister.HOP, () -> ModItemProperties.HOP_FLOWER_PROPERTIES);
    public static final DeferredItem<Item> HOP_LUPULIN = ITEMS.registerItem("hop_lupulin", Item::new);
    //beer
    //  empty
    public static final DeferredItem<Item> EMPTY_POLYMORPHIC_BOTTLE = ITEMS.registerItem("empty_polymorphic_bottle", Item::new);
    public static final DeferredItem<Item> EMPTY_POLYMORPHIC_GLASS = ITEMS.registerItem("empty_polymorphic_glass", Item::new);
    public static final DeferredItem<Item> EMPTY_POLYMORPHIC_MUG = ITEMS.registerItem("empty_polymorphic_mug", Item::new);
    //  emerald_call
    public static final DeferredItem<BeerItem> EMERALD_CALL_BOTTLE = ITEMS.registerItem("emerald_call_bottle", p -> new BeerItem(p, BeerType.BOTTLE, ModBeerEventRegister.EMERALD_CALL), () -> ModItemProperties.BOTTLE_BEER_PROPERTIES);
    public static final DeferredItem<BeerItem> EMERALD_CALL_GLASS = ITEMS.registerItem("emerald_call_glass", p -> new BeerItem(p, BeerType.GLASS, ModBeerEventRegister.EMERALD_CALL), () -> ModItemProperties.GLASS_BEER_PROPERTIES);
    public static final DeferredItem<BeerItem> EMERALD_CALL_MUG = ITEMS.registerItem("emerald_call_mug", p -> new BeerItem(p, BeerType.MUG, ModBeerEventRegister.EMERALD_CALL), () -> ModItemProperties.MUG_BEER_PROPERTIES);

    public static void register(IEventBus event)
    {
        ITEMS.register(event);
    }
}