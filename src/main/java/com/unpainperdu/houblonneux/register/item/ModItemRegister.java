package com.unpainperdu.houblonneux.register.item;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.world.item.LockerItem;
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
    public static final DeferredItem<Item> LOCKER = ITEMS.registerItem("locker", LockerItem::new);
    //beer
    //  empty
    public static final DeferredItem<Item> EMPTY_POLYMORPHIC_BOTTLE = ITEMS.registerItem("empty_polymorphic_bottle", Item::new);
    public static final DeferredItem<Item> EMPTY_POLYMORPHIC_GLASS = ITEMS.registerItem("empty_polymorphic_glass", Item::new);
    public static final DeferredItem<Item> EMPTY_POLYMORPHIC_MUG = ITEMS.registerItem("empty_polymorphic_mug", Item::new);
    //  emerald_call
    public static final DeferredItem<Item> EMERALD_CALL_BOTTLE = registerBeerItem("emerald_call_bottle", ModItemProperties.EMERALD_CALL_BOTTLE_PROPERTIES);
    public static final DeferredItem<Item> EMERALD_CALL_GLASS = registerBeerItem("emerald_call_glass", ModItemProperties.EMERALD_CALL_GLASS_PROPERTIES);
    public static final DeferredItem<Item> EMERALD_CALL_MUG = registerBeerItem("emerald_call_mug", ModItemProperties.EMERALD_CALL_MUG_PROPERTIES);
    //  worm_hole
    public static final DeferredItem<Item> WORM_HOLE_BOTTLE = registerBeerItem("worm_hole_bottle", ModItemProperties.WORM_HOLE_BOTTLE_PROPERTIES);
    public static final DeferredItem<Item> WORM_HOLE_GLASS = registerBeerItem("worm_hole_glass", ModItemProperties.WORM_HOLE_GLASS_PROPERTIES);
    public static final DeferredItem<Item> WORM_HOLE_MUG = registerBeerItem("worm_hole_mug", ModItemProperties.WORM_HOLE_MUG_PROPERTIES);
    //  gros_gueuleton
    public static final DeferredItem<Item> GROS_GUEULETON_BOTTLE = registerBeerItem("gros_gueuleton_bottle", ModItemProperties.GROS_GUEULETON_BOTTLE_PROPERTIES);
    public static final DeferredItem<Item> GROS_GUEULETON_GLASS = registerBeerItem("gros_gueuleton_glass", ModItemProperties.GROS_GUEULETON_GLASS_PROPERTIES);
    public static final DeferredItem<Item> GROS_GUEULETON_MUG = registerBeerItem("gros_gueuleton_mug", ModItemProperties.GROS_GUEULETON_MUG_PROPERTIES);

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