package com.unpainperdu.houblonneux.integration.jei.category;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.integration.jei.ModJeiPlugin;
import com.unpainperdu.houblonneux.level.world.item.crafting.brewing_barrel.brewing.BrewingRecipe;
import com.unpainperdu.houblonneux.register.block.ModBlockRegister;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

import java.util.List;

public class BrewingCategory extends AbstractRecipeCategory<BrewingRecipe>
{
    public static final String CATEGORY_KEY = "jei." + Houblonneux.MOD_ID + ".brewing";

    public static final Identifier BACKGROUND_TEXTURES = Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "textures/gui/integration/jei/brewing_menu.png");

    public BrewingCategory(IGuiHelper guiHelper)
    {
        super(ModJeiPlugin.BREWING,
                Component.translatable(CATEGORY_KEY),
                guiHelper.createDrawableIngredient(
                        VanillaTypes.ITEM_STACK,
                        ModBlockRegister.OAK_BREWING_BARREL.asItem().getDefaultInstance()
                ),
                176,
                105
        );
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BrewingRecipe recipe, IFocusGroup focuses)
    {
        //fluidIngredient
        SizedFluidIngredient inputFluid = recipe.sizedFluidIngredient();
        builder.addSlot(RecipeIngredientRole.INPUT, 14, 21).add(inputFluid.ingredient().fluids().getFirst().value(), inputFluid.amount()).setFluidRenderer(4000, false, 16, 64);
        //ingredients
        int row = 4;
        int slotPerRow = 4;
        for (int i = 0; i < row; i++)
        {
            for (int j = 0; j < slotPerRow; j++)
            {
                List<Ingredient> inputList = recipe.ingredients();
                Ingredient ingredient;
                if (j + i * slotPerRow < inputList.size())
                {
                    ingredient = inputList.get(j + i * slotPerRow);
                    builder.addSlot(RecipeIngredientRole.INPUT, 38 + (j * 18), 18 + (i * 18)).add(ingredient);
                }
                else
                {
                    break;
                }
            }
        }
        //fluidResult
        FluidStack outputFluid = recipe.assembleFluidStack();
        builder.addSlot(RecipeIngredientRole.OUTPUT, 138, 21).add(outputFluid.getFluid(), outputFluid.getAmount()).setFluidRenderer(1000, false, 16, 64);
    }

    @Override
    public void draw(BrewingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY)
    {
        //TODO display somewhere time needed for recipe
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND_TEXTURES, 0, 0, 0, 0, this.getWidth(), this.getHeight(), 256, 256);
    }
}