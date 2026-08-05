package com.unpainperdu.houblonneux.util.pos;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import javax.annotation.concurrent.Immutable;


@Immutable
public class DirectionalPosGetter
{
    private final Direction direction;
    private final int x;
    private final int y;
    private final int z;

    protected DirectionalPosGetter(Direction direction, int x, int y, int z)
    {
        this.direction = direction;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public DirectionalPosGetter getAbove()
    {
        return getAbove(1);
    }

    public DirectionalPosGetter getAbove(int added)
    {
        return new DirectionalPosGetter(direction, x, y + added, z);
    }

    public DirectionalPosGetter getBelow()
    {
        return getBelow(1);
    }

    public DirectionalPosGetter getBelow(int added)
    {
        return new DirectionalPosGetter(direction, x, y - added, z);
    }

    public DirectionalPosGetter getLeft()
    {
        return getLeft(1);
    }

    public DirectionalPosGetter getLeft(int added)
    {
        int addedX = 0;
        int addedZ = 0;
        switch (direction)
        {
            case Direction.NORTH -> addedX -= added;
            case Direction.EAST -> addedZ -= added;
            case Direction.SOUTH -> addedX += added;
            default -> addedZ += added;
        }
        return new DirectionalPosGetter(direction, x + addedX, y, z + addedZ);
    }

    public DirectionalPosGetter getRight()
    {
        return getRight(1);
    }

    public DirectionalPosGetter getRight(int added)
    {
        int addedX = 0;
        int addedZ = 0;
        switch (direction)
        {
            case Direction.NORTH -> addedX += added;
            case Direction.EAST -> addedZ += added;
            case Direction.SOUTH -> addedX -= added;
            default -> addedZ -= added;
        }
        return new DirectionalPosGetter(this.direction, this.x + addedX, this.y, this.z + addedZ);
    }

    public DirectionalPosGetter getFront()
    {
        return getFront(1);
    }

    public DirectionalPosGetter getFront(int added)
    {
        int addedX = 0;
        int addedZ = 0;
        switch (direction)
        {
            case Direction.NORTH -> addedZ += added;
            case Direction.EAST -> addedX -= added;
            case Direction.SOUTH -> addedZ -= added;
            default -> addedX += added;
        }
        return new DirectionalPosGetter(direction, x + addedX, y, z + addedZ);
    }

    public DirectionalPosGetter getBehind()
    {
        return getBehind(1);
    }

    public DirectionalPosGetter getBehind(int added)
    {
        int addedX = 0;
        int addedZ = 0;
        switch (direction)
        {
            case Direction.NORTH -> addedZ -= added;
            case Direction.EAST -> addedX += added;
            case Direction.SOUTH -> addedZ += added;
            default -> addedX -= added;
        }
        return new DirectionalPosGetter(direction, x + addedX, y, z + addedZ);
    }

    public BlockPos getBlockPos()
    {
        return new BlockPos(this.x, this.y, this.z);
    }
}