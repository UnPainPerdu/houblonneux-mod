package com.unpainperdu.houblonneux.datagen.data.loot_table;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.register.item.ModItemRegister;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.AllOfCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.AddTableLootModifier;

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
        addToVillagerHouse("lupulin", ModItemRegister.HOP_LUPULIN, ContextIntProviders.between(3, 7), 0.45F);
    }

    private void addToVillagerHouse(String name, ItemLike itemLike, Holder<ContextIntProvider> numberProvider, float chance)
    {
        Item item = itemLike.asItem();
        name = name + "_houblonneux_glm";
        String namePlains = name + "_for_plains";
        add(
                namePlains,
                new AddTableLootModifier(
                        Optional.of(Holder.direct(AllOfCondition.allOf(LootItemRandomChanceCondition.randomChance(chance)).build())), 1, this.getKeyFromVanillaPath("chests/village/village_plains_house")
                )
        );
        String nameSavanna = name + "_for_savanna";
        add(
                nameSavanna,
                new AddTableLootModifier(
                        Optional.of(Holder.direct(AllOfCondition.allOf(LootItemRandomChanceCondition.randomChance(chance)).build())), 1, this.getKeyFromVanillaPath("chests/village/village_savanna_house")
                )
        );

        String nameDesert = name + "_for_desert";
        add(
                nameDesert,
                new AddTableLootModifier(
                        Optional.of(Holder.direct(AllOfCondition.allOf(LootItemRandomChanceCondition.randomChance(chance)).build())), 1, this.getKeyFromVanillaPath("chests/village/village_desert_house")
                )
        );

        String nameSnowy = name + "_for_snowy";
        add(
                nameSnowy,
                new AddTableLootModifier(
                        Optional.of(Holder.direct(AllOfCondition.allOf(LootItemRandomChanceCondition.randomChance(chance)).build())), 1, this.getKeyFromVanillaPath("chests/village/village_snowy_house")
                )
        );

        String nameTaiga = name + "_for_taiga";
        add(
                nameTaiga,
                new AddTableLootModifier(
                        Optional.of(Holder.direct(AllOfCondition.allOf(LootItemRandomChanceCondition.randomChance(chance)).build())), 1, this.getKeyFromVanillaPath("chests/village/village_taiga_house")
                )
        );
    }

    private ResourceKey<LootTable> getKeyFromVanillaPath(String path)
    {
        return getKeyFromPath("minecraft", path);
    }

    private ResourceKey<LootTable> getKeyFromPath(String nameSpace, String path)
    {
        return ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(nameSpace, path));
    }
}