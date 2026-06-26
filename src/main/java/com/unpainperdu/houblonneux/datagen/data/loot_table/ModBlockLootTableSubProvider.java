package com.unpainperdu.houblonneux.datagen.data.loot_table;

import com.unpainperdu.houblonneux.level.world.block.block.crop.HopBlock;
import com.unpainperdu.houblonneux.level.world.block.blockstate.HopBlockstate;
import com.unpainperdu.houblonneux.register.block.ModBlockRegister;
import com.unpainperdu.houblonneux.register.item.ModItemRegister;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;

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
}