package com.unpainperdu.houblonneux.datagen.data.loot_table;

import com.unpainperdu.houblonneux.Houblonneux;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.neoforged.neoforge.common.Tags;

public class ModBeerDispenserLootTable implements LootTableSubProvider
{
    protected final LootTableSubProvider.Context output;
    protected final HolderGetter<Item> items;

    public ModBeerDispenserLootTable(Context output)
    {
        this.output = output;
        this.items = output.lookup(Registries.ITEM);
    }

    @Override
    public void run()
    {
        this.output.accept(
                ResourceKey.create(
                        Registries.LOOT_TABLE,
                        Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, "beer_dispenser/disc")
                ),
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .add(TagEntry.expandTag(this.items.getOrThrow(Tags.Items.MUSIC_DISCS))
                                                .apply(
                                                        SetItemCountFunction.setCount(ContextIntProviders.exactly(1))
                                                )
                                        )
                                        .setRolls(ContextIntProviders.binomial(10, 0.2F))
                        )
        );
    }
}