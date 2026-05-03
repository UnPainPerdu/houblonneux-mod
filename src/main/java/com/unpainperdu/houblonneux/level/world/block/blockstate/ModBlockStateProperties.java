package com.unpainperdu.houblonneux.level.world.block.blockstate;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class ModBlockStateProperties
{
    public static final EnumProperty<HopBlockstate> HOP = EnumProperty.create("hop", HopBlockstate.class);
    public static final BooleanProperty CAN_GROW = BooleanProperty.create("can_grow");
}