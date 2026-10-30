package com.unpainperdu.houblonneux.datagen.data.loot_table;

import com.unpainperdu.houblonneux.Houblonneux;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.AddTableLootModifier;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider
{

    public ModGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries)
    {
        super(output, registries, Houblonneux.MOD_ID);
    }

    @Override
    protected void start()
    {
        addToVillagerHouse("lupulin", ModEmptyContextLootTableProvider.LUPULINE_FOR_GLM);
    }

    private void addToVillagerHouse(String name, ResourceKey<LootTable> lootTableToAdd)
    {
        name = name + "_houblonneux_glm";
        String namePlains = name + "_for_plains";
        add(
                namePlains,
                new AddTableLootModifier(
                        Optional.of(Holder.direct(new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("chests/village/village_plains_house")).build())),
                        1,
                        lootTableToAdd
                )
        );
        String nameSavanna = name + "_for_savanna";
        add(
                nameSavanna,
                new AddTableLootModifier(
                        Optional.of(Holder.direct(new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("chests/village/village_savanna_house")).build())),
                        1,
                        lootTableToAdd
                )
        );

        String nameDesert = name + "_for_desert";
        add(
                nameDesert,
                new AddTableLootModifier(
                        Optional.of(Holder.direct(new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("chests/village/village_desert_house")).build())),
                        1,
                        lootTableToAdd
                )
        );

        String nameSnowy = name + "_for_snowy";
        add(
                nameSnowy,
                new AddTableLootModifier(
                        Optional.of(Holder.direct(new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("chests/village/village_snowy_house")).build())),
                        1,
                        lootTableToAdd
                )
        );

        String nameTaiga = name + "_for_taiga";
        add(
                nameTaiga,
                new AddTableLootModifier(
                        Optional.of(Holder.direct(new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("chests/village/village_taiga_house")).build())),
                        1,
                        lootTableToAdd
                )
        );
    }
}