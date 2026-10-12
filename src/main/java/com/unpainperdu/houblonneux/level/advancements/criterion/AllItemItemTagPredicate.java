package com.unpainperdu.houblonneux.level.advancements.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public record AllItemItemTagPredicate(Optional<HolderSet<Item>> items) implements Predicate<List<ItemInstance>>
{
    public AllItemItemTagPredicate(HolderGetter<Item> lookup, TagKey<Item> tag)
    {
        this(Optional.of(lookup.getOrThrow(tag)));
    }

    public static final Codec<AllItemItemTagPredicate> CODEC = RecordCodecBuilder.create(
            i -> i.group(RegistryCodecs.homogeneousList(Registries.ITEM).optionalFieldOf("items").forGetter(AllItemItemTagPredicate::items))
                    .apply(i, AllItemItemTagPredicate::new)
    );

    @Override
    public boolean test(List<ItemInstance> itemStacks)
    {
        List<ItemInstance> filteredItemStacks = List.copyOf(itemStacks).stream().filter(itemInstance -> !itemInstance.is(ItemStack.EMPTY.typeHolder())).toList();
        boolean result = true;
        if (items.isPresent())
        {
            HolderSet<Item> holders = items.get();
            for (Holder<Item> holderItem : holders)
            {
                boolean flag = false;
                for (ItemInstance itemInstances : filteredItemStacks)
                {
                    if (itemInstances.is(holderItem))
                    {
                        flag = true;
                        break;
                    }
                }
                if (!flag)
                {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }
}
