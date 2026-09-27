package com.unpainperdu.houblonneux.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mojang.math.Transformation;
import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.client.ModStandaloneModelRegister;
import com.unpainperdu.houblonneux.datagen.asset.model.ItemModelProvider;
import com.unpainperdu.houblonneux.level.world.block.block.BeerDispenserBlock;
import com.unpainperdu.houblonneux.level.world.block.entity.BeerDispenserBlockEntity;
import com.unpainperdu.houblonneux.register.ModDataComponentRegister;
import com.unpainperdu.houblonneux.register.block.ModBlockRegister;
import com.unpainperdu.houblonneux.util.ItemDisplayRenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.item.MissingItemModel;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class BeerDispenserBlockEntityRenderer implements BlockEntityRenderer<BeerDispenserBlockEntity, BeerDispenserBlockEntityRenderState>
{
    private static final Map<Direction, Transformation> TRANSFORMATIONS = Util.makeEnumMap(Direction.class, BeerDispenserBlockEntityRenderer::createModelTransformation);
    private final ItemModelResolver itemModelResolver;

    public BeerDispenserBlockEntityRenderer(BlockEntityRendererProvider.Context context)
    {
        this.itemModelResolver = context.itemModelResolver();
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

        if (state.trade != null)
        {
            ItemStack resultItem = state.trade.result().create();
            resultItem.set(ModDataComponentRegister.IS_DISPENSER_STANDALONE_MODEL_HACK.get(), true);
            ItemStackRenderState itemState = new ItemStackRenderState();
            this.itemModelResolver.updateForTopItem(itemState, resultItem, ItemDisplayContext.FIXED, blockEntity.getLevel(), null, (int) blockEntity.getBlockPos().asLong());
            state.item = itemState;
        }
    }

    @Override
    public void submit(BeerDispenserBlockEntityRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState)
    {
        ModelManager modelManager = Minecraft.getInstance().getModelManager();
        if (!tryUseHackItemModel(modelManager, state, poseStack, submitNodeCollector))
        {
            poseStack.pushPose();
            poseStack.mulPose(modelTransformation(state.direction));
            BlockStateModelPart model = modelManager.getStandaloneModel(ModStandaloneModelRegister.DEFAULT_BEER_DISPENSER_MODEL);
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
    }

    public static Transformation modelTransformation(Direction facing)
    {
        return TRANSFORMATIONS.get(facing);
    }

    private static Transformation createModelTransformation(Direction facing)
    {
        return new Transformation((new Matrix4f()).rotationAround(Axis.YP.rotationDegrees(-facing.toYRot()), 0.5F, 0.0F, 0.5F));
    }

    private boolean tryUseHackItemModel(ModelManager modelManager, BeerDispenserBlockEntityRenderState state, PoseStack poseStack,
                                        SubmitNodeCollector submitNodeCollector)
    {
        if (state.trade != null && state.item != null)
        {
            ItemStack resultItem = state.trade.result().create();
            resultItem.set(ModDataComponentRegister.IS_DISPENSER_STANDALONE_MODEL_HACK.get(), true);
            ItemModel model = modelManager.getItemModel(Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, ItemModelProvider.getDispenserHackName(resultItem.getItem())));
            if (!(model instanceof MissingItemModel))
            {
                displayHackModel(state.lightCoords, state.item, poseStack, submitNodeCollector, state.direction);
                return true;
            }
        }
        return false;
    }

    private void displayHackModel(int lightCoords, ItemStackRenderState itemRender, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, Direction direction)
    {
        poseStack.pushPose();
        ItemDisplayRenderHelper.setItemRenderToCenter(poseStack);
        poseStack.translate(0, 0.85D, 0);
        float angle = -direction.toYRot();
        poseStack.mulPose(Axis.YP.rotationDegrees(angle));
        poseStack.scale(2.5F, 2.5F, 2.5F);
        itemRender.submit(poseStack, submitNodeCollector, lightCoords, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
    }
}