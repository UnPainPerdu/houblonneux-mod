package com.unpainperdu.houblonneux.datagen.data.recipe;

import com.unpainperdu.houblonneux.register.item.ModItemRegister;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class RecipeProviderDispatcher extends RecipeProvider
{
    protected RecipeProviderDispatcher(HolderLookup.Provider registries, RecipeOutput output)
    {
        super(registries, output);
    }

    @Override
    protected void buildRecipes()
    {
        craftingTableRecipes();
    }

    public void craftingTableRecipes()
    {
        //item
        ShapelessRecipeBuilder.shapeless(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModItemRegister.HOP_LUPULIN)
                .requires(ModItemRegister.HOP_FLOWER)
                .unlockedBy("has_houblon_flower", this.has(ModItemRegister.HOP_FLOWER))
                .save(this.output);
        //dye
        ShapelessRecipeBuilder.shapeless(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, Items.YELLOW_DYE)
                .requires(ModItemRegister.HOP_LUPULIN)
                .unlockedBy("has_houblon_lupulin", this.has(ModItemRegister.HOP_LUPULIN))
                .save(this.output, "yellow_dye_from_lupulin");
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
            return new RecipeProviderDispatcher(provider, output);
        }

        @Override
        public String getName()
        {
            return "Houblonneux Recipes";
        }
    }
}