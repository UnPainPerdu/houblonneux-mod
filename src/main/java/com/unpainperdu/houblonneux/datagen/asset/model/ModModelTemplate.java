package com.unpainperdu.houblonneux.datagen.asset.model;

import com.unpainperdu.houblonneux.Houblonneux;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.resources.Identifier;

import java.util.Optional;

public class ModModelTemplate
{
    // id = json model name
    public static final ModelTemplate EMPTY_WITH_PARTICLE = createBlock("empty_with_particle", TextureSlot.PARTICLE);
    public static final ModelTemplate COASTER_1 = createBlock("coaster_1");
    public static final ModelTemplate COASTER_2 = createBlock("coaster_2");
    public static final ModelTemplate COASTER_3 = createBlock("coaster_3");
    public static final ModelTemplate COASTER_4 = createBlock("coaster_4");
    public static final ModelTemplate BREWING_BARREL = createBlock("brewing_barrel", TextureSlot.PARTICLE, ModTextureSlot.MATERIAL);
    public static final ModelTemplate BREWING_BARREL_FRONT = createBlock("brewing_barrel_front");

    public static ModelTemplate createBlock(TextureSlot... slots)
    {
        return new ModelTemplate(Optional.empty(), Optional.empty(), slots);
    }

    public static ModelTemplate createBlock(String id, TextureSlot... slots)
    {
        return new ModelTemplate(Optional.of(createBlockDecoratedId(id)), Optional.empty(), slots);
    }

    public static ModelTemplate createBlock(String id, String suffix, TextureSlot... slots)
    {
        return new ModelTemplate(Optional.of(createBlockDecoratedId(id)), Optional.of(suffix), slots);
    }

    public static Identifier createBlockDecoratedId(String path)
    {
        return Identifier.fromNamespaceAndPath(Houblonneux.MOD_ID, path).withPrefix("block/");
    }
}