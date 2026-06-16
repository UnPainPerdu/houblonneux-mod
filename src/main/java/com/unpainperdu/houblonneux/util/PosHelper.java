package com.unpainperdu.houblonneux.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.LiquidBlock;

import java.util.ArrayList;
import java.util.List;

public class PosHelper
{
    public static BlockPos getLeft(BlockPos pos, Direction direction)
    {
        return getLeft(pos, direction, 1);
    }

    public static BlockPos getLeft(BlockPos pos, Direction direction, int howMuch)
    {
        for (int i = 0; i < howMuch; i++)
        {
            switch (direction)
            {
                case Direction.NORTH:
                {
                    pos = pos.west();
                    break;
                }
                case Direction.EAST:
                {
                    pos = pos.north();
                    break;
                }
                case Direction.SOUTH:
                {
                    pos = pos.east();
                    break;
                }
                default:
                {
                    pos = pos.south();
                }
            }
        }
        return pos;
    }

    public static BlockPos getRight(BlockPos pos, Direction direction)
    {
        return getRight(pos, direction, 1);
    }

    public static BlockPos getRight(BlockPos pos, Direction direction, int howMuch)
    {
        for (int i = 0; i < howMuch; i++)
        {
            switch (direction)
            {
                case Direction.NORTH:
                {
                    pos = pos.east();
                    break;
                }
                case Direction.EAST:
                {
                    pos = pos.south();
                    break;
                }
                case Direction.SOUTH:
                {
                    pos = pos.west();
                    break;
                }
                default:
                {
                    pos = pos.north();
                }
            }
        }
        return pos;
    }

    public static BlockPos getBehind(BlockPos pos, Direction direction)
    {
        return getBehind(pos, direction, 1);
    }

    public static BlockPos getBehind(BlockPos pos, Direction direction, int howMuch)
    {
        for (int i = 0; i < howMuch; i++)
        {
            switch (direction)
            {
                case Direction.NORTH:
                {
                    pos = pos.north();
                    break;
                }
                case Direction.EAST:
                {
                    pos = pos.east();
                    break;
                }
                case Direction.SOUTH:
                {
                    pos = pos.south();
                    break;
                }
                default:
                {
                    pos = pos.west();
                }
            }
        }
        return pos;
    }

    public static BlockPos getFront(BlockPos pos, Direction direction)
    {
        return getFront(pos, direction, 1);
    }

    public static BlockPos getFront(BlockPos pos, Direction direction, int howMuch)
    {
        for (int i = 0; i < howMuch; i++)
        {
            switch (direction)
            {
                case Direction.NORTH:
                {
                    pos = pos.south();
                    break;
                }
                case Direction.EAST:
                {
                    pos = pos.west();
                    break;
                }
                case Direction.SOUTH:
                {
                    pos = pos.north();
                    break;
                }
                default:
                {
                    pos = pos.east();
                }
            }
        }
        return pos;
    }

    public static boolean isPosInList(BlockPos pos, List<BlockPos> posList)
    {
        boolean flag = false;
        for (BlockPos pos1 : posList)
        {
            if (pos1.equals(pos))
            {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * spread = max value in x and z that pos is far
     */
    public static ArrayList<BlockPos> getRandomPosWithSameY(BlockPos pos, int minNumberOfPos, int maxNumberOfPos, int spread, RandomSource rand)
    {
        int random = rand.nextInt(minNumberOfPos, maxNumberOfPos);
        return getRandomPosWithSameY(pos, random, spread, rand, false);
    }

    /**
     * @param canHaveSamePos if yes output list length = numberOfPos;
     */
    public static ArrayList<BlockPos> getRandomPosWithSameY(BlockPos basePos, int numberOfPos, int spread, RandomSource rand, boolean canHaveSamePos)
    {
        ArrayList<BlockPos> list = new ArrayList<>();
        list.add(basePos);

        for (int i = 0; i < numberOfPos; i++)
        {
            int newPosX = basePos.getX() + (rand.nextInt(spread) * (rand.nextBoolean() ? 1 : -1));
            int newPosZ = basePos.getZ() + (rand.nextInt(spread) * (rand.nextBoolean() ? 1 : -1));

            BlockPos tempPos = new BlockPos(newPosX, basePos.getY(), newPosZ);

            if (!(isPosInList(tempPos, list)) || canHaveSamePos)
            {
                list.add(tempPos);
            }
        }
        return list;
    }

    public static ArrayList<BlockPos> setAllPosToTheGround(List<BlockPos> list, WorldGenLevel worldIn)
    {
        ArrayList<BlockPos> tempList = new ArrayList<>();
        for (BlockPos pos1 : list)
        {
            int i = 0;
            boolean flag = false;
            while (!flag)
            {
                BlockPos belowPos = pos1.below();
                Block block = worldIn.getBlockState(belowPos).getBlock();
                if (!(block instanceof AirBlock) && !(block instanceof LiquidBlock) && !(block instanceof LeavesBlock))
                {
                    flag = true;
                    tempList.add(pos1);
                }
                else
                {
                    pos1 = belowPos;
                }
                if (i > 10)
                {
                    flag = true;
                }
                i++;
            }
        }
        return tempList;
    }

    public static boolean isFlying(BlockPos pos, WorldGenLevel worldIn)
    {
        Block block = worldIn.getBlockState(pos.below()).getBlock();
        return block instanceof AirBlock || block instanceof LiquidBlock || block instanceof LeavesBlock;
    }

    /**
     * set all pos in the list above their current pos
     *
     * @param howMuch is for how much block above you want to set the pos
     **/
    public static ArrayList<BlockPos> setPosAboveForAll(List<BlockPos> list, int howMuch)
    {
        ArrayList<BlockPos> tempList = new ArrayList<>();
        for (BlockPos pos1 : list)
        {
            tempList.add(pos1.above(howMuch));
        }
        return tempList;
    }

    public static BlockPos changeRandomlyYPos(RandomSource rand, BlockPos pos, int minOffset, int maxOffSet)
    {
        int i = rand.nextInt(2);
        int yChange = rand.nextInt(maxOffSet - minOffset + 1) + minOffset;
        int y = pos.getY() + (yChange * (i == 0 ? -1 : 1));
        return new BlockPos(pos.getX(), y, pos.getZ());
    }

    /**
     * See Bresenham 3D
     *
     * @return list of point on segment define by 2 BlockPos
     **/
    public static List<BlockPos> getBlockPosLine(BlockPos startPos, BlockPos endPos)
    {
        List<BlockPos> finalPosList = new ArrayList<>();
        int startPosX = startPos.getX();
        int startPosY = startPos.getY();
        int startPosZ = startPos.getZ();
        int endPosX = endPos.getX();
        int endPosY = endPos.getY();
        int endPosZ = endPos.getZ();

        int dx = Math.abs(endPosX - startPosX);
        int dy = Math.abs(endPosY - startPosY);
        int dz = Math.abs(endPosZ - startPosZ);

        int xs = (endPosX > startPosX) ? 1 : -1;
        int ys = (endPosY > startPosY) ? 1 : -1;
        int zs = (endPosZ > startPosZ) ? 1 : -1;

        if (dx >= dy && dx >= dz)
        {
            int p1_err = 2 * dy - dx;
            int p2_err = 2 * dz - dx;
            while (startPosX != endPosX)
            {
                finalPosList.add(new BlockPos(startPosX, startPosY, startPosZ));
                startPosX += xs;
                if (p1_err >= 0)
                {
                    startPosY += ys;
                    p1_err -= 2 * dx;
                }
                if (p2_err >= 0)
                {
                    startPosZ += zs;
                    p2_err -= 2 * dx;
                }
                p1_err += 2 * dy;
                p2_err += 2 * dz;
            }
        }
        else if (dy >= dx && dy >= dz)
        {
            int p1_err = 2 * dx - dy;
            int p2_err = 2 * dz - dy;
            while (startPosY != endPosY)
            {
                finalPosList.add(new BlockPos(startPosX, startPosY, startPosZ));
                startPosY += ys;
                if (p1_err >= 0)
                {
                    startPosX += xs;
                    p1_err -= 2 * dy;
                }
                if (p2_err >= 0)
                {
                    startPosZ += zs;
                    p2_err -= 2 * dy;
                }
                p1_err += 2 * dx;
                p2_err += 2 * dz;
            }
        }
        else
        {
            int p1_err = 2 * dy - dz;
            int p2_err = 2 * dx - dz;
            while (startPosZ != endPosZ)
            {
                finalPosList.add(new BlockPos(startPosX, startPosY, startPosZ));
                startPosZ += zs;
                if (p1_err >= 0)
                {
                    startPosY += ys;
                    p1_err -= 2 * dz;
                }
                if (p2_err >= 0)
                {
                    startPosX += xs;
                    p2_err -= 2 * dz;
                }
                p1_err += 2 * dy;
                p2_err += 2 * dx;
            }
        }
        finalPosList.add(new BlockPos(endPosX, endPosY, endPosZ));
        return finalPosList;
    }

    public static BlockPos minY(BlockPos b1, BlockPos b2)
    {
        return (b1.getY() <= b2.getY()) ? b1 : b2;
    }

    public static BlockPos maxY(BlockPos b1, BlockPos b2)
    {
        return (b1.getY() >= b2.getY()) ? b1 : b2;
    }
}