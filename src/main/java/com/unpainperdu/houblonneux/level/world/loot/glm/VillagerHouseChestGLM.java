package com.unpainperdu.houblonneux.level.world.loot.glm;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

import java.util.Optional;

public class VillagerHouseChestGLM extends LootModifier
{
    private final Holder<ContextIntProvider> numberProvider;
    private final ItemLike item;

    public static final MapCodec<VillagerHouseChestGLM> CODEC = RecordCodecBuilder.mapCodec(inst ->
            LootModifier.codecStart(inst).and(inst.group(
                    ContextIntProviders.CODEC.fieldOf("amountProvider").forGetter(e -> e.numberProvider),
                    BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(e -> e.item.asItem())
            )).apply(inst, VillagerHouseChestGLM::new)
    );

    public VillagerHouseChestGLM(Holder<LootItemCondition> condition, Holder<ContextIntProvider> numberProvider, ItemLike item)
    {
        this(Optional.of(condition), 1, numberProvider, item);
    }

    public VillagerHouseChestGLM(Optional<Holder<LootItemCondition>> condition, int priority, Holder<ContextIntProvider> numberProvider, ItemLike item)
    {
        super(condition, priority);
        this.numberProvider = numberProvider;
        this.item = item;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec()
    {
        return CODEC;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context)
    {
        if (this.condition.isPresent())
        {
            if (!condition.get().value().test(context))
            {
                return generatedLoot;
            }
        }

        generatedLoot.add(new ItemStack(this.item, this.numberProvider.value().getInt(context)));
        return generatedLoot;
    }
}