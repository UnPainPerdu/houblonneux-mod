package com.unpainperdu.houblonneux.level.world.item.consume_effect.beer;

import net.minecraft.util.StringRepresentable;

public enum BeerType implements StringRepresentable
{
    MUG("mug", 3),
    GLASS("glass", 2),
    BOTTLE("bottle", 1);

    private final String name;
    private final int power;

    public static final StringRepresentable.EnumCodec<BeerType> CODEC = StringRepresentable.fromEnum(BeerType::values);

    BeerType(String name, int power)
    {
        this.name = name;
        this.power = power;
    }

    public int getPowerLevel()
    {
        return this.power;
    }

    @Override
    public String getSerializedName()
    {
        return this.name;
    }
}