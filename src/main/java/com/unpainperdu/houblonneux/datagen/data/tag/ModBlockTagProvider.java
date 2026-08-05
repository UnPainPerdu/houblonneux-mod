package com.unpainperdu.houblonneux.datagen.data.tag;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.level.world.block.block.BrewingBarrelBlock;
import com.unpainperdu.houblonneux.register.block.ModBlockRegister;
import com.unpainperdu.houblonneux.register.block.list.BlockList;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ModBlockTagProvider extends BlockTagsProvider
{
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider)
    {
        super(output, lookupProvider, Houblonneux.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries)
    {
        this.addToTag(BlockTags.CROPS,
                Stream.of(
                        ModBlockRegister.HOP
                )
        );
        this.addToTag(BlockTags.MAINTAINS_FARMLAND,
                Stream.of(
                        ModBlockRegister.HOP
                )
        );
        this.addToTag(ModBlockTags.BREWING_BARREL,
                BlockList.getAllBlocksFromClass(true, BrewingBarrelBlock.class).stream().map(t -> () -> t)
        );
        this.addToTag(BlockTags.MINEABLE_WITH_AXE,
                BlockList.getAllBlocksFromClass(true, BrewingBarrelBlock.class).stream().map(t -> () -> t)
        );
        this.addToTag(BlockTags.MINEABLE_WITH_PICKAXE,
                Stream.of(
                        ModBlockRegister.BEER_DISPENSER
                ));
        this.addToTag(BlockTags.NEEDS_IRON_TOOL,
                Stream.of(
                        ModBlockRegister.BEER_DISPENSER
                ));
    }

    @SafeVarargs
    private void addToTag(TagKey<Block> blockTag, Stream<Supplier<? extends Block>>... blockStreams)
    {
        this.tag(blockTag).add(
                Stream.of(blockStreams)
                        .flatMap(s -> s)
                        .map(Supplier::get)
                        .toArray(Block[]::new)
        );
    }
}