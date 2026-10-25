package com.unpainperdu.houblonneux.datagen.data.worldgen.feature;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.world.worldgen.feature.vegetation.HopPatchFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.Feature;

import java.util.List;

public class ModFeatureProvider
{
    public static final ResourceKey<Feature> FOREST_HOP_PATCH = createKey("forest_hop_patch");

    public static void bootstrap(BootstrapContext<Feature> context)
    {
        context.register(
                FOREST_HOP_PATCH,
                new HopPatchFeature(3, 1, 6, List.of(), List.of(BlockTags.SUPPORTS_VEGETATION))
        );
    }

    public static ResourceKey<Feature> createKey(String name)
    {
        return ResourceKey.create(Registries.FEATURE, Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, name));
    }
}