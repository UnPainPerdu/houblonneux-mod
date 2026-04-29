package com.unpainperdu.houblonneux.register.block;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.world.block.crop.Hop;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockRegister
{
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Houblonneux.MOD_ID);

    public static final DeferredBlock<Block> HOP = BLOCKS.registerBlock("hop", Hop::new, () -> ModProperties.HOP_PROPERTIES);

    public static void register(IEventBus event)
    {
        BLOCKS.register(event);
    }
}