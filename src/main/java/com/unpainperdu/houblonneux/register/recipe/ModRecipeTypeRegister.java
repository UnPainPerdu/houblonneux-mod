package com.unpainperdu.houblonneux.register.recipe;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.world.item.crafting.brewing.BrewingRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipeTypeRegister
{
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, Houblonneux.MOD_ID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<BrewingRecipe>> BREWING_RECIPE = RECIPE_TYPES.register("brewing_recipe", RecipeType::simple);

    public static void register(IEventBus event)
    {
        RECIPE_TYPES.register(event);
    }
}