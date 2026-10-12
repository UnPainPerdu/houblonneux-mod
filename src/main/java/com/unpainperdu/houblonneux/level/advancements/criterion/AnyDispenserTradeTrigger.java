package com.unpainperdu.houblonneux.level.advancements.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.unpainperdu.houblonneux.register.ModTriggerRegister;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class AnyDispenserTradeTrigger extends SimpleCriterionTrigger<AnyDispenserTradeTrigger.Instance>
{
    @Override
    public Codec<Instance> codec()
    {
        return Instance.CODEC;
    }

    public void trigger(ServerPlayer player)
    {
        this.trigger(player, Instance::matches);
    }

    public record Instance(Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance
    {
        public static final Codec<Instance> CODEC = RecordCodecBuilder.create(
                i -> i.group(
                                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(Instance::player)
                        )
                        .apply(i, Instance::new)
        );

        public static Criterion<Instance> instance()
        {
            return ModTriggerRegister.ANY_DISPENSER_TRADE_TRIGGER.get().createCriterion(new AnyDispenserTradeTrigger.Instance(Optional.empty()));
        }

        private boolean matches()
        {
            return true;
        }
    }
}
