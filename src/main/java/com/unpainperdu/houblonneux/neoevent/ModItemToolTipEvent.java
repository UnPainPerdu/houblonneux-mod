package com.unpainperdu.houblonneux.neoevent;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.register.ModDataComponentRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.SimpleFluidContent;

import java.util.List;

@EventBusSubscriber(value = Dist.CLIENT)
public class ModItemToolTipEvent
{
    public static final String ITEM_FLUIDSTACK_TOOLTIP = Houblonneux.MOD_ID + "item.fluidstack.tooltip";

    @SubscribeEvent
    public static void addToolTipToItem(ItemTooltipEvent event)
    {
        ItemStack itemStack = event.getItemStack();
        List<Component> tooltips = event.getToolTip();
        handleFluidStackComponent(tooltips, itemStack);
    }

    private static void handleFluidStackComponent(List<Component> tooltips, ItemStack stack)
    {
        SimpleFluidContent simpleFluidContent = stack.getComponents().getOrDefault(ModDataComponentRegister.FLUIDSTACK, SimpleFluidContent.EMPTY);
        if (stack.getComponents().has(ModDataComponentRegister.FLUIDSTACK) && !(simpleFluidContent.isEmpty()))
        {
            Fluid fluid = simpleFluidContent.getFluid();
            FluidStack fluidStack = simpleFluidContent.copy();
            FluidModel fluidModel = Minecraft.getInstance().getModelManager().getFluidStateModelSet().get(fluidStack.getFluid().defaultFluidState());
            int color = 0xFFFFFFFF;
            if (fluidModel.fluidTintSource() != null)
            {
                color = fluidModel.fluidTintSource().colorAsStack(fluidStack);
            }
            addToolTipBeforeIdAndComponent(tooltips, Component.translatable(ITEM_FLUIDSTACK_TOOLTIP,
                            fluidStack.getAmount(),
                            fluid.getFluidType().getDescription().getString()
                    ).withColor(color)
            );
        }
    }

    private static void addToolTipBeforeIdAndComponent(List<Component> tooltips, Component component)
    {
        boolean noDarkGreyEndToolTip = true;
        for (int i = 0; i < tooltips.size(); i++)
        {
            Component comp = tooltips.get(i);
            TextColor currentColor = comp.getStyle().getColor();
            if (currentColor != null && currentColor.toString().equals("dark_gray"))
            {
                noDarkGreyEndToolTip = false;
                tooltips.add(i, component);
                break;
            }
        }
        if (noDarkGreyEndToolTip)
        {
            tooltips.add(component);
        }
    }
}