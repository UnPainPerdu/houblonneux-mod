package com.unpainperdu.houblonneux.register;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.menu.block.entity.BeerDispenserMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypeRegister
{
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, Houblonneux.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<BeerDispenserMenu>> BEER_DISPENSER = MENU_TYPES.register("beer_dispenser", () -> new MenuType<>(BeerDispenserMenu::new, FeatureFlags.DEFAULT_FLAGS));

    public static void register(IEventBus event)
    {
        MENU_TYPES.register(event);
    }
}