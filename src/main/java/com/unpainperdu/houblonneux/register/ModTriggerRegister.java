package com.unpainperdu.houblonneux.register;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.advancements.criterion.AllItemItemTagTrigger;
import com.unpainperdu.houblonneux.level.advancements.criterion.AnyDispenserTradeTrigger;
import com.unpainperdu.houblonneux.level.advancements.criterion.DamageWithEffectTrigger;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModTriggerRegister
{
    public static final DeferredRegister<CriterionTrigger<?>> TRIGGER_TYPES = DeferredRegister.create(Registries.TRIGGER_TYPE, Houblonneux.MOD_ID);

    public static final DeferredHolder<CriterionTrigger<?>, AllItemItemTagTrigger> ALL_ITEM_ITEM_TAG_TRIGGER = TRIGGER_TYPES.register("all_item_item_tag_trigger", AllItemItemTagTrigger::new);
    public static final DeferredHolder<CriterionTrigger<?>, AnyDispenserTradeTrigger> ANY_DISPENSER_TRADE_TRIGGER = TRIGGER_TYPES.register("any_dispenser_trade_trigger", AnyDispenserTradeTrigger::new);
    public static final DeferredHolder<CriterionTrigger<?>, DamageWithEffectTrigger> DAMAGE_WITH_EFFECT = TRIGGER_TYPES.register("damage_with_effect", DamageWithEffectTrigger::new);

    public static void register(IEventBus event)
    {
        TRIGGER_TYPES.register(event);
    }
}
