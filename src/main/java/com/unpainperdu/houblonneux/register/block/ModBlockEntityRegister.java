package com.unpainperdu.houblonneux.register.block;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.world.block.block.BeerDispenserBlock;
import com.unpainperdu.houblonneux.level.world.block.entity.BeerDispenserBlockEntity;
import com.unpainperdu.houblonneux.level.world.block.entity.CoasterBlockEntity;
import com.unpainperdu.houblonneux.register.block.list.BlockList;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntityRegister
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Houblonneux.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BeerDispenserBlockEntity>> BEER_DISPENSER = BLOCK_ENTITY_TYPES.register(
            "beer_dispenser",
            () -> new BlockEntityType<>(
                    BeerDispenserBlockEntity::new,
                    BlockList.getAllBlocksFromClass(true, BeerDispenserBlock.class).toArray(new Block[0])
            )
    );

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CoasterBlockEntity>> COASTER = BLOCK_ENTITY_TYPES.register(
            "coaster",
            () -> new BlockEntityType<>(
                    CoasterBlockEntity::new,
                    ModBlockRegister.COASTER.get()
            )
    );

    public static void register(IEventBus event)
    {
        BLOCK_ENTITY_TYPES.register(event);
    }
}