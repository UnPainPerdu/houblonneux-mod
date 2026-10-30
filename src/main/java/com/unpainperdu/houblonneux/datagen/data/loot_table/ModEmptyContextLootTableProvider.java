package com.unpainperdu.houblonneux.datagen.data.loot_table;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.register.item.ModItemRegister;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.neoforged.neoforge.common.Tags;

public class ModEmptyContextLootTableProvider implements LootTableSubProvider
{
    protected final LootTableSubProvider.Context output;
    protected final HolderGetter<Item> items;

    public static final ResourceKey<LootTable> BEER_DISPENSER_LOOT_TABLE_KEY = createKey("beer_dispenser/disc");
    public static final ResourceKey<LootTable> LUPULINE_FOR_GLM = createKey("glm/lupuline");

    public ModEmptyContextLootTableProvider(Context output)
    {
        this.output = output;
        this.items = output.lookup(Registries.ITEM);
    }

    @Override
    public void run()
    {
        //GLM
        this.output.accept(
                LUPULINE_FOR_GLM,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .add(LootItem.lootTableItem(ModItemRegister.HOP_LUPULIN)
                                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 7)))
                                        )
                                        .setRolls(ContextIntProviders.binomial(1, 0.45F))
                        )
        );

        //beer_dispenser
        this.output.accept(
                BEER_DISPENSER_LOOT_TABLE_KEY,
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

    private static ResourceKey<LootTable> createKey(String path)
    {
        return ResourceKey.create(
                Registries.LOOT_TABLE,
                Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, path)
        );
    }
}