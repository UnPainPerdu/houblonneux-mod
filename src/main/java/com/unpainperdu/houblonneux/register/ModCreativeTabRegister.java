package com.unpainperdu.houblonneux.register;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.menu.creative_tab.CreativeTabDisplayGenerator;
import com.unpainperdu.houblonneux.register.item.ModItemRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabRegister
{
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Houblonneux.MOD_ID);

    public static final String MAIN_TAB_TRANSLATION_KEY = "itemGroup." + Houblonneux.MOD_ID + ".mainTab";
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = CREATIVE_MODE_TABS.register("main_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable(MAIN_TAB_TRANSLATION_KEY))
            .icon(() -> ModItemRegister.HOP_FLOWER.get().getDefaultInstance())
            .displayItems(CreativeTabDisplayGenerator.MAIN_TAB)
            .build()
    );

    public static void register(IEventBus event)
    {
        CREATIVE_MODE_TABS.register(event);
    }
}