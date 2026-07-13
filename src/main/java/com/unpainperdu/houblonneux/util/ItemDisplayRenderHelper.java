package com.unpainperdu.houblonneux.util;


import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.core.Direction;

/**
 * Warning!
 * Scaling scale future rotation and translation
 * Rotation rotate future translation
 */
public class ItemDisplayRenderHelper
{
    /**
     * Center to the pos of block entity
     *
     */
    public static void setItemRenderToCenter(PoseStack poseStack)
    {
        poseStack.translate(0.5f, 0f, 0.5f);
    }

    /**
     * set translation on relative x
     * x
     * ^
     * |
     * |
     * |
     * 0------------------->y
     * main face
     * (facing state)
     */
    public static void moveOnRelativeXInPixel(Direction direction, PoseStack poseStack, float pixel)
    {
        float translation = getTranslationFloatFromPixel(pixel);
        switch (direction)
        {
            case EAST -> poseStack.translate(0F, 0F, translation);
            case SOUTH -> poseStack.translate(-translation, 0F, 0F);
            case WEST -> poseStack.translate(0F, 0F, -translation);
            default -> poseStack.translate(translation, 0F, 0F);
        }
    }

    /**
     * set translation on relative y
     * x
     * ^
     * |
     * |
     * |
     * 0------------------->y
     * main face
     * (facing state)
     */
    public static void moveOnRelativeYInPixel(Direction direction, PoseStack poseStack, float pixel)
    {
        float translation = getTranslationFloatFromPixel(pixel);
        switch (direction)
        {
            case EAST -> poseStack.translate(-translation, 0F, 0F);
            case SOUTH -> poseStack.translate(0F, 0F, -translation);
            case WEST -> poseStack.translate(translation, 0F, 0F);
            default -> poseStack.translate(0F, 0F, translation);
        }
    }

    /**
     *
     * @param i ==> i/16 if i = 16 => translation of 1 block, if i = 1 => translation of 1 pixel
     */
    public static float getTranslationFloatFromPixel(float i)
    {
        return i / 16;
    }
}