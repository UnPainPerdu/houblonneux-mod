package com.unpainperdu.houblonneux.client.screen;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.menu.block.entity.BeerDispenserMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class BeerDispenserScreen extends AbstractContainerScreen<BeerDispenserMenu> implements MenuAccess<BeerDispenserMenu>
{
    private static final Identifier BACKGROUND = Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "textures/gui/block/entity/dispenser_beer_storage.png");

    public BeerDispenserScreen(BeerDispenserMenu menu, Inventory inventory, Component title)
    {
        super(menu, inventory, title);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a)
    {
        super.extractBackground(graphics, mouseX, mouseY, a);
        int xo = (this.width - this.imageWidth) / 2;
        int yo = (this.height - this.imageHeight) / 2;
        graphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, xo, yo, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
    }
}