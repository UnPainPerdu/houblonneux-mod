package com.unpainperdu.houblonneux.datagen.data.tag;

import com.unpainperdu.houblonneux.Houblonneux;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public class ModFluidTags
{
    public static final TagKey<Fluid> BEER = create("beer");

    public static TagKey<Fluid> create(String name)
    {
        return TagKey.create(Registries.FLUID, Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, name));
    }
}