package com.unpainperdu.houblonneux.datagen.asset.model.item;

import com.unpainperdu.houblonneux.register.item.ModItemRegister;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;

public class ItemModelProvider
{
    private final ItemModelGenerators itemModelsGenerator;
    private final ModelProvider provider;

    public ItemModelProvider(ItemModelGenerators itemModelsGenerator, ModelProvider provider)
    {
        this.itemModelsGenerator = itemModelsGenerator;
        this.provider = provider;
        generateModel();
    }

    public void generateModel(){
        this.itemModelsGenerator.generateFlatItem(ModItemRegister.HOP_FLOWER.get(), ModelTemplates.FLAT_ITEM);
        this.itemModelsGenerator.generateFlatItem(ModItemRegister.HOP_LUPULIN.get(), ModelTemplates.FLAT_ITEM);
    }
}