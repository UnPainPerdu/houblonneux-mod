package com.unpainperdu.houblonneux.datagen.asset.model;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.register.block.ModBlockRegister;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;

import java.util.stream.Stream;

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

    /*
    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks()
    {
        return super.getKnownBlocks().filter(holder -> !holder.is(ModBlockRegister.BEER_DISPENSER.getId()));
    }
    */

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems()
    {
        return super.getKnownItems().filter(holder -> !holder.is(ModBlockRegister.BEER_DISPENSER.getId()));
    }
}