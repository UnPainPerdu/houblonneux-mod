package com.unpainperdu.houblonneux.level.world.item.crafting.brewing;

import net.minecraft.advancements.Criterion;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeUnlockAdvancementBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.fluids.FluidInstance;
import net.neoforged.neoforge.fluids.FluidStackTemplate;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import org.jspecify.annotations.Nullable;

import java.util.List;

//TODO handle tags for fluid ingredient and ingredients
public class BrewingRecipeBuilder implements RecipeBuilder
{
    protected boolean showNotification = true;
    private final SizedFluidIngredient sizedFluidIngredient;
    private final List<Ingredient> ingredients;
    private final Integer brewingTime;
    private final FluidStackTemplate result;
    private final RecipeUnlockAdvancementBuilder advancementBuilder;

    public BrewingRecipeBuilder(SizedFluidIngredient sizedFluidIngredient, List<Ingredient> ingredients, Integer brewingTime, FluidStackTemplate result)
    {
        this.sizedFluidIngredient = sizedFluidIngredient;
        this.ingredients = ingredients;
        this.brewingTime = brewingTime;
        this.result = result;
        this.advancementBuilder = new RecipeUnlockAdvancementBuilder();
    }

    public BrewingRecipeBuilder showNotification(boolean showNotification)
    {
        this.showNotification = showNotification;
        return this;
    }

    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion)
    {
        this.advancementBuilder.unlockedBy(name, criterion);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String group)
    {
        return this;
    }

    @Override
    public ResourceKey<Recipe<?>> defaultId()
    {
        return getDefaultRecipeId(this.result);
    }

    @Override
    public void save(RecipeOutput output, ResourceKey<Recipe<?>> resourceKey)
    {
        BrewingRecipe recipe = new BrewingRecipe(
                RecipeBuilder.createCraftingCommonInfo(this.showNotification),
                this.sizedFluidIngredient,
                this.ingredients,
                this.brewingTime,
                this.result
        );
        output.accept(resourceKey, recipe, this.advancementBuilder.build(output, resourceKey, RecipeCategory.MISC));
    }

    public static ResourceKey<Recipe<?>> getDefaultRecipeId(FluidInstance result)
    {
        return ResourceKey.create(Registries.RECIPE, result.typeHolder().unwrapKey().orElseThrow().identifier());
    }
}
