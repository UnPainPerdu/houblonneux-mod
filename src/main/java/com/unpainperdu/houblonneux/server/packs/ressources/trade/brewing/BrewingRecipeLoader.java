package com.unpainperdu.houblonneux.server.packs.ressources.trade.brewing;

import com.unpainperdu.houblonneux.level.world.item.crafting.brewing_barrel.brewing.BrewingRecipe;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.HashMap;
import java.util.Map;

public class BrewingRecipeLoader extends SimpleJsonResourceReloadListener<BrewingRecipe>
{
    public static final Map<Identifier, BrewingRecipe> RECIPES = new HashMap<>();

    public BrewingRecipeLoader()
    {
        super(
                BrewingRecipe.CODEC.codec(),
                FileToIdConverter.json(
                        "recipe"
                )
        );
    }

    @Override
    protected void apply(Map<Identifier, BrewingRecipe> preparations, ResourceManager manager, ProfilerFiller profiler)
    {
        RECIPES.clear();
        RECIPES.putAll(preparations);
    }
}