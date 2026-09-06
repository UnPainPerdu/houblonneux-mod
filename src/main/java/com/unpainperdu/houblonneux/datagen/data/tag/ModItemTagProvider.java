package com.unpainperdu.houblonneux.datagen.data.tag;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.register.item.ModItemRegister;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagCopyingItemTagProvider;

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
        this.addToTag(ModItemTags.COASTER_POSABLE,
                Stream.of(
                        ModItemRegister.EMPTY_POLYMORPHIC_BOTTLE,
                        ModItemRegister.EMPTY_POLYMORPHIC_MUG,
                        ModItemRegister.EMPTY_POLYMORPHIC_GLASS,
                        //emerald_call
                        ModItemRegister.EMERALD_CALL_BOTTLE,
                        ModItemRegister.EMERALD_CALL_MUG,
                        ModItemRegister.EMERALD_CALL_GLASS,
                        //worm_hole
                        ModItemRegister.WORM_HOLE_BOTTLE,
                        ModItemRegister.WORM_HOLE_MUG,
                        ModItemRegister.WORM_HOLE_GLASS,
                        //gros_gueuleton
                        ModItemRegister.GROS_GUEULETON_BOTTLE,
                        ModItemRegister.GROS_GUEULETON_MUG,
                        ModItemRegister.GROS_GUEULETON_GLASS,
                        //  pain_dieux
                        ModItemRegister.PAIN_DIEUX_BOTTLE,
                        ModItemRegister.PAIN_DIEUX_GLASS,
                        ModItemRegister.PAIN_DIEUX_MUG,
                        //  real_dwarve
                        ModItemRegister.REAL_DWARVE_BOTTLE,
                        ModItemRegister.REAL_DWARVE_GLASS,
                        ModItemRegister.REAL_DWARVE_MUG,
                        //  pied_de_geants
                        ModItemRegister.PIED_DE_GEANTS_BOTTLE,
                        ModItemRegister.PIED_DE_GEANTS_GLASS,
                        ModItemRegister.PIED_DE_GEANTS_MUG,
                        //  la_blanche_de_chez_nous
                        ModItemRegister.LA_BLANCHE_DE_CHEZ_NOUS_BOTTLE,
                        ModItemRegister.LA_BLANCHE_DE_CHEZ_NOUS_GLASS,
                        ModItemRegister.LA_BLANCHE_DE_CHEZ_NOUS_MUG
                )
        );
        this.copy(ModBlockTags.BREWING_BARREL, ModItemTags.BREWING_BARREL);
    }

    @SafeVarargs
    private void addToTag(TagKey<Item> itemTag, Stream<Supplier<? extends Item>>... itemStreams)
    {
        this.tag(itemTag).add(
                Stream.of(itemStreams)
                        .flatMap(s -> s)
                        .map(Supplier::get)
                        .toArray(Item[]::new)
        );
    }


}