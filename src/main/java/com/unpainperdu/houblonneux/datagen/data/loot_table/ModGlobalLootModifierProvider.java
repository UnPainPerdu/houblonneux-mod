package com.unpainperdu.houblonneux.datagen.data.loot_table;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.world.loot.glm.VillagerHouseChestGLM;
import com.unpainperdu.houblonneux.register.item.ModItemRegister;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

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
        addToVillagerHouse("lupulin", ModItemRegister.HOP_LUPULIN, UniformGenerator.between(3, 7), 0.45F);
    }

    private void addToVillagerHouse(String name, ItemLike itemLike, NumberProvider numberProvider, float chance)
    {
        Item item = itemLike.asItem();
        name = name + "_houblonneux_glm";
        String namePlains = name + "_for_plains";
        add(
                namePlains,
                new VillagerHouseChestGLM(new LootItemCondition[]
                        {
                                new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("chests/village/village_plains_house")).build(),
                                LootItemRandomChanceCondition.randomChance(chance).build()
                        },
                        numberProvider,
                        item));

        String nameSavanna = name + "_for_savanna";
        add(
                nameSavanna,
                new VillagerHouseChestGLM(new LootItemCondition[]
                        {
                                new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("chests/village/village_savanna_house")).build(),
                                LootItemRandomChanceCondition.randomChance(chance).build()
                        },
                        numberProvider,
                        item));

        String nameDesert = name + "_for_desert";
        add(
                nameDesert,
                new VillagerHouseChestGLM(new LootItemCondition[]
                        {
                                new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("chests/village/village_desert_house")).build(),
                                LootItemRandomChanceCondition.randomChance(chance).build()
                        },
                        numberProvider,
                        item));

        String nameSnowy = name + "_for_snowy";
        add(
                nameSnowy,
                new VillagerHouseChestGLM(new LootItemCondition[]
                        {
                                new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("chests/village/village_snowy_house")).build(),
                                LootItemRandomChanceCondition.randomChance(chance).build()
                        },
                        numberProvider,
                        item));

        String nameTaiga = name + "_for_taiga";
        add(
                nameTaiga,
                new VillagerHouseChestGLM(new LootItemCondition[]
                        {
                                new LootTableIdCondition.Builder(Identifier.withDefaultNamespace("chests/village/village_taiga_house")).build(),
                                LootItemRandomChanceCondition.randomChance(chance).build()
                        },
                        numberProvider,
                        item));
    }
}