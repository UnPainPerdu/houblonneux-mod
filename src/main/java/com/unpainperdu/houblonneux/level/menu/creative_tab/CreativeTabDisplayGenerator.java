package com.unpainperdu.houblonneux.level.menu.creative_tab;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.ItemLike;

import java.util.Arrays;
import java.util.function.Supplier;

import static com.unpainperdu.houblonneux.register.block.ModBlockRegister.BEER_DISPENSER;
import static com.unpainperdu.houblonneux.register.item.ModItemRegister.*;

public class CreativeTabDisplayGenerator
{
    public static final CreativeModeTab.DisplayItemsGenerator MAIN_TAB = generateBasicGenerator(
            BEER_DISPENSER,
            //beer
            //  empty
            EMPTY_POLYMORPHIC_BOTTLE,
            EMPTY_POLYMORPHIC_GLASS,
            EMPTY_POLYMORPHIC_MUG,
            //  emerald_call
            EMERALD_CALL_BOTTLE,
            EMERALD_CALL_GLASS,
            EMERALD_CALL_MUG,
            //other
            HOP_FLOWER,
            HOP_LUPULIN
    );

    @SafeVarargs
    private static CreativeModeTab.DisplayItemsGenerator generateBasicGenerator(Supplier<? extends ItemLike>... items)
    {
        return (_, output) -> Arrays.stream(items).map(Supplier::get).forEach(output::accept);
    }
}