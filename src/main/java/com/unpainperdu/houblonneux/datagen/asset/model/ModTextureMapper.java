package com.unpainperdu.houblonneux.datagen.asset.model;

import com.unpainperdu.houblonneux.level.world.block.block.BrewingBarrelBlock;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;

public class ModTextureMapper
{
    public static TextureMapping getBrewingStationTextureMapping(BrewingBarrelBlock brewingBarrelBlock)
    {
        return new TextureMapping()
                .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(brewingBarrelBlock))
                .put(ModTextureSlot.MATERIAL, TextureMapping.getBlockTexture(brewingBarrelBlock));
    }
}