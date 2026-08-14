package com.unpainperdu.houblonneux.register.recipe;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.world.item.crafting.brewing_barrel.brewing.BrewingRecipe;
import com.unpainperdu.houblonneux.level.world.item.crafting.brewing_barrel.pumping.PumpingRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipeSerializerRegister
{
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, Houblonneux.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<BrewingRecipe>> BREWING_RECIPE = RECIPE_SERIALIZERS.register("brewing_recipe", ()-> new RecipeSerializer<>(BrewingRecipe.CODEC, BrewingRecipe.STREAM_CODEC));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<PumpingRecipe>> PUMPING_RECIPE = RECIPE_SERIALIZERS.register("pomp_recipe", ()-> new RecipeSerializer<>(PumpingRecipe.CODEC, PumpingRecipe.STREAM_CODEC));

    public static void register(IEventBus event)
    {
        RECIPE_SERIALIZERS.register(event);
    }
}