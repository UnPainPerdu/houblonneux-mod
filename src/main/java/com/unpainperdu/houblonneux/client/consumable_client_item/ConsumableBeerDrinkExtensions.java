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
            float currUsageTime = player.getUseItemRemainingTicks() - partialTick + 1.0F;// time remaining in tick
            float scaledUsageTime = currUsageTime / itemInHand.getUseDuration(player);// time remaining in %
            float reversScaledUsageTime = 1 - (currUsageTime / itemInHand.getUseDuration(player));// time passed in %
            // base pose
            poseStack.translate(0.0F, -0.4F, -0.5F);
            //rotation and translation during drink
            if (reversScaledUsageTime < 0.50)
            {
                poseStack.mulPose(Axis.XP.rotationDegrees((reversScaledUsageTime * 2) * 75F));
                poseStack.translate(0.0F, (reversScaledUsageTime * 2) *-0.2F, (reversScaledUsageTime * 2) *-0.4F);
            }
            else
            {
                //eatJiggle
                if (scaledUsageTime < 0.8F)
                {
                    float extraHeightOffset = Mth.abs(Mth.cos(currUsageTime / 12.0F * (float) Math.PI) * 0.1F);
                    poseStack.translate(0.0F, extraHeightOffset, 0.0F);
                }
                poseStack.mulPose(Axis.XP.rotationDegrees(75F));
                poseStack.translate(0.0F, -0.2F, -0.4F);
            }

            //magic I add at the end that enhance everything
            float eatJiggle = 1.0F - (float) Math.pow(scaledUsageTime, 27.0);
            int invert = arm == HumanoidArm.RIGHT ? 1 : -1;
            poseStack.mulPose(Axis.YP.rotationDegrees(invert * eatJiggle * 90.0F));
            poseStack.mulPose(Axis.XP.rotationDegrees(eatJiggle * 10.0F));
            poseStack.mulPose(Axis.ZP.rotationDegrees(invert * eatJiggle * 30.0F));
            return true;
        }
        return false;
    }
}