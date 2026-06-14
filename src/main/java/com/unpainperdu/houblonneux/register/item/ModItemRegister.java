package com.unpainperdu.houblonneux.register.item;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.world.item.consume_effect.beer.BeerType;
import com.unpainperdu.houblonneux.level.world.item.consume_effect.beer.EmeraldCallBeerConsumeEffect;
import com.unpainperdu.houblonneux.register.block.ModBlockRegister;
import com.unpainperdu.houblonneux.register.item.list.ItemList;
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
    public static final DeferredItem<Item> EMERALD_CALL_BOTTLE = registerBeerItem("emerald_call_bottle", ModItemProperties.getBeerProperties(new EmeraldCallBeerConsumeEffect(BeerType.BOTTLE)));
    public static final DeferredItem<Item> EMERALD_CALL_GLASS = registerBeerItem("emerald_call_glass", ModItemProperties.getBeerProperties(new EmeraldCallBeerConsumeEffect(BeerType.GLASS)));
    public static final DeferredItem<Item> EMERALD_CALL_MUG = registerBeerItem("emerald_call_mug", ModItemProperties.getBeerProperties(new EmeraldCallBeerConsumeEffect(BeerType.MUG)));

    public static void register(IEventBus event)
    {
        ITEMS.register(event);
    }

    public static DeferredItem<Item> registerBeerItem(String name, Item.Properties properties)
    {
        DeferredItem<Item> deferredHolder = ITEMS.registerItem(name, Item::new, () -> properties);
        ItemList.BEER_ITEM.add(deferredHolder);
        return deferredHolder;
    }
}