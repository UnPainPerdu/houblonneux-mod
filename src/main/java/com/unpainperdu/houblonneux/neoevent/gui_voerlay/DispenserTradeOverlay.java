package com.unpainperdu.houblonneux.neoevent.gui_voerlay;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.world.block.block.BeerDispenserBlock;
import com.unpainperdu.houblonneux.level.world.block.entity.BeerDispenserBlockEntity;
import com.unpainperdu.houblonneux.level.world.item.trading.DispenserTrade;
import com.unpainperdu.houblonneux.register.block.ModBlockRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;

public class DispenserTradeOverlay
{
    private static final Identifier SCREEN = Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "textures/gui/block/entity/dispenser_beer_storage_world.png");

    public static void renderDispenserTradeHUD(RenderGuiLayerEvent event, Minecraft mc)
    {
        Level level = mc.level;
        if (level != null)
        {
            if (mc.hitResult instanceof BlockHitResult hit)
            {
                if (!mc.options.hideGui)
                {
                    BlockState state = level.getBlockState(hit.getBlockPos());
                    if (state.is(ModBlockRegister.BEER_DISPENSER))
                    {
                        BlockEntity be = ((BeerDispenserBlock) state.getBlock()).getBlockEntity(level, hit.getBlockPos(), state);
                        if (be instanceof BeerDispenserBlockEntity beerDispenserBE)
                        {
                            DispenserTrade trade = beerDispenserBE.getTrade();
                            if (trade != null)
                            {
                                ItemStack cost = trade.cost().create();
                                ItemStack result = trade.result().create();
                                GuiGraphicsExtractor gfx = event.getGuiGraphics();
                                int screenWidth = mc.getWindow().getGuiScaledWidth();
                                int screenHeight = mc.getWindow().getGuiScaledHeight();
                                int overlayWidth = 106;
                                int overlayHeight = 52;
                                int x = (screenWidth - overlayWidth) / 2;
                                int y = screenHeight - overlayHeight - 45;
                                gfx.blit(RenderPipelines.GUI_TEXTURED, SCREEN, x, y, 0.0F, 0.0F, overlayWidth, overlayHeight, 128, 128);
                                drawItemStack(gfx, x + 18, y + 18, cost);
                                drawItemStack(gfx, x + 72, y + 18, result);
                            }
                        }
                    }
                }
            }
        }
    }

    private static void drawItemStack(GuiGraphicsExtractor gfx, int x, int y, ItemStack stack)
    {
        gfx.item(stack, x, y);
        if (stack.getCount() > 1)
        {
            String count = String.valueOf(stack.getCount());
            gfx.text(
                    Minecraft.getInstance().font,
                    count,
                    x + 17 - Minecraft.getInstance().font.width(count),
                    y + 9,
                    0xFFFFFFFF
            );
        }
    }
}
