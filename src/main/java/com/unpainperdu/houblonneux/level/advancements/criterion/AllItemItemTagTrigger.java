package com.unpainperdu.houblonneux.level.advancements.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.unpainperdu.houblonneux.register.ModTriggerRegister;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.advancements.triggers.SimpleCriterionTrigger;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.Optional;

public class AllItemItemTagTrigger extends SimpleCriterionTrigger<AllItemItemTagTrigger.Instance>
{
    @Override
    public Codec<Instance> codec()
    {
        return Instance.CODEC;
    }

    public void trigger(ServerPlayer player, Inventory inventory)
    {
        this.trigger(player, triggerInstance -> triggerInstance.matches(inventory));
    }

    public record Instance(Optional<Holder<LootItemCondition>> player,
                           AllItemItemTagPredicate tagPredicate) implements SimpleCriterionTrigger.SimpleInstance
    {

        public static final Codec<Instance> CODEC = RecordCodecBuilder.create(
                i -> i.group(
                                LootItemCondition.CODEC.optionalFieldOf("player").forGetter(Instance::player),
                                AllItemItemTagPredicate.CODEC.fieldOf("tag_predicate").forGetter(Instance::tagPredicate)
                        )
                        .apply(i, Instance::new)
        );

        public static Criterion<Instance> instance(AllItemItemTagPredicate tagPredicate)
        {
            return ModTriggerRegister.ALL_ITEM_ITEM_TAG_TRIGGER.get().createCriterion(new Instance(Optional.empty(), tagPredicate));
        }

        private boolean matches(Inventory inventory)
        {
            if (inventory.isEmpty())
            {
                return false;
            }

            return this.tagPredicate.test(inventory.getNonEquipmentItems().stream().map(i -> (ItemInstance) i).toList());
        }
    }
}