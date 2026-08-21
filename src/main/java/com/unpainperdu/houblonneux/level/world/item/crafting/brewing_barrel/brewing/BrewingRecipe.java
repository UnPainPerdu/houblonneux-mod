package com.unpainperdu.houblonneux.level.world.item.crafting.brewing_barrel.brewing;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.unpainperdu.houblonneux.config.ModServerConfig;
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

public record BrewingRecipe(CommonInfo commonInfo,
                            SizedFluidIngredient sizedFluidIngredient,
                            List<Ingredient> ingredients,
                            Integer brewingTime,
                            FluidStackTemplate result
) implements Recipe<BrewingInput>
{
    public static final MapCodec<BrewingRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            CommonInfo.MAP_CODEC.forGetter(BrewingRecipe::commonInfo),
            SizedFluidIngredient.CODEC.fieldOf("fluidInput").forGetter(BrewingRecipe::sizedFluidIngredient),
            Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(BrewingRecipe::ingredients),
            Codec.INT.fieldOf("brewingTime").forGetter(BrewingRecipe::brewingTime),
            FluidStackTemplate.CODEC.fieldOf("fluidResult").forGetter(BrewingRecipe::result)
    ).apply(inst, BrewingRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, BrewingRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    CommonInfo.STREAM_CODEC, BrewingRecipe::commonInfo,
                    SizedFluidIngredient.STREAM_CODEC, BrewingRecipe::sizedFluidIngredient,
                    Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), BrewingRecipe::ingredients,
                    ByteBufCodecs.INT, BrewingRecipe::brewingTime,
                    FluidStackTemplate.STREAM_CODEC, BrewingRecipe::result,
                    BrewingRecipe::new
            );

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
        if (!this.sizedFluidIngredient().test(input.fluidStack()))
        {
            return false;
        }

        List<ItemStack> inPutItemStacks = input.itemStacks().stream().filter(itemStack -> !itemStack.isEmpty()).toList();
        if (inPutItemStacks.size() != this.ingredients().size())
        {
            return false;
        }

        boolean match = true;
        for (Ingredient ingredient : this.ingredients())
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
        return this.result().create();
    }

    @Override
    public boolean showNotification()
    {
        return this.commonInfo().showNotification();
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

    /**
     * @return brewing time multiplier is applied to it before returning
     */
    public int getRealBrewingTime()
    {
        return this.brewingTime * ModServerConfig.CONFIG.BREWING_RECIPE_TIME_MULTIPLICATION_FACTOR.get() / ModServerConfig.CONFIG.BREWING_RECIPE_TIME_DIVISION_FACTOR.get();
    }
}