package com.unpainperdu.houblonneux.neoevent.recipe;

import com.unpainperdu.houblonneux.level.world.item.crafting.brewing_barrel.pumping.PumpingRecipe;
import com.unpainperdu.houblonneux.register.recipe.ModRecipeTypeRegister;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeMap;

import java.util.Collection;
import java.util.List;

public class PumpingRecipeCache
{
    private static volatile List<PumpingRecipe> RECIPES = List.of();

    public static void rebuildFromServer(MinecraftServer server)
    {
        rebuildFromMap(server.getRecipeManager().recipeMap());
    }

    public static void rebuildFromMap(RecipeMap map)
    {
        Collection<RecipeHolder<PumpingRecipe>> holders = map.byType(ModRecipeTypeRegister.PUMPING_RECIPE.get());
        RECIPES = holders.stream().map(RecipeHolder::value).toList();
    }

    public static void clear()
    {
        RECIPES = List.of();
    }

    public static List<PumpingRecipe> all()
    {
        return RECIPES;
    }
}