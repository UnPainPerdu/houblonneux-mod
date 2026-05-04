package com.unpainperdu.houblonneux.datagen.data.worldgen.feature;

import com.unpainperdu.houblonneux.Houblonneux;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

public class ModPlacementProvider
{
    public static final ResourceKey<PlacedFeature> FOREST_HOP_PATCH = createKey("forest_hop_patch");

    public static void bootstrap(BootstrapContext<PlacedFeature> context)
    {
        HolderGetter<ConfiguredFeature<?, ?>> holdergetter = context.lookup(Registries.CONFIGURED_FEATURE);

        final Holder<ConfiguredFeature<?, ?>> FOREST_HOP_PATCH_HOLDER = holdergetter.getOrThrow(ModFeatureProvider.FOREST_HOP_PATCH);
        PlacementUtils.register(context,
                FOREST_HOP_PATCH,
                FOREST_HOP_PATCH_HOLDER,
                RarityFilter.onAverageOnceEvery(20),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome());
    }

    public static ResourceKey<PlacedFeature> createKey(String name)
    {
        return ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, name));
    }
}