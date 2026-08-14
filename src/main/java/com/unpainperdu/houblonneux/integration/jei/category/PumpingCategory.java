package com.unpainperdu.houblonneux.integration.jei.category;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.integration.jei.ModJeiPlugin;
import com.unpainperdu.houblonneux.level.world.item.crafting.brewing_barrel.pumping.PumpingRecipe;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

public class PumpingCategory extends AbstractRecipeCategory<PumpingRecipe>
{
    public static final String CATEGORY_KEY = "jei." + Houblonneux.MOD_ID + ".pumping";

    public static final Identifier BACKGROUND_TEXTURES = Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "textures/gui/integration/jei/pumping_menu.png");

    public PumpingCategory(IGuiHelper guiHelper)
    {
        super(ModJeiPlugin.PUMPING,
                Component.translatable(CATEGORY_KEY),
                guiHelper.createDrawableIngredient(
                        VanillaTypes.ITEM_STACK,
                        ModBlockRegister.OAK_BREWING_BARREL.asItem().getDefaultInstance()
                ),
                123,
                105
        );
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, PumpingRecipe recipe, IFocusGroup focuses)
    {
        //ingredients
        Ingredient ingredient = recipe.ingredient();
        builder.addSlot(RecipeIngredientRole.INPUT, 14, 45).add(ingredient);
        //fluidIngredient
        SizedFluidIngredient inputFluid = recipe.sizedFluidIngredient();
        builder.addSlot(RecipeIngredientRole.INPUT, 43, 21).add(inputFluid.ingredient().fluids().getFirst().value(), inputFluid.amount()).setFluidRenderer(4000, false, 16, 64);
        //result
        ItemStack result = recipe.result().create();
        builder.addSlot(RecipeIngredientRole.OUTPUT, 91, 45).add(result);
    }

    @Override
    public void draw(PumpingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY)
    {
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND_TEXTURES, 0, 0, 0, 0, 123, 105, 256, 256);
    }
}