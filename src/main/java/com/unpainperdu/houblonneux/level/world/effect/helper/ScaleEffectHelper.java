package com.unpainperdu.houblonneux.level.world.effect.helper;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.register.ModDataAttachmentRegister;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.function.Function;

public class ScaleEffectHelper
{
    private static final Identifier SIZE_MODIFIER_ID = Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "size");

    private static final Function<Double, AttributeModifier> SIZE_MODIFIER = (d) ->
            new AttributeModifier(SIZE_MODIFIER_ID, d, AttributeModifier.Operation.ADD_VALUE);

    private static final Identifier SPEED_MODIFIER_ID = Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "speed");

    private static final Function<Double, AttributeModifier> SPEED_MODIFIER = (d) ->
            new AttributeModifier(SPEED_MODIFIER_ID, d, AttributeModifier.Operation.ADD_VALUE);

    private static final Identifier JUMP_MODIFIER_ID = Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "jump");

    private static final Function<Double, AttributeModifier> JUMP_MODIFIER = (d) ->
            new AttributeModifier(JUMP_MODIFIER_ID, d, AttributeModifier.Operation.ADD_VALUE);

    private static final Identifier SAFE_FALL_DISTANCE_MODIFIER_ID = Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "safe_fall_distance");

    private static final Function<Double, AttributeModifier> SAFE_FALL_DISTANCE_MODIFIER = (d) ->
            new AttributeModifier(SAFE_FALL_DISTANCE_MODIFIER_ID, d, AttributeModifier.Operation.ADD_VALUE);

    private static final Identifier BLOCK_REACH_MODIFIER_ID = Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "block_reach");

    private static final Function<Double, AttributeModifier> BLOCK_REACH_MODIFIER = (d) ->
            new AttributeModifier(BLOCK_REACH_MODIFIER_ID, d, AttributeModifier.Operation.ADD_VALUE);

    private static final Identifier ENTITY_REACH_MODIFIER_ID = Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "entity_reach");

    private static final Function<Double, AttributeModifier> ENTITY_REACH_MODIFIER = (d) ->
            new AttributeModifier(ENTITY_REACH_MODIFIER_ID, d, AttributeModifier.Operation.ADD_VALUE);

    public static void scaleTo(LivingEntity entity, double scaleFactor)
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
            //scale
            sizeA.addOrReplacePermanentModifier(SIZE_MODIFIER.apply((sizeA.getValue() * scaleFactor) - sizeA.getValue()));
            speedMovementA.addOrReplacePermanentModifier(SPEED_MODIFIER.apply((speedMovementA.getValue() * scaleFactor + (scaleFactor < 1 ? speedMovementA.getValue() * 0.2 : 0)) - speedMovementA.getValue()));
            jumpFactorA.addOrReplacePermanentModifier(JUMP_MODIFIER.apply((Math.max(jumpFactorA.getValue() * (scaleFactor * 0.65), 0.3)) - jumpFactorA.getValue()));
            safeFallDistanceFactorA.addOrReplacePermanentModifier(SAFE_FALL_DISTANCE_MODIFIER.apply((safeFallDistanceFactorA.getValue() * scaleFactor) - safeFallDistanceFactorA.getValue()));
            //optional
            AttributeInstance blockReachFactorA = entity.getAttribute(Attributes.BLOCK_INTERACTION_RANGE);
            if (blockReachFactorA != null)
            {
                blockReachFactorA.addOrReplacePermanentModifier(BLOCK_REACH_MODIFIER.apply((blockReachFactorA.getValue() * scaleFactor * 0.8) - blockReachFactorA.getValue()));
            }

            AttributeInstance entityReachFactorA = entity.getAttribute(Attributes.ENTITY_INTERACTION_RANGE);
            if (entityReachFactorA != null)
            {
                entityReachFactorA.addOrReplacePermanentModifier(ENTITY_REACH_MODIFIER.apply((entityReachFactorA.getValue() * scaleFactor * 0.8) - entityReachFactorA.getValue()));
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
            size.removeModifier(SIZE_MODIFIER_ID);
            speedMovement.removeModifier(SPEED_MODIFIER_ID);
            jumpFactor.removeModifier(JUMP_MODIFIER_ID);
            safeFallDistanceFactor.removeModifier(SAFE_FALL_DISTANCE_MODIFIER_ID);

            AttributeInstance blockReachFactor = entity.getAttribute(Attributes.BLOCK_INTERACTION_RANGE);
            if (blockReachFactor != null)
            {
                blockReachFactor.removeModifier(BLOCK_REACH_MODIFIER_ID);
            }
            AttributeInstance entityReachFactor = entity.getAttribute(Attributes.ENTITY_INTERACTION_RANGE);
            if (entityReachFactor != null)
            {
                entityReachFactor.removeModifier(ENTITY_REACH_MODIFIER_ID);
            }

            entity.removeData(ModDataAttachmentRegister.SCALE_FACTOR);
        }
    }

    public static void handleDamageChangeFromScale(LivingDamageEvent.Pre event, LivingEntity entityHurt, Entity entitySource)
    {
        double scaleFactorEntityHurt = entityHurt.getData(ModDataAttachmentRegister.SCALE_FACTOR);
        double scaleFactorEntitySource = entitySource.getData(ModDataAttachmentRegister.SCALE_FACTOR);
        double ratio = scaleFactorEntitySource / scaleFactorEntityHurt;

        float finalDamage = (float) (event.getContainer().getNewDamage() * ratio);

        event.getContainer().setNewDamage(finalDamage);

        cleanDataIfDefault(entityHurt);
        cleanDataIfDefault(entitySource);
    }

    public static void cleanDataIfDefault(Entity entity)
    {
        double scaleFactorEntity = entity.getData(ModDataAttachmentRegister.SCALE_FACTOR);
        if (scaleFactorEntity == ModDataAttachmentRegister.DEFAULT_SCALE_FACTOR)
        {
            entity.removeData(ModDataAttachmentRegister.SCALE_FACTOR);
        }
    }
}