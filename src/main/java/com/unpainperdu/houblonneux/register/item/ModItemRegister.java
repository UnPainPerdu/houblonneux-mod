package com.unpainperdu.houblonneux.register.item;

import com.unpainperdu.houblonneux.Houblonneux;
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

    public static void register(IEventBus event)
    {
        ITEMS.register(event);
    }
}