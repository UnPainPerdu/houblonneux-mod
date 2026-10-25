package com.unpainperdu.houblonneux.register.worldgen;

import com.mojang.serialization.MapCodec;
import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.world.worldgen.feature.vegetation.HopPatchFeature;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModFeatureTypeRegister
{
    public static final DeferredRegister<MapCodec<? extends Feature>> FEATURE_TYPES = DeferredRegister.create(BuiltInRegistries.FEATURE_TYPE, Houblonneux.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends Feature>, MapCodec<HopPatchFeature>> HOP_PATCH = FEATURE_TYPES.register("hop_patch", () -> HopPatchFeature.CODEC);

    public static void register(IEventBus modEventBus)
    {
        FEATURE_TYPES.register(modEventBus);
    }
}