package com.unpainperdu.houblonneux.register.codec;

import com.mojang.serialization.MapCodec;
import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.world.loot.glm.VillagerHouseChestGLM;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModGLMCodecRegister
{
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIER_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Houblonneux.MOD_ID);

    public static final Supplier<MapCodec<VillagerHouseChestGLM>> VILLAGER_CHEST_GLM = GLOBAL_LOOT_MODIFIER_SERIALIZERS.register("villager_chest_glm", () -> VillagerHouseChestGLM.CODEC);

    public static void register(IEventBus event)
    {
        GLOBAL_LOOT_MODIFIER_SERIALIZERS.register(event);
    }
}