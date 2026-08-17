package com.unpainperdu.houblonneux.client.screen;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.client.screen.util.FluidTankRenderer;
import com.unpainperdu.houblonneux.level.menu.block.entity.BrewingBarrelMenu;
import com.unpainperdu.houblonneux.level.world.block.entity.BrewingBarrelBlockEntity;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;

public class BrewingBarrelScreen extends AbstractContainerScreen<BrewingBarrelMenu>
{
    private static final Identifier BACKGROUND = Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "textures/gui/block/entity/brewing_barrel.png");
    private static final Identifier TRASH_BUTTON_LIT = Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "common/trash_button_lit");
    private static final Identifier TRASH_BUTTON_UNLIT = Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "common/trash_button_unlit");

    public static final String TRASH = Houblonneux.MOD_ID + ".tooltip.container.trash_button";
    public static final int TRASH_BUTTON_ID = 0;

    public BrewingBarrelScreen(BrewingBarrelMenu menu, Inventory inventory, Component title)
    {
        super(menu, inventory, title, 176, 184);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a)
    {
        //TODO handle display somehow of brewing time left
        super.extractBackground(graphics, mouseX, mouseY, a);
        //background
        int mainScreenPosX = (this.width - this.imageWidth) / 2;
        int mainScreenPosY = (this.height - this.imageHeight) / 2;
        graphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, mainScreenPosX, mainScreenPosY, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
        //fluid tank
        FluidTankRenderer fluidTankRenderer = new FluidTankRenderer(BrewingBarrelBlockEntity.TANK_CAPACITY, true, 16, 64);
        fluidTankRenderer.render(graphics, mouseX, mouseY, mainScreenPosX + 74, mainScreenPosY + 21, this.getMenu().getFluidStack());
        //trash_button
        renderButton(graphics, mouseX, mouseY, mainScreenPosX + 52, mainScreenPosY + 69);
    }

    private void renderButton(GuiGraphicsExtractor graphics, int mouseX, int mouseY, int buttonPosX, int buttonPosY)
    {
        Identifier buttonTexture;
        if (isMouseOver(mouseX, mouseY, buttonPosX, buttonPosY, 16, 16))
        {
            buttonTexture = TRASH_BUTTON_LIT;
            graphics.setComponentTooltipForNextFrame(minecraft.font, List.of(Component.translatable(TRASH)), mouseX, mouseY);
        }
        else
        {
            buttonTexture = TRASH_BUTTON_UNLIT;
        }
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, buttonTexture, 16, 16, 0, 0, buttonPosX, buttonPosY, 16, 16);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick)
    {
        int mainScreenPosX = (this.width - this.imageWidth) / 2;
        int mainScreenPosY = (this.height - this.imageHeight) / 2;

        if (isMouseOver(event.x(), event.y(), mainScreenPosX + 52, mainScreenPosY + 69, 16, 16)
                && this.minecraft.player != null
                && this.minecraft.gameMode != null
                && this.menu.clickMenuButton(this.minecraft.player, TRASH_BUTTON_ID)
        )
        {
            this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, TRASH_BUTTON_ID);
            return true;
        }
        return super.mouseClicked(event, doubleClick);
    }

    private boolean isMouseOver(double mouseX, double mouseY, int x, int y, int sizeX, int sizeY)
    {
        return (mouseX >= x && mouseX <= x + sizeX) && (mouseY >= y && mouseY <= y + sizeY);
    }
}