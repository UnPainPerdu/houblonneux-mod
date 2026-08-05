package com.unpainperdu.houblonneux.register.block;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.world.block.block.BeerDispenserBlock;
import com.unpainperdu.houblonneux.level.world.block.block.BrewingBarrelBlock;
import com.unpainperdu.houblonneux.level.world.block.block.CoasterBlock;
import com.unpainperdu.houblonneux.level.world.block.block.crop.HopBlock;
import com.unpainperdu.houblonneux.register.item.ModItemRegister;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlockRegister
{
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Houblonneux.MOD_ID);

    public static final DeferredBlock<HopBlock> HOP = registerBlock("hop", HopBlock::new, () -> ModBlockProperties.HOP_PROPERTIES);
    public static final DeferredBlock<BeerDispenserBlock> BEER_DISPENSER = registerBlockWithSimpleItem("beer_dispenser", BeerDispenserBlock::new, () -> ModBlockProperties.BEER_DISPENSER_PROPERTIES);
    public static final DeferredBlock<CoasterBlock> COASTER = registerBlockWithSimpleItem("coaster", CoasterBlock::new, () -> ModBlockProperties.COASTER_PROPERTIES);
    public static final DeferredBlock<BrewingBarrelBlock> OAK_BREWING_BARREL = registerBlockWithSimpleItem("oak_brewing_barrel", BrewingBarrelBlock::new, BlockBehaviour.Properties::of);

    public static <B extends Block> DeferredBlock<B> registerBlock(String name, Function<BlockBehaviour.Properties, ? extends B> blockConstructor, Supplier<BlockBehaviour.Properties> properties)
    {
        return BLOCKS.registerBlock(name, blockConstructor, properties);
    }

    public static <B extends Block> DeferredBlock<B> registerBlockWithSimpleItem(String name, Function<BlockBehaviour.Properties, ? extends B> blockConstructor, Supplier<BlockBehaviour.Properties> properties)
    {
        DeferredBlock<B> block = registerBlock(name, blockConstructor, properties);
        ModItemRegister.ITEMS.registerItem(name, p -> new BlockItem(block.get(), p));
        return block;
    }

    public static void register(IEventBus event)
    {
        BLOCKS.register(event);
    }
}