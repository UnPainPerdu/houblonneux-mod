package com.unpainperdu.houblonneux.client.screen.util;

import com.google.common.base.Preconditions;
import com.unpainperdu.houblonneux.Houblonneux;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class FluidTankRenderer
{
    public static final String AMOUNT = Houblonneux.MOD_ID + ".tooltip.liquid.amount";
    public static final String AMOUNT_AND_CAPACITY = Houblonneux.MOD_ID + ".tooltip.liquid.amount.with.capacity";

    public static final Identifier LOCATION_BLOCKS = Identifier.withDefaultNamespace("textures/atlas/blocks.png");

    private static final NumberFormat nf = NumberFormat.getIntegerInstance();
    private static final int TEXTURE_SIZE = 16;

    private final long capacity;
    private final TooltipMode tooltipMode;
    private final int width;
    private final int height;

    enum TooltipMode
    {
        SHOW_AMOUNT,
        SHOW_AMOUNT_AND_CAPACITY,
        ITEM_LIST
    }

    public FluidTankRenderer(long capacity, boolean showCapacity, int width, int height)
    {
        this(capacity, showCapacity ? TooltipMode.SHOW_AMOUNT_AND_CAPACITY : TooltipMode.SHOW_AMOUNT, width, height);
    }

    private FluidTankRenderer(long capacity, TooltipMode tooltipMode, int width, int height)
    {
        Preconditions.checkArgument(capacity > 0, "capacity must be > 0");
        Preconditions.checkArgument(width > 0, "width must be > 0");
        Preconditions.checkArgument(height > 0, "height must be > 0");

        this.capacity = capacity;
        this.tooltipMode = tooltipMode;
        this.width = width;
        this.height = height;
    }

    public void render(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, int posX, int posY, FluidStack fluidStack)
    {
        Minecraft minecraft = Minecraft.getInstance();
        //texture
        FluidModel fluidModel = minecraft.getModelManager().getFluidStateModelSet().get(fluidStack.getFluid().defaultFluidState());
        TextureAtlasSprite sprite = fluidModel.stillMaterial().sprite();
        //color to apply on texture
        int color = 0xFFFFFFFF;
        if (fluidModel.fluidTintSource() != null)
        {
            color = fluidModel.fluidTintSource().colorAsStack(fluidStack);
        }

        int stored = fluidStack.getAmount();
        float filledVolume = Math.min(1.0f, (float) stored / this.capacity);
        int renderableHeight = (int) (filledVolume * height); //how much pixel filled

        int atlasWidth = (int) (sprite.contents().width() / (sprite.getU1() - sprite.getU0()));
        int atlasHeight = (int) (sprite.contents().height() / (sprite.getV1() - sprite.getV0()));

        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().translate(0, height - 16);
        for (int i = 0; i < Math.ceil(renderableHeight / 16f); i++)
        {
            int drawingHeight = Math.min(16, renderableHeight - 16 * i);
            int notDrawingHeight = 16 - drawingHeight;
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, LOCATION_BLOCKS,
                    posX, posY + notDrawingHeight,
                    sprite.getU0() * atlasWidth,
                    sprite.getV0() * atlasHeight + notDrawingHeight,
                    width, drawingHeight, atlasWidth,
                    atlasHeight, color);
            guiGraphics.pose().translate(0, -16);
        }
        guiGraphics.pose().popMatrix();
        if (isMouseAboveArea(mouseX, mouseY, posX, posY))
        {
            guiGraphics.setComponentTooltipForNextFrame(minecraft.font, getTooltip(fluidStack), mouseX, mouseY);
        }
    }


    public List<Component> getTooltip(FluidStack fluidStack)
    {
        List<Component> tooltip = new ArrayList<>();

        Fluid fluidType = fluidStack.getFluid();

        if (fluidType.isSame(Fluids.EMPTY))
        {
            return tooltip;
        }

        Component displayName = fluidStack.getHoverName();
        tooltip.add(displayName);

        long amount = fluidStack.getAmount();
        long milliBuckets = (amount * 1000) / FluidType.BUCKET_VOLUME;

        if (tooltipMode == TooltipMode.SHOW_AMOUNT_AND_CAPACITY)
        {
            MutableComponent amountString = Component.translatable(AMOUNT_AND_CAPACITY, nf.format(milliBuckets), nf.format(capacity));
            tooltip.add(amountString.withStyle(ChatFormatting.GRAY));
        }
        else if (tooltipMode == TooltipMode.SHOW_AMOUNT)
        {
            MutableComponent amountString = Component.translatable(AMOUNT, nf.format(milliBuckets));
            tooltip.add(amountString.withStyle(ChatFormatting.GRAY));
        }

        return tooltip;
    }

    private boolean isMouseAboveArea(int mouseX, int mouseY, int x, int y)
    {
        return isMouseOver(mouseX, mouseY, x , y , this.width, this.height);
    }

    private boolean isMouseOver(double mouseX, double mouseY, int x, int y, int sizeX, int sizeY)
    {
        return (mouseX >= x && mouseX <= x + sizeX) && (mouseY >= y && mouseY <= y + sizeY);
    }
}
