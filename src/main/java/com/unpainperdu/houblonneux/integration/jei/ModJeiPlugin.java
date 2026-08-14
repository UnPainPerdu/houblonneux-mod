package com.unpainperdu.houblonneux.integration.jei;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.integration.jei.category.BrewingCategory;
import com.unpainperdu.houblonneux.integration.jei.category.PumpingCategory;
import com.unpainperdu.houblonneux.level.world.block.block.BrewingBarrelBlock;
import com.unpainperdu.houblonneux.level.world.item.crafting.brewing_barrel.brewing.BrewingRecipe;
import com.unpainperdu.houblonneux.level.world.item.crafting.brewing_barrel.pumping.PumpingRecipe;
import com.unpainperdu.houblonneux.register.block.list.BlockList;
import com.unpainperdu.houblonneux.server.packs.ressources.trade.brewing.BrewingRecipeLoader;
import com.unpainperdu.houblonneux.server.packs.ressources.trade.pumping.PumpingRecipeLoader;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.types.IRecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;

@JeiPlugin
public class ModJeiPlugin implements IModPlugin
{
    public static final IRecipeType<BrewingRecipe> BREWING = IRecipeType.create(Houblonneux.MOD_ID, "brewing", BrewingRecipe.class);
    public static final IRecipeType<PumpingRecipe> PUMPING = IRecipeType.create(Houblonneux.MOD_ID, "pumping", PumpingRecipe.class);

    @Override
    public Identifier getPluginUid()
    {
        return Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration)
    {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new BrewingCategory(guiHelper));
        registration.addRecipeCategories(new PumpingCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration)
    {
        registration.addRecipes(BREWING, BrewingRecipeLoader.RECIPES.values().stream().toList());
        registration.addRecipes(PUMPING, PumpingRecipeLoader.RECIPES.values().stream().toList());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
    {
        registration.addCraftingStation(BREWING, BlockList.getAllBlocksFromClass(true, BrewingBarrelBlock.class).toArray(new Block[0]));
        registration.addCraftingStation(PUMPING, BlockList.getAllBlocksFromClass(true, BrewingBarrelBlock.class).toArray(new Block[0]));
    }
}