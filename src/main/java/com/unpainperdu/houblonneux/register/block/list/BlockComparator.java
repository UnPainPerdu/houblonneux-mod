package com.unpainperdu.houblonneux.register.block.list;

import net.minecraft.world.level.block.Block;

import java.util.Comparator;

public class BlockComparator implements Comparator<Block>
{

    @Override
    public int compare(Block o1, Block o2)
    {
        String id1 = o1.toString();
        String id2 = o2.toString();

        return id1.compareTo(id2);
    }
}