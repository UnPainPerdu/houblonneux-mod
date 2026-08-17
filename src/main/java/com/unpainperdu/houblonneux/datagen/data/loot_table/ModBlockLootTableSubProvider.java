package com.unpainperdu.houblonneux.datagen.data.loot_table;

import com.unpainperdu.houblonneux.level.world.block.block.BrewingBarrelBlock;
import com.unpainperdu.houblonneux.level.world.block.block.CoasterBlock;
import com.unpainperdu.houblonneux.level.world.block.block.crop.HopBlock;
import com.unpainperdu.houblonneux.level.world.block.blockstate.HopBlockstate;
import com.unpainperdu.houblonneux.register.ModDataComponentRegister;
import com.unpainperdu.houblonneux.register.block.ModBlockRegister;
import com.unpainperdu.houblonneux.register.item.ModItemRegister;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;
import java.util.stream.IntStream;

public class ModBlockLootTableSubProvider extends BlockLootSubProvider
{
    protected ModBlockLootTableSubProvider(HolderLookup.Provider registries)
    {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, registries);
    }

    @Override
    protected Iterable<Block> getKnownBlocks()
    {
        return ModBlockRegister.BLOCKS.getEntries()
                .stream()
                .map(e -> (Block) e.value())
                .toList();
    }

    @Override
    protected void generate()
    {
        hopDrop();
        this.add(ModBlockRegister.BEER_DISPENSER.get(), this::createDoorTable);
        this.add(ModBlockRegister.COASTER.get(), this.integerPropertySelfDrop(ModBlockRegister.COASTER.get(), CoasterBlock.COASTER_NUMBER, 1, 4));
        brewingBarrelBlockDrop(ModBlockRegister.OAK_BREWING_BARREL.get());
        brewingBarrelBlockDrop(ModBlockRegister.SPRUCE_BREWING_BARREL.get());
        brewingBarrelBlockDrop(ModBlockRegister.BIRCH_BREWING_BARREL.get());
        brewingBarrelBlockDrop(ModBlockRegister.JUNGLE_BREWING_BARREL.get());
        brewingBarrelBlockDrop(ModBlockRegister.ACACIA_BREWING_BARREL.get());
        brewingBarrelBlockDrop(ModBlockRegister.DARK_OAK_BREWING_BARREL.get());
        brewingBarrelBlockDrop(ModBlockRegister.MANGROVE_BREWING_BARREL.get());
        brewingBarrelBlockDrop(ModBlockRegister.CHERRY_BREWING_BARREL.get());
        brewingBarrelBlockDrop(ModBlockRegister.PALE_OAK_BREWING_BARREL.get());
        brewingBarrelBlockDrop(ModBlockRegister.BAMBOO_BREWING_BARREL.get());
        brewingBarrelBlockDrop(ModBlockRegister.CRIMSON_BREWING_BARREL.get());
        brewingBarrelBlockDrop(ModBlockRegister.WARPED_BREWING_BARREL.get());
    }

    private void hopDrop()
    {
        HopBlock hopBlock = ModBlockRegister.HOP.get();
        Item hopFlower = ModItemRegister.HOP_FLOWER.get();
        LootTable.Builder lootTable = LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .add(LootItem.lootTableItem(hopFlower)
                                        .when(
                                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(hopBlock)
                                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(HopBlock.HOP_BLOCKSTATE, HopBlockstate.MIDDLE_FLOWERED))
                                        )
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))
                                )
                );
        this.add(hopBlock, lootTable);
    }

    private LootTable.Builder integerPropertySelfDrop(Block block, IntegerProperty property, int min, int max)
    {
        return integerPropertyDrop(block, block, property, min, max);
    }

    private LootTable.Builder integerPropertyDrop(Block block, ItemLike drop, IntegerProperty property, int min, int max)
    {
        if (!block.defaultBlockState().hasProperty(property))
        {
            throw new RuntimeException("Property " + property.getName() + " is missing in " + block);
        }
        return LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(this.applyExplosionDecay(block, LootItem.lootTableItem(drop)
                                .apply(IntStream.rangeClosed(min, max).boxed().toList(),
                                        (count) -> SetItemCountFunction.setCount(ConstantValue.exactly((float) count))
                                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                                .hasProperty(property, count)))))));
    }

    private void brewingBarrelBlockDrop(Block block)
    {
        LootTable.Builder lootTable = LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .add(LootItem.lootTableItem(block)
                                        .apply(
                                                CopyComponentsFunction.copyComponentsFromBlockEntity(LootContextParams.BLOCK_ENTITY)
                                                        .include(DataComponents.CUSTOM_NAME)
                                                        .include(DataComponents.CONTAINER)
                                                        .include(DataComponents.LOCK)
                                                        .include(ModDataComponentRegister.FLUIDSTACK.get())
                                        )
                                        .when(
                                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BrewingBarrelBlock.POSITION, 0))
                                        )
                                )
                );
        this.add(block, lootTable);
    }
}