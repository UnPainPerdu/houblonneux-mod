package com.unpainperdu.houblonneux.datagen;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.datagen.asset.ModParticleDescriptionProvider;
import com.unpainperdu.houblonneux.datagen.asset.ModSoundDefinitionsProvider;
import com.unpainperdu.houblonneux.datagen.asset.language.EnglishLanguageProvider;
import com.unpainperdu.houblonneux.datagen.asset.language.FrenchLanguageProvider;
import com.unpainperdu.houblonneux.datagen.asset.model.ModelProviderDispatcher;
import com.unpainperdu.houblonneux.datagen.data.ModAdvancementSubProvider;
import com.unpainperdu.houblonneux.datagen.data.loot_table.ModEmptyContextLootTableProvider;
import com.unpainperdu.houblonneux.datagen.data.loot_table.ModBlockLootTableSubProvider;
import com.unpainperdu.houblonneux.datagen.data.loot_table.ModGlobalLootModifierProvider;
import com.unpainperdu.houblonneux.datagen.data.recipe.ModRecipeProvider;
import com.unpainperdu.houblonneux.datagen.data.tag.ModBlockTagProvider;
import com.unpainperdu.houblonneux.datagen.data.tag.ModDispenserTradeTagProvider;
import com.unpainperdu.houblonneux.datagen.data.tag.ModFluidTagsProvider;
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
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.data.event.PackGenerator;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.Set;

@EventBusSubscriber(modid = Houblonneux.MOD_ID)
public class DataGatherer
{
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event)
    {
        RegistrySetBuilder worldBuilder = new RegistrySetBuilder()
                .add(Registries.FEATURE, ModFeatureProvider::bootstrap)
                .add(Registries.PLACED_FEATURE, ModPlacementProvider::bootstrap)
                .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifierProvider::bootstrap)
                .add(Registries.TEMPLATE_POOL, ModStructureTemplatePoolRegister::boostrap)
                .add(Registries.PROCESSOR_LIST, ModProcessorListRegister::boostrap)
                .add(ModDataPackRegistriesRegister.DISPENSER_TRADE, ModDispenserTradeProvider::bootstrap)
                .add(ModDataPackRegistriesRegister.DISPENSER_TRADE_TABLE, ModDispenserTradeTableProvider::bootstrap)
                ;

        RegistrySetBuilder reloadableBuilder = new RegistrySetBuilder()
                .add(
                        Registries.LOOT_TABLE,
                        new LootTableProvider(
                                Set.of(),
                                List.of(
                                        new LootTableProvider.SubProviderEntry(ModBlockLootTableSubProvider::new, LootContextParamSets.BLOCK),
                                        new LootTableProvider.SubProviderEntry(ModEmptyContextLootTableProvider::new, LootContextParamSets.EMPTY)
                                )
                        )
                )
                .add(
                        Registries.ADVANCEMENT,
                        new AdvancementProvider(
                                List.of(
                                        ModAdvancementSubProvider::new
                                )
                        )
                )
                .add(ModRecipeProvider.create());

        PackGenerator packGenerator = event.getPackGenerator(event.getGenerator().getPackOutput());
        packGenerator.createWorldRegistryObjects(worldBuilder);
        packGenerator.createReloadableRegistryObjects(reloadableBuilder);

        packGenerator.createProvider(ModelProviderDispatcher::new);
        packGenerator.createProvider(EnglishLanguageProvider::new);
        packGenerator.createProvider(FrenchLanguageProvider::new);
        packGenerator.createProvider(ModGlobalLootModifierProvider::new);
        packGenerator.createBlockAndItemTags(ModBlockTagProvider::new, ModItemTagProvider::new);
        packGenerator.createProvider(ModDispenserTradeTagProvider::new);
        packGenerator.createProvider(ModFluidTagsProvider::new);
        packGenerator.createProvider(ModSoundDefinitionsProvider::new);
        packGenerator.createProvider(ModParticleDescriptionProvider::new);
    }
}