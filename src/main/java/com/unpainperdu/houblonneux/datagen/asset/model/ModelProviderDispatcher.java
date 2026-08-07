package com.unpainperdu.houblonneux.datagen.asset.model;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.register.block.ModBlockRegister;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.List;
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

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks()
    {
        return super.getKnownBlocks().filter(this::blockFilter);
    }


    @Override
    protected Stream<? extends Holder<Item>> getKnownItems()
    {
        return super.getKnownItems().filter(this::itemFilter);
    }

    private boolean blockFilter(Holder<Block> holder)
    {
        List<DeferredBlock<?>> filter = List.of(
        );
        ResourceKey<Block> key = holder.getKey();
        if (key != null)
        {
            return filter.stream().noneMatch(h -> h.is(key));
        }
        return false;
    }

    private boolean itemFilter(Holder<Item> holder)
    {
        List<Holder<Item>> filter = List.of(
        );
        ResourceKey<Item> key = holder.getKey();
        if (key != null)
        {
            return filter.stream().noneMatch(h -> h.is(key));
        }
        return false;
    }
}