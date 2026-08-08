package com.unpainperdu.houblonneux.datagen.asset.model;

import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.world.level.block.Block;

public class ModTextureMapper
{
    public static TextureMapping getBrewingStationTextureMapping(Block brewingBarrelBlock)
    {
        return new TextureMapping()
                .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(brewingBarrelBlock))
                .put(ModTextureSlot.MATERIAL, TextureMapping.getBlockTexture(brewingBarrelBlock));
    }
}