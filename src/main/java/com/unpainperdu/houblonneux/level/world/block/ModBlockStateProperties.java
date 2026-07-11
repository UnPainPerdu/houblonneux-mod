package com.unpainperdu.houblonneux.level.world.block;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class ModBlockStateProperties
{
    public static final BooleanProperty LOCKED = BooleanProperty.create("locked");
    public static final IntegerProperty COASTER_NUMBER = IntegerProperty.create("coaster_number", 1, 4);
}