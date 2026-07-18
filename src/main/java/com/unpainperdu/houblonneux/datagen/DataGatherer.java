package com.unpainperdu.houblonneux.datagen;

import com.unpainperdu.houblonneux.datagen.asset.ModParticleDescriptionProvider;
import com.unpainperdu.houblonneux.datagen.asset.ModSoundDefinitionsProvider;
import com.unpainperdu.houblonneux.datagen.asset.language.EnglishLanguageProvider;
import com.unpainperdu.houblonneux.datagen.asset.language.FrenchLanguageProvider;
import com.unpainperdu.houblonneux.datagen.asset.model.ModelProviderDispatcher;
import com.unpainperdu.houblonneux.datagen.data.loot_table.ModGlobalLootModifierProvider;
import com.unpainperdu.houblonneux.datagen.data.loot_table.ModLootTableProvider;
import com.unpainperdu.houblonneux.datagen.data.recipe.RecipeProviderDispatcher;
import com.unpainperdu.houblonneux.datagen.data.tag.ModBlockTagProvider;
import com.unpainperdu.houblonneux.datagen.data.tag.ModDispenserTradeTagProvider;
import com.unpainperdu.houblonneux.datagen.data.tag.ModItemTagProvider;
import com.unpainperdu.houblonneux.datagen.data.trading.ModDispenserTradeProvider;
import com.unpainperdu.houblonneux.datagen.data.trading.ModDispenserTradeTableProvider;
import com.unpainperdu.houblonneux.datagen.data.worldgen.feature.ModBiomeModifierProvider;
import com.unpainperdu.houblonneux.datagen.data.worldgen.feature.ModFeatureProvider;
import com.unpainperdu.houblonneux.datagen.data.worldgen.feature.ModPlacementProvider;
import com.unpainperdu.houblonneux.datagen.data.worldgen.structure.ModProcessorListRegister;
import com.unpainperdu.houblonneux.datagen.data.worldgen.structure.ModStructureTemplatePoolRegister;
import com.unpainperdu.houblonneux.register.registry.ModDataPackRegistriesRegister;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class DataGatherer
{
    public static void gatherData(GatherDataEvent.Client event)
    {
        event.createDatapackRegistryObjects(
                new RegistrySetBuilder()
                        .add(Registries.CONFIGURED_FEATURE, ModFeatureProvider::bootstrap)
                        .add(Registries.PLACED_FEATURE, ModPlacementProvider::bootstrap)
                        .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifierProvider::bootstrap)
                        .add(ModDataPackRegistriesRegister.DISPENSER_TRADE, ModDispenserTradeProvider::bootstrap)
                        .add(ModDataPackRegistriesRegister.DISPENSER_TRADE_TABLE, ModDispenserTradeTableProvider::bootstrap)
                        .add(Registries.PROCESSOR_LIST, ModProcessorListRegister::boostrap)
                        .add(Registries.TEMPLATE_POOL, ModStructureTemplatePoolRegister::boostrap)
        );
        event.createProvider(ModelProviderDispatcher::new);
        event.createProvider(EnglishLanguageProvider::new);
        event.createProvider(FrenchLanguageProvider::new);
        event.createProvider(ModLootTableProvider::new);
        event.createProvider(ModGlobalLootModifierProvider::new);
        event.createBlockAndItemTags(ModBlockTagProvider::new, ModItemTagProvider::new);
        event.createProvider(ModDispenserTradeTagProvider::new);
        event.createProvider(RecipeProviderDispatcher.Runner::new);
        event.createProvider(ModSoundDefinitionsProvider::new);
        event.createProvider(ModParticleDescriptionProvider::new);
    }
}