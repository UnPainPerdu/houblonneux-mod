package com.unpainperdu.houblonneux.datagen.asset.model;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.datagen.asset.model.block.BlockModelProvider;
import com.unpainperdu.houblonneux.datagen.asset.model.item.ItemModelProvider;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.data.PackOutput;

public class ModelProviderDispatcher extends ModelProvider
{
    public ModelProviderDispatcher(PackOutput output)
    {
        super(output, Houblonneux.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels)
    {
        new ItemModelProvider(itemModels, this);
        new BlockModelProvider(blockModels, itemModels, this);
    }
}