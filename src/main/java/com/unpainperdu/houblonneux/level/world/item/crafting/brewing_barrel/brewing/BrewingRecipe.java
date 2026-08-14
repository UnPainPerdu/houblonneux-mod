package com.unpainperdu.houblonneux.level.world.item.crafting.brewing_barrel.brewing;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.unpainperdu.houblonneux.register.recipe.ModRecipeSerializerRegister;
import com.unpainperdu.houblonneux.register.recipe.ModRecipeTypeRegister;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidStackTemplate;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

import java.util.List;

public class BrewingRecipe implements Recipe<BrewingInput>
{
    private final Recipe.CommonInfo commonInfo;
    private final SizedFluidIngredient sizedFluidIngredient;
    private final List<Ingredient> ingredients;
    private final FluidStackTemplate result;
    private final Integer brewingTime;

    public static final MapCodec<BrewingRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Recipe.CommonInfo.MAP_CODEC.forGetter(recipe -> recipe.commonInfo),
            SizedFluidIngredient.CODEC.fieldOf("fluidInput").forGetter(recipe -> recipe.sizedFluidIngredient),
            Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(recipe -> recipe.ingredients),
            Codec.INT.fieldOf("brewingTime").forGetter(BrewingRecipe::getBrewingTime),
            FluidStackTemplate.CODEC.fieldOf("fluidResult").forGetter(recipe -> recipe.result)
    ).apply(inst, BrewingRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, BrewingRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Recipe.CommonInfo.STREAM_CODEC, recipe -> recipe.commonInfo,
                    SizedFluidIngredient.STREAM_CODEC, recipe -> recipe.sizedFluidIngredient,
                    Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), recipe -> recipe.ingredients,
                    ByteBufCodecs.INT, BrewingRecipe::getBrewingTime,
                    FluidStackTemplate.STREAM_CODEC, recipe -> recipe.result,
                    BrewingRecipe::new
            );

    public BrewingRecipe(CommonInfo commonInfo, SizedFluidIngredient sizedFluidIngredient, List<Ingredient> ingredients, Integer brewingTime, FluidStackTemplate result)
    {
        this.commonInfo = commonInfo;
        this.sizedFluidIngredient = sizedFluidIngredient;
        this.ingredients = ingredients;
        this.result = result;
        this.brewingTime = brewingTime;
    }

    @Override
    public RecipeType<? extends Recipe<BrewingInput>> getType()
    {
        return ModRecipeTypeRegister.BREWING_RECIPE.get();
    }

    @Override
    public RecipeSerializer<? extends Recipe<BrewingInput>> getSerializer()
    {
        return ModRecipeSerializerRegister.BREWING_RECIPE.get();
    }

    @Override
    public boolean matches(BrewingInput input, Level level)
    {
        if (!sizedFluidIngredient.test(input.fluidStack()))
        {
            return false;
        }

        List<ItemStack> inPutItemStacks = input.itemStacks().stream().filter(itemStack -> !itemStack.isEmpty()).toList();
        if (inPutItemStacks.size() != this.ingredients.size())
        {
            return false;
        }

        boolean match = true;
        for (Ingredient ingredient : ingredients)
        {
            boolean ingreMatch = false;

            for (ItemStack itemStack : inPutItemStacks)
            {
                if (ingredient.test(itemStack))
                {
                    ingreMatch = true;
                }
            }
            if (!ingreMatch)
            {
                match = false;
            }
        }

        return match;
    }

    @Override
    public PlacementInfo placementInfo()
    {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public ItemStack assemble(BrewingInput input) //will not be used because of FluidStackTemplate as result, go to #assembleFluidStack
    {
        return new ItemStack(Blocks.AIR);
    }

    public FluidStack assembleFluidStack()
    {
        return this.result.create();
    }

    public Integer getBrewingTime()
    {
        return this.brewingTime;
    }

    @Override
    public boolean showNotification()
    {
        return this.commonInfo.showNotification();
    }

    @Override
    public String group()
    {
        return "";
    }

    @Override
    public RecipeBookCategory recipeBookCategory()
    {
        return RecipeBookCategories.CRAFTING_MISC;
    }

    @Override
    public boolean isSpecial()
    {
        return true;
    }
}