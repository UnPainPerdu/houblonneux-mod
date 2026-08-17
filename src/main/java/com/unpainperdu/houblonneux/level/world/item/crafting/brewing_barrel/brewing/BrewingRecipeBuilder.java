package com.unpainperdu.houblonneux.level.world.item.crafting.brewing_barrel.brewing;

import com.unpainperdu.houblonneux.Houblonneux;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeUnlockAdvancementBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidInstance;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidStackTemplate;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import net.neoforged.neoforge.fluids.crafting.SimpleFluidIngredient;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BrewingRecipeBuilder implements RecipeBuilder
{
    protected final HolderGetter<Item> items;
    protected final HolderGetter<Fluid> fluids;
    protected final String modNameSpace;

    protected boolean showNotification = true;
    private SizedFluidIngredient sizedFluidIngredient;
    private final List<Ingredient> ingredients = new ArrayList<>();
    private final Integer brewingTime;
    private final FluidStackTemplate result;
    private final RecipeUnlockAdvancementBuilder advancementBuilder;

    public BrewingRecipeBuilder(String modNameSpace, HolderGetter<Item> items, HolderGetter<Fluid> fluids, Integer brewingTime, FluidStackTemplate result)
    {
        this.modNameSpace = modNameSpace;
        this.items = items;
        this.fluids = fluids;

        this.brewingTime = brewingTime;
        this.result = result;
        this.advancementBuilder = new RecipeUnlockAdvancementBuilder();
    }

    public BrewingRecipeBuilder setFluidIngredient(TagKey<Fluid> fluidTag, int amount)
    {
        return this.setFluidIngredient(new SizedFluidIngredient(FluidIngredient.of(this.fluids.getOrThrow(fluidTag)), amount));
    }

    public BrewingRecipeBuilder setFluidIngredient(FluidStack fluidStack)
    {
        this.sizedFluidIngredient = new SizedFluidIngredient(SimpleFluidIngredient.of(fluidStack), fluidStack.amount());
        return this;
    }

    public BrewingRecipeBuilder setFluidIngredient(Fluid fluid, int amount)
    {
        this.sizedFluidIngredient = new SizedFluidIngredient(SimpleFluidIngredient.of(fluid), amount);
        return this;
    }

    public BrewingRecipeBuilder setFluidIngredient(SizedFluidIngredient sizedFluidIngredient)
    {
        this.sizedFluidIngredient = sizedFluidIngredient;
        return this;
    }

    public BrewingRecipeBuilder setIngredients(ItemLike... ingredients)
    {
        Arrays.stream(ingredients).forEach(item -> this.ingredients.add(Ingredient.of(item)));
        return this;
    }

    @SafeVarargs
    public final BrewingRecipeBuilder setIngredients(TagKey<Item>... itemTags)
    {
        Arrays.stream(itemTags).forEach(tagKey -> this.ingredients.add(Ingredient.of(this.items.getOrThrow(tagKey))));
        return this;
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
        if (ingredients.isEmpty() || sizedFluidIngredient == null)
        {
            throw new IllegalStateException("Can't save recipe with no fluid ingredient or no ingredient");
        }
        BrewingRecipe recipe = new BrewingRecipe(
                RecipeBuilder.createCraftingCommonInfo(this.showNotification),
                this.sizedFluidIngredient,
                this.ingredients,
                this.brewingTime,
                this.result
        );
        output.accept(resourceKey, recipe, this.advancementBuilder.build(output, resourceKey, RecipeCategory.MISC));
    }

    public ResourceKey<Recipe<?>> getDefaultRecipeId(FluidInstance result)
    {
        Identifier identifier = result.typeHolder().unwrapKey().orElseThrow().identifier();
        String currentNameSpace = identifier.getNamespace();
        Identifier recipeIdentifier = Identifier.fromNamespaceAndPath(this.modNameSpace, identifier.toString().replace(currentNameSpace + ":", "") + "_brewing");
        return ResourceKey.create(Registries.RECIPE, recipeIdentifier);
    }
}