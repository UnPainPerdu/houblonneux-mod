package com.unpainperdu.houblonneux.level.world.item.crafting.brewing_barrel.pumping;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.unpainperdu.houblonneux.register.recipe.ModRecipeSerializerRegister;
import com.unpainperdu.houblonneux.register.recipe.ModRecipeTypeRegister;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

public class PumpingRecipe implements Recipe<PumpingInput>
{
    private final Recipe.CommonInfo commonInfo;
    private final SizedFluidIngredient sizedFluidIngredient;
    private final Ingredient ingredient;
    private final ItemStackTemplate result;

    public static final MapCodec<PumpingRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Recipe.CommonInfo.MAP_CODEC.forGetter(recipe -> recipe.commonInfo),
            SizedFluidIngredient.CODEC.fieldOf("fluidInput").forGetter(recipe -> recipe.sizedFluidIngredient),
            Ingredient.CODEC.fieldOf("ingredient").forGetter(recipe -> recipe.ingredient),
            ItemStackTemplate.CODEC.fieldOf("result").forGetter(recipe -> recipe.result)
    ).apply(inst, PumpingRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, PumpingRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Recipe.CommonInfo.STREAM_CODEC, recipe -> recipe.commonInfo,
                    SizedFluidIngredient.STREAM_CODEC, recipe -> recipe.sizedFluidIngredient,
                    Ingredient.CONTENTS_STREAM_CODEC, recipe -> recipe.ingredient,
                    ItemStackTemplate.STREAM_CODEC, recipe -> recipe.result,
                    PumpingRecipe::new
            );

    public PumpingRecipe(CommonInfo commonInfo, SizedFluidIngredient sizedFluidIngredient, Ingredient ingredient, ItemStackTemplate result)
    {
        this.commonInfo = commonInfo;
        this.sizedFluidIngredient = sizedFluidIngredient;
        this.ingredient = ingredient;
        this.result = result;
    }

    @Override
    public RecipeType<? extends Recipe<PumpingInput>> getType()
    {
        return ModRecipeTypeRegister.PUMPING_RECIPE.get();
    }

    @Override
    public RecipeSerializer<? extends Recipe<PumpingInput>> getSerializer()
    {
        return ModRecipeSerializerRegister.PUMPING_RECIPE.get();
    }

    @Override
    public boolean matches(PumpingInput input, Level level)
    {
        return sizedFluidIngredient.test(input.fluidStack()) && ingredient.test(input.itemStack());
    }

    @Override
    public ItemStack assemble(PumpingInput input)
    {
        return this.result.create();
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
    public PlacementInfo placementInfo()
    {
        return PlacementInfo.NOT_PLACEABLE;
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
