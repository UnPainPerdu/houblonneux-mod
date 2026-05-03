package com.unpainperdu.houblonneux.datagen;

import com.unpainperdu.houblonneux.datagen.asset.language.EnglishLanguageProvider;
import com.unpainperdu.houblonneux.datagen.asset.language.FrenchLanguageProvider;
import com.unpainperdu.houblonneux.datagen.asset.model.ModelProviderDispatcher;
import com.unpainperdu.houblonneux.datagen.data.loot_table.ModLootTableProvider;
import com.unpainperdu.houblonneux.datagen.data.recipe.RecipeProviderDispatcher;
import com.unpainperdu.houblonneux.datagen.data.tag.ModBlockTagProvider;
import com.unpainperdu.houblonneux.datagen.data.tag.ModItemTagProvider;
import net.minecraft.core.RegistrySetBuilder;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class DataGatherer
{
    public static void gatherData(GatherDataEvent.Client event)
    {
        event.createDatapackRegistryObjects(
                new RegistrySetBuilder()
        );
        event.createProvider(ModelProviderDispatcher::new);
        event.createProvider(EnglishLanguageProvider::new);
        event.createProvider(FrenchLanguageProvider::new);
        event.createProvider(ModLootTableProvider::new);
        event.createBlockAndItemTags(ModBlockTagProvider::new, ModItemTagProvider::new);
        event.createProvider(RecipeProviderDispatcher.Runner::new);
    }
}