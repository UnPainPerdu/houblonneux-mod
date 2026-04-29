package com.unpainperdu.houblonneux.register.block;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class ModProperties
{
    public static final BlockBehaviour.Properties HOP_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).randomTicks().destroyTime(0.2F).sound(SoundType.CROP).pushReaction(PushReaction.DESTROY);
}
