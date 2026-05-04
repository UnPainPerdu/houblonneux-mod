package com.unpainperdu.houblonneux.datagen.data.worldgen.feature;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.world.worldgen.feature.vegetation.PatchConfiguration;
import com.unpainperdu.houblonneux.register.worldgen.ModFeatureRegister;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class ModFeatureProvider
{
    public static final ResourceKey<ConfiguredFeature<?, ?>> FOREST_HOP_PATCH = createKey("forest_hop_patch");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context)
    {
        FeatureUtils.register(
                context,
                FOREST_HOP_PATCH,
                ModFeatureRegister.HOP_PATCH.get(),
                new PatchConfiguration.Builder()
                        .groundAllowed(List.of(BlockTags.SUPPORTS_VEGETATION))
                        .minFlowerNumber(1)
                        .maxFlowerNumber(6)
                        .spread(3)
                        .build()
        );
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name)
    {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, name));
    }
}