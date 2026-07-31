package com.unpainperdu.houblonneux.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mojang.math.Transformation;
import com.unpainperdu.houblonneux.client.ModStandaloneModelRegister;
import com.unpainperdu.houblonneux.level.world.block.block.BeerDispenserBlock;
import com.unpainperdu.houblonneux.level.world.block.entity.BeerDispenserBlockEntity;
import com.unpainperdu.houblonneux.level.world.item.trading.DispenserTrade;
import com.unpainperdu.houblonneux.register.block.ModBlockRegister;
import com.unpainperdu.houblonneux.register.item.ModItemRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.core.Direction;
import net.minecraft.util.Util;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class BeerDispenserBlockEntityRenderer implements BlockEntityRenderer<BeerDispenserBlockEntity, BeerDispenserBlockEntityRenderState>
{
    private static final Map<Direction, Transformation> TRANSFORMATIONS = Util.makeEnumMap(Direction.class, BeerDispenserBlockEntityRenderer::createModelTransformation);

    public BeerDispenserBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {

    }

    @Override
    public BeerDispenserBlockEntityRenderState createRenderState()
    {
        return new BeerDispenserBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(BeerDispenserBlockEntity blockEntity, BeerDispenserBlockEntityRenderState state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress)
    {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        state.trade = blockEntity.getTrade();
        boolean hasLevel = blockEntity.getLevel() != null;
        BlockState blockState = hasLevel ? blockEntity.getBlockState() : ModBlockRegister.BEER_DISPENSER.get().defaultBlockState();
        state.direction = blockState.getValue(BeerDispenserBlock.FACING);
    }

    @Override
    public void submit(BeerDispenserBlockEntityRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState)
    {
        //TODO see TODO in ModStandaloneModelRegister
        poseStack.pushPose();
        poseStack.mulPose(modelTransformation(state.direction));
        BlockStateModelPart model = getModelPartFromTrade(state.trade);
        submitNodeCollector.submitBlockModel(
                poseStack,
                RenderTypes.translucentMovingBlock(),
                List.of(model),
                new int[]{},
                state.lightCoords,
                OverlayTexture.NO_OVERLAY,
                0
        );
        poseStack.popPose();
    }

    public static Transformation modelTransformation(Direction facing)
    {
        return TRANSFORMATIONS.get(facing);
    }

    private static Transformation createModelTransformation(Direction facing)
    {
        return new Transformation((new Matrix4f()).rotationAround(Axis.YP.rotationDegrees(-facing.toYRot()), 0.5F, 0.0F, 0.5F));
    }

    private BlockStateModelPart getModelPartFromTrade(DispenserTrade trade)
    {
        ModelManager modelManager = Minecraft.getInstance().getModelManager();
        if (trade != null)
        {
            if (trade.result().create().is(ModItemRegister.EMERALD_CALL_BOTTLE.get()))
            {
                return modelManager.getStandaloneModel(ModStandaloneModelRegister.EMERALD_CALL_BEER_DISPENSER_MODEL);
            }
            if (trade.result().create().is(ModItemRegister.WORM_HOLE_BOTTLE.get()))
            {
                return modelManager.getStandaloneModel(ModStandaloneModelRegister.WORM_HOLE_BEER_DISPENSER_MODEL);
            }
            if (trade.result().create().is(ModItemRegister.GROS_GUEULETON_BOTTLE.get()))
            {
                return modelManager.getStandaloneModel(ModStandaloneModelRegister.EMERALD_CALL_BEER_DISPENSER_MODEL); //TODO real model
            }
        }
        return modelManager.getStandaloneModel(ModStandaloneModelRegister.DEFAULT_BEER_DISPENSER_MODEL);
    }
}