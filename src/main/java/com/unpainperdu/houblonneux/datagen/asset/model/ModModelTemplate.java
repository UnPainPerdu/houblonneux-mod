package com.unpainperdu.houblonneux.datagen.asset.model;

import com.unpainperdu.houblonneux.Houblonneux;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.resources.Identifier;

import java.util.Optional;

public class ModModelTemplate
{
    // id = json model name
    public static final ModelTemplate EMPTY_WITH_PARTICLE = create("empty_with_particle", TextureSlot.PARTICLE);
    public static final ModelTemplate COASTER_1 = create("coaster_1");
    public static final ModelTemplate COASTER_2 = create("coaster_2");
    public static final ModelTemplate COASTER_3 = create("coaster_3");
    public static final ModelTemplate COASTER_4 = create("coaster_4");
    public static final ModelTemplate BREWING_BARREL = create("brewing_barrel", TextureSlot.PARTICLE, ModTextureSlot.MATERIAL);
    public static final ModelTemplate BREWING_BARREL_FRONT = create("brewing_barrel_front");

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