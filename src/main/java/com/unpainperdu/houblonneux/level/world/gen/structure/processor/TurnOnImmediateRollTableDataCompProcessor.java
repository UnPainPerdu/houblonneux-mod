package com.unpainperdu.houblonneux.level.world.gen.structure.processor;

import com.mojang.serialization.MapCodec;
import com.unpainperdu.houblonneux.level.world.block.entity.RandomizableDispenserTrade;
import com.unpainperdu.houblonneux.register.block.ModBlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jspecify.annotations.Nullable;

public class TurnOnImmediateRollTableDataCompProcessor implements StructureProcessor
{
    public static final MapCodec<TurnOnImmediateRollTableDataCompProcessor> CODEC = MapCodec.unit(new TurnOnImmediateRollTableDataCompProcessor());

    @Override
    public MapCodec<TurnOnImmediateRollTableDataCompProcessor> codec()
    {
        return CODEC;
    }

    @Override
    public StructureTemplate.@Nullable StructureBlockInfo process(LevelReader level, BlockPos targetPosition, BlockPos referencePos, StructureTemplate.StructureBlockInfo originalBlockInfo, StructureTemplate.StructureBlockInfo processedBlockInfo, StructurePlaceSettings settings, @Nullable StructureTemplate template)
    {
        BlockState originalState = originalBlockInfo.state();
        if (originalState.is(ModBlockRegister.BEER_DISPENSER))
        {
            @Nullable CompoundTag components = originalBlockInfo.nbt();
            if (components != null)
            {
                components = components.copy();
                if (components.contains(RandomizableDispenserTrade.DEFAULT_NBT_NAME))
                {
                    components.putBoolean(RandomizableDispenserTrade.DEFAULT_NBT_NAME, true);
                    return new StructureTemplate.StructureBlockInfo(targetPosition, originalState, components);
                }
            }
        }
        return processedBlockInfo;
    }
}