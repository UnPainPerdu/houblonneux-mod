package com.unpainperdu.houblonneux.level.world.loot.glm;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class VillagerHouseChestGLM extends LootModifier
{
    private final NumberProvider numberProvider;
    private final ItemLike item;

    public static final MapCodec<VillagerHouseChestGLM> CODEC = RecordCodecBuilder.mapCodec(inst ->
            LootModifier.codecStart(inst).and(inst.group(
                    NumberProviders.CODEC.fieldOf("amountProvider").forGetter(e -> e.numberProvider),
                    BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(e -> e.item.asItem())
            )).apply(inst, VillagerHouseChestGLM::new)
    );

    public VillagerHouseChestGLM(LootItemCondition[] conditions, NumberProvider numberProvider, ItemLike item)
    {
        this(conditions, 1, numberProvider, item);
    }

    public VillagerHouseChestGLM(LootItemCondition[] conditions, int priority, NumberProvider numberProvider, ItemLike item)
    {
        super(conditions, priority);
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
        for(LootItemCondition condition : this.conditions)
        {
            if(!condition.test(context))
            {
                return generatedLoot;
            }
        }

        generatedLoot.add(new ItemStack(this.item, this.numberProvider.getInt(context)));
        return generatedLoot;
    }
}