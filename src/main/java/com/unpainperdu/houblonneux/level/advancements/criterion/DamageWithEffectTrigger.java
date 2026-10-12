package com.unpainperdu.houblonneux.level.advancements.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.unpainperdu.houblonneux.register.ModTriggerRegister;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.MobEffectsPredicate;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;

import java.util.Optional;

public class DamageWithEffectTrigger extends SimpleCriterionTrigger<DamageWithEffectTrigger.Instance>
{
    @Override
    public Codec<Instance> codec()
    {
        return Instance.CODEC;
    }

    public void triggerAsKiller(ServerPlayer player, float damageTaken)
    {
        this.trigger(player, false, damageTaken);
    }

    public void triggerAsTarget(ServerPlayer player, float damageTaken)
    {
        this.trigger(player, true, damageTaken);
    }

    private void trigger(ServerPlayer player, boolean isTarget, float damageTaken)
    {
        this.trigger(player, i -> i.matches(player, isTarget, damageTaken));
    }

    public record Instance(Optional<ContextAwarePredicate> player,
                           boolean isTarget,
                           float minDamageTaken,
                           Optional<MobEffectsPredicate> mobEffectsPredicate) implements SimpleCriterionTrigger.SimpleInstance
    {
        public static final Codec<Instance> CODEC = RecordCodecBuilder.create(
                i -> i.group(
                                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(Instance::player),
                                Codec.BOOL.fieldOf("isTarget").forGetter(Instance::isTarget),
                                Codec.FLOAT.fieldOf("minDamageTaken").forGetter(Instance::minDamageTaken),
                                MobEffectsPredicate.CODEC.optionalFieldOf("predicate").forGetter(Instance::mobEffectsPredicate)
                        )
                        .apply(i, Instance::new)
        );

        public static Criterion<Instance> instance(boolean isTarget, float minDamageTaken, Holder<MobEffect> mobEffect)
        {
            return ModTriggerRegister.DAMAGE_WITH_EFFECT.get().createCriterion(new Instance(Optional.empty(), isTarget, minDamageTaken, MobEffectsPredicate.Builder.effects().and(mobEffect).build()));
        }

        private boolean matches(ServerPlayer serverPlayer, boolean isTarget, float damageTaken)
        {
            if (isTarget != this.isTarget || damageTaken < this.minDamageTaken)
            {
                return false;
            }
            return this.mobEffectsPredicate.map(effectsPredicate -> effectsPredicate.matches(serverPlayer)).orElse(true);
        }
    }
}
