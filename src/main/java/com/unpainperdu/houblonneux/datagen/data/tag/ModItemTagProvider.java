package com.unpainperdu.houblonneux.datagen.data.tag;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.register.item.ModItemRegister;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagCopyingItemTagProvider;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ModItemTagProvider extends BlockTagCopyingItemTagProvider
{
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags)
    {
        super(output, lookupProvider, blockTags, Houblonneux.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries)
    {
        this.addToTag(Tags.Items.CROPS,
                Stream.of(
                        ModItemRegister.HOP_FLOWER
                )
        );
        this.addToTag(Tags.Items.FOODS,
                Stream.of(
                        ModItemRegister.HOP_FLOWER
                )
        );
        this.tag(Tags.Items.FOODS).addTag(ModItemTags.COASTER_POSABLE);
        this.addToTag(ModItemTags.BEER_BOTTLE,
                Stream.of(
                        ModItemRegister.EMERALD_CALL_BOTTLE,
                        ModItemRegister.WORM_HOLE_BOTTLE,
                        ModItemRegister.GROS_GUEULETON_BOTTLE,
                        ModItemRegister.PAIN_DIEUX_BOTTLE,
                        ModItemRegister.REAL_DWARVE_BOTTLE,
                        ModItemRegister.PIED_DE_GEANTS_BOTTLE,
                        ModItemRegister.LA_BLANCHE_DE_CHEZ_NOUS_BOTTLE
                )
        );
        this.addToTag(ModItemTags.BEER_GLASS,
                Stream.of(
                        ModItemRegister.EMERALD_CALL_GLASS,
                        ModItemRegister.WORM_HOLE_GLASS,
                        ModItemRegister.GROS_GUEULETON_GLASS,
                        ModItemRegister.PAIN_DIEUX_GLASS,
                        ModItemRegister.REAL_DWARVE_GLASS,
                        ModItemRegister.PIED_DE_GEANTS_GLASS,
                        ModItemRegister.LA_BLANCHE_DE_CHEZ_NOUS_GLASS
                ));
        this.addToTag(ModItemTags.BEER_MUG,
                Stream.of(
                        ModItemRegister.EMERALD_CALL_MUG,
                        ModItemRegister.WORM_HOLE_MUG,
                        ModItemRegister.GROS_GUEULETON_MUG,
                        ModItemRegister.PAIN_DIEUX_MUG,
                        ModItemRegister.REAL_DWARVE_MUG,
                        ModItemRegister.PIED_DE_GEANTS_MUG,
                        ModItemRegister.LA_BLANCHE_DE_CHEZ_NOUS_MUG
                ));
        this.addTagsToTag(ModItemTags.BEER, ModItemTags.BEER_BOTTLE, ModItemTags.BEER_GLASS, ModItemTags.BEER_MUG);
        this.addTagsToTag(ModItemTags.COASTER_POSABLE, ModItemTags.BEER);
        this.addToTag(ModItemTags.COASTER_POSABLE,
                Stream.of(
                        ModItemRegister.EMPTY_POLYMORPHIC_MUG,
                        ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE,
                        ModItemRegister.EMPTY_POLYMORPHIC_GLASS
                ));
        this.copy(ModBlockTags.BREWING_BARREL, ModItemTags.BREWING_BARREL);
    }

    @SafeVarargs
    private void addToTag(TagKey<Item> itemTag, Stream<Supplier<? extends Item>>... itemStreams)
    {
        List<ResourceKey<Item>> itemRKs = Stream.of(itemStreams)
                .flatMap(s -> s)
                .map(s -> ModItemRegister.getResourceKey(s.get()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        this.tag(itemTag).add(itemRKs.toArray(new ResourceKey[0]));
    }

    @SafeVarargs
    private void addTagsToTag(TagKey<Item> itemTag, TagKey<Item>... itemTags)
    {
        for (TagKey<Item> key : itemTags)
        {
            this.tag(itemTag).addTag(key);
        }
    }
}