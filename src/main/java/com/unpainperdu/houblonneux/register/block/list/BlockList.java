package com.unpainperdu.houblonneux.register.block.list;

import com.unpainperdu.houblonneux.Houblonneux;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;

import java.util.*;

public class BlockList
{
    public static List<Block> ALL_BLOCKS = BuiltInRegistries.BLOCK.stream().toList();

    public static final List<Block> ALL_HOUBLONNEUX_BLOCKS = generateAllBlocksList();

    private static List<Block> generateAllBlocksList()
    {
        Object[] allBlocksRegistered = BuiltInRegistries.BLOCK.stream().toArray();

        List<Block> allBlocks = new ArrayList<>();

        for (Object obj : allBlocksRegistered)
        {
            if (obj instanceof Block block)
            {
                if (BuiltInRegistries.BLOCK.getKey(block).getNamespace().equals(Houblonneux.MOD_ID))
                {
                    allBlocks.add(block);
                }
            }
        }

        allBlocks.sort(new BlockComparator());
        return allBlocks;
    }

    public static List<Block> getAllBlocksFromClass(boolean onlyHoublonneuxBlock, Class<?>... cList)
    {
        List<Block> baseList = onlyHoublonneuxBlock ? ALL_HOUBLONNEUX_BLOCKS : ALL_BLOCKS;
        List<Block> list = new ArrayList<>();
        for (Block block : baseList)
        {
            for (Class<?> c : cList)
            {
                if (c.isInstance(block))
                {
                    list.add(block);
                }
            }

        }
        return list;
    }
}