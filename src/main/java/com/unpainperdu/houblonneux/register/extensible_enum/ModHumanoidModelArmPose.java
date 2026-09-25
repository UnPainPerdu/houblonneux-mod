package com.unpainperdu.houblonneux.register.extensible_enum;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.HumanoidArm;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;
import net.neoforged.neoforge.client.IArmPoseTransformer;

public class ModHumanoidModelArmPose
{
    public static final EnumProxy<HumanoidModel.ArmPose> BEER_DRINKING = new EnumProxy<>(
            HumanoidModel.ArmPose.class,
            false,
            false,
            (IArmPoseTransformer) (HumanoidModel<?> model, HumanoidRenderState state, HumanoidArm arm) ->
            {
                ModelPart usingArm = model.getArm(arm);
                if (arm == HumanoidArm.RIGHT)
                {
                    usingArm.xRot = (float) (-Math.PI / 2) - 0.1F;
                    usingArm.yRot = -0.2F;
                    usingArm.zRot = 0.75F;
                }
                else
                {
                    usingArm.xRot = (float) (-Math.PI / 2) - 0.1F;
                    usingArm.yRot = 0.2F;
                    usingArm.zRot = -0.75F;
                }
            }
    );
}