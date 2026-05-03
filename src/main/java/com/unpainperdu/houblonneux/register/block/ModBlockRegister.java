package com.unpainperdu.houblonneux.register.block;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.world.block.block.crop.HopBlock;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockRegister
{
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Houblonneux.MOD_ID);

    public static final DeferredBlock<HopBlock> HOP = BLOCKS.registerBlock("hop", HopBlock::new, () -> ModBlockProperties.HOP_PROPERTIES);

    public static void register(IEventBus event)
    {
        BLOCKS.register(event);
    }
}