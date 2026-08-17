package com.unpainperdu.houblonneux.level.menu.creative_tab;

import com.unpainperdu.houblonneux.register.block.ModBlockRegister;
import com.unpainperdu.houblonneux.register.item.ModItemRegister;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.ItemLike;

import java.util.Arrays;
import java.util.function.Supplier;

public class CreativeTabDisplayGenerator
{
    public static final CreativeModeTab.DisplayItemsGenerator MAIN_TAB = generateBasicGenerator(
            //hop basis
            ModItemRegister.HOP_FLOWER,
            ModItemRegister.HOP_LUPULIN,
            //utility
            ModBlockRegister.BEER_DISPENSER,
            ModItemRegister.LOCKER,
            ModBlockRegister.COASTER,
            //beer
            //  empty
            ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE,
            ModItemRegister.EMPTY_POLYMORPHIC_GLASS,
            ModItemRegister.EMPTY_POLYMORPHIC_MUG,
            //  emerald_call
            ModItemRegister.EMERALD_CALL_BOTTLE,
            ModItemRegister.EMERALD_CALL_GLASS,
            ModItemRegister.EMERALD_CALL_MUG,
            //  worm_hole
            ModItemRegister.WORM_HOLE_BOTTLE,
            ModItemRegister.WORM_HOLE_GLASS,
            ModItemRegister.WORM_HOLE_MUG,
            //  gros_gueuleton
            ModItemRegister.GROS_GUEULETON_BOTTLE,
            ModItemRegister.GROS_GUEULETON_GLASS,
            ModItemRegister.GROS_GUEULETON_MUG,
            //brewing_barrel
            ModBlockRegister.OAK_BREWING_BARREL,
            ModBlockRegister.SPRUCE_BREWING_BARREL,
            ModBlockRegister.BIRCH_BREWING_BARREL,
            ModBlockRegister.JUNGLE_BREWING_BARREL,
            ModBlockRegister.ACACIA_BREWING_BARREL,
            ModBlockRegister.DARK_OAK_BREWING_BARREL,
            ModBlockRegister.MANGROVE_BREWING_BARREL,
            ModBlockRegister.CHERRY_BREWING_BARREL,
            ModBlockRegister.PALE_OAK_BREWING_BARREL,
            ModBlockRegister.BAMBOO_BREWING_BARREL,
            ModBlockRegister.CRIMSON_BREWING_BARREL,
            ModBlockRegister.WARPED_BREWING_BARREL
            //other
    );

    @SafeVarargs
    private static CreativeModeTab.DisplayItemsGenerator generateBasicGenerator(Supplier<? extends ItemLike>... items)
    {
        return (_, output) -> Arrays.stream(items).map(Supplier::get).forEach(output::accept);
    }
}