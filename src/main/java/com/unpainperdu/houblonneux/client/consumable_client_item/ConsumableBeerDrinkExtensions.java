package com.unpainperdu.houblonneux.client.consumable_client_item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.unpainperdu.houblonneux.register.extensible_enum.ModItemUseAnimation;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public class ConsumableBeerDrinkExtensions implements IClientItemExtensions
{
    //first person
    @Override
    public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess)
    {
        HumanoidArm usingArm = player.getUsedItemHand() == InteractionHand.MAIN_HAND
                ? player.getMainArm()
                : player.getMainArm().getOpposite();
        if (
                player.isUsingItem() && player.getUseItemRemainingTicks() > 0
                        && usingArm == arm && itemInHand.getUseAnimation() == ModItemUseAnimation.BEER_DRINK.get()
        )
        {
            float currUsageTime = player.getUseItemRemainingTicks() - partialTick + 1.0F;
            float scaledUsageTime = currUsageTime / itemInHand.getUseDuration(player);
            if (scaledUsageTime < 0.8F)
            {
                float extraHeightOffset = Mth.abs(Mth.cos(currUsageTime / 4.0F * (float) Math.PI) * 0.1F);
                poseStack.translate(0.0F, extraHeightOffset, 0.0F);
            }

            float eatJiggle = 1.0F;// - (float) Math.pow(scaledUsageTime, 27.0);
            int invert = arm == HumanoidArm.RIGHT ? 1 : -1;
            poseStack.translate(eatJiggle * 0.6F * invert, eatJiggle * -0.5F, eatJiggle * 0.0F);
            poseStack.mulPose(Axis.YP.rotationDegrees(invert * eatJiggle * 90.0F));
            poseStack.mulPose(Axis.XP.rotationDegrees(eatJiggle * 10.0F));
            poseStack.mulPose(Axis.ZP.rotationDegrees(invert * eatJiggle * 30.0F));
            return true;
        }
        return false;
    }
}