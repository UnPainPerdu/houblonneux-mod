package com.unpainperdu.houblonneux.datagen.data.tag;

import com.unpainperdu.houblonneux.Houblonneux;
import com.unpainperdu.houblonneux.register.fluid.ModFluidRegister;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.material.Fluid;

import java.util.concurrent.CompletableFuture;

public class ModFluidTagsProvider extends TagsProvider<Fluid>
{
    public ModFluidTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider)
    {
        super(output, Registries.FLUID, lookupProvider, Houblonneux.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries)
    {
        this.getOrCreateRawBuilder(ModFluidTags.BEER)
                .addElement(ModFluidRegister.EMERALD_CALL.getId())
                .addElement(ModFluidRegister.FLOWING_EMERALD_CALL.getId())
                .addElement(ModFluidRegister.WORM_HOLE.getId())
                .addElement(ModFluidRegister.FLOWING_WORM_HOLE.getId())
                .addElement(ModFluidRegister.GROS_GUEULETON.getId())
                .addElement(ModFluidRegister.FLOWING_GROS_GUEULETON.getId())
                .addElement(ModFluidRegister.PAIN_DIEUX.getId())
                .addElement(ModFluidRegister.FLOWING_PAIN_DIEUX.getId())
                .addElement(ModFluidRegister.REAL_DWARVE.getId())
                .addElement(ModFluidRegister.FLOWING_REAL_DWARVE.getId())
                .addElement(ModFluidRegister.PIED_DE_GEANTS.getId())
                .addElement(ModFluidRegister.FLOWING_PIED_DE_GEANTS.getId())
                .addElement(ModFluidRegister.LA_BLANCHE_DE_CHEZ_NOUS.getId())
                .addElement(ModFluidRegister.FLOWING_LA_BLANCHE_DE_CHEZ_NOUS.getId())
        ;
    }
}
