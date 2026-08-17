package com.unpainperdu.houblonneux.register.block;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.world.block.block.BeerDispenserBlock;
import com.unpainperdu.houblonneux.level.world.block.block.BrewingBarrelBlock;
import com.unpainperdu.houblonneux.level.world.block.block.CoasterBlock;
import com.unpainperdu.houblonneux.level.world.block.block.crop.HopBlock;
import com.unpainperdu.houblonneux.register.item.ModItemProperties;
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
    //brewing_barrel
    public static final DeferredBlock<BrewingBarrelBlock> OAK_BREWING_BARREL = registerBrewingBarrel("oak_brewing_barrel");
    public static final DeferredBlock<BrewingBarrelBlock> SPRUCE_BREWING_BARREL = registerBrewingBarrel("spruce_brewing_barrel");
    public static final DeferredBlock<BrewingBarrelBlock> BIRCH_BREWING_BARREL = registerBrewingBarrel("birch_brewing_barrel");
    public static final DeferredBlock<BrewingBarrelBlock> JUNGLE_BREWING_BARREL = registerBrewingBarrel("jungle_brewing_barrel");
    public static final DeferredBlock<BrewingBarrelBlock> ACACIA_BREWING_BARREL = registerBrewingBarrel("acacia_brewing_barrel");
    public static final DeferredBlock<BrewingBarrelBlock> DARK_OAK_BREWING_BARREL = registerBrewingBarrel("dark_oak_brewing_barrel");
    public static final DeferredBlock<BrewingBarrelBlock> MANGROVE_BREWING_BARREL = registerBrewingBarrel("mangrove_brewing_barrel");
    public static final DeferredBlock<BrewingBarrelBlock> CHERRY_BREWING_BARREL = registerBrewingBarrel("cherry_brewing_barrel");
    public static final DeferredBlock<BrewingBarrelBlock> PALE_OAK_BREWING_BARREL = registerBrewingBarrel("pale_oak_brewing_barrel");
    public static final DeferredBlock<BrewingBarrelBlock> BAMBOO_BREWING_BARREL = registerBrewingBarrel("bamboo_brewing_barrel");
    public static final DeferredBlock<BrewingBarrelBlock> CRIMSON_BREWING_BARREL = registerBrewingBarrel("crimson_brewing_barrel");
    public static final DeferredBlock<BrewingBarrelBlock> WARPED_BREWING_BARREL = registerBrewingBarrel("warped_brewing_barrel");

    private static DeferredBlock<BrewingBarrelBlock> registerBrewingBarrel(String name)
    {
        DeferredBlock<BrewingBarrelBlock> block = registerBlock(name, BrewingBarrelBlock::new, () -> ModBlockProperties.BREWING_BARREL_PROPERTIES);
        ModItemRegister.ITEMS.registerItem(name, p -> new BlockItem(block.get(), p), () -> ModItemProperties.ModBlockItemProperties.BREWING_BARREL_PROPERTIES);
        return block;
    }

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