package com.unpainperdu.houblonneux.level.world.item.crafting.brewing_barrel.pomp;

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
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import net.neoforged.neoforge.fluids.crafting.SimpleFluidIngredient;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import org.jspecify.annotations.Nullable;

public class PumpingRecipeBuilder implements RecipeBuilder
{
    protected final HolderGetter<Item> items;
    protected final HolderGetter<Fluid> fluids;
    protected final String modNameSpace;

    protected boolean showNotification = true;
    private SizedFluidIngredient sizedFluidIngredient;
    private Ingredient ingredient;
    private final ItemStackTemplate result;
    private final RecipeUnlockAdvancementBuilder advancementBuilder;

    public PumpingRecipeBuilder(String modNameSpace, HolderGetter<Item> items, HolderGetter<Fluid> fluids, ItemStackTemplate result)
    {
        this.modNameSpace = modNameSpace;
        this.items = items;
        this.fluids = fluids;

        this.result = result;
        this.advancementBuilder = new RecipeUnlockAdvancementBuilder();
    }

    public PumpingRecipeBuilder setFluidIngredient(TagKey<Fluid> fluidTag, int amount)
    {
        return this.setFluidIngredient(new SizedFluidIngredient(FluidIngredient.of(this.fluids.getOrThrow(fluidTag)), amount));
    }

    public PumpingRecipeBuilder setFluidIngredient(FluidStack fluidStack)
    {
        this.sizedFluidIngredient = new SizedFluidIngredient(SimpleFluidIngredient.of(fluidStack), fluidStack.amount());
        return this;
    }

    public PumpingRecipeBuilder setFluidIngredient(Fluid fluid, int amount)
    {
        this.sizedFluidIngredient = new SizedFluidIngredient(SimpleFluidIngredient.of(fluid), amount);
        return this;
    }

    public PumpingRecipeBuilder setFluidIngredient(SizedFluidIngredient sizedFluidIngredient)
    {
        this.sizedFluidIngredient = sizedFluidIngredient;
        return this;
    }

    public PumpingRecipeBuilder setIngredient(ItemLike ingredient)
    {
        this.ingredient = Ingredient.of(ingredient);
        return this;
    }

    public PumpingRecipeBuilder setIngredient(TagKey<Item> itemTag)
    {
        this.ingredient = Ingredient.of(this.items.getOrThrow(itemTag));
        return this;
    }

    public PumpingRecipeBuilder showNotification(boolean showNotification)
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
        if (ingredient == null || sizedFluidIngredient == null)
        {
            throw new IllegalStateException("Can't save recipe with no fluid ingredient or no ingredient");
        }
        PumpingRecipe recipe = new PumpingRecipe(
                RecipeBuilder.createCraftingCommonInfo(this.showNotification),
                this.sizedFluidIngredient,
                this.ingredient,
                this.result
        );
        output.accept(resourceKey, recipe, this.advancementBuilder.build(output, resourceKey, RecipeCategory.MISC));
    }

    public ResourceKey<Recipe<?>> getDefaultRecipeId(ItemStackTemplate result)
    {
        Identifier identifier = result.typeHolder().unwrapKey().orElseThrow().identifier();
        String currentNameSpace = identifier.getNamespace();
        Identifier recipeIdentifier = Identifier.fromNamespaceAndPath(this.modNameSpace, identifier.toString().replace(currentNameSpace + ":", "") + "_pumping");
        return ResourceKey.create(Registries.RECIPE, recipeIdentifier);
    }
}
