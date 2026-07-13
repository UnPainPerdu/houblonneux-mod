package com.unpainperdu.houblonneux.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.unpainperdu.houblonneux.level.world.block.block.CoasterBlock;
import com.unpainperdu.houblonneux.level.world.block.entity.CoasterBlockEntity;
import com.unpainperdu.houblonneux.register.block.ModBlockRegister;
import com.unpainperdu.houblonneux.util.ItemDisplayRenderHelper;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class CoasterBlockEntityRenderer implements BlockEntityRenderer<CoasterBlockEntity, CoasterBlockEntityRenderState>
{
    private final ItemModelResolver itemModelResolver;

    public CoasterBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public CoasterBlockEntityRenderState createRenderState()
    {
        return new CoasterBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(CoasterBlockEntity blockEntity, CoasterBlockEntityRenderState state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress)
    {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        int seed = (int) blockEntity.getBlockPos().asLong();
        for (int slot = 0; slot < blockEntity.getItems().size(); slot++)
        {
            ItemStackRenderState itemState = new ItemStackRenderState();
            this.itemModelResolver.updateForTopItem(itemState, blockEntity.getItem(slot), ItemDisplayContext.FIXED, blockEntity.getLevel(), null, seed + slot);
            state.items.add(itemState);
        }
        boolean hasLevel = blockEntity.getLevel() != null;
        BlockState blockState = hasLevel ? blockEntity.getBlockState() : ModBlockRegister.COASTER.get().defaultBlockState();
        state.direction = blockState.getValue(CoasterBlock.FACING);
    }

    @Override
    public void submit(CoasterBlockEntityRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera)
    {
        Direction direction = state.direction;
        List<ItemStackRenderState> items = state.items;
        ItemStackRenderState item0 = items.getFirst();
        if (!item0.isEmpty())
        {
            poseItemWithPos(state, item0, poseStack, submitNodeCollector, direction, -3.6F, -3.6F);
        }
        ItemStackRenderState item1 = items.get(1);
        if (!item1.isEmpty())
        {
            poseItemWithPos(state, item1, poseStack, submitNodeCollector, direction, 3.6F, -3.6F);
        }
        ItemStackRenderState item2 = items.get(2);
        if (!item2.isEmpty())
        {
            poseItemWithPos(state, item2, poseStack, submitNodeCollector, direction, -3.6F, 3.6F);
        }
        ItemStackRenderState item3 = items.get(3);
        if (!item3.isEmpty())
        {
            poseItemWithPos(state, item3, poseStack, submitNodeCollector, direction, 3.6F, 3.6F);
        }
    }

    private void poseItemWithPos(CoasterBlockEntityRenderState state, ItemStackRenderState itemRender, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, Direction direction, float xPixel, float zPixel)
    {
        poseStack.pushPose();
        ItemDisplayRenderHelper.setItemRenderToCenter(poseStack);
        ItemDisplayRenderHelper.moveOnRelativeXInPixel(direction, poseStack, xPixel);
        ItemDisplayRenderHelper.moveOnRelativeYInPixel(direction, poseStack, zPixel);
        poseStack.translate(0, 0.25F, 0);
        poseStack.scale(0.7F, 0.7F, 0.7F);
        itemRender.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
    }
}