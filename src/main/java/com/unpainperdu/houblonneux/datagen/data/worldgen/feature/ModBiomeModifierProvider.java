package com.unpainperdu.houblonneux.datagen.data.worldgen.feature;

import com.unpainperdu.houblonneux.Houblonneux;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModBiomeModifierProvider
{
    public static final ResourceKey<BiomeModifier> ADD_HOP_PATCH_TO_FOREST = createKey("add_hop_patch_to_forest");

    public static void bootstrap(BootstrapContext<BiomeModifier> context)
    {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);

        context.register(
                ADD_HOP_PATCH_TO_FOREST,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        biomes.getOrThrow(Tags.Biomes.IS_FOREST),
                        HolderSet.direct(placedFeatures.getOrThrow(ModPlacementProvider.FOREST_HOP_PATCH)),
                        GenerationStep.Decoration.VEGETAL_DECORATION
                )
        );
    }

    public static ResourceKey<BiomeModifier> createKey(String name)
    {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, name));
    }
}