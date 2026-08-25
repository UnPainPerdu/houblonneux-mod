package com.unpainperdu.houblonneux.level.world.effect.helper;

import com.unpainperdu.houblonneux.register.ModDataAttachmentRegister;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ScaleEffectHelper
{
    public static void scaleTo(LivingEntity entity, float scaleFactor)
    {
        AttributeInstance sizeA = entity.getAttribute(Attributes.SCALE);
        AttributeInstance speedMovementA = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        AttributeInstance jumpFactorA = entity.getAttribute(Attributes.JUMP_STRENGTH);
        AttributeInstance safeFallDistanceFactorA = entity.getAttribute(Attributes.SAFE_FALL_DISTANCE);
        if (sizeA != null //necessary
                && speedMovementA != null
                && jumpFactorA != null
                && safeFallDistanceFactorA != null)
        {
            double sizeFactor = sizeA.getValue();
            double speedFactor = speedMovementA.getValue();
            double jumpFactor = jumpFactorA.getValue();
            double safeFallDistanceFactor = safeFallDistanceFactorA.getValue();
            //save
            entity.setData(ModDataAttachmentRegister.ORIGINAL_SIZE_FACTOR, sizeFactor);
            entity.setData(ModDataAttachmentRegister.ORIGINAL_SPEED_FACTOR, speedFactor);
            entity.setData(ModDataAttachmentRegister.ORIGINAL_JUMP_FACTOR, jumpFactor);
            entity.setData(ModDataAttachmentRegister.ORIGINAL_SAFE_FALL_DISTANCE_FACTOR, safeFallDistanceFactor);
            //scale
            sizeA.setBaseValue(sizeFactor * scaleFactor);
            speedMovementA.setBaseValue((speedFactor * scaleFactor));
            jumpFactorA.setBaseValue(Math.max(jumpFactor * (scaleFactor * 0.65), 0.3));
            safeFallDistanceFactorA.setBaseValue(safeFallDistanceFactor * scaleFactor);
            //optional
            AttributeInstance reachFactorA = entity.getAttribute(Attributes.BLOCK_INTERACTION_RANGE);
            if (reachFactorA != null)
            {
                double reachFactor = reachFactorA.getValue();
                entity.setData(ModDataAttachmentRegister.ORIGINAL_REACH_FACTOR, reachFactor);
                reachFactorA.setBaseValue(reachFactor * (scaleFactor * 0.8));
            }
        }
    }

    public static void resetToBaseScale(LivingEntity entity)
    {
        AttributeInstance size = entity.getAttribute(Attributes.SCALE);
        AttributeInstance speedMovement = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        AttributeInstance jumpFactor = entity.getAttribute(Attributes.JUMP_STRENGTH);
        AttributeInstance safeFallDistanceFactor = entity.getAttribute(Attributes.SAFE_FALL_DISTANCE);
        if (size != null && speedMovement != null && jumpFactor != null && safeFallDistanceFactor != null)
        {
            size.setBaseValue(entity.getData(ModDataAttachmentRegister.ORIGINAL_SIZE_FACTOR));
            entity.removeData(ModDataAttachmentRegister.ORIGINAL_SIZE_FACTOR);
            speedMovement.setBaseValue(entity.getData(ModDataAttachmentRegister.ORIGINAL_SPEED_FACTOR));
            entity.removeData(ModDataAttachmentRegister.ORIGINAL_SPEED_FACTOR);
            jumpFactor.setBaseValue(entity.getData(ModDataAttachmentRegister.ORIGINAL_JUMP_FACTOR));
            entity.removeData(ModDataAttachmentRegister.ORIGINAL_JUMP_FACTOR);
            safeFallDistanceFactor.setBaseValue(entity.getData(ModDataAttachmentRegister.ORIGINAL_SAFE_FALL_DISTANCE_FACTOR));
            entity.removeData(ModDataAttachmentRegister.ORIGINAL_SAFE_FALL_DISTANCE_FACTOR);

            AttributeInstance reachFactor = entity.getAttribute(Attributes.BLOCK_INTERACTION_RANGE);
            if (reachFactor != null)
            {
                reachFactor.setBaseValue(entity.getData(ModDataAttachmentRegister.ORIGINAL_REACH_FACTOR));
                entity.removeData(ModDataAttachmentRegister.ORIGINAL_REACH_FACTOR);
            }
        }
    }
}