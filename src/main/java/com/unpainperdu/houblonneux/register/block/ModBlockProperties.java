package com.unpainperdu.houblonneux.register.block;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class ModBlockProperties
{
    public static final BlockBehaviour.Properties HOP_PROPERTIES = BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .randomTicks()
            .destroyTime(0.2F)
            .sound(SoundType.CROP)
            .pushReaction(PushReaction.DESTROY)
            .noCollision();
    public static final BlockBehaviour.Properties BEER_DISPENSER_PROPERTIES = BlockBehaviour.Properties.of()
            .pushReaction(PushReaction.DESTROY)
            .sound(SoundType.METAL)
            .mapColor(MapColor.METAL)
            .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
            .requiresCorrectToolForDrops()
            .strength(5.0F, 6.0F);

    public static final BlockBehaviour.Properties COASTER_PROPERTIES = BlockBehaviour.Properties.of()
            .sound(SoundType.WOOD)
            .mapColor(MapColor.COLOR_BROWN)
            .instabreak()
            .noOcclusion()
            .pushReaction(PushReaction.DESTROY);
}