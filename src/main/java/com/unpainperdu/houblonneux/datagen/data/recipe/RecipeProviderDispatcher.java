package com.unpainperdu.houblonneux.datagen.data.recipe;

import com.unpainperdu.houblonneux.register.block.ModBlockRegister;
import com.unpainperdu.houblonneux.register.item.ModItemRegister;
import com.unpainperdu.houblonneux.util.StringHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

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
        //block
        this.shapeless(RecipeCategory.MISC, ModBlockRegister.COASTER)
                .requires(ItemTags.WOODEN_BUTTONS)
                .unlockedBy("has_button", this.has(ItemTags.WOODEN_BUTTONS))
                .save(this.output);
        this.shaped(RecipeCategory.MISC, ModBlockRegister.BEER_DISPENSER, 1)
                .define('x', Tags.Items.INGOTS_IRON)
                .define('y', Tags.Items.GEMS_EMERALD)
                .pattern("xxx")
                .pattern("xyx")
                .pattern("xxx")
                .unlockedBy("has_iron", this.has(Tags.Items.INGOTS_IRON))
                .save(this.output);
        // brewing_barrel
        this.brewingBarrelRecipe(ModBlockRegister.OAK_BREWING_BARREL, Blocks.OAK_PLANKS);
        //item
        this.shapeless(RecipeCategory.MISC, ModItemRegister.HOP_LUPULIN)
                .requires(ModItemRegister.HOP_FLOWER)
                .unlockedBy("has_houblon_flower", this.has(ModItemRegister.HOP_FLOWER))
                .save(this.output);
        this.shaped(RecipeCategory.MISC, ModItemRegister.LOCKER, 1)
                .define('y', Tags.Items.NUGGETS_IRON)
                .define('x', Tags.Items.NUGGETS_GOLD)
                .pattern(" y ")
                .pattern("xxx")
                .pattern("xxx")
                .unlockedBy("has_iron", this.has(Items.IRON_INGOT))
                .save(this.output);
        //  dye
        this.shapeless(RecipeCategory.MISC, Items.YELLOW_DYE)
                .requires(ModItemRegister.HOP_LUPULIN)
                .unlockedBy("has_houblon_lupulin", this.has(ModItemRegister.HOP_LUPULIN))
                .save(this.output, "yellow_dye_from_lupulin");
        //  beer container
        this.shaped(RecipeCategory.MISC, ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE, 7)
                .define('x', Tags.Items.GLASS_BLOCKS)
                .define('y', Tags.Items.GEMS_LAPIS)
                .pattern(" x ")
                .pattern("xyx")
                .pattern("xxx")
                .unlockedBy("has_glass", this.has(Tags.Items.GLASS_BLOCKS))
                .save(this.output);
        this.shaped(RecipeCategory.MISC, ModItemRegister.EMPTY_POLYMORPHIC_GLASS, 6)
                .define('x', Tags.Items.GLASS_BLOCKS)
                .define('y', Tags.Items.GEMS_LAPIS)
                .pattern("x x")
                .pattern("xyx")
                .pattern("xxx")
                .unlockedBy("has_glass", this.has(Tags.Items.GLASS_BLOCKS))
                .save(this.output);
        this.shaped(RecipeCategory.MISC, ModItemRegister.EMPTY_POLYMORPHIC_MUG, 5)
                .define('x', ItemTags.PLANKS)
                .define('y', Tags.Items.GEMS_LAPIS)
                .define('z', Tags.Items.INGOTS_IRON)
                .pattern("x x")
                .pattern("zyz")
                .pattern("xxx")
                .unlockedBy("has_iron", this.has(Tags.Items.INGOTS_IRON))
                .save(this.output);
        //  beer glass
        shapelessRecipe(ModItemRegister.EMERALD_CALL_GLASS, ModItemRegister.EMERALD_CALL_BOTTLE, ModItemRegister.EMPTY_POLYMORPHIC_GLASS);
        shapelessRecipe(ModItemRegister.WORM_HOLE_GLASS, ModItemRegister.WORM_HOLE_BOTTLE, ModItemRegister.EMPTY_POLYMORPHIC_GLASS);
        shapelessRecipe(ModItemRegister.GROS_GUEULETON_GLASS, ModItemRegister.GROS_GUEULETON_BOTTLE, ModItemRegister.EMPTY_POLYMORPHIC_GLASS);
    }

    /**
     *
     * @param ingredient first one used as unlock condition
     */
    private void shapelessRecipe(ItemLike result, ItemLike... ingredient)
    {
        ShapelessRecipeBuilder recipeBuilder = this.shapeless(RecipeCategory.MISC, result);
        for (ItemLike item : ingredient)
        {
            recipeBuilder.requires(item);
        }
        ItemLike firstIngredient = ingredient[0];
        recipeBuilder.unlockedBy("has_" + StringHelper.getSimpleName(firstIngredient.asItem()), this.has(firstIngredient))
                .save(this.output);
    }

    private void brewingBarrelRecipe(ItemLike barrel, ItemLike planks)
    {
        this.shaped(RecipeCategory.MISC, barrel, 1)
                .define('x', planks)
                .define('y', Tags.Items.INGOTS_IRON)
                .pattern("xxx")
                .pattern("y y")
                .pattern("xxx")
                .unlockedBy("has_iron", this.has(Tags.Items.INGOTS_IRON))
                .save(this.output);
    }

    public static class Runner extends RecipeProvider.Runner
    {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider)
        {
            super(output, lookupProvider);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output)
        {
            return new RecipeProviderDispatcher(provider, output);
        }

        @Override
        public String getName()
        {
            return "Houblonneux Recipes";
        }
    }
}