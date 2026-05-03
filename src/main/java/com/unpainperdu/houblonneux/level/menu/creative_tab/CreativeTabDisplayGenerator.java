package com.unpainperdu.houblonneux.level.menu.creative_tab;

import com.unpainperdu.houblonneux.register.item.ModItemRegister;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.ItemLike;

import java.util.Arrays;
import java.util.function.Supplier;

public class CreativeTabDisplayGenerator
{
    public static final CreativeModeTab.DisplayItemsGenerator MAIN_TAB = generateBasicGenerator(
            ModItemRegister.HOP_FLOWER,
            ModItemRegister.HOP_LUPULIN
    );

    @SafeVarargs
    private static CreativeModeTab.DisplayItemsGenerator generateBasicGenerator(Supplier<? extends ItemLike>... items)
    {
        return (_, output) -> Arrays.stream(items).map(Supplier::get).forEach(output::accept);
    }
}