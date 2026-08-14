package com.unpainperdu.houblonneux.server.packs.ressources.trade.pumping;

import com.unpainperdu.houblonneux.level.world.item.crafting.brewing_barrel.pumping.PumpingRecipe;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.HashMap;
import java.util.Map;

public class PumpingRecipeLoader extends SimpleJsonResourceReloadListener<PumpingRecipe>
{
    public static final Map<Identifier, PumpingRecipe> RECIPES = new HashMap<>();

    public PumpingRecipeLoader()
    {
        super(
                PumpingRecipe.CODEC.codec(),
                FileToIdConverter.json(
                        "recipe"
                )
        );
    }

    @Override
    protected void apply(Map<Identifier, PumpingRecipe> preparations, ResourceManager manager, ProfilerFiller profiler)
    {
        RECIPES.clear();
        RECIPES.putAll(preparations);
    }
}