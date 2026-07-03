package com.unpainperdu.houblonneux.client.render;

import com.unpainperdu.houblonneux.level.world.item.trading.DispenserTrade;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.core.Direction;

public class BeerDispenserBlockEntityRenderState extends BlockEntityRenderState
{
    DispenserTrade trade;
    Direction direction;
}
