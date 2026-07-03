package com.unpainperdu.houblonneux.datagen.asset.model;

import com.unpainperdu.houblonneux.Houblonneux;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.resources.Identifier;

import java.util.Optional;

public class ModModelTemplate
{
    // id = json model name
    public static final ModelTemplate EMPTY = create("empty");
    public static final ModelTemplate EMPTY_WITH_PARTICLE = create("empty_with_particle", TextureSlot.PARTICLE);

    public static ModelTemplate create(TextureSlot... slots)
    {
        return new ModelTemplate(Optional.empty(), Optional.empty(), slots);
    }

    public static ModelTemplate create(String id, TextureSlot... slots)
    {
        return new ModelTemplate(Optional.of(createDecoratedId(id)), Optional.empty(), slots);
    }

    public static ModelTemplate createItem(String id, TextureSlot... slots)
    {
        return new ModelTemplate(Optional.of(createDecoratedId(id)), Optional.empty(), slots);
    }

    public static ModelTemplate createItem(String id, String suffix, TextureSlot... slots)
    {
        return new ModelTemplate(Optional.of(createDecoratedId(id)), Optional.of(suffix), slots);
    }

    public static ModelTemplate create(String id, String suffix, TextureSlot... slots)
    {
        return new ModelTemplate(Optional.of(createDecoratedId(id)), Optional.of(suffix), slots);
    }

    public static Identifier createDecoratedId(String path)
    {
        return Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, path).withPrefix("block/");
    }
}