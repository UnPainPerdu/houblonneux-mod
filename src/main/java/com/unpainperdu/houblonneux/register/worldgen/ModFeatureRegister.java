package com.unpainperdu.houblonneux.register.worldgen;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.world.worldgen.feature.vegetation.HopPatchFeature;
import com.unpainperdu.houblonneux.level.world.worldgen.feature.vegetation.PatchConfiguration;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModFeatureRegister
{
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, Houblonneux.MOD_ID);

    public static final DeferredHolder<Feature<?>, Feature<PatchConfiguration>> HOP_PATCH = FEATURES.register("hop_patch", () -> new HopPatchFeature(PatchConfiguration.CODEC));

    public static void register(IEventBus modEventBus)
    {
        FEATURES.register(modEventBus);
    }
}