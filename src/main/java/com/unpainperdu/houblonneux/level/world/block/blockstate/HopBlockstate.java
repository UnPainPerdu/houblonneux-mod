package com.unpainperdu.houblonneux.level.world.block.blockstate;

import net.minecraft.util.StringRepresentable;

public enum HopBlockstate implements StringRepresentable
{
    SEED("seed"),
    TOP_GROW("top_grow"),
    MIDDLE_GROW("middle_grow"),
    MIDDLE_FLOWERED("middle_flowered");

    private final String name;

    HopBlockstate(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return this.getSerializedName();
    }

    @Override
    public String getSerializedName()
    {
        return this.name;
    }
}