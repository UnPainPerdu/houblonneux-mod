package com.unpainperdu.houblonneux.client.screen;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.client.screen.util.FluidTankRenderer;
import com.unpainperdu.houblonneux.level.menu.block.entity.BrewingBarrelMenu;
import com.unpainperdu.houblonneux.level.world.block.entity.BrewingBarrelBlockEntity;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class BrewingBarrelScreen extends AbstractContainerScreen<BrewingBarrelMenu>
{
    private static final Identifier BACKGROUND = Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "textures/gui/block/entity/brewing_barrel.png");

    public BrewingBarrelScreen(BrewingBarrelMenu menu, Inventory inventory, Component title)
    {
        super(menu, inventory, title, 176, 184);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a)
    {
        super.extractBackground(graphics, mouseX, mouseY, a);
        int mainScreenXPos = (this.width - this.imageWidth) / 2;
        int mainScreenYPos = (this.height - this.imageHeight) / 2;
        graphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, mainScreenXPos, mainScreenYPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
        FluidTankRenderer fluidTankRenderer = new FluidTankRenderer(BrewingBarrelBlockEntity.TANK_CAPACITY, true, 16, 64);
        fluidTankRenderer.render(graphics, mouseX, mouseY, mainScreenXPos + 74, mainScreenYPos + 21, this.getMenu().getFluidStack());
    }
}