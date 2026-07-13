package com.unpainperdu.houblonneux.client.render;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.Direction;

import java.util.ArrayList;
import java.util.List;

public class CoasterBlockEntityRenderState extends BlockEntityRenderState
{
    List<ItemStackRenderState> items = new ArrayList<>();
    Direction direction;
}