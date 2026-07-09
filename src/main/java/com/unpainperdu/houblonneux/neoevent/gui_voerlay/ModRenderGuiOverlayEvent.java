package com.unpainperdu.houblonneux.neoevent.gui_voerlay;

import com.unpainperdu.houblonneux.Houblonneux;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;

@EventBusSubscriber(modid = Houblonneux.MOD_ID, value = {Dist.CLIENT})
public class ModRenderGuiOverlayEvent
{
    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiLayerEvent.Post event)
    {
        Minecraft mc = Minecraft.getInstance();
        DispenserTradeOverlay.renderDispenserTradeHUD(event, mc);
    }
}