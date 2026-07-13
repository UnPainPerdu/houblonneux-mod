package com.unpainperdu.houblonneux.datagen.data.loot_table;

import com.unpainperdu.houblonneux.Houblonneux;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.common.Tags;

import java.util.function.BiConsumer;

public class ModBeerDispenserLootTable implements LootTableSubProvider
{
    protected final HolderLookup.Provider registries;

    public ModBeerDispenserLootTable(HolderLookup.Provider registries)
    {
        this.registries = registries;
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output)
    {
        output.accept(
                ResourceKey.create(
                        Registries.LOOT_TABLE,
                        Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "beer_dispenser/disc")
                ),
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .add(TagEntry.expandTag(Tags.Items.MUSIC_DISCS)
                                                .apply(
                                                        SetItemCountFunction.setCount(ConstantValue.exactly(1))
                                                )
                                        )
                                        .setRolls(BinomialDistributionGenerator.binomial(10, 0.2F))
                        )
        );
    }
}