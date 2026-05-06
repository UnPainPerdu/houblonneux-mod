package com.unpainperdu.houblonneux.level.world.item.beer;

public enum BeerType
{
    MUG("mug", 3),
    GLASS("glass", 2),
    BOTTLE("bottle", 1);

    private final String name;
    private final int power;

    BeerType(String name, int power)
    {
        this.name = name;
        this.power = power;
    }

    public int getPowerLevel()
    {
        return power;
    }

    public String getName()
    {
        return name;
    }
}
